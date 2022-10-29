package me.hi12167pies.BedClutch.Events;

import me.hi12167pies.BedClutch.Config;
import me.hi12167pies.BedClutch.Main;
import me.hi12167pies.BedClutch.Utils.Arenas;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
    @EventHandler
    void a(PlayerJoinEvent e) {
        if (Main.instance.getConfig().getBoolean("bungeecord.enabled")) {
            Arenas.join(e.getPlayer(), Main.instance.getConfig().getString("bungeecord.map"));
        }
    }
}
