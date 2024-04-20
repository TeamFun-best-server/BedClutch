package me.hi12167pies.BedClutch.Utils;

import me.hi12167pies.BedClutch.Config;
import me.hi12167pies.BedClutch.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Arenas {
    public static HashMap<Player, String> Playing = new HashMap<>();

    public static boolean isPlaying(Player player) {
        return Playing.containsKey(player);
    }

    public static void teleportSpawn(Player player) {
        String map = Playing.get(player);
        player.teleport(Config.data.getMapSpawn(map));
    }

    public static double getVoid(Player player) {
        String map = Playing.get(player);
        return Config.data.getMapVoid(map);
    }

    public static void join(Player player, String map) {
        if (!Config.data.mapExist(map)) {
            player.sendMessage("§cThe map was not found.");
            return;
        }
        player.teleport(Config.data.getMapSpawn(map));
        setItems(player);
        Playing.put(player, map);
        runStartCommands(player);
    }

    public static void leave(Player player) {
        if (!isPlaying(player)) {
            player.sendMessage("§cYou're not in a game");
            return;
        }
        leaveFinal(player);
        runEndCommands(player);
    }

    public static void leaveFinal(Player player) {
        Playing.remove(player);
        player.getInventory().clear();
    }

    public static void setItems(Player player) {
        Inventory inv = player.getInventory();

        inv.clear();

        for (String slot : Main.instance.getConfig().getConfigurationSection("inv").getKeys(false)) {
            String item = Main.instance.getConfig().getString("inv." + slot + ".item");
            int i = Integer.parseInt(slot);
            if (item.equalsIgnoreCase("clutch")) {
                inv.setItem(i, new ItemBuilder(Material.INK_SACK).durability(10).name("§aClutch §7(Right-Click to use)").build());
                continue;
            }
            List<String> enchants = Main.instance.getConfig().contains("inv." + slot + ".enchants") ?
                    Main.instance.getConfig().getStringList("inv." + slot + ".enchants") :
                    Collections.emptyList();

            int amount = Main.instance.getConfig().contains("inv." + slot + ".amount") ?
                    Main.instance.getConfig().getInt("inv." + slot + ".amount") : 1;

            String name = Main.instance.getConfig().getString("inv." + slot + ".name");

            Enchantment[] enchantments = new Enchantment[enchants.size()];
            int[] enchantLevels = new int[enchants.size()];
            // save some time from looping if its empty
            if (enchants.size() > 0) {
                int in = 0;
                for (String enchant : enchants) {
                    String eName = enchant.split(" ")[0];
                    String eLevel = enchant.split(" ")[1];
                    enchantments[in] = Enchantment.getByName(eName);
                    enchantLevels[in] = Integer.parseInt(eLevel);
                    in++;
                }
            }

            String item_type = item.split(":")[0];
            byte item_byte = (byte) Integer.parseInt(item.split(":")[1]);
            ItemBuilder builder = new ItemBuilder(
                    Material.matchMaterial(item_type),
                    item_byte
            )
                    .ench(enchantments, enchantLevels)
                    .amount(amount);
            if (name != null) {
                builder.name(ChatColor.translateAlternateColorCodes('&', name));
            }
            inv.setItem(i, builder.build());
        }

    }

    public static void runStartCommands(Player player) {
        if (!Main.instance.getConfig().getBoolean("commands.enabled.join")) return;
        for (String s : Main.instance.getConfig().getStringList("commands.join_arena")) {
            String cmd = s;
            if (cmd.startsWith("/")) cmd = s.substring(1);
            player.performCommand(cmd);
        }
    }
    public static void runEndCommands(Player player) {
        if (!Main.instance.getConfig().getBoolean("commands.enabled.leave")) return;
        for (String s: Main.instance.getConfig().getStringList("commands.leave_arena")) {
            String cmd = s;
            if (cmd.startsWith("/")) cmd = s.substring(1);
            player.performCommand(cmd);
        }
    }
}
