package com.withheldewe.rmportalgun;

import com.mojang.logging.LogUtils;
import com.withheldewe.rmportalgun.item.ModCreativeModeTabs;
import com.withheldewe.rmportalgun.item.ModItems;
import com.withheldewe.rmportalgun.item.client.PortalGunFirstPersonHandler;
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

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RMPortalGun.MOD_ID)
public class RMPortalGun {

    // Mod ID
    public static final String MOD_ID = "rmportalgun";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RMPortalGun(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Common setup
        modEventBus.addListener(this::commonSetup);

        // Register creative tabs
        ModCreativeModeTabs.register(modEventBus);

        // Register items
        ModItems.register(modEventBus);

        // Add creative tab contents
        modEventBus.addListener(this::addCreative);

        // Register config
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // Register this mod to Forge event bus
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup complete for RM Portal Gun mod.");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            // Add extra items to vanilla creative tab if desired
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server starting with RM Portal Gun mod.");
    }

    // Client-side events
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("Client setup complete for RM Portal Gun mod.");

            // ✅ Explicitly register PortalGunFirstPersonHandler to event bus
            MinecraftForge.EVENT_BUS.register(PortalGunFirstPersonHandler.class);
        }
    }
}
