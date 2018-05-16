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
public class GroupTest {
    private static Database db = new Database();
    Connection conn;
    Group instance;
    
    public GroupTest() throws Exception {
        this.conn = Database.getConnection();
        instance = db.getGroup(21);
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
     * Test of getGroupID method, of class Group.
     */
    @Test
    public void testGetGroupID() {
        System.out.println("getGroupID");
        int expResult = 21;
        int result = instance.getGroupID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGroupName method, of class Group.
     */
    @Test
    public void testGetGroupName() {
        System.out.println("getGroupName");
        String expResult = "UEA Runners";
        String result = instance.getGroupName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUser method, of class Group.
     */
    @Test
    public void testGetUser() throws SQLException {
        System.out.println("getUser");
        int expResult = db.getUser(1).getID();
        int result = instance.getUser().getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDescription method, of class Group.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        String expResult = "UEA Running Club";
        String result = instance.getDescription();
        assertEquals(expResult, result);
    }

    /**
     * Test of getImagePath method, of class Group.
     */
    @Test
    public void testGetImagePath() {
        System.out.println("getImagePath");
        String expResult = "img/leaves.jpg";
        String result = instance.getImagePath();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistanceGoal method, of class Group.
     */
    @Test
    public void testGetDistanceGoal() {
        System.out.println("getDistanceGoal");
        String expResult = "150";
        String result = instance.getDistanceGoal();
        assertEquals(expResult, result);
    }
    
}
