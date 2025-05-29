package net.kogane.crownmod.datagen;

import net.kogane.crownmod.CrownMod;
import net.kogane.crownmod.block.ModBlocks;
import net.kogane.crownmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CrownMod.MOD_ID, existingFileHelper);
    }
    protected void registerModels() {
        simpleBlockItem(ModBlocks.YELLOW_BRICK_BLOCK);
        simpleBlockItem(ModBlocks.BLUE_BRICK_BLOCK);
        simpleBlockItem(ModBlocks.GREEN_BRICK_BLOCK);
        simpleBlockItem(ModBlocks.PINK_BRICK_BLOCK);
        simpleBlockItem(ModBlocks.RED_BRICK_BLOCK);

        simpleItem(ModItems.COPPER_HELMET);
        simpleItem(ModItems.COPPER_CHESTPLATE);
        simpleItem(ModItems.COPPER_LEGGINGS);
        simpleItem(ModItems.COPPER_BOOTS);
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(CrownMod.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(CrownMod.MOD_ID, "item/" + item.getId().getPath()));
    }
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(CrownMod.MOD_ID,"item/" + item.getId().getPath()));
    }
}
