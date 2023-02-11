package org.liangguo.breakingbad.entity.mobs

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.DifficultyInstance
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation
import net.minecraft.world.entity.animal.IronGolem
import net.minecraft.world.entity.animal.SnowGolem
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.level.Explosion.BlockInteraction
import net.minecraft.world.level.Level
import net.minecraft.world.level.ServerLevelAccessor
import org.liangguo.breakingbad.entity.base.HumanoidTrader
import org.liangguo.breakingbad.init.EntityRegistry
import org.liangguo.breakingbad.init.ItemRegistry
import org.liangguo.breakingbad.init.VillagerRegistry
import org.liangguo.breakingbad.utils.convertTo
import org.liangguo.breakingbad.utils.toStack
import org.liangguo.breakingbad.utils.tryAs
import kotlin.random.Random

/**
 * @author ldh
 * 时间: 2023/1/28 16:02
 * 邮箱: ldh.liangguo@outlook.com
 */
class GusFring(type: EntityType<GusFring>, level: Level) :
    HumanoidTrader(type, level, profession = VillagerRegistry.GUS_FRING.get(), tradeItemCount = 5) {

    //todo 伤害以后还得改
    //todo 如何与炸鸡叔进行更好的交易

    private var weapon = ItemStack(Items.DIAMOND_SWORD)

    override fun registerGoals() {
        super.registerGoals()
        goalSelector.apply {
            this@GusFring.apply {

                //浮在水面上
                addGoal(0, FloatGoal(this))
                addGoal(1, OpenDoorGoal(this, true))
                //近战攻击，看不见目标就不会追踪
                addGoal(2, MeleeAttackGoal(this, 1.2, false))
                //蓝色冰毒对炸鸡叔有很大的诱惑
                addGoal(3, TemptGoal(this, 1.1, Ingredient.of(ItemRegistry.BLUE_METHAMPHETAMINE.get()), false))
                addGoal(4, TemptGoal(this, 1.1, Ingredient.of(ItemRegistry.METHAMPHETAMINE.get()), false))
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
        //添加攻击目标的属性
        targetSelector.apply {
            this@GusFring.apply {
                //谁打他他就打谁
                addGoal(1, HurtByTargetGoal(this).setAlertOthers())
                //攻击最近的铁傀儡
                addGoal(3, NearestAttackableTargetGoal(this, IronGolem::class.java, true))
                //攻击最近的雪傀儡
                addGoal(5, NearestAttackableTargetGoal(this, SnowGolem::class.java, true))
                //测试用，炸鸡叔不应该攻击村民
//                addGoal(5, NearestAttackableTargetGoal(this, AbstractVillager::class.java, true))
            }
        }
    }

    init {
        setCanPickUpLoot(true)
        //设置可以开门
        navigation.tryAs<GroundPathNavigation> {
            it.setCanOpenDoors(true)
        }
    }

    override fun finalizeSpawn(
        serverLevelAccessor: ServerLevelAccessor,
        difficultyInstance: DifficultyInstance,
        mobSpawnType: MobSpawnType,
        spawnGroupData: SpawnGroupData?,
        compoundTag: CompoundTag?
    ): SpawnGroupData? {
        //生成的时候随机加上装备
        populateDefaultEquipmentSlots(serverLevelAccessor.random, difficultyInstance)
        //对炸鸡叔的钻石剑随机附魔，有概率附魔
        if (Random.nextFloat() < 0.3f) {
            EnchantmentHelper.enchantItem(
                serverLevelAccessor.random,
                weapon,
                (5.0f + difficultyInstance.specialMultiplier * serverLevelAccessor.random.nextInt(18)
                    .toFloat()).toInt(),
                false
            )
        }
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag)
    }


    override fun customServerAiStep() {
        super.customServerAiStep()
        if (!level.isClientSide) {
            //在攻击的时候使用主手的物品，即默认给主手的武器
            if (target != null && target!!.isAttackable) {
                //当炸鸡叔在攻击的时候才会拿出武，默认是钻石剑
                setItemSlot(EquipmentSlot.MAINHAND, weapon)
            } else {
                setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY)
            }

            if (health < maxHealth && Random.nextFloat() < 0.003) {
                val stack = ItemRegistry.CHICKEN_NUGGET.toStack(1)
                setItemInHand(InteractionHand.OFF_HAND, stack)
                startUsingItem(InteractionHand.OFF_HAND)
            }
        }
    }


    override fun mobInteract(player: Player, hand: InteractionHand): InteractionResult {
        return if (isAlive && target == null) {
            if (!offers.isEmpty() && !isClientSide && hand == InteractionHand.MAIN_HAND) {
                startTrading(player)
            }
            InteractionResult.sidedSuccess(level.isClientSide)
        } else {
            super.mobInteract(player, hand)
        }
    }

    override fun thunderHit(level: ServerLevel, lightningBolt: LightningBolt) {
        //被闪电击中后变成亡灵古斯
        convert()
    }

    override fun die(source: DamageSource) {
        //古斯在死亡的时候会发生爆炸，新生成一个亡灵古斯
        if (!level.isClientSide) {
            level.explode(this, x, y, z, EXPLOSION_RADIUS, BlockInteraction.NONE)
            convert()
        }
    }

    /**
     * 古斯变身成亡灵古斯
     */
    private fun convert() {
        convertTo(EntityRegistry.UNDEAD_GUS_FRING.get(), level, saveItemInHand = true) {
            //转换后的亡灵古斯应该手持原型的钻石剑
            it.weapon = weapon
        }
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

        const val ENTITY_ID = "gus_fring"

        private const val SAVE_DATA_KEY_ITEMS = "save_data_key_items"

        /**
         * 炸鸡叔发生爆炸的半径
         */
        private const val EXPLOSION_RADIUS = 3.0f

        /**
         * 炸鸡叔的基础伤害
         */
        private val BASE_DAMAGE = 3.0

        fun prepareAttributes(): AttributeSupplier = createMobAttributes()
            .add(Attributes.FOLLOW_RANGE, 35.0)
            .add(Attributes.MAX_HEALTH, 25.0)
            .add(Attributes.ARMOR, 6.0)
            .add(Attributes.ATTACK_DAMAGE, BASE_DAMAGE)
            .add(Attributes.ATTACK_SPEED, 0.8)
            .add(Attributes.MOVEMENT_SPEED, 0.35)
            .build()

    }


}
