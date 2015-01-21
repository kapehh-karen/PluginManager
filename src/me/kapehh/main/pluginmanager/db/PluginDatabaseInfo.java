package me.kapehh.main.pluginmanager.db;

/**
 * Created by Karen on 21.01.2015.
 */
public class PluginDatabaseInfo {
    String ip;
    String db;
    String login;
    String password;
    String table;

    public PluginDatabaseInfo() { }

    public PluginDatabaseInfo(String ip, String db, String login, String password, String table) {
        this.ip = ip;
        this.db = db;
        this.login = login;
        this.password = password;
        this.table = table;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "PluginDatabaseInfo{" +
                "ip='" + ip + '\'' +
                ", db='" + db + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", table='" + table + '\'' +
                '}';
    }
}
