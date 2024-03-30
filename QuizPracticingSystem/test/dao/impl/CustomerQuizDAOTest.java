/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.CustomerQuiz;
import bean.QuizQuizHandle;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class CustomerQuizDAOTest {

    public CustomerQuizDAOTest() {
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
     * Test of getAllCustomerQuiz method, of class CustomerQuizDAOImpl.
     */
    @Test
    public void testGetAllCustomerQuiz() throws Exception {
        System.out.println("getAllCustomerQuiz");
        CustomerQuizDAOImpl instance = new CustomerQuizDAOImpl();
        ArrayList<CustomerQuiz> expResult = null;
        ArrayList<CustomerQuiz> result = instance.getAllCustomerQuiz();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getQuizByUser method, of class CustomerQuizDAOImpl.
     */
    @Test
    public void testGetQuizByUser() throws Exception {
        System.out.println("getQuizByUser");
        int userId = 1;
        CustomerQuizDAOImpl instance = new CustomerQuizDAOImpl();
        int expResult = 0;
        ArrayList<CustomerQuiz> result = instance.getQuizByUser(userId);
        assertTrue(expResult < result.size());
    }

    /**
     * Test of getQuizById method, of class CustomerQuizDAOImpl.
     */
    @Test
    public void testGetQuizById() throws Exception {
        System.out.println("getQuizById");
        int quizId = 0;
        CustomerQuizDAOImpl instance = new CustomerQuizDAOImpl();
        CustomerQuiz expResult = null;
        CustomerQuiz result = instance.getQuizByTakeQuizId(quizId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editCustomerQuiz method, of class CustomerQuizDAOImpl.
     */
    @Test
    public void testEditCustomerQuiz() throws Exception {
        System.out.println("editCustomerQuiz");
        int customerQuizId = 0;
        CustomerQuiz customerQuiz = null;
        CustomerQuizDAOImpl instance = new CustomerQuizDAOImpl();
        int expResult = 0;
        int result = instance.editCustomerQuiz(customerQuizId, customerQuiz);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCustomerQuiz method, of class CustomerQuizDAOImpl.
     */
    @Test
    public void testAddCustomerQuiz() throws Exception {
        System.out.println("addCustomerQuiz");
        CustomerQuiz customerQuiz = null;
        CustomerQuizDAOImpl instance = new CustomerQuizDAOImpl();
        int expResult = 0;
        int result = instance.addCustomerQuiz(customerQuiz);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastAddedCustomerQuiz method, of class CustomerQuizDAOImpl.
     */
    @Test
    public void testGetLastAddedCustomerQuiz() throws Exception {
        System.out.println("getLastAddedCustomerQuiz");
        CustomerQuizDAOImpl instance = new CustomerQuizDAOImpl();
        CustomerQuiz expResult = null;
        CustomerQuiz result = instance.getLastAddedCustomerQuiz();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteCustomerQuiz method, of class CustomerQuizDAOImpl.
     */
    @Test
    public void testDeleteCustomerQuiz() throws Exception {
        System.out.println("deleteCustomerQuiz");
        int customerQuizId = 0;
        CustomerQuizDAOImpl instance = new CustomerQuizDAOImpl();
        int expResult = 0;
        int result = instance.deleteCustomerQuiz(customerQuizId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTakeAnswer method, of class CustomerQuizDAOImpl.
     */
    @Test
    public void testAddTakeAnswer() throws Exception {
        System.out.println("addTakeAnswer");
        QuizQuizHandle quiz = null;
        CustomerQuizDAOImpl instance = new CustomerQuizDAOImpl();
        int expResult = 0;
        int result = instance.addTakeAnswer(quiz);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getQuizByTakeQuizId method, of class CustomerQuizDAOImpl.
     */
    @Test
    public void testGetQuizByTakeQuizId() throws Exception {
        System.out.println("getQuizByTakeQuizId");
        int quizTakeId = 0;
        CustomerQuizDAOImpl instance = new CustomerQuizDAOImpl();
        CustomerQuiz expResult = null;
        CustomerQuiz result = instance.getQuizByTakeQuizId(quizTakeId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkTeakedQuiz method, of class CustomerQuizDAOImpl.
     */
    @Test
    public void testCheckTeakedQuiz() throws Exception {
        System.out.println("checkTeakedQuiz");
        int quizId = 4;
        CustomerQuizDAOImpl instance = new CustomerQuizDAOImpl();
        boolean expResult = true;
        boolean result = instance.checkTeakedQuiz(quizId);
        assertEquals(expResult, result);
    }
}
