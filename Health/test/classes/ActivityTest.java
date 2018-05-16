/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
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
public class ActivityTest {
    private static Database db = new Database();
    Connection conn;
    Activity instance;
    public ActivityTest() throws Exception {
        this.conn = Database.getConnection();
        instance = db.getActivity(1);
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
     * Test of getActivityID method, of class Activity.
     */
    @Test
    public void testGetActivityID() throws Exception {
        System.out.println("getActivityID");
        int expResult = 1;
        int result = instance.getActivityID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getActivity method, of class Activity.
     */
    @Test
    public void testGetActivity() {
        System.out.println("getActivity");
        String expResult = "Running";
        String result = instance.getActivity();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMET method, of class Activity.
     */
    @Test
    public void testGetMET() {
        System.out.println("getMET");
        double expResult = 12.9;
        double result = instance.getMET();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setActivityID method, of class Activity.
     */
    @Test
    public void testSetActivityID() {
        System.out.println("setActivityID");
        int activityID = 2;
        instance.setActivityID(activityID);
        assertEquals(activityID, instance.getActivityID());
    }

    /**
     * Test of setActivity method, of class Activity.
     */
    @Test
    public void testSetActivity() {
        System.out.println("setActivity");
        String activity = "Walking";
        instance.setActivity(activity);
        assertEquals(activity, instance.getActivity());
    }

    /**
     * Test of setMET method, of class Activity.
     */
    @Test
    public void testSetMET() {
        System.out.println("setMET");
        double MET = 10.0;
        instance.setMET(MET);
        assertEquals(MET, instance.getMET(), 0.0);
    }
    
}
