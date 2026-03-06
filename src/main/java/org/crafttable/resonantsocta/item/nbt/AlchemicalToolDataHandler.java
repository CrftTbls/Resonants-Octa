package org.crafttable.resonantsocta.item.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 錬金ツールの NBT データを操作するハンドラクラス。
 * 基礎ステータス、現在耐久値、組み込まれた特性リストなどを管理します。
 */
public class AlchemicalToolDataHandler {

    public static final String KEY_TOOL_DATA = "ROToolData";
    public static final String KEY_BLUEPRINT_ID = "blueprint_id";
    public static final String KEY_TOOL_TYPE = "tool_type";
    public static final String KEY_TRAITS = "traits";
    // 将来的な拡張項目（耐久値や各種ステータスマップ等）

    public static CompoundTag getOrCreateToolData(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(KEY_TOOL_DATA, Tag.TAG_COMPOUND)) {
            tag.put(KEY_TOOL_DATA, new CompoundTag());
        }
        return tag.getCompound(KEY_TOOL_DATA);
    }

    // ---------------------------------------------------------
    // Blueprint ID
    // ---------------------------------------------------------
    public static void setBlueprintId(ItemStack stack, ResourceLocation id) {
        CompoundTag data = getOrCreateToolData(stack);
        data.putString(KEY_BLUEPRINT_ID, id.toString());
    }

    public static ResourceLocation getBlueprintId(ItemStack stack) {
        CompoundTag data = getOrCreateToolData(stack);
        if (data.contains(KEY_BLUEPRINT_ID, Tag.TAG_STRING)) {
            return new ResourceLocation(data.getString(KEY_BLUEPRINT_ID));
        }
        return null;
    }

    // ---------------------------------------------------------
    // Tool Type ID (キャッシュ・整合性用)
    // ---------------------------------------------------------
    public static void setToolType(ItemStack stack, ResourceLocation type) {
        CompoundTag data = getOrCreateToolData(stack);
        data.putString(KEY_TOOL_TYPE, type.toString());
    }

    public static ResourceLocation getToolType(ItemStack stack) {
        CompoundTag data = getOrCreateToolData(stack);
        if (data.contains(KEY_TOOL_TYPE, Tag.TAG_STRING)) {
            return new ResourceLocation(data.getString(KEY_TOOL_TYPE));
        }
        return null; // NBTに無い場合はBlueprintManagerから引く等
    }

    // ---------------------------------------------------------
    // 特性リスト
    // ---------------------------------------------------------
    public static void setTraits(ItemStack stack, List<ResourceLocation> traits) {
        CompoundTag data = getOrCreateToolData(stack);
        ListTag listTag = new ListTag();
        for (ResourceLocation trait : traits) {
            listTag.add(StringTag.valueOf(trait.toString()));
        }
        data.put(KEY_TRAITS, listTag);
    }

    public static List<ResourceLocation> getTraits(ItemStack stack) {
        List<ResourceLocation> traits = new ArrayList<>();
        CompoundTag data = getOrCreateToolData(stack);
        if (data.contains(KEY_TRAITS, Tag.TAG_LIST)) {
            ListTag listTag = data.getList(KEY_TRAITS, Tag.TAG_STRING);
            for (int i = 0; i < listTag.size(); i++) {
                traits.add(new ResourceLocation(listTag.getString(i)));
            }
        }
        return traits;
    }
}
