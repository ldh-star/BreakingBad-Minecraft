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
 * 时间: 2023/2/6 21:30
 * 邮箱: ldh.liangguo@outlook.com
 */
class UndeadZombieSpawnEgg(properties: Properties) :
    ForgeSpawnEggItem(EntityRegistry.UNDEAD_ZOMBIE, 0x9402f1, 0x4cf51a, properties) {

    override fun appendHoverText(
        itemStack: ItemStack,
        level: Level?,
        components: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        components.customItemTooltips(listOf(R.string.explosion_immunity.withStyle(ChatFormatting.YELLOW)))
        super.appendHoverText(itemStack, level, components, tooltipFlag)
    }

}