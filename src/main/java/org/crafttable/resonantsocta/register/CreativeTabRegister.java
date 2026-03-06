package org.crafttable.resonantsocta.register;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import org.crafttable.resonantsocta.ResonantsOcta;
import org.crafttable.resonantsocta.item.nbt.AlchemicalDataHandler;

import java.util.HashMap;
import java.util.Map;

import static org.crafttable.resonantsocta.core.RORegistries.CREATIVE_MODE_TABS;

public class CreativeTabRegister {
	public static void setUp() {
		final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main_tab",
				() -> CreativeModeTab.builder()
						.icon(() -> new ItemStack(ItemRegister.ITEMS_LIST.get("example_item").get()))
						.title(Component.translatable("itemGroup." + ResonantsOcta.MODID + "creative_tab_main"))
						.displayItems((parameters, output) -> {
							ItemRegister.ITEMS_LIST
									.forEach((id, item) -> {
										if (id.equals("alchemical_matter_item")) {
											for (Map.Entry<ResourceLocation, org.crafttable.resonantsocta.data.alchemical.AlchemicalMatterDefinition> entrySet : org.crafttable.resonantsocta.data.alchemical.AlchemicalMatterManager
													.getAll().entrySet()) {
												ResourceLocation matterId = entrySet.getKey();
												ItemStack matterStack = new ItemStack(item.get());
												AlchemicalDataHandler.setAlchemicalMatterId(matterStack, matterId);

												// json定義のtraitsを初期から持たせておく処理
												org.crafttable.resonantsocta.data.alchemical.AlchemicalMatterDefinition def = org.crafttable.resonantsocta.data.alchemical.AlchemicalMatterManager
														.get(matterId);
												if (def != null && def.getTraits() != null
														&& !def.getTraits().isEmpty()) {
													Map<ResourceLocation, Integer> traits = new HashMap<>();
													for (String traitString : def.getTraits()) {
														traits.put(new ResourceLocation(traitString), 1); // テスト用にレベル1として付与
													}
													AlchemicalDataHandler.setTraits(matterStack, traits);
												}
												output.accept(matterStack);
											}
										} else if (id.equals("blueprint_item")) {
											ItemStack testBlueprint = new ItemStack(item.get());
											org.crafttable.resonantsocta.item.nbt.BlueprintDataHandler.setBlueprintId(
													testBlueprint,
													new ResourceLocation(ResonantsOcta.MODID, "test_sword_basic"));
											output.accept(testBlueprint);
										} else {
											output.accept(new ItemStack(item.get()));
										}
									});
							BlockRegister.BLOCKS_LIST
									.forEach((id, block) -> output.accept(new ItemStack(block.get())));
						})
						.build());
	}
}
