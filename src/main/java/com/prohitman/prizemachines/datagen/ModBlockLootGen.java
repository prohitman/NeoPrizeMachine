package com.prohitman.prizemachines.datagen;

import com.prohitman.prizemachines.PrizeMachines;
import com.prohitman.prizemachines.registry.BlockRegistry;
import it.unimi.dsi.fastutil.Function;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootGen extends BlockLootSubProvider {
    protected ModBlockLootGen(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        // TODO: decide whether it needs iron pick
        dropSelf(BlockRegistry.EQUIPMENT_PRIZE_MACHINE.get());
        dropSelf(BlockRegistry.BLOCK_PRIZE_MACHINE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.entrySet().stream()
                .filter(e -> e.getKey().location().getNamespace().equals(PrizeMachines.MODID))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
