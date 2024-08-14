package com.prohitman.prizemachines.datagen;

import com.prohitman.prizemachines.PrizeMachines;
import com.prohitman.prizemachines.registry.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGen extends BlockTagsProvider {
    public ModBlockTagGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PrizeMachines.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockRegistry.PRIZE_MACHINES)
                .add(BlockRegistry.EQUIPMENT_PRIZE_MACHINE.get(), BlockRegistry.BLOCK_PRIZE_MACHINE.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockRegistry.PRIZE_MACHINES);
        tag(BlockTags.NEEDS_STONE_TOOL)
                .addTag(BlockRegistry.PRIZE_MACHINES);
        tag(BlockTags.FEATURES_CANNOT_REPLACE)
                .addTag(BlockRegistry.PRIZE_MACHINES);
    }
}