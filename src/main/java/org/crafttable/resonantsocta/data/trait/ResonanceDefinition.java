package org.crafttable.resonantsocta.data.trait;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ResonanceDefinition {
    private final ResourceLocation resonanceId;
    private final int priority;
    private final List<TraitRequirement> requiredTraits;
    private final List<TraitRequirement> removeTraits;
    private final List<TraitRequirement> addTraits;

    public ResonanceDefinition(ResourceLocation resonanceId, int priority, List<TraitRequirement> requiredTraits,
            List<TraitRequirement> removeTraits, List<TraitRequirement> addTraits) {
        this.resonanceId = resonanceId;
        this.priority = priority;
        this.requiredTraits = requiredTraits;
        this.removeTraits = removeTraits;
        this.addTraits = addTraits;
    }

    public ResourceLocation getResonanceId() {
        return resonanceId;
    }

    public int getPriority() {
        return priority;
    }

    public List<TraitRequirement> getRequiredTraits() {
        return requiredTraits;
    }

    public List<TraitRequirement> getRemoveTraits() {
        return removeTraits;
    }

    public List<TraitRequirement> getAddTraits() {
        return addTraits;
    }

    public static ResonanceDefinition fromJson(ResourceLocation id, JsonObject json) {
        int priority = json.has("priority") ? json.get("priority").getAsInt() : 0;

        List<TraitRequirement> required = parseRequirements(json, "required_traits");
        List<TraitRequirement> remove = parseRequirements(json, "remove_traits");
        List<TraitRequirement> add = parseRequirements(json, "add_traits");

        return new ResonanceDefinition(id, priority, required, remove, add);
    }

    private static List<TraitRequirement> parseRequirements(JsonObject json, String key) {
        List<TraitRequirement> list = new ArrayList<>();
        if (json.has(key)) {
            JsonArray arr = json.getAsJsonArray(key);
            for (int i = 0; i < arr.size(); i++) {
                list.add(TraitRequirement.fromJson(arr.get(i).getAsJsonObject()));
            }
        }
        return list;
    }
}
