package org.liangguo.breakingbad.entity.base

import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.ai.goal.Goal


/**
 * @author ldh
 * 时间: 2023/2/5 18:41
 * 邮箱: ldh.liangguo@outlook.com
 */
abstract class SpellcasterUseSpellGoal<E : Spell, T : HumanoidSpellCasterEntity<E>>(private val mob: T) : Goal() {

    var attackWarmupDelay = 0

    /**
     * 下次可以施法的时间（技能什么时候好）
     */
    var nextAttackTickCount = 0

    /**
     * 效果持续的时间，默认和施法时间一致
     */
    protected open val castWarmupTime
        get() = castingTime

    protected abstract val castingInterval: Int

    protected abstract val castingTime: Int

    override fun canUse(): Boolean {
        mob.target?.let { target ->
            if (target.isAlive) {
                return if (mob.isSpellCasting) {
                    false
                } else {
                    mob.tickCount >= nextAttackTickCount
                }
            }
        }
        return false
    }

    override fun canContinueToUse(): Boolean {
        mob.target?.let { target ->
            return target.isAlive && attackWarmupDelay > 0
        }
        return false
    }

    override fun start() {
        attackWarmupDelay = adjustedTickDelay(castWarmupTime)
        mob.spellTicks = castingTime
        nextAttackTickCount = mob.tickCount + castingInterval
        getSpellPrepareSound()?.let {
            mob.playSound(it, 1f, 1f)
        }
        mob.currentSpell = getSpell()
    }

    override fun tick() {
        --attackWarmupDelay
        if (attackWarmupDelay == 0) {
            performSpellCasting()
            mob.getCastingSoundEvent()?.let {
                mob.playSound(it, 1f, 1f)
            }
        }
    }

    protected abstract fun performSpellCasting()

    protected open fun getSpellPrepareSound(): SoundEvent? = null

    protected abstract fun getSpell(): E

}