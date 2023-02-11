package org.liangguo.breakingbad.entity.ai.goal

import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.goal.Goal
import org.liangguo.breakingbad.entity.base.SpellcasterImp
import java.util.*

/**
 * @author ldh
 * 时间: 2023/2/5 16:04
 * 邮箱: ldh.liangguo@outlook.com
 *
 * 祈祷施法
 */
class CastingSpellGoal<T: Mob>(private val spellCaster: SpellcasterImp<T>) : Goal() {

    init {
        flags = EnumSet.of(Flag.MOVE, Flag.LOOK)
    }

    override fun canUse() = spellCaster.isSpellCasting

    override fun start() {
        //开始施法的时候停止走路
        spellCaster.mob.navigation.stop()
    }

    override fun tick() {
        super.tick()
        //施法的时候盯着目标
        spellCaster.mob.target?.let { target ->
            spellCaster.mob.lookControl.setLookAt(
                target,
                spellCaster.mob.maxHeadYRot.toFloat(),
                spellCaster.mob.maxHeadXRot.toFloat()
            )
        }
    }

}