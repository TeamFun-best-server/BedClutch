package me.hi12167pies.BedClutch.Listeners;

import me.hi12167pies.BedClutch.Config;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerFallListener implements Listener {

    @EventHandler
    public void onPlayerFall(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (!Arenas.isPlaying(player)) return;
        
        if (player.getLocation().getY() < 44) { // controleer of de speler in de void is
            String map = getCurrentMap(player); // Dit moet een methode zijn die de huidige map teruggeeft
            if (map != null) {
                Config.data.teleportToRandomSpawn(player, map);
            }
        }
    }

    private String getCurrentMap(Player player) {
        // Implementatie om de huidige map te bepalen (bijv. via een metadata of een andere manier)
        return "nature"; // Voorbeeld
    }
}


        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (!Arenas.isPlaying(player)) return;
