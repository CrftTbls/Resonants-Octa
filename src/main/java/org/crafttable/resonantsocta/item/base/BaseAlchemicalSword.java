package org.crafttable.resonantsocta.item.base;

import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 錬金ツール（剣類）のベースクラス。
 * バニラのSwordItemの固有機能（なぎ払い、蜘蛛の巣破壊等）を継承しつつ、
 * 錬金ツールのステータス計算・ツールチップ描画をAlchemicalToolHelperへ委譲する。
 */
public abstract class BaseAlchemicalSword extends SwordItem implements IAlchemicalTool {

    public BaseAlchemicalSword(Properties properties) {
        // Tiersや基本攻撃力はダミー。実際のステータスはNBTに基づきHelper内で上書きされる。
        super(Tiers.IRON, 3, -2.4F, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        // デフォルト値として鉄の剣相当を渡す（NBT欠落時用）
        return AlchemicalToolHelper.getAttributeModifiers(slot, stack, super.getAttributeModifiers(slot, stack), 6.0,
                -2.4);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents,
            TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        AlchemicalToolHelper.appendHoverText(pStack, pTooltipComponents);
    }
}
