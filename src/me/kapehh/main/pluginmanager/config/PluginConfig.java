package me.kapehh.main.pluginmanager.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Karen on 30.06.2014.
 */
public class PluginConfig {
    private static final WeakHashMap<JavaPlugin, PluginConfig> pluginConfigs = new WeakHashMap<JavaPlugin, PluginConfig>();

    public static boolean loadData(String pluginName) {
        PluginConfig pluginConfig;
        for (JavaPlugin javaPlugin : pluginConfigs.keySet()) {
            if (javaPlugin.getName().equalsIgnoreCase(pluginName)) {
                pluginConfig = pluginConfigs.get(javaPlugin);
                pluginConfig.loadData();
                return true;
            }
        }
        return false;
    }

    // Списки методов, которые надо будет вызывать
    private Map<Object, List<Method>> listOfMethodsLoad = new HashMap<Object, List<Method>>();
    private Map<Object, List<Method>> listOfMethodsSave = new HashMap<Object, List<Method>>();

    // Внутренние переменные
    private FileConfiguration cfg;
    private JavaPlugin plugin;

    public PluginConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        pluginConfigs.put(plugin, this);
    }

    /*@Override
    protected void finalize() throws Throwable {
        pluginConfigs.remove(this);
        super.finalize();
    }*/

    public PluginConfig addEventClasses(Object... classes) {
        ArrayList<Method> methodArrayListLoad;
        ArrayList<Method> methodArrayListSave;
        listOfMethodsLoad.clear();
        listOfMethodsSave.clear();
        for (Object c : classes) {
            methodArrayListLoad = new ArrayList<Method>();
            methodArrayListSave = new ArrayList<Method>();
            Method[] methods = c.getClass().getMethods();
            for(Method method : methods){
                if(method.isAnnotationPresent(EventPluginConfig.class)) {
                    if (method.getAnnotation(EventPluginConfig.class).value().equals(EventType.LOAD)) {
                        methodArrayListLoad.add(method);
                    } else if (method.getAnnotation(EventPluginConfig.class).value().equals(EventType.SAVE)) {
                        methodArrayListSave.add(method);
                    }
                }
            }
            if (methodArrayListLoad.size() > 0) {
                listOfMethodsLoad.put(c, methodArrayListLoad);
            }
            if (methodArrayListSave.size() > 0) {
                listOfMethodsSave.put(c, methodArrayListSave);
            }
        }
        return this;
    }

    public PluginConfig RaiseEvent(EventType eventPluginConfig) throws InvocationTargetException, IllegalAccessException {
        Map<Object, List<Method>> mapSelect = null;
        if (eventPluginConfig.equals(EventType.LOAD)) {
            mapSelect = listOfMethodsLoad;
        } else if (eventPluginConfig.equals(EventType.SAVE)) {
            mapSelect = listOfMethodsSave;
        }
        if (mapSelect == null) {
            return this;
        }
        for (Object c : mapSelect.keySet()) {
            List<Method> methods = mapSelect.get(c);
            for(Method method : methods) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length == 0) { // поддержка пустого параметра для обратной совместимости
                    method.invoke(c); // вызов метода без параметров
                } else if (params[0].equals(FileConfiguration.class)) {
                    method.invoke(c, cfg); // вызов метода с параметром
                }
            }
        }
        return this;
    }

    public FileConfiguration getConfig() {
        return cfg;
    }

    public PluginConfig setup() {
        plugin.saveDefaultConfig();
        cfg = plugin.getConfig();
        cfg.options().copyDefaults(true);
        plugin.saveConfig();
        return this;
    }

    public PluginConfig loadData() {
        plugin.reloadConfig();
        cfg = plugin.getConfig();
        try {
            RaiseEvent(EventType.LOAD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginConfig saveData() {
        try {
            RaiseEvent(EventType.SAVE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        plugin.saveConfig();
        return this;
    }


    // TODO: Сделать конструктор типа PluginConfig(String configName) для кода ниже

    /*private static LinkedHashMap<String, YamlConfiguration> lstCfg = new LinkedHashMap<String, YamlConfiguration>();

    public static YamlConfiguration addCustomConfig(String name) {
        return lstCfg.put(
            name,
            YamlConfiguration.loadConfiguration(
                new File(CFPlugin.getPlugin().getDataFolder().getPath() + CFConfig.fileSep + name + ".yml")
            )
        );
    }

    public static YamlConfiguration getCustomConfig(String name) {
        return lstCfg.get(name);
    }*/
}
