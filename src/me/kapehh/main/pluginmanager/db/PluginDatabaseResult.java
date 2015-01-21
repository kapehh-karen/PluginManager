package me.kapehh.main.pluginmanager.db;

import java.sql.*;

/**
 * Created by Karen on 21.01.2015.
 */
public class PluginDatabaseResult {
    Statement statement;
    ResultSet resultSet;

    public PluginDatabaseResult(Statement statement, ResultSet resultSet) {
        this.statement = statement;
        this.resultSet = resultSet;
    }

    public Statement getStatement() {
        return statement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    @Override
    public String toString() {
        return "PluginDatabaseResult{" +
                "statement=" + statement +
                ", resultSet=" + resultSet +
                '}';
    }
}
