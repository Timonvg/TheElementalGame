package nl.curio.elemental;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;

public final class Elemental extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("elementinfo").setExecutor(new ElementInfoCommand());
        Bukkit.getScheduler().runTaskLater(this, () -> {
            Bukkit.getLogger().info("KillTrackerPlugin has been enabled!");
        }, 20L); // 1-second delay to ensure the server is initialized
        Bukkit.getPluginManager().registerEvents(new KillTracker(), this);
        getCommand("pickElement").setExecutor(new pickElement());
        getCommand("pickElement").setTabCompleter(new pickElement());

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team fireTeam = scoreboard.getTeam("Fire");
        Team waterTeam = scoreboard.getTeam("Water");
        Team airTeam = scoreboard.getTeam("Air");
        Team earthTeam = scoreboard.getTeam("Earth");
        if (fireTeam == null) {
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
//Makes every blaze team fire
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BlazeTeamListener(), this);
        Bukkit.getWorlds().forEach(world ->
                world.getEntitiesByClass(Blaze.class).forEach(blaze ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Fire " + blaze.getUniqueId().toString())
                )
        );

//        Makes every (elder)guardian team water
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

//        Makes every breeze team air
        pm.registerEvents(new BreezeTeamListener(), this);
        Bukkit.getWorlds().forEach(world ->
                world.getEntitiesByClass(Breeze.class).forEach(Breeze ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Air " + Breeze.getUniqueId().toString())
                )
        );

//        Makes every cave spider team earth
        pm.registerEvents(new CSpiderTeamListener(), this);
        Bukkit.getWorlds().forEach(world ->
                world.getEntitiesByClass(CaveSpider.class).forEach(CaveSpider ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Earth " + CaveSpider.getUniqueId().toString())
                )
        );

        pm.registerEvents(new FireballShooter(), this);
        pm.registerEvents(new ArrowShooter(),this);
        pm.registerEvents(new EggSmasher(),this);
        pm.registerEvents(new ElementalLocationBoosts(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
