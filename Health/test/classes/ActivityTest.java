/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

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
    
    public ActivityTest() {
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
    public void testGetActivityID() {

 
    }

    /**
     * Test of getActivity method, of class Activity.
     */
    @Test
    public void testGetActivity() {
        System.out.println("getActivity");
        Activity instance = null;
        String expResult = "";
        String result = instance.getActivity();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMET method, of class Activity.
     */
    @Test
    public void testGetMET() {
        System.out.println("getMET");
        Activity instance = null;
        double expResult = 0.0;
        double result = instance.getMET();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActivityID method, of class Activity.
     */
    @Test
    public void testSetActivityID() {
        System.out.println("setActivityID");
        int activityID = 0;
        Activity instance = null;
        instance.setActivityID(activityID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActivity method, of class Activity.
     */
    @Test
    public void testSetActivity() {
        System.out.println("setActivity");
        String activity = "";
        Activity instance = null;
        instance.setActivity(activity);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMET method, of class Activity.
     */
    @Test
    public void testSetMET() {
        System.out.println("setMET");
        double MET = 0.0;
        Activity instance = null;
        instance.setMET(MET);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
