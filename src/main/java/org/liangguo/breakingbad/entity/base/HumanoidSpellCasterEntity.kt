package org.liangguo.breakingbad.entity.base

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import org.liangguo.breakingbad.entity.ai.goal.CastingSpellGoal


/**
 * @author ldh
 * 时间: 2023/2/7 14:52
 * 邮箱: ldh.liangguo@outlook.com
 *
 * 可祈祷施法的人物
 */
abstract class HumanoidSpellCasterEntity<T : Spell>(
    type: EntityType<out HumanoidEntity>,
    level: Level,
) : HumanoidEntity(type, level), SpellcasterImp<HumanoidEntity> {


    override var spellTicks = 0

    override val spellColor = arrayOf(0.0, 0.0, 0.0)

    override val mob
        get() = this

    /**
     * 当前正在祈祷的类型
     */
    abstract var currentSpell: T

    override fun registerGoals() {
        super.registerGoals()
        goalSelector.addGoal(1, CastingSpellGoal(this))
    }

    override fun addAdditionalSaveData(compoundTag: CompoundTag) {
        super.addAdditionalSaveData(compoundTag)
        compoundTag.putInt(SAVE_DATA_KEY_SPELL_TICKS, spellTicks)
    }

    override fun readAdditionalSaveData(compoundTag: CompoundTag) {
        super.readAdditionalSaveData(compoundTag)
        if (compoundTag.contains(SAVE_DATA_KEY_SPELL_TICKS)) {
            spellTicks = compoundTag.getInt(SAVE_DATA_KEY_SPELL_TICKS)
        }
    }

    override fun tick() {
        super.tick()
        if (level.isClientSide && isSpellCasting) {
            addSpellingParticle()
        }
    }

    override fun customServerAiStep() {
        super.customServerAiStep()
        if (spellTicks > 0) {
            spellTicks--
        }
    }

    companion object {
        const val SAVE_DATA_KEY_SPELL_TICKS = "save_data_key_spell_ticks"
    }

}