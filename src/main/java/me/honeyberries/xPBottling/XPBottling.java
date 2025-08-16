package me.honeyberries.xPBottling;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.EquipmentSlot;

public final class XPBottling extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register events
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("XP Bottling has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("XP Bottling has been disabled!");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if it's a right-click and a block
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.ENCHANTING_TABLE) {
            return;
        }

        Player player = event.getPlayer();

        // Check permission
        if (!player.hasPermission("xpbottling.use")) {
            player.sendMessage(Component.text("You don't have permission to use XP Bottling!")
                    .color(NamedTextColor.RED));
            return;
        }

        // Check if player has a glass bottle in hand
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        if (heldItem.getType() != Material.GLASS_BOTTLE) {
            return;
        }

        // Check if player has enough XP
        int playerExp = player.getTotalExperience();
        if (playerExp < 7 && player.getLevel() < 1) {
            player.sendMessage(Component.text("You don't have enough XP to store it in a bottle!")
                    .color(NamedTextColor.RED));
            event.setCancelled(true);
            return;
        }

        // Process the bottling
        processBottling(player, heldItem);
        event.setCancelled(true);
    }

    private void processBottling(Player player, ItemStack bottle) {
        // Remove 12 XP points
        player.giveExp(-7);
        // Consume the bottle
        if (bottle.getAmount() > 1) {
            bottle.setAmount(bottle.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }

        // Give exp bottle
        player.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, 1));

        // Play sound
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.0f, 1.25f);
    }
}
