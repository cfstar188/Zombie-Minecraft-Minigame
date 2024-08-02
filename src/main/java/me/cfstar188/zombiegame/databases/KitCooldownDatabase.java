package me.cfstar188.zombiegame.databases;

import java.sql.*;

public class KitCooldownDatabase {

    private static Connection connection = null;

    public KitCooldownDatabase(String filePath) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS kit_cooldowns (" +
                    "uuid TEXT," +
                    "kit_name TEXT," +
                    "time_received DOUBLE," +
                    "PRIMARY KEY (uuid, kit_name)" +
                    ");");
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static void insertTuple(String uuid, String kitName) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT OR REPLACE INTO kit_cooldowns (uuid, kit_name, time_received) VALUES (?, ?, ?);")) {
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, kitName);
            preparedStatement.setDouble(3, (double) System.currentTimeMillis() / 3600000);
            preparedStatement.execute();

        }

    }

    // returns the hoursRemaining in hours
    public static double hoursPassed(String uuid, String kitName) {

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT time_received from kit_cooldowns WHERE uuid = ? AND kit_name = ?;")) {
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, kitName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                double timeReceived = resultSet.getDouble("time_received");
                return ((double) System.currentTimeMillis() / 3600000 - timeReceived);
            }
        }
        catch (SQLException e) {
            return 0;
        }

    }
}
