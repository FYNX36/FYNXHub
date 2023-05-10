package org.fynx.fynxhub.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.fynx.fynxhub.FYNXHub;

import java.util.ArrayList;
import java.util.List;

public class minigames implements Listener, CommandExecutor {

    private String invName = "§6§lMinigame Selection";

    public minigames(FYNXHub plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!event.getView().getTitle().equals(invName)){
            return;
        }

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();

        event.setCancelled(true);

        if (slot == 11){

            Location loc = new Location(Bukkit.getWorld("Lobby"), 86.5, 69, -9.5, -90,1);
            player.teleport(loc);

            player.closeInventory();
        }
        if (slot == 13){

            Location loc = new Location(Bukkit.getWorld("Lobby"), 96.5, 69, 4.5, -90,1);
            player.teleport(loc);

            player.closeInventory();
        }
        if (slot == 15){

            Location loc = new Location(Bukkit.getWorld("Lobby"), 95.5, 69, -23.5, -90,1);
            player.teleport(loc);

            player.closeInventory();
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() == null) return;
            if (event.getItem().getType() == Material.COMPASS) {
                Player player = event.getPlayer();
                player.performCommand("minigames");
            }
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("This Command is only avaliable for Players");
            return true;
        }

        Player player = (Player) sender;

        Inventory inv = Bukkit.createInventory(player, 9*3 , invName);
        for(int i = 0; i < 27; i++){
            inv.setItem(i, getItem(new ItemStack(Material.GRAY_STAINED_GLASS_PANE), "§", ""));
        }
        inv.setItem(11, getItem(new ItemStack(Material.CRAFTING_TABLE), "&9Minigames", "&a Teleports you to the Minigame lobby"));
        inv.setItem(13, getItem(new ItemStack(Material.GRASS_BLOCK), "&3Minechess", "&a Teleports you to the Minechess games"));
        inv.setItem(15, getItem(new ItemStack(Material.GRASS_BLOCK), "&6Bingo", "&a Teleports you to the Minigame lobby"));

        player.openInventory(inv);
        return true;

    }
    private ItemStack getItem(ItemStack item, String name, String ... lore){
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        List<String> lores = new ArrayList<>();
        for (String s : lore){
            lores.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        meta.setLore(lores);

        item.setItemMeta(meta);
        return item;
    }

}
