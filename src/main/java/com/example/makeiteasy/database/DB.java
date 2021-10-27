package com.example.makeiteasy.database;

import com.example.makeiteasy.database.pojo.Food;
import com.example.makeiteasy.database.pojo.Meal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public final class DB {

    //<editor-fold desc="object variables">
    final static String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final static String URL = "jdbc:derby:Database;create=true";

    private static Connection conn = null;
    private static Statement createStatement = null;
    private static DatabaseMetaData dmbd = null;

    private static ObservableList<Food> foods = FXCollections.observableArrayList();
    private static ObservableList<Meal> meals = FXCollections.observableArrayList();
    //</editor-fold">

    static {
        new DB();
        foods.addAll(DB.getAllFoods());
        meals.addAll(DB.getAllMealByUserID(0));
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

        //FoodTable
        try {
            ResultSet rs1 = dmbd.getTables(null, "APP", "FOOD", null);

            if (!rs1.next()) {
                createStatement.execute("create table food(" +
                        "id int not null primary key generated always as identity (START WITH 1, INCREMENT BY 1)," +
                        " name varchar(20)," +
                        " protein int," +
                        " carbohydrate int," +
                        " fat int)");
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with the creating of the table(s)");
            System.out.println("" + e);
        }

        //MealTable

        try {
            ResultSet rs1 = dmbd.getTables(null, "APP", "MEAL", null);

            if (!rs1.next()) {
                createStatement.execute("create table meal(" +
                        "userId int, " +
                        " foodId int," +
                        " date DATE FORMAT 'dd.mm.yyyy'," +
                        " whichMeal int," +
                        " weight int)");
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with the creating of the table(s)");
            System.out.println("" + e);
        }

    }

    //<editor-fold desc="all methods to food table">
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
        foods.add(food);

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

        } catch (SQLException e) {
            System.out.println("Something wrong with the updateFood method");
        }
    }

    public static ObservableList<Food> getFoods() {
        return foods;
    }
    //</editor-fold">

    //<editor-fold desc="all methods to meal table">
    public void addMeal(Meal meal) {
        String sql = "insert into meal values(?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, meal.getUserId());
            pstm.setInt(2, meal.getFoodId());
            pstm.setDate(3, (Date) meal.getDate());
            pstm.setInt(4, meal.getWhichMeal());
            pstm.setInt(5, meal.getWeight());

        } catch (SQLException e) {
            System.out.println("Something wrong with the addMeal method");
        }
    }

    public static ArrayList<Meal> getAllMealByUserID(int userID) {
        String sql ="select * from meal where userID = " + userID;
        ArrayList<Meal> meals = null;

        try {
            meals = new ArrayList<>();
            ResultSet rs = createStatement.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                Meal actualMeal = new Meal(rs.getInt("userId"),
                        rs.getInt("foodId"),
                        rs.getDate("date"),
                        rs.getInt("whichMeal"),
                        rs.getInt("weight"));
                meals.add(actualMeal);
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with the getAllMealByUserID method");
            System.out.println("" + e);
        }

        return meals;
    }
    
    public static ObservableList<Meal> getMeals() {
        return meals;
    }

    //</editor-fold">
}
