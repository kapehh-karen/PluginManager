package me.kapehh.main.pluginmanager.utils;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Coord;
import com.palmergames.bukkit.towny.object.WorldCoord;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
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

    // Required: Towny, WorldEdit, WorldGuard
    public static boolean isLocationPVP(Location location) {
        RegionManager regionManager = WGBukkit.getPlugin().getRegionManager(location.getWorld());
        StateFlag.State queryPvP = regionManager.getApplicableRegions(location).queryValue(null, DefaultFlag.PVP);
        boolean isWorldGuardPVP = StateFlag.State.ALLOW.equals(queryPvP);

        //System.out.println("-> isWorldGuardPVP: " + isWorldGuardPVP);

        WorldCoord worldCoord = new WorldCoord(location.getWorld().getName(), Coord.parseCoord(location));
        boolean isTownyWorldPVP = false;
        try {
            isTownyWorldPVP = worldCoord.getTownyWorld().isPVP() || worldCoord.getTownyWorld().isForcePVP();
        } catch (NotRegisteredException e) {

        }

        //System.out.println("isTownyWorldPVP: " + isTownyWorldPVP);

        boolean isTownyPlot = false;
        boolean isTownyPlotPVP = false;
        try {
            isTownyPlot = (worldCoord.getTownBlock().getTown() != null);
            isTownyPlotPVP = worldCoord.getTownBlock().getPermissions().pvp;
        } catch (NotRegisteredException e) {

        }

        //System.out.println("isTownyPlot: " + isTownyPlot + ", isTownyPlotPVP: " + isTownyPlotPVP);

        boolean isTownyPVP = (isTownyPlot && isTownyPlotPVP) || (!isTownyPlot && isTownyWorldPVP);

        //System.out.println("-> isTownyPVP: " + isTownyPVP);

        // Если и то и это разрешают, то PVP разрешено
        return isTownyPVP && isWorldGuardPVP;
    }

}
