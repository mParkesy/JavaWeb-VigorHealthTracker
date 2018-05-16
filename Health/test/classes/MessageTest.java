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
public class MessageTest {
    private static Database db = new Database();
    Connection conn;
    Message instance;
    
    public MessageTest() throws Exception {
        this.conn = Database.getConnection();
        instance = new Message("", db.getUser(2), db.getUser(0));
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
     * Test of getMessage method, of class Message.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        String expResult = "";
        String result = instance.getMessage();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSender method, of class Message.
     */
    @Test
    public void testGetSender() throws SQLException {
        System.out.println("getSender");
        int expResult = db.getUser(2).getID();
        int result = instance.getSender().getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getRecipent method, of class Message.
     */
    @Test
    public void testGetRecipent() throws SQLException {
        System.out.println("getRecipent");
        int expResult = db.getUser(2).getID();
        int result = 2;
        assertEquals(expResult, result);
    }
    
}
