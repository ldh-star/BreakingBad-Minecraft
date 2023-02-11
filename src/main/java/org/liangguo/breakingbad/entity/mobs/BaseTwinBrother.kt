package org.liangguo.breakingbad.entity.mobs

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.DifficultyInstance
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.animal.IronGolem
import net.minecraft.world.entity.animal.SnowGolem
import net.minecraft.world.entity.monster.Zombie
import net.minecraft.world.entity.monster.ZombieVillager
import net.minecraft.world.entity.monster.ZombifiedPiglin
import net.minecraft.world.entity.npc.AbstractVillager
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.ServerLevelAccessor
import org.liangguo.breakingbad.entity.base.HumanoidEntity
import org.liangguo.breakingbad.utils.toStack


/**
 * @author ldh
 * 时间: 2023/2/4 16:56
 * 邮箱: ldh.liangguo@outlook.com
 *
 * 绝命毒师里双胞胎兄弟
 */
open class BaseTwinBrother(type: EntityType<BaseTwinBrother>, level: Level, private val isBlue: Boolean) :
    HumanoidEntity(type, level) {

    override fun getMainArm() = if (isBlue) HumanoidArm.LEFT else HumanoidArm.RIGHT


    //todo 兄弟俩走一起可能得参考一下Evoker和Vex的代码

    init {
        xpReward = XP_REWARD_LARGE
    }

    override fun registerGoals() {
        goalSelector.apply {
            this@BaseTwinBrother.apply {
                //浮在水面上
                addGoal(0, FloatGoal(this))
                //近战攻击，看不见目标就不会追踪
                addGoal(2, MeleeAttackGoal(this, 1.2, false))
                //随机漫步
                addGoal(5, RandomStrollGoal(this, 0.75))
                //望向玩家
                addGoal(5, LookAtPlayerGoal(this, Player::class.java, 6.0F, 1.0F))
                //看向其他生物
                addGoal(6, LookAtPlayerGoal(this, Mob::class.java, 8.0F));
                //随便到处看
                addGoal(8, RandomLookAroundGoal(this))
                //绕着水塘随处走
                addGoal(7, WaterAvoidingRandomStrollGoal(this, 1.0))
            }
        }
        targetSelector.apply {
            this@BaseTwinBrother.apply {
                //谁打他他就打谁
                addGoal(1, HurtByTargetGoal(this).setAlertOthers())
                //攻击最近的铁傀儡
                addGoal(3, NearestAttackableTargetGoal(this, IronGolem::class.java, true))
                //攻击最近的雪傀儡
                addGoal(5, NearestAttackableTargetGoal(this, SnowGolem::class.java, true))

                addGoal(5, NearestAttackableTargetGoal(this, AbstractVillager::class.java, true))
                addGoal(5, NearestAttackableTargetGoal(this, Zombie::class.java, true))
                addGoal(5, NearestAttackableTargetGoal(this, ZombieVillager::class.java, true))
                addGoal(5, NearestAttackableTargetGoal(this, ZombifiedPiglin::class.java, true))

            }
        }
    }

    override fun finalizeSpawn(
        serverLevelAccessor: ServerLevelAccessor,
        difficultyInstance: DifficultyInstance,
        mobSpawnType: MobSpawnType,
        spawnGroupData: SpawnGroupData?,
        compoundTag: CompoundTag?
    ): SpawnGroupData? {
        //生成的时候默认手持一把铁斧
        setItemInHand(InteractionHand.MAIN_HAND, Items.IRON_AXE.toStack())
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag)
    }

    companion object {
        fun prepareAttributes(): AttributeSupplier = createMobAttributes()
            .add(Attributes.FOLLOW_RANGE, 35.0)
            .add(Attributes.MAX_HEALTH, 30.0)
            .add(Attributes.ARMOR, 8.0)
            .add(Attributes.ATTACK_DAMAGE, 7.5)
            .add(Attributes.ATTACK_SPEED, 0.8)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.37)
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .build()

        const val ENTITY_ID_BLUE = "twin_brother_blue"

        const val ENTITY_ID_RED = "twin_brother_red"


    }

}