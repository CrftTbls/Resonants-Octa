package org.crafttable.resonantsocta.data.trait;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public class TraitRequirement {
    private final ResourceLocation traitId;
    private final int count;

    public TraitRequirement(ResourceLocation traitId, int count) {
        this.traitId = traitId;
        this.count = count;
    }

    public ResourceLocation getTraitId() {
        return traitId;
    }

    public int getCount() {
        return count;
    }

    public static TraitRequirement fromJson(JsonObject json) {
        ResourceLocation traitId = json.has("trait") ? new ResourceLocation(json.get("trait").getAsString()) : null;
        int count = json.has("count") ? json.get("count").getAsInt() : 1;
        return new TraitRequirement(traitId, count);
    }
}
