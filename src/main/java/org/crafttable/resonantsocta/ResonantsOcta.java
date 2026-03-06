package org.crafttable.resonantsocta;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.crafttable.resonantsocta.core.RORegistries;
import org.crafttable.resonantsocta.register.BlockRegister;
import org.crafttable.resonantsocta.register.CreativeTabRegister;
import org.crafttable.resonantsocta.register.ItemRegister;
import org.slf4j.Logger;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import org.crafttable.resonantsocta.data.alchemical.AlchemicalMatterManager;
import org.crafttable.resonantsocta.data.alchemical.AlchemicalMatterDefinition;
import org.crafttable.resonantsocta.item.nbt.AlchemicalDataHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.item.ItemProperties;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ResonantsOcta.MODID)
public class ResonantsOcta {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "resonants_octa";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public ResonantsOcta() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Item and blocks registration
        BlockRegister.setUp();
        ItemRegister.setUp();
        CreativeTabRegister.setUp();

        // Register connect to eventBus
        RORegistries.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        // Register our mod's ForgeConfigSpec so that Forge can create and setUp the
        // config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("RO MOD START SETUP");
        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods
    // in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

            event.enqueueWork(() -> {
                ItemProperties.register(ItemRegister.ITEMS_LIST.get("alchemical_matter_item").get(),
                        new ResourceLocation(ResonantsOcta.MODID, "texture_variant"),
                        (stack, level, entity, seed) -> {
                            ResourceLocation matterId = AlchemicalDataHandler.getMatterId(stack);
                            AlchemicalMatterDefinition def = AlchemicalMatterManager.get(matterId);
                            if (def != null) {
                                ResourceLocation tex = def.getTextureId();
                                // test_matterのテクスチャIDだった場合のみ 1.0F を返す
                                if (tex != null
                                        && tex.equals(new ResourceLocation(ResonantsOcta.MODID, "test_matter"))) {
                                    return 1.0F;
                                }
                            }
                            return 0.0F;
                        });

                ItemProperties.register(ItemRegister.ITEMS_LIST.get("blueprint_item").get(),
                        new ResourceLocation(ResonantsOcta.MODID, "blueprint_variant"),
                        (stack, level, entity, seed) -> {
                            ResourceLocation blueprintId = org.crafttable.resonantsocta.item.nbt.BlueprintDataHandler
                                    .getBlueprintId(stack);
                            org.crafttable.resonantsocta.data.blueprint.BlueprintDefinition bDef = org.crafttable.resonantsocta.data.blueprint.BlueprintManager
                                    .get(blueprintId);
                            if (bDef != null) {
                                ResourceLocation tex = bDef.getTextureId();
                                // test_sword_basicのテクスチャIDとして指定された場合に1.0Fを返す
                                if (tex != null
                                        && tex.equals(new ResourceLocation(ResonantsOcta.MODID, "test_sword_basic"))) {
                                    return 1.0F;
                                }
                            }
                            return 0.0F;
                        });
            });
        }

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.register((stack, tintIndex) -> {
                if (tintIndex > 0)
                    return 0xFFFFFF;
                ResourceLocation matterId = AlchemicalDataHandler.getMatterId(stack);
                AlchemicalMatterDefinition def = AlchemicalMatterManager.get(matterId);
                return def != null ? def.getBaseColor() : 0xFFFFFF;
            }, ItemRegister.ITEMS_LIST.get("alchemical_matter_item").get());
        }
    }
}
