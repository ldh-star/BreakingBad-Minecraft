package org.liangguo.breakingbad.entity.ai.undeadgus

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.MobSpawnType
import org.liangguo.breakingbad.entity.base.SpellcasterUseSpellGoal
import org.liangguo.breakingbad.entity.mobs.UndeadGusFring
import org.liangguo.breakingbad.init.EntityRegistry
import org.liangguo.breakingbad.utils.tryAs


/**
 * @author ldh
 * 时间: 2023/2/5 19:17
 * 邮箱: ldh.liangguo@outlook.com
 */
class UndeadGusSummonSpellGoal(private val mob: UndeadGusFring, private val onStop: () -> Unit) :
    SpellcasterUseSpellGoal<UndeadGusSpell, UndeadGusFring>(mob) {

    /**
     * 一次施法持续4秒
     */
    override val castingTime = 80

    /**
     * 技能冷却15秒
     */
    override val castingInterval = 300


    override fun getSpell() = UndeadGusSpell.SummonZombie

    override fun performSpellCasting() {
        mob.apply {
            level.tryAs<ServerLevel> {
                performSpellCasting(it)
            }
        }
    }

    private fun UndeadGusFring.performSpellCasting(level: ServerLevel) {
        for (i in 0..2) {
            val position: BlockPos = blockPosition()
                .offset(-2 + random.nextInt(5), 1, -2 + random.nextInt(5))
            val zombie = EntityRegistry.UNDEAD_ZOMBIE.get().create(level)?.apply {
                moveTo(position, 0.0f, 0.0f)
                finalizeSpawn(
                    level,
                    level.getCurrentDifficultyAt(position),
                    MobSpawnType.MOB_SUMMONED,
                    null,
                    null
                )

            }
            zombie!!.moveTo(position, 0.0f, 0.0f)
            zombie.finalizeSpawn(
                level,
                level.getCurrentDifficultyAt(position),
                MobSpawnType.MOB_SUMMONED,
                null,
                null
            )
//        todo    zombie!!.setOwner(this@Evoker)
            //todo 跟随主人的代码 zombie.boundOrigin = position
            level.addFreshEntityWithPassengers(zombie)
        }
    }


    override fun stop() {
        super.stop()
        onStop()
    }

}