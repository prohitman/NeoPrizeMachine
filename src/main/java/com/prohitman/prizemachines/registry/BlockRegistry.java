package com.prohitman.prizemachines.registry;

import com.prohitman.prizemachines.PrizeMachines;
import com.prohitman.prizemachines.blocks.PrizeMachineBlock;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class BlockRegistry {
    public static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(PrizeMachines.MODID);

    public static final TagKey<Block> PRIZE_MACHINES = REGISTER.createTagKey("prize_machines");
    public static final DeferredBlock<Block> EQUIPMENT_PRIZE_MACHINE = REGISTER.register("equipment_prize_machine", () -> new PrizeMachineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
    public static final DeferredBlock<Block> BLOCK_PRIZE_MACHINE = REGISTER.register("block_prize_machine", () -> new PrizeMachineBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)));
}
