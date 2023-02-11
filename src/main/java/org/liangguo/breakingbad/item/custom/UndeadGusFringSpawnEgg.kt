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
 * 时间: 2023/2/1 14:03
 * 邮箱: ldh.liangguo@outlook.com
 */
class UndeadGusFringSpawnEgg(properties: Properties) :
    ForgeSpawnEggItem(EntityRegistry.UNDEAD_GUS_FRING, 0x4C6578FE, 0xFFEFB8C8.toInt(), properties) {

    override fun appendHoverText(
        itemStack: ItemStack,
        level: Level?,
        components: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        R.string
        components.customItemTooltips(
            listOf(
                R.string.fire_immunity.withStyle(ChatFormatting.YELLOW),
                R.string.explosion_immunity.withStyle(ChatFormatting.YELLOW)
            )
        )
        super.appendHoverText(itemStack, level, components, tooltipFlag)
    }

}
