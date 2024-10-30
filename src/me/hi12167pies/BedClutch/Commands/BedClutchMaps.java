package me.hi12167pies.BedClutch.Commands;

import me.hi12167pies.BedClutch.Config;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class BedClutchMaps implements CommandExecutor {
    private Random random = new Random();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (!sender.hasPermission(command.getPermission())) {
            sender.sendMessage(command.getPermissionMessage());
            return true;
        }
        Player player = (Player) sender;

        if (args.length >= 2) {
            String map = args[1];
            if (args[0].equalsIgnoreCase("setspawn")) {
                Config.data.saveMapSpawn(map, player.getLocation());
                player.sendMessage("§aSet the map's spawn to your current location");
            } else if (args[0].equalsIgnoreCase("delete")) {
                if (!Config.data.mapExist(map)) {
                    player.sendMessage("§cThat map doesn't exist");
                } else {
                    Config.data.deleteMap(map);
                }
            } else {
                player.sendMessage(command.getUsage());
            }
        } else {
            player.sendMessage(command.getUsage());
        }

        return true;
    }

    public void teleportToRandomSpawn(Player player, String map) {
        List<Location> spawns = Config.data.getMapSpawns(map);
        if (spawns != null && !spawns.isEmpty()) {
            Location randomSpawn = spawns.get(random.nextInt(spawns.size()));
            player.teleport(randomSpawn);
        }
    }
}
