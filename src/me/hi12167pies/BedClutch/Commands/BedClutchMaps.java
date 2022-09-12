package me.hi12167pies.BedClutch.Commands;

import me.hi12167pies.BedClutch.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BedClutchMaps implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (!sender.hasPermission(command.getPermission())) {
            sender.sendMessage(command.getPermissionMessage());
            return true;
        }
        Player player = (Player) sender;

        if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("setspawn")) {
                String map = args[1];
                Config.data.saveMapSpawn(map, player.getLocation());
                player.sendMessage("§aSet the map's spawn to your current location");
            } else if (args[0].equalsIgnoreCase("delete")) {
                String map = args[1];
                if (!Config.data.mapExist(map))
                    player.sendMessage("§cThat map doesn't exist");
                else
                    Config.data.deleteMap(map);
            } else sender.sendMessage(command.getUsage());
        } else sender.sendMessage(command.getUsage());

        return true;
    }
}