package me.hi12167pies.BedClutch.Utils.Files;

import me.hi12167pies.BedClutch.Utils.MainFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Data extends MainFile {
    public Data() {
        super("data.yml");
    }

    public void saveMapSpawn(String map, Location location) {
        config.set("map."+map+".spawn.x", location.getX());
        config.set("map."+map+".spawn.y", location.getY());
        config.set("map."+map+".spawn.z", location.getZ());
        config.set("map."+map+".spawn.pitch", (double) location.getPitch());
        config.set("map."+map+".spawn.yaw", (double) location.getYaw());
        config.set("map."+map+".spawn.world", location.getWorld().getName());

        config.set("map."+map+".void", location.subtract(0, 5, 0).getY());

        save();
    }

    public double getMapVoid(String map) {
        return config.getDouble("map."+map+".void");
    }

    public Location getMapSpawn(String map) {
        double x = config.getDouble("map."+map+".spawn.x");
        double y = config.getDouble("map."+map+".spawn.y");
        double z = config.getDouble("map."+map+".spawn.z");
        float pitch = (float) config.getDouble("map."+map+".spawn.pitch");
        float yaw = (float) config.getDouble("map."+map+".spawn.yaw");
        World world = Bukkit.getWorld(config.getString("map."+map+".spawn.world"));

        return new Location(world, x, y, z, yaw, pitch);
    }

    public void deleteMap(String map) {
        config.set("map."+map, null);
        save();
    }

    public boolean mapExist(String map) {
        return config.getConfigurationSection("map").getKeys(false).contains(map);
    }
}
