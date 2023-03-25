package org.liangguo.breakingbad.init

import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.entity.EntityRenderers
import net.minecraft.client.renderer.entity.ThrownItemRenderer
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.SpawnPlacements
import net.minecraft.world.item.Items
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent
import net.minecraftforge.event.village.VillagerTradesEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import org.liangguo.breakingbad.R
import org.liangguo.breakingbad.entity.base.HumanoidRenderer
import org.liangguo.breakingbad.entity.mobs.BaseTwinBrother
import org.liangguo.breakingbad.entity.mobs.GusFring
import org.liangguo.breakingbad.entity.mobs.UndeadGusFring
import org.liangguo.breakingbad.entity.mobs.UndeadZombie
import org.liangguo.breakingbad.entity.model.UndeadGusFringModel
import org.liangguo.breakingbad.entity.renderer.UndeadZombieRenderer
import org.liangguo.breakingbad.utils.AN_EMERALD
import org.liangguo.breakingbad.utils.newTrade
import org.liangguo.breakingbad.utils.toStack


/**
 * @author ldh
 * 时间: 2023/1/28 10:27
 * 邮箱: ldh.liangguo@outlook.com
 */
object ModEvents {

    @Mod.EventBusSubscriber(modid = R.MOD_ID)
    object ForgeEvents {

        @JvmStatic
        @SubscribeEvent
        fun spawnPlacement(event: SpawnPlacementRegisterEvent) {
            event.register(
                EntityRegistry.GUS_FRING.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND,
            )
            event.register(
                EntityRegistry.TWIN_BROTHER_RED.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND,
            )
            event.register(
                EntityRegistry.TWIN_BROTHER_BLUE.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Mob::checkMobSpawnRules,
                SpawnPlacementRegisterEvent.Operation.AND,
            )
        }


//        @JvmStatic
//        @SubscribeEvent
//        fun addCustomTrades(event: VillagerTradesEvent) {
//            event.trades.apply {
//                //对于某种类型的村民，多少个a可以换多少个b。EMERALD是绿宝石
//                when (event.type) {
//                    VillagerRegistry.DRUG_DEALER.get() -> {
//                        //对于毒贩
//                        //1级村民的10个大麻叶换1个绿宝石
//                        newTrade(1, ItemRegistry.CANNABIS_LEAF.toStack(10))
//                        newTrade(1, inputItem = AN_EMERALD, outputItem = ItemRegistry.CANNABIS_SEEDS.toStack(6))
//                        //1级 3个绿宝石换1个蓝色冰毒
//                        newTrade(
//                            1,
//                            inputItem = Items.EMERALD.toStack(3),
//                            outputItem = ItemRegistry.BLUE_METHAMPHETAMINE.toStack(1)
//                        )
//                        newTrade(
//                            1,
//                            inputItem = Items.EMERALD.toStack(2),
//                            outputItem = ItemRegistry.METHAMPHETAMINE.toStack(1)
//                        )
//
//                        //2级 一个绿宝石换6个大麻叶
//                        newTrade(2, inputItem = AN_EMERALD, outputItem = ItemRegistry.CANNABIS_LEAF.toStack(6))
//                        newTrade(
//                            2,
//                            inputItem = Items.EMERALD.toStack(3),
//                            outputItem = ItemRegistry.BLUE_METHAMPHETAMINE.toStack(2)
//                        )
//                        newTrade(
//                            2,
//                            inputItem = Items.EMERALD.toStack(1),
//                            outputItem = ItemRegistry.METHAMPHETAMINE.toStack(1)
//                        )
//
//                        newTrade(
//                            3,
//                            inputItem = Items.EMERALD.toStack(1),
//                            outputItem = ItemRegistry.BLUE_METHAMPHETAMINE.toStack(1)
//                        )
//
//                    }
//
//                    VillagerRegistry.GUS_FRING.get() -> {
//                        //炸鸡叔的交易配置
//                        newTrade(1, inputItem = AN_EMERALD, outputItem = ItemRegistry.CHICKEN_NUGGET.toStack(5))
//                        newTrade(
//                            1,
//                            inputItem = Items.EMERALD.toStack(16),
//                            ItemRegistry.BLUE_METHAMPHETAMINE.toStack(24)
//                        )
//                        newTrade(1, inputItem = Items.EMERALD.toStack(16), ItemRegistry.METHAMPHETAMINE.toStack(36))
//                        newTrade(
//                            1,
//                            inputItem = Items.GOLD_INGOT.toStack(64),
//                            ItemRegistry.BLUE_METHAMPHETAMINE.toStack(36)
//                        )
//                        newTrade(1, inputItem = Items.GOLD_INGOT.toStack(64), ItemRegistry.METHAMPHETAMINE.toStack(24))
//
//                    }
//
//                    else -> {
//                        //对于其他的普通村民
////                        newTrade(1, ItemRegistry.CANNABIS_LEAF.toStack(8))
////                        newTrade(2, ItemRegistry.CANNABIS_LEAF.toStack(7))
////                        newTrade(3, ItemRegistry.CANNABIS_LEAF.toStack(6))
////                        newTrade(4, ItemRegistry.CANNABIS_LEAF.toStack(5))
////                        newTrade(5, ItemRegistry.CANNABIS_LEAF.toStack(4))
////
////                        newTrade(1, ItemRegistry.METHAMPHETAMINE.toStack(4))
////                        newTrade(2, ItemRegistry.METHAMPHETAMINE.toStack(3))
////                        newTrade(3, ItemRegistry.METHAMPHETAMINE.toStack(3))
////                        newTrade(4, ItemRegistry.METHAMPHETAMINE.toStack(2))
////                        newTrade(5, ItemRegistry.METHAMPHETAMINE.toStack(2))
////
////                        newTrade(1, ItemRegistry.BLUE_METHAMPHETAMINE.toStack(3))
////                        newTrade(2, ItemRegistry.BLUE_METHAMPHETAMINE.toStack(2))
////                        newTrade(3, ItemRegistry.BLUE_METHAMPHETAMINE.toStack(1))
////                        newTrade(4, ItemRegistry.BLUE_METHAMPHETAMINE.toStack(1))
////                        newTrade(5, ItemRegistry.BLUE_METHAMPHETAMINE.toStack(1))
//                    }
//                }
//            }
//        }

    }

    @Mod.EventBusSubscriber(modid = R.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    object ModEventBusEvents {

        @JvmStatic
        @SubscribeEvent
        fun entityAttributeEvent(event: EntityAttributeCreationEvent) {
            event.put(EntityRegistry.GUS_FRING.get(), GusFring.prepareAttributes())
            event.put(EntityRegistry.UNDEAD_GUS_FRING.get(), UndeadGusFring.prepareAttributes())
            event.put(EntityRegistry.TWIN_BROTHER_BLUE.get(), BaseTwinBrother.prepareAttributes())
            event.put(EntityRegistry.TWIN_BROTHER_RED.get(), BaseTwinBrother.prepareAttributes())
            event.put(EntityRegistry.UNDEAD_ZOMBIE.get(), UndeadZombie.prepareAttributes())
        }

    }

    @Mod.EventBusSubscriber(modid = R.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    object ClientModEvents {


        @JvmStatic
        @SubscribeEvent
        fun onClientSetup(event: FMLClientSetupEvent) {
            EntityRenderers.register(EntityRegistry.GUS_FRING.get()) { HumanoidRenderer(it, GusFring.ENTITY_ID) }
            EntityRenderers.register(EntityRegistry.UNDEAD_GUS_FRING.get()) {
                HumanoidRenderer(
                    it, UndeadGusFring.ENTITY_ID, modelProvider = UndeadGusFringModel(
                        it.bakeLayer(
                            ModelLayers.PLAYER
                        ), false
                    )
                )
            }

            EntityRenderers.register(EntityRegistry.TWIN_BROTHER_BLUE.get()) {
                HumanoidRenderer(
                    it,
                    BaseTwinBrother.ENTITY_ID_BLUE,
                    scale = 1.15f,
                    headScale = 0.8f,
                    shadowRadius = 0.6f
                )
            }

            EntityRenderers.register(EntityRegistry.TWIN_BROTHER_RED.get()) {
                HumanoidRenderer(it, BaseTwinBrother.ENTITY_ID_RED, scale = 1.15f, shadowRadius = 0.6f)
            }

            EntityRenderers.register(EntityRegistry.UNDEAD_ZOMBIE.get()) {
                UndeadZombieRenderer(it)
            }

            EntityRenderers.register(EntityRegistry.MERCURIC_FULMINATE.get()) { ThrownItemRenderer(it) }
        }

    }


}
