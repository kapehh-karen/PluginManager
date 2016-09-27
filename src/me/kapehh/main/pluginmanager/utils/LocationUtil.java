package me.kapehh.main.pluginmanager.utils;

import me.kapehh.main.pluginmanager.otherplugins.PluginsAPI;
import me.kapehh.main.pluginmanager.otherplugins.TownyAPIUtil;
import me.kapehh.main.pluginmanager.otherplugins.WorldGuardAPIUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

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

    // Required: Towny, WorldEdit, WorldGuard
    public static boolean isLocationPVP(Location location) {
        boolean res = true;

        if (PluginsAPI.TOWNY)
            res &= TownyAPIUtil.isTownPVP(location);

        if (PluginsAPI.WORLD_GUARD)
            res &= WorldGuardAPIUtil.isRegionPVP(location);

        // Result
        return res;
    }

    // world:x,y,z:pitch,yaw
    public static Location parseLocation(String location) {
        double x, y, z;
        float pitch, yaw;
        location = location.trim();

        if (location.matches("^[^,:]+(:[^,:]+,[^,:]+,[^,:]+(:[^,:]+,[^,:]+)?)?$")) {
            String[] p = location.split("(:|,)");
            World world = Bukkit.getWorld(p[0]);
            if (p.length < 4) {
                return world.getSpawnLocation();
            } else {
                x = Double.valueOf(p[1]);
                y = Double.valueOf(p[2]);
                z = Double.valueOf(p[3]);
                if (p.length >= 6) {
                    pitch = Float.valueOf(p[4]);
                    yaw = Float.valueOf(p[5]);
                } else {
                    pitch = yaw = 0;
                }
                return new Location(world, x, y, z, yaw, pitch);
            }
        }

        return null;
    }

    public static String toString(Location location) {
        return String.format(
                "%s:%f,%f,%f:%f,%f",
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getPitch(),
                location.getYaw()
        );
    }

}
