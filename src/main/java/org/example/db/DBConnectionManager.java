package org.example.db;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnectionManager {
    Connection getConnection() throws SQLException;
}
