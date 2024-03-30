/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Dimension;
import bean.DimensionType;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DimensionDAOTest {
    
    public DimensionDAOTest() {
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
     * Test of getAllDimension method, of class DimensionDAOImpl.
     */
    @Test
    public void testGetAllDimension() throws Exception {
        System.out.println("getAllDimension");
        DimensionDAOImpl instance = new DimensionDAOImpl();
        ArrayList<Dimension> expResult = null;
        ArrayList<Dimension> result = instance.getAllDimension();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDimensionBySubject method, of class DimensionDAOImpl.
     */
    @Test
    public void testGetDimensionBySubject() throws Exception {
        System.out.println("getDimensionBySubject");
        int subjectId = 1;
        DimensionDAOImpl instance = new DimensionDAOImpl();
        int expResult = 0;
        int result = instance.getDimensionBySubject(subjectId).size();
        assertTrue(expResult < result);
    }

    /**
     * Test of getDimensionById method, of class DimensionDAOImpl.
     */
    @Test
    public void testGetDimensionById() throws Exception {
        System.out.println("getDimensionById");
        int dimensionId = 1;
        DimensionDAOImpl instance = new DimensionDAOImpl();
        Dimension expResult = null;
        Dimension result = instance.getDimensionById(dimensionId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDimension method, of class DimensionDAOImpl.
     */
    @Test
    public void testAddDimension() throws Exception {
        System.out.println("addDimension");
        Dimension dimension = null;
        DimensionDAOImpl instance = new DimensionDAOImpl();
        int expResult = 0;
        int result = instance.addDimension(dimension);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteDimension method, of class DimensionDAOImpl.
     */
    @Test
    public void testDeleteDimension() throws Exception {
        System.out.println("deleteDimension");
        int dimensionId = 0;
        DimensionDAOImpl instance = new DimensionDAOImpl();
        int expResult = 0;
        int result = instance.deleteDimension(dimensionId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editDimension method, of class DimensionDAOImpl.
     */
    @Test
    public void testEditDimension() throws Exception {
        System.out.println("editDimension");
        int dimensionId = 0;
        Dimension dimension = null;
        DimensionDAOImpl instance = new DimensionDAOImpl();
        int expResult = 0;
        int result = instance.editDimension(dimensionId, dimension);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSubjectDimensionType method, of class DimensionDAOImpl.
     */
    @Test
    public void testGetSubjectDimensionType() throws Exception {
        System.out.println("getSubjectDimensionType");
        int subjectId = 1;
        DimensionDAOImpl instance = new DimensionDAOImpl();
        int expResult = 0;
        ArrayList<DimensionType> result = instance.getSubjectDimensionType(subjectId);
        assertTrue(expResult <= result.size());
    }
    
}
