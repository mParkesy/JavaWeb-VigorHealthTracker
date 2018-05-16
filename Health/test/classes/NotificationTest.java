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
public class NotificationTest {
    private static Database db = new Database();
    Connection conn;
    Notification instance;
    
    public NotificationTest() throws Exception {
        this.conn = Database.getConnection();
        instance = new Notification(3, "Distance Goal achieved!");
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
     * Test of getId method, of class Notification.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        int expResult = 3;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDesc method, of class Notification.
     */
    @Test
    public void testGetDesc() {
        System.out.println("getDesc");
        String expResult = "Distance Goal achieved!";
        String result = instance.getDesc();
        assertEquals(expResult, result);
    }
    
}
