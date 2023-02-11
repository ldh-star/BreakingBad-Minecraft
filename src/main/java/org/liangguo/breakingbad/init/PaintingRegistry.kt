package org.liangguo.breakingbad.init

import net.minecraft.world.entity.decoration.PaintingVariant
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import org.liangguo.breakingbad.R


/**
 * @author ldh
 * 时间: 2023/2/4 21:41
 * 邮箱: ldh.liangguo@outlook.com
 */
object PaintingRegistry {
    private val Int.block: Int
        get() = this * 16

    val PAINTING_VARIANTS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, R.MOD_ID)

    val HANK_1_1 = PAINTING_VARIANTS.register("hank_1_1") {
        PaintingVariant(1.block, 1.block)
    }

    val MIKE_1_2 = PAINTING_VARIANTS.register("mike_1_2") {
        PaintingVariant(1.block, 2.block)
    }

    val WHITE_2_1 = PAINTING_VARIANTS.register("white_2_1") {
        PaintingVariant(2.block, 1.block)
    }

    val COVER_2_2 = PAINTING_VARIANTS.register("cover_2_2") {
        PaintingVariant(2.block, 2.block)
    }

    val GUS_3_2 = PAINTING_VARIANTS.register("gus_3_2") {
        PaintingVariant(3.block, 2.block)
    }

    val COVER_B_2_1 = PAINTING_VARIANTS.register("cover_b_2_1") {
        PaintingVariant(2.block, 1.block)
    }

    val COVER_2_1 = PAINTING_VARIANTS.register("cover_2_1") {
        PaintingVariant(2.block, 1.block)
    }


    fun IEventBus.registerPaintings() {
        PAINTING_VARIANTS.register(this)
    }

}