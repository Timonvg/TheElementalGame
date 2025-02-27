package nl.curio.elemental;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ElementalLocationBoosts implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        World world = location.getWorld();
        String element = getPlayerElement(player);

        if (element == null) return;

        // Nether boosts for fire element
        if (element.equalsIgnoreCase("Fire") && world.getEnvironment() == World.Environment.NETHER) {
            applyBoost(player, PotionEffectType.SPEED, 200, 1); //10 sec
            applyBoost(player, PotionEffectType.JUMP_BOOST, 200, 1); //10 sec
        }
        // Water boosts in oceans and rivers
        else if (element.equalsIgnoreCase("Water") && isWaterBiome(location)) {
            applyBoost(player, PotionEffectType.WATER_BREATHING, 200, 0); //10 sec
            applyBoost(player, PotionEffectType.DOLPHINS_GRACE, 200, 0); //10 sec
        }
        // Air boosts in mountains or high altitudes
        else if (element.equalsIgnoreCase("Air") && location.getY() > 120) {
            applyBoost(player, PotionEffectType.SLOW_FALLING, 200, 0); //10 sec
            applyBoost(player, PotionEffectType.JUMP_BOOST, 200, 1); //10 sec
        }
        // Earth boosts in caves or underground
        else if (element.equalsIgnoreCase("Earth") && location.getBlockY() < 30) {
            applyBoost(player, PotionEffectType.RESISTANCE, 200, 1); //10 sec
            applyBoost(player, PotionEffectType.STRENGTH, 200, 0); //10 sec
        }
    }

    private boolean isWaterBiome(Location location) {
        Material material = location.getBlock().getType();
        return material == Material.WATER;
    }

    private String getPlayerElement(Player player) {
        if (player.getScoreboard().getEntryTeam(player.getName()) != null) {
            return player.getScoreboard().getEntryTeam(player.getName()).getName();
        }
        return null;
    }

    private void applyBoost(Player player, PotionEffectType effect, int duration, int amplifier) {
        player.addPotionEffect(new PotionEffect(effect, duration, amplifier, true, false));
    }
}
