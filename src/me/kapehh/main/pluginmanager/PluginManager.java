package me.kapehh.main.pluginmanager;

import me.kapehh.main.pluginmanager.logger.PluginLogger;
import me.kapehh.main.pluginmanager.parsers.PluginParserItem;
import me.kapehh.main.pluginmanager.parsers.PluginParserLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Karen on 15.07.2014.
 */
public class PluginManager extends JavaPlugin {
    // TODO: в parsers вставить механизм JavaScript для вычисления значений
    // TODO: в parsers вставить механизм выбора рандомного значения с условиями, например выбрать из пяти какой чар апнуть, если максимальный может быть 5
    // TODO: добавить функции randInt и randDouble

    /*public static void main(String[] args) {
        ItemStack item = PluginParserItem.parseItem("WOOL:3");
        System.out.println(PluginParserItem.toString(item));
    }*/
}
