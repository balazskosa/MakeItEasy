package com.example.makeiteasy.database;

import com.example.makeiteasy.database.pojo.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public final class DB {

    final static String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final static String URL = "jdbc:derby:Database;create=true";

    private static Connection conn = null;
    private static Statement createStatement = null;
    private static DatabaseMetaData dmbd = null;

    private static ObservableList<Food> data = FXCollections.observableArrayList();

    static {
        new DB();
        data.addAll(DB.getAllFoods());
    }

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

        try {
            ResultSet rs1 = dmbd.getTables(null, "APP", "FOOD", null);

            if (!rs1.next()) {
                createStatement.execute("create table food(" +
                        "id int not null primary key generated always as identity (START WITH 1, INCREMENT BY 1)," +
                        "name varchar(20)," +
                        " protein int," +
                        " carbohydrate int," +
                        " fat int)");
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with the creating of the table(s)");
            System.out.println("" + e);
        }

    }

    public static void addFood(Food food) {
        String sql = "insert into food (name, protein, carbohydrate, fat) values(?, ?, ?, ?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, food.getName());
            pstm.setInt(2, food.getProtein());
            pstm.setInt(3, food.getCarbohydrate());
            pstm.setInt(4, food.getFat());
            pstm.execute();

        } catch (SQLException e) {
            System.out.println("Something wrong with the addFood method");
            System.out.println("" + e);
        }
        data.add(food);

    }

    public static ArrayList<Food> getAllFoods() {
        String sql = "select * from food";
        ArrayList<Food> foods = null;


        try {
            foods = new ArrayList<>();
            ResultSet rs = createStatement.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                Food actualFood = new Food(rs.getString("name"),
                        rs.getInt("protein"),
                        rs.getInt("carbohydrate"),
                        rs.getInt("fat"));
                foods.add(actualFood);
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with de getAllFood method");
            System.out.println("" + e);
        }

        return foods;
    }

    public static void updateFood(Food food) {
        String sql = "update food set name = ?, protein = ?, carbohydrate = ?, fat = ? where id = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, food.getName());
            pstm.setInt(2, food.getProtein());
            pstm.setInt(3, food.getCarbohydrate());
            pstm.setInt(4, food.getFat());
            pstm.setInt(5, food.getId());

            System.out.println("updateFood method already ran");

        } catch (SQLException e) {
            System.out.println("Something wrong with the updateFood method");
        }
    }

    public static ObservableList<Food> getData() {
        return data;
    }
}
