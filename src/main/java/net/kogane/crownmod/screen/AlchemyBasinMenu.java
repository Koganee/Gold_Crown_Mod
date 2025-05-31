package net.kogane.crownmod.screen;

import net.kogane.crownmod.block.entity.AlchemyBasinBlockEntity;
import net.kogane.crownmod.screen.slot.ModResultSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class AlchemyBasinMenu extends AbstractContainerMenu {
    private final AlchemyBasinBlockEntity blockEntity;
    private final Level level;

    public AlchemyBasinMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id, playerInventory, (AlchemyBasinBlockEntity) playerInventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public AlchemyBasinMenu(int id, Inventory playerInventory, AlchemyBasinBlockEntity blockEntity) {
        super(ModMenuTypes.ALCHEMY_BASIN_MENU.get(), id);
        this.blockEntity = blockEntity;
        this.level = playerInventory.player.level();

        IItemHandler handler = blockEntity.getCapability(net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(new ItemStackHandler(3));

        // BlockEntity Inventory Slots
        this.addSlot(new SlotItemHandler(handler, 0, 44, 35));  // Input slot (e.g., gold ingot)
        this.addSlot(new SlotItemHandler(handler, 1, 44, 59));  // Fluid item slot (e.g., water bottle)
        this.addSlot(new ModResultSlot(handler, 2, 116, 35));   // Output slot (copper ingot)

        // Player Inventory
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack currentStack = slot.getItem();
            itemstack = currentStack.copy();

            if (index < 3) {
                if (!this.moveItemStackTo(currentStack, 3, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(currentStack, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (currentStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return ContainerLevelAccess.create(level, blockEntity.getBlockPos())
                .evaluate((lvl, pos) -> lvl.getBlockEntity(pos) instanceof AlchemyBasinBlockEntity &&
                        player.distanceToSqr(pos.getCenter()) < 64, true);
    }
}
