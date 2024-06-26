package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String DB_USERNAME = "admin";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:derby://localhost:1527/homeLibrary";

    public static Connection connectToDataBase() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}