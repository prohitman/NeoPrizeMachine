package com.prohitman.prizemachines.datagen;

import com.prohitman.prizemachines.PrizeMachines;
import com.prohitman.prizemachines.blocks.PrizeMachineBlock;
import com.prohitman.prizemachines.loot.functions.SetRandomFireworkStarFunction;
import com.prohitman.prizemachines.loot.functions.SetRandomPotionFunction;
import com.prohitman.prizemachines.registry.BlockRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetStewEffectFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;

public class ModPrizeLootGen extends AbstractLootTableSubProvider {
    public ModPrizeLootGen(HolderLookup.Provider registries) {
        super("prizes", registries);
    }

    @Override
    protected void generate() {
        ResourceLocation localPotionsTable = PrizeMachines.MOD_RL.withPath("items_with_potions");
        add(localPotionsTable, LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .add(
                                LootItem.lootTableItem(Items.POTION)
                                        .apply(SetRandomPotionFunction.randomPotion())
                        )
                        .add(
                                LootItem.lootTableItem(Items.SPLASH_POTION)
                                        .apply(SetRandomPotionFunction.randomPotion())
                        )
                        .add(
                                LootItem.lootTableItem(Items.LINGERING_POTION)
                                        .apply(SetRandomPotionFunction.randomPotion())
                        )
                        .add(
                                LootItem.lootTableItem(Items.TIPPED_ARROW)
                                        .apply(SetRandomPotionFunction.randomPotion())
                        )
                        .add(
                                LootItem.lootTableItem(Items.SUSPICIOUS_STEW)
                                        .apply(
                                                SetStewEffectFunction.stewEffect()
                                                        .withEffect(MobEffects.ABSORPTION, UniformGenerator.between(60, 120))
                                                        .withEffect(MobEffects.DAMAGE_BOOST, UniformGenerator.between(60, 120))
                                                        .withEffect(MobEffects.FIRE_RESISTANCE, UniformGenerator.between(60, 120))
                                                        .withEffect(MobEffects.WATER_BREATHING, UniformGenerator.between(60, 120))
                                                        .withEffect(MobEffects.JUMP, UniformGenerator.between(60, 120))
                                                        .withEffect(MobEffects.MOVEMENT_SPEED, UniformGenerator.between(60, 120))
                                                        .withEffect(MobEffects.REGENERATION, UniformGenerator.between(60, 120))
                                        )
                        )
        ));

        ResourceLocation localFireworks = PrizeMachines.MOD_RL.withPath("fireworks");
        add(localFireworks, LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .add(
                                LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .apply(SetComponentsFunction.setComponent(DataComponents.FIREWORKS, createFireworkFlightTag(1)))
                        )
                        .add(
                                LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .apply(SetComponentsFunction.setComponent(DataComponents.FIREWORKS, createFireworkFlightTag(2)))
                        )
                        .add(
                                LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .apply(SetComponentsFunction.setComponent(DataComponents.FIREWORKS, createFireworkFlightTag(3)))
                        )
                        .add(
                                LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .apply(SetRandomFireworkStarFunction.randomFireworkStar())
                        )
                        .add(
                                LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .apply(SetRandomFireworkStarFunction.randomFireworkStar())
                        )
                        .add(
                                LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .apply(SetRandomFireworkStarFunction.randomFireworkStar())
                        )
                        .add(
                                LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .apply(SetComponentsFunction.setComponent(DataComponents.FIREWORKS, createFireworkFlightTag(1)))
                                        .apply(SetRandomFireworkStarFunction.randomFireworkStar())
                        )
                        .add(
                                LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .apply(SetComponentsFunction.setComponent(DataComponents.FIREWORKS, createFireworkFlightTag(2)))
                                        .apply(SetRandomFireworkStarFunction.randomFireworkStar())
                        )
                        .add(
                                LootItem.lootTableItem(Items.FIREWORK_ROCKET)
                                        .apply(SetComponentsFunction.setComponent(DataComponents.FIREWORKS, createFireworkFlightTag(3)))
                                        .apply(SetRandomFireworkStarFunction.randomFireworkStar())
                        )
        ));

        ResourceLocation localMaps = PrizeMachines.MOD_RL.withPath("maps");
        add(localMaps, LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .add(
                                LootItem.lootTableItem(Items.MAP)
                                        .apply(
                                                ExplorationMapFunction.makeExplorationMap()
                                                        .setDestination(StructureTags.ON_TREASURE_MAPS)
                                                        .setMapDecoration(MapDecorationTypes.RED_X)
                                                        .setSearchRadius(10)
                                        )
                        )
                        .add(
                                LootItem.lootTableItem(Items.MAP)
                                        .apply(
                                                ExplorationMapFunction.makeExplorationMap()
                                                        .setDestination(StructureTags.ON_WOODLAND_EXPLORER_MAPS)
                                                        .setMapDecoration(MapDecorationTypes.WOODLAND_MANSION)
                                                        .setSearchRadius(10)
                                        )
                        )
                        .add(
                                LootItem.lootTableItem(Items.MAP)
                                        .apply(
                                                ExplorationMapFunction.makeExplorationMap()
                                                        .setDestination(StructureTags.ON_OCEAN_EXPLORER_MAPS)
                                                        .setMapDecoration(MapDecorationTypes.OCEAN_MONUMENT)
                                                        .setSearchRadius(10)
                                        )
                        )
                        .add(
                                LootItem.lootTableItem(Items.MAP)
                                        .apply(
                                                ExplorationMapFunction.makeExplorationMap()
                                                        .setDestination(StructureTags.RUINED_PORTAL)
                                                        .setMapDecoration(MapDecorationTypes.TARGET_X)
                                                        .setSearchRadius(10)
                                        )
                        )
                        .add(
                                AlternativesEntry.alternatives(
                                        createVillageMap(MapDecorationTypes.BLUE_BANNER)
                                                .when(LootItemRandomChanceCondition.randomChance(0.166f)),
                                        createVillageMap(MapDecorationTypes.BROWN_BANNER)
                                                .when(LootItemRandomChanceCondition.randomChance(0.166f)),
                                        createVillageMap(MapDecorationTypes.GREEN_BANNER)
                                                .when(LootItemRandomChanceCondition.randomChance(0.166f)),
                                        createVillageMap(MapDecorationTypes.ORANGE_BANNER)
                                                .when(LootItemRandomChanceCondition.randomChance(0.166f)),
                                        createVillageMap(MapDecorationTypes.LIME_BANNER)
                                                .when(LootItemRandomChanceCondition.randomChance(0.166f)),
                                        createVillageMap(MapDecorationTypes.PINK_BANNER)
                                                .when(LootItemRandomChanceCondition.randomChance(0.166f))
                                )
                        )
        ));

        addBlock(BlockRegistry.EQUIPMENT_PRIZE_MACHINE.get(),
                LootTable.lootTable().setParamSet(LootContextParamSets.BLOCK).withPool(
                        LootPool.lootPool().name("base_pool")
                                .add(DynamicLoot.dynamicEntry(PrizeMachineBlock.EQUIPMENT_DROP).setWeight(100))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, localPotionsTable.withPrefix("prizes/"))).setWeight(1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, localPotionsTable.withPrefix("prizes/"))).setWeight(1))
                                .add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, localPotionsTable.withPrefix("prizes/"))).setWeight(1))
                                .add(
                                        LootItem.lootTableItem(Items.FIREWORK_STAR)
                                                .apply(SetRandomFireworkStarFunction.randomFireworkStar()).setWeight(1)
                                )
                                .add(
                                        LootItem.lootTableItem(Items.BOOK)
                                                .apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)).setWeight(1)
                                )
                ));
        addBlock(BlockRegistry.BLOCK_PRIZE_MACHINE.get(),
                LootTable.lootTable().setParamSet(LootContextParamSets.BLOCK).withPool(
                        poolWithDynamic(PrizeMachineBlock.BLOCK_DROP)
                ));
    }

    protected LootPoolSingletonContainer.Builder<?> createVillageMap(Holder<MapDecorationType> marker) {
        return LootItem.lootTableItem(Items.MAP).apply(
                ExplorationMapFunction.makeExplorationMap()
                        .setDestination(StructureTags.VILLAGE)
                        .setMapDecoration(marker)
                        .setSearchRadius(10)
        );
    }

    protected Fireworks createFireworkFlightTag(int flight) {
        return new Fireworks(flight, List.of(FireworkExplosion.DEFAULT));
    }

    protected LootPool.Builder poolWithDynamic(ResourceLocation dynamicDropName) {
        return LootPool.lootPool().name("base_pool").add(DynamicLoot.dynamicEntry(dynamicDropName));
    }

    protected void addBlock(Block block, LootTable.Builder builder) {
        add(BuiltInRegistries.BLOCK.getKey(block), builder);
    }
}
