package org.crafttable.resonantsocta.register;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import static org.crafttable.resonantsocta.core.RORegistries.BLOCKS;
import static org.crafttable.resonantsocta.core.RORegistries.ITEMS;

public class BlockRegister
{
	public static Map<String, RegistryObject<Block>> BLOCKS_LIST = new HashMap<>();
	
	public static void setUp()
	{
		BLOCKS_LIST.computeIfAbsent("alchemy_crucible", key -> registerBlock(key, () -> new Block(BlockBehaviour.Properties.of())));
		BLOCKS_LIST.computeIfAbsent("alchemy_station", key -> registerBlock(key, () -> new Block(BlockBehaviour.Properties.of())));
		BLOCKS_LIST.computeIfAbsent("maintenance_station", key -> registerBlock(key, () -> new Block(BlockBehaviour.Properties.of())));
	}
	
	private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
	{
		RegistryObject<T> registerBlock = BLOCKS.register(name, block);
		ITEMS.register(name, () -> new BlockItem(registerBlock.get(), new Item.Properties()));
		return registerBlock;
	}
}
