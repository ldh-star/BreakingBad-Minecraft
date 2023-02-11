package org.liangguo.breakingbad.block.crops


import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import org.liangguo.breakingbad.block.base.HighCropBlock
import java.util.function.Supplier


/**
 * @author ldh
 * 时间: 2023/1/27 17:21
 * 邮箱: ldh.liangguo@outlook.com
 */
class CannabisCrop<T : ItemLike>(properties: Properties, seed: Supplier<T>) :
    HighCropBlock(properties, seed) {

    //todo 大麻叶 + 纸张 = 烟

    private val UPPER_SHAPE_BY_AGE = arrayOf(
        Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
        Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
        Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
        Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
        Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
        Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
        Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
        Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0)
    )

    override val growUpperAge = 4

    @Deprecated("Deprecated in Java")
    override fun getShape(
        state: BlockState,
        worldIn: BlockGetter,
        pos: BlockPos,
        context: CollisionContext
    ): VoxelShape {
        return if (state.getValue(UPPER)) UPPER_SHAPE_BY_AGE[state.getValue(this.ageProperty)] else super.getShape(
            state,
            worldIn,
            pos,
            context
        )
    }

}