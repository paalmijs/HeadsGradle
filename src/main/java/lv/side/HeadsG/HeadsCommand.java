package lv.side.HeadsG;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class HeadsCommand implements CommandExecutor {

    private final HeadsMain headDatabaseAPI;
    private final Random random;

    public HeadsCommand(HeadsMain headDatabaseAPI) {
        this.headDatabaseAPI = headDatabaseAPI;
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
            player.getInventory().addItem(headItem);

            player.sendMessage("You have been given a random head!");
        } else {
            String argument = args[0];

                HeadDatabaseAPI api = new HeadDatabaseAPI();
                ItemStack headItem = api.getItemHead(argument);
                if (headItem != null) {
                    player.getInventory().addItem(headItem);
                    player.sendMessage("You have been given the head with ID: " + argument);
                } else {
                    player.sendMessage("Failed to retrieve head with ID: " + argument);
                }

            return true;
        }

        return true;
    }
}