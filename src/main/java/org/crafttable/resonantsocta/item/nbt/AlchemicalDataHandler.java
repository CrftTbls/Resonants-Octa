package org.crafttable.resonantsocta.item.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.crafttable.resonantsocta.ResonantsOcta;

import java.util.HashMap;
import java.util.Map;

public class AlchemicalDataHandler {
    public static final String KEY_ALCHEMICAL_DATA = "AlchemicalData";

    public static void setAlchemicalMatterId(ItemStack stack, ResourceLocation matterId) {
        CompoundTag root = stack.getOrCreateTag();
        CompoundTag data;
        if (root.contains(KEY_ALCHEMICAL_DATA, CompoundTag.TAG_COMPOUND)) {
            data = root.getCompound(KEY_ALCHEMICAL_DATA);
        } else {
            data = new CompoundTag();
            root.put(KEY_ALCHEMICAL_DATA, data);
        }

        data.putString("matter_id", matterId.toString());
    }

    public static ResourceLocation getMatterId(ItemStack stack) {
        CompoundTag data = stack.getTagElement(KEY_ALCHEMICAL_DATA);
        if (data != null && data.contains("matter_id", CompoundTag.TAG_STRING)) {
            return new ResourceLocation(data.getString("matter_id"));
        }
        return new ResourceLocation(ResonantsOcta.MODID, "empty_matter");
    }

    public static void setTraits(ItemStack stack, Map<ResourceLocation, Integer> traits) {
        CompoundTag root = stack.getOrCreateTag();
        CompoundTag data;
        if (root.contains(KEY_ALCHEMICAL_DATA, CompoundTag.TAG_COMPOUND)) {
            data = root.getCompound(KEY_ALCHEMICAL_DATA);
        } else {
            data = new CompoundTag();
            root.put(KEY_ALCHEMICAL_DATA, data);
        }

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
        CompoundTag data = stack.getTagElement(KEY_ALCHEMICAL_DATA);
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
