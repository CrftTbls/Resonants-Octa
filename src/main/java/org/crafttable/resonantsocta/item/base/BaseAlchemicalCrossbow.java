package org.crafttable.resonantsocta.item.base;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseAlchemicalCrossbow extends CrossbowItem implements IAlchemicalTool {
    public BaseAlchemicalCrossbow(Properties properties) {
        super(properties);
    }

    // TODO: 動的ステータスのAttributeModifierオーバーライドを後日実装。
    
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        AlchemicalToolHelper.appendHoverText(pStack, pTooltipComponents);
    }
}
