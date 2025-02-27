package nl.curio.elemental;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.HashMap;
import java.util.UUID;

public class KillTracker implements Listener {

    private final HashMap<UUID, Integer> killCount = new HashMap<>();
    private final EntityType[] trackedMobs = {
            EntityType.CAVE_SPIDER,
            EntityType.GUARDIAN,
            EntityType.ELDER_GUARDIAN,
            EntityType.BLAZE,
            EntityType.BREEZE
    };

    @EventHandler
    public void onMobKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        Bukkit.getLogger().info("Killed entity type: " + event.getEntity().getType());  // Debugging line

        for (EntityType mob : trackedMobs) {
            if (event.getEntity().getType() == mob) {
                UUID playerId = killer.getUniqueId();
                int currentKills = killCount.getOrDefault(playerId, 0) + 1;
                killCount.put(playerId, currentKills);

                killer.sendMessage("You've killed " + currentKills + " tracked mobs!");

                if (currentKills >= 1) {
                    teleportToEnd(killer);
                    killCount.put(playerId, 90);  // Reset kills after teleport
                }
            }
        }
    }


    private void teleportToEnd(Player player) {
        World endWorld = Bukkit.getWorld("world_the_end");
        if (endWorld != null) {
            // Create a location with specific coordinates (100, 50, 0) (obsidian island)
            org.bukkit.Location targetLocation = new org.bukkit.Location(endWorld, 100, 50, 0);
            player.teleport(targetLocation);
            player.sendMessage("You have been teleported to The End!");
        } else {
            Bukkit.getLogger().warning("End world not found. Teleport failed.");
            player.sendMessage("The End world is not available.");
        }
    }

    @EventHandler
    public void onEndPortalInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null &&
                event.getClickedBlock().getType() == Material.END_PORTAL_FRAME &&
                event.getItem() != null &&
                event.getItem().getType() == Material.ENDER_EYE) {

            event.setCancelled(true);
            event.getPlayer().sendMessage("End portals cannot be activated with Eyes of Ender.");
        }
    }
}
