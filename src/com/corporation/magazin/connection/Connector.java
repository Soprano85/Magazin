package com.corporation.magazin.connection;

import java.sql.*;

public class Connector {
    private Connection connection;
    private Statement statement;

    public Connector() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\user\\IdeaProjects\\Magazin\\src\\com\\corporation\\magazin\\connection\\magazin.db");
            statement = connection.createStatement();
            System.out.println("Мы подключились");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        return statement.executeQuery(query);
    }

    public void executeUpdate(String query) throws SQLException {
        statement.executeUpdate(query);
    }


}
