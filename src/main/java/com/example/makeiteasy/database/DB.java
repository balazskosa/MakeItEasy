package com.example.makeiteasy.database;

import java.sql.*;

public final class DB {

    final static String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final static String URL = "jdbc:derby:Database;create=true";

    private static Connection conn = null;
    private static Statement createStatement = null;
    private static DatabaseMetaData dmbd = null;

    private DB() {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Bridge is open");
        } catch (SQLException e) {
            System.out.println("Something wrong with the bridge");
            System.out.println("" + e);
        }

        if (conn != null) {
            try {
                createStatement = conn.createStatement();
            } catch (SQLException e) {
                System.out.println("Something wrong with the createStatement");
                System.out.println("" + e);
            }
        }

        dmbd = null;
        try {
            dmbd = conn.getMetaData();
        } catch (SQLException e) {
            System.out.println("Something wrong with the meta data");
            System.out.println("" + e);
        }

    }

    static {
        new DB();
        FoodTable.getData().addAll(FoodTable.getAllFoods());
    }

    public static Connection getConn() {
        return conn;
    }

    public static Statement getCreateStatement() {
        return createStatement;
    }

    public static DatabaseMetaData getDmbd() {
        return dmbd;
    }

}
