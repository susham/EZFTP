// Copyright © 2017 Kenneth Martin, Susham Yerabolu, Henry Cooney, Matthew Hawkins, Dakota Sanchez, Chris Kim
// [This program is licensed under the "MIT License"]
// Please see the file COPYING in the source
// distribution of this software for license terms.

package edu.pdx.cs510.agile.team3.db;

import java.sql.*;

public class DBManager {

    private static String connectionString = "jdbc:sqlite:src/sql/saved_connections.db";
    private static Connection connection;

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(connectionString);
            DatabaseMetaData meta = connection.getMetaData();
            // Check if table already exists
            ResultSet results = meta.getTables(null, null, "connections", null);
            if (!results.next()) {
                initialize();
            }
        } catch(ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // Input is not sanitized! Have fun!
    public static boolean saveConnection(String user,
                                      String pass,
                                      String host) throws SQLException {
        String sql = null;
        if(checkIfExists(user)) {
            if(!login(user, pass)) {
                return false;
            }
            sql = "UPDATE connections set pass = '" + pass + "', " +
                    "host = '" + host + "' WHERE user = '" + user + "';";
        } else {
            sql = "INSERT INTO connections (user, pass, host) " +
                    "VALUES ('" + user + "', '" + pass + "', '" + host + "');";
        }
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
        return true;
    }


    public static boolean login(String user,
                               String pass) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT host FROM connections " +
                "WHERE user = '" + user + "' AND pass = '" + pass + "';";
        ResultSet results = statement.executeQuery(sql);
        boolean valid = false;
        if(results.next()) {
            valid = true;
        }
        results.close();
        statement.close();
        return valid;
    }

    private static void initialize() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE connections (" +
                "id      INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user    VARCHAR NOT NULL," +
                "pass    VARCHAR NOT NULL," +
                "host    VARCHAR NOT NULL" +
                ");";
        statement.executeUpdate(sql);
        statement.close();
    }

    private static boolean checkIfExists(String user) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM connections " +
                "WHERE user = '" + user + "';";
        ResultSet results = statement.executeQuery(sql);
        boolean exists = false;
        if(results.next()) {
            exists = true;
        }
        results.close();
        statement.close();
        return exists;
    }
}
