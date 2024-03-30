/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Subject;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class SubjectDAOTest {
    
    public SubjectDAOTest() {
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
     * Test of getAllSubjects method, of class SubjectDAOImpl.
     */
    @Test
    public void testGetAllSubjects() throws Exception {
        System.out.println("getAllSubjects");
        SubjectDAOImpl instance = new SubjectDAOImpl();
        int expResult = 4;
        int result = instance.getAllSubjects().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFeaturedSubjects method, of class SubjectDAOImpl.
     */
    @Test
    public void testGetFeaturedSubjects() throws Exception {
        System.out.println("getFeaturedSubjects");
        SubjectDAOImpl instance = new SubjectDAOImpl();
        int expResult = 3;
        int result = instance.getFeaturedSubjects().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSubjectsAssigned method, of class SubjectDAOImpl.
     */
    @Test
    public void testGetSubjectsAssigned() throws Exception {
        System.out.println("getSubjectsAssigned");
        int userId = 6;
        SubjectDAOImpl instance = new SubjectDAOImpl();
        int expResult = 3;
        int result = instance.getSubjectsAssigned(userId).size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSubjectbyId method, of class SubjectDAOImpl.
     */
    @Test
    public void testGetSubjectbyId() throws Exception {
        System.out.println("getSubjectbyId");
        int subjectId = 1;
        SubjectDAOImpl instance = new SubjectDAOImpl();
        int expResult = 1;
        int result = instance.getSubjectbyId(subjectId).getSubjectId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSubjectbyCateId method, of class SubjectDAOImpl.
     */
    @Test
    public void testGetSubjectbyCateId() throws Exception {
        System.out.println("getSubjectbyCateId");
        int cateId = 1;
        SubjectDAOImpl instance = new SubjectDAOImpl();
        int expResult = 1;
        ArrayList<Subject> result = instance.getSubjectbyCateId(cateId);
        assertEquals(expResult, result.size());
    }

    /**
     * Test of updateSubject method, of class SubjectDAOImpl.
     */
    @Test
    public void testUpdateSubject() throws Exception {
        System.out.println("updateSubject");
        int subjectId = 0;
        Subject subject = null;
        SubjectDAOImpl instance = new SubjectDAOImpl();
        int expResult = 0;
        int result = instance.updateSubject(subjectId, subject);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addSubject method, of class SubjectDAOImpl.
     */
    @Test
    public void testAddSubject() throws Exception {
        System.out.println("addSubject");
        Subject subject = null;
        SubjectDAOImpl instance = new SubjectDAOImpl();
        int expResult = 0;
        int result = instance.addSubject(subject);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteSubject method, of class SubjectDAOImpl.
     */
    @Test
    public void testDeleteSubject() throws Exception {
        System.out.println("deleteSubject");
        int subjectId = 0;
        SubjectDAOImpl instance = new SubjectDAOImpl();
        int expResult = 0;
        int result = instance.deleteSubject(subjectId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
