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
        EmailSetup instance = ;
        boolean expResult = false;
        boolean result = instance.sendEmail();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getToAddress method, of class EmailSetup.
     */
    @Test
    public void testGetToAddress() {
        System.out.println("getToAddress");
        EmailSetup instance = null;
        String expResult = "";
        String result = instance.getToAddress();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFromAddress method, of class EmailSetup.
     */
    @Test
    public void testGetFromAddress() {
        System.out.println("getFromAddress");
        EmailSetup instance = null;
        String expResult = "";
        String result = instance.getFromAddress();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPassword method, of class EmailSetup.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        EmailSetup instance = null;
        String expResult = "";
        String result = instance.getPassword();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessage method, of class EmailSetup.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        EmailSetup instance = null;
        String expResult = "";
        String result = instance.getMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSubject method, of class EmailSetup.
     */
    @Test
    public void testGetSubject() {
        System.out.println("getSubject");
        EmailSetup instance = null;
        String expResult = "";
        String result = instance.getSubject();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setToAddress method, of class EmailSetup.
     */
    @Test
    public void testSetToAddress() {
        System.out.println("setToAddress");
        String toAddress = "";
        EmailSetup instance = null;
        instance.setToAddress(toAddress);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMessage method, of class EmailSetup.
     */
    @Test
    public void testSetMessage() {
        System.out.println("setMessage");
        String message = "";
        EmailSetup instance = null;
        instance.setMessage(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSubject method, of class EmailSetup.
     */
    @Test
    public void testSetSubject() {
        System.out.println("setSubject");
        String subject = "";
        EmailSetup instance = null;
        instance.setSubject(subject);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUpVerifyEmail method, of class EmailSetup.
     */
    @Test
    public void testSetUpVerifyEmail() {
        System.out.println("setUpVerifyEmail");
        String link = "";
        String first = "";
        EmailSetup instance = null;
        String expResult = "";
        String result = instance.setUpVerifyEmail(link, first);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
