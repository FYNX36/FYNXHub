package org.fynx.fynxhub.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class InventoryListener implements Listener{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.getInventory().clear();


        //Server select
        ItemStack star = new ItemStack(Material.NETHER_STAR);
        ItemMeta starMeta = star.getItemMeta();
        starMeta.setDisplayName("§aServer Selector §7(Right Click)");
        star.setItemMeta(starMeta);
        player.getInventory().setItem(0, star);

        //Minigames
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName("§aMinigame Selector §7(Right Click)");
        compass.setItemMeta(compassMeta);
        player.getInventory().setItem(1, compass);
        player.setCompassTarget(new Location(player.getWorld(), -10, 58, -10));

        //PlayerHead
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName("§6Profile §7(Comming Later)");
        headMeta.setOwningPlayer(player);
        head.setItemMeta(headMeta);
        player.getInventory().setItem(8, head);

        //Make hotbar unmodifiable
        for(int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if(item != null) {
                ItemMeta meta = item.getItemMeta();
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                item.setItemMeta(meta);
                player.getInventory().setItem(i, item);
            }
        }

    }
}
