package services.Impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import services.DatabaseConnectionService;

public class MySQLConnectionService implements DatabaseConnectionService {

    private String username = "root";
    private String password = "root";
    private String dbName = "greenloop";
    private String host = "localhost";
    private int port = 3306;

    public MySQLConnectionService(){}

    public MySQLConnectionService(String username, String password, String dbName, String host, int port) {
        this.username = username;
        this.password = password;
        this.dbName = dbName;
        this.host = host;
        this.port = port;
    }

    @Override
    public Connection getConnection() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
