package org.liangguo.breakingbad.item.custom

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraftforge.common.ForgeSpawnEggItem
import org.liangguo.breakingbad.R
import org.liangguo.breakingbad.init.EntityRegistry
import org.liangguo.breakingbad.utils.AppUtils.customItemTooltips


/**
 * @author ldh
 * 时间: 2023/1/28 18:24
 * 邮箱: ldh.liangguo@outlook.com
 */
class GusFringSpawnEgg(properties: Properties) :
    ForgeSpawnEggItem(EntityRegistry.GUS_FRING, 0x223a12, 0xcc53fa, properties) {

    override fun appendHoverText(
        itemStack: ItemStack,
        level: Level?,
        components: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        components.customItemTooltips(
            listOf(R.string.gus_fring_soubriquet.withStyle(ChatFormatting.GREEN)),
            listOf(R.string.gus_fring_introduction)
        )
        super.appendHoverText(itemStack, level, components, tooltipFlag)
    }

}
