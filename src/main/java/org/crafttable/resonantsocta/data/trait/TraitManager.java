package org.crafttable.resonantsocta.data.trait;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.crafttable.resonantsocta.ResonantsOcta;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ResonantsOcta.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TraitManager extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FOLDER = "traits";
    private static final Map<ResourceLocation, TraitDefinition> TRAITS = new HashMap<>();

    public TraitManager() {
        super(GSON, FOLDER);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager,
            ProfilerFiller profilerFiller) {
        TRAITS.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation id = entry.getKey();
            try {
                JsonObject json = entry.getValue().getAsJsonObject();
                TraitDefinition definition = TraitDefinition.fromJson(id, json);
                TRAITS.put(id, definition);
                LOGGER.debug("Loaded trait: {}", id);
            } catch (Exception e) {
                LOGGER.error("Failed to load trait: {}", id, e);
            }
        }
        LOGGER.info("Loaded {} traits", TRAITS.size());
    }

    public static TraitDefinition get(ResourceLocation id) {
        return TRAITS.get(id);
    }

    public static Map<ResourceLocation, TraitDefinition> getAll() {
        return TRAITS;
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new TraitManager());
    }
}
