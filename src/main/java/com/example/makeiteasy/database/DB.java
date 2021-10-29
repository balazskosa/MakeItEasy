package com.example.makeiteasy.database;

import com.example.makeiteasy.database.pojo.User;
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
                        " date date," +
                        " whichMeal int," +
                        " weight int)");
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with the creating of the MEAL table");
            System.out.println("" + e);
        }

        //User table
        try {
            ResultSet rs1 = dmbd.getTables(null, "APP", "USER2", null);
            if (!rs1.next()) {
                createStatement.execute("create table user2(" +
                        " firstName varchar(20)," +
                        " lastName varchar(20)," +
                        " weight int," +
                        " gender varchar(6)," +
                        " birthday date," +
                        " height int)");
            }

        } catch (SQLException e) {
            System.out.println("Something wrong with the creating of the USER2 table");
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
            pstm.execute();

        } catch (SQLException e) {
            System.out.println("Something wrong with the updateFood method");
        }
    }

    public static ObservableList<Food> getFoods() {
        return foods;
    }
    //</editor-fold">

    //<editor-fold desc="all methods to meal table">
    public static void addMeal(Meal meal) {
        try {
            String sql = "insert into meal (userId, foodId, date, whichMeal, weight) values(?, ?, ?, ?, ?)";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, meal.getUserId());
            pstm.setInt(2, meal.getFoodId());
            pstm.setDate(3, meal.getDate());
            pstm.setInt(4, meal.getWhichMeal());
            pstm.setInt(5, meal.getWeight());
            pstm.execute();

        } catch (SQLException e) {
            System.out.println("Something wrong with the addMeal method");
        }

    }

    public static ArrayList<Meal> getAllMealByUserID(int userID) {
        String sql = "select * from meal where userID = " + userID;
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

    public static void getAllMealWithMeta() {
        String sql = "select * from meal";
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        try {
            rs = createStatement.executeQuery(sql);
            rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i < columnCount + 1; i++) {
                System.out.print(rsmd.getColumnName(i) + " | ");
            }
            System.out.println();

            while (rs.next()) {
                int userId = rs.getInt(rsmd.getColumnName(1));
                int foodId = rs.getInt(rsmd.getColumnName(2));
                Date date = rs.getDate(rsmd.getColumnName(3));
                int whichMeal = rs.getInt(rsmd.getColumnName(4));
                int weight = rs.getInt(rsmd.getColumnName(5));
                System.out.println(userId + " | " + foodId + " | " + date + " | " + whichMeal + " | " + weight);
            }


        } catch (SQLException e) {
            System.out.println("Something wrong with the reading of the data");
            System.out.println("" + e);
        }
    }

    public static ObservableList<Meal> getMeals() {
        return meals;
    }

    //</editor-fold">

    //<editor-fold desc="all methods to user table">
    public static void addUser(User user) {
        String sql = "insert into user2 (firstname, lastname, weight, gender, birthday, height) values(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.firstName);
            pstm.setString(2, user.lastName);
            pstm.setInt(3, user.weight);
            pstm.setString(4, user.gender);
            pstm.setDate(5, Date.valueOf(user.birthday));
            pstm.setInt(6, user.height);
            pstm.execute();

        } catch (SQLException e) {
            System.out.println("Something wrong with the addUser method");
            System.out.println("" + e);
        }

    }

    public static void getAllUserWithMeta() {
        String sql = "select * from user2";
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        try {
            rs = createStatement.executeQuery(sql);
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for (int i = 1; i < columnCount + 1; i++) {
                System.out.print(rsmd.getColumnName(i) + " | ");
            }
            System.out.println();

            while (rs.next()) {
                String firstName = rs.getString(rsmd.getColumnName(1));
                String lastName = rs.getString(rsmd.getColumnName(2));
                int weight = rs.getInt(rsmd.getColumnName(3));
                String gender = rs.getString(rsmd.getColumnName(4));
                Date date = rs.getDate(rsmd.getColumnName(5));
                int height = rs.getInt(rsmd.getColumnName(6));
                System.out.println(firstName + " | " + lastName + " | " + weight + " | " + gender + " | " + date + " | " + height);
            }


        } catch (SQLException e) {
            System.out.println("Something wrong with the reading of the data");
            System.out.println("" + e);
        }
    }
    //</editor-fold>
}
