package org.crafttable.resonantsocta.data.alchemical;

import com.google.gson.annotations.SerializedName;
import net.minecraft.resources.ResourceLocation;
import java.util.Map;
import java.util.List;

public class AlchemicalMatterDefinition {
    @SerializedName("representative_item")
    private String representativeItem;

    @SerializedName("base_color")
    private int baseColor = 0xFFFFFF;

    @SerializedName("texture_id")
    private String textureId;

    @SerializedName("stats")
    private Map<String, Float> stats;

    @SerializedName("traits")
    private List<String> traits;

    public ResourceLocation getRepresentativeItem() {
        return representativeItem != null && !representativeItem.isEmpty() ? new ResourceLocation(representativeItem)
                : null;
    }

    public int getBaseColor() {
        return baseColor;
    }

    public ResourceLocation getTextureId() {
        return textureId != null && !textureId.isEmpty() ? new ResourceLocation(textureId) : null;
    }

    public Map<String, Float> getStats() {
        return stats;
    }

    public List<String> getTraits() {
        return traits;
    }
}
