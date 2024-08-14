package com.prohitman.prizemachines.datagen;

import com.prohitman.prizemachines.PrizeMachines;
import com.prohitman.prizemachines.blocks.PrizeMachineBlock;
import com.prohitman.prizemachines.registry.BlockRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateGen extends BlockStateProvider {
    public ModBlockStateGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PrizeMachines.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        prizeMachine(BlockRegistry.EQUIPMENT_PRIZE_MACHINE.get());
        prizeMachine(BlockRegistry.BLOCK_PRIZE_MACHINE.get());
    }

    protected void prizeMachine(Block block) {
        ResourceLocation location = BuiltInRegistries.BLOCK.getKey(block).withPrefix(ModelProvider.BLOCK_FOLDER + "/");
        getVariantBuilder(block)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(location.withSuffix("_" + state.getValue(PrizeMachineBlock.DOUBLE_BLOCK_HALF).getSerializedName())))
                        .rotationY((int) state.getValue(PrizeMachineBlock.FACING).toYRot())
                        .build()
                );
    }
}
