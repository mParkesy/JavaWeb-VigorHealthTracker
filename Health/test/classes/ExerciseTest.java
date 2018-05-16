/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.SQLException;
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
public class ExerciseTest {
    private static Database db = new Database();
    Connection conn;
    Exercise instance;
    public ExerciseTest() throws Exception {
        this.conn = Database.getConnection();
        instance = db.getExercise(6);
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

    /**
     * Test of getExerciseID method, of class Exercise.
     */
    @Test
    public void testGetExerciseID() {
        System.out.println("getExerciseID");
        int expResult = 6;
        int result = instance.getExerciseID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDate method, of class Exercise.
     */
    @Test
    public void testGetDate() {
        System.out.println("getDate");
        String expResult = "14/05/2018";
        String result = instance.getDate();
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getActivity method, of class Exercise.
     */
    @Test
    public void testGetActivity() throws Exception {
        System.out.println("getActivity");
        int expResult = db.getActivity(1).getActivityID();
        int result = instance.getActivity().getActivityID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getActivityName method, of class Exercise.
     */
    @Test
    public void testGetActivityName() {
        System.out.println("getActivityName");
        String expResult = "Running";
        String result = instance.getActivityName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUser method, of class Exercise.
     * @throws java.sql.SQLException
     */
    @Test
    public void testGetUser() throws SQLException {
        System.out.println("getUser");
        int expResult = db.getUser(1).getID();
        int result = instance.getUser().getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class Exercise.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        double expResult = 10.0;
        double result = instance.getDistance();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getMinutes method, of class Exercise.
     */
    @Test
    public void testGetMinutes() {
        System.out.println("getMinutes");
        int expResult = 45;
        int result = instance.getMinutes();
        assertEquals(expResult, result);
    }

    /**
     * Test of getHours method, of class Exercise.
     */
    @Test
    public void testGetHours() {
        System.out.println("getHours");
        double expResult = 0.75;
        double result = instance.getHours();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getCaloriesBurnt method, of class Exercise.
     */
    @Test
    public void testGetCaloriesBurnt() throws Exception {
        System.out.println("getCaloriesBurnt");
        int expResult = 677;
        int result = instance.getCaloriesBurnt();
        assertEquals(expResult, result);
    }
    
}
