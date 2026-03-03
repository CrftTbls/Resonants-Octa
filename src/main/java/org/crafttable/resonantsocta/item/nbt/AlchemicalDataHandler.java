package org.crafttable.resonantsocta.item.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.crafttable.resonantsocta.ResonantsOcta;

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
}
