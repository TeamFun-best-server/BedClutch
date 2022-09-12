package me.hi12167pies.BedClutch.Utils;

import me.hi12167pies.BedClutch.Config;
import me.hi12167pies.BedClutch.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import java.util.HashMap;


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
        Inventory i = player.getInventory();

        i.clear();

        i.setItem(0, new ItemBuilder(Material.WOOD_SWORD).unBreak().hideUnBreak().build());
        i.setItem(1, new ItemBuilder(Material.WOOD_PICKAXE).unBreak().hideUnBreak().build());
        i.setItem(2, new ItemBuilder(Material.WOOL).amount(64).unBreak().hideUnBreak().build());
        i.setItem(3, new ItemBuilder(Material.WOOD_AXE).unBreak().hideUnBreak().build());

        i.setItem(8, new ItemBuilder(Material.INK_SACK).durability(10).name("§aClutch §7(Right-Click to use)").build());
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
