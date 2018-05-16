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
public class WeightTest {
    private static Database db = new Database();
    Connection conn;
    Weight instance;
    
    public WeightTest() throws Exception {
        this.conn = Database.getConnection();
        instance = db.currentWeight(1);
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
     * Test of getWeight method, of class Weight.
     */
    @Test
    public void testGetWeight() {
        System.out.println("getWeight");
        double expResult = 70.0;
        double result = instance.getWeight();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getDate method, of class Weight.
     */
    @Test
    public void testGetDate() {
        System.out.println("getDate");
        String expResult = "08/05/2018";
        String result = instance.getDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDay method, of class Weight.
     */
    @Test
    public void testGetDay() {
        System.out.println("getDay");
        int expResult = 8;
        int result = instance.getDay();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMonth method, of class Weight.
     */
    @Test
    public void testGetMonth() {
        System.out.println("getMonth");
        int expResult = 4;
        int result = instance.getMonth();
        assertEquals(expResult, result);
    }

    /**
     * Test of getYear method, of class Weight.
     */
    @Test
    public void testGetYear() {
        System.out.println("getYear");
        int expResult = 2018;
        int result = instance.getYear();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWeightID method, of class Weight.
     */
    @Test
    public void testGetWeightID() {
        System.out.println("getWeightID");
        int expResult = 5;
        int result = instance.getWeightID();
        assertEquals(expResult, result);
    }
}
