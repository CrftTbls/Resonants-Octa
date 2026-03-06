package org.crafttable.resonantsocta.data.blueprint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.crafttable.resonantsocta.ResonantsOcta;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ResonantsOcta.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlueprintManager extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FOLDER = "blueprints";
    private static final Map<ResourceLocation, BlueprintDefinition> BLUEPRINTS = new HashMap<>();

    public BlueprintManager() {
        super(GSON, FOLDER);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager,
            ProfilerFiller profilerFiller) {
        BLUEPRINTS.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation id = entry.getKey();
            try {
                JsonObject json = entry.getValue().getAsJsonObject();
                BlueprintDefinition definition = BlueprintDefinition.fromJson(id, json);
                BLUEPRINTS.put(id, definition);
                LOGGER.debug("Loaded blueprint: {}", id);
            } catch (Exception e) {
                LOGGER.error("Failed to load blueprint: {}", id, e);
            }
        }
        LOGGER.info("Loaded {} blueprints", BLUEPRINTS.size());
    }

    public static BlueprintDefinition get(ResourceLocation id) {
        return BLUEPRINTS.get(id);
    }

    public static Map<ResourceLocation, BlueprintDefinition> getAll() {
        return BLUEPRINTS;
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new BlueprintManager());
    }
}
