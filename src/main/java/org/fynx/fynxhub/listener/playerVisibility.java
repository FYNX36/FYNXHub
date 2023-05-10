package org.fynx.fynxhub.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.fynx.fynxhub.FYNXHub;

public class playerVisibility implements Listener {

    private FYNXHub plugin;

    public playerVisibility(FYNXHub plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() == null) return;
            if (event.getItem().getType() == Material.LIME_DYE) {
                Player player = event.getPlayer();
                ItemStack dye = new ItemStack(Material.GRAY_DYE);
                ItemMeta dyeMeta = dye.getItemMeta();
                dyeMeta.setDisplayName("§fPlayer: §4Hidden §7(Right Click)");
                dye.setItemMeta(dyeMeta);
                player.getInventory().setItem(7, dye);
                togglePlayerVisibility(player, false);

            }
            if (event.getItem().getType() == Material.GRAY_DYE) {
                Player player = event.getPlayer();
                ItemStack dye = new ItemStack(Material.LIME_DYE);
                ItemMeta dyeMeta = dye.getItemMeta();
                dyeMeta.setDisplayName("§fPlayer: §aVisible §7(Right Click)");
                dye.setItemMeta(dyeMeta);
                player.getInventory().setItem(7, dye);
                togglePlayerVisibility(player, true);
            }
        }
    }
    private void togglePlayerVisibility(Player player, boolean hidePlayers) {
        boolean playersVisible = player.hasMetadata("playersVisible") ? player.getMetadata("playersVisible").get(0).asBoolean() : true;
        if (playersVisible != hidePlayers) {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                player.getWorld().getPlayers().stream()
                        .filter(p -> !p.equals(player))
                        .forEach(p -> {
                            if (hidePlayers) {
                                player.hidePlayer(this.plugin, p); // Hide player
                            } else {
                                player.showPlayer(this.plugin, p); // Show player
                            }
                        });
            });
            player.setMetadata("playersVisible", new FixedMetadataValue(this.plugin, !hidePlayers));
        }
    }
}
