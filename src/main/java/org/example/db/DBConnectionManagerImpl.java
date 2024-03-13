package org.example.db;

import org.example.exception.DataBaseDriverLoadException;
import org.example.util.PropertiesUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnectionManagerImpl  implements DBConnectionManager {
    private static final String DRIVER_CLASS_KEY = "db.driver-class-name";
    private static final String URL_KEY = "db.host";
    private static final String USERNAME_KEY = "userName";
    private static final String PASSWORD_KEY = "password";
    private static DBConnectionManagerImpl instance;

    private DBConnectionManagerImpl() {
    }

    public static synchronized DBConnectionManagerImpl getInstance() {
        if (instance == null) {
            instance = new DBConnectionManagerImpl();
            loadDriver(PropertiesUtil.getProperties(DRIVER_CLASS_KEY));
        }
        return instance;
    }

    private static void loadDriver(String driverClass) {
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new DataBaseDriverLoadException("Database driver not loaded.");
        }
    }


    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                PropertiesUtil.getProperties(URL_KEY),
                PropertiesUtil.getProperties(USERNAME_KEY),
                PropertiesUtil.getProperties(PASSWORD_KEY)
        );
    }
}
