package net.kogane.crownmod.block.entity;

import net.kogane.crownmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.property.Properties;
import org.jetbrains.annotations.Nullable;



public class AlchemyBasinBlock extends BaseEntityBlock {
    private final SimpleContainer inventory = new SimpleContainer(1); // 1-slot inventory for example

    public AlchemyBasinBlock(Properties pProperties) {
        super(pProperties);
    }


    public boolean addItem(ItemStack stack) {
        if (inventory.getItem(0).isEmpty()) {
            inventory.setItem(0, stack.copyWithCount(1));
            stack.shrink(1);
            return true;
        }
        return false;
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AlchemyBasinBlockEntity(pPos, pState);
    }
}
