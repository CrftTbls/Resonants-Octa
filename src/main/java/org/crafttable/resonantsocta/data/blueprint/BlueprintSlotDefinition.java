package org.crafttable.resonantsocta.data.blueprint;

import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class BlueprintSlotDefinition {
    private final String slotId;
    private final String slotName;
    private final boolean required;
    private final int minCount;
    private final int maxCount;
    private final Map<String, Float> statMultipliers;

    public BlueprintSlotDefinition(String slotId, String slotName, boolean required, int minCount, int maxCount,
            Map<String, Float> statMultipliers) {
        this.slotId = slotId;
        this.slotName = slotName;
        this.required = required;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.statMultipliers = statMultipliers;
    }

    public String getSlotId() {
        return slotId;
    }

    public String getSlotName() {
        return slotName;
    }

    public boolean isRequired() {
        return required;
    }

    public int getMinCount() {
        return minCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public Map<String, Float> getStatMultipliers() {
        return statMultipliers;
    }

    public static BlueprintSlotDefinition fromJson(JsonObject json) {
        String slotId = json.has("slot_id") ? json.get("slot_id").getAsString() : "";
        String slotName = json.has("slot_name") ? json.get("slot_name").getAsString() : "";
        boolean required = json.has("required") ? json.get("required").getAsBoolean() : false;
        int minCount = json.has("min_count") ? json.get("min_count").getAsInt() : 0;
        int maxCount = json.has("max_count") ? json.get("max_count").getAsInt() : 0;

        Map<String, Float> multipliers = new HashMap<>();
        if (json.has("stat_multipliers")) {
            JsonObject multiObj = json.getAsJsonObject("stat_multipliers");
            for (Map.Entry<String, com.google.gson.JsonElement> entry : multiObj.entrySet()) {
                multipliers.put(entry.getKey(), entry.getValue().getAsFloat());
            }
        }

        return new BlueprintSlotDefinition(slotId, slotName, required, minCount, maxCount, multipliers);
    }
}
