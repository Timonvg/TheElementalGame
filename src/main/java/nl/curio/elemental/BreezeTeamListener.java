package nl.curio.elemental;

import org.bukkit.Bukkit;
import org.bukkit.entity.Breeze;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class BreezeTeamListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Breeze) {
            Breeze breeze = (Breeze) event.getEntity();

            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

            Team fireTeam = scoreboard.getTeam("Air");

            if (fireTeam != null) {
                fireTeam.addEntry(breeze.getUniqueId().toString());
            } else {
                Bukkit.getLogger().warning("Air team does not exist!");
            }
        }

    }
}