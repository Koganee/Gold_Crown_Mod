package net.kogane.crownmod.screen;

import net.kogane.crownmod.CrownMod;
import net.kogane.crownmod.block.entity.AlchemyBasinBlockEntity;
import net.kogane.crownmod.screen.AlchemyBasinMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, CrownMod.MOD_ID);

    public static final RegistryObject<MenuType<AlchemyBasinMenu>> ALCHEMY_BASIN_MENU =
            MENU_TYPES.register("alchemy_basin_menu",
                    () -> new MenuType<>(AlchemyBasinMenu::new));
}
