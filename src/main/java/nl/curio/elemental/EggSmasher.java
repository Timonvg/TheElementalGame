package nl.curio.elemental;

import org.bukkit.Material;
import org.bukkit.entity.Egg;
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

public class EggSmasher implements Listener {

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime = 7000; // 7 seconds in milliseconds

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
                    player.getScoreboard().getEntryTeam(player.getName()).getName().equalsIgnoreCase("Fire")) {
                if (player.getInventory().getItemInMainHand().getType() == Material.EGG) {
                    Egg egg = player.launchProjectile(Egg.class);
                    egg.setVelocity(player.getLocation().getDirection().multiply(1.8));
                    cooldowns.put(playerId, currentTime);
                    player.setCooldown(Material.EGG, (int) (cooldownTime / 50)); // Set visual cooldown
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Egg) {
            Egg egg = (Egg) event.getEntity();
            if (event.getHitEntity() instanceof Player) {
                Player hitPlayer = (Player) event.getHitEntity();
                hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 1)); // 2 seconds of slowness
                hitPlayer.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 40, 1)); // Simulate stun
            }
        }
    }
}
