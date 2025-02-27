package nl.curio.elemental;

import org.bukkit.Bukkit;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scoreboard.Team;

public class GuardianTeamListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Guardian) {
            Guardian Guardian = (Guardian) event.getEntity();

            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

            Team waterTeam = scoreboard.getTeam("Water");

            if (waterTeam != null) {
                waterTeam.addEntry(Guardian.getUniqueId().toString());
            } else {
                Bukkit.getLogger().warning("Water team does not exist!");
            }
        }
        if (event.getEntity() instanceof ElderGuardian) {
            ElderGuardian ElderGuardian = (ElderGuardian) event.getEntity();

            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

            Team waterTeam = scoreboard.getTeam("Water");

            if (waterTeam != null) {
                waterTeam.addEntry(ElderGuardian.getUniqueId().toString());
            } else{
                Bukkit.getLogger().warning("Water team does not exist!");
            }
        }
    }
}
