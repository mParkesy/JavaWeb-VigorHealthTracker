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
public class EmailSetupTest {
        EmailSetup instance;
    public EmailSetupTest() {
        instance = new EmailSetup("matt.parkes@outlook.com", 
                "Test message", "Test", "Matt", "Test");
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
     * Test of sendEmail method, of class EmailSetup.
     */
    @Test
    public void testSendEmail() throws Exception {
        System.out.println("sendEmail");
        boolean expResult = true;
        boolean result = instance.sendEmail();
        assertEquals(expResult, result);
    }

    /**
     * Test of getToAddress method, of class EmailSetup.
     */
    @Test
    public void testGetToAddress() {
        System.out.println("getToAddress");
        String expResult = "matt.parkes@outlook.com";
        String result = instance.getToAddress();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFromAddress method, of class EmailSetup.
     */
    @Test
    public void testGetFromAddress() {
        System.out.println("getFromAddress");
        String expResult = "vigorhealthtracker@outlook.com";
        String result = instance.getFromAddress();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPassword method, of class EmailSetup.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        String expResult = "Vigor123";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMessage method, of class EmailSetup.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        String expResult = "Test message";
        String result = instance.getMessage();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSubject method, of class EmailSetup.
     */
    @Test
    public void testGetSubject() {
        System.out.println("getSubject");
        String expResult = "Test";
        String result = instance.getSubject();
        assertEquals(expResult, result);
    }

    /**
     * Test of setToAddress method, of class EmailSetup.
     */
    @Test
    public void testSetToAddress() {
        System.out.println("setToAddress");
        String toAddress = "m.parkes@uea.ac.uk";
        instance.setToAddress(toAddress);
        assertEquals(toAddress, instance.getToAddress());
    }

    /**
     * Test of setMessage method, of class EmailSetup.
     */
    @Test
    public void testSetMessage() {
        System.out.println("setMessage");
        String message = "Hello";
        instance.setMessage(message);
        assertEquals(message, instance.getMessage());
    }

    /**
     * Test of setSubject method, of class EmailSetup.
     */
    @Test
    public void testSetSubject() {
        System.out.println("setSubject");
        String subject = "Hello";
        instance.setSubject(subject);
        assertEquals(subject, instance.getSubject());
    }
}
