package net.kogane.crownmod.datagen.loot;

import net.kogane.crownmod.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        add(ModBlocks.YELLOW_BRICK_BLOCK.get(), block ->
                createSingleItemTable(ModBlocks.YELLOW_BRICK_BLOCK.get().asItem()));
        add(ModBlocks.BLUE_BRICK_BLOCK.get(), block ->
                createSingleItemTable(ModBlocks.BLUE_BRICK_BLOCK.get().asItem()));
        add(ModBlocks.GREEN_BRICK_BLOCK.get(), block ->
                createSingleItemTable(ModBlocks.GREEN_BRICK_BLOCK.get().asItem()));
        add(ModBlocks.PINK_BRICK_BLOCK.get(), block ->
                createSingleItemTable(ModBlocks.PINK_BRICK_BLOCK.get().asItem()));
        add(ModBlocks.RED_BRICK_BLOCK.get(), block ->
                createSingleItemTable(ModBlocks.RED_BRICK_BLOCK.get().asItem()));
    }

    protected Iterable<Block> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    private LootTable.Builder createItemTableWithMultipleItems(Item item, int quantity) {
        // Create a loot table that drops a fixed quantity of the item
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(applyExplosionDecay(item, LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(quantity))))));
    }
}