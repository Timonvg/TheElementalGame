package nl.curio.elemental;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class pickElement implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            String element = args[0].toLowerCase();
            try {
                switch (element) {
                    case "fire":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Fire " + player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect clear " + player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give " + player.getName() + " minecraft:fire_resistance infinite");
                        player.sendMessage("§cYou have gained fire powers!");
                        break;
                    case "water":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Water " + player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect clear " + player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give " + player.getName() + " minecraft:water_breathing infinite");
                        player.sendMessage("§bYou become one with the sea");
                        break;
                    case "air":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Air " + player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect clear " + player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give " + player.getName() + " minecraft:speed infinite 2");
                        player.sendMessage("§8The air is your ally");
                        break;
                    case "earth":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Earth " + player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect clear " + player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give " + player.getName() + " minecraft:jump_boost infinite 2");
                        player.sendMessage("§aThe earth is on your side");
                        break;
                    default:
                        player.sendMessage("§4That element does not exist. Try again.");
                        break;
                }
            } catch (Exception e) {
                player.sendMessage("§4An error occurred while assigning your element. Please try again.");
            }
        } else {
            player.sendMessage("§4Usage: /PickElement [element]");
        }

        return true;
    }

    // Autocompl
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("fire", "water", "air", "earth");
        }
        return null;  // Return null to show no suggestions if more than 1 argument is entered
    }
}
