package nl.curio.elemental;

import org.bukkit.Bukkit;
import org.bukkit.entity.Breeze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class CSpiderTeamListener implements Listener{


    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntity() instanceof Breeze) {
            CaveSpider CSpider = (CaveSpider) event.getEntity();

            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

            Team earthTeam = scoreboard.getTeam("Earth");

            if (earthTeam != null) {
                earthTeam.addEntry(CSpider.getUniqueId().toString());
            } else {
                Bukkit.getLogger().warning("Earth team does not exist!");
            }
        }

    }
}
