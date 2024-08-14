package com.prohitman.prizemachines.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class AbstractLootTableSubProvider implements LootTableSubProvider {

    protected final String prefix;
    protected final Map<ResourceKey<LootTable>, LootTable.Builder> map = new HashMap<>();
    protected final HolderLookup.Provider registries;

    public AbstractLootTableSubProvider(String prefix, HolderLookup.Provider registries) {
        this.prefix = prefix;
        this.registries = registries;
    }

    protected abstract void generate();

    @Override
    public void generate(BiConsumer pOutput) {
        generate();
        map.forEach(pOutput);
    }

    protected void add(ResourceLocation tableId, LootTable.Builder builder) {
        map.put(ResourceKey.create(Registries.LOOT_TABLE, tableId.withPrefix(prefix + "/")) , builder);
    }
}
