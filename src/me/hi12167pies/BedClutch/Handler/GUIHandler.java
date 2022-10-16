package me.hi12167pies.BedClutch.Handler;

import me.hi12167pies.BedClutch.Main;
import me.hi12167pies.BedClutch.Utils.Arenas;
import me.hi12167pies.BedClutch.Utils.DirectionVector;
import me.hi12167pies.BedClutch.Utils.ItemBuilder;
import me.hi12167pies.BedClutch.Utils.Titles;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.util.Vector;

import java.awt.geom.Area;
import java.util.HashMap;

public class GUIHandler implements Listener {
    public static InventoryHolder holder = () -> null;

    public static void openHitsGUI(Player player) {
        Inventory inv = Bukkit.createInventory(holder, Main.instance.getConfig().getInt("menu.size") * 9, "Select a clutch!");

        for (String hitID : Main.instance.getConfig().getConfigurationSection("menu.options").getKeys(false)) {
            String name = Main.instance.getConfig().getString("menu.options." + hitID + ".displayName")
                    .replace("&", "§");
            String lore = Main.instance.getConfig().getString("menu.options." + hitID + ".lore")
                    .replace("&", "§");
            String[] item = Main.instance.getConfig().getString("menu.options." + hitID + ".block").split(":");
            Material material = Material.getMaterial(item[0]);

            ItemBuilder builder = new ItemBuilder(material)
                    .durability(Integer.parseInt(item[1]))
                    .name(name);
            if (!lore.equals(""))
                builder.lore(lore);

            inv.setItem(Integer.parseInt(hitID), builder.build());
        }

        player.openInventory(inv);
    }
    public static HashMap<Player, Integer> lastHit = new HashMap<>();
    @EventHandler
    void a(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getInventory().getHolder() == holder) {
            e.setCancelled(true);
        }
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getHolder() == holder) {
            int hitID = e.getRawSlot();
            if (e.getCurrentItem().getType() == Material.AIR) return;
            String name = Main.instance.getConfig().getString("menu.options." + hitID + ".name")
                    .replace("&", "§");

            player.closeInventory();
            lastHit.put(player, hitID);

            Titles.send(player, "§6§l3", "§6§l[" + name + "§6§l]");
            await(20, () -> {
                Titles.send(player, "§6§l2", "§6§l[" + name + "§6§l]");
            });
            await(40, () -> {
                Titles.send(player, "§6§l1", "§6§l[" + name + "§6§l]");
            });

            await(60, () -> {
                start(player, hitID, 1);
            });
        }
    }

    public static void start(Player player, int hitID, int current) {
        if (!Arenas.isPlaying(player)) return;
        double power = Main.instance.getConfig().getDouble("menu.options." + hitID + ".hit.power");
        double vertical = Main.instance.getConfig().getDouble("menu.options." + hitID + ".hit.vertical");
        int times = Main.instance.getConfig().getInt("menu.options." + hitID + ".hit.hits");
        boolean diag = Main.instance.getConfig().getBoolean("menu.options." + hitID + ".hit.diagonal");

        Vector vector = diag ?
                DirectionVector.getDiag(player, power, vertical, power) :
                DirectionVector.getStraight(player, power, vertical);

        player.setVelocity(vector);
        player.damage(0);

        if (times > current) {
            await(10, () -> {
                start(player, hitID, current + 1);
            });
        }
    }
    public static void await(int time, Event e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
            @Override
            public void run() {
               e.run();
            }
        }, time);
    }
}
