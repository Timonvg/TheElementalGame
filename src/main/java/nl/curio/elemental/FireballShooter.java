package nl.curio.elemental;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

public class FireballShooter implements Listener {

    // Method to get the team of a player
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
        // Check if the interaction is a right-click
        if (event.getAction().toString().contains("RIGHT_CLICK")) {

            // Ensure the player is holding a fire charge in their main hand
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.FIRE_CHARGE) {

                // Get the player's team
                String teamName = getPlayerTeam(player);

                if (teamName != null) {
                    // Example: Only allow players in the "Fire" team to shoot fireballs
                    if (teamName.equalsIgnoreCase("Fire")) {
                        // Launch a fireball
                        Fireball fireball = player.launchProjectile(Fireball.class);

                        // Set the fireball's velocity in the direction the player is looking
                        Vector direction = player.getLocation().getDirection().normalize();
                        fireball.setVelocity(direction.multiply(2)); // Adjust speed as needed

                        // Optionally, decrease the number of fire charges in the player's hand
                        if (item.getAmount() > 1) {
                            item.setAmount(item.getAmount() - 1);
                        } else {
                            player.getInventory().setItemInMainHand(null); // Remove fire charge
                        }

                        event.setCancelled(true); // Cancel any other interaction (like placing the fire charge)
                    } else {
                        player.sendMessage("§4You must be in the 'Fire' team to shoot fireballs.");
                    }
                } else {
                    player.sendMessage("§4You are not in any team.");
                }
            }
        }
    }
}
