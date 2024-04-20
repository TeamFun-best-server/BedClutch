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

        Bukkit.getPluginManager().registerEvents(new MoveListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIHandler(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new WorldChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new DropListener(), this);
    }
}
