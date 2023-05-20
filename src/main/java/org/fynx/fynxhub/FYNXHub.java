package org.fynx.fynxhub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.fynx.fynxhub.commands.*;
import org.fynx.fynxhub.events.ConnectEvents;
import org.fynx.fynxhub.events.InteractEvents;
import org.fynx.fynxhub.listener.InventoryListener;
import org.fynx.fynxhub.listener.JoinQuitListener;
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

        //Commands
        Bukkit.getPluginCommand("navigate").setExecutor(new Servers(this));
        Bukkit.getPluginCommand("spawn").setExecutor(new spawnCommand());
        getCommand("minigames").setExecutor(new minigames(this));


        tablistManager = new TablistManager();
        noplayervisible = new ArrayList<>();
        instance = this;

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