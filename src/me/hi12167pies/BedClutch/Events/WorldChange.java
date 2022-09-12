package me.hi12167pies.BedClutch.Events;

import me.hi12167pies.BedClutch.Utils.Arenas;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChange implements Listener {
    @EventHandler
    void a(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (Arenas.isPlaying(player))
            Arenas.leave(player);
    }
}