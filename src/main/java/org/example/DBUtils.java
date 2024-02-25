package org.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {

    public static Connection getConnection() {
        String dBURL = null;
        String userName = null;
        String  pass = null;

        FileInputStream fis;
        Properties properties = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);

            dBURL = properties.getProperty("db.host");
            userName = properties.getProperty("userName");
            pass = properties.getProperty("password");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dBURL, userName, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
