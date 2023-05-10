package org.fynx.fynxhub.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.fynx.fynxhub.FYNXHub;
import org.fynx.fynxhub.scoreboard.TestScoreboard;


public class JoinQuitListener implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage("§7[§a§l+ §7"+ player.getDisplayName() + "§7]");
        player.setGameMode(GameMode.ADVENTURE);
        player.setFoodLevel(2000);
        player.setHealth(20);
        new TestScoreboard(player);
        FYNXHub.getInstance().getTablistManager().setTablist(player);
        FYNXHub.getInstance().getTablistManager().setAllPlayerTeams();
        player.performCommand("spawn");
        player.playSound(player,Sound.ENTITY_PLAYER_LEVELUP, 0.2f, 1.2f);



    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        event.setQuitMessage("§7[§c§l- §7"+ player.getDisplayName() + "§7]");
    }
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        Bukkit.getLogger().info("Gay");
        new TestScoreboard(player);
    }
}
