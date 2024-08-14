package com.prohitman.prizemachines.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.prohitman.prizemachines.PrizeMachines;
import com.prohitman.prizemachines.loot.modifiers.LootTableLootModifier;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class GlobalLootModifierRegistry {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> REGISTER = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, PrizeMachines.MODID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<LootTableLootModifier>> LOOT_TABLE = REGISTER.register("loot_table_inject", () -> LootTableLootModifier.CODEC);
}
