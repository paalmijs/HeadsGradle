package lv.side.HeadsG;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;

public class HeadsMain extends JavaPlugin {
    private List<String> headOptions;

    @Override
    public void onEnable() {
        getLogger().info("HeadsM has been enabled!");

        loadConfigValues();

        BukkitRunnable runnable = new MyBukkitRunnable();
        getCommand("gethead").setExecutor(new HeadsCommand(this));
        getCommand("gethead").setTabCompleter(new HeadsCommand(this));
    }


    @Override
    public void onDisable() {
        getLogger().info("HeadsM has been disabled!");
    }

    private void loadConfigValues() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        headOptions = config.getStringList("head-options");
    }

    public List<String> getHeadOptions() {
        return headOptions;
    }
}
class MyBukkitRunnable extends BukkitRunnable {
    public void run() {

    }
}