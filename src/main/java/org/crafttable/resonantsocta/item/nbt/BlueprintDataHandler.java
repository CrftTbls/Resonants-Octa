package org.crafttable.resonantsocta.item.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.HashMap;
import java.util.Map;

public class BlueprintDataHandler {
    public static final String KEY_BLUEPRINT_DATA = "BlueprintData";
    public static final String KEY_BLUEPRINT_ID = "blueprint_id";

    public static void saveFixationData(ItemStack stack, float rate, float growth) {
        CompoundTag data = stack.getOrCreateTagElement(KEY_BLUEPRINT_DATA);
        data.putFloat("current_fixation_rate", rate);
        data.putFloat("stat_multiplier_growth", growth);
    }

    public static float getFixationRate(ItemStack stack) {
        CompoundTag data = stack.getTagElement(KEY_BLUEPRINT_DATA);
        return data != null ? data.getFloat("current_fixation_rate") : 0.0f;
    }

    public static void setBlueprintId(ItemStack stack, ResourceLocation id) {
        CompoundTag data = stack.getOrCreateTagElement(KEY_BLUEPRINT_DATA);
        data.putString(KEY_BLUEPRINT_ID, id.toString());
    }

    public static ResourceLocation getBlueprintId(ItemStack stack) {
        CompoundTag data = stack.getTagElement(KEY_BLUEPRINT_DATA);
        if (data != null && data.contains(KEY_BLUEPRINT_ID)) {
            return new ResourceLocation(data.getString(KEY_BLUEPRINT_ID));
        }
        return null;
    }

    public static void setTraits(ItemStack stack, Map<ResourceLocation, Integer> traits) {
        CompoundTag data = stack.getOrCreateTagElement(KEY_BLUEPRINT_DATA);
        ListTag list = new ListTag();
        for (Map.Entry<ResourceLocation, Integer> entry : traits.entrySet()) {
            CompoundTag traitTag = new CompoundTag();
            traitTag.putString("id", entry.getKey().toString());
            traitTag.putInt("lvl", entry.getValue());
            list.add(traitTag);
        }
        data.put("AttachedTraits", list);
    }

    public static Map<ResourceLocation, Integer> getTraits(ItemStack stack) {
        Map<ResourceLocation, Integer> traits = new HashMap<>();
        CompoundTag data = stack.getTagElement(KEY_BLUEPRINT_DATA);
        if (data != null && data.contains("AttachedTraits", CompoundTag.TAG_LIST)) {
            ListTag list = data.getList("AttachedTraits", CompoundTag.TAG_COMPOUND);
            for (int i = 0; i < list.size(); i++) {
                CompoundTag tag = list.getCompound(i);
                ResourceLocation id = new ResourceLocation(tag.getString("id"));
                int lvl = tag.getInt("lvl");
                traits.put(id, lvl);
            }
        }
        return traits;
    }
}
