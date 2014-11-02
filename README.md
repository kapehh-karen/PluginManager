PluginManager
=============

Модуль Bukkit плагинов, для работы с Конфигом, Логом и т.п.

<b>Установка:</b> Скопировать *.jar в папку plugins

Требований нет. Плагин ничего не делает, только предоставляет типичные классы, чтоб не реализовывать их каждый раз.

<b>Проверка плагина на доступность:</b>
<pre>
if (getServer().getPluginManager().getPlugin("PluginManager") == null) {
    getLogger().info("PluginManager not found!!!");
    getServer().getPluginManager().disablePlugin(this);
    return;
}
</pre>

<b>До кучи в .gitignore</b>
<pre>
# IDEA Files
.idea/workspace.xml
.idea/libraries
out/
</pre>

Использование
=============

<b>Загрузка из конфига</b>
<pre>
// Инициализация
PluginConfig pluginConfig = new PluginConfig(this);
pluginConfig.addEventClasses(new MainConfig(this, pluginConfig));
pluginConfig.setup();
pluginConfig.loadData();

// В слушателе
@EventPluginConfig(EventType.LOAD)
public void onLoad() {
    // TODO
}
</pre>

<b>Логирование</b>
<pre>
// Инициализация
PluginLogger pluginLogger = new PluginLogger(this, "dupers");
pluginLogger.setup();
pluginLogger.getLog().info("* * * STARTED * * *");

// Выключение
if (pluginLogger != null) {
    pluginLogger.shutDown();
}
</pre>
