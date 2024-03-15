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

public final class ConnectionManagerImpl implements ConnectionManager {
    private static final String DRIVER_CLASS_KEY = "db.driver-class-name";
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static ConnectionManager instance;

    private ConnectionManagerImpl() {
    }

    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManagerImpl();
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

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PropertiesUtil.getProperties(URL_KEY),
                PropertiesUtil.getProperties(USERNAME_KEY),
                PropertiesUtil.getProperties(PASSWORD_KEY)
        );
    }

}


//public final class ConnectionManagerImpl implements ConnectionManager {
//    private static ConnectionManagerImpl instance;
//
//    private ConnectionManagerImpl() {
//    }
//
//    public static synchronized ConnectionManagerImpl getInstance() {
//        if (instance == null) {
//            instance = new ConnectionManagerImpl();
//        }
//        return instance;
//    }
//
//    public Connection getConnection() throws SQLException {
//        String dbURL;
//        String dbUserName;
//        String dbPassword;
//
//        FileInputStream fis;
//        Properties properties = new Properties();
//
//        try {
//            fis = new FileInputStream("/home/efremychev_a/IdeaProjects/RestCrudProject/src/main/resources/config.properties");
//            properties.load(fis);
//
//            dbURL = properties.getProperty("db.host");
//            dbUserName = properties.getProperty("db.username");
//            dbPassword = properties.getProperty("db.password");
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        Connection connection = null;
//
//        try {
//            connection = DriverManager.getConnection(dbURL, dbUserName,dbPassword);
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return connection;
//    }
//}
