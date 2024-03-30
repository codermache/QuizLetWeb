/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.TestType;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTypeDAOTest {
    
    public TestTypeDAOTest() {
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
     * Test of getAllTestTypes method, of class TestTypeDAOImpl.
     */
    @Test
    public void testGetAllTestTypes() throws Exception {
        System.out.println("getAllTestTypes");
        TestTypeDAOImpl instance = new TestTypeDAOImpl();
        int expResult = 0;
        ArrayList<TestType> result = instance.getAllTestTypes();
        assertTrue(expResult <= result.size());
    }

    /**
     * Test of getTestTypeById method, of class TestTypeDAOImpl.
     */
    @Test
    public void testGetTestTypeById() throws Exception {
        System.out.println("getTestTypeById");
        int testTypeId = 1;
        TestTypeDAOImpl instance = new TestTypeDAOImpl();
        int expResult = 1;
        TestType result = instance.getTestTypeById(testTypeId);
        assertTrue(expResult == result.getTestTypeId());
    }

    /**
     * Test of updateTestType method, of class TestTypeDAOImpl.
     */
    @Test
    public void testUpdateTestType() throws Exception {
        System.out.println("updateTestType");
        TestType updatedTestType = null;
        TestTypeDAOImpl instance = new TestTypeDAOImpl();
        int expResult = 0;
        int result = instance.updateTestType(updatedTestType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteTestType method, of class TestTypeDAOImpl.
     */
    @Test
    public void testDeleteTestType() throws Exception {
        System.out.println("deleteTestType");
        int testTypeId = 0;
        TestTypeDAOImpl instance = new TestTypeDAOImpl();
        int expResult = 0;
        int result = instance.deleteTestType(testTypeId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTestType method, of class TestTypeDAOImpl.
     */
    @Test
    public void testAddTestType() throws Exception {
        System.out.println("addTestType");
        TestType newTestType = null;
        TestTypeDAOImpl instance = new TestTypeDAOImpl();
        int expResult = 0;
        int result = instance.addTestType(newTestType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
