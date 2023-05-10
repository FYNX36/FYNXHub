package org.fynx.fynxhub.tablist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.fynx.fynxhub.FYNXHub;

public class TablistManager {

    private int socialId;

    public void setTablist(Player player){
        player.setPlayerListHeaderFooter(ChatColor.GOLD.toString() + "Server Netzwerk","by F Y N X");
    }

    public void setAllPlayerTeams(){
        Bukkit.getOnlinePlayers().forEach(this::setPlayerTeams);
    }
    public void setPlayerTeams(Player player){
        Scoreboard scoreboard = player.getScoreboard();

        Team players = scoreboard.getTeam("bplayers");

        if (players == null){
            players = scoreboard.registerNewTeam("bplayers");
        }

        Team operators = scoreboard.getTeam("aoperators");

        if (operators == null){
            operators = scoreboard.registerNewTeam("aoperators");
        }
        players.setPrefix(ChatColor.GREEN + "Player " + ChatColor.DARK_GRAY + "| ");
        players.setColor(ChatColor.GREEN);

        operators.setPrefix(ChatColor.DARK_RED + "Admin " + ChatColor.DARK_GRAY + "| ");
        operators.setColor(ChatColor.DARK_RED);

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.isOp()){
                operators.addEntry(target.getName());
                continue;
            }

            players.addEntry(target.getName());

        }







    }


}
