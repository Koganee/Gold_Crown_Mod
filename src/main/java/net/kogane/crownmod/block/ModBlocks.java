package net.kogane.crownmod.block;

import net.kogane.crownmod.CrownMod;
import net.kogane.crownmod.item.ModItems;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.kogane.crownmod.block.entity.AlchemyBasinBlock;

import java.util.function.Supplier;

public abstract class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CrownMod.MOD_ID);

    public static final RegistryObject<Block> YELLOW_BRICK_BLOCK = registerBlock("yellow_brick_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).sound(SoundType.STONE)));
    public static final RegistryObject<Block> BLUE_BRICK_BLOCK = registerBlock("blue_brick_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GREEN_BRICK_BLOCK = registerBlock("green_brick_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).sound(SoundType.STONE)));
    public static final RegistryObject<Block> PINK_BRICK_BLOCK = registerBlock("pink_brick_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).sound(SoundType.STONE)));
    public static final RegistryObject<Block> RED_BRICK_BLOCK = registerBlock("red_brick_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS).sound(SoundType.STONE)));

    public static final RegistryObject<Block> ALCHEMY_BASIN_BLOCK = registerBlock("alchemy_basin_block",
            () -> new AlchemyBasinBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;

    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
    {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }


    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
