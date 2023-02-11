package org.liangguo.breakingbad.entity.base

import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundEvent
import net.minecraft.util.Mth
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.Mob


/**
 * @author ldh
 * 时间: 2023/2/5 15:11
 * 邮箱: ldh.liangguo@outlook.com
 */
interface SpellcasterImp<T: Mob> {

    /**
     * 正在祈祷、念咒
     */
    val isSpellCasting: Boolean

    var spellTicks: Int

    val spellColor: Array<Double>

    val mob: T

    /**
     * 为正在祈祷施法的实体添加例子效果
     */
    fun LivingEntity.addSpellingParticle() {
        val f: Float = yBodyRot * (Math.PI.toFloat() / 180f) + Mth.cos(tickCount.toFloat() * 0.6662f) * 0.25f
        val f1: Float = Mth.cos(f)
        val f2: Float = Mth.sin(f)
        this.level.addParticle(
            ParticleTypes.ENTITY_EFFECT,
            x + f1.toDouble() * 0.6,
            y + 1.8,
            z + f2.toDouble() * 0.6,
            spellColor[0],
            spellColor[1],
            spellColor[2]
        )
        this.level.addParticle(
            ParticleTypes.ENTITY_EFFECT,
            x - f1.toDouble() * 0.6,
            y + 1.8,
            z - f2.toDouble() * 0.6,
            spellColor[0],
            spellColor[1],
            spellColor[2]
        )

    }

    fun getCastingSoundEvent(): SoundEvent? = null


}