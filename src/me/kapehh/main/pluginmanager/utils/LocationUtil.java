package me.kapehh.main.pluginmanager.utils;

import org.bukkit.Location;

/**
 * Created by karen on 25.08.2016.
 */
public class LocationUtil {

    public static boolean isCheckDistance(Location pos1, Location pos2, double distance) {
        // Worlds not equals
        if (!pos1.getWorld().equals(pos2.getWorld())) {
            return false;
        }
        return (pos1.distance(pos2) <= distance);
    }

}
