package org.crafttable.resonantsocta.inventory;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.crafttable.resonantsocta.register.MenuRegister;

/**
 * ツールボックスおよびアーマーボックスのインベントリを管理するメニュー。
 * スロット数は可変。
 */
public class BoxMenu extends AbstractContainerMenu {
    private final ItemStack boxStack;
    private final ItemStackHandler inventory;
    public final int boxSlotCount;

    // クライアントサイド用コンストラクタ
    public BoxMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, playerInventory.getSelected(), extraData.readInt());
    }

    // サーバーサイド・共通コンストラクタ
    public BoxMenu(int containerId, Inventory playerInventory, ItemStack boxStack, int boxSlotCount) {
        super(MenuRegister.MENUS.get("box").get(), containerId);
        this.boxStack = boxStack;
        this.boxSlotCount = boxSlotCount;
        this.inventory = new ItemStackHandler(boxSlotCount);

        // NBTからインベントリを復元
        if (boxStack.hasTag() && boxStack.getTag().contains("Inventory")) {
            inventory.deserializeNBT(boxStack.getTag().getCompound("Inventory"));
        }

        addBoxSlots();
        addPlayerSlots(playerInventory);
    }

    private void addBoxSlots() {
        int columns = 9;
        int rows = (int) Math.ceil((double) boxSlotCount / columns);

        for (int i = 0; i < boxSlotCount; i++) {
            int row = i / columns;
            int col = i % columns;
            this.addSlot(new SlotItemHandler(inventory, i, 8 + col * 18, 18 + row * 18));
        }
    }

    private void addPlayerSlots(Inventory playerInventory) {
        // プレイヤーのインベントリ
        int boxRows = (int) Math.ceil((double) boxSlotCount / 9);
        int startY = 32 + boxRows * 18;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, startY + row * 18));
            }
        }

        // ホットバー
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, startY + 58));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < boxSlotCount) {
                if (!this.moveItemStackTo(itemstack1, boxSlotCount, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, boxSlotCount, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return !boxStack.isEmpty() && player.getMainHandItem() == boxStack || player.getOffhandItem() == boxStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        // インベントリをNBTに保存
        if (!player.level().isClientSide) {
            boxStack.getOrCreateTag().put("Inventory", inventory.serializeNBT());
        }
    }
}
