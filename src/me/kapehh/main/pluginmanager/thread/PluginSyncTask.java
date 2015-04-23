package me.kapehh.main.pluginmanager.thread;

/**
 * Created by Karen on 23.04.2015.
 */
class PluginSyncTask {
    public static final int IS_SUCCESS = 1;
    public static final int IS_FAILURE = 2;

    int type;
    int id;
    IPluginAsyncTask iPluginAsyncTask;
    Object obj;
    Throwable throwable;

    public PluginSyncTask(int type, int id, IPluginAsyncTask iPluginAsyncTask, Object obj, Throwable throwable) {
        this.id = id;
        this.iPluginAsyncTask = iPluginAsyncTask;
        this.obj = obj;
        this.throwable = throwable;
    }

    public boolean isSuccess() {
        return type == IS_SUCCESS;
    }

    public boolean isFailure() {
        return type == IS_FAILURE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public IPluginAsyncTask getiPluginAsyncTask() {
        return iPluginAsyncTask;
    }

    public void setiPluginAsyncTask(IPluginAsyncTask iPluginAsyncTask) {
        this.iPluginAsyncTask = iPluginAsyncTask;
    }

    @Override
    public String toString() {
        return "PluginSyncTask{" +
                "id=" + id +
                ", iPluginAsyncTask=" + iPluginAsyncTask +
                ", obj=" + obj +
                ", throwable=" + throwable +
                '}';
    }
}
