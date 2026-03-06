package org.crafttable.resonantsocta.item;

import net.minecraft.resources.ResourceLocation;
import org.crafttable.resonantsocta.ResonantsOcta;

import java.util.HashSet;
import java.util.Set;

/**
 * ゲーム内に実際に実装コードとして存在するツールタイプを管理するレジストリ。
 * データパック側で未実装のツールタイプが指定された設計図を検証・弾くための安全機構。
 */
public class AlchemicalToolRegistry {

    private static final Set<ResourceLocation> IMPLEMENTED_TOOL_TYPES = new HashSet<>();

    /**
     * 各ツール用アイテムクラスの登録時など、実装が存在することを自己申告するメソッド。
     */
    public static void registerImplementedToolType(ResourceLocation toolTypeId) {
        IMPLEMENTED_TOOL_TYPES.add(toolTypeId);
        ResonantsOcta.LOGGER.debug("Registered implemented tool type: {}", toolTypeId);
    }

    /**
     * 指定されたツールタイプが、現在のゲーム側コードとして実装されているか確認する。
     */
    public static boolean isImplemented(ResourceLocation toolTypeId) {
        return IMPLEMENTED_TOOL_TYPES.contains(toolTypeId);
    }

    /**
     * ロードされている全ツールタイプのリストを取得する。
     */
    public static Set<ResourceLocation> getImplementedToolTypes() {
        return IMPLEMENTED_TOOL_TYPES;
    }
}
