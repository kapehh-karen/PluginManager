package me.kapehh.main.pluginmanager.db;

import java.sql.*;

/**
 * Created by Karen on 21.01.2015.
 */
public class PluginDatabase {
    private Connection connection = null;
    private String ip;
    private int port;
    private String db;
    private String login;
    private String password;

    public PluginDatabase(PluginDatabaseInfo dbInfo) {
        this.ip = dbInfo.getIp();
        this.port = dbInfo.getPort();
        this.db = dbInfo.getDb();
        this.login = dbInfo.getLogin();
        this.password = dbInfo.getPassword();
    }

    public PluginDatabase(String ip, int port, String db, String login, String password) {
        this.ip = ip;
        this.port = port;
        this.db = db;
        this.login = login;
        this.password = password;
    }

    public void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + db, login, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public String getIp() {
        return ip;
    }

    public String getDb() {
        return db;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    // ФУНКЦИИ ДЛЯ ВЫПОЛНЕНИЯ ЗАПРОСА
    // while (result.next()) { ... }

    public PluginDatabaseResult queryStart(String query) throws SQLException {
        Statement sql = connection.createStatement();
        ResultSet result = sql.executeQuery(query);
        return new PluginDatabaseResult(sql, result);
    }

    public PluginDatabaseResult prepareQueryStart(String query, Object... args) throws SQLException {
        PreparedStatement sql = connection.prepareStatement(query);
        int index = 1;
        for (Object o : args) {
            sql.setObject(index, o);
            index++;
        }
        ResultSet result = sql.executeQuery();
        return new PluginDatabaseResult(sql, result);
    }

    // ФУНКЦИЯ ИЗМЕНЕНИЯ ДАННЫХ

    public int queryUpdate(String query) throws SQLException {
        Statement sql = connection.createStatement();
        int ret = sql.executeUpdate(query);
        sql.close();
        return ret;
    }

    public int prepareQueryUpdate(String query, Object... args) throws SQLException {
        PreparedStatement sql = connection.prepareStatement(query);
        int index = 1;
        for (Object o : args) {
            sql.setObject(index, o);
            index++;
        }
        int ret = sql.executeUpdate();
        sql.close();
        return ret;
    }

    // ФУНКЦИЯ ДЛЯ ЗАВЕРШЕНИЯ ЗАПРОСА

    public void queryEnd(PluginDatabaseResult dbHelperResult) throws SQLException {
        if (dbHelperResult.getResultSet() != null) dbHelperResult.getResultSet().close();
        dbHelperResult.getStatement().close();
    }
}
