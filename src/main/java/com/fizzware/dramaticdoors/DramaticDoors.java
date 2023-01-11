package com.fizzware.dramaticdoors;

import com.fizzware.dramaticdoors.blockentities.DDBlockEntities;
import com.fizzware.dramaticdoors.blocks.DDBlocks;
import com.fizzware.dramaticdoors.client.ClientRenderer;
import com.fizzware.dramaticdoors.compat.AutomaticDoorCompat;
import com.fizzware.dramaticdoors.compat.Compats;
import com.fizzware.dramaticdoors.compat.QuarkCompat;
import com.fizzware.dramaticdoors.items.DDItems;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod("dramaticdoors")
public class DramaticDoors
{
    public static final String MOD_ID = "dramaticdoors";

    public DramaticDoors() {
    	DDBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    	DDItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    	DDBlockEntities.BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DDConfig.CONFIG);
    	
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        if (FMLEnvironment.dist == Dist.CLIENT) {
        	FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient); 
        }

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
    	if (Compats.AUTOMATIC_DOORS_INSTALLED) {
    		MinecraftForge.EVENT_BUS.register(new AutomaticDoorCompat());
    	}
    	if (Compats.QUARK_INSTALLED) {
    		MinecraftForge.EVENT_BUS.register(new QuarkCompat());
    	}
    }
    
    private void setupClient(final FMLClientSetupEvent event) {
    	ClientRenderer.setRenderers();
    	//DDItems.assignItemsToTabs();
    }

    /*public static final CreativeModeTab MAIN_TAB = CreativeModeTab.builder(Row.TOP "dramaticdoors") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DDItems.TALL_OAK_DOOR.get());
		}
    };
    
    public static final CreativeModeTab CHIPPED_TAB = Compats.CHIPPED_INSTALLED ? CreativeModeTab.builder("dramaticdoors_chipped") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DDItems.TALL_CHIPPED_BIRCH_DOOR_07.get());
		}
    } : null;
    
    public static final CreativeModeTab MACAW_TAB = Compats.MACAWS_DOORS_INSTALLED ? CreativeModeTab.builder("dramaticdoors_macaw") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(DDItems.TALL_MACAW_DARK_OAK_BARN_DOOR.get());
		}
    } : null;*/

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}
}
