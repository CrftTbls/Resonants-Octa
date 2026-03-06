package org.crafttable.resonantsocta.data.trait;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class TraitEffectDefinition {
    private final ResourceLocation effectId;
    private final String triggerType;
    private final float baseValue;
    private final List<ResourceLocation> applicableToolTypes;

    public TraitEffectDefinition(ResourceLocation effectId, String triggerType, float baseValue,
            List<ResourceLocation> applicableToolTypes) {
        this.effectId = effectId;
        this.triggerType = triggerType;
        this.baseValue = baseValue;
        this.applicableToolTypes = applicableToolTypes;
    }

    public ResourceLocation getEffectId() {
        return effectId;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public float getBaseValue() {
        return baseValue;
    }

    public List<ResourceLocation> getApplicableToolTypes() {
        return applicableToolTypes;
    }

    public static TraitEffectDefinition fromJson(JsonObject json) {
        ResourceLocation effectId = json.has("effect_id") ? new ResourceLocation(json.get("effect_id").getAsString())
                : null;
        String triggerType = json.has("trigger_type") ? json.get("trigger_type").getAsString() : "";
        float baseValue = json.has("base_value") ? json.get("base_value").getAsFloat() : 0.0f;

        List<ResourceLocation> toolTypes = new ArrayList<>();
        if (json.has("applicable_tool_types")) {
            JsonArray arr = json.getAsJsonArray("applicable_tool_types");
            for (int i = 0; i < arr.size(); i++) {
                toolTypes.add(new ResourceLocation(arr.get(i).getAsString()));
            }
        }

        return new TraitEffectDefinition(effectId, triggerType, baseValue, toolTypes);
    }
}
