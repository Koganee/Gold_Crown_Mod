package net.kogane.crownmod.events;

import net.kogane.crownmod.CrownMod;
import net.kogane.crownmod.item.ModItems;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CrownMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        Level world = player.getCommandSenderWorld();
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack helmetItemStack = player.getInventory().armor.get(3);

        if(helmetItemStack.getItem() == ModItems.GOLD_CROWN.get())
        {
            if(mainHandItem.getItem() == Items.GOLDEN_CHESTPLATE && (!mainHandItem.isEnchanted() || mainHandItem.getEnchantmentLevel(Enchantments.UNBREAKING) < 10))
            {
                mainHandItem.enchant(Enchantments.UNBREAKING, 10);
            }
            if(mainHandItem.getItem() == Items.GOLDEN_LEGGINGS && (!mainHandItem.isEnchanted() || mainHandItem.getEnchantmentLevel(Enchantments.UNBREAKING) < 10))
            {
                mainHandItem.enchant(Enchantments.UNBREAKING, 10);
            }
            if(mainHandItem.getItem() == Items.GOLDEN_BOOTS && (!mainHandItem.isEnchanted() || mainHandItem.getEnchantmentLevel(Enchantments.UNBREAKING) < 10))
            {
                mainHandItem.enchant(Enchantments.UNBREAKING, 10);
            }

        }

    }
}
