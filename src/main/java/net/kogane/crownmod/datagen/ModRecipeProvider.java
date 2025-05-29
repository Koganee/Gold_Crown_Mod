package net.kogane.crownmod.datagen;

import net.kogane.crownmod.block.ModBlocks;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput)
    {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.YELLOW_BRICK_BLOCK.get(), 8)
                .pattern("YYY")
                .pattern("YXY")
                .pattern("YYY")
                .define('X', Items.YELLOW_DYE)
                .define('Y', Blocks.BRICKS)
                .unlockedBy("has_yellow_dye", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.YELLOW_DYE).build()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BLUE_BRICK_BLOCK.get(), 8)
                .pattern("YYY")
                .pattern("YXY")
                .pattern("YYY")
                .define('X', Items.BLUE_DYE)
                .define('Y', Blocks.BRICKS)
                .unlockedBy("has_blue_dye", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.BLUE_DYE).build()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GREEN_BRICK_BLOCK.get(), 8)
                .pattern("YYY")
                .pattern("YXY")
                .pattern("YYY")
                .define('X', Items.GREEN_DYE)
                .define('Y', Blocks.BRICKS)
                .unlockedBy("has_green_dye", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.GREEN_DYE).build()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PINK_BRICK_BLOCK.get(), 8)
                .pattern("YYY")
                .pattern("YXY")
                .pattern("YYY")
                .define('X', Items.PINK_DYE)
                .define('Y', Blocks.BRICKS)
                .unlockedBy("has_pink_dye", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.PINK_DYE).build()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RED_BRICK_BLOCK.get(), 8)
                .pattern("YYY")
                .pattern("YXY")
                .pattern("YYY")
                .define('X', Items.RED_DYE)
                .define('Y', Blocks.BRICKS)
                .unlockedBy("has_red_dye", inventoryTrigger(ItemPredicate.Builder.item().
                        of(Items.RED_DYE).build()))
                .save(pWriter);
    }
}

