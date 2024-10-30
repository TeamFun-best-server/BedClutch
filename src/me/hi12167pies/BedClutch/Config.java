package me.hi12167pies.BedClutch;

import me.hi12167pies.BedClutch.Utils.Files.Data;

package me.hi12167pies.BedClutch;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    public static Data data = new Data();

    public static class Data {
        private Map<String, List<Location>> mapSpawns = new HashMap<>();

        public void saveMapSpawn(String map, Location location) {
            mapSpawns.putIfAbsent(map, new ArrayList<>());
            mapSpawns.get(map).add(location);
        }

        public List<Location> getMapSpawns(String map) {
            return mapSpawns.get(map);
        }

        public boolean mapExist(String map) {
            return mapSpawns.containsKey(map);
        }

        public void deleteMap(String map) {
            mapSpawns.remove(map);
        }
public class Config {
    public static boolean pvp = Main.instance.getConfig().getBoolean("pvp");
    public static Data data = new Data();
}
