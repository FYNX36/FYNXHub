package org.fynx.fynxhub.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fynx.fynxhub.FYNXHub;
import org.fynx.fynxhub.utils.FileConfig;
import org.fynx.fynxhub.utils.LocationUtils;
import org.jetbrains.annotations.NotNull;

public class spawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {

        if(!(sender instanceof Player)){
            sender.sendMessage("This Command is only avaliable for Players");
            return true;
        }

        Player player = (Player) sender;
        FileConfig spawns = new FileConfig("Locations.yml");
        if(label.equalsIgnoreCase("setspawn")){
            if(player.hasPermission("org.fynx.fynxhub.setspawn")){
                spawns.set("spawn", LocationUtils.loc2Str(player.getLocation()));
                spawns.saveConfig();
                player.sendMessage(FYNXHub.PREFIX + "§aSpawn gesetzt");
            } else {
                player.sendMessage(FYNXHub.PREFIX + "§cDir fehlen die Berechtigungen dazu.");
            }
            return true;
        }
        if(spawns.contains("spawn")){
            LocationUtils.teleport(player, LocationUtils.str2Loc(spawns.getString("spawn")));
        } else {
            player.sendMessage(FYNXHub.PREFIX + "§cEs ist kein Spawn festgelegt.");
        }

        return true;
    }
}
