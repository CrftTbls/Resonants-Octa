package org.crafttable.resonantsocta.item.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.crafttable.resonantsocta.item.base.BaseBox;

public class ToolBox extends BaseBox {
    public ToolBox(Item.Properties properties) {
        super(properties);
    }

    @Override
    protected int getSlotCount(net.minecraft.world.item.ItemStack stack) {
        return 9;
    }

    // TODO: ToolBox の固有挙動（特殊能力等のアクション）を後日実装。現在は親クラスの基本動作のみ。
}
