PluginManager
=============

Модуль Bukkit плагинов, для работы с Конфигом, Логом и т.п.

<b>Команды:</b>
<code>/plugmng reload [pluginName]</code> - перезагрузить конфиг плагина под управлением PluginManager

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
public void onLoadConfig(/* FileConfiguration cfg - можно и так */) {
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

<b>БД</b>
<pre>
PluginDatabaseInfo dbInfo = new PluginDatabaseInfo();
PluginDatabase dbHelper;

if (dbHelper != null) {
	try {
		dbHelper.disconnect();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	dbHelper = null;
}

dbInfo.setIp(cfg.getString("connect.ip", ""));
dbInfo.setDb(cfg.getString("connect.db", ""));
dbInfo.setLogin(cfg.getString("connect.login", ""));
dbInfo.setPassword(cfg.getString("connect.password", ""));
dbInfo.setTable(cfg.getString("connect.table", ""));

// коннектимся
try {
	// создаем экземпляр класса для соединения с БД
	dbHelper = new PluginDatabase(
		dbInfo.getIp(),
		dbInfo.getDb(),
		dbInfo.getLogin(),
		dbInfo.getPassword()
	);

	dbHelper.connect();
	getLogger().info("Success connect to MySQL!");
} catch (SQLException e) {
	dbHelper = null;
	e.printStackTrace();
}

// ПРИМЕР ИСПОЛЬЗОВАНИЯ
try {
    dbHelper.prepareQueryUpdate("UPDATE players SET online = 0 WHERE name = ?", item.getPlayerName());
} catch (SQLException e) {
    e.printStackTrace();
}

// ЕЩЁ ПРИМЕР
Connection connection = dbHelper.getConnection();
PreparedStatement statement = connection.prepareStatement(
    "INSERT INTO `mail`(`raw`, `info`, `sended_date`, `received_date`, `from`, `to`, `is_received`) VALUES (?, ?, NOW(), '0000-00-00 00:00:00', ?, ?, 0)"
);
statement.setBytes(1, bItem);
statement.setString(2, itemStack.toString());
statement.setString(3, from.getName());
statement.setString(4, to.getName());
statement.executeUpdate();
statement.close();
</pre>
