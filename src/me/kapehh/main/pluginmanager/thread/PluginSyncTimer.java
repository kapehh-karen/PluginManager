package me.kapehh.main.pluginmanager.thread;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karen on 23.04.2015.
 */
class PluginSyncTimer extends BukkitRunnable {
    JavaPlugin plugin;
    List<PluginSyncTask> syncTasks;

    public PluginSyncTimer(JavaPlugin plugin) {
        this.plugin = plugin;
        this.syncTasks = new ArrayList<PluginSyncTask>();
    }

    public void runTask(IPluginAsyncTask iPluginAsyncTask, int id, Object obj, Throwable throwable) {
        if (obj == null && throwable == null) return;
        syncTasks.add(new PluginSyncTask(id, iPluginAsyncTask, obj, throwable));
    }

    @Override
    public void run() {
        IPluginAsyncTask iPluginAsyncTask;
        PluginSyncTask task;
        while (syncTasks.size() > 0) {
            task = syncTasks.get(0);

            iPluginAsyncTask = task.getiPluginAsyncTask();
            if (task.isSuccess())
                iPluginAsyncTask.onSuccess(task.getId(), task.getObj());
            else if (task.isFailure())
                iPluginAsyncTask.onFailure(task.getId(), task.getThrowable());

            syncTasks.remove(0);
        }
    }
}
