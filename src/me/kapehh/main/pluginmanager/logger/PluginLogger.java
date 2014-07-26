package me.kapehh.main.pluginmanager.logger;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Formatter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Created by Karen on 09.07.2014.
 */
public class PluginLogger {
    private Logger log;
    private JavaPlugin plugin;
    private String logName;
    private Formatter logFormat;

    public PluginLogger(JavaPlugin plugin, String logName) {
        this(plugin, logName, new PluginLoggerFormatter());
    }

    public PluginLogger(JavaPlugin plugin, String logName, Formatter logFormat) {
        this.plugin = plugin;
        this.logName = logName;
        this.log = Logger.getLogger("me.kapehh.main.pluginmanager.logger." + logName);
        this.logFormat = logFormat;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
    }

    public Logger getLog() {
        return log;
    }

    public PluginLogger setup() {
        try {
            FileHandler fh = new FileHandler(new File(plugin.getDataFolder(), logName + ".log").getAbsolutePath(), true);
            fh.setFormatter(logFormat);
            log.setUseParentHandlers(false);
            log.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginLogger shutDown() {
        for (Handler fh : log.getHandlers()) {
            log.removeHandler(fh);
            fh.close();
        }
        return this;
    }

}
