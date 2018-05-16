/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 100116544
 */
public class DatabaseIT {
    
    public DatabaseIT() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    public Date createDate(String s) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(s);
    }
    
    /**
     * Test of getConnection method, of class Database.
     */
    @Test
    public void testGetConnection() throws Exception {
        System.out.println("getConnection");
        final int expResult = 1;
        int result = 0;

        Connection conn = Database.getConnection();
        String sql = "SELECT id FROM test_table";
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        while(rs.next()){
            result = rs.getInt("id");
        }       
        assertEquals(expResult, result);
    }

    /**
     * Test of passwordDigest method, of class Database.
     */
    @Test
    public void testPasswordDigestToText() throws Exception {
        System.out.println("passwordDigest");
        String p = "1234";
        String expResult = "81dc9bdb52d04dc20036dbd8313ed055";
        String result = Database.passwordDigest(p);
        assertEquals(expResult, result);
    }

    /**
     * Test of updatePassword method, of class Database.
     */
//    @Test
//    public void testUpdatePassword() throws Exception {
//        System.out.println("updatePassword");
//        String newPassword = "123";
//        int userID = 1;
//        Database instance = new Database();
//        Database.getConnection();
//        boolean expResult = true;
//        boolean result = instance.updatePassword(newPassword, userID);
//        assertEquals(expResult, result);
//        String pass = instance.getPassword(1);
//        assertEquals(newPassword, pass);
//    }
//
//    /**
//     * Test of insertUser method, of class Database.
//     */
//    @Test
//    public void testInsertUser() throws Exception {
//        System.out.println("insertUser");
//        String username = "";
//        String password = "";
//        String firstname = "";
//        String lastname = "";
//        String gender = "";
//        Date dob = null;
//        String postcode = "";
//        String nationality = "";
//        String email = "";
//        double height = 0.0;
//        double weight = 0.0;
//        double exercise = 0.0;
//        String verification = "";
//        Database instance = new Database();
//        User expResult = null;
//        User result = instance.insertUser(username, password, firstname, lastname, gender, dob, postcode, nationality, email, height, weight, exercise, verification);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }


    /**
     * Test of allFoodLogs method, of class Database.
     */
//    @Test
//    public void testAllFoodLogs() throws Exception {
//        System.out.println("allFoodLogs");
//        int userID = 0;
//        Database instance = new Database();
//        ArrayList<FoodLog> expResult = null;
//        ArrayList<FoodLog> result = instance.allFoodLogs(userID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of allSleep method, of class Database.
     */
//    @Test
//    public void testAllSleep() throws Exception {
//        System.out.println("allSleep");
//        int userID = 0;
//        Database instance = new Database();
//        ArrayList<Sleep> expResult = null;
//        ArrayList<Sleep> result = instance.allSleep(userID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of allExercise method, of class Database.
     */
//    @Test
//    public void testAllExercise() throws Exception {
//        System.out.println("allExercise");
//        int userID = 0;
//        Database instance = new Database();
//        ArrayList<Exercise> expResult = null;
//        ArrayList<Exercise> result = instance.allExercise(userID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of insertSleep method, of class Database.
     */
//    @Test
//    public void testInsertSleep() throws Exception {
//        System.out.println("insertSleep");
//        int userID = 0;
//        DateTime bedTime = null;
//        DateTime wakeTime = null;
//        int sleepGrade = 0;
//        Database instance = new Database();
//        Sleep expResult = null;
//        Sleep result = instance.insertSleep(userID, bedTime, wakeTime, sleepGrade);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }



    /**
     * Test of insertWeight method, of class Database.
     */
//    @Test
//    public void testInsertWeight() throws Exception {
//        System.out.println("insertWeight");
//        int userID = 0;
//        double weight = 0.0;
//        Date date = null;
//        Database instance = new Database();
//        Weight expResult = null;
//        Weight result = instance.insertWeight(userID, weight, date);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of insertGroup method, of class Database.
     */
//    @Test
//    public void testInsertGroup() throws Exception {
//        System.out.println("insertGroup");
//        int userID = 0;
//        String name = "";
//        String description = "";
//        String image = "";
//        Database instance = new Database();
//        Group expResult = null;
//        Group result = instance.insertGroup(userID, name, description, image);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }



    /**
     * Test of insertMember method, of class Database.
     */
//    @Test
//    public void testInsertMember() {
//        System.out.println("insertMember");
//        int groupID = 0;
//        int userID = 0;
//        Database instance = new Database();
//        boolean expResult = false;
//        boolean result = instance.insertMember(groupID, userID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of isAdmin method, of class Database.
     */
    @Test
    public void testIsAdmin() throws Exception {
        System.out.println("isAdmin");
        int userID = 1;
        int groupID = 9;
        Database instance = new Database();
        Database.getConnection();
        boolean expResult = false;
        boolean result = instance.isAdmin(userID, groupID);
        assertEquals(expResult, result);
    }

    /**
     * Test of getGroupList method, of class Database.
     */
//    @Test
//    public void testGetGroupList() {
//        System.out.println("getGroupList");
//        int userID = 0;
//        int joined = 0;
//        Database instance = new Database();
//        ArrayList<Group> expResult = null;
//        ArrayList<Group> result = instance.getGroupList(userID, joined);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of acceptInvite method, of class Database.
     */
//    @Test
//    public void testAcceptInvite() {
//        System.out.println("acceptInvite");
//        int groupID = 0;
//        int userID = 0;
//        Database instance = new Database();
//        instance.acceptInvite(groupID, userID);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of sendInvite method, of class Database.
     */
//    @Test
//    public void testSendInvite() {
//        System.out.println("sendInvite");
//        int groupID = 0;
//        int userID = 0;
//        Database instance = new Database();
//        boolean expResult = false;
//        boolean result = instance.sendInvite(groupID, userID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }



    /**
     * Test of getGroupDistanceLeaderboard method, of class Database.
     */
//    @Test
//    public void testGetGroupDistanceLeaderboard() throws Exception {
//        System.out.println("getGroupDistanceLeaderboard");
//        int groupID = 0;
//        Database instance = new Database();
//        TreeMap expResult = null;
//        TreeMap result = instance.getGroupDistanceLeaderboard(groupID);
//        assertEquals(expResult, result);
//    }

    /**
     * Test of getMembers method, of class Database.
     */
//    @Test
//    public void testGetMembers() {
//        System.out.println("getMembers");
//        int groupID = 0;
//        Database instance = new Database();
//        ArrayList<User> expResult = null;
//        ArrayList<User> result = instance.getMembers(groupID);
//        assertEquals(expResult, result);
//    }


    /**
     * Test of allActivity method, of class Database.
     */
//    @Test
//    public void testAllActivity() throws Exception {
//        System.out.println("allActivity");
//        Database instance = new Database();
//        ArrayList<Activity> expResult = null;
//        ArrayList<Activity> result = instance.allActivity();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of insertExercise method, of class Database.
     */
//    @Test
//    public void testInsertExercise() throws Exception {
//        System.out.println("insertExercise");
//        int userID = 0;
//        int activityID = 0;
//        Date date = null;
//        int minutes = 0;
//        double distance = 0.0;
//        Database instance = new Database();
//        Exercise expResult = null;
//        Exercise result = instance.insertExercise(userID, activityID, date, minutes, distance);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }



    /**
     * Test of getMaxExercise method, of class Database.
     */
//    @Test
//    public void testGetMaxExercise() {
//        System.out.println("getMaxExercise");
//        int userID = 0;
//        Database instance = new Database();
//        Exercise expResult = null;
//        Exercise result = instance.getMaxExercise(userID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of allFood method, of class Database.
     */
//    @Test
//    public void testAllFood() throws Exception {
//        System.out.println("allFood");
//        Database instance = new Database();
//        ArrayList<Food> expResult = null;
//        ArrayList<Food> result = instance.allFood();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getFood method, of class Database.
     */
//    @Test
//    public void testGetFood() throws Exception {
//        System.out.println("getFood");
//        int id = 0;
//        Database instance = new Database();
//        Food expResult = null;
//        Food result = instance.getFood(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of insertFoodLog method, of class Database.
     */
//    @Test
//    public void testInsertFoodLog() throws Exception {
//        System.out.println("insertFoodLog");
//        int foodID = 0;
//        int userID = 0;
//        String meal = "";
//        Date date = null;
//        Database instance = new Database();
//        FoodLog expResult = null;
//        FoodLog result = instance.insertFoodLog(foodID, userID, meal, date);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of insertNotification method, of class Database.
     */
//    @Test
//    public void testInsertNotification() throws Exception {
//        System.out.println("insertNotification");
//        int userID = 0;
//        String text = "";
//        Database instance = new Database();
//        instance.insertNotification(userID, text);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getNotifications method, of class Database.
     */
//    @Test
//    public void testGetNotifications() throws Exception {
//        System.out.println("getNotifications");
//        int id = 0;
//        Database instance = new Database();
//        ArrayList<Notification> expResult = null;
//        ArrayList<Notification> result = instance.getNotifications(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of deleteNotification method, of class Database.
     */
//    @Test
//    public void testDeleteNotification() {
//        System.out.println("deleteNotification");
//        int id = 0;
//        Database instance = new Database();
//        instance.deleteNotification(id);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of insertGoal method, of class Database.
     */
//    @Test
//    public void testInsertGoal() {
//        System.out.println("insertGoal");
//        Database instance = new Database();
//        Goal expResult = null;
//        Goal result = instance.insertGoal();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getGoal method, of class Database.
     */
//    @Test
//    public void testGetGoal() throws Exception {
//        System.out.println("getGoal");
//        int userID = 0;
//        String type = "";
//        Database instance = new Database();
//        Goal expResult = null;
//        Goal result = instance.getGoal(userID, type);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getMessages method, of class Database.
     */
//    @Test
//    public void testGetMessages() throws Exception {
//        System.out.println("getMessages");
//        int senderID = 0;
//        int recipientID = 0;
//        Database instance = new Database();
//        ArrayList<Message> expResult = null;
//        ArrayList<Message> result = instance.getMessages(senderID, recipientID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getUnread method, of class Database.
     */
//    @Test
//    public void testGetUnread() throws Exception {
//        System.out.println("getUnread");
//        int userID = 0;
//        Database instance = new Database();
//        ArrayList<Integer> expResult = null;
//        ArrayList<Integer> result = instance.getUnread(userID);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of sendMessage method, of class Database.
     */
//    @Test
//    public void testSendMessage() throws Exception {
//        System.out.println("sendMessage");
//        String message = "";
//        int senderID = 0;
//        int recipientID = 0;
//        Database instance = new Database();
//        instance.sendMessage(message, senderID, recipientID);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of setSeen method, of class Database.
     */
//    @Test
//    public void testSetSeen() throws Exception {
//        System.out.println("setSeen");
//        int userID = 0;
//        int senderID = 0;
//        Database instance = new Database();
//        instance.setSeen(userID, senderID);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
