package net.kogane.crownmod.events;

import net.kogane.crownmod.CrownMod;
import net.kogane.crownmod.particle.ModParticles;
import net.kogane.crownmod.particle.RedstoneCopperParticles;
import net.kogane.crownmod.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CrownMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {
    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.REDSTONE_COPPER_PARTICLES.get(), RedstoneCopperParticles.Provider::new);
    }
    public static void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.ALCHEMY_BASIN_MENU.get(), AlchemyBasinScreen::new);
        });
    }

}
