package org.liangguo.breakingbad.block.base

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.Property
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraftforge.common.ForgeHooks
import java.util.function.Supplier

open class HighCropBlock(properties: Properties, seed: Supplier<out ItemLike>) : BaseCropBlock(properties, seed) {


    open val upperProperty: BooleanProperty
        get() = UPPER

    init {
        registerDefaultState(
            ((stateDefinition.any() as BlockState).setValue(AGE, 0) as BlockState).setValue(
                upperProperty, false
            ) as BlockState
        )
    }

    open val growUpperAge: Int
        get() = 3

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(*arrayOf<Property<*>>(AGE, upperProperty))
    }

    @Deprecated("Deprecated in Java")
    override fun getShape(
        state: BlockState,
        worldIn: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        return SHAPE_BY_AGE[(state.getValue(this.ageProperty) as Int)]
    }

    @Deprecated("Deprecated in Java")
    override fun canSurvive(state: BlockState, worldIn: LevelReader, pos: BlockPos): Boolean {
        val downpos = pos.below()
        return if (!worldIn.getBlockState(downpos).`is`(this)) {
            super.canSurvive(state, worldIn, pos)
        } else {
            !worldIn.getBlockState(downpos).getValue(upperProperty) && (worldIn.getRawBrightness(
                pos,
                0
            ) >= 8 || worldIn.canSeeSky(pos)) && getAge(worldIn.getBlockState(downpos)) >= growUpperAge
        }
    }

    override fun getStateForAge(age: Int): BlockState {
        return super.getStateForAge(age)
    }

    @Deprecated("Deprecated in Java")
    override fun randomTick(state: BlockState, worldIn: ServerLevel, pos: BlockPos, rand: RandomSource) {
        if (worldIn.isAreaLoaded(pos, 1)) {
            val f = getGrowthSpeed(this, worldIn, pos)
            val age = getAge(state)
            if (worldIn.getRawBrightness(pos, 0) >= 9 && age < this.maxAge && ForgeHooks.onCropsGrowPre(
                    worldIn,
                    pos,
                    state,
                    rand.nextInt((25.0f / f).toInt() + 1) == 0
                )
            ) {
                worldIn.setBlock(
                    pos, getStateForAge(age + 1).setValue(
                        upperProperty, state.getValue(
                            upperProperty
                        ) as Boolean
                    ) as BlockState, 2
                )
                ForgeHooks.onCropsGrowPost(worldIn, pos, state)
            }
            if (!state.getValue(upperProperty)) {
                if (age >= growUpperAge && ForgeHooks.onCropsGrowPre(
                        worldIn,
                        pos,
                        state,
                        rand.nextInt((25.0f / f).toInt() + 1) == 0
                    ) && defaultBlockState().canSurvive(worldIn, pos.above()) && worldIn.isEmptyBlock(pos.above())
                ) {
                    worldIn.setBlockAndUpdate(
                        pos.above(),
                        defaultBlockState().setValue(upperProperty, true) as BlockState
                    )
                    ForgeHooks.onCropsGrowPost(worldIn, pos, state)
                }
            }
        }
    }

    override fun isValidBonemealTarget(
        worldIn: BlockGetter,
        pos: BlockPos,
        state: BlockState,
        isClient: Boolean
    ): Boolean {
        val upperState = worldIn.getBlockState(pos.above())
        return if (upperState.`is`(this)) {
            !isMaxAge(upperState)
        } else if (state.getValue(upperProperty) as Boolean) {
            !isMaxAge(state)
        } else {
            true
        }
    }

    override fun isBonemealSuccess(worldIn: Level, rand: RandomSource, pos: BlockPos, state: BlockState): Boolean {
        return true
    }

    override fun performBonemeal(worldIn: ServerLevel, rand: RandomSource, pos: BlockPos, state: BlockState) {
        val ageGrowth = (getAge(state) + getBonemealAgeIncrease(worldIn)).coerceAtMost(15)
        if (ageGrowth <= this.maxAge) {
            worldIn.setBlockAndUpdate(pos, state.setValue(AGE, ageGrowth) as BlockState)
        } else {
            worldIn.setBlockAndUpdate(pos, state.setValue(AGE, this.maxAge) as BlockState)
            if (state.getValue(upperProperty) as Boolean) {
                return
            }
            val top = worldIn.getBlockState(pos.above())
            if (top.`is`(this)) {
                val growable = worldIn.getBlockState(pos.above()).block as BonemealableBlock
                if (growable.isValidBonemealTarget(worldIn, pos.above(), top, false)) {
                    growable.performBonemeal(worldIn, worldIn.random, pos.above(), top)
                }
            } else {
                val remainingGrowth = ageGrowth - this.maxAge - 1
                if (defaultBlockState().canSurvive(worldIn, pos.above()) && worldIn.isEmptyBlock(pos.above())) {
                    worldIn.setBlock(
                        pos.above(),
                        (defaultBlockState().setValue(upperProperty, true) as BlockState).setValue(
                            this.ageProperty,
                            remainingGrowth
                        ) as BlockState,
                        3
                    )
                }
            }
        }
    }

    companion object {

        val UPPER = BooleanProperty.create("upper")

        private val SHAPE_BY_AGE = arrayOf(
            box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
        )
    }
}