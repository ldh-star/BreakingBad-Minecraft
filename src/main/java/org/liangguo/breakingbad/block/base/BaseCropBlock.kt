package org.liangguo.breakingbad.block.base

import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.CropBlock
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Supplier

open class BaseCropBlock(properties: Properties, private val seedItem: Supplier<out ItemLike>) : CropBlock(properties) {
    override fun getBaseSeedId(): ItemLike {
        return seedItem.get()
    }

    fun withAge(age: Int): BlockState {
        return defaultBlockState().setValue(this.ageProperty, age) as BlockState
    }
}