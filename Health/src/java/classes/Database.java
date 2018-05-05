package classes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Database {

    private static final String URL = "jdbc:mysql://localhost/studentdb";
    private static final String USERNAME = "root";
    private static final String PASS = "";

    private static Connection CON = null;

    public Database() {

    }

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        if (CON == null) {
            CON = DriverManager.getConnection(URL, USERNAME, PASS);
            return CON;
        } else if (CON.isValid(1) == false) {
            CON = DriverManager.getConnection(URL, USERNAME, PASS);
            return CON;
        } else {
            return CON;
        }
    }

    public static String passwordDigest(String p) 
            throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(p.getBytes(), 0, p.length());
        return new BigInteger(1, digest.digest()).toString(16);
    }

    // ---------------------------------------------USER----------------------------------------------------------
    public User getUser(int id) throws SQLException, Exception {
        User user = null;
        String sql = "SELECT * FROM user WHERE userID =?";
        PreparedStatement st = CON.prepareStatement(sql);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            user = new User(id, rs.getString("username"), 
                    rs.getString("firstname"), rs.getString("lastname"), 
                    rs.getString("gender"), rs.getString("postcode"),
                    rs.getString("nationality"), rs.getString("email"), 
                    rs.getDouble("height"), (Date) rs.getDate("dob"), 
                    rs.getDouble("exerciseLevel")
            );

        } else {
            return user;
        }
        return user;
    }

    public User getUser(String username) throws SQLException, Exception {
        User user = null;
        String sql = "SELECT * FROM user WHERE username =?";
        PreparedStatement st = CON.prepareStatement(sql);
        st.setString(1, username);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            user = new User(rs.getInt("userID"), username, 
                    rs.getString("firstname"), rs.getString("lastname"), 
                    rs.getString("gender"), rs.getString("postcode"),
                    rs.getString("nationality"), rs.getString("email"), 
                    rs.getDouble("height"), (Date) rs.getDate("dob"), 
                    rs.getDouble("exerciseLevel")
            );
        } else {
            return user;
        }
        return user;
    }
    
    public String getPassword(int userID) throws SQLException {
        String sql = "SELECT password FROM user WHERE userID = ?";
        PreparedStatement st = CON.prepareStatement(sql);
        st.setInt(1, userID);
        ResultSet rs = st.executeQuery();
        while(rs.next()){
            return rs.getString("password");
        }
        return null;
    }

    public User insertUser(String username, String password, String firstname,
            String lastname, String gender, Date dob, String postcode,
            String nationality, String email, double height, double weight,
            double exercise) throws Exception {
        User user = null;
        try {
            String dbPassword = "";
            try {
                dbPassword = passwordDigest(password);
            } catch (NoSuchAlgorithmException ex) {
                System.out.println("Failed to hash password string");
            }

            String sql = "INSERT INTO `user`(username, password, firstname, "
                    + "lastname, gender, dob, postcode, nationality, email, "
                    + "height, exerciseLevel) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, username);
            st.setString(2, dbPassword);
            st.setString(3, firstname);
            st.setString(4, lastname);
            st.setString(5, gender);
            java.sql.Date date = new java.sql.Date(dob.getTime());
            st.setDate(6, date);
            st.setString(7, postcode);
            st.setString(8, nationality);
            st.setString(9, email);
            st.setDouble(10, height);
            st.setDouble(11, exercise);

            st.executeUpdate();
            int userID = 0;
            /**
             * get the auto incremented id created by the database, 
             * construct user
             */
            try (ResultSet key = st.getGeneratedKeys()) {
                if (key.next()) {
                    userID = key.getInt(1);
                    user = new User(userID, username, firstname, lastname,
                            gender, postcode, nationality, email, height, 
                            dob, exercise);
                } else {
                    throw new SQLException("No ID found, User not created");
                }
            }
            Date now = new Date();
            insertWeight(userID, weight, now);

        } catch (SQLException ex) {
            System.out.println("Duplicate database entry");
        }
        return user;
    }

    /**
     * Get all weight data in an arrayList for a specific user
     *
     * @return A list of all weights
     * @throws Exception
     */
    public ArrayList<Weight> allWeight(int id) throws Exception {
        ArrayList<Weight> weightList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM weight WHERE userID = ? "
                    + "ORDER BY date DESC";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, id);

            ResultSet result = st.executeQuery();
            Weight weight;

            while (result.next()) {
                int weightID = result.getInt("weightID");
                double newWeight = result.getDouble("weight");
                Date date = result.getDate("date");

                weight = new Weight(weightID, id, newWeight, date);

                weightList.add(weight);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get a user's weight logs");
        }

        return weightList;
    }

    /**
     *
     * @return @throws Exception
     */
    public ArrayList<FoodLog> allFoodLogs(int userID) throws Exception {

        ArrayList<FoodLog> logs = new ArrayList<>();
        try {
            String sql = "SELECT * FROM foodLog where userID = ?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                Food food = getFood(result.getInt("foodID"));
                String meal = result.getString("meal");
                Date date = result.getDate("date");

                logs.add(new FoodLog(id, food, userID, meal, date));
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get a user's food logs");
        }
        return logs;

    }

    /**
     * Get all sleep data in an arrayList for a specific user
     *
     * @return A list of all sleep
     * @throws Exception
     */
    public ArrayList<Sleep> allSleep(int userID) throws Exception {
        ArrayList<Sleep> sleepList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM sleep WHERE userID = ?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);

            ResultSet result = st.executeQuery();
            Sleep sleep;

            while (result.next()) {
                int sleepID = result.getInt("sleepID");
                Date bedTime = result.getDate("bedTime");
                Date wakeTime = result.getDate("wakeTime");
                int sleepGrade = result.getInt("sleepGrade");

                sleep = new Sleep(sleepID, userID, bedTime, 
                        wakeTime, sleepGrade);

                sleepList.add(sleep);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get user's sleep logs");
        }
        return sleepList;
    }

    public ArrayList<Exercise> allExercise(int userID) throws Exception {
        ArrayList<Exercise> exerciseList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM exercise WHERE userID = ?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);

            ResultSet result = st.executeQuery();
            Exercise exercise;

            while (result.next()) {
                int exerciseID = result.getInt("exerciseID");
                Date date = result.getDate("date");
                int minutes = result.getInt("minutes");
                double distance = result.getDouble("distance");

                exercise = new Exercise(exerciseID, userID, getActivity(
                        result.getInt("activityID")), date, minutes, distance);
                exerciseList.add(exercise);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get user's excercise logs");
        }
        return exerciseList;
    }

    // ---------------------------------------------SLEEP----------------------------------------------------------
    /**
     * Construct a sleep instance and add it to the database
     *
     * @param userID
     * @param bedTime
     * @param wakeTime
     * @param sleepGrade
     * @return
     * @throws Exception
     */
    public Sleep insertSleep(int userID, Date bedTime, Date wakeTime, 
            int sleepGrade) throws Exception {
        Sleep sleep = null;
        try {
            String sql = "INSERT INTO sleep "
                    + "(userID, bedTime, wakeTime, sleepGrade) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, userID);
            java.sql.Date newBed = new java.sql.Date(bedTime.getTime());
            st.setDate(2, newBed);
            java.sql.Date newWake = new java.sql.Date(wakeTime.getTime());
            st.setDate(3, newWake);
            st.setInt(4, sleepGrade);

            st.executeQuery();

            try (ResultSet key = st.getGeneratedKeys()) {
                if (key.next()) {
                    sleep = new Sleep(key.getInt(1), userID, bedTime, 
                            wakeTime, sleepGrade);
                } else {
                    throw new SQLException("No ID found, Sleep data not saved");
                }
            }
        } catch (Exception ex) {
            System.out.println("Failed to insert sleep data");
        }
        return sleep;
    }

    // ---------------------------------------------WEIGHT----------------------------------------------------------
    /**
     * Construct the most recent weight instance from the database
     *
     * @param userID
     * @param weightID
     * @throws Exception
     */
    public Weight currentWeight(int userID) throws Exception {
        Weight weight = null;
        try {
            String sql = "SELECT * FROM weight WHERE date = (SELECT MAX(date) "
                    + "FROM weight where userID = ?)";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                weight = new Weight(result.getInt("weightID"), userID,
                        result.getDouble("weight"), result.getDate("date"));

            }
        } catch (Exception ex) {
            System.out.println("Failed to get current weight");
        }
        return weight;
    }

    /**
     * Construct a weight instance and add it to the database
     *
     * @param userID
     * @throws Exception
     */
    public Weight insertWeight(int userID, double weight, Date date) 
            throws Exception {
        Weight log = null;
        try {
            String sql = "INSERT INTO `weight` (userID, weight, date)"
                    + "VALUES (?,?,?)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, userID);
            st.setDouble(2, weight);
            java.sql.Date newDate = new java.sql.Date(date.getTime());
            st.setDate(3, newDate);

            st.executeUpdate();

            try (ResultSet key = st.getGeneratedKeys()) {
                if (key.next()) {
                    log = new Weight(key.getInt(1), userID, weight, date);
                } else {
                    throw new SQLException("No ID found, "
                            + "Weight data not saved");
                }
            }
        } catch (Exception ex) {
            System.out.println("Failed to insert weight log");
        }
        return log;
    }

    // ---------------------------------------------GROUP----------------------------------------------------------
    public Group insertGroup(int userID, String name, String description) 
            throws SQLException, Exception {
        Group group = null;
        try {
            String sql = "INSERT INTO ugroup (userID, name, description)"
                    + "VALUES (?,?,?)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, userID);
            st.setString(2, name);
            st.setString(3, description);
            st.executeUpdate();

            try (ResultSet key = st.getGeneratedKeys()) {
                if (key.next()) {
                    int groupID = key.getInt(1);
                    group = getGroup(groupID);
                    sql = "INSERT INTO groupmembers (userID, groupID, joined)"
                            + "VALUES (?,?,?)";
                    st = CON.prepareStatement(sql);
                    st.setInt(1, userID);
                    st.setInt(2, groupID);
                    st.setInt(3, 1);
                    st.executeUpdate();
                } else {
                    throw new SQLException("No ID found, group data not saved");
                }
            }

        } catch (Exception ex) {
            System.out.println("Failed to insert group");
        }
        return group;
    }

    public Group getGroup(int groupID) {
        Group group = null;
        try {
            String sql = "SELECT * FROM ugroup WHERE groupID = ?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, groupID);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                group = new Group(result.getInt("groupID"), 
                        result.getString("name"), result.getInt("userID"), 
                        result.getString("description"));
            }
        } catch (Exception ex) {
            System.out.println("Failed to get current weight");
        }
        return group;
    }

    public boolean isAdmin(int userID, int groupID) {
        try {
            String sql = "SELECT * FROM ugroup WHERE userID = ? "
                    + "AND groupID = ?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            st.setInt(2, groupID);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Failed to check if admin");
        }
        return false;
    }

    /**
     * A method that returns a list of groups that a specific user is either a
     * member of or has been invited to
     *
     * @param userID The user
     * @param joined 0 if not joined, 1 if joined
     * @return A list of groups
     */
    public ArrayList<Group> getGroupList(int userID, int joined) {
        ArrayList<Group> memberOf = new ArrayList<>();
        try {

            String sql = "SELECT * FROM groupmembers WHERE userID = ? "
                    + "AND joined =?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            st.setInt(2, joined);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                Group group = getGroup(result.getInt("groupID"));
                memberOf.add(group);
            }
        } catch (Exception ex) {
            System.out.println("Failed to get groups "
                    + "that the user is a part of");
        }

        return memberOf;
    }

    public void acceptInvite(int groupID, int userID) {
        try {
            String sql = "UPDATE groupmembers SET joined = 1 WHERE userID = ? "
                    + "AND groupID = ?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            st.setInt(2, groupID);
            st.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Failed to accept invite to group");
        }
    }

    public boolean sendInvite(int groupID, int userID) {
        try {
            String sql = "INSERT INTO groupmembers (userID, groupID, joined) "
                    + "VALUES(?,?,0)";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            st.setInt(2, groupID);
            st.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Failed to send invite to user");
            return false;
        }
    }

    // ---------------------------------------------Activity----------------------------------------------------------
    public Activity getActivity(int activityID) throws Exception {
        Activity activity = null;
        try {
            String sql = "SELECT * FROM activity WHERE activityID = ?";

            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, activityID);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                activity = new Activity(activityID, 
                        result.getString("activity"), result.getDouble("MET"));
            }
        } catch (Exception ex) {
            System.out.println("Failed to get activity");
        }
        return activity;
    }

    public ArrayList<Activity> allActivity() throws Exception {
        ArrayList<Activity> activityList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM activity";
            PreparedStatement st = CON.prepareStatement(sql);

            ResultSet result = st.executeQuery();
            Activity activity;

            while (result.next()) {
                int activityID = result.getInt("activityID");
                String name = result.getString("activity");
                double MET = result.getDouble("MET");
                activity = new Activity(activityID, name, MET);

                activityList.add(activity);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get activity list");
        }
        return activityList;
    }

    // ---------------------------------------------Exercise----------------------------------------------------------
    public Exercise insertExercise(int userID, int activityID, Date date, 
            int minutes, double distance) throws Exception {
        Exercise exercise = null;
        try {
            String sql = "INSERT INTO `exercise` (activityID, userID, "
                    + "date, minutes, distance)"
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, activityID);
            st.setInt(2, userID);
            java.sql.Date newDate = new java.sql.Date(date.getTime());
            st.setDate(3, newDate);
            st.setInt(4, minutes);
            st.setDouble(5, distance);

            st.executeUpdate();

            try (ResultSet key = st.getGeneratedKeys()) {
                if (key.next()) {
                    exercise = new Exercise(key.getInt(1), userID, 
                            getActivity(activityID), date, minutes, distance);

                } else {
                    throw new SQLException("No ID found, "
                            + "Exercise data not saved");
                }
            }
        } catch (Exception ex) {
            System.out.println("Failed to insert exercise");
        }
        return exercise;
    }

    // ---------------------------------------------Food----------------------------------------------------------
    public ArrayList<Food> allFood() throws Exception {
        ArrayList<Food> foodList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM food";
            PreparedStatement st = CON.prepareStatement(sql);

            ResultSet result = st.executeQuery();
            Food food;

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("Name");
                double protein = result.getDouble("Protein");
                double fat = result.getDouble("Fat");
                double carbs = result.getDouble("Carbs");
                double energy = result.getDouble("Energy");
                double sugar = result.getDouble("Sugar");

                food = new Food(id, name, protein, fat, carbs, energy, sugar);

                foodList.add(food);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return foodList;
    }

    public Food getFood(int id) throws Exception {
        Food food = null;
        try {
            String sql = "SELECT * FROM food WHERE id =?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                food = new Food(id, result.getString("name"), 
                        result.getDouble("protein"), result.getDouble("fat"), 
                        result.getDouble("carbs"), result.getDouble("energy"),
                        result.getDouble("sugar"));
            }
        } catch (Exception ex) {
            System.out.println("Failed to get food data");
        }
        return food;
    }

    // ---------------------------------------------FoodLog----------------------------------------------------------
    /**
     * Construct a FoodLog instance and add it to the database
     *
     * @param foodID
     * @param userID
     * @param meal
     * @param date
     * @throws Exception
     */
    public FoodLog insertFoodLog(int foodID, int userID, String meal, Date date)
            throws Exception {
        FoodLog log = null;
        try {
            String sql = "INSERT INTO `foodLog` "
                    + "(foodID, userID, meal, date) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, foodID);
            st.setInt(2, userID);
            st.setString(3, meal);
            java.sql.Date newDate = new java.sql.Date(date.getTime());
            st.setDate(4, newDate);

            st.executeUpdate();

            try (ResultSet key = st.getGeneratedKeys()) {
                if (key.next()) {
                    log = new FoodLog(key.getInt(1), getFood(foodID), 
                            userID, meal, date);
                } else {
                    throw new SQLException("No ID found, "
                            + "Food Log data not saved");
                }
            }
        } catch (Exception ex) {
            System.out.println("Failed to insert food log");
        }
        return log;
    }
}
