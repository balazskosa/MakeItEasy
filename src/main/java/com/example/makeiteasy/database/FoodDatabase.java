package com.example.makeiteasy.database;

import com.example.makeiteasy.database.pojo.Food;

import java.sql.*;
import java.util.ArrayList;

public class FoodDatabase {
    private Connection conn = null;
    private Statement createStatement = null;
    private DatabaseMetaData dmbd = null;


    public FoodDatabase(Connection conn, Statement createStatement, DatabaseMetaData dmbd) {
        this.conn = conn;
        this.createStatement = createStatement;
        this.dmbd = dmbd;

        try {
            ResultSet rs1 = dmbd.getTables(null, "APP", "FOOD", null);

            if (!rs1.next()) {
                createStatement.execute("create table food(name varchar(20), protein int, carbohydrate int, fat int)");
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with the creating of the table(s)");
            System.out.println("" + e);
        }
    }

    public void addFood(Food food) {
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

    }

    public ArrayList<Food> getAllFoods() {
        String sql = "select * from food";
        ArrayList<Food> foods = null;


        try {
            foods = new ArrayList<>();
            ResultSet rs = createStatement.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                Food actualFood = new Food(rs.getString(rsmd.getColumnName(1)),
                        rs.getInt(rsmd.getColumnName(2)),
                        rs.getInt(rsmd.getColumnName(3)),
                        rs.getInt(rsmd.getColumnName(4)));
                foods.add(actualFood);
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with de getAllFood method");
            System.out.println("" + e);
        }

        return foods;
    }

}
