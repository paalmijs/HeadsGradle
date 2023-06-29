package lv.side.HeadsG;

import me.arcaniax.hdb.api.DatabaseLoadEvent;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class HeadDatabaseAPIPlugin extends JavaPlugin implements Listener {

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onDatabaseLoad(DatabaseLoadEvent e) {
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        try {
            ItemStack item = api.getItemHead("7129");
            getLogger().info(api.getItemID(item));
        } catch (NullPointerException nullPointerException) {
            getLogger().info("Could not find the head you were looking for");
        }
    }
}