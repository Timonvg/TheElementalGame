package nl.curio.elemental;

import org.bukkit.Bukkit;
import org.bukkit.entity.Blaze;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scoreboard.Team;

public class BlazeTeamListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Blaze) {
            Blaze blaze = (Blaze) event.getEntity();

            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

            Team fireTeam = scoreboard.getTeam("Fire");

            if (fireTeam != null) {
                fireTeam.addEntry(blaze.getUniqueId().toString());
            } else {
                Bukkit.getLogger().warning("Fire team does not exist!");
            }
        }
    }
}
