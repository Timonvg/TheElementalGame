package nl.curio.elemental;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class ArrowShooter implements Listener {

    // Cooldown times in ticks
    private final int sniperCooldownTicks = 100; // 5 seconds for Sniper bow
    private final int arrowCooldownTicks = 30;   // 1.5 seconds for arrow shooting
    private final HashMap<UUID, Long> sniperCooldowns = new HashMap<>();
    private final HashMap<UUID, Long> arrowCooldowns = new HashMap<>();

    public String getPlayerTeam(Player player) {
        // Get the main scoreboard
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        // Loop through all teams to check if the player is in any of them
        for (Team team : scoreboard.getTeams()) {
            if (team.hasEntry(player.getName())) {
                return team.getName(); // Return the name of the team the player is in
            }
        }
        return null; // Return null if the player is not in any team
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if the interaction is a left-click (PRIMARY)
        if (event.getAction().toString().contains("LEFT_CLICK")) {
            // Check if the player is holding the "Sniper" bow
            if (item.getType() == Material.BOW) {
                ItemMeta meta = item.getItemMeta();
                String teamName = getPlayerTeam(player);

                if (teamName != null) {
                    if (teamName.equalsIgnoreCase("Air")) {
                        if (meta != null && "Sniper".equalsIgnoreCase(meta.getDisplayName())) { // Assuming the bow is named "Sniper"
                            if (isInCooldown(player.getUniqueId(), sniperCooldowns, sniperCooldownTicks)) {
                                return; // Exit if still in cooldown
                            }
                            shootArrow(player, 4); // Faster speed for Sniper
                            player.setCooldown(Material.BOW, sniperCooldownTicks); // Set cooldown for the bow
                        }
                    }else {
                        player.sendMessage("§4You must be in an airbender to shoot arrows.");
                    }
                }else {
                    player.sendMessage("§4You are not a bender.");
                }
            }
        }

        // Check if the interaction is a right-click (SECONDARY)
        if (event.getAction().toString().contains("RIGHT_CLICK")) {
            // Ensure the player is holding an arrow in their main hand
            if (item.getType() == Material.ARROW) {
                // Get the player's team
                String teamName = getPlayerTeam(player);

                if (teamName != null) {
                    // Example: Only allow players in the "Air" team to shoot arrows
                    if (teamName.equalsIgnoreCase("Air")) {
                        if (isInCooldown(player.getUniqueId(), arrowCooldowns, arrowCooldownTicks)) {
                            return; // Exit if still in cooldown
                        }
                        shootArrow(player, 2); // Regular speed for arrow shooting
                        event.setCancelled(true); // Cancel any other interaction (like placing the arrow)
                        player.setCooldown(Material.ARROW, arrowCooldownTicks); // Set cooldown for the arrow
                    } else {
                        player.sendMessage("§4You must be in an airbender to shoot arrows.");
                    }
                } else {
                    player.sendMessage("§4You are not a bender.");
                }
            }
        }
    }

    private void shootArrow(Player player, double speed) {
        // Launch the arrow
        Arrow arrow = player.launchProjectile(Arrow.class);

        // Set the arrow's velocity in a straight line
        Vector direction = player.getLocation().getDirection().normalize();
        arrow.setVelocity(direction.multiply(speed)); // Adjust speed as needed

        // Disable gravity on the arrow
        arrow.setGravity(false);
    }

    private boolean isInCooldown(UUID playerId, HashMap<UUID, Long> cooldownMap, int cooldownTicks) {
        if (cooldownMap.containsKey(playerId)) {
            long timeSinceLastShot = System.currentTimeMillis() - cooldownMap.get(playerId);
            if (timeSinceLastShot < cooldownTicks * 50) { // Convert ticks to milliseconds
                return true; // Still in cooldown
            }
        }
        // Update cooldown time
        cooldownMap.put(playerId, System.currentTimeMillis());
        return false; // Not in cooldown
    }
}
