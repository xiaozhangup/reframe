package me.eereeska.reframe;

import me.eereeska.reframe.configs.ItemFrameConfig;
import me.eereeska.reframe.listeners.ItemFrameMenuInventoryClickListener;
import me.eereeska.reframe.listeners.PlayerInteractAtEntityEventListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReFrame extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();

        final PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerInteractAtEntityEventListener(this), this);
        pm.registerEvents(new ItemFrameMenuInventoryClickListener(this), this);
    }
}
