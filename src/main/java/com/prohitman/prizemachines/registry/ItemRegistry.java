package com.prohitman.prizemachines.registry;

import com.prohitman.prizemachines.PrizeMachines;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(PrizeMachines.MODID);

    public static final TagKey<Item> PRIZE_MACHINES = REGISTER.createTagKey("prize_machines");
    public static final TagKey<Item> TOKENS = REGISTER.createTagKey("tokens");
    public static final TagKey<Item> PRIZE_BLACKLIST = REGISTER.createTagKey("prize_blacklist");
    public static final DeferredItem<Item> PRIZE_TOKEN = REGISTER.register("prize_token", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> EQUIPMENT_PRIZE_MACHINE = REGISTER.register("equipment_prize_machine", () -> new BlockItem(BlockRegistry.EQUIPMENT_PRIZE_MACHINE.get(), new Item.Properties()));
    public static final DeferredItem<Item> BLOCK_PRIZE_MACHINE = REGISTER.register("block_prize_machine", () -> new BlockItem(BlockRegistry.BLOCK_PRIZE_MACHINE.get(), new Item.Properties()));
}
