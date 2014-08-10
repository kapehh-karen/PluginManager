package me.kapehh.main.pluginmanager.parsers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Created by Karen on 10.08.2014.
 */
public class PluginParserLocation {

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
            } else if (p.length >= 4) {
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
