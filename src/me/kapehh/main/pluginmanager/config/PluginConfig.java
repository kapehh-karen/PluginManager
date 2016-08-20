package me.kapehh.main.pluginmanager.config;

import me.kapehh.main.pluginmanager.constants.ConstantSystem;
import me.kapehh.main.pluginmanager.utils.StreamUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
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

    // Метод для перезагрузки плагина
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
    private Map<Object, List<Method>> listOfMethodsDefault = new HashMap<Object, List<Method>>();

    // Внутренние переменные
    private FileConfiguration cfg;
    private JavaPlugin plugin;
    private File configFile;

    // Списки конфигов
    private LinkedHashMap<String, YamlConfiguration> lstCfg = new LinkedHashMap<String, YamlConfiguration>();

    public PluginConfig(JavaPlugin plugin, String folder, String name) {
        this.plugin = plugin;
        // TODO: Заменить WeakHashMap<JavaPlugin, PluginConfig> на WeakHashMap<JavaPlugin, List<PluginConfig>>
        //pluginConfigs.put(plugin, this);

        // Создаем папку плагина, если ее нету
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        // Создаем File на наш конфиг будущий
        // configFile = /PluginName/folder/name.yml
        configFile = new File(new File(plugin.getDataFolder(), folder), name + ".yml");
    }

    public PluginConfig setup() {
        try {
            if (!configFile.exists()) {
                // Если нет конфига

                // Перед созданием файла, необходимо убедиться что все папки созданы для этого
                if (!configFile.getParentFile().exists()) {
                    configFile.getParentFile().mkdirs();
                }

                // Создаем пустой файл
                configFile.createNewFile();

                // Читаем конфиг
                cfg = YamlConfiguration.loadConfiguration(configFile);

                // Вызываем все методы DEFAULT чтобы в конфиг прописали значения по умолчанию
                RaiseEvent(EventType.DEFAULT);

                // Сохраняем в файл значения по умолчанию
                cfg.save(configFile);
            } else {
                // Если конфиг есть

                // Читаем конфиг
                cfg = YamlConfiguration.loadConfiguration(configFile);
            }

            // После корректного создания конфига, вызываем событие загрузки значений
            RaiseEvent(EventType.LOAD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public PluginConfig addEventClasses(Object... classes) {
        // TODO: Очищается постоянно, может убрать?
        listOfMethodsLoad.clear();
        listOfMethodsSave.clear();
        listOfMethodsDefault.clear();
        for (Object c : classes) {
            ArrayList<Method> methodArrayListLoad = new ArrayList<Method>();
            ArrayList<Method> methodArrayListSave = new ArrayList<Method>();
            ArrayList<Method> methodArrayListDefault = new ArrayList<Method>();
            Method[] methods = c.getClass().getMethods();
            for(Method method : methods){
                if(method.isAnnotationPresent(EventPluginConfig.class)) {
                    if (method.getAnnotation(EventPluginConfig.class).value().equals(EventType.LOAD)) {
                        methodArrayListLoad.add(method);
                    } else if (method.getAnnotation(EventPluginConfig.class).value().equals(EventType.SAVE)) {
                        methodArrayListSave.add(method);
                    } else if (method.getAnnotation(EventPluginConfig.class).value().equals(EventType.DEFAULT)) {
                        methodArrayListDefault.add(method);
                    }
                }
            }
            if (methodArrayListLoad.size() > 0) {
                listOfMethodsLoad.put(c, methodArrayListLoad);
            }
            if (methodArrayListSave.size() > 0) {
                listOfMethodsSave.put(c, methodArrayListSave);
            }
            if (methodArrayListDefault.size() > 0) {
                listOfMethodsDefault.put(c, methodArrayListDefault);
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
        } else if (eventPluginConfig.equals(EventType.DEFAULT)) {
            mapSelect = listOfMethodsDefault;
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


    public PluginConfig loadData() {
        // Читаем конфиг
        cfg = YamlConfiguration.loadConfiguration(configFile);

        try {
            // Вызываем событие загрузки значений
            RaiseEvent(EventType.LOAD);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public PluginConfig saveData() {
        try {
            // Вызываем событие сохранения значений
            RaiseEvent(EventType.SAVE);

            // На всякий случай проверяем, есть ли папки
            if (!configFile.getParentFile().exists()) {
                configFile.getParentFile().mkdirs();
            }

            // Сохраняем в файл
            cfg.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }
}
