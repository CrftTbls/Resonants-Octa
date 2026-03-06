package org.crafttable.resonantsocta.item.base;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ArmorMaterials;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseAlchemicalArmor extends ArmorItem implements IAlchemicalTool {
    public BaseAlchemicalArmor(Type type, Properties properties) {
        super(ArmorMaterials.IRON, type, properties);
    }

    // TODO: 防具の防御力・タフネスの動的AttributeModifierオーバーライドを後日実装。
    
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        AlchemicalToolHelper.appendHoverText(pStack, pTooltipComponents);
    }
}
