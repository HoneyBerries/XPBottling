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

/**
 * A lightweight Bukkit plugin that lets players bottle their experience.
 * <p>
 * Core behavior:
 * - When a player right-clicks an enchanting table with a glass bottle (main hand),
 *   consume a fixed amount of experience and give back one experience bottle.
 * - The action requires a specific permission and provides feedback via chat messages and sound.
 * <p>
 * Permissions:
 * - xpbottling.use — required to perform the bottling action.
 * <p>
 * Notes:
 * - The interaction event is canceled to prevent the enchanting table UI from opening.
 * - Experience is removed using the same cost value that is granted to the bottle.
 * - Feedback messages are sent using Adventure Components.
 */
public final class XPBottling extends JavaPlugin implements Listener {

    /** Permission required to use the bottling interaction. */
    private static final String PERMISSION_USE = "xpbottling.use";
    /** Flat experience point cost taken from the player for one bottle. */
    private static final int EXPERIENCE_COST = 7;
    /** Message shown when the player lacks the required permission. */
    private static final Component NO_PERMISSION_MESSAGE = Component.text("You don't have permission to use XP Bottling!").color(NamedTextColor.RED);
    /** Message shown when the player lacks sufficient experience to bottle. */
    private static final Component NOT_ENOUGH_XP_MESSAGE = Component.text("You don't have enough XP to store it in a bottle!").color(NamedTextColor.RED);
    /** Success sound volume. */
    private static final float SOUND_VOLUME = 1.0f;
    /** Success sound pitch. */
    private static final float SOUND_PITCH = 1.25f;

    /**
     * Plugin boot hook.
     * - Registers this class as an event listener.
     * - Emits a log line for server administrators.
     */
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

    /**
     * Handles right-click interactions to perform the bottling logic.
     * <p>
     * Flow:
     * 1) Only proceed for main-hand right-click on a block.
     * 2) Require the clicked block to be an enchanting table.
     * 3) Require the player to be holding a glass bottle in the main hand.
     * 4) Cancel the event to prevent the enchanting interface from opening.
     * 5) Check permission and experience; if valid, process bottling.
     * <p>
     * This handler is intentionally strict to avoid false positives and
     * to keep the action explicit and predictable for players.
     *
     * @param event the interaction event fired by Bukkit
     */
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

    /**
     * Completes the bottling operation for a valid interaction:
     * - Removes experience from the player.
     * - Adds a single experience bottle to the player's inventory.
     * - Plays a short confirmation sound at the player's location.
     * <p>
     * This method assumes all preconditions (permission, target block, held item,
     * and sufficient experience) have already been validated.
     *
     * @param player the player receiving the bottled experience
     */
    private void processBottling(Player player) {
        // Remove the experience cost from the player.
        player.giveExp(-EXPERIENCE_COST);

        // Give the player an experience bottle.
        player.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, 1));

        // Play a sound to indicate success.
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_BOTTLE_FILL_DRAGONBREATH, SOUND_VOLUME, SOUND_PITCH);
    }
}
