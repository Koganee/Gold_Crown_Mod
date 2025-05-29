package net.kogane.crownmod.events;

import net.kogane.crownmod.CrownMod;
import net.kogane.crownmod.particle.ModParticles;
import net.kogane.crownmod.particle.RedstoneCopperParticles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CrownMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {
    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.REDSTONE_COPPER_PARTICLES.get(), RedstoneCopperParticles.Provider::new);
    }


}
