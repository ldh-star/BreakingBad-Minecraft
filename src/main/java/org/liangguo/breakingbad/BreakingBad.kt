package org.liangguo.breakingbad

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.liangguo.breakingbad.init.BlockRegistry.registerBlocks
import org.liangguo.breakingbad.init.EffectRegistry.registerEffects
import org.liangguo.breakingbad.init.EntityRegistry.registerEntityTypes
import org.liangguo.breakingbad.init.ItemRegistry.registerItems
import org.liangguo.breakingbad.init.PaintingRegistry.registerPaintings
import org.liangguo.breakingbad.init.SoundEventRegistry.registerSounds
import org.liangguo.breakingbad.init.VillagerRegistry
import org.liangguo.breakingbad.init.VillagerRegistry.registerVillagers
import org.liangguo.breakingbad.loot.ModLootModifiers.registerLootModifiers


@Mod(R.MOD_ID)
class BreakingBad {
    init {
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus
        // Cannot inline bytecode built with JVM target 17 into bytecode that is being built with JVM target 1.8. Please specify proper '-jvm-target' option
//        val modEventBus = thedarkcolour.kotlinforforge.forge.MOD_CONTEXT.getKEventBus()
        with(modEventBus) {
            registerItems()
            registerSounds()
            registerBlocks()
            registerEffects()
            registerVillagers()
            registerPaintings()
            registerEntityTypes()
            registerLootModifiers()
        }
//        GeckoLib.initialize()
        modEventBus.addListener { event: FMLCommonSetupEvent -> commonSetup(event) }
        MinecraftForge.EVENT_BUS.register(this)
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        event.enqueueWork {
            VillagerRegistry.registerPOIs()
        }
    }


}