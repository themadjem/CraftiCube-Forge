package com.themadjem.crafticube;

import com.mojang.logging.LogUtils;
import com.themadjem.crafticube.block.ModBlocks;
import com.themadjem.crafticube.block.entity.ModBlockEntities;
import com.themadjem.crafticube.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CraftiCube.MOD_ID)
public class CraftiCube {

    public static final String MOD_ID = "crafticube";
    private static final Logger LOGGER = LogUtils.getLogger(); // Directly reference a slf4j logger

    public CraftiCube() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(ModItems.CRAFTING_CORE);
            event.accept(ModBlocks.IRON_CRAFTICUBE);
            event.accept(ModBlocks.GOLD_CRAFTICUBE);
            event.accept(ModBlocks.DIAMOND_CRAFTICUBE);
            event.accept(ModBlocks.NETHERITE_CRAFTICUBE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }

    public static void logInfo(String s){LOGGER.info(s);}
    public static void logWarning(String s){LOGGER.warn(s);}
    public static void logError(String s){LOGGER.error(s);}
}
