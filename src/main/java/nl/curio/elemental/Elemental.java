package nl.curio.elemental;

import org.bukkit.Bukkit;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.Guardian;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;

public final class Elemental extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("pickElement").setExecutor(new pickElement());

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team fireTeam = scoreboard.getTeam("Fire");
        Team waterTeam = scoreboard.getTeam("Water");
        Team airTeam = scoreboard.getTeam("Air");
        Team earthTeam = scoreboard.getTeam("Earth");
        if (fireTeam == null) {
            // Add the Blaze to the "Fire" team using its UUID
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team add Fire");
        }
        if (waterTeam == null){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team add Water");
        }
        if (airTeam == null) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team add Air");
        }
        if (earthTeam == null){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team add Earth");
        }

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BlazeTeamListener(), this);
        Bukkit.getWorlds().forEach(world ->
                world.getEntitiesByClass(Blaze.class).forEach(blaze ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Fire " + blaze.getUniqueId().toString())
                )
        );
        pm.registerEvents(new GuardianTeamListener(),this);
        Bukkit.getWorlds().forEach(world ->
                world.getEntitiesByClass(Guardian.class).forEach(Guardian ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Water " + Guardian.getUniqueId().toString())
                )
        );
        Bukkit.getWorlds().forEach(world ->
                world.getEntitiesByClass(ElderGuardian.class).forEach(ElderGuardian ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Water " + ElderGuardian.getUniqueId().toString())
                )
        );

        pm.registerEvents(new FireballShooter(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
