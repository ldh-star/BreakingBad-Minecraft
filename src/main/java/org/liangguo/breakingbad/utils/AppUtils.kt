package org.liangguo.breakingbad.utils

import net.minecraft.ChatFormatting
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import org.liangguo.breakingbad.R


/**
 * @author ldh
 * 时间: 2023/1/28 16:47
 * 邮箱: ldh.liangguo@outlook.com
 */
object AppUtils {

    fun resourceLocation(id: String, modId: String = R.MOD_ID) = ResourceLocation(modId, id)

    /**
     * 为物品添加自定义的描述
     */
    fun MutableList<Component>.customItemTooltips(
        toolsTips: List<Component> = listOf(),
        shiftTooltips: List<Component> = listOf()
    ) {
        addAll(toolsTips)
        if (shiftTooltips.isNotEmpty()) {
            if (Screen.hasShiftDown()) {
                addAll(shiftTooltips)
            } else {
                add(R.string.press_shift_to_look_details.withStyle(ChatFormatting.BLUE))
            }
        }
    }

}


