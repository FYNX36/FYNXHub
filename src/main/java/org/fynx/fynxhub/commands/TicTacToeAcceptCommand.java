package org.fynx.fynxhub.commands;

import org.bukkit.entity.Player;
import org.fynx.fynxhub.minigames.TicTacToeGame;

public class TicTacToeAcceptCommand implements org.bukkit.command.CommandExecutor {
    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                TicTacToeGame game = TicTacToeGame.getPendingGame(player);
                if (game != null) {
                    game.acceptGame(player);
                } else {
                    player.sendMessage("No pending Tic Tac Toe invitation.");
                }
            } else {
                player.sendMessage("Usage: /accept");
            }
        }
        return true;
    }
}
