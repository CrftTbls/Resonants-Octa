package org.crafttable.resonantsocta.item.base;

import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.crafttable.resonantsocta.data.blueprint.BlueprintDefinition;
import org.crafttable.resonantsocta.data.blueprint.BlueprintManager;
import org.crafttable.resonantsocta.data.blueprint.ToolTypeDefinition;
import org.crafttable.resonantsocta.data.blueprint.ToolTypeManager;
import org.crafttable.resonantsocta.item.nbt.AlchemicalToolDataHandler;

import java.util.List;
import java.util.UUID;

/**
 * 各種バニラアイテム（SwordItem, AxeItem等）を継承したベース錬金ツールクラスに対し、
 * 共通のステータス計算・ツールチップ描画ロジックを提供するヘルパークラス。
 */
public class AlchemicalToolHelper {

    // 攻撃力と攻撃速度のモディファイア用UUID（バニラ準拠で固定割り当て）
    public static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    public static final UUID BASE_ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    /**
     * NBTに基づいて動的ステータス（攻撃力・攻撃速度）を計算し、Modifierマップに追加して返す
     * 
     * @param slot                装備スロット
     * @param stack               対象のアイテムスタック
     * @param originalModifiers   バニラ定義などの元のModifier（今回はNBT基準で上書きするためベースを使用）
     * @param defaultAttackDamage 万が一NBTがない場合のデフォルト攻撃力
     * @param defaultAttackSpeed  万が一NBTがない場合のデフォルト攻撃速度
     */
    public static Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack,
            Multimap<Attribute, AttributeModifier> originalModifiers, double defaultAttackDamage,
            double defaultAttackSpeed) {

        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

            double attackDamage = 0.0;
            double attackSpeed = 0.0;

            ResourceLocation blueprintId = AlchemicalToolDataHandler.getBlueprintId(stack);
            if (blueprintId != null) {
                BlueprintDefinition blueprint = BlueprintManager.get(blueprintId);
                if (blueprint != null) {
                    ResourceLocation toolTypeId = blueprint.getToolType();
                    ToolTypeDefinition typeDef = ToolTypeManager.get(toolTypeId);

                    if (typeDef != null && typeDef.getBaseStats() != null) {
                        float baseAtk = typeDef.getBaseStats().getOrDefault("atk", 1.0f);
                        float baseSpd = typeDef.getBaseStats().getOrDefault("spd", 1.0f);
                        float multiAtk = blueprint.getStatMultipliers().getOrDefault("atk", 1.0f);

                        attackDamage = baseAtk * multiAtk;
                        // 攻撃速度の補正式（バニラは 4.0 が基準。baseSpd は振りの速さ）
                        attackSpeed = -(4.0 - baseSpd);
                    }
                }
            }

            if (attackDamage == 0)
                attackDamage = defaultAttackDamage;
            if (attackSpeed == 0)
                attackSpeed = defaultAttackSpeed;

            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier",
                    attackDamage, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier",
                    attackSpeed, AttributeModifier.Operation.ADDITION));

            return builder.build();
        }
        return originalModifiers;
    }

    /**
     * 錬金ツール共通のツールチップ（青写真ID、ツールタイプ、特性）を描画する。
     */
    public static void appendHoverText(ItemStack pStack, List<Component> pTooltipComponents) {
        ResourceLocation blueprintId = AlchemicalToolDataHandler.getBlueprintId(pStack);
        if (blueprintId != null) {
            pTooltipComponents.add(Component.translatable("tooltip.resonants_octa.blueprint_id")
                    .append(": ").append(blueprintId.toString()).withStyle(ChatFormatting.DARK_GRAY));

            BlueprintDefinition blueprint = BlueprintManager.get(blueprintId);
            if (blueprint != null) {
                pTooltipComponents.add(Component.translatable("tooltip.resonants_octa.tool_type")
                        .append(": ").append(blueprint.getToolType().toString()).withStyle(ChatFormatting.GOLD));
            }
        }

        List<ResourceLocation> traits = AlchemicalToolDataHandler.getTraits(pStack);
        if (!traits.isEmpty()) {
            pTooltipComponents
                    .add(Component.translatable("tooltip.resonants_octa.traits").withStyle(ChatFormatting.DARK_AQUA));
            for (ResourceLocation trait : traits) {
                pTooltipComponents.add(Component.literal(" - ")
                        .append(Component.translatable(trait.toLanguageKey("trait"))).withStyle(ChatFormatting.AQUA));
            }
        }
    }
}
