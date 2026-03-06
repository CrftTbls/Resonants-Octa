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
public class ToolTypeManager extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FOLDER = "tool_types";
    private static final Map<ResourceLocation, ToolTypeDefinition> TOOL_TYPES = new HashMap<>();

    public ToolTypeManager() {
        super(GSON, FOLDER);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager,
            ProfilerFiller profilerFiller) {
        TOOL_TYPES.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation id = entry.getKey();
            try {
                JsonObject json = entry.getValue().getAsJsonObject();
                ToolTypeDefinition definition = ToolTypeDefinition.fromJson(id, json);
                TOOL_TYPES.put(id, definition);
                LOGGER.debug("Loaded tool type: {}", id);
            } catch (Exception e) {
                LOGGER.error("Failed to load tool type: {}", id, e);
            }
        }
        LOGGER.info("Loaded {} tool types", TOOL_TYPES.size());
    }

    public static ToolTypeDefinition get(ResourceLocation id) {
        return TOOL_TYPES.get(id);
    }

    public static Map<ResourceLocation, ToolTypeDefinition> getAll() {
        return TOOL_TYPES;
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new ToolTypeManager());
    }
}
