package org.liangguo.breakingbad.init

import net.minecraft.ChatFormatting
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.item.RecordItem
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import org.liangguo.breakingbad.R
import org.liangguo.breakingbad.item.ModCreativeModeTab.DRUGS_TAB
import org.liangguo.breakingbad.item.base.EatableDrugItemTooltipItem
import org.liangguo.breakingbad.item.custom.*
import org.liangguo.breakingbad.utils.literals

/**
 * @author ldh
 * 时间: 2023/1/27 15:49
 * 邮箱: ldh.liangguo@outlook.com
 */
object ItemRegistry {

    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, R.MOD_ID)

    val CANNABIS_SEEDS = registerItem("cannabis_seeds") {
        ItemNameBlockItem(BlockRegistry.CANNABIS_CROP.get(), it)
    }

    val CANNABIS_LEAF = registerItem<Item>("cannabis_leaf")

    val CHICKEN_NUGGET = registerItem<Item>(
        "chicken_nugget",
        properties = Item.Properties().tab(DRUGS_TAB).food(
            FoodProperties.Builder()
                //营养，即恢复的饥饿值
                .nutrition(6)
                //饱和度倍数，该参数乘以上面那个营养参数就是保食度
                .saturationMod(1f)
                .build()
        )
    )

    val METHAMPHETAMINE = registerItem(
        "methamphetamine", properties = commonItemProperties().food(
            FoodProperties.Builder()
                .alwaysEat()
                .effect({ MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2 * 60 * 20) }, 0.9f)
                .effect({ MobEffectInstance(MobEffects.JUMP, 90 * 20) }, 0.9f)
                .effect({ MobEffectInstance(MobEffects.CONFUSION, 15 * 20) }, 1f)
                .effect({ MobEffectInstance(MobEffects.DIG_SPEED, 2 * 60 * 20) }, 0.9f)
                .effect({ MobEffectInstance(MobEffects.DAMAGE_BOOST, 2 * 60 * 20) }, 0.9f)
                .effect({ MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2 * 60 * 20) }, 0.9f)
                .effect({ MobEffectInstance(EffectRegistry.ADDICITION_TIMER.get(), 30 * 60 * 20) }, 1f)
                .build()
        )
    ) {
        EatableDrugItemTooltipItem(
            it,
            literals("C₁₀H₁₅N", color = ChatFormatting.YELLOW),
            literals("甲基苯丙胺或甲基安非他命（英语：methamphetamine，全名N-methylamphetamine），化学式：C₆H₅CH₂CH(CH₃)NHCH₃（N-甲基-1-苯基丙-2-胺、N-甲基-α-甲基苯乙胺），其结晶形态俗称冰毒、黑话“猪肉”，是一种强效中枢神经系统兴奋剂，主要被用于毒品，较少被用于治疗注意力不足过动症和肥胖症，即便有也被当成第二线疗法。"),
            soundEvent = SoundEventRegistry.TUCO.get(),
            damage = 0.8f,
        )
    }

    val BLUE_METHAMPHETAMINE = registerItem(
        "methamphetamine_blue", properties = commonItemProperties().food(
            FoodProperties.Builder()
                .alwaysEat()
                .effect({ MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3 * 60 * 20) }, 1f)
                .effect({ MobEffectInstance(MobEffects.JUMP, 2 * 60 * 20) }, 1f)
                .effect({ MobEffectInstance(MobEffects.CONFUSION, 20 * 20) }, 1f)
                .effect({ MobEffectInstance(MobEffects.DIG_SPEED, 3 * 60 * 20) }, 1f)
                .effect({ MobEffectInstance(MobEffects.DAMAGE_BOOST, 3 * 60 * 20) }, 1f)
                .effect({ MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3 * 60 * 20) }, 1f)
                .effect({ MobEffectInstance(EffectRegistry.ADDICITION_TIMER.get(), 30 * 60 * 20) }, 1f)
                .build()
        )
    ) {
        EatableDrugItemTooltipItem(
            it,
            literals("C₁₀H₁₅N", color = ChatFormatting.YELLOW),
            literals("在绝命毒师电视剧中，老白为了制作更多更高纯度的冰毒，自己发明了一种蓝色的冰毒。这种冰毒有着更高的纯度，深受市场的欢迎。"),
            soundEvent = SoundEventRegistry.TUCO.get(),
            damage = 1f,
        )
    }

    val GUS_FRING_SPAWN_EGG = registerItem("gus_fring_spawn_egg") {
        GusFringSpawnEgg(it)
    }

    val UNDEAD_GUS_FRING_SPAWN_EGG = registerItem("undead_gus_fring_spawn_egg") {
        UndeadGusFringSpawnEgg(it)
    }

    val TWIN_BROTHER_RED_SPAWN_EGG = registerItem("twin_brother_red_spawn_egg") {
        TwinBrotherRedSpawnEgg(it)
    }

    val TWIN_BROTHER_BLUE_SPAWN_EGG = registerItem("twin_brother_blue_spawn_egg") {
        TwinBrotherBlueSpawnEgg(it)
    }

    val UNDEAD_ZOMBIE_SPAWN_EGG = registerItem("undead_zombie_spawn_egg") {
        UndeadZombieSpawnEgg(it)
    }


    val MERCURIC_FULMINATE_ITEM = registerItem("mercuric_fulminate_item") {
        MercuricFulminateItem(it.stacksTo(16))
    }

    val LOGO = registerItem<Item>("logo", properties = Item.Properties())

    val BREAKING_BAD_THEME_MUSIC_DISC = registerItem("breaking_bad_theme_music_disc") {
        RecordItem(4, SoundEventRegistry.BREAKING_BAD_THEME, it.stacksTo(1), 100)
    }

    /**
     * 客户端启动的时候调用此函数
     */
    fun IEventBus.registerItems() {
        ITEMS.register(this)
    }

    /**
     * 注册新的物品
     *
     * @param tab 创造模式标签栏，默认在本模组的标签栏下
     * @param properties Item的属性，这是Item的构造函数的必要参数
     * @param item 构造[Item]的函数，默认由[properties]构造普通的Item，如果你需要注册非[Item]类的（[Item]下的子类），请一定要覆盖此参数
     * @return 注册好的item
     */
    inline fun <reified T : Item> registerItem(
        id: String,
        tab: CreativeModeTab = DRUGS_TAB,
        properties: Item.Properties = Item.Properties().tab(tab),
        crossinline item: (properties: Item.Properties) -> T = { Item(it) as T }
    ): RegistryObject<T> =
        ITEMS.register(id) { item(properties) }


    private fun commonItemProperties(tab: CreativeModeTab = DRUGS_TAB): Item.Properties {
        return Item.Properties().tab(tab)
    }

}