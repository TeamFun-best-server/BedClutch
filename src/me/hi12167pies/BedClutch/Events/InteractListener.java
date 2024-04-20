package me.hi12167pies.BedClutch.Events;

import me.hi12167pies.BedClutch.Handler.GUIHandler;
import me.hi12167pies.BedClutch.Main;
import me.hi12167pies.BedClutch.Utils.Arenas;
import me.hi12167pies.BedClutch.Utils.Titles;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {
    @EventHandler
    public void event(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!Arenas.isPlaying(player)) return;

        ItemStack item = player.getItemInHand();
        if (item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null) return;
        if (item.getItemMeta().getDisplayName().equals("§aClutch §7(Right-Click to use)")) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
                GUIHandler.openHitsGUI(player);
            }
            if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
                if (!GUIHandler.lastHit.containsKey(player)) {
                    player.sendMessage("§cYou do not have a last hit.");
                    return;
                }
                String name = Main.instance.getConfig().getString("menu.options." + GUIHandler.lastHit.get(player) + ".name")
                        .replace("&", "§");
                Titles.send(player, "§6§l3", "§6§l[" + name + "§6§l]");
                GUIHandler.await(20, () -> {
                    Titles.send(player, "§6§l2", "§6§l[" + name + "§6§l]");
                });
                GUIHandler.await(40, () -> {
                    Titles.send(player, "§6§l1", "§6§l[" + name + "§6§l]");
                });

                GUIHandler.await(60, () -> {
                    GUIHandler.start(player, GUIHandler.lastHit.get(player), 1);
                });
            }
        }
    }
}
