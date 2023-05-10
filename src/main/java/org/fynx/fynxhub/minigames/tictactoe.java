package org.fynx.fynxhub.minigames;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class tictactoe {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("ttt")) {
            if (args.length == 1) {
                Player player = Bukkit.getPlayer(args[0]);
                if (player != null) {
                    openTicTacToeGame(player);
                }
            }
        }
    
    return true;
}

private void openTicTacToeGame(Player player) {
    Inventory inventory = Bukkit.createInventory(null, 9, "Tic-Tac-Toe");
    for (int i = 0; i < 9; i++) {
        inventory.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1, (short) 15));
    }
    player.openInventory(inventory);
}

private int[][] board = new int[3][3];
private int currentPlayer = 1;

// Handle player moves
@EventHandler
public void onInventoryClick(InventoryClickEvent event) {
    if (event.getInventory().getType().equals("Tic-Tac-Toe")) {
        event.setCancelled(true);
        if (event.getClick() != ClickType.LEFT) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();
        int row = slot / 3;
        int col = slot % 3;
        if (board[row][col] == 0) {
            board[row][col] = currentPlayer;
            // Update the inventory
            ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1, currentPlayer == 1 ? (short) 14 : (short) 11);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(currentPlayer == 1 ? "X" : "O");
            item.setItemMeta(meta);
            event.getInventory().setItem(slot, item);
            // Check for a win or a draw
            if (checkWin(currentPlayer)) {
                player.sendMessage("You win!");
                Player otherPlayer = getOtherPlayer(player);
                if (otherPlayer != null) {
                    otherPlayer.sendMessage(player.getName() + " has won the game!");
                }
                // End the game
                board = new int[3][3];
                currentPlayer = 1;
                player.closeInventory();
            } else if (isBoardFull()) {
                player.sendMessage("Draw!");
                Player otherPlayer = getOtherPlayer(player);
                if (otherPlayer != null) {
                    otherPlayer.sendMessage("The game ended in a draw.");
                }
                // End the game
                board = new int[3][3];
                currentPlayer = 1;
                player.closeInventory();
            } else {
                // Switch to the other player's turn
                currentPlayer = currentPlayer == 1 ? 2 : 1;
            }
        }
    }
}

// Check for a win
private boolean checkWin(int player) {
    // Check rows
    for (int i = 0; i < 3; i++) {
        if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
            return true;
        }
    }
    // Check columns
    for (int j = 0; j < 3; j++) {
        if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
            return true;
        }
    }   
// Check diagonals
    if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
        return true;
    }
    if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
        return true;
    }
    return false;
}

// Check if the board is full
private boolean isBoardFull() {
    for (int i = 0; i < 3; i++) {
       for (int j = 0; j < 3; j++) {
            if (board[i][j] == 0) {
                return false;
            }
        }
    }
    return true;
}

// Get the other player
private Player getOtherPlayer(Player player) {
    for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
        if (!onlinePlayer.equals(player)) {
            return onlinePlayer;
        }
    }
    return null;
}


@EventHandler
public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    String[] args = event.getMessage().split(" ");
    if (args.length == 2 && args[0].equalsIgnoreCase("/ttt")) {
        Player player = event.getPlayer();
        Player otherPlayer = getOtherPlayer(player);
        if (otherPlayer != null) {
            Inventory inventory = Bukkit.createInventory(null, 9, "Tic-Tac-Toe");
            for (int i = 0; i < 9; i++) {
                ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1, (short) 0);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(" ");
                item.setItemMeta(meta);
                inventory.setItem(i, item);
            }
// Open the inventory for both players
            player.openInventory(inventory);
            otherPlayer.openInventory(inventory);
        } else {
            player.sendMessage("There are no other players online.");
        }
        event.setCancelled(true);
    }
}
}