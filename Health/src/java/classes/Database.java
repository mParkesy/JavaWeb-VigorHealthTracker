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
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.joda.time.DateTime;

/**
 * A class that makes a connection to a MySQL database and contains all methods
 * that run a query with the database
 */
public class Database {

    private static final String URL = "jdbc:mysql://localhost/studentdb";
    private static final String USERNAME = "root";
    private static final String PASS = "";

    private static Connection CON = null;

    /**
     * A blank constructor, Database is instantiated many times and connection
     * to the database only needs to be made once on login
     */
    public Database() {
    }

    /**
     * A static method that makes the connection to the database, this only runs
     * when the database is using and there are no sessions or once a user
     * logins in
     *
     * @return A connection variable
     * @throws Exception If the connection to the database fails
     */
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

    /**
     * A method that takes a string and digests it, this is used with passwords
     * in the database, the method can digest and undigest a string
     *
     * @param p The string to digest
     * @return The digested string
     * @throws NoSuchAlgorithmException If the digest fails
     */
    public static String passwordDigest(String p)
            throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(p.getBytes(), 0, p.length());
        return new BigInteger(1, digest.digest()).toString(16);
    }

    /**
     * A static method that is used throughout the system, it is called when a
     * sweet alert pop up needs to be generated for an error message to the user
     *
     * @param message The message required in the alert
     * @return The message surround by the sweet alert body
     */
    public static String makeAlert(String message, String type) {
        String title = "";
        if(type.equals("error")){
            title = "There was an error";
        } else if (type.equals("success")){
            title = "Success!";
        }
        return "<script>"
                + "swal({type : '" + type + "',"
                + "title: '" + title + "',"
                + "text: '" + message + "',"
                + "showConfirmButton: false,"
                + "timer: 3000"
                + "})</script>";
    }

// ---------------------------------------------USER----------------------------------------------------------
    /**
     * A method that returns a User object from the database based on the userID
     * passed to it
     *
     * @param userID The userID of the User that needs to be constructed
     * @return The User object
     * @throws SQLException If the SQL statement fails to execute
     */
    public User getUser(int userID) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM user WHERE userID =?";
        PreparedStatement st = CON.prepareStatement(sql);
        st.setInt(1, userID);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            user = new User(userID, rs.getString("username"),
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

    /**
     * A method that returns a User object from the database based on the
     * username passed to it
     *
     * @param username The username of the User that needs to be constructed
     * @return The User object
     * @throws SQLException If the SQL statement fails to execute
     */
    public User getUser(String username) throws SQLException, Exception {
        User user = null;
        String sql = "SELECT * FROM user WHERE username =?";
        PreparedStatement st = getConnection().prepareStatement(sql);
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

    /**
     * A method that gets the verification for a specific User from the database
     *
     * @param userID The userID of the User whose verification is needed
     * @return The verification string from the database
     * @throws SQLException If the SQL statement fails to execute
     */
    public String getVerification(int userID) throws SQLException {
        String sql = "SELECT verification FROM user WHERE userID = ?";
        PreparedStatement st = CON.prepareStatement(sql);
        st.setInt(1, userID);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            return rs.getString("verification");
        }
        return null;
    }

    /**
     * An update method for a users verification, used when a User has clicked
     * the link emailed to them, it sets their verification string to 1
     *
     * @param ver The verification string from the URL sent by email
     * @throws Exception If the SQL statement fails to execute
     */
    public User updateVerification(String ver, int userID) throws Exception {
        User user = null;
        try {
            String sql = "UPDATE user SET verification = ?";
            PreparedStatement st = null;
            if (userID == 0) {
                sql = sql + "WHERE verification = ?";
                st = getConnection().prepareStatement(sql);
                st.setString(1, "1");
                st.setString(2, ver);
                String sqlSelect = "SELECT * FROM user WHERE verification =?";
                PreparedStatement st2 = getConnection().prepareStatement(sqlSelect);
                st2.setString(1, ver);

                ResultSet rs = st2.executeQuery();
                if (rs.next()) {
                    user = new User(rs.getInt("userID"), rs.getString("username"),
                            rs.getString("firstname"), rs.getString("lastname"),
                            rs.getString("gender"), rs.getString("postcode"),
                            rs.getString("nationality"), rs.getString("email"),
                            rs.getDouble("height"), (Date) rs.getDate("dob"),
                            rs.getDouble("exerciseLevel")
                    );
                }
            } else {
                sql = sql + "WHERE userID = ?";
                st = getConnection().prepareStatement(sql);
                st.setString(1, ver);
                st.setInt(2, userID);
            }
            int affected = st.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("Failed to update verification field");
        }
        return user;
    }

    /**
     * A method that gets the password for a specific User from the database
     *
     * @param userID The userID of the User whose password is needed
     * @return The password in its long format from the database as a string
     * @throws SQLException If the SQL statement fails to execute
     */
    public String getPassword(int userID) throws SQLException {
        String sql = "SELECT password FROM user WHERE userID = ?";
        PreparedStatement st = CON.prepareStatement(sql);
        st.setInt(1, userID);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            return rs.getString("password");
        }
        return null;
    }

    /**
     * A method that updates a Users password in the database based on the
     * userID
     *
     * @param newPassword The new password as as a string
     * @param userID The userID of the Users password that needs changing
     * @return True if successfully change, false if not
     * @throws SQLException If the SQL statement fails to execute
     */
    public boolean updatePassword(String newPassword, int userID)
            throws SQLException {
        try {
            String sql = "UPDATE user SET password = ? WHERE userID  = ?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setString(1, newPassword);
            st.setInt(2, userID);
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * A method that inserts a new User into the database and returns the new
     * User object if it is successful
     *
     * @param username The new username
     * @param password The new password having been digested
     * @param firstname The new firstname
     * @param lastname The new lastname
     * @param gender The new gender
     * @param dob The new DOB
     * @param postcode The new postcode
     * @param nationality The new nationality
     * @param email The new email
     * @param height The new height
     * @param weight The new height
     * @param exercise The exercise level
     * @param verification The verification code for the account
     * @return The new User object
     * @throws Exception If the Insert SQL statement fails to execute
     */
    public User insertUser(String username, String password, String firstname,
            String lastname, String gender, Date dob, String postcode,
            String nationality, String email, double height, double weight,
            double exercise, String verification) throws Exception {
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
                    + "height, exerciseLevel, verification) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
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
            st.setString(12, verification);

            st.executeUpdate();
            int userID = 0;
            /**
             * get the auto incremented id created by the database, construct
             * user
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
            // weight instance needs to be created in database
            insertWeight(userID, weight, now);

        } catch (SQLException ex) {
            System.out.println("Duplicate database entry for User");
        }
        return user;
    }

    /**
     * A method that collects all weight data for a specific User
     *
     * @param userID The userID of the User whose weight data is being placed
     * into a list
     * @return An ArrayList of Weight objects
     * @throws Exception If the Select SQL fails to execute
     */
    public ArrayList<Weight> allWeight(int userID) throws Exception {
        ArrayList<Weight> weightList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM weight WHERE userID = ? "
                    + "ORDER BY date DESC";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);

            ResultSet result = st.executeQuery();
            Weight weight;

            while (result.next()) {
                int weightID = result.getInt("weightID");
                double newWeight = result.getDouble("weight");
                Date date = result.getDate("date");

                weight = new Weight(weightID, userID, newWeight, date);

                weightList.add(weight);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get a user's weight logs");
        }

        return weightList;
    }

    /**
     * A method that collects all food log data for a specific User
     *
     * @param userID The userID of the User whose food log data is being placed
     * into a list
     * @return An ArrayList of food log objects
     * @throws Exception If the Select SQL fails to execute
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
     * A method that collects all sleep data for a specific User
     *
     * @param userID The userID of the User whose sleep data is being placed
     * into a list
     * @return An ArrayList of Sleep objects
     * @throws Exception If the Select SQL fails to execute
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
                java.sql.Timestamp bedTime = result.getTimestamp("bedTime");
                java.sql.Timestamp wakeTime = result.getTimestamp("wakeTime");
                int sleepGrade = result.getInt("sleepGrade");

                sleep = new Sleep(sleepID, userID, new DateTime(bedTime.getTime()),
                        new DateTime(wakeTime.getTime()), sleepGrade);

                sleepList.add(sleep);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get user's sleep logs");
        }
        return sleepList;
    }

    /**
     * A method that collects all exercise data for a specific User
     *
     * @param userID The userID of the User whose exercise data is being placed
     * into a list
     * @return An ArrayList of Exercise objects
     * @throws Exception If the Select SQL fails to execute
     */
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
     * @return A new sleep object
     * @throws Exception If the Insert SQL fails to execute
     */
    public Sleep insertSleep(int userID, DateTime bedTime, DateTime wakeTime,
            int sleepGrade) throws Exception {
        Sleep sleep = null;
        try {
            String sql = "INSERT INTO sleep "
                    + "(userID, bedTime, wakeTime, sleepGrade) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, userID);
            java.sql.Timestamp bed = new java.sql.Timestamp(bedTime.getMillis());
            st.setTimestamp(2, bed);
            java.sql.Timestamp wake = new java.sql.Timestamp(wakeTime.getMillis());
            st.setTimestamp(3, wake);
            st.setInt(4, sleepGrade);

            st.executeUpdate();
            try (ResultSet key = st.getGeneratedKeys()) {
                if (key.next()) {
                    sleep = new Sleep(key.getInt(1), userID, bedTime,
                            wakeTime, sleepGrade);
                } else {
                    throw new SQLException("No ID found, Sleep data not saved");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to insert sleep data");
        }
        return sleep;
    }

    // ---------------------------------------------WEIGHT----------------------------------------------------------
    /**
     * Construct the most recent weight instance from the database
     *
     * @param userID The userID of the User whose current weight is needed
     * @return The most recent weight object for the user
     * @throws Exception If the Select SQL statement fails to execute
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
                    insertMember(groupID, userID);
                    acceptInvite(groupID, userID);
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

    public boolean insertMember(int groupID, int userID) {
        try {
            String sql = "INSERT INTO groupmembers (userID, groupID, joined)"
                    + "VALUES (?,?, 0)";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            st.setInt(2, groupID);
            st.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println("Failed to add a new member into a group");
            return false;
        }
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
                if (!(isAdmin(userID, group.getGroupID()) && joined == 0)) {
                    memberOf.add(group);
                }

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

    public double getGroupDistance(int groupID, String date) {
        double totalDistance = 0;
        try {
            String sql = "SELECT SUM(exercise.distance) as total FROM exercise INNER JOIN groupmembers on exercise.userID = groupmembers.userID WHERE groupmembers.groupID = ?";
            if (!date.equals("")) {
                sql = sql + "AND exercise.date > '" + date + "'";
            }
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, groupID);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                totalDistance += result.getDouble("total");
            }
        } catch (Exception ex) {
            System.out.println("Failed to get groups total distance");
        }
        return totalDistance;
    }

    public TreeMap getGroupDistanceLeaderboard(int groupID) throws SQLException {
        TreeMap<Double, Integer> t = new TreeMap(Collections.reverseOrder());
        String sql = "SELECT e.userID, SUM(e.distance) as total "
                + "FROM exercise e INNER JOIN groupmembers g "
                + "ON e.userID = g.userID "
                + "WHERE g.groupID = ? GROUP BY e.userID";
        PreparedStatement st = CON.prepareStatement(sql);
        st.setInt(1, groupID);
        ResultSet result = st.executeQuery();

        while (result.next()) {
            User user = getUser(result.getInt("e.userID"));
            t.put(result.getDouble("total"), result.getInt("e.userID"));
        }
        Set set = t.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            System.out.print(me.getKey() + ": " + me.getValue());
        }

        return t;
    }

    // ---------------------------------------------ACTIVITY----------------------------------------------------------
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

    // ---------------------------------------------EXERCISE----------------------------------------------------------
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

    public Exercise getExercise(int exerciseID) throws Exception {
        Exercise exercise = null;
        try {
            String sql = "SELECT * FROM exercise WHERE exerciseID = ?";

            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, exerciseID);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                exercise = new Exercise(exerciseID, result.getInt("userID"), getActivity(result.getInt("activityID")), result.getDate("date"), result.getInt("minutes"), result.getDouble("distance"));
            }
        } catch (Exception ex) {
            System.out.println("Failed to get exercise by ID");
        }
        return exercise;
    }

    // ---------------------------------------------FOOD----------------------------------------------------------
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

    // ---------------------------------------------FOODLOG----------------------------------------------------------
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

    //----------------------------NOTIFICATIONS--------------------------------------
    public ArrayList<Notification> getNotifications(int id) throws SQLException, Exception {
        ArrayList<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notification WHERE userID =?";
        PreparedStatement st = this.CON.prepareStatement(sql);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            list.add(new Notification(rs.getInt("id"), rs.getString("Text")));
        }
        return list;
    }

    public void deleteNotification(int id) {

        Notification n = null;
        try {
            String sql = "DELETE FROM notification WHERE id = ?";
            PreparedStatement st = this.CON.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("ERROR DELETING NOTIF");
        }

    }

    // ---------------------------------------------GOAL----------------------------------------------------------
    public Goal insertGoal() {

        return null;
    }

    public Goal getGoal(int groupID) {

        return null;
    }

    // ---------------------------------------------MESSAGE----------------------------------------------------------
    //gets conversation between two users
    public ArrayList<Message> getMessages(int senderID, int recipientID) throws SQLException, Exception {
        ArrayList<Message> list = new ArrayList<>();
        String sql = "SELECT * FROM `message` "
                + "WHERE (recipientID = ? AND senderID = ?) "
                + "OR (recipientID = ? AND senderID = ?)"
                + "ORDER BY time ASC";
        PreparedStatement st = this.CON.prepareStatement(sql);
        st.setInt(1, recipientID);
        st.setInt(2, senderID);
        st.setInt(3, senderID);
        st.setInt(4, recipientID);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            User sender = this.getUser(rs.getInt("senderID"));
            User recipent = this.getUser(rs.getInt("recipientID"));
            Message msg = new Message(rs.getString("message"), sender, recipent);
            list.add(msg);
        }
        return list;
    }

    public ArrayList<Integer> getUnread(int userID) throws SQLException, Exception {
        ArrayList<Integer> list = new ArrayList<>();
        String sql = "SELECT senderID ,MIN(time) "
                + "FROM message WHERE recipientID = ? "
                + "AND seen = 0 "
                + "GROUP BY senderID";
        PreparedStatement st = this.CON.prepareStatement(sql);
        st.setInt(1, userID);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            list.add(rs.getInt("senderID"));
        }
        return list;
    }

    //Send message
    public void sendMessage(String message, int senderID, int recipientID) throws SQLException {
        try {
            String sql = "INSERT INTO `message` "
                    + "(senderID, recipientID, message,seen) "
                    + "VALUES (?,?,?,0)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, senderID);
            st.setInt(2, recipientID);
            st.setString(3, message);

            st.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Failed to send message");
        }
    }

    //Set message as seen
    public void setSeen(int userID, int senderID) throws SQLException {
        try {
            String sql = "UPDATE message SET seen = 1 WHERE senderID = ? AND recipientID = ?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, senderID);
            st.setInt(2, userID);

            st.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
