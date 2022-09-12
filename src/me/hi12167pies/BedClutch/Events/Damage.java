package me.hi12167pies.BedClutch.Events;

import me.hi12167pies.BedClutch.Utils.Arenas;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Damage implements Listener {
    @EventHandler
    void a(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        if (!Arenas.isPlaying(player)) return;
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL)
            e.setCancelled(true);
    }
    @EventHandler
    void a(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        if (!Arenas.isPlaying(player)) return;
        e.setCancelled(true);
    }
}
