package org.liangguo.breakingbad.item.custom

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.common.ForgeSpawnEggItem
import org.liangguo.breakingbad.init.EntityRegistry
import org.liangguo.breakingbad.utils.AppUtils.customItemTooltips
import org.liangguo.breakingbad.utils.withStyle


/**
 * @author ldh
 * 时间: 2023/2/4 17:35
 * 邮箱: ldh.liangguo@outlook.com
 */
class TwinBrotherBlueSpawnEgg(properties: Properties) :
    ForgeSpawnEggItem(EntityRegistry.TWIN_BROTHER_BLUE, 0x623fa0, 0x3cf6af, properties) {
    override fun appendHoverText(
        itemStack: ItemStack,
        level: Level?,
        components: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        components.customItemTooltips(
            listOf("Leonel Salamanca".withStyle(ChatFormatting.GREEN)),
        )
        super.appendHoverText(itemStack, level, components, tooltipFlag)
    }


}