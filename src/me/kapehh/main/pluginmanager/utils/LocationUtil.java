package me.kapehh.main.pluginmanager.utils;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Coord;
import com.palmergames.bukkit.towny.object.WorldCoord;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
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
        StateFlag.State globalPVP = regionManager.getRegion("__global__").getFlag(DefaultFlag.PVP);
        boolean isGlobalPVP = StateFlag.State.ALLOW.equals(globalPVP);

        //System.out.println("Global PVP: " + isGlobalPVP);

        boolean isContainsRegions = false;
        boolean isRegionPVP = true;
        for (ProtectedRegion protectedRegion : regionManager.getApplicableRegions(location).getRegions()) {
            StateFlag.State pvp = protectedRegion.getFlag(DefaultFlag.PVP);
            isRegionPVP &= StateFlag.State.ALLOW.equals(pvp);
            isContainsRegions = true;
        }

        //System.out.println("Regions PVP: " + isRegionPVP);

        boolean isWorldGuardPVP = isContainsRegions ? isRegionPVP : isGlobalPVP;

        //System.out.println("IS WG PVP: " + isWorldGuardPVP);

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

        //System.out.println("IS TOWNY PVP: " + isTownyPVP);

        // Если и то и это разрешают, то почему бы и нет
        return isTownyPVP && isWorldGuardPVP;
    }

}
