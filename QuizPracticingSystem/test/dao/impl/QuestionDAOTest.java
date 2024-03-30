/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Question;
import bean.QuestionManage;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuestionDAOTest {
    
    public QuestionDAOTest() {
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
     * Test of getAllQuestion method, of class QuestionDAO.
     */
    @Test
    public void testGetAllQuestion1() throws Exception {
        System.out.println("getAllQuestion");
        QuestionDAOImpl instance = new QuestionDAOImpl();
        int expResult = 27 ;
        ArrayList<Question> result = instance.getAllQuestion();
        assertEquals(expResult, result.size());
    }
    @Test
    public void testGetAllQuestion2() throws Exception {
        System.out.println("getAllQuestion");
        QuestionDAOImpl instance = new QuestionDAOImpl();
        int expResult = 0 ;
        ArrayList<Question> result = instance.getAllQuestion();
        assertEquals(expResult, result.size());
    }
    /**
     * Test of getQuestionById method, of class QuestionDAO.
     */
    @Test
    public void testGetQuestionById1() throws Exception {
        System.out.println("getQuestionById1");
        int questionId = 1;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        String expResult = "Watashi";
        String result = instance.getQuestionById(questionId).getContent();
        assertEquals(expResult, result);       
    }
    @Test
    public void testGetQuestionById2() throws Exception {
        System.out.println("getQuestionById2");
        int questionId = 2;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        String expResult = "Neko";
        String result = instance.getQuestionById(questionId).getContent();
        assertEquals(expResult, result);       
    }
    
    @Test
    public void testGetQuestionById3() throws Exception {
        System.out.println("getQuestionById3");
        int questionId = 3;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        String expResult = "Ohayo";
        String result = instance.getQuestionById(questionId).getContent();
        assertEquals(expResult, result);       
    }
    
    @Test
    public void testGetQuestionById4() throws Exception {
        System.out.println("getQuestionById4");
        int questionId = 4;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        String expResult = "Anata";
        String result = instance.getQuestionById(questionId).getContent();
        
        assertEquals(expResult, result);       
    }
    
    @Test
    public void testGetQuestionById5() throws Exception {
        System.out.println("getQuestionById5");
        int questionId = 5;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        String expResult = "Watashi";
        String result = instance.getQuestionById(questionId).getContent();
        assertEquals(expResult, result);       
    }
    
    @Test
    public void testGetQuestionById6() throws Exception {
        System.out.println("getQuestionById6");
        int questionId = -1;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        String expResult = "Iroshi";
        String result = instance.getQuestionById(questionId).getContent();
        assertEquals(expResult, result);       
    }
    
    @Test
    public void testGetQuestionById7() throws Exception {
        System.out.println("getQuestionById7");
        int questionId = 7;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        String expResult = "Mijikai";
        String result = instance.getQuestionById(questionId).getContent();
        assertEquals(expResult, result);       
    }
    
    @Test
    public void testGetQuestionById8() throws Exception {
        System.out.println("getQuestionById8");
        int questionId = 6;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        String expResult = "Arigatou gozaimasu";
        String result = instance.getQuestionById(questionId).getContent();
        assertEquals(expResult, result);       
    }
    /**
     * Test of getQuestionByQuizId method, of class QuestionDAO.
     */
    @Test
    public void testGetQuestionByQuizId() throws Exception {
        System.out.println("getQuestionByQuizId");
        int quizId = 1;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        ArrayList<Question> expResult = null;
        ArrayList<Question> result = instance.getQuestionByQuizId(quizId);
        assertEquals(10, result.size());
       
    }

    /**
     * Test of addQuestion method, of class QuestionDAO.
     */
    @Test
    public void testAddQuestion() throws Exception {
        System.out.println("addQuestion");
        Question newQuestion = new Question(1,2,2,5,"hon","","nihongo",true);
        QuestionDAOImpl instance = new QuestionDAOImpl();
        int expResult = 1;
        int result = instance.addQuestion(newQuestion);
        assertEquals(expResult, result);    
    }

    /**
     * Test of editQuestion method, of class QuestionDAO.
     */
    @Test
    public void testEditQuestion() throws Exception {
        System.out.println("editQuestion");
        int questionId = 1;
        
        QuestionDAOImpl instance = new QuestionDAOImpl();
        Question editedQuestion = instance.getQuestionById(questionId);
        int expResult = 0;
        int result = instance.editQuestion(questionId, editedQuestion);
        assertNotEquals(expResult, result);
        

    }

    /**
     * Test of deleteQuestion method, of class QuestionDAO.
     */
    @Test
    public void testDeleteQuestion() throws Exception {
        System.out.println("deleteQuestion");
        int questionId = 0;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        int expResult = 0;
        int result = instance.deleteQuestion(questionId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of importQuestion method, of class QuestionDAO.
     */
    @Test
    public void testImportQuestion() throws Exception {
        System.out.println("importQuestion");
        ArrayList<Question> questionList = null;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        int expResult = 0;
        int result = instance.importQuestion(questionList);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getQuestionByContent method, of class QuestionDAOImpl.
     */
    @Test
    public void testGetQuestionByContent() throws Exception {
        System.out.println("getQuestionByContent");
        String content = "OOPs";
        QuestionDAOImpl instance = new QuestionDAOImpl();
        ArrayList<QuestionManage> expResult = null;
        ArrayList<QuestionManage> result = instance.getQuestionByContent(content);
        assertTrue(result.size()>0);
       
    }

    /**
     * Test of getQuestionIdCreated method, of class QuestionDAOImpl.
     */
    @Test
    public void testGetQuestionIdCreated() throws Exception {
        System.out.println("getQuestionIdCreated");
        Question question = null;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        int expResult = 0;
        int result = instance.getQuestionIdCreated(question);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getQuestionManage method, of class QuestionDAOImpl.
     */
    @Test
    public void testGetQuestionManage() throws Exception {
        System.out.println("getQuestionManage");
        int subjectId = 1;
        int dimensionId = 1;
        int lessonId = 1;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        int expResult = 4;
        ArrayList<QuestionManage> result = instance.getQuestionManage(subjectId, dimensionId, lessonId);
        assertEquals(expResult, result.size());
        
    }

    /**
     * Test of getQuestionForCreateQuiz method, of class QuestionDAOImpl.
     */
    @Test
    public void testGetQuestionForCreateQuiz() throws Exception {
        System.out.println("getQuestionForCreateQuiz");
        int numberOfQuestion = 10;
        int subjectId = 2;
        int dimensionId = 2;
        QuestionDAOImpl instance = new QuestionDAOImpl();
        int expResult = 0;
        ArrayList<Question> result = instance.getQuestionForCreateQuiz(numberOfQuestion, subjectId, dimensionId);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of main method, of class QuestionDAO.
     */
    
}
