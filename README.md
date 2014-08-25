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
