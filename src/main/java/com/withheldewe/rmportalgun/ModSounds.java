package com.withheldewe.rmportalgun;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    // Create a DeferredRegister to handle our custom sounds
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RMPortalGun.MOD_ID);

    // Register a new SoundEvent called "portal_shoot"
    public static final RegistryObject<SoundEvent> PORTAL_SHOOT =
            SOUND_EVENTS.register("portal_shoot",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(RMPortalGun.MOD_ID, "portal_shoot")
                    )
            );

    // Call this from your main mod class
    public static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }
}
