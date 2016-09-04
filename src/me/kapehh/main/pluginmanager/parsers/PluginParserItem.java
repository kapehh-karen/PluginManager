package me.kapehh.main.pluginmanager.parsers;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Karen on 10.08.2014.
 */
public class PluginParserItem {
    // TODO: Засунуть в Util

    public static ItemStack parseItem(String item) {
        String type;
        short damage;
        item = item.trim();

        if (item.matches("^[^:]+(:[0-9]+)?$")) {
            String[] p = item.split(":");

            type = p[0];
            if (p.length > 1) {
                damage = Short.valueOf(p[1]);
            } else {
                damage = 0;
            }

            return new ItemStack(Material.valueOf(type), 1, damage);
        }

        return null;
    }

    public static String toString(ItemStack item) {
        if (item.getDurability() != 0)
            return String.format("%s:%s", item.getType(), item.getDurability());
        else
            return String.valueOf(item.getType());
    }
}
