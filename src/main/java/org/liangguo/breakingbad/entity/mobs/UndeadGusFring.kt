package org.liangguo.breakingbad.entity.mobs

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.animal.IronGolem
import net.minecraft.world.entity.animal.SnowGolem
import net.minecraft.world.entity.monster.ZombifiedPiglin
import net.minecraft.world.entity.npc.AbstractVillager
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.pathfinder.BlockPathTypes
import org.liangguo.breakingbad.R
import org.liangguo.breakingbad.entity.ai.undeadgus.UndeadGusSpell
import org.liangguo.breakingbad.entity.ai.undeadgus.UndeadGusSpell.Companion.id
import org.liangguo.breakingbad.entity.ai.undeadgus.UndeadGusSummonSpellGoal
import org.liangguo.breakingbad.entity.base.HumanoidSpellCasterEntity
import org.liangguo.breakingbad.utils.toStack
import kotlin.random.Random


/**
 * @author ldh
 * 时间: 2023/2/1 13:52
 * 邮箱: ldh.liangguo@outlook.com
 */
class UndeadGusFring(type: EntityType<UndeadGusFring>, level: Level) :
    HumanoidSpellCasterEntity<UndeadGusSpell>(type, level) {

    override val cloakTexture = ResourceLocation(R.MOD_ID, "textures/entity/cape/undead_gus_fring_cape.png")

    var weapon = ItemStack(Items.DIAMOND_SWORD)


    override val spellColor = arrayOf(0.8, 0.5, 0.4)

    override val isSpellCasting: Boolean
        get() = if (level.isClientSide) entityData.get(DATA_SPELL_CASTING_ID).toInt() > 0 else spellTicks > 0


    override var currentSpell: UndeadGusSpell = UndeadGusSpell.None
        get() = if (level.isClientSide) UndeadGusSpell.getById(entityData.get(DATA_SPELL_CASTING_ID)) else field
        set(value) {
            field = value
            entityData.set(DATA_SPELL_CASTING_ID, currentSpell.id)
        }

    init {
        setCanPickUpLoot(true)
        setPathfindingMalus(BlockPathTypes.LAVA, 0.0f)
        setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0f)
        setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0f)
    }


    override fun defineSynchedData() {
        super.defineSynchedData()
        entityData.define(DATA_SPELL_CASTING_ID, UndeadGusSpell.None.id)
    }

    override fun registerGoals() {
        super.registerGoals()
        goalSelector.apply {
            this@UndeadGusFring.apply {
                //浮在水面上
                addGoal(0, FloatGoal(this))
                //近战攻击，看不见目标就不会追踪
                addGoal(2, MeleeAttackGoal(this, 1.2, false))
                addGoal(3, UndeadGusSummonSpellGoal(this) {
                    currentSpell = UndeadGusSpell.None
                })
                //随机漫步
                addGoal(4, RandomStrollGoal(this, 0.75))
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
            this@UndeadGusFring.apply {
                //攻击玩家
                addGoal(1, NearestAttackableTargetGoal(this, Player::class.java, true))
                //谁打他他就打谁
                addGoal(2, HurtByTargetGoal(this).setAlertOthers())
                //攻击最近的铁傀儡
                addGoal(3, NearestAttackableTargetGoal(this, IronGolem::class.java, true))
                //攻击村民
                addGoal(2, NearestAttackableTargetGoal(this, AbstractVillager::class.java, true))
                //挨打后还会通知周围的僵尸猪人来打你
                addGoal(5, HurtByTargetGoal(this).setAlertOthers(ZombifiedPiglin::class.java))
                //攻击最近的雪傀儡
                addGoal(6, NearestAttackableTargetGoal(this, SnowGolem::class.java, true))
            }
        }
    }

    override fun fireImmune() = true

    override fun ignoreExplosion() = true

    override fun customServerAiStep() {
        super.customServerAiStep()
        if (!level.isClientSide) {
            setItemInHand(InteractionHand.MAIN_HAND, if (isSpellCasting) ItemStack.EMPTY else weapon)
            if (health < maxHealth && Random.nextFloat() < 0.003 && !isSpellCasting) {
                val stack = Items.ROTTEN_FLESH.toStack(1)
                setItemInHand(InteractionHand.OFF_HAND, stack)
                startUsingItem(InteractionHand.OFF_HAND)
            }
        }
    }


    override fun eat(level: Level, itemStack: ItemStack): ItemStack {
        if (itemStack.item == Items.ROTTEN_FLESH) {
            heal(5f)
            return itemStack.apply { shrink(1) }
        }
        return super.eat(level, itemStack)
    }

    override fun addAdditionalSaveData(compoundTag: CompoundTag) {
        super.addAdditionalSaveData(compoundTag)
        val itemListTag = ListTag()
        val itemCompoundTag = CompoundTag()
        weapon.save(itemCompoundTag)
        itemListTag.add(itemCompoundTag)
        compoundTag.put(SAVE_DATA_KEY_ITEMS, itemListTag)
    }

    override fun readAdditionalSaveData(compoundTag: CompoundTag) {
        super.readAdditionalSaveData(compoundTag)
        if (compoundTag.contains(SAVE_DATA_KEY_ITEMS, 9)) {
            val itemList: ListTag = compoundTag.getList(SAVE_DATA_KEY_ITEMS, 10)
            weapon = ItemStack.of(itemList.getCompound(0))
        }
    }


    companion object {

        const val ENTITY_ID = "undead_gus_fring"

        @JvmStatic
        private val DATA_SPELL_CASTING_ID =
            SynchedEntityData.defineId(UndeadGusFring::class.java, EntityDataSerializers.BYTE)


        private const val SAVE_DATA_KEY_ITEMS = "save_data_key_items"


        fun prepareAttributes(): AttributeSupplier = createMobAttributes()
            .add(Attributes.FOLLOW_RANGE, 35.0)
            .add(Attributes.MAX_HEALTH, 40.0)
            .add(Attributes.ATTACK_DAMAGE, 10.0)
            .add(Attributes.ATTACK_SPEED, 0.9)
            .add(Attributes.MOVEMENT_SPEED, 0.35)
            .add(Attributes.ARMOR, 10.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.4)
            .add(Attributes.ARMOR_TOUGHNESS, 23.0)
            .build()


    }

}