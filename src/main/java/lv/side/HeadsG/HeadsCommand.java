package lv.side.HeadsG;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeadsCommand implements CommandExecutor, TabCompleter {

    private final HeadsMain headsMain;
    private final Random random;

    public HeadsCommand(HeadsMain headsMain) {
        this.headsMain = headsMain;
        this.random = new Random();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            HeadDatabaseAPI api = new HeadDatabaseAPI();
            ItemStack headItem = api.getRandomHead();

            if (headItem != null) {
                player.getInventory().addItem(headItem);
                player.sendMessage("You have been given a random head!");
            } else {
                player.sendMessage("Failed to retrieve a random head.");
            }
        } else if (args.length == 1) {
            String argument = args[0];

            HeadDatabaseAPI api = new HeadDatabaseAPI();
            ItemStack headItem = api.getItemHead(argument);

            if (headItem != null) {
                player.getInventory().addItem(headItem);
                player.sendMessage("You have been given the head with ID: " + argument);
            } else {
                player.sendMessage("Failed to retrieve head with ID: " + argument);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            List<String> headOptions = headsMain.getHeadOptions();

            completions.addAll(headOptions);

            return completions;
        }

        return null;
    }
}