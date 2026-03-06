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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = ResonantsOcta.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ResonanceManager extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FOLDER = "resonances";
    private static final Map<ResourceLocation, ResonanceDefinition> RESONANCES = new HashMap<>();

    public ResonanceManager() {
        super(GSON, FOLDER);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager,
            ProfilerFiller profilerFiller) {
        RESONANCES.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation id = entry.getKey();
            try {
                JsonObject json = entry.getValue().getAsJsonObject();
                ResonanceDefinition definition = ResonanceDefinition.fromJson(id, json);
                RESONANCES.put(id, definition);
                LOGGER.debug("Loaded resonance: {}", id);
            } catch (Exception e) {
                LOGGER.error("Failed to load resonance: {}", id, e);
            }
        }
        LOGGER.info("Loaded {} resonances", RESONANCES.size());
    }

    public static ResonanceDefinition get(ResourceLocation id) {
        return RESONANCES.get(id);
    }

    public static Map<ResourceLocation, ResonanceDefinition> getAll() {
        return RESONANCES;
    }

    /**
     * エンジン実行用に、優先度(priority)の高い順にソートしたリストを返します。
     */
    public static List<ResonanceDefinition> getSortedResonances() {
        List<ResonanceDefinition> list = new ArrayList<>(RESONANCES.values());
        list.sort(Comparator.comparingInt(ResonanceDefinition::getPriority).reversed());
        return list;
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new ResonanceManager());
    }
}
