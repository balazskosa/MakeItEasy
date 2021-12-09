package com.example.makeiteasy.database;

import com.example.makeiteasy.database.pojo.Food;
import com.example.makeiteasy.database.pojo.Meal;
import com.example.makeiteasy.database.pojo.User;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
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
    public static User user;
    //</editor-fold">

    static {
        new DB();
        foods.addAll(DB.getAllFoods());
        meals.addAll(DB.getAllMeals());
        setUserTable();
        user = getUser();

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
                        " calories int, " +
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
                        "id int not null primary key generated always as identity (START WITH 1, INCREMENT BY 1)," +
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
                        " height int," +
                        " activityMultiplier float)");

            }

        } catch (SQLException e) {
            System.out.println("Something wrong with the creating of the USER2 table");
            System.out.println("" + e);
        }

    }

    //<editor-fold desc="all methods to food table">
    public static void addFood(Food food) {
        String sql = "insert into food (name, calories, protein, carbohydrate, fat) values(?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, food.getName());
            pstm.setInt(2, food.getCalories());
            pstm.setInt(3, food.getProtein());
            pstm.setInt(4, food.getCarbohydrate());
            pstm.setInt(5, food.getFat());


            int updated = pstm.executeUpdate();
            if (updated == 1) {
                ResultSet generatedKeys = pstm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int key = generatedKeys.getInt(1);
                    food.setId(key);
                }
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with the addFood method");
            System.out.println("" + e);
        }
        foods.add(food);

    }

    public static String getFoodNameByID(int foodID) {
        String sql = "select name from food where id = " + foodID;
        String name = null;
        ResultSet rs = null;
        try {
            rs = createStatement.executeQuery(sql);
            if (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with te getFoodByNameID method");
            System.out.println("" + e);
        }

        return name;
    }

    public static ArrayList<Food> getAllFoods() {
        String sql = "select * from food";
        ArrayList<Food> foods = null;


        try {
            foods = new ArrayList<>();
            ResultSet rs = createStatement.executeQuery(sql);

            while (rs.next()) {
                Food actualFood = new Food(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("calories"),
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
        String sql = "update food set name = ?, calories = ?, protein = ?, carbohydrate = ?, fat = ? where id = ?";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, food.getName());
            pstm.setInt(2, food.getCalories());
            pstm.setInt(3, food.getProtein());
            pstm.setInt(4, food.getCarbohydrate());
            pstm.setInt(5, food.getFat());
            pstm.setInt(6, food.getId());
            pstm.execute();

        } catch (SQLException e) {
            System.out.println("Something wrong with the updateFood method");
            System.out.println("" + e);
        }
    }

    public static void clearFoodTable() {
        String sql = "delete from food";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.execute();
        } catch (SQLException e) {
            System.out.println("Something wrong with the clearFoodTable method");
            System.out.println("" + e);
        }
        foods.clear();
    }

    public static void searchFoodByName(String name) {
        foods.clear();
        foods.addAll(getAllFoods());
        foods.removeIf(f -> !f.getName().contains(name));
    }

    public static void resetSearchFood() {
        foods.clear();
        foods.addAll(getAllFoods());
    }

    public static void searchMealsByWhichMeal(int whichMeal, LocalDate date) {
        meals.clear();
        meals.addAll(getAllMeals());
        meals.removeIf(f -> !(f.getWhichMeal() == whichMeal));
        meals.removeIf(f -> !(f.getDate().toLocalDate().equals(date)));
    }


    public static void getAllFoodsWithMeta() {
        String sql = "select * from food";
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
                int foodId = rs.getInt(rsmd.getColumnName(1));
                String foodName = rs.getString(rsmd.getColumnName(2));
                int calories = rs.getInt(rsmd.getColumnName(3));
                int protein = rs.getInt(rsmd.getColumnName(4));
                int carbs = rs.getInt(rsmd.getColumnName(5));
                int fat = rs.getInt(rsmd.getColumnName(5));
                System.out.println(foodId + " | " + foodName + " | " + calories + " | " + protein + " | " + carbs + " |" + fat);
            }


        } catch (SQLException e) {
            System.out.println("Something wrong with the reading of the data");
            System.out.println("" + e);
        }
    }


    public static ObservableList<Food> getFoods() {
        return foods;
    }
    //</editor-fold">

    //<editor-fold desc="all methods to meal table">
    public static void addMeal(Meal meal) {
        try {
            String sql = "insert into meal (foodId, date, whichMeal, weight) values(?, ?, ?, ?)";
            PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, meal.getFoodId());
            pstm.setDate(2, meal.getDate());
            pstm.setInt(3, meal.getWhichMeal());
            pstm.setInt(4, meal.getWeight());

            int updated = pstm.executeUpdate();
            if (updated == 1) {
                ResultSet generatedKeys = pstm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int key = generatedKeys.getInt(1);
                    meal.setId(key);
                }
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with the addMeal method");
            System.out.println("" + e);
        }
        meals.add(meal);

    }

    public static ArrayList<Meal> getAllMeals() {
        String sql = "select * from meal";
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        ArrayList<Meal> meals = null;

        try {
            meals = new ArrayList<>();
            rs = createStatement.executeQuery(sql);
            rsmd = rs.getMetaData();
            while (rs.next()) {
                Meal actualMeal = new Meal(
                        rs.getInt("id"),
                        rs.getInt("foodId"),
                        rs.getDate("date"),
                        rs.getInt("whichMeal"),
                        rs.getInt("weight"));
                meals.add(actualMeal);
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with the getAllMeals method");
            System.out.println("" + e);
        }

        return meals;
    }

    public static ArrayList<Meal> getAllMealsByWhichMeal(int whichMeal) {
        String sql = "select * from meal where whicmeal = " + whichMeal;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        ArrayList<Meal> meals = null;

        try {
            meals = new ArrayList<>();
            rs = createStatement.executeQuery(sql);
            rsmd = rs.getMetaData();
            while (rs.next()) {
                Meal actualMeal = new Meal(
                        rs.getInt("id"),
                        rs.getInt("foodId"),
                        rs.getDate("date"),
                        rs.getInt("whichMeal"),
                        rs.getInt("weight"));
                meals.add(actualMeal);
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with the getAllMealsByWhichMeal method");
            System.out.println("" + e);
        }

        return meals;
    }

    public static void getAllMealsWithMeta() {
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
                int id = rs.getInt(rsmd.getColumnName(1));
                int foodId = rs.getInt(rsmd.getColumnName(2));
                Date date = rs.getDate(rsmd.getColumnName(3));
                int whichMeal = rs.getInt(rsmd.getColumnName(4));
                int weight = rs.getInt(rsmd.getColumnName(5));
                System.out.println(id + " | " + foodId + " | " + date + " | " + whichMeal + " | " + weight);
            }


        } catch (SQLException e) {
            System.out.println("Something wrong with the reading of the data");
            System.out.println("" + e);
        }
    }
    public static void deleteMeal(Meal meal) {
        String sql = "delete from meal where id = " + meal.getId();

        try {
            createStatement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Something wrong with te deleteMealByID method");
            System.out.println("" + e);
        }

        meals.remove(meal);
    }

    public static void updateMeal(Meal meal, int weight) {
        String sql = "update meal set weight = " + weight + " where id = " + meal.getId();

        try {
            createStatement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Something wrong with te deleteMealByID method");
            System.out.println("" + e);
        }


        int index = meals.indexOf(meal);
        meal.setWeight(weight);
        meals.set(index, meal);

    }

    public static ObservableList<Meal> getMeals() {
        return meals;
    }

    //</editor-fold">

    //<editor-fold desc="all methods to user table">
    public static void addUser(User newUser) {
        String sqlDeleteData = "delete from user2";
        try {
            PreparedStatement pstm2 = conn.prepareStatement(sqlDeleteData);
            pstm2.execute();
        } catch (SQLException e) {
            System.out.println("Something wrong with the deleting of users");
            System.out.println("" + e);
        }

        String sql = "insert into user2 (firstname, lastname, weight, gender, birthday, height, activityMultiplier) values(?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, user.firstName);
            pstm.setString(2, user.lastName);
            pstm.setInt(3, user.weight);
            pstm.setString(4, user.gender);
            pstm.setDate(5, Date.valueOf(user.birthday));
            pstm.setInt(6, user.height);
            pstm.setDouble(7, user.activityMultiplier);
            pstm.execute();

        } catch (SQLException e) {
            System.out.println("Something wrong with the addUser method");
            System.out.println("" + e);
        }
        user = newUser;

    }

    public static void updateUser(User user) {

    }

    private static void setUserTable() {
        String sql = "select count(*) as count from user2";
        ResultSet rs = null;
        int count = 0;
        try {
            rs = createStatement.executeQuery(sql);
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            System.out.println("Something wrong with the userTableIsEmpty method");
            System.out.println("" + e);
        }

        if (count != 1) {
            User generalUser = new User("firstname",
                    "lastname",
                    70,
                    "Male", LocalDate.of(2000, 2, 12), 170, 1.35);
            DB.addUser(generalUser);
        }
    }

    public static User getUser() {
        String sql = "select * from user2";
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;

        User actualUser = null;
        try {
            rs = createStatement.executeQuery(sql);
            rsmd = rs.getMetaData();
            if (rs.next()) {
                String firstName = rs.getString(rsmd.getColumnName(1));
                String lastName = rs.getString(rsmd.getColumnName(2));
                int weight = rs.getInt(rsmd.getColumnName(3));
                String gender = rs.getString(rsmd.getColumnName(4));
                Date date = rs.getDate(rsmd.getColumnName(5));
                int height = rs.getInt(rsmd.getColumnName(6));
                double activity = rs.getDouble(rsmd.getColumnName(7));

                actualUser = new User(firstName, lastName, weight, gender, date.toLocalDate(), height, activity);
            }


        } catch (SQLException e) {
            System.out.println("Something wrong with getUser method");
            System.out.println("" + e);
        }

        return actualUser;
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
                double activity = rs.getDouble(rsmd.getColumnName(7));
                System.out.println(firstName + " | " + lastName + " | " + weight + " | " + gender + " | " + date + " | " + height + " | " + activity);
            }


        } catch (SQLException e) {
            System.out.println("Something wrong with the reading of the data");
            System.out.println("" + e);
        }
    }

    //</editor-fold>
}
