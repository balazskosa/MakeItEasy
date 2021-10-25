package com.example.makeiteasy.database;

import com.example.makeiteasy.database.pojo.Food;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public final class FoodTable {


    protected static ObservableList<Food> data = FXCollections.observableArrayList();

    static {
        new FoodTable();
    }

    private FoodTable() {
        //FoodTable
        try {
            ResultSet rs1 = DB.getDmbd().getTables(null, "APP", "FOOD", null);

            if (!rs1.next()) {
                DB.getCreateStatement().execute("create table food(name varchar(20), protein int, carbohydrate int, fat int)");
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with the creating of the table(s)");
            System.out.println("" + e);
        }


    }

    public static void addFood(Food food) {
        String sql = "insert into food (name, protein, carbohydrate, fat) values(?, ?, ?, ?)";
        try {
            PreparedStatement pstm = DB.getConn().prepareStatement(sql);
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
            ResultSet rs = DB.getCreateStatement().executeQuery(sql);
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

    public static ObservableList<Food> getData() {
        return data;
    }
}
