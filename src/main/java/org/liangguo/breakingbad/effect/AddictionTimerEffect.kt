package org.liangguo.breakingbad.effect

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import org.liangguo.breakingbad.init.EffectRegistry


/**
 * @author ldh
 * 时间: 2023/2/10 21:11
 * 邮箱: ldh.liangguo@outlook.com
 *
 * 这个类没有游戏中的具体效果，而是作为毒瘾的计时器，会使实体进入毒瘾发作阶段
 */
class AddictionTimerEffect(category: MobEffectCategory, color: Int) : MobEffect(category, color) {
    
    override fun isDurationEffectTick(duration: Int, amplifier: Int): Boolean {
        if (duration == 1) return true
        return false
    }

    override fun applyEffectTick(entity: LivingEntity, amplifier: Int) {
        entity.apply {
            addEffect(MobEffectInstance(EffectRegistry.ADDICTION.get(), DURATION))
            addEffect(MobEffectInstance(MobEffects.WEAKNESS, DURATION))
            addEffect(MobEffectInstance(MobEffects.CONFUSION, DURATION))
            addEffect(MobEffectInstance(MobEffects.DARKNESS, DURATION))
            addEffect(MobEffectInstance(MobEffects.HUNGER, DURATION))
            addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, DURATION))
        }
    }
    
    companion object {
        /**
         * 毒瘾犯了要持续两分钟的效果
         */
        private const val DURATION = 2 * 60 * 20
    }

}