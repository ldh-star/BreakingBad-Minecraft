package org.liangguo.breakingbad

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent


/**
 * @author ldh
 * 时间: 2023/2/3 12:40
 * 邮箱: ldh.liangguo@outlook.com
 */
object R {

    const val MOD_ID = "breaking_bad"

    object string {

        val gus_fring_soubriquet: MutableComponent get() = fromResource("gus_fring_soubriquet")
        val gus_fring_introduction: MutableComponent get() = fromResource("gus_fring_introduction")
        val press_shift_to_look_details: MutableComponent get() = fromResource("press_shift_to_look_details")
        val fire_immunity: MutableComponent get() = fromResource("fire_immunity")
        val explosion_immunity: MutableComponent get() = fromResource("explosion_immunity")

        private fun fromResource(id: String) = Component.translatable("$MOD_ID.string.$id")
    }
}