package me.hi12167pies.BedClutch.Events;

import me.hi12167pies.BedClutch.Config;
import me.hi12167pies.BedClutch.Utils.Arenas;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
    @EventHandler
    public void event(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        if (!Arenas.isPlaying(player)) return;
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL)
            e.setCancelled(true);
    }
    @EventHandler
    public void event(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        if (!Arenas.isPlaying(player)) return;
        if (Config.pvp) {
            e.setDamage(0);
        } else {
            e.setCancelled(true);
        }
    }
}
