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

    public String getPlayerTeam(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        for (Team team : scoreboard.getTeams()) {
            if (team.hasEntry(player.getName())) {
                return team.getName();
            }
        }
        return null;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().toString().contains("RIGHT_CLICK")) {

            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.FIRE_CHARGE) {

                String teamName = getPlayerTeam(player);

                if (teamName != null) {
                    if (teamName.equalsIgnoreCase("Fire")) {
                        Fireball fireball = player.launchProjectile(Fireball.class);

                        Vector direction = player.getLocation().getDirection().normalize();
                        fireball.setVelocity(direction.multiply(2));

                        // "Gebruik" er 1
                        if (item.getAmount() > 1) {
                            item.setAmount(item.getAmount() - 1);
                        } else {
                            player.getInventory().setItemInMainHand(null); // Remove fire charge
                        }

                        event.setCancelled(true);
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
