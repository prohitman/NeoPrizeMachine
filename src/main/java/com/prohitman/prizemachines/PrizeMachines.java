package com.prohitman.prizemachines;

import com.mojang.datafixers.util.Pair;
import com.prohitman.prizemachines.mixin.StructureTemplatePoolAccessor;
import com.prohitman.prizemachines.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

import java.util.ArrayList;
import java.util.List;

@Mod(PrizeMachines.MODID)
public class PrizeMachines {
    public static final String MODID = "prizemachines";
    public static final ResourceLocation MOD_RL = ResourceLocation.fromNamespaceAndPath(MODID, MODID);

    public PrizeMachines(IEventBus modEventBus, ModContainer modContainer) {
        IEventBus forgeBus = NeoForge.EVENT_BUS;

        BlockRegistry.REGISTER.register(modEventBus);
        ItemRegistry.REGISTER.register(modEventBus);
        SoundEventRegistry.REGISTER.register(modEventBus);
        CreativeModeTabRegistry.REGISTER.register(modEventBus);
        LootItemFunctionRegistry.REGISTER.register(modEventBus);
        GlobalLootModifierRegistry.REGISTER.register(modEventBus);

        forgeBus.addListener(this::addStructures);

        modContainer.registerConfig(ModConfig.Type.SERVER, Config.buildServerSpec());
    }

    public static final ResourceLocation BLOCK_PRIZE_MACHINE_STRUCTURE = PrizeMachines.MOD_RL.withPath("block_prize_machine");
    public static final ResourceLocation EQUIPMENT_PRIZE_MACHINE_STRUCTURE = PrizeMachines.MOD_RL.withPath("equipment_prize_machine");

    private void addStructures(final ServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();

        Registry<StructureTemplatePool> templatePoolRegistry = server.registryAccess().registryOrThrow(Registries.TEMPLATE_POOL);

        SinglePoolElement blockMachine = StructurePoolElement.single(BLOCK_PRIZE_MACHINE_STRUCTURE.toString()).apply(StructureTemplatePool.Projection.RIGID);
        SinglePoolElement equipmentMachine = StructurePoolElement.single(EQUIPMENT_PRIZE_MACHINE_STRUCTURE.toString()).apply(StructureTemplatePool.Projection.RIGID);

        addToAllVillageHouses(templatePoolRegistry, blockMachine, 1);
        addToAllVillageHouses(templatePoolRegistry, equipmentMachine, 1);
    }

    private <T extends StructurePoolElement> void addToAllVillageHouses(Registry<StructureTemplatePool> templatePoolRegistry, T poolElement, int weight) {
        StructureTemplatePool plainsPool = templatePoolRegistry.get(ResourceLocation.fromNamespaceAndPath("minecraft","village/plains/houses"));
        StructureTemplatePool desertPool = templatePoolRegistry.get(ResourceLocation.fromNamespaceAndPath("minecraft","village/desert/houses"));
        StructureTemplatePool savannaPool = templatePoolRegistry.get(ResourceLocation.fromNamespaceAndPath("minecraft","village/savanna/houses"));
        StructureTemplatePool snowyPool = templatePoolRegistry.get(ResourceLocation.fromNamespaceAndPath("minecraft","village/snowy/houses"));
        StructureTemplatePool taigaPool = templatePoolRegistry.get(ResourceLocation.fromNamespaceAndPath("minecraft", "village/taiga/houses"));

        addStructureToPool(plainsPool, poolElement, weight);
        addStructureToPool(desertPool, poolElement, weight);
        addStructureToPool(savannaPool, poolElement, weight);
        addStructureToPool(snowyPool, poolElement, weight);
        addStructureToPool(taigaPool, poolElement, weight);
    }

    private <T extends StructurePoolElement> void addStructureToPool(StructureTemplatePool templatePool, T poolElement, int weight) {
        if (templatePool == null) {
            return;
        }

        StructureTemplatePoolAccessor accessor = (StructureTemplatePoolAccessor) templatePool;
        for (int i = 0; i < weight; i++) {
            accessor.getTemplates().add(poolElement);
        }

        List<Pair<StructurePoolElement, Integer>> rawTemplates = new ArrayList<>(accessor.getRawTemplates());
        rawTemplates.add(Pair.of(poolElement, weight));
        accessor.setRawTemplates(rawTemplates);
    }
}
