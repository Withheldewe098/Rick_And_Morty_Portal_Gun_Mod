package com.withheldewe.rmportalgun.item;

import com.withheldewe.rmportalgun.RMPortalGun;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RMPortalGun.MOD_ID);

    public static final RegistryObject<CreativeModeTab> RICKS_PORTAL_GUN_ITEMS_TAB = CREATIVE_MODE_TABS.register("ricks_portalgun_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.Portal_Gun.get()))
                    .title(Component.translatable("creativetab.rmportalgun.ricks_portalgun_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.Portal_Gun.get());
                        output.accept(ModItems.MK1_Portal_Gun.get());
                        output.accept(ModItems.MK2_Portal_Gun.get());
                        output.accept(ModItems.MK3_Portal_Gun.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> RICKS_PORTAL_GUN_BLOCKS_TAB = CREATIVE_MODE_TABS.register("ricks_portalgun_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.Portal_Gun.get()))
                    .withTabsBefore(RICKS_PORTAL_GUN_ITEMS_TAB.getId())
                    .title(Component.translatable("creativetab.rmportalgun.ricks_portalgun_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.Portal_Gun.get());
                        output.accept(ModItems.MK1_Portal_Gun.get());
                        output.accept(ModItems.MK2_Portal_Gun.get());
                        output.accept(ModItems.MK3_Portal_Gun.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
