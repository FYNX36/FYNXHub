package org.fynx.fynxhub;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.fynx.fynxhub.commands.*;
import org.fynx.fynxhub.events.ConnectEvents;
import org.fynx.fynxhub.events.InteractEvents;
import org.fynx.fynxhub.listener.InventoryListener;
import org.fynx.fynxhub.listener.JoinQuitListener;
import org.fynx.fynxhub.listener.playerVisibility;
import org.fynx.fynxhub.minigames.TicTacToeGame;
import org.fynx.fynxhub.minigames.bridge;
import org.fynx.fynxhub.tablist.TablistManager;

import java.util.ArrayList;
import java.util.UUID;


public class FYNXHub extends JavaPlugin implements PluginMessageListener {

    private static FYNXHub instance;

    private static ArrayList<UUID> noplayervisible;


    private TablistManager tablistManager;
    @Override
    public void onLoad() {
        instance = this;
    }

    public static String PREFIX = "§6§lFYNX HUB: ";

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("F Y N X Hub Online");

        //Listener
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new JoinQuitListener(), this);
        pluginManager.registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new ConnectEvents(), this);
        Bukkit.getPluginManager().registerEvents(new InteractEvents(), this);
        Bukkit.getPluginManager().registerEvents(new bridge(), this);

        //Commands
        Bukkit.getPluginCommand("navigate").setExecutor(new Servers(this));
        Bukkit.getPluginCommand("spawn").setExecutor(new spawnCommand());
        getCommand("minigames").setExecutor(new minigames(this));
        getCommand("ttt").setExecutor(new TicTacToeCommand());
        getCommand("accept").setExecutor(new TicTacToeAcceptCommand());
        getCommand("decline").setExecutor(new  TicTacToeDeclineCommand());


        tablistManager = new TablistManager();
        noplayervisible = new ArrayList<>();
        instance =this;

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        return;
    }

    public static FYNXHub getInstance() {
        return instance;
    }

    public TablistManager getTablistManager() {
        return tablistManager;
    }

    public static ArrayList<UUID> getNoplayervisible() {
        return noplayervisible;
    }
}
