package org.crafttable.resonantsocta.item.base;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.crafttable.resonantsocta.inventory.BoxMenu;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BaseBox extends Item implements IAlchemicalTool {
    public BaseBox(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            int slots = getSlotCount(stack);
            NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                    (containerId, playerInventory, p) -> new BoxMenu(containerId, playerInventory, stack,
                            slots),
                    stack.getHoverName()), buf -> buf.writeInt(slots));
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    protected abstract int getSlotCount(ItemStack stack);

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents,
            TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        AlchemicalToolHelper.appendHoverText(pStack, pTooltipComponents);
    }
}
