package org.liangguo.breakingbad.item.base

import net.minecraft.ChatFormatting
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import org.liangguo.breakingbad.utils.AppUtils.customItemTooltips


/**
 * @author ldh
 * 时间: 2023/1/27 16:35
 * 邮箱: ldh.liangguo@outlook.com
 *
 * 将鼠标移到该物品上显示的信息
 *
 * @param toolsTips 显示的信息
 * @param shiftTooltips 按下shift健时附加显示的信息
 */
open class TooltipItem(
    properties: Properties,
    private val toolsTips: List<Component> = listOf(),
    private val shiftTooltips: List<Component> = listOf(),
) : Item(properties) {

    override fun appendHoverText(
        itemStack: ItemStack,
        level: Level?,
        components: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        components.customItemTooltips(toolsTips, shiftTooltips)
        super.appendHoverText(itemStack, level, components, tooltipFlag)
    }

}