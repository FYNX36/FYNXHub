package org.fynx.fynxhub.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.fynx.fynxhub.minigames.TicTacToeGame;

public class TicTacToeCommand implements org.bukkit.command.CommandExecutor {
    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                String opponentName = args[0];
                Player opponent = Bukkit.getPlayer(opponentName);

                if (opponent != null && opponent != player) {
                    TicTacToeGame.startGame(player, opponent);
                } else {
                    player.sendMessage("Invalid opponent.");
                }
            } else {
                player.sendMessage("Usage: /ttt <opponent>");
            }
        }
        return true;
    }
}
