package me.kapehh.main.pluginmanager.otherplugins;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Coord;
import com.palmergames.bukkit.towny.object.WorldCoord;
import org.bukkit.Location;

/**
 * Created by karen on 27.09.2016.
 */
public class TownyAPIUtil {
    public static boolean isTownPVP(Location location) {
        // Start check Towny PVP
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

        return (isTownyPlot && isTownyPlotPVP) || (!isTownyPlot && isTownyWorldPVP);
    }
}
