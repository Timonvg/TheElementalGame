package nl.curio.elemental;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ElementInfoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            player.sendMessage("Usage: /elementinfo <element>");
            return true;
        }

        String element = args[0].toLowerCase();

        switch (element) {
            case "fire":
                player.sendMessage("Fire Element: Grants speed and jump boost in the Nether. Blazes are on your side");
                break;

            case "water":
                player.sendMessage("Water Element: Increases swim speed and grants water breathing. (Elder)Guardians are your bff");
                break;

            case "earth":
                player.sendMessage("Earth Element: Provides knockback resistance and slower fall speed. Drink a beer with your buddy the cave spider");
                break;

            case "air":
                player.sendMessage("Air Element: Grants slow falling and faster movement in the sky. Breezes are very cool, so they wont attack you.");
                break;

            default:
                player.sendMessage("Unknown element. Available elements: fire, water, earth, air.");
                break;
        }
        return true;
    }
}
