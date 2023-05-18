package org.fynx.fynxhub.minigames;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.fynx.fynxhub.FYNXHub;
import org.bukkit.event.EventHandler;
import sun.awt.Symbol;

import java.util.HashMap;
import java.util.Map;

public class TicTacToeGame implements Listener {
    private static final String INV_TITLE = "Tic Tac Toe";

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Map<Integer, Symbol> board;
    private Inventory gameInventory;

    private static Map<Player, TicTacToeGame> pendingInvitations = new HashMap<>();

    public TicTacToeGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.board = new HashMap<>();

        // Register the game listener
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(FYNXHub.class));

        // Initialize the game state and display the game GUI
        initializeGame();
        showGameGUI(player1);
        showGameGUI(player2);

        // Store the pending invitation
        pendingInvitations.put(player2, this);
    }

    public static TicTacToeGame getPendingGame(Player player) {
        return pendingInvitations.get(player);
    }

    public static void startGame(Player player1, Player player2) {
        new TicTacToeGame(player1, player2);
    }

    private void initializeGame() {
        gameInventory = Bukkit.createInventory(null, 9, INV_TITLE);

        for (int i = 0; i < 9; i++) {
            gameInventory.setItem(i, createEmptySlot());
            board.put(i, Symbol.EMPTY);
        }
    }

    private void showGameGUI(Player player) {
        player.openInventory(gameInventory);
    }

    private ItemStack createEmptySlot() {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        return item;
    }

    private void makeMove(int slot) {
        Symbol symbol = currentPlayer == player1 ? Symbol.CIRCLE : Symbol.CROSS;
        board.put(slot, symbol);
        gameInventory.setItem(slot, createSymbolItem(symbol));

        if (checkWin(symbol)) {
            endGame(currentPlayer);
        } else if (checkDraw()) {
            endGame(null);
        } else {
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        }
    }

    private boolean checkWin(Symbol symbol) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board.get(i) == symbol && board.get(i + 3) == symbol && board.get(i + 6) == symbol)
                return true;
        }

        // Check columns
        for (int i = 0; i < 9; i += 3) {
            if (board.get(i) == symbol && board.get(i + 1) == symbol && board.get(i + 2) == symbol)
                return true;
        }

        // Check diagonals
        if ((board.get(0) == symbol && board.get(4) == symbol && board.get(8) == symbol) ||
                (board.get(2) == symbol && board.get(4) == symbol && board.get(6) == symbol))
            return true;

        return false;
    }

    private boolean checkDraw() {
        return board.values().stream().noneMatch(symbol -> symbol == Symbol.EMPTY);
    }

    private void endGame(Player winner) {
        if (winner != null) {
            player1.sendMessage(winner.getName() + " has won the Tic Tac Toe match.");
            player2.sendMessage(winner.getName() + " has won the Tic Tac Toe match.");
        } else {
            player1.sendMessage("Draw! Would you like to have a rematch?");
            player2.sendMessage("Draw! Would you like to have a rematch?");
        }
    }

    private ItemStack createSymbolItem(Symbol symbol) {
        ItemStack item;
        String displayName;

        if (symbol == Symbol.CIRCLE) {
            item = new ItemStack(Material.END_CRYSTAL);
            displayName = "Circle";
        } else if (symbol == Symbol.CROSS) {
            item = new ItemStack(Material.NETHER_STAR);
            displayName = "Cross";
        } else {
            return createEmptySlot();
        }

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return item;
    }

    public void acceptGame(Player player) {
        pendingInvitations.remove(player);
        currentPlayer = player;
        player.sendMessage("You have accepted the Tic Tac Toe invitation.");
    }

    public void declineGame(Player player) {
        TicTacToeGame game = pendingInvitations.get(player);
        if (game != null) {
            pendingInvitations.remove(player);
            player.sendMessage("You have declined the Tic Tac Toe invitation.");
            game.endGame(null);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory != null && inventory.getType() == InventoryType.CHEST) {
            InventoryHolder holder = inventory.getHolder();
            if (holder instanceof Player && (holder == player1 || holder == player2)) {
                event.setCancelled(true);

                if (event.getRawSlot() >= 0 && event.getRawSlot() < 9) {
                    makeMove(event.getRawSlot());
                }
            }
        }
    }

    private enum Symbol {
        EMPTY,
        CIRCLE,
        CROSS
    }
}

