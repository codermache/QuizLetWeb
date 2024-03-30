/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.ItemDashboard;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ViewDAOImplTest {

    public ViewDAOImplTest() {
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
     * Test of updateView method, of class ViewDAOImpl.
     */
    @Test
    public void testUpdateView() throws Exception {
        System.out.println("updateView");
        ViewDAOImpl instance = new ViewDAOImpl();
        int expResult = 0;
        int result = instance.updateView();
        assertTrue(1 == result);
    }

    /**
     * Test of checkCurrentDateViewExist method, of class ViewDAOImpl.
     */
    @Test
    public void testCheckCurrentDateViewExist() throws Exception {
        System.out.println("checkCurrentDateViewExist");
        ViewDAOImpl instance = new ViewDAOImpl();
        boolean expResult = false;
        boolean result = instance.checkCurrentDateViewExist();
        assertEquals(true, result);
    }

    /**
     * Test of getViewStatistics method, of class ViewDAOImpl.
     */
    @Test
    public void testGetViewStatistics() throws Exception {
        System.out.println("getViewStatistics");
        String from = "2018-1-1";
        String to = "2022-1-1";
        ViewDAOImpl instance = new ViewDAOImpl();
        ArrayList<ItemDashboard> expResult = null;
        ArrayList<ItemDashboard> result = instance.getViewStatistics(from, to);
        assertEquals(4, result.size());
    }

    /**
     * Test of convertJson method, of class ViewDAOImpl.
     */
    @Test
    public void testConvertJson() throws Exception {
        System.out.println("convertJson");
        ArrayList<ItemDashboard> viewList = null;
        ViewDAOImpl instance = new ViewDAOImpl();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.convertJson(instance.getViewStatistics("2018-1-1", "2022-1-1"));
        assertTrue(0 <= result.size());
    }

    /**
     * Test of getNameList method, of class ViewDAOImpl.
     */
    @Test
    public void testGetNameList() throws Exception {
        System.out.println("getNameList");
        ViewDAOImpl instance = new ViewDAOImpl();
        ArrayList<ItemDashboard> viewList = instance.getViewStatistics("2018-1-1", "2022-1-1");
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getNameList(viewList);
        assertTrue(0 <= result.size());
    }

}
