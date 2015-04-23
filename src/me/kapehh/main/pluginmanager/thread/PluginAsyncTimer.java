package me.kapehh.main.pluginmanager.thread;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karen on 23.04.2015.
 */
public class PluginAsyncTimer extends BukkitRunnable {
    boolean isCancelled;
    JavaPlugin plugin;
    PluginSyncTimer pluginSyncTimer;
    List<PluginAsyncTask> asyncTasks;

    public PluginAsyncTimer(JavaPlugin plugin) {
        this.plugin = plugin;
        this.asyncTasks = new ArrayList<PluginAsyncTask>();
        this.pluginSyncTimer = new PluginSyncTimer(plugin);
        this.isCancelled = false;
    }

    public void start(long period) {
        runTaskAsynchronously(plugin); // async
        pluginSyncTimer.runTaskTimer(plugin, 0, period); // sync
    }

    public void stop() {
        cancel(); // async
        pluginSyncTimer.cancel(); // sync
    }

    public void runTask(IPluginAsyncTask iPluginAsyncTask, int id, Object... params) {
        asyncTasks.add(new PluginAsyncTask(id, iPluginAsyncTask, params));
    }

    @Override
    public void run() {
        IPluginAsyncTask iPluginAsyncTask;
        PluginAsyncTask task;
        Object ret;
        boolean isThrows;

        while (!isCancelled) {
            while (asyncTasks.size() > 0) {
                task = asyncTasks.get(0);

                ret = null;
                iPluginAsyncTask = task.getiPluginAsyncTask();
                try {
                    ret = iPluginAsyncTask.doRun(task.getId(), task.getParams());
                    isThrows = false;
                } catch (Throwable throwable) {
                    pluginSyncTimer.runTask(PluginSyncTask.IS_FAILURE, iPluginAsyncTask, task.getId(), null, throwable);
                    isThrows = true;
                }
                if (!isThrows) pluginSyncTimer.runTask(PluginSyncTask.IS_SUCCESS, iPluginAsyncTask, task.getId(), ret, null);

                asyncTasks.remove(0);
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) { }
        }
    }

    @Override
    public void cancel() throws IllegalStateException {
        isCancelled = true;
        super.cancel();
    }
}
