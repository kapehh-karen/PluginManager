package me.kapehh.main.pluginmanager;

import me.kapehh.main.pluginmanager.config.PluginConfig;
import me.kapehh.main.pluginmanager.logger.PluginLogger;
import me.kapehh.main.pluginmanager.parsers.PluginParserItem;
import me.kapehh.main.pluginmanager.parsers.PluginParserLocation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Karen on 15.07.2014.
 */
public class PluginManager extends JavaPlugin implements CommandExecutor {
    // TODO: в parsers вставить механизм JavaScript для вычисления значений
    // TODO: в parsers вставить механизм выбора рандомного значения с условиями, например выбрать из пяти какой чар апнуть, если максимальный может быть 5
    // TODO: добавить функции randInt и randDouble
    // TODO: возможно добавить функцию вычисления расстояния от Location до Location

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.DARK_RED + "Forbidden! Only OP players.");
            return true;
        }

        if (args.length < 2) {
            return false;
        }

        String method = args[0];
        String pluginName = args[1];

        if (method.equals("reload")) {
            if (PluginConfig.loadData(pluginName)) {
                sender.sendMessage("[PluginManager] Plugin reloaded!");
            } else {
                sender.sendMessage("[PluginManager] Plugin not found!");
            }
            return true;
        }
        return false;
    }

    @Override
    public void onEnable() {
        getCommand("pluginmanager").setExecutor(this);
    }
}
