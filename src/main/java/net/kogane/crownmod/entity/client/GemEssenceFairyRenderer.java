package net.kogane.crownmod.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kogane.crownmod.CrownMod;
import net.kogane.crownmod.entity.custom.GemEssenceFairyEntity;
import net.kogane.crownmod.entity.layers.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GemEssenceFairyRenderer extends MobRenderer<GemEssenceFairyEntity, GemEssenceFairyModel<GemEssenceFairyEntity>> {
    private static final ResourceLocation GEM_ESSENCE_FAIRY_LOCATION = new ResourceLocation(CrownMod.MOD_ID,"textures/entity/gem_essence_fairy.png");

    public GemEssenceFairyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GemEssenceFairyModel<>(pContext.bakeLayer(ModModelLayers.GEM_ESSENCE_FAIRY_LAYER)), 0.85f);
    }

    @Override
    public ResourceLocation getTextureLocation(GemEssenceFairyEntity pEntity) { return GEM_ESSENCE_FAIRY_LOCATION; }

    @Override
    public void render(GemEssenceFairyEntity pEntity, float pEntityYaw, float pPartialTicks,
                       PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pMatrixStack.scale(0.05f, 0.05f, 0.05f);
        }

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
