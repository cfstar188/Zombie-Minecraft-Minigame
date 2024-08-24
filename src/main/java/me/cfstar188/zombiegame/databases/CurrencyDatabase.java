package me.cfstar188.zombiegame.databases;

import me.cfstar188.zombiegame.gui.ScoreboardGUI;

import java.sql.*;

public class CurrencyDatabase {

    private static Connection connection = null;

    public CurrencyDatabase(String filePath) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS currency (" +
                    "uuid TEXT," +
                    "currency_value INT" +
                    ");");
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static void giveCurrency(String uuid, int currency) throws SQLException {

        // check if the uuid exists in the currency database
        try (PreparedStatement checkStatement = connection.prepareStatement(
                "SELECT currency_value FROM currency WHERE uuid = ?;")) {
            checkStatement.setString(1, uuid);
            ResultSet resultSet = checkStatement.executeQuery();

            // if uuid exists
            if (resultSet.next()) {
                try (PreparedStatement updateStatement = connection.prepareStatement(
                        "UPDATE currency SET currency_value = currency_value + ? WHERE uuid = ?;")) {
                    updateStatement.setInt(1, currency);
                    updateStatement.setString(2, uuid);
                    updateStatement.execute();
                }
            }

            // uuid doesn't exist
            else {
                try (PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO currency (uuid, currency_value) VALUES (?, ?);")) {
                    insertStatement.setString(1, uuid);
                    insertStatement.setInt(2, currency);
                    insertStatement.execute();
                }
            }
        }
    }

    public static int getCurrency(String uuid) throws SQLException {

        // check if the uuid exists in the currency database
        try (PreparedStatement checkStatement = connection.prepareStatement(
                "SELECT currency_value FROM currency WHERE uuid = ?;")) {
            checkStatement.setString(1, uuid);
            ResultSet resultSet = checkStatement.executeQuery();

            // if uuid exists
            if (resultSet.next()) {
                return resultSet.getInt("currency_value");
            }

            // uuid doesn't exist
            try (PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO currency (uuid, currency_value) VALUES (?, 0);")) {
                insertStatement.setString(1, uuid);
                insertStatement.execute();
            }
            return 0;
        }
    }
}
