package com.withheldewe.rmportalgun;

import com.mojang.logging.LogUtils;
import com.withheldewe.rmportalgun.item.ModCreativeModeTabs;
import com.withheldewe.rmportalgun.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in your META-INF/mods.toml
@Mod(RMPortalGun.MOD_ID)
public class RMPortalGun {
    public static final String MOD_ID = "rmportalgun";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RMPortalGun() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // **NEW** register our custom sounds
        ModSounds.register(modEventBus);

        // register your items and tabs
        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);

        // common setup listener
        modEventBus.addListener(this::commonSetup);
        // creative tab contents
        modEventBus.addListener(this::addCreative);
        // config
        FMLJavaModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // register server-side events
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup complete for RM Portal Gun mod.");
    }

    private void addCreative(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            // add to vanilla tab if desired
        }
    }

    @SubscribeEvent
    public void onServerStarting(final ServerStartingEvent event) {
        LOGGER.info("Server starting with RM Portal Gun mod.");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(final FMLClientSetupEvent event) {
            LOGGER.info("Client setup complete for RM Portal Gun mod.");
            // register renderers or other client-only setup here
        }
    }
}
