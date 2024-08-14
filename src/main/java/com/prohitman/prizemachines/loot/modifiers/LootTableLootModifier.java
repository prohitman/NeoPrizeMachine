package com.prohitman.prizemachines.loot.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class LootTableLootModifier extends LootModifier {
    public static final MapCodec<LootTableLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance ->
            codecStart(instance).and(
                    ResourceLocation.CODEC.fieldOf("loot_table").forGetter(lootTableLootModifier -> lootTableLootModifier.lootTableId)
            ).apply(instance, LootTableLootModifier::new)
    );

    private final ResourceLocation lootTableId;

    public LootTableLootModifier(LootItemCondition[] conditionsIn, ResourceLocation lootTableId) {
        super(conditionsIn);
        this.lootTableId = lootTableId;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootTable extraTable = context.getResolver().get(Registries.LOOT_TABLE, ResourceKey.create(Registries.LOOT_TABLE, lootTableId)).get().value();
        extraTable.getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), generatedLoot::add));
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }

    /*@Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }*/
}