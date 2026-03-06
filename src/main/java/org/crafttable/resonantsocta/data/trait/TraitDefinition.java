package org.crafttable.resonantsocta.data.trait;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class TraitDefinition {
    private final ResourceLocation traitId;
    private final String name;
    private final String description;
    private final int maxLevel;
    private final List<TraitEffectDefinition> effects;

    public TraitDefinition(ResourceLocation traitId, String name, String description, int maxLevel,
            List<TraitEffectDefinition> effects) {
        this.traitId = traitId;
        this.name = name;
        this.description = description;
        this.maxLevel = maxLevel;
        this.effects = effects;
    }

    public ResourceLocation getTraitId() {
        return traitId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public List<TraitEffectDefinition> getEffects() {
        return effects;
    }

    public static TraitDefinition fromJson(ResourceLocation id, JsonObject json) {
        String name = json.has("name") ? json.get("name").getAsString() : "";
        String description = json.has("description") ? json.get("description").getAsString() : "";
        int maxLevel = json.has("max_level") ? json.get("max_level").getAsInt() : 1;

        List<TraitEffectDefinition> effects = new ArrayList<>();
        if (json.has("effects")) {
            JsonArray arr = json.getAsJsonArray("effects");
            for (int i = 0; i < arr.size(); i++) {
                effects.add(TraitEffectDefinition.fromJson(arr.get(i).getAsJsonObject()));
            }
        }

        return new TraitDefinition(id, name, description, maxLevel, effects);
    }
}
