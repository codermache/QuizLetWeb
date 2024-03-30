/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.PostCate;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class PostCateDAOTest {
    
    public PostCateDAOTest() {
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
     * Test of getAllPostCates method, of class PostCateDAO.
     */
    @Test
    public void testGetAllPostCates() throws Exception {
        System.out.println("getAllPostCates");
        PostCateDAOImpl instance = new PostCateDAOImpl();
        ArrayList<PostCate> expResult = null;
        ArrayList<PostCate> result = instance.getAllPostCates();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPostCateById method, of class PostCateDAO.
     */
    @Test
    public void testGetPostCateById() throws Exception {
        System.out.println("getPostCateById");
        int pcId = 1;
        PostCateDAOImpl instance = new PostCateDAOImpl();
        int expResult = 1;
        int result = instance.getPostCateById(pcId).getPostCateId();
        assertEquals(expResult,result);
       
    }
    @Test
    public void testGetBlogPostCateByBlogId() throws Exception {
        System.out.println("getAllPostCates");
        PostCateDAOImpl instance = new PostCateDAOImpl();
        ArrayList<PostCate> expResult = null;
        int result = instance.getBlogCateByBlogId(10);
        assertEquals(3, result);
    }

    /**
     * Test of updatePostCate method, of class PostCateDAO.
     */
    @Test
    public void testUpdatePostCate() throws Exception {
        System.out.println("updatePostCate");
        PostCate updatedPostCate = null;
        PostCateDAOImpl instance = new PostCateDAOImpl();
        int expResult = 0;
        int result = instance.updatePostCate(updatedPostCate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deletePostCate method, of class PostCateDAO.
     */
    @Test
    public void testDeletePostCate() throws Exception {
        System.out.println("deletePostCate");
        int pcId = 0;
        PostCateDAOImpl instance = new PostCateDAOImpl();
        int expResult = 0;
        int result = instance.deletePostCate(pcId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPostCate method, of class PostCateDAO.
     */
    @Test
    public void testAddPostCate() throws Exception {
        System.out.println("addPostCate");
        PostCate newPostCate = null;
        PostCateDAOImpl instance = new PostCateDAOImpl();
        int expResult = 0;
        int result = instance.addPostCate(newPostCate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
