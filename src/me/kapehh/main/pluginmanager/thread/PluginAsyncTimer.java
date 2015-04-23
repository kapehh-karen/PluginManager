package me.kapehh.main.pluginmanager.thread;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karen on 23.04.2015.
 */
public class PluginAsyncTimer extends BukkitRunnable {
    final Object syncObj = new Object();
    JavaPlugin plugin;
    PluginSyncTimer pluginSyncTimer;
    List<PluginAsyncTask> asyncTasks;

    public PluginAsyncTimer(JavaPlugin plugin) {
        this.plugin = plugin;
        this.asyncTasks = new ArrayList<PluginAsyncTask>();
        this.pluginSyncTimer = new PluginSyncTimer(plugin);
    }

    public void start(long period) {
        runTaskTimerAsynchronously(plugin, 0, period); // async
        pluginSyncTimer.runTaskTimer(plugin, 0, period); // sync
    }

    public void stop() {
        cancel(); // async
        pluginSyncTimer.cancel(); // sync
    }

    public void runTask(IPluginAsyncTask iPluginAsyncTask, int id, Object... params) {
        synchronized (syncObj) {
            asyncTasks.add(new PluginAsyncTask(id, iPluginAsyncTask, params));
        }
    }

    @Override
    public void run() {
        synchronized (syncObj) {
            IPluginAsyncTask iPluginAsyncTask;
            Object ret;
            boolean isThrows;
            for (PluginAsyncTask task : asyncTasks) {
                ret = null;
                iPluginAsyncTask = task.getiPluginAsyncTask();
                try {
                    ret = iPluginAsyncTask.doRun(task.getId(), task.getParams());
                    isThrows = false;
                } catch (Throwable throwable) {
                    pluginSyncTimer.runTask(iPluginAsyncTask, task.getId(), null, throwable);
                    isThrows = true;
                }
                if (!isThrows) pluginSyncTimer.runTask(iPluginAsyncTask, task.getId(), ret, null);
            }
        }
    }
}
