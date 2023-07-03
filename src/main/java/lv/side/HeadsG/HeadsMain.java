package lv.side.HeadsG;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

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
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        headOptions = config.getStringList("head-options");
    }

    public List<String> getHeadOptions() {
        return headOptions;
    }
}
