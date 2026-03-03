package org.crafttable.resonantsocta.item.base;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.crafttable.resonantsocta.data.alchemical.AlchemicalMatterDefinition;
import org.crafttable.resonantsocta.data.alchemical.AlchemicalMatterManager;
import org.crafttable.resonantsocta.item.nbt.AlchemicalDataHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public abstract class BaseAlchemicalMatter extends Item {
    public BaseAlchemicalMatter(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        ResourceLocation matterId = AlchemicalDataHandler.getMatterId(stack);
        AlchemicalMatterDefinition def = AlchemicalMatterManager.get(matterId);

        if (def == null) {
            tooltip.add(Component.translatable("tooltip.resonant_octa.unknown_matter").withStyle(ChatFormatting.GRAY));
            return;
        }

        ResourceLocation repItem = def.getRepresentativeItem();
        if (repItem != null && !repItem.getPath().equals("air")) {
            tooltip.add(Component.translatable("tooltip.resonant_octa.source").append(": ")
                    .withStyle(ChatFormatting.DARK_GREEN)
                    .append(Component.translatable(net.minecraft.Util.makeDescriptionId("item", repItem))
                            .withStyle(ChatFormatting.WHITE)));
        }

        Map<String, Float> stats = def.getStats();
        if (stats != null && !stats.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.resonant_octa.stats").withStyle(ChatFormatting.GOLD));
            stats.forEach((key, value) -> {
                String translatedKey = "stat.resonant_octa." + key;
                tooltip.add(Component.literal(" - ").append(Component.translatable(translatedKey)).append(": " + value)
                        .withStyle(ChatFormatting.GRAY));
            });
        }

        List<String> traits = def.getTraits();
        if (traits != null && !traits.isEmpty()) {
            tooltip.add(Component.translatable("tooltip.resonant_octa.traits").withStyle(ChatFormatting.AQUA));
            traits.forEach(trait -> {
                tooltip.add(Component.literal(" * ").append(Component.translatable("trait." + trait.replace(":", ".")))
                        .withStyle(ChatFormatting.DARK_AQUA));
            });
        }
    }
}
