package me.honeyberries.xpbottling;

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

    private static final String PERMISSION_USE = "xpbottling.use";
    private static final int EXPERIENCE_COST = 7;
    private static final Component NO_PERMISSION_MESSAGE = Component.text("You don't have permission to use XP Bottling!").color(NamedTextColor.RED);
    private static final Component NOT_ENOUGH_XP_MESSAGE = Component.text("You don't have enough XP to store it in a bottle!").color(NamedTextColor.RED);
    private static final float SOUND_VOLUME = 1.0f;
    private static final float SOUND_PITCH = 1.25f;

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
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() != Material.ENCHANTING_TABLE) {
            return;
        }

        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() != Material.GLASS_BOTTLE) {
            return;
        }

        // At this point, we know the player is right-clicking an enchanting table with a glass bottle.
        // We should handle this, so we cancel the event to prevent the GUI from opening.
        event.setCancelled(true);

        if (!player.hasPermission(PERMISSION_USE)) {
            player.sendMessage(NO_PERMISSION_MESSAGE);
            return;
        }

        // Check if the player has enough experience.
        // The user requested to keep this logic as is.
        if (player.getTotalExperience() < EXPERIENCE_COST && player.getLevel() < 1) {
            player.sendMessage(NOT_ENOUGH_XP_MESSAGE);
            return;
        }

        processBottling(player);
    }

    private void processBottling(Player player) {
        // Remove the experience cost from the player.
        player.giveExp(-EXPERIENCE_COST);

        // Give the player an experience bottle.
        player.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, 1));

        // Play a sound to indicate success.
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, SOUND_VOLUME, SOUND_PITCH);
    }
}
