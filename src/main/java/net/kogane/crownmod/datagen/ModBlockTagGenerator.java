package net.kogane.crownmod.datagen;

import net.kogane.crownmod.CrownMod;
import net.kogane.crownmod.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CrownMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.YELLOW_BRICK_BLOCK.get())
                .add(ModBlocks.BLUE_BRICK_BLOCK.get())
                .add(ModBlocks.GREEN_BRICK_BLOCK.get())
                .add(ModBlocks.PINK_BRICK_BLOCK.get())
                .add(ModBlocks.RED_BRICK_BLOCK.get());

    }

    @Override
    public String getName() {
        return "Block Tags";
    }
}
