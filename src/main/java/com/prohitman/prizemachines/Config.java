package com.prohitman.prizemachines;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    private static ModConfigSpec.BooleanValue dungeonsHavePrizeMachines;
    private static ModConfigSpec.DoubleValue prizeMachineDungeonChance;

    public static ModConfigSpec buildServerSpec() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("Server");
        dungeonsHavePrizeMachines = builder.comment("Determines whether prize machines attempt to generate in dungeons. Default: true")
                .define("dungeonsHavePrizeMachines", true);
        prizeMachineDungeonChance = builder.comment("Chance of a prize machine being placed for any spot against a cobblestone wall in a dungeon. Default: 0.25")
                .defineInRange("prizeMachineDungeonChance", 0.25, 0, 1);

        return builder.build();
    }

    public static boolean dungeonPrizeMachines() {
        return dungeonsHavePrizeMachines.get();
    }

    public static float dungeonPrizeMachineChance() {
        return prizeMachineDungeonChance.get().floatValue();
    }
}
