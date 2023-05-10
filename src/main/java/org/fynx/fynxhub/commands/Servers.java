package org.fynx.fynxhub.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import jdk.tools.jlink.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.fynx.fynxhub.FYNXHub;

import java.util.ArrayList;
import java.util.List;

public class Servers implements Listener, CommandExecutor {
    private String invName = "§6§lServer Selection";



    public Servers(FYNXHub plugin){
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
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("survival");

            player.sendPluginMessage(FYNXHub.getInstance(), "BungeeCord", out.toByteArray());
            player.closeInventory();
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() == null) return;
            if (event.getItem().getType() == Material.NETHER_STAR) {
                Player player = event.getPlayer();
                player.performCommand("navigate");
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
        inv.setItem(9, getItem(new ItemStack(Material.CRAFTING_TABLE), "&9Lobby", "&2 You are already on this Server"));
        inv.setItem(11, getItem(new ItemStack(Material.GRASS_BLOCK), "&3Survival 1", "&a Click to Join the Lobby Server"));
        inv.setItem(13, getItem(new ItemStack(Material.GRASS_BLOCK), "&6Survival 2", "&a Click to Join the Lobby Server"));
        inv.setItem(15, getItem(new ItemStack(Material.DIAMOND_BLOCK), "&2Modded 1", "&a Click to Join the Lobby Server"));
        inv.setItem(17, getItem(new ItemStack(Material.DIAMOND_BLOCK), "&5Modded 2", "&a Click to Join the Lobby Server"));

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

