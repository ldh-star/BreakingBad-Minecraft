package org.liangguo.breakingbad.entity.ai.undeadgus

import org.liangguo.breakingbad.entity.base.Spell


/**
 * @author ldh
 * 时间: 2023/2/5 18:18
 * 邮箱: ldh.liangguo@outlook.com
 *
 * 亡灵古斯的祈祷技能
 */
sealed interface UndeadGusSpell : Spell {

    object SummonZombie : UndeadGusSpell {
        override val colorR = 0.7
        override val colorG = 0.7
        override val colorB = 0.8
        override fun toString(): String {
            return "SummonZombie"
        }
    }

    object None : UndeadGusSpell {
        override val colorR = 0.0
        override val colorG = 0.0
        override val colorB = 0.0
        override fun toString(): String {
            return "None"
        }
    }

    companion object {
        val ARRAY = arrayOf(None, SummonZombie)

        fun getById(id: Byte) = ARRAY[id.toInt()]

        val UndeadGusSpell.id: Byte
            get() = ARRAY.indexOf(this).toByte()

    }

}