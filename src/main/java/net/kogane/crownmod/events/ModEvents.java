package net.kogane.crownmod.events;

import net.kogane.crownmod.CrownMod;
import net.kogane.crownmod.entity.ModEntities;
import net.kogane.crownmod.entity.custom.GemEssenceFairyEntity;
import net.kogane.crownmod.entity.custom.GoldenFairyEntity;
import net.kogane.crownmod.item.ModItems;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = CrownMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

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
            player.heal(8.0f); // Heal 4 hearts
        }
    }

    /*
    @SubscribeEvent
    public static void onPlayerAttackEvent(LivingHurtEvent event) {
        // Get the attacker
        Entity source = event.getSource().getEntity();

        if (source instanceof Player player) {
            player.resetAttackStrengthTicker();
            player.swinging = false;
        }
    }
    */
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GEM_ESSENCE_FAIRY.get(), GemEssenceFairyEntity.createAttributes().build());
        event.put(ModEntities.GOLDEN_FAIRY.get(), GoldenFairyEntity.createAttributes().build());
    }

}
