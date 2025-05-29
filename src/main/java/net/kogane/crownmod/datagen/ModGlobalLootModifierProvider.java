package net.kogane.crownmod.datagen;

import net.kogane.crownmod.CrownMod;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output) {
        super(output, CrownMod.MOD_ID);
    }

    @Override
    protected void start() {
    }
}
