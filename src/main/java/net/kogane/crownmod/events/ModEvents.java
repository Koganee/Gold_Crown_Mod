package net.kogane.crownmod.events;

import net.kogane.crownmod.CrownMod;
import net.kogane.crownmod.entity.ModEntities;
import net.kogane.crownmod.entity.custom.GemEssenceFairyEntity;
import net.kogane.crownmod.entity.custom.GoldenFairyEntity;
import net.kogane.crownmod.item.ModArmorMaterials;
import net.kogane.crownmod.item.ModItems;
import net.kogane.crownmod.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CrownMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    static boolean hasChargedEffect = false;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        Level world = player.getCommandSenderWorld();
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack helmetItemStack = player.getInventory().armor.get(3);

        if(helmetItemStack.getItem() == ModItems.GOLD_CROWN.get()) {
            if (mainHandItem.getItem() == Items.GOLDEN_CHESTPLATE && (!mainHandItem.isEnchanted() || mainHandItem.getEnchantmentLevel(Enchantments.UNBREAKING) < 10)) {
                mainHandItem.enchant(Enchantments.UNBREAKING, 10);
            }
            if (mainHandItem.getItem() == Items.GOLDEN_LEGGINGS && (!mainHandItem.isEnchanted() || mainHandItem.getEnchantmentLevel(Enchantments.UNBREAKING) < 10)) {
                mainHandItem.enchant(Enchantments.UNBREAKING, 10);
            }
            if (mainHandItem.getItem() == Items.GOLDEN_BOOTS && (!mainHandItem.isEnchanted() || mainHandItem.getEnchantmentLevel(Enchantments.UNBREAKING) < 10)) {
                mainHandItem.enchant(Enchantments.UNBREAKING, 10);
            }
        }
        if (event.player.isSprinting()) {
            event.player.setSprinting(false);
        }
        player.getFoodData().setFoodLevel(6); // Set food level to 20 (10 hunger points)


        if (!world.isClientSide) {
            BlockPos posBelow = player.blockPosition().below();
            if (hasChargedEffect == true) {
                activateChargedEffect(player, world);
            } else if (world.getBlockState(posBelow).is(Blocks.REDSTONE_BLOCK) && !hasChargedEffect) {
                boolean wearingCopper = false;

                for (ItemStack armorPiece : player.getArmorSlots()) {
                    if (!armorPiece.isEmpty() && armorPiece.getItem() instanceof ArmorItem armorItem) {
                        if (armorItem.getMaterial() == ModArmorMaterials.COPPER_ARMOR) {
                            wearingCopper = true;
                            break;
                        }
                    }
                }

                if (wearingCopper) {
                    activateChargedEffect(player, world);
                    hasChargedEffect = true;
                }
            }
        }

        ItemStack head = player.getInventory().armor.get(3); // Helmet
        ItemStack chest = player.getInventory().armor.get(2); // Chestplate
        ItemStack legs = player.getInventory().armor.get(1); // Leggings
        ItemStack boots = player.getInventory().armor.get(0); // Boots

        // Check if all are copper armor
        boolean isFullCopper =
                head.getItem() == ModItems.COPPER_HELMET.get() &&
                        chest.getItem() == ModItems.COPPER_CHESTPLATE.get() &&
                        legs.getItem() == ModItems.COPPER_LEGGINGS.get() &&
                        boots.getItem() == ModItems.COPPER_BOOTS.get();

        if (!isFullCopper) {
            hasChargedEffect = false;
        }
    }

    @SubscribeEvent
    public static void onUseBone(PlayerInteractEvent.EntityInteractSpecific event)
    {
        Player player = event.getEntity();
        Level world = player.getCommandSenderWorld();
        Entity targetEntity = event.getTarget();
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack helmetItemStack = player.getInventory().armor.get(3);

        if (targetEntity instanceof Wolf) {
            Wolf wolf = (Wolf) targetEntity;
            if(mainHandItem.getItem() == Items.BONE && helmetItemStack.getItem() == ModItems.GOLD_CROWN.get())
            {
                wolf.setTame(true);
                wolf.setOwnerUUID(player.getUUID());
            }
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay().id().equals(VanillaGuiOverlay.FOOD_LEVEL.id())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerEatEvent(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player player && event.getItem().getItem().isEdible()) {
            Item item = event.getItem().getItem();

            // Check if it's a cooked food item
            if (item == Items.COOKED_BEEF ||
                    item == Items.COOKED_CHICKEN ||
                    item == Items.COOKED_MUTTON ||
                    item == Items.COOKED_PORKCHOP ||
                    item == Items.COOKED_RABBIT ||
                    item == Items.COOKED_COD ||
                    item == Items.COOKED_SALMON ||
                    item == Items.BAKED_POTATO) {

                player.heal(8.0f); // Heal 4 hearts
            }
            else
            {
                player.heal(2.0f); //Heal 1 heart
            }
        }
    }


    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        // Get the attacker
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) entity;
            LivingEntity attacker = event.getSource().getEntity() instanceof LivingEntity ? (LivingEntity) event.getSource().getEntity() : null;

            if (hasChargedEffect == true) {
                attacker.hurt(attacker.damageSources().cactus(), 1.0f);
            }
        }
    }

    @SubscribeEvent
    public static void onPhantomSpawn(MobSpawnEvent event) {
        if (event.getEntity() instanceof Phantom) {
            event.setResult(Event.Result.DENY);
        }
    }
    @SubscribeEvent
    public static void onPiglinSpawn(MobSpawnEvent event) {
        if (event.getEntity() instanceof Piglin) {
            event.setResult(Event.Result.DENY);
        }
        if (event.getEntity() instanceof PiglinBrute) {
            event.setResult(Event.Result.DENY);
        }
        if (event.getEntity() instanceof Hoglin) {
            event.setResult(Event.Result.DENY);
        }
    }

    public static void activateChargedEffect(Player player, Level world)
    {
        System.out.println("Test 1");
        ServerLevel level = (ServerLevel) world;
        if (!world.isClientSide)
        {
            for(int i = 0; i < 1; i++)
            {
                System.out.println("Test 2");
                level.sendParticles(ModParticles.REDSTONE_COPPER_PARTICLES.get(),
                        player.getX(), player.getY(), player.getZ(), 1,
                        Math.cos(i * 18) * 0.15d, 0.15d, Math.sin(i * 18) * 0.15d, 0.1);
            }
        }
    }


    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GEM_ESSENCE_FAIRY.get(), GemEssenceFairyEntity.createAttributes().build());
        event.put(ModEntities.GOLDEN_FAIRY.get(), GoldenFairyEntity.createAttributes().build());
    }

}
