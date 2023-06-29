package lv.side.HeadsG;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class HeadsMain extends JavaPlugin {
    private List<String> headOptions;

    @Override
    public void onEnable() {
        getLogger().info("HeadsM has been enabled!");

        loadConfigValues();

        getCommand("gethead").setExecutor(new HeadsCommand(this));
        getCommand("gethead").setTabCompleter(new HeadsCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("HeadsM has been disabled!");
    }

    private void loadConfigValues() {

        File dataFolder = getDataFolder();


        File configFile = new File(dataFolder, "config.yml");
        if (configFile.exists()) {

            FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            headOptions = config.getStringList("head-options");
        } else {
            getLogger().warning("Config file not found. Creating a new one.");
            saveDefaultConfig();
        }
    }

    public List<String> getHeadOptions() {
        return headOptions;
    }
}
