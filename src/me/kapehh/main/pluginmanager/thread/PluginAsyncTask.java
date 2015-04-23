package me.kapehh.main.pluginmanager.thread;

import java.util.Arrays;

/**
 * Created by Karen on 23.04.2015.
 */
class PluginAsyncTask {
    int id;
    IPluginAsyncTask iPluginAsyncTask;
    Object[] params;

    public PluginAsyncTask(int id, IPluginAsyncTask iPluginAsyncTask, Object[] params) {
        this.id = id;
        this.iPluginAsyncTask = iPluginAsyncTask;
        this.params = params;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IPluginAsyncTask getiPluginAsyncTask() {
        return iPluginAsyncTask;
    }

    public void setiPluginAsyncTask(IPluginAsyncTask iPluginAsyncTask) {
        this.iPluginAsyncTask = iPluginAsyncTask;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "PluginAsyncTask{" +
                "id=" + id +
                ", iPluginAsyncTask=" + iPluginAsyncTask +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
