package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void initializeDatabase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            String createPlaceTable = "CREATE TABLE place (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "floor INT, " +
                    "wardrobe INT, " +
                    "shelf INT" +
                    ")";
            String createBookTable = "CREATE TABLE book (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
                    "author VARCHAR(255), " +
                    "publication VARCHAR(255), " +
                    "publishing_house VARCHAR(255), " +
                    "year_public INT, " +
                    "pages INT, " +
                    "year_write INT, " +
                    "weight INT, " +
                    "place_id INT, " +
                    "FOREIGN KEY (place_id) REFERENCES place(id)" +
                    ")";

            statement.execute(createPlaceTable);
            statement.execute(createBookTable);

            System.out.println("Таблицы созданы успешно.");
        } catch (SQLException e) {
            System.out.println("Произошла ошибка при создании таблиц: " + e.getMessage());
        }
    }
}
