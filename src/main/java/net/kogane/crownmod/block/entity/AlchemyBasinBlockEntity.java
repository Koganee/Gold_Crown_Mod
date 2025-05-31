package net.kogane.crownmod.block.entity;

import net.kogane.crownmod.recipe.AlchemyBasinRecipe;
import net.kogane.crownmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AlchemyBasinBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int FLUID_ITEM_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    private final FluidTank fluidTank = new FluidTank(4000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }
    };

    private final EnergyStorage energyStorage = new EnergyStorage(10000);
    private int progress = 0;
    private int maxProgress = 78;
    private int energyCost = 100;
    private FluidStack requiredFluid = FluidStack.EMPTY;

    public AlchemyBasinBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ALCHEMY_BASIN_BE.get(), pos, state);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide) return;

        if (hasRecipe()) {
            progress++;
            energyStorage.extractEnergy(energyCost, false);

            if (progress >= maxProgress) {
                craftItem();
                progress = 0;
            }
        } else {
            progress = 0;
        }
    }

    private void craftItem() {
        Optional<AlchemyBasinRecipe> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(getLevel().registryAccess()).copy();
            itemHandler.extractItem(INPUT_SLOT, 1, false);
            fluidTank.drain(recipe.get().getFluidStack().getAmount(), FluidTank.FluidAction.EXECUTE);

            ItemStack output = itemHandler.getStackInSlot(OUTPUT_SLOT);
            if (output.isEmpty()) {
                itemHandler.setStackInSlot(OUTPUT_SLOT, result);
            } else {
                output.grow(result.getCount());
            }
        }
    }

    private boolean hasRecipe() {
        Optional<AlchemyBasinRecipe> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) return false;

        AlchemyBasinRecipe r = recipe.get();
        ItemStack result = r.getResultItem(getLevel().registryAccess());

        if (!canOutput(result)) return false;
        if (energyStorage.getEnergyStored() < r.getEnergyAmount()) return false;
        if (fluidTank.getFluidAmount() < r.getFluidStack().getAmount()) return false;

        maxProgress = r.getCraftTime();
        energyCost = r.getEnergyAmount();
        requiredFluid = r.getFluidStack();
        return true;
    }

    private boolean canOutput(ItemStack result) {
        ItemStack output = itemHandler.getStackInSlot(OUTPUT_SLOT);
        return output.isEmpty() || (output.is(result.getItem()) && output.getCount() + result.getCount() <= output.getMaxStackSize());
    }

    private Optional<AlchemyBasinRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        return level.getRecipeManager().getRecipeFor(AlchemyBasinRecipe.Type.INSTANCE, inventory, level);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        fluidTank.readFromNBT(tag.getCompound("fluid"));
        energyStorage.receiveEnergy(tag.getInt("energy"), false);
        progress = tag.getInt("progress");
        maxProgress = tag.getInt("maxProgress");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.put("fluid", fluidTank.writeToNBT(new CompoundTag()));
        tag.putInt("energy", energyStorage.getEnergyStored());
        tag.putInt("progress", progress);
        tag.putInt("maxProgress", maxProgress);
        super.saveAdditional(tag);
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Alchemy Basin");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new AlchemyBasinMenu(id, inv, this); // You must implement this menu class
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
    }
}
