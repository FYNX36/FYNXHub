package org.fynx.fynxhub.minigames;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.fynx.fynxhub.FYNXHub;

public class bridge implements Listener {
    @EventHandler
    public void onSignEdit(SignChangeEvent event){
        String s = event.getLine(0);


        if(s.equalsIgnoreCase("[bingo]")){
            event.setLine(0, "§7[§aBingo§7]");
            event.setLine(2, "§9 Bingo");
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getClickedBlock() == null) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getState() instanceof Sign){
            String bridge = ((Sign) event.getClickedBlock().getState()).getLine(0);

            if (bridge.equalsIgnoreCase("§7[§aBingo§7]")){
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF("bingo");

                player.sendPluginMessage(FYNXHub.getInstance(), "BungeeCord", out.toByteArray());
            }
        }
    }
}
