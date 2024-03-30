/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Question;
import bean.QuizQuizHandle;
import bean.User;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class QuizQuizHandleDAOTest {
    
    public QuizQuizHandleDAOTest() {
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
     * Test of generateQuiz method, of class QuizQuizHandleDAO.
     */
    @Test
    public void testGenerateQuiz() throws Exception {
        System.out.println("generateQuiz");
        QuestionDAOImpl questionDAO = new QuestionDAOImpl();
        ArrayList<Question> questionList = questionDAO.getQuestionByQuizId(1);
        int quizId = 0;
        QuizQuizHandleDAOImpl instance = new QuizQuizHandleDAOImpl();
        QuizQuizHandle expResult = null;
        User user = new User();
        QuizQuizHandle result = instance.generateQuiz(questionList, 1,user);
        assertEquals(7200, result.getTime());
    }

    /**
     * Test of calculateScore method, of class QuizQuizHandleDAO.
     */
    @Test
    public void testCalculateScore() throws Exception {
        System.out.println("calculateScore");
        QuizQuizHandleDAOImpl instance = new QuizQuizHandleDAOImpl();
        QuizQuizHandle questionList = instance.getReviewQuiz(1);
        double expResult = 0.0;
        double result = instance.calculateScore(questionList);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getAnsweredQuestion method, of class QuizQuizHandleDAO.
     */
    @Test
    public void testGetAnsweredQuestion() throws Exception {
        System.out.println("getAnsweredQuestion");
        QuizQuizHandle quiz = null;
        QuizQuizHandleDAOImpl instance = new QuizQuizHandleDAOImpl();
        QuizQuizHandle questionList = instance.getReviewQuiz(1);
        int expResult = 0;
        int result = instance.getAnsweredQuestion(questionList);
        assertEquals(0, result);
    }

    /**
     * Test of getReviewQuiz method, of class QuizQuizHandleDAO.
     */
    @Test
    public void testGetReviewQuiz() throws Exception {
        System.out.println("getReviewQuiz");
        int quizTakeId = 1;
        QuizQuizHandleDAOImpl instance = new QuizQuizHandleDAOImpl();
        QuizQuizHandle expResult = null;
        QuizQuizHandle result = instance.getReviewQuiz(quizTakeId);
        assertEquals(10, result.getQuestions().size());
       
    }
    
}
