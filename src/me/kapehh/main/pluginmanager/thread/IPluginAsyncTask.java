package me.kapehh.main.pluginmanager.thread;

/**
 * Created by Karen on 23.04.2015.
 */
public interface IPluginAsyncTask {
    public Object doRun(int id, Object[] params) throws Throwable;
    public void onSuccess(int id, Object res);
    public void onFailure(int id, Throwable err);
}