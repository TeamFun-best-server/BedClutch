package me.hi12167pies.BedClutch;

import me.hi12167pies.BedClutch.Commands.BedClutch;
import me.hi12167pies.BedClutch.Commands.BedClutchMaps;
import me.hi12167pies.BedClutch.Events.*;
import me.hi12167pies.BedClutch.Handler.GUIHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        Bukkit.getPluginCommand("bedclutch").setExecutor(new BedClutch());
        Bukkit.getPluginCommand("bedclutchmaps").setExecutor(new BedClutchMaps());

        Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getPluginManager().registerEvents(new PlaceBreak(), this);
        Bukkit.getPluginManager().registerEvents(new Damage(), this);
        Bukkit.getPluginManager().registerEvents(new GUIHandler(), this);
        Bukkit.getPluginManager().registerEvents(new Interact(), this);
        Bukkit.getPluginManager().registerEvents(new WorldChange(), this);
    }
}
