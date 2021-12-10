package com.example.makeiteasy.database;

import com.example.makeiteasy.database.pojo.Food;
import com.example.makeiteasy.database.pojo.Meal;
import com.example.makeiteasy.database.pojo.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Database class which contains all related methods and variables
 */
public final class DB {

    //<editor-fold desc="object variables">
    final static String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    final static String URL = "jdbc:derby:Database;create=true";

    private static Connection conn = null;
    private static Statement createStatement = null;
    private static DatabaseMetaData dmbd = null;

    private static ObservableList<Food> foods = FXCollections.observableArrayList();
    private static ObservableList<Meal> meals = FXCollections.observableArrayList();
    private static User user;
    //</editor-fold">

    /**
     * Set up all variables
     */
    static {
        new DB();
        foods.addAll(DB.getAllFoods());
        meals.addAll(DB.getAllMeals());
        setUserTable();
        user = getUser();

    }

    /**
     * Return user object
     * @return user object
     */
    public static User user() {
        return user;
    }

    /**
     * Private Constructor to set all tables
     */
    private DB() {
        try {
            conn = DriverManager.getConnection(URL);
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
        } catch (SQLException | NullPointerException e) {
           e.printStackTrace();
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
        } catch (SQLException | NullPointerException e) {
           e.printStackTrace();
        }

        //User table
        try {
            assert dmbd != null;
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

    /**
     * Add food to the DB
     * @param food food object
     */
    public static void addFood(Food food) {
        String sql = "insert into food (name, calories, protein, carbohydrate, fat) values(?, ?, ?, ?, ?)";
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
        }  finally {
            if(pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        foods.add(food);

    }

    /**
     * Return the food object from the DB which is the same as foodID
     * @param foodID the id of the food object
     * @return food object
     */
    public static Food getFoodByID(int foodID) {
        String sql = "select * from food where id = " + foodID;
        Food actualFood = null;
        ResultSet rs = null;
        try {
            rs = createStatement.executeQuery(sql);
            if (rs.next()) {
                actualFood = new Food(
                        //rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("calories"),
                        rs.getInt("protein"),
                        rs.getInt("carbohydrate"),
                        rs.getInt("fat"));
            }

        } catch (SQLException e) {
            System.out.println("Something wrong with te getFoodByNameID method");
            System.out.println("" + e);
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return actualFood;
    }

    /**
     * Return all foods from the DB
     * @return Arraylist with all foods
     */
    public static ArrayList<Food> getAllFoods() {
        String sql = "select * from food";
        ArrayList<Food> foods = null;

        ResultSet rs = null;
        try {
            foods = new ArrayList<>();
            rs = createStatement.executeQuery(sql);

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
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return foods;
    }

    /**
     * Update the food in the DB
     * @param food food object
     */
    public static void updateFood(Food food) {
        String sql = "update food set name = ?, calories = ?, protein = ?, carbohydrate = ?, fat = ? where id = ?";
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, food.getName());
            pstm.setInt(2, food.getCalories());
            pstm.setInt(3, food.getProtein());
            pstm.setInt(4, food.getCarbohydrate());
            pstm.setInt(5, food.getFat());
            pstm.setInt(6, food.getId());
            pstm.execute();

            pstm.close();
        } catch (SQLException e) {
            System.out.println("Something wrong with the updateFood method");
            System.out.println("" + e);
        } finally {
            if(pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Reset food and meal table
     */
    public static void clearFoodTable() {
        String sql = "delete from food";
        String sql2 = "delete from meal";
        PreparedStatement pstm = null;
        PreparedStatement pstm2 = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm2 = conn.prepareStatement(sql2);
            pstm.execute();
            pstm2.execute();

        } catch (SQLException e) {
            System.out.println("Something wrong with the clearFoodTable method");
            System.out.println("" + e);
        } finally {
            if(pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pstm2 != null) {
                try {
                    pstm2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        foods.clear();
    }

    /**
     * Return the foods which is same as name
     * @param name the name of the food
     */
    public static void searchFoodByName(String name) {
        foods.clear();
        foods.addAll(getAllFoods());
        foods.removeIf(f -> !f.getName().contains(name));
    }

    /**
     * Reset the searching, and set up foods from the DB
     */
    public static void resetSearchFood() {
        foods.clear();
        foods.addAll(getAllFoods());
    }

    /**
     * Return foods which contains all foods
     * @return ObservableList with all foods object
     */
    public static ObservableList<Food> getFoods() {
        return foods;
    }
    //</editor-fold">

    //<editor-fold desc="all methods to meal table">

    /**
     *
     * @param whichMeal
     * @param date
     */
    public static void searchMealsByWhichMeal(int whichMeal, LocalDate date) {
        meals.clear();
        meals.addAll(getAllMeals());
        meals.removeIf(f -> !(f.getWhichMeal() == whichMeal));
        meals.removeIf(f -> !(f.getDate().toLocalDate().equals(date)));
    }

    /**
     *
     * @param meal
     */
    public static void addMeal(Meal meal) {
        PreparedStatement pstm  = null;
        try {
            String sql = "insert into meal (foodId, date, whichMeal, weight) values(?, ?, ?, ?)";
            pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
        } finally {
            if(pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        meals.add(meal);

    }

    /**
     *
     * @return
     */
    public static ArrayList<Meal> getAllMeals() {
        String sql = "select * from meal";
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        ArrayList<Meal> meals = null;

        try {
            meals = new ArrayList<>();
            rs = createStatement.executeQuery(sql);
            while (rs.next()) {
                Meal actualMeal = new Meal(
                        rs.getInt("id"),
                        rs.getInt("foodId"),
                        rs.getDate("date"),
                        rs.getInt("whichMeal"),
                        rs.getInt("weight"));
                meals.add(actualMeal);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Something wrong with the getAllMeals method");
            System.out.println("" + e);
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return meals;
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

    /**
     *
     * @return
     */
    public static ObservableList<Meal> getMeals() {
        return meals;
    }

    //</editor-fold">

    //<editor-fold desc="all methods to user table">

    /**
     *
     * @param newUser
     */
    public static void addUser(User newUser) {
        String sqlDeleteData = "delete from user2";
        PreparedStatement pstm2 = null;
        try {
            pstm2 = conn.prepareStatement(sqlDeleteData);
            pstm2.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(pstm2 != null) {
                try {
                    pstm2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        String sql = "insert into user2 (firstname, lastname, weight, gender, birthday, height, activityMultiplier) values(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, newUser.firstName);
            pstm.setString(2, newUser.lastName);
            pstm.setInt(3, newUser.weight);
            pstm.setString(4, newUser.gender);
            pstm.setDate(5, Date.valueOf(newUser.birthday));
            pstm.setInt(6, newUser.height);
            pstm.setDouble(7, newUser.activityMultiplier);
            pstm.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        user = newUser;

    }

    /**
     *
     */
    private static void setUserTable() {
        String sql = "select count(*) as count from user2";
        ResultSet rs = null;
        int count = 0;
        try {
            rs = createStatement.executeQuery(sql);
            while (rs.next()) {
                count = rs.getInt("count");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        if (count != 1) {
            User generalUser = new User("Quest",
                    "Quest",
                    70,
                    "Male", LocalDate.of(2000, 2, 12), 170, 1.35);
            DB.addUser(generalUser);
        }
    }

    /**
     *
     * @return
     */
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
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return actualUser;
    }

    //</editor-fold>

//    public static void getAllMealsWithMeta() {
//        String sql = "select * from meal";
//        ResultSet rs = null;
//        ResultSetMetaData rsmd = null;
//        try {
//            rs = createStatement.executeQuery(sql);
//            rsmd = rs.getMetaData();
//
//            int columnCount = rsmd.getColumnCount();
//            for (int i = 1; i < columnCount + 1; i++) {
//                System.out.print(rsmd.getColumnName(i) + " | ");
//            }
//            System.out.println();
//
//            while (rs.next()) {
//                int id = rs.getInt(rsmd.getColumnName(1));
//                int foodId = rs.getInt(rsmd.getColumnName(2));
//                Date date = rs.getDate(rsmd.getColumnName(3));
//                int whichMeal = rs.getInt(rsmd.getColumnName(4));
//                int weight = rs.getInt(rsmd.getColumnName(5));
//                System.out.println(id + " | " + foodId + " | " + date + " | " + whichMeal + " | " + weight);
//            }
//
//
//        } catch (SQLException e) {
//            System.out.println("Something wrong with the reading of the data");
//            System.out.println("" + e);
//        }
//    }
//
//    public static void getAllUserWithMeta() {
//        String sql = "select * from user2";
//        ResultSet rs = null;
//        ResultSetMetaData rsmd = null;
//        try {
//            rs = createStatement.executeQuery(sql);
//            rsmd = rs.getMetaData();
//            int columnCount = rsmd.getColumnCount();
//            for (int i = 1; i < columnCount + 1; i++) {
//                System.out.print(rsmd.getColumnName(i) + " | ");
//            }
//            System.out.println();
//
//            while (rs.next()) {
//                String firstName = rs.getString(rsmd.getColumnName(1));
//                String lastName = rs.getString(rsmd.getColumnName(2));
//                int weight = rs.getInt(rsmd.getColumnName(3));
//                String gender = rs.getString(rsmd.getColumnName(4));
//                Date date = rs.getDate(rsmd.getColumnName(5));
//                int height = rs.getInt(rsmd.getColumnName(6));
//                double activity = rs.getDouble(rsmd.getColumnName(7));
//                System.out.println(firstName + " | " + lastName + " | " + weight + " | " + gender + " | " + date + " | " + height + " | " + activity);
//            }
//
//
//        } catch (SQLException e) {
//            System.out.println("Something wrong with the reading of the data");
//            System.out.println("" + e);
//        }
//    }
//
//    public static void getAllFoodsWithMeta() {
//        String sql = "select * from food";
//        ResultSet rs = null;
//        ResultSetMetaData rsmd = null;
//        try {
//            rs = createStatement.executeQuery(sql);
//            rsmd = rs.getMetaData();
//
//            int columnCount = rsmd.getColumnCount();
//            for (int i = 1; i < columnCount + 1; i++) {
//                System.out.print(rsmd.getColumnName(i) + " | ");
//            }
//            System.out.println();
//
//            while (rs.next()) {
//                int foodId = rs.getInt(rsmd.getColumnName(1));
//                String foodName = rs.getString(rsmd.getColumnName(2));
//                int calories = rs.getInt(rsmd.getColumnName(3));
//                int protein = rs.getInt(rsmd.getColumnName(4));
//                int carbs = rs.getInt(rsmd.getColumnName(5));
//                int fat = rs.getInt(rsmd.getColumnName(5));
//                System.out.println(foodId + " | " + foodName + " | " + calories + " | " + protein + " | " + carbs + " |" + fat);
//            }
//
//
//        } catch (SQLException e) {
//            System.out.println("Something wrong with the reading of the data");
//            System.out.println("" + e);
//        }
//    }
}
