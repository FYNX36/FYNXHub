package org.fynx.fynxhub.scoreboard;

import jdk.tools.jmod.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.fynx.fynxhub.FYNXHub;

public class TestScoreboard extends ScoreboardBuilder{

    private int socialId;

    public TestScoreboard(Player player) {
        super(player, ChatColor.GOLD.toString() + ChatColor.BOLD+ "  FYNX Network  ");
        socialId = 0;

        run();

    }

    @Override
    public void createScoreboard() {
        int playerCount = Bukkit.getOnlinePlayers().size();
        int maxPlayer = Bukkit.getMaxPlayers();
        setScore(ChatColor.DARK_GRAY.toString(), 9);
        setScore(ChatColor.GRAY + "Dein Rang" + ChatColor.DARK_GRAY + ":", 8);

        if (player.isOp()){
            setScore(ChatColor.DARK_RED + "Admin", 7);
        }else {
            setScore(ChatColor.GREEN + "Spieler", 7);
        }

        setScore(ChatColor.GRAY.toString(), 6);
        setScore(ChatColor.DARK_AQUA + "Discord" + ChatColor.DARK_GRAY + ":", 5);
        setScore(ChatColor.DARK_AQUA + "F Y N X#4383", 4);
        setScore(ChatColor.RED.toString(), 3);
        setScore(ChatColor.GRAY + "Spieler Online" + ChatColor.DARK_GRAY + ":", 2);
        setScore( playerCount + " von " + maxPlayer, 1);
        setScore(ChatColor.AQUA.toString(), 0);

    }

    @Override
    public void update() {
        int playerCount = Bukkit.getOnlinePlayers().size();
        int maxPlayer = Bukkit.getMaxPlayers();
        switch (socialId){
            case 0:
                setScore(ChatColor.DARK_PURPLE+ "Twitch" + ChatColor.DARK_GRAY + ":", 5);
                setScore(ChatColor.DARK_PURPLE + "twitch.tv/fynx36", 4);
                break;
            case 1:
                setScore(ChatColor.DARK_AQUA + "Discord" + ChatColor.DARK_GRAY + ":", 5);
                setScore(ChatColor.DARK_AQUA + "F Y N X#4383", 4);
                break;
        }
        setScore( playerCount + " von " + maxPlayer, 1);

        if (player.isOp()){
            setScore(ChatColor.DARK_RED + "Admin", 7);
        }else {
            setScore(ChatColor.GREEN + "Spieler", 7);
        }

        socialId++;

        if (socialId >= 2){
            socialId = 0;
        }

    }

    private void run(){
        new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }.runTaskTimer(FYNXHub.getInstance(), 100, 100);
    }

}
