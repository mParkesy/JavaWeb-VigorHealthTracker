/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.util.ArrayList;
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
public class FoodTest {
    private static Database db = new Database();
    Connection conn;
    Food instance;

    public FoodTest() throws Exception {
        this.conn = Database.getConnection();
        instance = db.getFood(1);
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
     * Test of getID method, of class Food.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        int expResult = 1;
        int result = instance.getID();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Food.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        int id = 2;
        instance.setId(id);
        assertEquals(id, instance.getID());
    }

    /**
     * Test of getName method, of class Food.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        String expResult = "Ackee, canned, drained";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class Food.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "Hello";
        instance.setName(name);
        assertEquals(name, instance.getName());
    }

    /**
     * Test of getProtein method, of class Food.
     */
    @Test
    public void testGetProtein() {
        System.out.println("getProtein");
        double expResult = 3.0;
        double result = instance.getProtein();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setProtein method, of class Food.
     */
    @Test
    public void testSetProtein() {
        System.out.println("setProtein");
        double protein = 4.0;
        instance.setProtein(protein);
        assertEquals(protein, instance.getProtein(), 0.0);
    }

    /**
     * Test of getFat method, of class Food.
     */
    @Test
    public void testGetFat() {
        System.out.println("getFat");
        double expResult = 15.0;
        double result = instance.getFat();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setFat method, of class Food.
     */
    @Test
    public void testSetFat() {
        System.out.println("setFat");
        double fat = 10.0;
        instance.setFat(fat);
        assertEquals(fat, instance.getFat(), 0.0);
    }

    /**
     * Test of getCarbs method, of class Food.
     */
    @Test
    public void testGetCarbs() {
        System.out.println("getCarbs");
        double expResult = 1.0;
        double result = instance.getCarbs();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setCarbs method, of class Food.
     */
    @Test
    public void testSetCarbs() {
        System.out.println("setCarbs");
        double carbs = 2.0;
        instance.setCarbs(carbs);
        assertEquals(carbs, instance.getCarbs(), 0.0);
    }

    /**
     * Test of getEnergy method, of class Food.
     */
    @Test
    public void testGetEnergy() {
        System.out.println("getEnergy");
        double expResult = 151.0;
        double result = instance.getEnergy();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of setEnergy method, of class Food.
     */
    @Test
    public void testSetEnergy() {
        System.out.println("setEnergy");
        double energy = 5.0;
        instance.setEnergy(energy);
        assertEquals(energy, instance.getEnergy(), 0.0);
    }

    /**
     * Test of getSugar method, of class Food.
     */
    @Test
    public void testGetSugar() {
        System.out.println("getSugar");
        double expResult = 1.0;
        double result = instance.getSugar();
        assertEquals(expResult, result, 0.0);
    }

    
}
