package org.crafttable.resonantsocta.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static org.crafttable.resonantsocta.ResonantsOcta.MODID;

public class RORegistries {

	// Create a Deferred Register to hold Blocks which will all be registered under
	// the "resonants_octa" namespace
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	// Create a Deferred Register to hold Items which will all be registered under
	// the "resonants_octa" namespace
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	// Create a Deferred Register to hold CreativeModeTabs which will all be
	// registered under the "resonants_octa" namespace
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
			.create(Registries.CREATIVE_MODE_TAB, MODID);
	// Create a Deferred Register to hold MenuTypes
	public static final DeferredRegister<net.minecraft.world.inventory.MenuType<?>> MENU_TYPES = DeferredRegister
			.create(net.minecraftforge.registries.ForgeRegistries.MENU_TYPES, MODID);

	// EventBus registration
	public static void register(IEventBus modEventBus) {
		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		CREATIVE_MODE_TABS.register(modEventBus);
		MENU_TYPES.register(modEventBus);
	}
}
