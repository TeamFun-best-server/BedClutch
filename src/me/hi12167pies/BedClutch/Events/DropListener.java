package me.hi12167pies.BedClutch.Events;

import me.hi12167pies.BedClutch.Utils.Arenas;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropListener implements Listener {
    @EventHandler
    public void event(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (!Arenas.isPlaying(player)) return;

        event.setCancelled(true);
    }
}
