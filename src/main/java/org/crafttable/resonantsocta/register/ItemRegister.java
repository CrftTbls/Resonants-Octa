package org.crafttable.resonantsocta.register;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import java.util.HashMap;
import java.util.Map;
import static org.crafttable.resonantsocta.core.RORegistries.ITEMS;

public class ItemRegister
{
	public static Map<String, RegistryObject<Item>> ITEMS_LIST = new HashMap<>();
	
	public static void setUp()
	{
		ITEMS_LIST.computeIfAbsent("example_item", key -> ITEMS.register(key,
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder()
							.alwaysEat()
							.nutrition(1)
							.saturationMod(2f)
							.build()))));
	}
}
