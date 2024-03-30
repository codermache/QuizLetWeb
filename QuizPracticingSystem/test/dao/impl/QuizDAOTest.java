/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Quiz;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuizDAOTest {

    public QuizDAOTest() {
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
     * Test of getAllQuiz method, of class QuizDAOImpl.
     */
    @Test
    public void testGetAllQuiz() throws Exception {
        System.out.println("getAllQuiz");
        QuizDAOImpl instance = new QuizDAOImpl();
        int expResult = 0;
        ArrayList<Quiz> result = instance.getAllQuiz();
        assertTrue(expResult < result.size());
    }

    /**
     * Test of getQuizById method, of class QuizDAOImpl.
     */
    @Test
    public void testGetQuizById() throws Exception {
        System.out.println("getQuizById");
        int quizId = 1;
        QuizDAOImpl instance = new QuizDAOImpl();
        Quiz expResult = null;
        Quiz result = instance.getQuizById(quizId);
        assertEquals(2, result.getTestTypeId());

    }

    /**
     * Test of getQuizByQuizTakeId method, of class QuizDAOImpl.
     */
    @Test
    public void testGetQuizByQuizTakeId() throws Exception {
        System.out.println("getQuizByQuizTakeId");
        int quizTakeId = 1;
        QuizDAOImpl instance = new QuizDAOImpl();
        Quiz result = instance.getQuizByQuizTakeId(quizTakeId);
        assertEquals(900, result.getQuizDuration());
    }

    /**
     * Test of getQuizBySubject method, of class QuizDAOImpl.
     */
    @Test
    public void testGetQuizBySubject() throws Exception {
        System.out.println("getQuizBySubject");
        int subjectId = 0;
        QuizDAOImpl instance = new QuizDAOImpl();
        ArrayList<Quiz> expResult = null;
        ArrayList<Quiz> result = instance.getQuizBySubject(subjectId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getQuizByLesson method, of class QuizDAOImpl.
     */
    @Test
    public void testGetQuizByLesson() throws Exception {
        System.out.println("getQuizByLesson");
        int lessonId = 0;
        QuizDAOImpl instance = new QuizDAOImpl();
        ArrayList<Quiz> expResult = null;
        ArrayList<Quiz> result = instance.getQuizByLesson(lessonId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editQuiz method, of class QuizDAOImpl.
     */
    @Test
    public void testEditQuiz() throws Exception {
        System.out.println("editQuiz");
        int quizId = 1;
        QuizDAOImpl instance = new QuizDAOImpl();
        Quiz quiz = instance.getQuizById(quizId);
        quiz.setNumberQuestion(11);
        int expResult = 0;
        int result = instance.editQuiz(quizId, quiz);
        quiz.setNumberQuestion(10);
        instance.editQuiz(quizId, quiz);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of addQuiz method, of class QuizDAOImpl.
     */
    @Test
    public void testAddQuiz() throws Exception {
        System.out.println("addQuiz");
        QuizDAOImpl instance = new QuizDAOImpl();
        SubjectDAOImpl subjectDAO = new SubjectDAOImpl();
        Quiz quiz = new Quiz(0, null, subjectDAO.getSubjectbyId(2), "test", 2, null, 600, 10, 3, null, "hi", 10, 2, null, true);
        int expResult = 0;
        int result = instance.addQuiz(quiz);
        int quizId = instance.getQuizIdCreated(quiz);
        instance.deleteQuiz(quizId);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of deleteQuiz method, of class QuizDAOImpl.
     */
    @Test
    public void testDeleteQuiz() throws Exception {
        System.out.println("deleteQuiz");
        SubjectDAOImpl subjectDAO = new SubjectDAOImpl();
        Quiz quiz = new Quiz(0, null, subjectDAO.getSubjectbyId(2), "test", 2, null, 600, 10, 3, null, "hi", 10, 2, null, true);
        QuizDAOImpl instance = new QuizDAOImpl();
        instance.addQuiz(quiz);
        int quizId = instance.getQuizIdCreated(quiz);
        int expResult = 0;
        int result = instance.deleteQuiz(quizId);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of getAllSimulationQuizByUser method, of class QuizDAOImpl.
     */
    @Test
    public void testGetAllSimulationQuizByUser() throws Exception {
        System.out.println("getAllSimulationQuizByUser");
        QuizDAOImpl instance = new QuizDAOImpl();
        ArrayList<Quiz> result = instance.getAllSimulationQuizByUser(8, 0, "");
        assertEquals(2, result.size());
       
    }

    /**
     * Test of getQuizIdCreated method, of class QuizDAOImpl.
     */
    @Test
    public void testGetQuizIdCreated() throws Exception {
        System.out.println("getQuizIdCreated");
        SubjectDAOImpl subjectDAO = new SubjectDAOImpl();
        Quiz quiz = new Quiz(0, null, subjectDAO.getSubjectbyId(2), "test", 2, null, 600, 10, 3, null, "hi", 10, 2, null, true);
        QuizDAOImpl instance = new QuizDAOImpl();
        instance.addQuiz(quiz);
        int expResult = 0;
        int result = instance.getQuizIdCreated(quiz);
        instance.deleteQuiz(result);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of addQuizQuestion method, of class QuizDAOImpl.
     */
    @Test
    public void testAddQuizQuestion() throws Exception {
        System.out.println("addQuizQuestion");
        SubjectDAOImpl subjectDAO = new SubjectDAOImpl();
        Quiz quiz = new Quiz(0, null, subjectDAO.getSubjectbyId(2), "test", 2, null, 600, 10, 3, null, "hi", 10, 2, null, true);
        QuizDAOImpl instance = new QuizDAOImpl();
        instance.addQuiz(quiz);
        int expResult = 0;
        int quizId = instance.getQuizIdCreated(quiz);
        int result = instance.addQuizQuestion(quizId, 1);
        instance.removeQuizQuestion(quizId);
        instance.deleteQuiz(quizId);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of getFilteredQuiz method, of class QuizDAOImpl.
     */
    @Test
    public void testGetFilteredQuiz() throws Exception {
        System.out.println("getFilteredQuiz");
        int subjectId = 2;
        int quizTypeId = 0;
        QuizDAOImpl instance = new QuizDAOImpl();
        int expResult = 0;
        ArrayList<Quiz> result = instance.getFilteredQuiz(subjectId, quizTypeId);
        assertTrue(expResult < result.size());
    }

    /**
     * Test of getQuizByName method, of class QuizDAOImpl.
     */
    @Test
    public void testGetQuizByName() throws Exception {
        System.out.println("getQuizByName");
        String searchName = "Practice Quiz";
        QuizDAOImpl instance = new QuizDAOImpl();
        ArrayList<Quiz> result = instance.getQuizByName(searchName);
        assertTrue(result.size() > 0);
    }

    /**
     * Test of removeQuizQuestion method, of class QuizDAOImpl.
     */
    @Test
    public void testRemoveQuizQuestion() throws Exception {
        System.out.println("removeQuizQuestion");
        int quizId = 2;
        QuizDAOImpl instance = new QuizDAOImpl();
        int expResult = 0;
        int result = instance.removeQuizQuestion(quizId);
        for (int i = 1; i <= 10; i++) {
            instance.addQuizQuestion(quizId, i);
        }
        assertTrue(expResult != result);
    }

}
