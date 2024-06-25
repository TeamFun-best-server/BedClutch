import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.World;
import org.bukkit.block.Block;


import java.util.HashMap;
import java.util.Map;

public class MyMCPlugin extends JavaPlugin implements Listener {

    private Location lobbyLocation;
    private Location mlgrushLobbyLocation;
    private Map<String, Location> mlgrushSpawns = new HashMap<>();
    private Map<String, Integer> rankPriority = new HashMap<>();
    private Map<String, String> playerRanks = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        setupCommands();
        loadConfig();
    }

    @Override
    public void onDisable() {
        // Opslaan van eventuele wijzigingen
    }

    private void setupCommands() {
        getCommand("setlobby").setExecutor(new LobbyCommand());
        getCommand("lobby").setExecutor(new LobbyCommand());
        getCommand("mlgrush").setExecutor(new MLGRushCommand());
        getCommand("rank").setExecutor(new RankCommand());
        getCommand("nick").setExecutor(new NickCommand());
        getCommand("setvoid").setExecutor(new VoidCommand());
    }

    private void loadConfig() {
        FileConfiguration config = getConfig();
        lobbyLocation = parseLocation(config.getString("lobby.location"));
        mlgrushLobbyLocation = parseLocation(config.getString("mlgrush.lobby_location"));

        for (String rank : config.getStringList("ranks.list")) {
            String[] parts = rank.split(",");
            rankPriority.put(parts[0].trim(), Integer.parseInt(parts[3].trim()));
        }
    }

    private Location parseLocation(String locStr) {
        String[] parts = locStr.split(",");
        return new Location(Bukkit.getWorld(parts[0]), Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
    }

    private class LobbyCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("setlobby")) {
                if (!player.isOp()) {
                    player.sendMessage(ChatColor.RED + "Je moet een OP zijn om dit te doen.");
                    return true;
                }
                lobbyLocation = player.getLocation();
                getConfig().set("lobby.location", locationToString(lobbyLocation));
                saveConfig();
                player.sendMessage(ChatColor.GREEN + "Lobby locatie ingesteld!");
                return true;
            } else if (cmd.getName().equalsIgnoreCase("lobby")) {
                if (lobbyLocation != null) {
                    player.teleport(lobbyLocation);
                    player.sendMessage(ChatColor.GREEN + "Je bent naar de lobby geteleporteerd.");
                } else {
                    player.sendMessage(ChatColor.RED + "Lobby locatie is niet ingesteld.");
                }
                return true;
            }
            return false;
        }
    }

    private class MLGRushCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("mlgrush")) {
                if (mlgrushLobbyLocation != null) {
                    player.teleport(mlgrushLobbyLocation);
                    giveMLGRushInventory(player);
                    player.sendMessage(ChatColor.GREEN + "Je bent naar de MLGRush lobby geteleporteerd.");
                } else {
                    player.sendMessage(ChatColor.RED + "MLGRush lobby locatie is niet ingesteld.");
                }
                return true;
            }
            return false;
        }

        private void giveMLGRushInventory(Player player) {
            player.getInventory().clear();
            player.getInventory().setItem(1, new ItemStack(Material.IRON_SWORD));
            player.getInventory().setItem(2, new ItemStack(Material.SANDSTONE, 64));
            player.getInventory().setItem(7, new ItemStack(Material.STICK)); // Placeholder
            player.getInventory().setItem(8, new ItemStack(Material.IRON_PICKAXE));
        }
    }

    private class RankCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("rank")) {
                if (args.length < 3) {
                    player.sendMessage(ChatColor.RED + "Gebruik: /rank set [player] [rank] [tijd]");
                    return true;
                }
                String playerName = args[1];
                String rankName = args[2];
                int duration = Integer.parseInt(args[3]);

                playerRanks.put(playerName, rankName);
                getConfig().set("players." + playerName, rankName);
                saveConfig();
                player.sendMessage(ChatColor.GREEN + "Rank " + rankName + " ingesteld voor " + playerName + " voor " + duration + " dagen.");
                return true;
            }
            return false;
        }
    }

    private class NickCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("nick")) {
                String randomNick
@Override
    public void onEnable() {
        getLogger().info("MyPlugin has been enabled!");

        // Voeg hier je command om de plugin te starten
        getCommand("cleardirtandbedrock").setExecutor((sender, command, label, args) -> {
            if (sender.isOp()) {
                clearDirtAndBedrock();
                sender.sendMessage("All dirt and bedrock have been cleared!");
            } else {
                sender.sendMessage("You must be an operator to use this command.");
            }
            return true;
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("MyPlugin has been disabled!");
    }

    // Functie om alle aarde en bedrock te verwijderen
    private void clearDirtAndBedrock() {
        for (World world : Bukkit.getWorlds()) {
            for (int x = world.getMinHeight(); x < world.getMaxHeight(); x++) {
                for (int z = world.getMinHeight(); z < world.getMaxHeight(); z++) {
                    for (int y = 0; y < world.getMaxHeight(); y++) {
                        Block block = world.getBlockAt(x, y, z);
                        if (block.getType() == Material.DIRT || block.getType() == Material.BEDROCK) {
                            block.setType(Material.AIR);
                        }
                    }
                }
            }
        }
    }
}
