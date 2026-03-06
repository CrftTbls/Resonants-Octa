package org.crafttable.resonantsocta.data.blueprint;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ToolTypeDefinition {
    private final ResourceLocation toolTypeId;
    private final Map<String, Float> baseStats;

    public ToolTypeDefinition(ResourceLocation toolTypeId, Map<String, Float> baseStats) {
        this.toolTypeId = toolTypeId;
        this.baseStats = baseStats;
    }

    public ResourceLocation getToolTypeId() {
        return toolTypeId;
    }

    public Map<String, Float> getBaseStats() {
        return baseStats;
    }

    public static ToolTypeDefinition fromJson(ResourceLocation id, JsonObject json) {
        Map<String, Float> stats = new HashMap<>();
        if (json.has("base_stats")) {
            JsonObject statsObj = json.getAsJsonObject("base_stats");
            for (Map.Entry<String, com.google.gson.JsonElement> entry : statsObj.entrySet()) {
                stats.put(entry.getKey(), entry.getValue().getAsFloat());
            }
        }
        return new ToolTypeDefinition(id, stats);
    }
}
