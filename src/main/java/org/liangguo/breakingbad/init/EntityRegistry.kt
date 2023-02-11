package org.liangguo.breakingbad.init

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.level.Level
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import org.liangguo.breakingbad.R
import org.liangguo.breakingbad.entity.mobs.BaseTwinBrother
import org.liangguo.breakingbad.entity.mobs.GusFring
import org.liangguo.breakingbad.entity.mobs.UndeadGusFring
import org.liangguo.breakingbad.entity.mobs.UndeadZombie
import org.liangguo.breakingbad.entity.projectile.MercuricFulminate
import org.liangguo.breakingbad.utils.AppUtils.resourceLocation


/**
 * @author ldh
 * 时间: 2023/1/28 16:21
 * 邮箱: ldh.liangguo@outlook.com
 */
object EntityRegistry {
    val ENTITY_TYPES: DeferredRegister<EntityType<out Entity>> =
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, R.MOD_ID)

    val GUS_FRING = ENTITY_TYPES.register("gus_fring") {
        EntityType.Builder.of({ type: EntityType<GusFring>, level: Level ->
            GusFring(type, level)
        }, MobCategory.MONSTER).sized(0.6f, 1.85f).build(resourceLocation("gus_fring").toString())
    }

    val UNDEAD_GUS_FRING = ENTITY_TYPES.register(UndeadGusFring.ENTITY_ID) {
        EntityType.Builder.of({ type: EntityType<UndeadGusFring>, level: Level ->
            UndeadGusFring(type, level)
        }, MobCategory.MONSTER).sized(0.6f, 1.85f).build(resourceLocation(UndeadGusFring.ENTITY_ID).toString())
    }

    val TWIN_BROTHER_BLUE = ENTITY_TYPES.register(BaseTwinBrother.ENTITY_ID_BLUE) {
        EntityType.Builder.of({ type: EntityType<BaseTwinBrother>, level: Level ->
            BaseTwinBrother(type, level, true)
        }, MobCategory.MONSTER).sized(0.9f, 2.0f).build(resourceLocation(BaseTwinBrother.ENTITY_ID_BLUE).toString())
    }

    val TWIN_BROTHER_RED = ENTITY_TYPES.register(BaseTwinBrother.ENTITY_ID_RED) {
        EntityType.Builder.of({ type: EntityType<BaseTwinBrother>, level: Level ->
            BaseTwinBrother(type, level, false)
        }, MobCategory.MONSTER).sized(0.9f, 2.0f).build(resourceLocation(BaseTwinBrother.ENTITY_ID_RED).toString())
    }

    val MERCURIC_FULMINATE = ENTITY_TYPES.register(MercuricFulminate.ENTITY_ID) {
        EntityType.Builder.of({ type: EntityType<MercuricFulminate>, level: Level ->
            MercuricFulminate(type, level)
        }, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10)
            .build(resourceLocation(MercuricFulminate.ENTITY_ID).toString())
    }

    val UNDEAD_ZOMBIE = ENTITY_TYPES.register(UndeadZombie.ENTITY_ID) {
        EntityType.Builder.of({ type: EntityType<UndeadZombie>, level: Level ->
            UndeadZombie(type, level)
        }, MobCategory.MONSTER).sized(0.6f, 1.95f).clientTrackingRange(10)
            .build(resourceLocation(UndeadZombie.ENTITY_ID).toString())
    }

    fun IEventBus.registerEntityTypes() {
        ENTITY_TYPES.register(this)
    }

}