package org.crafttable.resonantsocta.data.blueprint;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlueprintDefinition {
    private final ResourceLocation blueprintId;
    private final ResourceLocation resultItem;
    private final ResourceLocation toolType;
    private final float enchantCapacity;
    private final Map<String, Float> statMultipliers;
    private final int maxFixedTraits;
    private final ResourceLocation textureId;
    private final List<BlueprintSlotDefinition> slots;

    public BlueprintDefinition(
            ResourceLocation blueprintId,
            ResourceLocation resultItem,
            ResourceLocation toolType,
            float enchantCapacity,
            Map<String, Float> statMultipliers,
            int maxFixedTraits,
            ResourceLocation textureId,
            List<BlueprintSlotDefinition> slots) {
        this.blueprintId = blueprintId;
        this.resultItem = resultItem;
        this.toolType = toolType;
        this.enchantCapacity = enchantCapacity;
        this.statMultipliers = statMultipliers;
        this.maxFixedTraits = maxFixedTraits;
        this.textureId = textureId;
        this.slots = slots;
    }

    public ResourceLocation getBlueprintId() {
        return blueprintId;
    }

    public ResourceLocation getResultItem() {
        return resultItem;
    }

    public ResourceLocation getToolType() {
        return toolType;
    }

    public float getEnchantCapacity() {
        return enchantCapacity;
    }

    public Map<String, Float> getStatMultipliers() {
        return statMultipliers;
    }

    public int getMaxFixedTraits() {
        return maxFixedTraits;
    }

    public ResourceLocation getTextureId() {
        return textureId;
    }

    public List<BlueprintSlotDefinition> getSlots() {
        return slots;
    }

    public static BlueprintDefinition fromJson(ResourceLocation id, JsonObject json) {
        ResourceLocation resultItem = json.has("result_item")
                ? new ResourceLocation(json.get("result_item").getAsString())
                : null;
        ResourceLocation toolType = json.has("tool_type") ? new ResourceLocation(json.get("tool_type").getAsString())
                : null;
        float enchantCapacity = json.has("enchant_capacity") ? json.get("enchant_capacity").getAsFloat() : 0.0f;
        int maxFixedTraits = json.has("max_fixed_traits") ? json.get("max_fixed_traits").getAsInt() : 0;
        ResourceLocation textureId = json.has("texture_id") ? new ResourceLocation(json.get("texture_id").getAsString())
                : null;

        Map<String, Float> multipliers = new HashMap<>();
        if (json.has("stat_multipliers")) {
            JsonObject multiObj = json.getAsJsonObject("stat_multipliers");
            for (Map.Entry<String, com.google.gson.JsonElement> entry : multiObj.entrySet()) {
                multipliers.put(entry.getKey(), entry.getValue().getAsFloat());
            }
        }

        List<BlueprintSlotDefinition> slots = new ArrayList<>();
        if (json.has("slots")) {
            JsonArray slotsArr = json.getAsJsonArray("slots");
            for (int i = 0; i < slotsArr.size(); i++) {
                slots.add(BlueprintSlotDefinition.fromJson(slotsArr.get(i).getAsJsonObject()));
            }
        }

        return new BlueprintDefinition(id, resultItem, toolType, enchantCapacity, multipliers, maxFixedTraits,
                textureId, slots);
    }
}
