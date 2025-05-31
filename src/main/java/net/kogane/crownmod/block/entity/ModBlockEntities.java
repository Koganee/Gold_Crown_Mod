package net.kogane.crownmod.block.entity;

import net.kogane.crownmod.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MCCourseMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<AlchemyBasinBlockEntity>> ALCHEMY_BASIN_BE =
            BLOCK_ENTITIES.register("alchemy_basin_block_entity", () ->
                    BlockEntityType.Builder.of(AlchemyBasinBlockEntity::new,
                            ModBlocks.ALCHEMY_BASIN_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}