package me.kapehh.main.pluginmanager.thread;

/**
 * Created by Karen on 23.04.2015.
 */
public interface IPluginAsyncTask {
    Object doRun(int id, Object[] params) throws Throwable;
    void onSuccess(int id, Object res);
    void onFailure(int id, Throwable err);
}
