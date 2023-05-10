package org.fynx.fynxhub.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.fynx.fynxhub.FYNXHub;
import org.jetbrains.annotations.NotNull;

public class InteractEvents implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getItem() != null) {
                if (e.getItem().getType().equals(Material.LIME_DYE) || e.getItem().getType().equals(Material.GRAY_DYE)) {
                    if (FYNXHub.getNoplayervisible().contains(e.getPlayer().getUniqueId())) {
                        ItemStack dye = new ItemStack(Material.LIME_DYE);
                        ItemMeta dyeMeta = dye.getItemMeta();
                        dyeMeta.setDisplayName("§fPlayer: §aVisible §7(Right Click)");
                        dye.setItemMeta(dyeMeta);
                        e.getPlayer().getInventory().setItem(7, dye);
                        e.getPlayer().updateInventory();
                        e.getPlayer().sendMessage("§aPlayers are now Vissible");
                        FYNXHub.getNoplayervisible().remove(e.getPlayer().getUniqueId());
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            e.getPlayer().showPlayer(FYNXHub.getInstance(), onlinePlayer);
                        }
                    } else {
                        ItemStack dye = new ItemStack(Material.GRAY_DYE);
                        ItemMeta dyeMeta = dye.getItemMeta();
                        dyeMeta.setDisplayName("§fPlayer: §4Hidden §7(Right Click)");
                        dye.setItemMeta(dyeMeta);
                        e.getPlayer().getInventory().setItem(7, dye);
                        e.getPlayer().updateInventory();
                        e.getPlayer().sendMessage("§cPlayers are now Hidden");
                        FYNXHub.getNoplayervisible().add(e.getPlayer().getUniqueId());
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            e.getPlayer().hidePlayer(FYNXHub.getInstance(), onlinePlayer);
                        }
                    }
                }
            }
        }
    }
}

