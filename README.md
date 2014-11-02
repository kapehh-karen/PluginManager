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
// Enable
PluginConfig pluginConfig = new PluginConfig(this);
pluginConfig.addEventClasses(new MainConfig(this, pluginConfig));
pluginConfig.setup();
pluginConfig.loadData();

// В слушателе
@EventPluginConfig(EventType.LOAD)
public void onLoad() {
    // TODO
}

// Disable
if (pluginConfig != null) {
    pluginConfig.saveData();
}
</pre>

<b>Логирование</b>
<pre>
// Enable
PluginLogger pluginLogger = new PluginLogger(this, "dupers");
pluginLogger.setup();
pluginLogger.getLog().info("* * * STARTED * * *");

// Disable
if (pluginLogger != null) {
    pluginLogger.shutDown();
}
</pre>

<b>Чекер</b>
<pre>
PluginChecker pluginChecker = new PluginChecker(this);
if (!pluginChecker.check("WorldEdit")) {
    return;
}
</pre>

<b>Валюта</b>
<pre>
Economy economy = PluginVault.setupEconomy();
Permission permission = PluginVault.setupPermissions();
if (economy == null) {
    getLogger().info("Economy plugin not found!!!");
    getServer().getPluginManager().disablePlugin(this);
    return;
}
</pre>
