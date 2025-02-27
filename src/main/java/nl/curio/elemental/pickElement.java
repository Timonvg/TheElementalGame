package nl.curio.elemental;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

// Make sure the class is properly declared and placed within the correct file structure
public class pickElement implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Ensure the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;  // Command handled
        }

        Player player = (Player) sender;

        // Check if exactly one argument is provided
        if (args.length == 1) {
            String element = args[0].toLowerCase();  // Make case-insensitive
            try {
                // Check if the element is "fire"
                switch (element) {
                    case "fire":
                        // Add the player to the "Fire" team
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Fire " + player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect clear " + player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give " + player.getName() + " minecraft:fire_resistance infinite");
                        player.sendMessage("§cYou have gained fire powers!");
                        break;
                    case "§water":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Water " + player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect clear " + player.getName());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "effect give " + player.getName() + " minecraft:water_breathing infinite");
                        player.sendMessage("§bYou become one with the sea");
//
                        break;
                    case "air":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Air " + player.getName());
                        player.sendMessage("§8The air is your ally");
                        break;
                    case "earth":
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "team join Earth " + player.getName());
                        player.sendMessage("§aThe earth is on your side");
                        break;
                    default:
                        // If element is not recognized, send an error message
                        player.sendMessage("§4That element does not exist. Try again.");
                        break;
                }
            } catch (Exception e) {
                // Handle any exceptions and notify the player
                player.sendMessage("§4An error occurred while assigning your element. Please try again.");
            }
        } else {
            // Incorrect usage message
            player.sendMessage("§4Usage: /PickElement [element]");
        }

        return true;  // Command was processed correctly
    }
}
