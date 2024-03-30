/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.User;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDAOTest {

    public UserDAOTest() {
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
     * Test of getUserAllUser method, of class UserDAOImpl.
     */
    @Test
    public void testGetUserAllUser() throws Exception {
        System.out.println("getUserAllUser");
        UserDAOImpl instance = new UserDAOImpl();
        ArrayList<User> expResult = null;
        ArrayList<User> result = instance.getUserAllUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserLogin method, of class UserDAOImpl.
     */
    @Test
    public void testGetUserLogin() throws Exception {
        System.out.println("getUserLogin");
        String userMail = "DuongNHHE150328@fpt.edu.vn";
        String password = "1";
        UserDAOImpl instance = new UserDAOImpl();
        User expResult = null;
        User result = instance.getUserLogin(userMail, password);
        assertTrue(result.getPassword().equalsIgnoreCase("1"));
    }

    /**
     * Test of getUserById method, of class UserDAOImpl.
     */
    @Test
    public void testGetUserById() throws Exception {
        System.out.println("getUserById");
        int userId = 1;
        UserDAOImpl instance = new UserDAOImpl();
        int expResult = 1;
        User result = instance.getUserById(userId);
        assertTrue(expResult == result.getUserId());
    }

    /**
     * Test of getUserByMail method, of class UserDAOImpl.
     */
    @Test
    public void testGetUserByMail() throws Exception {
        System.out.println("getUserByMail");
        String userMail = "LamNTHE161761@fpt.edu.vn";
        UserDAOImpl instance = new UserDAOImpl();
        String expResult = "LamNTHE161761@fpt.edu.vn";
        User result = instance.getUserByMail(userMail);
        assertTrue(expResult.equalsIgnoreCase(result.getUserMail()));
    }

    /**
     * Test of getUserByMobile method, of class UserDAOImpl.
     */
    @Test
    public void testGetUserByMobile() throws Exception {
        System.out.println("getUserByMobile");
        String Moblie = "0969044713";
        UserDAOImpl instance = new UserDAOImpl();
        String expResult = "0969044713";
        User result = instance.getUserByMobile(Moblie);
        assertTrue(expResult.equalsIgnoreCase(result.getUserMobile()));
    }

    /**
     * Test of updateUser method, of class UserDAOImpl.
     */
    @Test
    public void testUpdateUser() throws Exception {
        System.out.println("updateUser");
        UserDAOImpl instance = new UserDAOImpl();
        User updatedUser = instance.getUserById(1);
        int expResult = 1;
        int result = instance.updateUser(updatedUser);
        assertTrue(expResult == result);
    }

    /**
     * Test of changeStatus method, of class UserDAOImpl.
     */
    @Test
    public void testChangeStatus() throws Exception {
        System.out.println("changeStatus");
        int userId = 1;
        boolean newStatus = true;
        UserDAOImpl instance = new UserDAOImpl();
        int expResult = 1;
        int result = instance.changeStatus(userId, newStatus);
        assertTrue(expResult == result);
    }

    /**
     * Test of addUser method, of class UserDAOImpl.
     */
    @Test
    public void testAddUser() throws Exception {
        System.out.println("addUser");
        User newUser = new User(0, "Lam", "1", 1, null, "lamnthe161761", true, "0842274855", true);
        UserDAOImpl instance = new UserDAOImpl();
        int expResult = 1;
        int result = instance.addUser(newUser);
        assertTrue(expResult == result);
    }

    /**
     * Test of deleteUser method, of class UserDAOImpl.
     */
    @Test
    public void testDeleteUser() throws Exception {
        System.out.println("deleteUser");
        UserDAOImpl instance = new UserDAOImpl();
        User user = instance.getUserByMail("duonghoang8805");
        int expResult = 1;
        int result = instance.deleteUser(user);
        assertTrue(expResult == result);
    }

}
