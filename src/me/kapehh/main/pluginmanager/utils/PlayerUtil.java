package me.kapehh.main.pluginmanager.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by Karen on 12.04.2015.
 */
public class PlayerUtil {

    public static Player getOnlinePlayer(String name) {
        return getOnlinePlayer(name, false);
    }

    public static Player getOnlinePlayer(String name, boolean ignoreCase) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (ignoreCase) {
                if (player.getName().equalsIgnoreCase(name)) {
                    return player;
                }
            } else {
                if (player.getName().equals(name)) {
                    return player;
                }
            }
        }
        return null;
    }

}
