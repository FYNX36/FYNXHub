package org.fynx.fynxhub.commands;

import org.bukkit.entity.Player;
import org.fynx.fynxhub.minigames.TicTacToeGame;

public class TicTacToeDeclineCommand implements org.bukkit.command.CommandExecutor {
    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                TicTacToeGame game = TicTacToeGame.getPendingGame(player);
                if (game != null) {
                    game.declineGame(player);
                } else {
                    player.sendMessage("No pending Tic Tac Toe invitation.");
                }
            } else {
                player.sendMessage("Usage: /decline");
            }
        }
        return true;
    }
}