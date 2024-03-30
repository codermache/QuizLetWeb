/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.Blog;
import bean.PostCate;
import bean.User;
import java.sql.Date;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BlogDAOTest {
    
    public BlogDAOTest() {
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
     * Test of getAllBlog method, of class BlogDAO.
     */
    
    @Test
    public void testGetAllBlog1() throws Exception {
        
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 12;
        int result = instance.getAllBlog().size();
        if(expResult == result){
            System.out.println("get All Blog");
        }
        else System.out.println("Fail to get all blog");
        assertEquals(expResult,result);
    }
    
    @Test
    public void testGetAllBlog2() throws Exception {
        
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 0;
        int result = instance.getAllBlog().size();
        if(expResult == result){
            System.out.println("get All Blog");
        }
        else System.out.println("Fail to get all blog");
        assertEquals(expResult,result);
        
    }

    /**
     * Test of getBlogByCategory method, of class BlogDAO.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetBlogByCategory() throws Exception {
        System.out.println("getBlogByCategory");
        String[] postCateIdList = {"1","2"};
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 0;
        int result = instance.getBlogByCategory(postCateIdList).size();
        assertEquals(expResult, result);
    }

    /**
     * Test of getBlogByUser method, of class BlogDAO.
     */
    @Test
    public void testGetBlogByUser1() throws Exception {
        System.out.println("getBlogByUser");
        int userId = 2;
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 4;
        int result = instance.getBlogByUser(userId).size();
        if(expResult == result){
            System.out.println("get Blog By User Success");
        }
        else System.out.println("Fail to get Blog By User");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByUser2() throws Exception {
        System.out.println("getBlogByUser");
        int userId = 0;
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 10;
        int result = instance.getBlogByUser(userId).size();
        if(expResult == result){
            System.out.println("get Blog By User Success");
        }
        else System.out.println("Fail to get Blog By User");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByUser3() throws Exception {
        System.out.println("getBlogByUser");
        int userId = 3;
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 2;
        int result = instance.getBlogByUser(userId).size();
        if(expResult == result){
            System.out.println("get Blog By User Success");
        }
        else fail("Fail to get Blog By User");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByUser4() throws Exception {
        System.out.println("getBlogByUser");
        int userId = 4;
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 2;
        int result = instance.getBlogByUser(userId).size();
        if(expResult == result){
            System.out.println("get Blog By User Success");
        }
        else fail("Fail to get Blog By User");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByUser5() throws Exception {
        System.out.println("getBlogByUser");
        int userId = 5;
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 6;
        int result = instance.getBlogByUser(userId).size();
        if(expResult == result){
            System.out.println("get Blog By User Success");
        }
        else fail("Fail to get Blog By User");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByUser6() throws Exception {
        System.out.println("getBlogByUser");
        int userId = 6;
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 2;
        int result = instance.getBlogByUser(userId).size();
        if(expResult == result){
            System.out.println("get Blog By User Success");
        }
        else fail("Fail to get Blog By User");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByUser7() throws Exception {
        System.out.println("getBlogByUser");
        int userId = 7;
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 4;
        int result = instance.getBlogByUser(userId).size();
        if(expResult == result){
            System.out.println("get Blog By User Success");
        }
        else fail("Fail to get Blog By User");
        assertEquals(expResult, result);
    }
    /**
     * Test of getBlogById method, of class BlogDAO.
     */
    @Test
    public void testGetBlogById1() throws Exception {
        System.out.println("getBlogById");
        int blogId = 1;
        BlogDAOImpl instance = new BlogDAOImpl();
        String expResult = "Rita’s Way: Why is it so Effective?";
        String result = instance.getBlogById(blogId).getBlogTitle();
        if(expResult == result){
            System.out.println("get Blog By ID Success");
        }
        else System.out.println("Fail to get Blog By ID");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogById2() throws Exception {
        System.out.println("getBlogById");
        int blogId = 2;
        BlogDAOImpl instance = new BlogDAOImpl();
        String expResult = "Write Useful Comments";
        String result = instance.getBlogById(blogId).getBlogTitle();
        if(expResult == result){
            System.out.println("get Blog By ID Success");
        }
        else System.out.println("Fail to get Blog By ID");
        assertEquals(expResult, result);
    }
    @Test
    public void testGetBlogById3() throws Exception {
        System.out.println("getBlogById");
        int blogId = 3;
        BlogDAOImpl instance = new BlogDAOImpl();
        String expResult = "7 Ways to Be a Healthy Web Programmer";
        String result = instance.getBlogById(blogId).getBlogTitle();
        if(expResult == result){
            System.out.println("get Blog By ID Success");
        }
        else System.out.println("Fail to get Blog By ID");
        assertEquals(expResult, result);
    }
    @Test
    public void testGetBlogById4() throws Exception {
        System.out.println("getBlogById");
        int blogId = 4;
        BlogDAOImpl instance = new BlogDAOImpl();
        String expResult = "Decision Making: Going Forward in Reverse";
        String result = instance.getBlogById(blogId).getBlogTitle();
        if(expResult == result){
            System.out.println("get Blog By ID Success");
        }
        else System.out.println("Fail to get Blog By ID");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogById5() throws Exception {
        System.out.println("getBlogById");
        int blogId = 5;
        BlogDAOImpl instance = new BlogDAOImpl();
        String expResult = "Coding Math: What You Should Know";
        String result = instance.getBlogById(blogId).getBlogTitle();
        if(expResult == result){
            System.out.println("get Blog By ID Success");
        }
        else System.out.println("Fail to get Blog By ID");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogById6() throws Exception {
        System.out.println("getBlogById");
        int blogId = 6;
        BlogDAOImpl instance = new BlogDAOImpl();
        String expResult = "Future of AI";
        String result = instance.getBlogById(blogId).getBlogTitle();
        if(expResult == result){
            System.out.println("get Blog By ID Success");
        }
        else System.out.println("Fail to get Blog By ID");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogById7() throws Exception {
        System.out.println("getBlogById");
        int blogId = 7;
        BlogDAOImpl instance = new BlogDAOImpl();
        String expResult = "What is Agile?";
        String result = instance.getBlogById(blogId).getBlogTitle();
        if(expResult == result){
            System.out.println("get Blog By ID Success");
        }
        else System.out.println("Fail to get Blog By ID");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogById8() throws Exception {
        System.out.println("getBlogById");
        int blogId = 8;
        BlogDAOImpl instance = new BlogDAOImpl();
        String expResult = "Using workflows for fun & profit";
        String result = instance.getBlogById(blogId).getBlogTitle();
        if(expResult == result){
            System.out.println("get Blog By ID Success");
        }
        else System.out.println("Fail to get Blog By ID");
        assertEquals(expResult, result);
    }
    /**
     * Test of getBlogByTitle method, of class BlogDAO.
     */
    @Test
    public void testGetBlogByTitle1() throws Exception {
        System.out.println("getBlogByTitle");
        String title = "Why";
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 3;
        int result = instance.getBlogByTitle(title).size();
        if(expResult == result){
            System.out.println("get Blog By Title Success");
        }
        else fail("Fail to get Blog By Titel");
        assertEquals(expResult, result);
    }
    
    
    @Test
    public void testGetBlogByTitle2() throws Exception {
        System.out.println("getBlogByTitle");
        String title = "All";
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 0;
        int result = instance.getBlogByTitle(title).size();
        if(expResult == result){
            System.out.println("get Blog By Title Success");
        }
        else fail("Fail to get Blog By Titel");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByTitle3() throws Exception {
        System.out.println("getBlogByTitle");
        String title = "AI";
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 1;
        int result = instance.getBlogByTitle(title).size();
        if(expResult == result){
            System.out.println("get Blog By Title Success");
        }
        else fail("Fail to get Blog By Titel");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByTitle4() throws Exception {
        System.out.println("getBlogByTitle");
        String title = "Math";
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 1;
        int result = instance.getBlogByTitle(title).size();
        if(expResult == result){
            System.out.println("get Blog By Title Success");
        }
        else fail("Fail to get Blog By Titel");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByTitle5() throws Exception {
        System.out.println("getBlogByTitle");
        String title = "Test";
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 2;
        int result = instance.getBlogByTitle(title).size();
        if(expResult == result){
            System.out.println("get Blog By Title Success");
        }
        else fail("Fail to get Blog By Titel");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByTitle6() throws Exception {
        System.out.println("getBlogByTitle");
        String title = "All";
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 3;
        int result = instance.getBlogByTitle(title).size();
        if(expResult == result){
            System.out.println("get Blog By Title Success");
        }
        else fail("Fail to get Blog By Titel");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByTitle7() throws Exception {
        System.out.println("getBlogByTitle");
        String title = "What";
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 1;
        int result = instance.getBlogByTitle(title).size();
        if(expResult == result){
            System.out.println("get Blog By Title Success");
        }
        else fail("Fail to get Blog By Titel");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByTitle8() throws Exception {
        System.out.println("getBlogByTitle");
        String title = "Why";
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 2;
        int result = instance.getBlogByTitle(title).size();
        if(expResult == result){
            System.out.println("get Blog By Title Success");
        }
        else fail("Fail to get Blog By Titel");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetBlogByTitle9() throws Exception {
        System.out.println("getBlogByTitle");
        String title = "Math";
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 2;
        int result = instance.getBlogByTitle(title).size();
        if(expResult == result){
            System.out.println("get Blog By Title Success");
        }
        else fail("Fail to get Blog By Titel");
        assertEquals(expResult, result);
    }

    /**
     * Test of getAllTrueBlog method, of class BlogDAO.
     */
    @Test
    public void testGetAllTrueBlog() throws Exception {
        System.out.println("getAllTrueBlog");
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 0;
        int result = instance.getAllTrueBlog().size();
        if(expResult == result){
            System.out.println("get All True Blog Success");
        }
        else fail("Fail to get All True Blog");
        assertEquals(expResult, result);
    }

    /**
     * Test of getLastBlogs method, of class BlogDAO.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetLastBlogs() throws Exception {
        System.out.println("getLastBlogs");
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResultUpper = 1;
        int expResultLower = 0;
        int result = instance.getLastBlogs().size();
        assertTrue((expResultUpper >= result) && (expResultLower <= result));
    }

    /**
     * Test of getBlogByCategoryAndTitle method, of class BlogDAO.
     */
    @Test
    public void testGetBlogByCategoryAndTitle() throws Exception {
        System.out.println("getBlogByCategoryAndTitle");
        String[] postCateIdList = null;
        String search = "";
        BlogDAOImpl instance = new BlogDAOImpl();
        ArrayList<Blog> expResult = null;
        ArrayList<Blog> result = instance.getBlogByCategoryAndTitle(postCateIdList, search);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
    /**
         * Test of addBlog method, of class BlogDAO.
         */
        @Test
        public void testAddBlog1() throws Exception {
            System.out.println("addBlog");
            User user = new User(1, "testUserName", "testUserPassword", 1, "testProfilePic", "testUserMail", true, "0303030303", true);
            Blog blog = new Blog(0, "testBlogTitle1", Date.valueOf("2023-07-07"), Date.valueOf("2023-07-07"), user, "test2", "testThumbnail3", true);
            
            BlogDAOImpl instance = new BlogDAOImpl();
            int expResult = 1;
            int result = instance.addBlog(blog);
            if(expResult == result){
                System.out.println("Quiz updated successfully");
            }
            else System.out.println("Null quiz object. Quiz not updated.");
            assertEquals(expResult, result);
            
        }
        @Test
        public void testAddBlog2() throws Exception {
            System.out.println("addBlog");
            User user = new User(1, "testUserName", "testUserPassword", 1, "testProfilePic", "testUserMail", true, "0303030303", true);
            Blog blog = new Blog(0, "testBlogTitle2", Date.valueOf("2023-08-07"), Date.valueOf("2023-08-07"), user, "test4", "testThumbnail2", false);
            
            BlogDAOImpl instance = new BlogDAOImpl();
            int expResult = 1;
            int result = instance.addBlog(blog);
            if(expResult == result){
                System.out.println("Quiz updated successfully");
            }
            else System.out.println("Null quiz object. Quiz not updated.");
            assertEquals(expResult, result);
            
        }

        @Test
        public void testAddBlog3() throws Exception {
            System.out.println("addBlog");
            User user = new User(1, "testUserName", "testUserPassword", 1, "testProfilePic", "testUserMail", true, "0303030303", true);
            Blog blog = new Blog(0, "testBlogTitle3", Date.valueOf("2023-06-07"), Date.valueOf("2023-09-07"), user, "test3", "testThumbnail4", true);
            
            BlogDAOImpl instance = new BlogDAOImpl();
            int expResult = 1;
            int result = instance.addBlog(blog);
            if(expResult == result){
                System.out.println("Quiz updated successfully");
            }
            else System.out.println("Null quiz object. Quiz not updated.");
            assertEquals(expResult, result);
            
        }
        @Test
        public void testAddBlog4() throws Exception {
            System.out.println("addBlog");
            User user = new User(1, "testUserName", "testUserPassword", 1, "testProfilePic", "testUserMail", true, "0303030303", true);
            Blog blog = new Blog(0, "testBlogTitle1", Date.valueOf("2023-08-07"), Date.valueOf("2023-09-07"), user, "test1", "testThumbnai2", true);
            
            BlogDAOImpl instance = new BlogDAOImpl();
            int expResult = 1;
            int result = instance.addBlog(blog);
            if(expResult == result){
                System.out.println("Quiz updated successfully");
            }
            else System.out.println("Null quiz object. Quiz not updated.");
            assertEquals(expResult, result);
            
        }
        
        @Test
        public void testAddBlog5() throws Exception {
            System.out.println("addBlog");
            User user = new User(1, "testUserName", "testUserPassword", 1, "testProfilePic", "testUserMail", true, "0303030303", true);
            Blog blog = new Blog(0, "testBlogTitle2", Date.valueOf("2023-07-07"), Date.valueOf("2023-08-07"), user, "test2", "testThumbnail", false);
            
            BlogDAOImpl instance = new BlogDAOImpl();
            int expResult = 2;
            int result = instance.addBlog(blog);
            if(expResult == result){
                System.out.println("Add Blog successfully");
            }
            else System.out.println("Fail to Add Blog");
            assertEquals(expResult, result);
            
        }
        
        @Test
        public void testAddBlog6() throws Exception {
            System.out.println("addBlog");
            User user = new User(1, "testUserName", "testUserPassword", 1, "testProfilePic", "testUserMail", true, "0303030303", true);
            Blog blog = new Blog(0, "testBlogTitle3", Date.valueOf("2023-08-07"), Date.valueOf("2023-09-07"), user, "test3", "testThumbnai3", true);
            
            BlogDAOImpl instance = new BlogDAOImpl();
            int expResult = 3;
            int result = instance.addBlog(blog);
            if(expResult == result){
                System.out.println("Add Blog successfully");
            }
            else System.out.println("Fail to Add Blog");
            assertEquals(expResult, result);
            
        }
    /**
     * Test of editBlog method, of class BlogDAO.
     */
    @Test
    public void testEditBlog() throws Exception {
        int blogId = 0;
        Blog blog = null;
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 0;
        int result = instance.editBlog(blogId, blog);
        assertEquals(expResult, result);
    }

    

    /**
     * Test of deleteBlog method, of class BlogDAO.
     */
    @Test
    public void testDeleteBlog() throws Exception {
        System.out.println("deleteBlog");
        int blogId = 0;
        BlogDAOImpl instance = new BlogDAOImpl();
        int expResult = 0;
        int result = instance.deleteBlog(blogId);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAuthor method, of class BlogDAO.
     */
    @Test
    public void testGetAuthor() throws Exception {
        System.out.println("getAuthor");
        int blogId = 0;
        BlogDAOImpl instance = new BlogDAOImpl();
        User expResult = null;
        User result = instance.getAuthor(blogId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getBlogCategory method, of class BlogDAO.
     */
    @Test
    public void testGetBlogCategory() throws Exception {
        System.out.println("getBlogCategory");
        int blogId = 0;
        BlogDAOImpl instance = new BlogDAOImpl();
        PostCate expResult = null;
        ArrayList<PostCate> result = instance.getBlogCategory(blogId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of Paging method, of class BlogDAO.
     */
    @Test
    public void testPaging() throws Exception {
        System.out.println("Paging");
        int page = 0;
        ArrayList<Blog> list = null;
        BlogDAOImpl instance = new BlogDAOImpl();
        ArrayList<Blog> expResult = null;
        ArrayList<Blog> result = instance.Paging(page, list);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    
}
