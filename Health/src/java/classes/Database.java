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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
     * @param type The type of sweet alert to create
     * @return The message surround by the sweet alert body
     */
    public static String makeAlert(String message, String type) {
        String title = "";
        if (type.equals("error")) {
            title = "There was an error";
        } else if (type.equals("success")) {
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

    public static String getLastSunday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_WEEK, -(cal.get(Calendar.DAY_OF_WEEK) - 1));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(cal.getTime());
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
                    rs.getString("gender"), rs.getString("email"),
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
                    rs.getString("gender"), rs.getString("email"),
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
     * @param userID The user ID for the verification change
     * @return The constructed user, used for a password change
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
                            rs.getString("gender"), rs.getString("email"),
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
            String lastname, String gender, Date dob, String email, double height, double weight,
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
                    + "lastname, gender, dob, email, "
                    + "height, exerciseLevel, verification) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, username);
            st.setString(2, dbPassword);
            st.setString(3, firstname);
            st.setString(4, lastname);
            st.setString(5, gender);
            java.sql.Date date = new java.sql.Date(dob.getTime());
            st.setDate(6, date);
            st.setString(7, email);
            st.setDouble(8, height);
            st.setDouble(9, exercise);
            st.setString(10, verification);

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
                            gender, email, height,
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
    
    public boolean updateUsername(int userID, String username){
        try {
            String sql = "UPDATE user SET username = ? WHERE userID =?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setString(1, username);
            st.setInt(2, userID);
            int affected = st.executeUpdate();
            if(affected == 0){
                return false;
            } else {
                return true;
            }
        } catch(SQLException ex){
            System.out.println("Failed to update username");
            return false;
        }
    }
    
    public boolean updateHeight(int userID, double height){
        try {
            String sql = "UPDATE user SET height = ? WHERE userID =?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setDouble(1, height);
            st.setInt(2, userID);
            int affected = st.executeUpdate();
            if(affected == 0){
                return false;
            } else {
                return true;
            }
        } catch(SQLException ex){
            System.out.println("Failed to update user height");
            return false;
        }
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
            String sql = "SELECT * FROM foodLog where userID = ? AND date = CURRENT_DATE";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                int id = result.getInt("foodLogID");
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
     * A method that collects old food log data for a specific User
     *
     * @param userID The userID of the User whose food log data is being placed
     * into a list
     * @return An ArrayList of food log objects
     * @throws Exception If the Select SQL fails to execute
     */
    public ArrayList<FoodLog> oldFoodLogs(int userID) throws Exception {

        ArrayList<FoodLog> logs = new ArrayList<>();
        try {
            String sql = "SELECT * FROM foodLog where userID = ? AND date != CURRENT_DATE";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                int id = result.getInt("foodLogID");
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
    
    public void deleteFoodLog(int id) {

        Notification n = null;
        try {
            String sql = "DELETE FROM foodlog WHERE foodLogID = ?";
            PreparedStatement st = this.CON.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("ERROR DELETING foodlog");
        }

    }
    
    //returns daily nutrients fir a user as a json string
    public String getNutrients(int userID){
        Food sum = new Food();
        try {
            String sql = "SELECT SUM(food.Protein) as protein,"
                    + "sum(food.Fat) as fat, "
                    + "sum(food.Carbs) as carbs, "
                    + "sum(food.Energy) as energy, "
                    + "sum(food.Sugar) as sugar "
                    + "FROM foodlog "
                    + "INNER JOIN food ON foodlog.foodID = food.id "
                    + "WHERE foodlog.date = CURRENT_DATE "
                    + "AND foodLog.userID = ?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            ResultSet result = st.executeQuery();
            
            while (result.next()) {
                sum.setCarbs(result.getDouble("carbs"));
                sum.setSugar(result.getDouble("sugar"));
                sum.setProtein(result.getDouble("protein"));
                sum.setEnergy(result.getDouble("energy"));
                sum.setFat(result.getDouble("fat"));
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get a user's nutrients");
        }
        return sum.toJSON();
        
    }
    
    public int getCaloriesBurnt(int userID){
        int sum = 0;
        Exercise e =null;
        try{
            String sql = "SELECT * FROM exercise WHERE userID = ? "
                    + "AND date = CURRENT_DATE";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                
                int exerciseID = rs.getInt("exerciseID");
                Date date = rs.getDate("date");
                int minutes = rs.getInt("minutes");
                double distance = rs.getDouble("distance");
                Activity activity = getActivity(
                        rs.getInt("activityID"));

                e= new Exercise(exerciseID, userID, activity , date, minutes, distance);
                System.out.println(e.getCaloriesBurnt());
                sum += e.getCaloriesBurnt();
            }
        }catch(Exception ex){
            System.out.println("Failed to sum calories from today");
            ex.printStackTrace();
        }
        return sum;
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
     * @param inputDate The date to get all exercise from, usually blank
     * @return An ArrayList of Exercise objects
     * @throws Exception If the Select SQL fails to execute
     */
    public ArrayList<Exercise> allExercise(int userID, String inputDate)
            throws Exception {
        ArrayList<Exercise> exerciseList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM exercise WHERE userID = ?";
            if (!inputDate.equals("")) {
                sql += " AND date > '" + inputDate + "'";
            }

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

    /**
     * A method that inserts a login log to the database
     * @param userID The userID of the user logging in
     * @throws SQLException If the SQL statement fails to insert
     */
    public void loginLog(int userID) throws SQLException {
        try {
            String sql = "INSERT INTO log (userID, login) "
                    + "VALUES(?,CURRENT_TIMESTAMP)";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Failed to insert into login log");
        }
    }

    /**
     * A method that updates the log to include a logout timestamp
     * @param userID The userID of the user logging out
     */
    public void logoutLog(int userID) {
        try {
            String sql = "UPDATE log SET logout = CURRENT_TIMESTAMP "
                    + "WHERE userID = ?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Failed to update log with logout time");
        }
    }
    /**
     * A method that can check to see if a user has logged out or not
     * @param userID The userID of the user being checked
     * @return True if logged in, false if not
     */
    public boolean isLoggedIn(int userID) {
        try {
            String sql = "SELECT logout FROM log WHERE userID = ? "
                    + "ORDER BY login DESC "
                    + "LIMIT 1";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                if(rs.getTimestamp("logout") == null){
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Failed to check if user is logged in");
            return false;
        }
        return false;
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
            System.out.println("Failed to insert sleep data");
        }
        return sleep;
    }

    public boolean deleteSleep(int sleepID){
        try{
            String sql = "DELETE FROM sleep WHERE sleepID =?";
            PreparedStatement st = CON.prepareCall(sql);
            st.setInt(1, sleepID);
            st.executeUpdate();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Failed to delete sleep");
            return false;
        }   
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
     * @param userID The userID of the user who is entering a weight
     * @param weight The weight as a double
     * @param date The date for the weight entry
     * @return The weight object entered
     * @throws Exception If the SQL insert fails
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
    
    public boolean deleteWeight(int weightID){
        try{
            String sql = "DELETE FROM weight WHERE weightID =?";
            PreparedStatement st = CON.prepareCall(sql);
            st.setInt(1, weightID);
            st.executeUpdate();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Failed to delete weight");
            return false;
        }   
    }

    // ---------------------------------------------GROUP----------------------------------------------------------
    
    /**
     * A method that takes group details and inserts it into the database
     * @param userID The userID of the user making a group
     * @param name The group name
     * @param description The description
     * @param image The image
     * @param distanceGoal The distance goal for the group
     * @return The group object inserted
     * @throws SQLException If the SQL Fails
     * @throws Exception  If fails to get ID generated
     */
    public Group insertGroup(int userID, String name, String description, 
            String image, String distanceGoal)
            throws SQLException, Exception {
        Group group = null;
        try {
            String sql = "INSERT INTO ugroup (userID, name, description, image, distanceGoal)"
                    + "VALUES (?,?,?,?,?)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, userID);
            st.setString(2, name);
            st.setString(3, description);
            st.setString(4, image);
            st.setString(5, distanceGoal);
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
            ex.printStackTrace();
        }
        return group;
    }

    /**
     * Gets a group object from the database
     * @param groupID The groupID of the needed object
     * @return The group object required
     */
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
                        result.getString("description"),
                        result.getString("image"), 
                        result.getString("distanceGoal"));

            }
        } catch (Exception ex) {
            System.out.println("Failed to get group");

        }
        return group;
    }
    
    /**
     * A method to insert an invited but not joined user to a group
     * @param groupID The group that the user has been invited to
     * @param userID The users ID who is being invited
     * @return True if successfully added, false if not
     */
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

    /**
     * Checks to see if the user is an admin for the group
     * @param userID The user ID of the potential admijn
     * @param groupID The group ID to be checked
     * @return True if they are admin, false if not
     */
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
            return false;
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

    /**
     * Accept an invite for a user 
     * @param groupID The group that the invite is being accepted for
     * @param userID The userId of the invited user
     */
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

    /**
     * A method that sends an invite by inserting into database with joined as 0
     * @param groupID The groupID
     * @param userID The userID for the invite
     * @return True if sent, false if not
     */
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

    /**
     * Gets the group distance forever or since a passed date
     * @param groupID The groupID to get their distance
     * @param date The date to be passed into the query
     * @return 
     */
    public double getGroupDistance(int groupID, String date) {
        double totalDistance = 0;
        try {
            String sql = "SELECT SUM(exercise.distance) as total FROM exercise "
                    + "INNER JOIN groupmembers on "
                    + "exercise.userID = groupmembers.userID "
                    + "WHERE groupmembers.groupID = ? "
                    + "AND date > '" + date + "'";
            
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, groupID);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                totalDistance += result.getDouble("total");
            }
        } catch (Exception ex) {
            System.out.println("Failed to get groups total distance");
            ex.printStackTrace();
        }
        return totalDistance;
    }

    /**
     * A method that creates a distance leaderboard and passes it into 
     * a treemap
     * @param groupID The groupID that the leaderboard is needed for
     * @return The leaderboard as a treemap
     * @throws SQLException If the SQl fails
     */
    public TreeMap getGroupDistanceLeaderboard(int groupID) throws SQLException {
        TreeMap<Integer, Double> t = new TreeMap(Collections.reverseOrder());
        String date = getLastSunday();
        String sql = "SELECT e.userID, SUM(e.distance) as total "
                + "FROM exercise e INNER JOIN groupmembers g "
                + "ON e.userID = g.userID "
                + "WHERE g.groupID = ? AND date > '" + date
                + "' GROUP BY e.userID";
        PreparedStatement st = CON.prepareStatement(sql);
        st.setInt(1, groupID);
        ResultSet result = st.executeQuery();

        while (result.next()) {
            t.put(result.getInt("e.userID"), result.getDouble("total"));
        }
        return t;
    }

    /**
     * A method that creates a calorie leaderboard and passes it into 
     * a treemap
     * @param groupID The groupID that the leaderboard is needed for
     * @return The leaderboard as a treemap
     * @throws SQLException If the SQl fails
     */
    public TreeMap getGroupCalorieLeaderboard(int groupID)
            throws SQLException, Exception {
        TreeMap<Integer, Integer> t = new TreeMap();
        String date = getLastSunday();
        ArrayList<User> userList = getMembers(groupID);
        for (User u : userList) {
            int calories = 0;
            ArrayList<Exercise> exerciseList = allExercise(u.getID(), date);
            for (Exercise e : exerciseList) {
                calories += e.getCaloriesBurnt();
            }
            if (calories != 0) {
                t.put(u.getID(), calories);
            }
        }

        return t;
    }

    /**
     * Gets a list of User objects who are a part of a group
     * @param groupID The group ID to get list of members
     * @return The list of users
     */
    public ArrayList<User> getMembers(int groupID) {
        ArrayList<User> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM groupmembers where groupID = ? "
                    + "AND joined = 1";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, groupID);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                User user = getUser(result.getInt("userID"));
                list.add(user);
            }
        } catch (Exception ex) {
            System.out.println("Failed to get member list");
        }
        return list;
    }

    /**
     * An update method to update a row in the database with new group info
     * @param groupID The groupID of the group for the update to occur on
     * @param name The new name of the group
     * @param description The new description
     * @param image The new image path
     * @param distanceGoal The new distance goal
     * @return True if update successful, false if not
     */
    public boolean updateGroup(int groupID, String name, String description, 
            String image, String distanceGoal){
        try {
            String sql = "UPDATE ugroup SET name = ?, description = ?, "
                    + "image = ?, distanceGoal =? WHERE groupID = ?"; 
            PreparedStatement st = CON.prepareCall(sql);
            st.setString(1, name);
            st.setString(2, description);
            st.setString(3, image);
            st.setString(4, distanceGoal);
            st.setInt(5, groupID);
            int affected  = st.executeUpdate();
            if(affected > 0){
                return true;
            }
        }catch (Exception ex){
            System.out.println("Failed to update group");
            return false;
        }
        return false;
    }

    // ---------------------------------------------ACTIVITY----------------------------------------------------------
    
    /**
     * A method to get an activity object from the database by activityID
     * @param activityID The acitivtyID of the activity needed
     * @return The activity as an object
     * @throws Exception If the SQL fails 
     */
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

    /**
     * Get a list of all activities in the database
     * @return A list of possible activities
     * @throws Exception 
     */
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
    
    /**
     * Insert an exercise into the database
     * @param userID The userID of the user who has carried out the exercise
     * @param activityID The activityID
     * @param date The date of the exercise
     * @param minutes The duration
     * @param distance The distance
     * @return The exercise object
     * @throws Exception If the SQL insert fails
     */
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

    /**
     * Get an exercise object from the database
     * @param exerciseID The exerciseID in the database
     * @return The exercise object
     * @throws Exception If the SQL fails
     */
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

    /**
     * Gets the most recent exercise in the database
     * @param userID The userID of the user needing the most recent exercise
     * @return The object exercise
     */
    public Exercise getMaxExercise(int userID) {
        Exercise exercise = null;
        try {
            String sql = "SELECT * FROM exercise WHERE userID = ? "
                    + "AND distance = (SELECT MAX(distance) "
                    + "FROM exercise WHERE userID = ?)";

            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            st.setInt(2, userID);
            ResultSet result = st.executeQuery();

            while (result.next()) {
                exercise = new Exercise(result.getInt("exerciseID"), 
                        userID, getActivity(result.getInt("activityID")), 
                        result.getDate("date"), result.getInt("minutes"), 
                        result.getDouble("distance"));
            }
        } catch (Exception ex) {
            System.out.println("Failed to get exercise by userID");
        }
        return exercise;   
    }

    public boolean deleteExercise(int exerciseID){
        try{
            String sql = "DELETE FROM exercise WHERE exerciseID =?";
            PreparedStatement st = CON.prepareCall(sql);
            st.setInt(1, exerciseID);
            st.executeUpdate();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Failed to delete exercise");
            return false;
        }   
    }
        
    // ---------------------------------------------FOOD----------------------------------------------------------
    
    /**
     * Get a list of all possible food in database
     * @return List of food
     * @throws Exception If SQL fails
     */
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

    /**
     * Gets a specific food object from database
     * @param id The foodID
     * @return The food object
     * @throws Exception If the SQL fails
     */
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
     * @param foodID The foodID
     * @param userID The userID of user logged n
     * @param meal meal type
     * @param date date of food
     * @return The FoodLog object
     * @throws Exception If SQL fails
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
    
    /**
     * Insert a notification into the database
     * @param userID The userID attached to the notification
     * @param text The text attached to the notification
     * @throws Exception If the SQL fails
     */
    public void insertNotification(int userID, String text)
            throws Exception {

        try {
            String sql = "INSERT INTO `notification` "
                    + "(userID,Text) "
                    + "VALUES(?,?)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, userID);
            st.setString(2, text);
            st.executeUpdate();
            User user = getUser(userID);
            EmailSetup notif = new EmailSetup(user.getEmail(), 
                    "<b>New notification: </b><br>" + text,"New Notification",
                    user.getFirstname(), "New Notification");
            notif.setUpEmail();
            notif.sendEmail();
        } catch (Exception ex) {
            System.out.println("Failed to insert notification");
        }

    }

    /**
     * Gets a list of notifications from the database
     * @param userID The userID of the who needs all notifications 
     * @return A list of Notification objects
     * @throws SQLException If the SQL fails
     */
    public ArrayList<Notification> getNotifications(int userID) 
            throws SQLException {
        ArrayList<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notification WHERE userID =?";
        PreparedStatement st = this.CON.prepareStatement(sql);
        st.setInt(1, userID);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            list.add(new Notification(rs.getInt("id"), rs.getString("Text")));
        }
        return list;
    }


    /**
     * Deletes a specific notification from the database
     * @param notificationID The ID
     */
    public void deleteNotification(int notificationID) {

        try {
            String sql = "DELETE FROM notification WHERE id = ?";
            PreparedStatement st = this.CON.prepareStatement(sql);
            st.setInt(1, notificationID);
            st.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("ERROR DELETING NOTIF");
        }
    }

    // ---------------------------------------------GOAL----------------------------------------------------------
    public boolean insertGoal(int userID, double start, double target, String type) {
        try {
            String sql = "INSERT INTO goal (userID, start, target, type) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, userID);
            st.setDouble(2, start);
            st.setDouble(3, target);
            st.setString(4, type);
            st.executeUpdate();
            return true;
        }catch(Exception ex){
            System.out.println("Failed to insert goal");
            ex.printStackTrace();
            return false;
        }       
    }

    public Goal getGoal(int userID, String type) throws SQLException, Exception {
        try {
            String sql = "SELECT * FROM goal WHERE userID =? AND type =?";
            PreparedStatement st = this.CON.prepareStatement(sql);
            st.setInt(1, userID);
            st.setString(2, type);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                return new Goal(rs.getInt("goalID"),rs.getDouble("start"), rs.getDouble("target"),
                        rs.getInt("userID"), rs.getString("type"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Goal(userID, type);

    }
    
    public boolean deleteGoal(int goalID){
        try {
            String sql = "DELETE FROM goal WHERE goalID = ?";
            PreparedStatement st = this.CON.prepareStatement(sql);
            st.setInt(1, goalID);
            st.executeUpdate();
            System.out.println("GOAL ID: " +  goalID);

        } catch (SQLException ex) {
            System.out.println("ERROR DELETING goal");
        }
        return true;
    }

    // ---------------------------------------------MESSAGE----------------------------------------------------------
    //gets conversation between two users
    /**
     * Gets messages between to users
     * @param senderID The userID of the sender
     * @param recipientID The userID of the receiver
     * @return A list of messages
     * @throws SQLException If the SQL fails
     */
    public ArrayList<Message> getMessages(int senderID, int recipientID) 
            throws SQLException {
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

    /**
     * Gets a list of senderIDs to check unread messages for a user
     * @param userID The user to check for unread
     * @return The list of senderIDs
     * @throws SQLException
     * @throws Exception 
     */
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

    /**
     * Sends a message by inserting into database
     * @param message The message as text
     * @param senderID The senderID
     * @param recipientID The receiverID
     * @throws SQLException If the SQL fails
     */
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

    /**
     * Sets a message to seen in the database
     * @param userID The userID of the user logged in
     * @param senderID The senderID of the message
     * @throws SQLException If the SQL fails
     */
    public void setSeen(int userID, int senderID) throws SQLException {
        try {
            String sql = "UPDATE message SET seen = 1 WHERE senderID = ? AND recipientID = ?";
            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, senderID);
            st.setInt(2, userID);

            st.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Failed to check if message has been seen");
        }
    }
}
