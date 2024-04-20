package me.hi12167pies.BedClutch.Events;

import me.hi12167pies.BedClutch.Utils.Arenas;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeListener implements Listener {
    @EventHandler
    public void event(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (Arenas.isPlaying(player)) {
            Arenas.leave(player);
        }
    }
}
