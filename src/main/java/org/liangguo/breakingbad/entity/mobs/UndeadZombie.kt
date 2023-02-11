package org.liangguo.breakingbad.entity.mobs

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.DifficultyInstance
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.SpawnGroupData
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.animal.IronGolem
import net.minecraft.world.entity.animal.SnowGolem
import net.minecraft.world.entity.monster.Zombie
import net.minecraft.world.entity.monster.ZombifiedPiglin
import net.minecraft.world.entity.npc.AbstractVillager
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.ServerLevelAccessor


/**
 * @author ldh
 * 时间: 2023/2/6 20:26
 * 邮箱: ldh.liangguo@outlook.com
 */
class UndeadZombie(type: EntityType<UndeadZombie>, level: Level) : Zombie(type, level) {

    override fun registerGoals() {
        goalSelector.addGoal(8, LookAtPlayerGoal(this, Player::class.java, 8.0f))
        goalSelector.addGoal(8, RandomLookAroundGoal(this))
        goalSelector.addGoal(2, ZombieAttackGoal(this, 1.0, false))
        goalSelector.addGoal(7, WaterAvoidingRandomStrollGoal(this, 1.0))

        targetSelector.registerTarget()
    }


    private fun GoalSelector.registerTarget() {
        this@UndeadZombie.apply {
            addGoal(1, NearestAttackableTargetGoal(this, Player::class.java, true))
            //谁打他他就打谁
            addGoal(2, HurtByTargetGoal(this).setAlertOthers())
            //攻击最近的铁傀儡
            addGoal(3, NearestAttackableTargetGoal(this, IronGolem::class.java, true))
            //攻击村民
            addGoal(2, NearestAttackableTargetGoal(this, AbstractVillager::class.java, true))
            addGoal(5, HurtByTargetGoal(this).setAlertOthers())
            addGoal(5, HurtByTargetGoal(this).setAlertOthers(ZombifiedPiglin::class.java))
            //攻击最近的雪傀儡
            addGoal(6, NearestAttackableTargetGoal(this, SnowGolem::class.java, true))

        }
    }

    override fun ignoreExplosion() = true

    override fun isSunBurnTick(): Boolean {
        return false
    }

    override fun finalizeSpawn(
        serverLevelAccessor: ServerLevelAccessor,
        difficultyInstance: DifficultyInstance,
        mobSpawnType: MobSpawnType,
        spawnGroupData: SpawnGroupData?,
        compoundTag: CompoundTag?
    ): SpawnGroupData? {
        val result =
            super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag)
        setCanPickUpLoot(true)
        return result
    }

    companion object {

        const val ENTITY_ID = "undead_zombie"

        fun prepareAttributes(): AttributeSupplier = createAttributes()
            .add(Attributes.MAX_HEALTH, 25.0)
            .add(Attributes.ATTACK_DAMAGE, 5.0)
            .add(Attributes.MOVEMENT_SPEED, 0.26)
            .add(Attributes.ARMOR, 4.0)
            .build()


    }

}