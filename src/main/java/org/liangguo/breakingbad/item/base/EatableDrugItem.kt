package org.liangguo.breakingbad.item.base

import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level


/**
 * @author ldh
 * 时间: 2023/1/27 17:05
 * 邮箱: ldh.liangguo@outlook.com
 */
open class EatableDrugItemTooltipItem(
    properties: Properties,
    private val toolsTips: List<Component> = emptyList(),
    private val shiftTooltips: List<Component> = emptyList(),
    private val soundEvent: SoundEvent? = null,
    private val damage: Float = 0f,
) : TooltipItem(properties, toolsTips, shiftTooltips) {


    /**
     * 毒品吃完了过后会发生的事情
     */
    override fun finishUsingItem(itemStack: ItemStack, level: Level, entity: LivingEntity): ItemStack {
        soundEvent?.let {
            entity.playSound(it)
        }
        if (damage != 0f) {
            if (!level.isClientSide) {
                entity.hurt(DamageSource.STARVE, damage)
            }
        }
        return super.finishUsingItem(itemStack, level, entity)
    }

}