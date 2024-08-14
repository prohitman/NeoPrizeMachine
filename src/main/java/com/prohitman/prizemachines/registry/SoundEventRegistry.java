package com.prohitman.prizemachines.registry;

import com.prohitman.prizemachines.PrizeMachines;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SoundEventRegistry {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(Registries.SOUND_EVENT, PrizeMachines.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> AWARDED_PRIZE = REGISTER.register("awarded_prize", () -> SoundEvent.createVariableRangeEvent(PrizeMachines.MOD_RL.withPath("block.prizemachines.awarded_prize")));
}
