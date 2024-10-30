package me.hi12167pies.BedClutch.Events;

import me.hi12167pies.BedClutch.Main;
import me.hi12167pies.BedClutch.Utils.Arenas;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (!Arenas.isPlaying(player)) return;

        // Annuleer het standaard breekgedrag
        e.setCancelled(true);

        // Start de blokbreektaak met een vertraging
        Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
            // Breek het blok handmatig
            e.getBlock().breakNaturally();

            // Optioneel: geef een item terug aan de speler
            ItemStack drop = new ItemStack(e.getBlock().getType());
            player.getWorld().dropItemNaturally(e.getBlock().getLocation(), drop);

        }, 100); // 100 ticks is 5 seconden (20 ticks per seconde)
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (!Arenas.isPlaying(player)) return;

        // Controleer of de speler meer dan 31 items in de hand heeft
        if (player.getInventory().getItemInHand().getAmount() > 31) {
            player.getInventory().getItemInHand().setAmount(64);
        }

        // Blok plaatsen met vertraging
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
            e.getBlockPlaced().getLocation().getChunk().load();
            e.getBlockPlaced().setType(Material.REDSTONE_BLOCK);
        }, 80); // 80 ticks na het plaatsen

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
            e.getBlockPlaced().getLocation().getChunk().load();
            e.getBlockPlaced().setType(Material.AIR);
        }, 100); // 100 ticks voor het verwijderen van het blok
    }
}

