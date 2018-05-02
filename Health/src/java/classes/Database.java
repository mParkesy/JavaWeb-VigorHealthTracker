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

    public static String passwordDigest(String p) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(p.getBytes(), 0, p.length());
        return new BigInteger(1, digest.digest()).toString(16);
    }

    // ---------------------------------------------USER----------------------------------------------------------
    public User getUser(int id) throws SQLException, Exception {
        String sql = "SELECT *, COUNT(*) FROM user WHERE userID =?";
        PreparedStatement st = this.CON.prepareStatement(sql);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            if (rs.getInt("COUNT(*)") == 0) {
                throw new Exception("No user exists with that User ID");
            }

            return new User(id, rs.getString("username"), rs.getString("firstname"),
                    rs.getString("lastname"), rs.getString("gender"), rs.getString("postcode"),
                    rs.getString("nationality"), rs.getString("email"), rs.getDouble("height"),
                    (Date) rs.getDate("dob"), rs.getDouble("exerciseLevel")
            );

        }
        return null;
    }

    public User getUser(String username) throws SQLException, Exception {
        String sql = "SELECT *, COUNT(*) FROM user WHERE username =?";
        PreparedStatement st = CON.prepareStatement(sql);
        st.setString(1, username);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            if (rs.getInt("COUNT(*)") == 0) {
                throw new Exception("No user exists with that User ID");
            }

            return new User(rs.getInt("userID"), username, rs.getString("firstname"),
                    rs.getString("lastname"), rs.getString("gender"), rs.getString("postcode"),
                    rs.getString("nationality"), rs.getString("email"), rs.getDouble("height"),
                    (Date) rs.getDate("dob"), rs.getDouble("exerciseLevel")
            );
        }
        return null;
    }

    public User insertUser(int id, String username, String password, String firstname,
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
            PreparedStatement st = Database.getConnection().prepareStatement(sql,
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

            // get the auto incremented id created by the database, construct user
            try (ResultSet key = st.getGeneratedKeys()) {
                if (key.next()) {
                    user = new User(key.getInt(1), username, firstname, lastname,
                            gender, postcode, nationality, email, height, dob, exercise);
                } else {
                    throw new SQLException("No ID found, User not created");
                }
            }
            Date now = new Date();
            insertWeight(id, weight, now);

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
            String sql = "SELECT * FROM weight WHERE userID = ? ORDER BY date DESC";
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

                sleep = new Sleep(sleepID, userID, bedTime, wakeTime, sleepGrade);

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

                exercise = new Exercise(exerciseID, userID, getActivity(result.getInt("activityID")), date, minutes, distance);
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
    public Sleep insertSleep(int userID, Date bedTime, Date wakeTime, int sleepGrade)
            throws Exception {
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
                    sleep = new Sleep(key.getInt(1), userID, bedTime, wakeTime, sleepGrade);
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
            String sql = "SELECT * FROM weight WHERE date = (SELECT MAX(date) FROM weight where userID = ?)";
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
     * @param userID
     * @param bedTime
     * @param wakeTime
     * @param sleepGrade
     * @throws Exception 
     */    
    public Weight insertWeight(int userID, double weight, Date date) throws Exception {
        Weight log = null;
        try{
             String sql = "INSERT INTO `weight` (userID, weight, date)"
                + "VALUES (?,?,?)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, userID);
            st.setDouble(2, weight);
            java.sql.Date newDate = new java.sql.Date(date.getTime());
            st.setDate(3, newDate);

            st.executeUpdate();

            try(ResultSet key = st.getGeneratedKeys()){
                if(key.next()){
                    log = new Weight(key.getInt(1),userID,weight,date);
                }else {
                    throw new SQLException("No ID found, Weight data not saved");
                }
            }
        }
        catch(Exception ex){
            System.out.println("Failed to insert weight log");
        }
       return log;
    }
    
    // ---------------------------------------------Group----------------------------------------------------------
    
     public Group insertGroup(int userID, String name) throws SQLException, Exception{
         Group group = null;
         int groupID = 0;
        try{
            String sql = "INSERT INTO group (userID, name)"
                + "VALUES (?,?)";
            PreparedStatement st = CON.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, userID);
            st.setString(2, name);
            st.executeUpdate();

            try(ResultSet key = st.getGeneratedKeys()){
                if(key.next()){
                    group = new Group(key.getInt(1),name,userID);
                    sql = "INSERT INTO groupmembers (userID, groupID, invited, joined)"
                    + "VALUES (?,?,?,?)";
                    st = CON.prepareStatement(sql);
                    st.setInt(1, userID);
                    st.setInt(2, key.getInt(1));
                    st.setBoolean(3, true);
                    st.setBoolean(4, true);
                    st.executeUpdate();
                }
                else{
                    throw new SQLException("No ID found, group data not saved");
                }
            }
            
        }
        catch(Exception ex){
            System.out.println("Failed to insert group");
        }
        return group;
     }
      
     // ---------------------------------------------Group----------------------------------------------------------
     
     public Activity getActivity(int activityID) throws Exception{
         Activity activity = null;
        try {
            String sql = "SELECT * FROM activity WHERE activityID = ?";

            PreparedStatement st = CON.prepareStatement(sql);
            st.setInt(1, activityID);
            ResultSet result = st.executeQuery();

            while(result.next()){
                activity = new Activity(activityID,result.getString("activity"),result.getDouble("MET"));
            }    
        } catch (Exception ex) {
            System.out.println("Failed to get activity");
        }   
        return activity;
    }  
     
    public ArrayList<Activity> allActivity() throws Exception{
        ArrayList<Activity> activityList = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM activity";
            PreparedStatement st = CON.prepareStatement(sql);

            ResultSet result = st.executeQuery();
            Activity activity;

            while(result.next()){
                int activityID = result.getInt("activityID");
                String name = result.getString("activity");
                double MET = result.getDouble("MET");
                activity = new Activity(activityID, name, MET);

                activityList.add(activity);
            }
        } catch(SQLException ex){
            System.out.println("Failed to get activity list");
        }
        return activityList;
    }

    // ---------------------------------------------Exercise----------------------------------------------------------

     
    public Exercise insertExercise(int userID, int activityID, Date date, int minutes, double distance) throws Exception {
        Exercise exercise = null;
        try{
            String sql = "INSERT INTO `exercise` (activityID, userID, date, minutes, distance)"
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

            try(ResultSet key = st.getGeneratedKeys()){
                if(key.next()){
                    exercise = new Exercise(key.getInt(1),userID,getActivity(activityID),date,minutes,distance);

                }else {
                    throw new SQLException("No ID found, Exercise data not saved");
                }
            }
        }
        catch(Exception ex){
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

            while(result.next()){
                int id = result.getInt("id");
                String name = result.getString("Name");
                double protein = result.getDouble("Protein");
                double fat = result.getDouble("Fat");
                double carbs = result.getDouble("Carbs");
                double energy = result.getDouble("Energy");
                double sugar = result.getDouble("Sugar");
                
                food = new Food(id,name,protein,fat,carbs,energy,sugar);

                foodList.add(food);
            }
        } catch(SQLException ex){
            System.out.println(ex.toString());
        }
        
        return foodList;
    }
    
     public Food getFood(int id) throws Exception {
        Food food = null;
        try {
            String sql = "SELECT * FROM food WHERE id =?";
            PreparedStatement st = Database.getConnection().prepareStatement(sql);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();

            while(result.next()){
                food = new Food(id,result.getString("name"),result.getDouble("protein"),
                result.getDouble("fat"),result.getDouble("carbs"),result.getDouble("energy"),
                result.getDouble("sugar"));
            }
        }
        catch(Exception ex){
            System.out.println("Failed to get food data");
        }
        return food;
    }
     
     
     // ---------------------------------------------FoodLog----------------------------------------------------------
     
      /**
     * Construct a FoodLog instance and add it to the database
     * @param foodID
     * @param userID
     * @param meal
     * @param date
     * @throws Exception 
     */
    public FoodLog insertFoodLog(int foodID, int userID, String meal, Date date) 
            throws Exception{
        FoodLog log = null;
        try{
            String sql = "INSERT INTO `foodLog` "
                + "(foodID, userID, meal, date) "
                + "VALUES (?,?,?,?)";
            PreparedStatement st = Database.getConnection().prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, foodID);
            st.setInt(2, userID);
            st.setString(3, meal);
            java.sql.Date newDate = new java.sql.Date(date.getTime());
            st.setDate(4, newDate);


            st.executeUpdate();

            try(ResultSet key = st.getGeneratedKeys()){
                if(key.next()){
                    log = new FoodLog(key.getInt(1),getFood(foodID),userID,meal,date);
                }else{
                    throw new SQLException("No ID found, Food Log data not saved");
                }
            }
        }
        catch(Exception ex){
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
         System.out.println("BOOBS");
        while (rs.next()) {
            list.add(new Notification(rs.getInt("id"), rs.getString("Text")));
        }
        return list;
    }
     
     public void deleteNotification(int id){
         
        Notification n = null;
        try{
            String sql = "DELETE FROM notification WHERE id = ?";
            PreparedStatement st = this.CON.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();

        }
        catch(SQLException ex){
               System.out.println("ERROR DELETING NOTIF"); 
        } 
        
     }

}
