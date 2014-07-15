package me.kapehh.main.pluginmanager.checker;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Karen on 15.07.2014.
 */
public class PluginChecker {
    private JavaPlugin plugin;

    public PluginChecker(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean check(String pluginName) {
        return this.check(pluginName, true);
    }

    public boolean check(String pluginName, boolean disableIfNotFound) {
        if (plugin.getServer().getPluginManager().getPlugin(pluginName) == null) {
            if (disableIfNotFound) {
                plugin.getLogger().info(pluginName + " not found!!!");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
            }
            return false;
        }
        return true;
    }

    public Object get(String pluginName) {
        return plugin.getServer().getPluginManager().getPlugin(pluginName);
    }

}
