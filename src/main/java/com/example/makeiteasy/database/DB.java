package com.example.makeiteasy.database;

import java.sql.*;

public class DB {

    final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final String URL = "jdbc:derby:Database;create=true";

    private  Connection conn = null;
    private  Statement createStatement = null;
    private  DatabaseMetaData dmbd = null;


    public DB() {
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

    public Connection getConn() {
        return conn;
    }

    public Statement getCreateStatement() {
        return createStatement;
    }

    public DatabaseMetaData getDmbd() {
        return dmbd;
    }

}
