package org.crafttable.resonantsocta.item.base;

import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseAlchemicalShovel extends ShovelItem implements IAlchemicalTool {
    public BaseAlchemicalShovel(Properties properties) {
        super(Tiers.IRON, 1.5F, -3.0F, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return AlchemicalToolHelper.getAttributeModifiers(slot, stack, super.getAttributeModifiers(slot, stack), 4.0, -2.4);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        AlchemicalToolHelper.appendHoverText(pStack, pTooltipComponents);
    }
}
