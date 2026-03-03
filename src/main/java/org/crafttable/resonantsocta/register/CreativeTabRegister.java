package org.crafttable.resonantsocta.register;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import org.crafttable.resonantsocta.ResonantsOcta;
import org.crafttable.resonantsocta.item.nbt.AlchemicalDataHandler;

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
											ItemStack testMatter = new ItemStack(item.get());
											AlchemicalDataHandler.setAlchemicalMatterId(testMatter,
													new ResourceLocation(ResonantsOcta.MODID, "test_matter"));
											output.accept(testMatter);
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
