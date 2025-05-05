package com.example.lab_5;

import java.sql.*;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:library.db";
    public static final String[] GENRES = {
            "Фантастика", "Фэнтези", "Детектив", "Роман",
            "Ужасы", "Научная литература", "Биография", "Поэзия", "Поэма"
    };
    private Connection connection;

    // Блок инициализации класса драйвера для связи Java и бд
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC driver not found", e);
        }
    }

    public DatabaseHandler() {
        connect();
    }

    // Установка соедниения с бд
    private void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
                createTables();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    private void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {

            String createAuthors = "CREATE TABLE IF NOT EXISTS authors (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "surname TEXT NOT NULL," +
                    "name TEXT NOT NULL," +
                    "patronymic TEXT," +
                    "birth_date TEXT," +
                    "country TEXT)";

            String createBooks = "CREATE TABLE IF NOT EXISTS books (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT NOT NULL," +
                    "author_id INTEGER NOT NULL," +
                    "year INTEGER," +
                    "genre TEXT CHECK(genre IN ('Фантастика', 'Фэнтези', 'Детектив', " +
                    "'Роман', 'Ужасы', 'Научная литература', 'Биография', 'Поэзия'))," +
                    "quantity INTEGER DEFAULT 1," +
                    "FOREIGN KEY (author_id) REFERENCES authors(id))";

            stmt.execute(createAuthors);
            stmt.execute(createBooks);
        }
    }

    // Получение активного соединения
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connect();
        }
        return connection;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

