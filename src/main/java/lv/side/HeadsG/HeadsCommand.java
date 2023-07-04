package lv.side.HeadsG;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class HeadsCommand implements CommandExecutor, TabCompleter {

    private final HeadsMain headsMain;
    private final Random random;
    private final Map<UUID, List<String>> playerHeads;
    private final File dataFolder;
    private BukkitRunnable saveDataTask;

    public HeadsCommand(HeadsMain headsMain) {
        this.headsMain = headsMain;
        this.random = new Random();
        this.playerHeads = new HashMap<>();
        this.dataFolder = new File(headsMain.getDataFolder(), "player_data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        this.saveDataTask = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;
        UUID playerId = player.getUniqueId();

        if (args.length == 0) {
            if (saveDataTask != null) {
                saveDataTask.cancel();
                saveDataTask = null;
                player.sendMessage("The random head generation has been stopped.");
            } else {
                player.sendMessage("You will be given a random head every 1-3 seconds!");
                startRandomHeadTask(playerId);
            }
        } else if (args.length == 1) {
        }
        return true;
    }

    private void startRandomHeadTask(UUID playerId) {
        saveDataTask = new BukkitRunnable() {
            @Override
            public void run() {
                HeadDatabaseAPI api = new HeadDatabaseAPI();
                List<String> headOptions = headsMain.getHeadOptions();

                if (!headOptions.isEmpty()) {
                    String randomHead = headOptions.get(random.nextInt(headOptions.size()));
                    ItemStack headItem = api.getItemHead(randomHead);

                    if (headItem != null) {
                        List<String> playerHeadsList = playerHeads.getOrDefault(playerId, new ArrayList<>());

                        if (!playerHeadsList.contains(randomHead)) {
                            Player player = Bukkit.getPlayer(playerId);
                            if (player != null) {
                                player.getInventory().addItem(headItem);
                                player.sendMessage("You have been given a random head with ID: " + randomHead);

                                playerHeadsList.add(randomHead);
                                playerHeads.put(playerId, playerHeadsList);

                                saveHeadToPlayerFile(playerId, randomHead);
                            }
                        }
                    }
                }
            }
        };
        int delay = random.nextInt(3) + 1;
        saveDataTask.runTaskTimerAsynchronously(headsMain, delay * 20, delay * 20);
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

    private class SaveDataBukkitRunnable extends BukkitRunnable {
        @Override
        public void run() {
            for (UUID playerId : playerHeads.keySet()) {
                List<String> playerHeadsList = playerHeads.get(playerId);
                if (playerHeadsList.isEmpty()) {
                    continue;
                }

                File playerDataFile = new File(dataFolder, playerId + ".txt");

                try (FileWriter writer = new FileWriter(playerDataFile, true)) {
                    for (String head : playerHeadsList) {
                        writer.write(head + System.lineSeparator());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                playerHeadsList.clear();
            }
        }
    }

    // Metode, kas saglabā spēlētāja galvu personīgā failā
    private void saveHeadToPlayerFile(UUID playerId, String headId) {
        File playerDataFile = new File(dataFolder, playerId + ".txt");

        try (FileWriter writer = new FileWriter(playerDataFile, true)) {
            writer.write(headId + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
