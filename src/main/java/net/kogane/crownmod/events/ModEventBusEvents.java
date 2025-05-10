package net.kogane.crownmod.events;

import net.kogane.crownmod.CrownMod;
import net.kogane.crownmod.entity.ModEntities;
import net.kogane.crownmod.entity.client.GemEssenceFairyModel;
import net.kogane.crownmod.entity.custom.GemEssenceFairyEntity;
import net.kogane.crownmod.entity.layers.ModModelLayers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CrownMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.GEM_ESSENCE_FAIRY_LAYER, GemEssenceFairyModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GEM_ESSENCE_FAIRY.get(), GemEssenceFairyEntity.createAttributes().build());
    }
}
