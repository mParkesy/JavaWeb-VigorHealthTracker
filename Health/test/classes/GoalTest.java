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
public class GoalTest {
    private static Database db = new Database();
    Connection conn;
    Goal instance;
    
    public GoalTest() throws Exception {
        this.conn = Database.getConnection();
        instance = db.getGoal(1, "weight");
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
     * Test of getUser method, of class Goal.
     */
    @Test
    public void testGetUser() throws SQLException {
        System.out.println("getUser");
        int expResult = db.getUser(1).getID();
        int result = instance.getUser().getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGoalID method, of class Goal.
     */
    @Test
    public void testSetGoalID() {
        System.out.println("setGoalID");
        int goalID = 1;
        instance.setGoalID(goalID);
        assertEquals(goalID, instance.getGoalID());
    }

    /**
     * Test of getGoalID method, of class Goal.
     */
    @Test
    public void testGetGoalID() {
        System.out.println("getGoalID");
        int expResult = 0;
        int result = instance.getGoalID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTarget method, of class Goal.
     */
    @Test
    public void testGetTarget() {
        System.out.println("getTarget");
        double expResult = 0.0;
        double result = instance.getTarget();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setTarget method, of class Goal.
     */
    @Test
    public void testSetTarget() {
        System.out.println("setTarget");
        double target = 60.0;
        instance.setTarget(target);
        assertEquals(target, instance.getTarget(), 0.0);
    }

    /**
     * Test of getType method, of class Goal.
     */
    @Test
    public void testGetType() {
        System.out.println("getType");
        String expResult = "weight";
        String result = instance.getType();
        assertEquals(expResult, result);
    }

    /**
     * Test of setType method, of class Goal.
     */
    @Test
    public void testSetType() {
        System.out.println("setType");
        String type = "distance";
        instance.setType(type);
        assertEquals(type, instance.getType());
    }
    
}
