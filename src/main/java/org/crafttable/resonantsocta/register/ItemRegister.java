package org.crafttable.resonantsocta.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import org.crafttable.resonantsocta.ResonantsOcta;
import org.crafttable.resonantsocta.item.AlchemicalToolRegistry;
import org.crafttable.resonantsocta.item.accessories.AlchemicalAmulet;
import org.crafttable.resonantsocta.item.accessories.AlchemicalAnklet;
import org.crafttable.resonantsocta.item.accessories.AlchemicalCape;
import org.crafttable.resonantsocta.item.accessories.AlchemicalCirclet;
import org.crafttable.resonantsocta.item.accessories.AlchemicalGauntlet;
import org.crafttable.resonantsocta.item.accessories.AlchemicalRing;
import org.crafttable.resonantsocta.item.accessories.AlchemicalTalisman;
import org.crafttable.resonantsocta.item.accessories.AlchemicalToolBelt;
import org.crafttable.resonantsocta.item.armors.AlchemicalAeroBoots;
import org.crafttable.resonantsocta.item.armors.AlchemicalAeroChestplate;
import org.crafttable.resonantsocta.item.armors.AlchemicalAeroHelmet;
import org.crafttable.resonantsocta.item.armors.AlchemicalAeroLeggings;
import org.crafttable.resonantsocta.item.armors.AlchemicalBoots;
import org.crafttable.resonantsocta.item.armors.AlchemicalChestplate;
import org.crafttable.resonantsocta.item.armors.AlchemicalHeavyBoots;
import org.crafttable.resonantsocta.item.armors.AlchemicalHeavyChestplate;
import org.crafttable.resonantsocta.item.armors.AlchemicalHeavyHelmet;
import org.crafttable.resonantsocta.item.armors.AlchemicalHeavyLeggings;
import org.crafttable.resonantsocta.item.armors.AlchemicalHelmet;
import org.crafttable.resonantsocta.item.armors.AlchemicalLeggings;
import org.crafttable.resonantsocta.item.armors.AlchemicalLightBoots;
import org.crafttable.resonantsocta.item.armors.AlchemicalLightChestplate;
import org.crafttable.resonantsocta.item.armors.AlchemicalLightHelmet;
import org.crafttable.resonantsocta.item.armors.AlchemicalLightLeggings;
import org.crafttable.resonantsocta.item.armors.AlchemicalScubaBoots;
import org.crafttable.resonantsocta.item.armors.AlchemicalScubaChestplate;
import org.crafttable.resonantsocta.item.armors.AlchemicalScubaHelmet;
import org.crafttable.resonantsocta.item.armors.AlchemicalScubaLeggings;
import org.crafttable.resonantsocta.item.armors.AlchemicalShadowBoots;
import org.crafttable.resonantsocta.item.armors.AlchemicalShadowChestplate;
import org.crafttable.resonantsocta.item.armors.AlchemicalShadowHelmet;
import org.crafttable.resonantsocta.item.armors.AlchemicalShadowLeggings;
import org.crafttable.resonantsocta.item.tools.AlchemicalAxe;
import org.crafttable.resonantsocta.item.tools.AlchemicalBow;
import org.crafttable.resonantsocta.item.tools.AlchemicalBroadAxe;
import org.crafttable.resonantsocta.item.tools.AlchemicalBroadsword;
import org.crafttable.resonantsocta.item.tools.AlchemicalBuckler;
import org.crafttable.resonantsocta.item.tools.AlchemicalCrossbow;
import org.crafttable.resonantsocta.item.tools.AlchemicalExcavator;
import org.crafttable.resonantsocta.item.tools.AlchemicalGreatshield;
import org.crafttable.resonantsocta.item.tools.AlchemicalHammer;
import org.crafttable.resonantsocta.item.tools.AlchemicalHandCrossbow;
import org.crafttable.resonantsocta.item.tools.AlchemicalHatchet;
import org.crafttable.resonantsocta.item.tools.AlchemicalHeavyCrossbow;
import org.crafttable.resonantsocta.item.tools.AlchemicalHoe;
import org.crafttable.resonantsocta.item.tools.AlchemicalJavelin;
import org.crafttable.resonantsocta.item.tools.AlchemicalKnife;
import org.crafttable.resonantsocta.item.tools.AlchemicalLongbow;
import org.crafttable.resonantsocta.item.tools.AlchemicalPickaxe;
import org.crafttable.resonantsocta.item.tools.AlchemicalPike;
import org.crafttable.resonantsocta.item.tools.AlchemicalScythe;
import org.crafttable.resonantsocta.item.tools.AlchemicalShield;
import org.crafttable.resonantsocta.item.tools.AlchemicalShortbow;
import org.crafttable.resonantsocta.item.tools.AlchemicalShovel;
import org.crafttable.resonantsocta.item.tools.AlchemicalSpear;
import org.crafttable.resonantsocta.item.tools.AlchemicalSword;
import org.crafttable.resonantsocta.item.utils.AlchemicalArmorBox;
import org.crafttable.resonantsocta.item.utils.AlchemicalToolBox;
import org.crafttable.resonantsocta.item.BlueprintItem;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import org.crafttable.resonantsocta.item.AlchemicalMatterItem;

import java.util.HashMap;
import java.util.Map;
import static org.crafttable.resonantsocta.core.RORegistries.ITEMS;

public class ItemRegister {
	public static Map<String, RegistryObject<Item>> ITEMS_LIST = new HashMap<>();

	public static void setUp() {
		ITEMS_LIST.computeIfAbsent("example_item", key -> ITEMS.register(key,
				() -> new Item(new Item.Properties()
						.food(new FoodProperties.Builder()
								.alwaysEat()
								.nutrition(1)
								.saturationMod(2f)
								.build()))));

		ITEMS_LIST.computeIfAbsent("alchemical_matter_item", key -> ITEMS.register(key,
				() -> new AlchemicalMatterItem(new Item.Properties())));

		ITEMS_LIST.computeIfAbsent("blueprint_item", key -> ITEMS.register(key,
				() -> new BlueprintItem(new Item.Properties())));
		// 錬金ツール（基底）一括登録
		ITEMS_LIST.computeIfAbsent("alchemical_sword", key -> ITEMS.register(key,
				() -> new AlchemicalSword(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "sword"));
		ITEMS_LIST.computeIfAbsent("alchemical_knife", key -> ITEMS.register(key,
				() -> new AlchemicalKnife(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "knife"));
		ITEMS_LIST.computeIfAbsent("alchemical_broadsword", key -> ITEMS.register(key,
				() -> new AlchemicalBroadsword(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "broadsword"));
		ITEMS_LIST.computeIfAbsent("alchemical_javelin", key -> ITEMS.register(key,
				() -> new AlchemicalJavelin(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "javelin"));
		ITEMS_LIST.computeIfAbsent("alchemical_spear", key -> ITEMS.register(key,
				() -> new AlchemicalSpear(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "spear"));
		ITEMS_LIST.computeIfAbsent("alchemical_pike", key -> ITEMS.register(key,
				() -> new AlchemicalPike(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "pike"));
		ITEMS_LIST.computeIfAbsent("alchemical_pickaxe", key -> ITEMS.register(key,
				() -> new AlchemicalPickaxe(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "pickaxe"));
		ITEMS_LIST.computeIfAbsent("alchemical_hammer", key -> ITEMS.register(key,
				() -> new AlchemicalHammer(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "hammer"));
		ITEMS_LIST.computeIfAbsent("alchemical_axe", key -> ITEMS.register(key,
				() -> new AlchemicalAxe(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "axe"));
		ITEMS_LIST.computeIfAbsent("alchemical_hatchet", key -> ITEMS.register(key,
				() -> new AlchemicalHatchet(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "hatchet"));
		ITEMS_LIST.computeIfAbsent("alchemical_broad_axe", key -> ITEMS.register(key,
				() -> new AlchemicalBroadAxe(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "broad_axe"));
		ITEMS_LIST.computeIfAbsent("alchemical_shovel", key -> ITEMS.register(key,
				() -> new AlchemicalShovel(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "shovel"));
		ITEMS_LIST.computeIfAbsent("alchemical_excavator", key -> ITEMS.register(key,
				() -> new AlchemicalExcavator(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "excavator"));
		ITEMS_LIST.computeIfAbsent("alchemical_hoe", key -> ITEMS.register(key,
				() -> new AlchemicalHoe(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "hoe"));
		ITEMS_LIST.computeIfAbsent("alchemical_scythe", key -> ITEMS.register(key,
				() -> new AlchemicalScythe(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "scythe"));
		ITEMS_LIST.computeIfAbsent("alchemical_bow", key -> ITEMS.register(key,
				() -> new AlchemicalBow(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "bow"));
		ITEMS_LIST.computeIfAbsent("alchemical_shortbow", key -> ITEMS.register(key,
				() -> new AlchemicalShortbow(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "shortbow"));
		ITEMS_LIST.computeIfAbsent("alchemical_longbow", key -> ITEMS.register(key,
				() -> new AlchemicalLongbow(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "longbow"));
		ITEMS_LIST.computeIfAbsent("alchemical_crossbow", key -> ITEMS.register(key,
				() -> new AlchemicalCrossbow(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "crossbow"));
		ITEMS_LIST.computeIfAbsent("alchemical_hand_crossbow", key -> ITEMS.register(key,
				() -> new AlchemicalHandCrossbow(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "hand_crossbow"));
		ITEMS_LIST.computeIfAbsent("alchemical_heavy_crossbow", key -> ITEMS.register(key,
				() -> new AlchemicalHeavyCrossbow(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "heavy_crossbow"));
		ITEMS_LIST.computeIfAbsent("alchemical_shield", key -> ITEMS.register(key,
				() -> new AlchemicalShield(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "shield"));
		ITEMS_LIST.computeIfAbsent("alchemical_buckler", key -> ITEMS.register(key,
				() -> new AlchemicalBuckler(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "buckler"));
		ITEMS_LIST.computeIfAbsent("alchemical_greatshield", key -> ITEMS.register(key,
				() -> new AlchemicalGreatshield(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "greatshield"));
		ITEMS_LIST.computeIfAbsent("alchemical_ring", key -> ITEMS.register(key,
				() -> new AlchemicalRing(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "ring"));
		ITEMS_LIST.computeIfAbsent("alchemical_amulet", key -> ITEMS.register(key,
				() -> new AlchemicalAmulet(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "amulet"));
		ITEMS_LIST.computeIfAbsent("alchemical_circlet", key -> ITEMS.register(key,
				() -> new AlchemicalCirclet(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "circlet"));
		ITEMS_LIST.computeIfAbsent("alchemical_gauntlet", key -> ITEMS.register(key,
				() -> new AlchemicalGauntlet(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "gauntlet"));
		ITEMS_LIST.computeIfAbsent("alchemical_tool_belt", key -> ITEMS.register(key,
				() -> new AlchemicalToolBelt(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "tool_belt"));
		ITEMS_LIST.computeIfAbsent("alchemical_cape", key -> ITEMS.register(key,
				() -> new AlchemicalCape(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "cape"));
		ITEMS_LIST.computeIfAbsent("alchemical_talisman", key -> ITEMS.register(key,
				() -> new AlchemicalTalisman(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "talisman"));
		ITEMS_LIST.computeIfAbsent("alchemical_anklet", key -> ITEMS.register(key,
				() -> new AlchemicalAnklet(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "anklet"));
		ITEMS_LIST.computeIfAbsent("alchemical_tool_box", key -> ITEMS.register(key,
				() -> new AlchemicalToolBox(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "tool_box"));
		ITEMS_LIST.computeIfAbsent("alchemical_armor_box", key -> ITEMS.register(key,
				() -> new AlchemicalArmorBox(new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "armor_box"));

		// 錬金防具 一括登録
		ITEMS_LIST.computeIfAbsent("alchemical_helmet", key -> ITEMS.register(key,
				() -> new AlchemicalHelmet(ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "helmet"));
		ITEMS_LIST.computeIfAbsent("alchemical_chestplate", key -> ITEMS.register(key,
				() -> new AlchemicalChestplate(ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "chestplate"));
		ITEMS_LIST.computeIfAbsent("alchemical_leggings", key -> ITEMS.register(key,
				() -> new AlchemicalLeggings(ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "leggings"));
		ITEMS_LIST.computeIfAbsent("alchemical_boots", key -> ITEMS.register(key,
				() -> new AlchemicalBoots(ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "boots"));
		ITEMS_LIST.computeIfAbsent("alchemical_scuba_helmet", key -> ITEMS.register(key,
				() -> new AlchemicalScubaHelmet(ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "scuba_helmet"));
		ITEMS_LIST.computeIfAbsent("alchemical_scuba_chestplate", key -> ITEMS.register(key,
				() -> new AlchemicalScubaChestplate(ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "scuba_chestplate"));
		ITEMS_LIST.computeIfAbsent("alchemical_scuba_leggings", key -> ITEMS.register(key,
				() -> new AlchemicalScubaLeggings(ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "scuba_leggings"));
		ITEMS_LIST.computeIfAbsent("alchemical_scuba_boots", key -> ITEMS.register(key,
				() -> new AlchemicalScubaBoots(ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "scuba_boots"));
		ITEMS_LIST.computeIfAbsent("alchemical_shadow_helmet", key -> ITEMS.register(key,
				() -> new AlchemicalShadowHelmet(ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "shadow_helmet"));
		ITEMS_LIST.computeIfAbsent("alchemical_shadow_chestplate", key -> ITEMS.register(key,
				() -> new AlchemicalShadowChestplate(ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "shadow_chestplate"));
		ITEMS_LIST.computeIfAbsent("alchemical_shadow_leggings", key -> ITEMS.register(key,
				() -> new AlchemicalShadowLeggings(ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "shadow_leggings"));
		ITEMS_LIST.computeIfAbsent("alchemical_shadow_boots", key -> ITEMS.register(key,
				() -> new AlchemicalShadowBoots(ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "shadow_boots"));
		ITEMS_LIST.computeIfAbsent("alchemical_aero_helmet", key -> ITEMS.register(key,
				() -> new AlchemicalAeroHelmet(ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "aero_helmet"));
		ITEMS_LIST.computeIfAbsent("alchemical_aero_chestplate", key -> ITEMS.register(key,
				() -> new AlchemicalAeroChestplate(ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "aero_chestplate"));
		ITEMS_LIST.computeIfAbsent("alchemical_aero_leggings", key -> ITEMS.register(key,
				() -> new AlchemicalAeroLeggings(ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "aero_leggings"));
		ITEMS_LIST.computeIfAbsent("alchemical_aero_boots", key -> ITEMS.register(key,
				() -> new AlchemicalAeroBoots(ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "aero_boots"));
		ITEMS_LIST.computeIfAbsent("alchemical_heavy_helmet", key -> ITEMS.register(key,
				() -> new AlchemicalHeavyHelmet(ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "heavy_helmet"));
		ITEMS_LIST.computeIfAbsent("alchemical_heavy_chestplate", key -> ITEMS.register(key,
				() -> new AlchemicalHeavyChestplate(ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "heavy_chestplate"));
		ITEMS_LIST.computeIfAbsent("alchemical_heavy_leggings", key -> ITEMS.register(key,
				() -> new AlchemicalHeavyLeggings(ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "heavy_leggings"));
		ITEMS_LIST.computeIfAbsent("alchemical_heavy_boots", key -> ITEMS.register(key,
				() -> new AlchemicalHeavyBoots(ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "heavy_boots"));
		ITEMS_LIST.computeIfAbsent("alchemical_light_helmet", key -> ITEMS.register(key,
				() -> new AlchemicalLightHelmet(ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "light_helmet"));
		ITEMS_LIST.computeIfAbsent("alchemical_light_chestplate", key -> ITEMS.register(key,
				() -> new AlchemicalLightChestplate(ArmorItem.Type.CHESTPLATE, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "light_chestplate"));
		ITEMS_LIST.computeIfAbsent("alchemical_light_leggings", key -> ITEMS.register(key,
				() -> new AlchemicalLightLeggings(ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "light_leggings"));
		ITEMS_LIST.computeIfAbsent("alchemical_light_boots", key -> ITEMS.register(key,
				() -> new AlchemicalLightBoots(ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1))));
		AlchemicalToolRegistry.registerImplementedToolType(
				new ResourceLocation(ResonantsOcta.MODID, "light_boots"));

	}
}