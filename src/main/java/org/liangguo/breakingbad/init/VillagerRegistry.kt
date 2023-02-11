package org.liangguo.breakingbad.init

import com.google.common.collect.ImmutableSet
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.ai.village.poi.PoiType
import net.minecraft.world.entity.npc.VillagerProfession
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.util.ObfuscationReflectionHelper
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import org.liangguo.breakingbad.R
import java.lang.reflect.InvocationTargetException


/**
 * @author ldh
 * 时间: 2023/1/28 9:50
 * 邮箱: ldh.liangguo@outlook.com
 */
object VillagerRegistry {

    val POI_TYPES =
        DeferredRegister.create(ForgeRegistries.POI_TYPES, R.MOD_ID)

    val VILLAGER_PROFESSIONS =
        DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, R.MOD_ID)

    val DRUGS_DEALER_POI = POI_TYPES.register("drugs_dealer_poi") {
        PoiType(ImmutableSet.copyOf(Blocks.GOLD_BLOCK.stateDefinition.possibleStates), 5, 5)
    }

    val GUS_FRING_POI = POI_TYPES.register("gus_fring_poi") {
        PoiType(ImmutableSet.of(), 5, 5)
    }

    /**
     * 第二个参数是工作地点
     * 第三个参数是可获得工作地点
     *
     */
    val DRUG_DEALER = VILLAGER_PROFESSIONS.register("drug_dealer") {
        VillagerProfession(
            "drug_dealer",
            { it.get() == DRUGS_DEALER_POI.get() },
            { it.get() == DRUGS_DEALER_POI.get() },
            ImmutableSet.of(),
            ImmutableSet.of(),
            SoundEvents.VILLAGER_WORK_MASON
        )
    }

    /**
     * 炸鸡叔专属职业
     */
    val GUS_FRING = VILLAGER_PROFESSIONS.register("gus_fring") {
        VillagerProfession(
            "gus_fring",
            { it.get() == GUS_FRING_POI.get() },
            { it.get() == GUS_FRING_POI.get() },
            ImmutableSet.of(),
            ImmutableSet.of(),
            SoundEvents.VILLAGER_WORK_MASON
        )
    }

    fun registerPOIs() {
        try {
            ObfuscationReflectionHelper.findMethod(PoiType::class.java, "registerBlockStates", PoiType::class.java)
                .invoke(null, DRUGS_DEALER_POI.get())
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun IEventBus.registerVillagers() {
        POI_TYPES.register(this)
        VILLAGER_PROFESSIONS.register(this)
    }
}
