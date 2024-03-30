/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.ItemDashboard;
import bean.Registration;
import bean.Subject;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RegistrationDAOTest {

    public RegistrationDAOTest() {
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
     * Test of getAllRegistration method, of class RegistrationDAOImpl.
     */
    @Test
    public void testGetAllRegistration() throws Exception {
        System.out.println("getAllRegistration");
        RegistrationDAOImpl instance = new RegistrationDAOImpl();
        ArrayList<Registration> result = instance.getAllRegistration();
        assertEquals(13, result.size());

    }

    /**
     * Test of getRegistrationById method, of class RegistrationDAOImpl.
     */
    @Test
    public void testGetRegistrationById() throws Exception {
        System.out.println("getRegistrationById");
        int registrationId = 1;
        RegistrationDAOImpl instance = new RegistrationDAOImpl();
        Registration result = instance.getRegistrationById(registrationId);
        assertEquals(2, result.getUserId());
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addRegistration method, of class RegistrationDAOImpl.
     */
    @Test
    public void testAddRegistration() throws Exception {
        System.out.println("addRegistration");
        Registration newRegistration = null;
        RegistrationDAOImpl instance = new RegistrationDAOImpl();
        int expResult = 0;
        int result = instance.addRegistration(newRegistration);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editRegistration method, of class RegistrationDAOImpl.
     */
    @Test
    public void testEditRegistration() throws Exception {
        System.out.println("editRegistration");
        int registrationId = 0;
        Registration editedRegistration = null;
        RegistrationDAOImpl instance = new RegistrationDAOImpl();
        int expResult = 0;
        int result = instance.editRegistration(registrationId, editedRegistration);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteRegistration method, of class RegistrationDAOImpl.
     */
    @Test
    public void testDeleteRegistration() throws Exception {
        System.out.println("deleteRegistration");
        int registrationId = 0;
        RegistrationDAOImpl instance = new RegistrationDAOImpl();
        int expResult = 0;
        int result = instance.deleteRegistration(registrationId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRegistedSubject method, of class RegistrationDAOImpl.
     */
    @Test
    public void testGetRegistedSubject() throws Exception {
        System.out.println("getRegistedSubject");
        int userId = 1;
        RegistrationDAOImpl instance = new RegistrationDAOImpl();
        int expResult = 0;
        ArrayList<Subject> result = instance.getRegistedSubject(userId);
        assertTrue(expResult <= result.size());
    }

    /**
     * Test of getRegistedSubjectbyUserId method, of class RegistrationDAOImpl.
     */
    @Test
    public void testGetRegistedSubjectbyUserId() throws Exception {
        System.out.println("getRegistedSubjectbyUserId");
        int userId = 8;
        RegistrationDAOImpl instance = new RegistrationDAOImpl();
        ArrayList<Subject> expResult = null;
        ArrayList<Subject> result = instance.getRegistedSubjectbyUserId(userId);
        assertEquals(11, result.size());
    }

    /**
     * Test of getSubjectStatistics method, of class RegistrationDAOImpl.
     */
    @Test
    public void testGetSubjectStatistics() throws Exception {
        System.out.println("getSubjectStatistics");
        RegistrationDAOImpl instance = new RegistrationDAOImpl();
        String from = "2018-1-1";
        String to = "2022-1-1";
        ArrayList<Subject> subjectList = instance.getRegistedSubjectbyUserId(8);
        String type = "revenue";
        ArrayList<ItemDashboard> result = instance.getSubjectStatistics(from, to, subjectList, type);
        assertEquals("Elementary Japanese 101", result.get(0).getName());
    }

    /**
     * Test of getRevenueStatistics method, of class RegistrationDAOImpl.
     */
    @Test
    public void testGetRevenueStatistics() throws Exception {
        System.out.println("getRevenueStatistics");
        String from = "2018-1-1";
        String to = "2022-1-1";
        RegistrationDAOImpl instance = new RegistrationDAOImpl();
        ArrayList<ItemDashboard> result = instance.getRevenueStatistics(from, to);
        assertEquals("", result.get(0).getName());
    }

    /**
     * Test of getRevenueStatisticsBySubjectCate method, of class
     * RegistrationDAOImpl.
     */
    @Test
    public void testGetRevenueStatisticsBySubjectCate() throws Exception {
        System.out.println("getRevenueStatisticsBySubjectCate");
        String from = "2018-1-1";
        String to = "2022-1-1";
        RegistrationDAOImpl instance = new RegistrationDAOImpl();
        ArrayList<ItemDashboard> result = instance.getRevenueStatisticsBySubjectCate(from, to);
        assertEquals("Japanese", result.get(0).getName());
    }

    /**
     * Test of convertJson method, of class RegistrationDAOImpl.
     */
    @Test
    public void testConvertJson() throws Exception {
        System.out.println("convertJson");
        RegistrationDAOImpl instance = new RegistrationDAOImpl();
        ArrayList<ItemDashboard> viewList = instance.getRevenueStatisticsBySubjectCate("2018-1-1", "2022-1-1");
        ArrayList<String> result = instance.convertJson(viewList);
        assertTrue(0 <= result.size());
    }

    /**
     * Test of getNameList method, of class RegistrationDAOImpl.
     */
    @Test
    public void testGetNameList() throws Exception {
        System.out.println("getNameList");
        RegistrationDAOImpl instance = new RegistrationDAOImpl();
        ArrayList<ItemDashboard> viewList = instance.getRevenueStatisticsBySubjectCate("2018-1-1", "2022-1-1");
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getNameList(viewList);
        assertTrue(0 <= result.size());
    }
}
