package me.kapehh.main.pluginmanager.otherplugins;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Location;

/**
 * Created by karen on 27.09.2016.
 */
public class WorldGuardAPIUtil {
    public static boolean isRegionPVP(Location location) {
        // Start check WorldGuard PVP
        RegionManager regionManager = WGBukkit.getPlugin().getRegionManager(location.getWorld());
        StateFlag.State queryPvP = regionManager.getApplicableRegions(location).queryValue(null, DefaultFlag.PVP);
        return StateFlag.State.ALLOW.equals(queryPvP);
    }
}
