package me.kapehh.main.pluginmanager.utils;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Coord;
import com.palmergames.bukkit.towny.object.WorldCoord;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
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
        // Start check WorldGuard
        RegionManager regionManager = WGBukkit.getPlugin().getRegionManager(location.getWorld());
        StateFlag.State queryPvP = regionManager.getApplicableRegions(location).queryValue(null, DefaultFlag.PVP);
        boolean isWorldGuardPVP = StateFlag.State.ALLOW.equals(queryPvP);

        // Start check Towny
        WorldCoord worldCoord = new WorldCoord(location.getWorld().getName(), Coord.parseCoord(location));
        boolean isTownyWorldPVP = false;
        try {
            isTownyWorldPVP = worldCoord.getTownyWorld().isPVP() || worldCoord.getTownyWorld().isForcePVP();
        } catch (NotRegisteredException e) {

        }
        boolean isTownyPlot = false;
        boolean isTownyPlotPVP = false;
        try {
            isTownyPlot = (worldCoord.getTownBlock().getTown() != null);
            isTownyPlotPVP = worldCoord.getTownBlock().getPermissions().pvp;
        } catch (NotRegisteredException e) {

        }
        boolean isTownyPVP = (isTownyPlot && isTownyPlotPVP) || (!isTownyPlot && isTownyWorldPVP);

        // Result
        return isTownyPVP && isWorldGuardPVP;
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
