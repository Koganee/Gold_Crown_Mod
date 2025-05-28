package net.kogane.crownmod.entity;

import net.kogane.crownmod.CrownMod;
import net.kogane.crownmod.entity.custom.GemEssenceFairyEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CrownMod.MOD_ID);

    public static final RegistryObject<EntityType<GemEssenceFairyEntity>> GEM_ESSENCE_FAIRY =
            ENTITY_TYPES.register("gem_essence_fairy", () -> EntityType.Builder.of(GemEssenceFairyEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F).build("gem_essence_fairy"));
    public static final RegistryObject<EntityType<GemEssenceFairyEntity>> GOLDEN_FAIRY =
            ENTITY_TYPES.register("golden_fairy", () -> EntityType.Builder.of(GemEssenceFairyEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F).build("golden_fairy"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}