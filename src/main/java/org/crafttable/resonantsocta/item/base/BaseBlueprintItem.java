package org.crafttable.resonantsocta.item.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.crafttable.resonantsocta.data.blueprint.BlueprintDefinition;
import org.crafttable.resonantsocta.data.blueprint.BlueprintManager;
import org.crafttable.resonantsocta.data.blueprint.BlueprintSlotDefinition;
import org.crafttable.resonantsocta.item.nbt.BlueprintDataHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public abstract class BaseBlueprintItem extends Item {
    public BaseBlueprintItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        ResourceLocation blueprintId = BlueprintDataHandler.getBlueprintId(stack);
        if (blueprintId == null) {
            tooltip.add(
                    Component.translatable("tooltip.resonant_octa.unknown_blueprint").withStyle(ChatFormatting.GRAY));
            return;
        }

        BlueprintDefinition def = BlueprintManager.get(blueprintId);
        if (def == null) {
            tooltip.add(
                    Component.translatable("tooltip.resonant_octa.invalid_blueprint").withStyle(ChatFormatting.RED));
            return;
        }

        // 基本情報表示
        ResourceLocation baseTool = def.getToolType();
        if (baseTool != null) {
            tooltip.add(Component.translatable("tooltip.resonant_octa.tool_type").append(": ")
                    .withStyle(ChatFormatting.DARK_GREEN)
                    .append(Component.translatable(net.minecraft.Util.makeDescriptionId("item", baseTool))
                            .withStyle(ChatFormatting.WHITE)));
        }

        // ステータス乗数
        Map<String, Float> stats = def.getStatMultipliers();
        if (stats != null && !stats.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.resonant_octa.stat_multiplier_overall")
                    .withStyle(ChatFormatting.GOLD));
            stats.forEach((key, value) -> {
                String translatedKey = "stat.resonant_octa." + key;
                tooltip.add(Component.literal(" - ").append(Component.translatable(translatedKey)).append(": x" + value)
                        .withStyle(ChatFormatting.GRAY));
            });
        }

        // 要求仕様スロット
        List<BlueprintSlotDefinition> slots = def.getSlots();
        if (slots != null && !slots.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.resonant_octa.blueprint_slots").withStyle(ChatFormatting.AQUA));
            slots.forEach(slot -> {
                Component slotName = Component.translatable(slot.getSlotName());
                String reqStr = slot.isRequired() ? "[Req]" : "[Opt]";
                ChatFormatting color = slot.isRequired() ? ChatFormatting.YELLOW : ChatFormatting.GRAY;
                tooltip.add(Component.literal(" * " + reqStr + " ").withStyle(color).append(slotName)
                        .append(Component.literal(" (" + slot.getMinCount() + "-" + slot.getMaxCount() + ")")
                                .withStyle(ChatFormatting.DARK_GRAY)));

                // スロットごとの乗数も一部表示（簡略化：全て0でなければ「影響あり」等）
                Map<String, Float> slotStats = slot.getStatMultipliers();
                if (slotStats != null) {
                    boolean hasEffect = slotStats.values().stream().anyMatch(v -> v > 0);
                    if (hasEffect) {
                        tooltip.add(Component.literal("   -> ").withStyle(ChatFormatting.DARK_GREEN)
                                .append(Component.translatable("tooltip.resonant_octa.affects_stats")));
                    }
                }
            });
        }
    }
}
