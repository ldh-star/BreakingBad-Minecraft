package org.liangguo.breakingbad.loot

import com.google.common.base.Suppliers
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import net.minecraftforge.common.loot.IGlobalLootModifier
import net.minecraftforge.common.loot.LootModifier
import net.minecraftforge.registries.ForgeRegistries
import org.liangguo.breakingbad.utils.toStack
import java.util.function.Supplier

/**
 * @author ldh
 * 时间: 2023/2/1 22:02
 * 邮箱: ldh.liangguo@outlook.com
 */
class AddItemModifier(conditions: Array<LootItemCondition>, private val item: Item) : LootModifier(conditions) {

    override fun codec(): Codec<out IGlobalLootModifier> = CODEC.get()


    override fun doApply(
        generatedLoot: ObjectArrayList<ItemStack>,
        context: LootContext
    ): ObjectArrayList<ItemStack> {
        // 80%概率添加物品
        if (context.random.nextFloat() >= 0.2f) {
            generatedLoot.add(item.toStack(1))
        }

        return generatedLoot
    }


    companion object {
        val CODEC: Supplier<Codec<AddItemModifier>> = Suppliers.memoize {
            RecordCodecBuilder.create { inst ->
                codecStart(inst).and(ForgeRegistries.ITEMS.codec.fieldOf("item").forGetter { it.item })
                    .apply(inst) { conditions, item ->
                        AddItemModifier(conditions, item)
                    }
            }
        }
    }

}