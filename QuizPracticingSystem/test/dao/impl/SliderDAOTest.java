/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Slider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class SliderDAOTest {
    
    public SliderDAOTest() {
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
     * Test of getSlider method, of class SliderDAOImpl.
     */
    @Test
    public void testGetSlider() throws Exception {
        System.out.println("getSlider");
        SliderDAOImpl instance = new SliderDAOImpl();
        int expResult = 0;
        int result = instance.getSlider().size();
        assertTrue(expResult < result);
     
    }
    
}
