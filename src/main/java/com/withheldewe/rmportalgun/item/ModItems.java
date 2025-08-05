package com.withheldewe.rmportalgun.item;

import com.withheldewe.rmportalgun.RMPortalGun;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RMPortalGun.MOD_ID);

    public static final RegistryObject<Item> Portal_Gun = ITEMS.register("portalgun",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MK1_Portal_Gun = ITEMS.register("mkoneportalgun",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
