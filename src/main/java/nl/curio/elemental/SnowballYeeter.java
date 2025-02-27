package nl.curio.elemental;

import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class SnowballYeeter implements Listener {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime = 5000; // 5 seconds in milliseconds

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (cooldowns.containsKey(playerId)) {
                long lastUsed = cooldowns.get(playerId);
                if (currentTime - lastUsed < cooldownTime) {
                    player.sendMessage("Ability on cooldown!");
                    return;
                }
            }

            if (player.getScoreboard().getEntryTeam(player.getName()) != null &&
                    player.getScoreboard().getEntryTeam(player.getName()).getName().equalsIgnoreCase("Water")) {
                if (player.getInventory().getItemInMainHand().getType() == Material.SNOWBALL) {
                    Snowball snowball = player.launchProjectile(Snowball.class);
                    snowball.setVelocity(player.getLocation().getDirection().multiply(1.5));
                    cooldowns.put(playerId, currentTime);
                    player.setCooldown(Material.SNOWBALL, (int) (cooldownTime / 50)); // Set visual cooldown
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();
            if (event.getHitEntity() instanceof Player) {
                Player hitPlayer = (Player) event.getHitEntity();
                hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 1)); // 2 seconds of slowness
            }
        }
    }
}
