package org.liangguo.breakingbad.effect

import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob
import org.liangguo.breakingbad.utils.tryAs
import kotlin.random.Random


/**
 * @author ldh
 * 时间: 2023/2/10 18:37
 * 邮箱: ldh.liangguo@outlook.com
 */
class AddictionEffect(category: MobEffectCategory, color: Int) : MobEffect(category, color) {

//https://minecraft.fandom.com/zh/wiki/%E7%8A%B6%E6%80%81%E6%95%88%E6%9E%9C?variant=zh

    /**
     * 某一个效果生效时的逻辑在这里实现
     */
    override fun applyEffectTick(entity: LivingEntity, amplifier: Int) {
        //0就是戒毒成功了
        val duration = entity.getEffect(this)?.duration ?: 0
        with(entity) {
//            when (duration) {
//                in 1 until 200 -> addictionDamage().loseSleep()
//                in 200 until 400 -> loseSleep()
//                else -> Unit
//            }
            addictionDamage().loseSleep()
        }
        //todo 让生物吸毒，给生物添加goal来驯化他们
        super.applyEffectTick(entity, amplifier)
    }

    /**
     * 犯毒瘾者概率性失眠
     */
    private fun LivingEntity.loseSleep() = apply {
        if (isSleeping) {
            if (Random.nextFloat() < 0.2f) {
                stopSleeping()
            }
        }
    }

    /**
     * 毒瘾开始对上瘾者造成伤害
     */
    private fun LivingEntity.addictionDamage() = apply {
        hurt(DamageSource.STARVE, 1f)
    }

    /**
     * 在某一帧是否执行该效果导致的逻辑
     *
     * @param amplifier 增益参数，这个数越大，那么这个效果逻辑就会执行的越频繁
     * @param duration 逻辑帧，到0时效果消失
     */
    override fun isDurationEffectTick(duration: Int, amplifier: Int): Boolean {
        val k = 50 shr amplifier
        return if (k > 0) duration % k == 0
        else true
    }

}