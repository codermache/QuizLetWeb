/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.QuizLevel;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class QuizLevelDAOTest {
    
    public QuizLevelDAOTest() {
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
     * Test of getAllQuizLevel method, of class QuizLevelDAO.
     */
    @Test
    public void testGetAllQuizLevel() throws Exception {
        System.out.println("getAllQuizLevel");
        QuizLevelDAOImpl instance = new QuizLevelDAOImpl();
        int expResult = 0;
        ArrayList<QuizLevel> result = instance.getAllQuizLevel();
        assertTrue(expResult <= result.size());
    }

    /**
     * Test of getQuizLevelById method, of class QuizLevelDAO.
     */
    @Test
    public void testGetQuizLevelById() throws Exception {
        System.out.println("getQuizLevelById");
        int quizLevelId = 1;
        QuizLevelDAOImpl instance = new QuizLevelDAOImpl();
        QuizLevel expResult = null;
        QuizLevel result = instance.getQuizLevelById(quizLevelId);
        assertEquals("Hard", result.getQuizLevelName());
      
    }

    /**
     * Test of editQuizLevel method, of class QuizLevelDAO.
     */
    @Test
    public void testEditQuizLevel() throws Exception {
        System.out.println("editQuizLevel");
        int quizLevelId = 0;
        QuizLevel quizLevel = null;
        QuizLevelDAOImpl instance = new QuizLevelDAOImpl();
        int expResult = 0;
        int result = instance.editQuizLevel(quizLevel);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addQuizLevel method, of class QuizLevelDAO.
     */
    @Test
    public void testAddQuizLevel() throws Exception {
        System.out.println("addQuizLevel");
        QuizLevel quizLevel = null;
        QuizLevelDAOImpl instance = new QuizLevelDAOImpl();
        int expResult = 0;
        int result = instance.addQuizLevel(quizLevel);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteQuizLevel method, of class QuizLevelDAO.
     */
    @Test
    public void testDeleteQuizLevel() throws Exception {
        System.out.println("deleteQuizLevel");
        int quizLevelId = 0;
        QuizLevelDAOImpl instance = new QuizLevelDAOImpl();
        int expResult = 0;
        int result = instance.deleteQuizLevel(quizLevelId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
