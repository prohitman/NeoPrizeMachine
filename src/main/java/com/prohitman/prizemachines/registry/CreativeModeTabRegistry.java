package com.prohitman.prizemachines.registry;

import com.prohitman.prizemachines.PrizeMachines;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeModeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PrizeMachines.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_TAB = REGISTER.register("prize_machines", CreativeModeTabRegistry::createModTab);


    private static CreativeModeTab createModTab() {
        return CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.prizemachines.prize_machines"))
                .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                .icon(() -> ItemRegistry.BLOCK_PRIZE_MACHINE.get().getDefaultInstance())
                .displayItems((pParameters, pOutput) -> {
                    pOutput.accept(ItemRegistry.EQUIPMENT_PRIZE_MACHINE.get());
                    pOutput.accept(ItemRegistry.BLOCK_PRIZE_MACHINE.get());
                    pOutput.accept(ItemRegistry.PRIZE_TOKEN.get());
                }).build();
    }
}
