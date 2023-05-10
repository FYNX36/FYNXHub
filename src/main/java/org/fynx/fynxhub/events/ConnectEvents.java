package org.fynx.fynxhub.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.fynx.fynxhub.FYNXHub;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ConnectEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if (FYNXHub.getNoplayervisible().contains(e.getPlayer().getUniqueId())){
            ItemStack dye = new ItemStack(Material.GRAY_DYE);
            ItemMeta dyeMeta = dye.getItemMeta();
            dyeMeta.setDisplayName("§fPlayer: §4Hidden §7(Right Click)");
            dye.setItemMeta(dyeMeta);
            e.getPlayer().getInventory().setItem(7, dye);
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                e.getPlayer().hidePlayer(FYNXHub.getInstance(), onlinePlayer);

            }
        }else{
            ItemStack dye = new ItemStack(Material.LIME_DYE);
            ItemMeta dyeMeta = dye.getItemMeta();
            dyeMeta.setDisplayName("§fPlayer: §aVisible §7(Right Click)");
            dye.setItemMeta(dyeMeta);
            e.getPlayer().getInventory().setItem(7, dye);
        }
        for (UUID onlinePlayer :FYNXHub.getNoplayervisible()) {
            if (Bukkit.getPlayer(onlinePlayer) != null){
                Bukkit.getPlayer(onlinePlayer).hidePlayer(FYNXHub.getInstance(), e.getPlayer());
            }

        }
    }

}
