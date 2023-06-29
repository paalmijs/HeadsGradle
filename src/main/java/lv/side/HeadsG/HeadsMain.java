package lv.side.HeadsG;


import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;


public class HeadsMain extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getLogger().info("HeadsM has been enabled!");
        getCommand("gethead").setExecutor(new HeadsCommand((this)));
    }
    @Override
    public void onDisable() {
        getLogger().info("HeadsM has been disabled!");
    }

}
