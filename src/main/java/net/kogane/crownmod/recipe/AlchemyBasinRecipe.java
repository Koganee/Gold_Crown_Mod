package net.kogane.crownmod.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import net.kogane.crownmod.CrownMod;

public class AlchemyBasinRecipe implements Recipe<Container> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<ItemStack> ingredients;
    private final FluidStack fluid;
    private final int energyAmount;
    private final int craftTime;

    public AlchemyBasinRecipe(ResourceLocation id, ItemStack output, NonNullList<ItemStack> ingredients, FluidStack fluid, int energyAmount, int craftTime) {
        this.id = id;
        this.output = output;
        this.ingredients = ingredients;
        this.fluid = fluid;
        this.energyAmount = energyAmount;
        this.craftTime = craftTime;
    }

    public FluidStack getFluidStack() {
        return fluid;
    }

    public int getEnergyAmount() {
        return energyAmount;
    }

    public int getCraftTime() {
        return craftTime;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return ItemStack.isSameItemSameTags(ingredients.get(0), container.getItem(0));
    }

    @Override
    public ItemStack assemble(Container container) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AlchemyBasinRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "alchemy_basin";
    }

    public static class Serializer implements RecipeSerializer<AlchemyBasinRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(CrownMod.MOD_ID, "alchemy_basin");

        @Override
        public AlchemyBasinRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            JsonArray ingredientsArray = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<ItemStack> ingredients = NonNullList.withSize(1, ItemStack.EMPTY);
            ingredients.set(0, ShapedRecipe.itemStackFromJson(ingredientsArray.get(0).getAsJsonObject()));

            JsonObject fluidObj = GsonHelper.getAsJsonObject(json, "fluid");
            String fluidName = GsonHelper.getAsString(fluidObj, "fluid");
            int fluidAmount = GsonHelper.getAsInt(fluidObj, "amount");

            FluidStack fluid = new FluidStack(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName)), fluidAmount);

            int energy = GsonHelper.getAsInt(json, "energy", 100);
            int craftTime = GsonHelper.getAsInt(json, "craft_time", 78);

            return new AlchemyBasinRecipe(id, output, ingredients, fluid, energy, craftTime);
        }

        @Override
        public AlchemyBasinRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<ItemStack> ingredients = NonNullList.withSize(1, ItemStack.EMPTY);
            ingredients.set(0, buf.readItem());

            ItemStack output = buf.readItem();
            FluidStack fluid = FluidStack.readFromPacket(buf);
            int energy = buf.readInt();
            int craftTime = buf.readInt();

            return new AlchemyBasinRecipe(id, output, ingredients, fluid, energy, craftTime);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AlchemyBasinRecipe recipe) {
            buf.writeItem(recipe.ingredients.get(0));
            buf.writeItem(recipe.output);
            recipe.fluid.writeToPacket(buf);
            buf.writeInt(recipe.energyAmount);
            buf.writeInt(recipe.craftTime);
        }
    }
}
