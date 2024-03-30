/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.DimensionType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DimensionTypeDAOTest {
    
    public DimensionTypeDAOTest() {
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
     * Test of getAllDimensionTypes method, of class DimensionTypeDAOImpl.
     */
    @Test
    public void testGetAllDimensionTypes() {
        try {
            System.out.println("getAllDimensionTypes");
            DimensionTypeDAOImpl instance = new DimensionTypeDAOImpl();
            int expResult = 0;
            ArrayList<DimensionType> result = instance.getAllDimensionTypes();
            assertTrue(expResult <= result.size());
        } catch (Exception ex) {
            Logger.getLogger(DimensionTypeDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of getDimensionTypeById method, of class DimensionTypeDAOImpl.
     */
    @Test
    public void testGetDimensionTypeById1() {
        try {
            System.out.println("getDimensionTypeById");
            int dimensionTypeId = 1;
            DimensionTypeDAOImpl instance = new DimensionTypeDAOImpl();
            DimensionType expResult = new DimensionType(1, "Domain", true);
            DimensionType result = instance.getDimensionTypeById(dimensionTypeId);
            assertEquals(expResult.getDimensionTypeId(), result.getDimensionTypeId());
        } catch (Exception ex) {
            Logger.getLogger(DimensionTypeDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of updateDimensionType method, of class DimensionTypeDAOImpl.
     */
    @Test
    public void testUpdateDimensionType() {
        try {
            System.out.println("updateDimensionType");
            DimensionType updatedDimensionType = null;
            DimensionTypeDAOImpl instance = new DimensionTypeDAOImpl();
            int expResult = 0;
            int result = instance.updateDimensionType(updatedDimensionType);
            assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (Exception ex) {
            Logger.getLogger(DimensionTypeDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of deteteDimensionTyoe method, of class DimensionTypeDAOImpl.
     */
    @Test
    public void testDeteteDimensionTyoe() {
        try {
            System.out.println("deteteDimensionTyoe");
            int dtId = 0;
            DimensionTypeDAOImpl instance = new DimensionTypeDAOImpl();
            int expResult = 0;
            int result = instance.deteteDimensionType(dtId);
            assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (Exception ex) {
            Logger.getLogger(DimensionTypeDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of addDimensionType method, of class DimensionTypeDAOImpl.
     */
    @Test
    public void testAddDimensionType() {
        try {
            System.out.println("addDimensionType");
            DimensionType newDimensionType = null;
            DimensionTypeDAOImpl instance = new DimensionTypeDAOImpl();
            int expResult = 0;
            int result = instance.addDimensionType(newDimensionType);
            assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            fail("The test case is a prototype.");
        } catch (Exception ex) {
            Logger.getLogger(DimensionTypeDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
