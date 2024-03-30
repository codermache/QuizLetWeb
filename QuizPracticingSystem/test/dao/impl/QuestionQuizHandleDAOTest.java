/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Answer;
import bean.QuestionQuizHandle;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuestionQuizHandleDAOTest {
    
    public QuestionQuizHandleDAOTest() {
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
     * Test of generateQuestionById method, of class QuestionQuizHandleDAO.
     */
    @Test
    public void testGenerateQuestionById() throws Exception {
        System.out.println("generateQuestionById");
        int questionId = 1;
        QuestionQuizHandleDAOImpl instance = new QuestionQuizHandleDAOImpl();
        QuestionQuizHandle expResult = null;
        QuestionQuizHandle result = instance.generateQuestionById(questionId);
        assertEquals("nihongo", result.getQuestion().getExplanation());       
    }

    /**
     * Test of markQuestion method, of class QuestionQuizHandleDAO.
     */
    @Test
    public void testMarkQuestion() throws Exception {
        System.out.println("markQuestion");
        QuestionQuizHandle question = new QuestionQuizHandle();
        QuestionQuizHandleDAOImpl instance = new QuestionQuizHandleDAOImpl();
        instance.markQuestion(question);
        assertEquals(true, question.isMarked());
    }

    /**
     * Test of getRightAnswer method, of class QuestionQuizHandleDAO.
     */
    @Test
    public void testGetRightAnswer() throws Exception{
        System.out.println("getRightAnswer");
        QuestionQuizHandleDAOImpl instance = new QuestionQuizHandleDAOImpl();
        QuestionQuizHandle questionQH  = instance.generateQuestionById(1);
        Answer expResult = null;
        String result = questionQH.getAnswerList().get(0).getAnswerContent();
        assertEquals("I", result);
    }
    /**
     * Test of getReviewQuestion method, of class QuestionQuizHandleDAO.
     */
    @Test
    public void testGetReviewQuestion() throws Exception{
        System.out.println("getReviewQuestion");
        int quizTakeId = 2;
        QuestionQuizHandleDAOImpl instance = new QuestionQuizHandleDAOImpl();
        ArrayList<QuestionQuizHandle> expResult = null;
        ArrayList<QuestionQuizHandle> result = instance.getReviewQuestion(quizTakeId);
        assertEquals(1, result.get(0).getQuestion().getQuestionId());
    }   
}
