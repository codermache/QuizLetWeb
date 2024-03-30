/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import bean.UserRole;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserRoleDAOTest {

    public UserRoleDAOTest() {
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
     * Test of getAllUserRole method, of class UserRoleDAOImpl.
     */
    @Test
    public void testGetAllUserRole() throws Exception {
        System.out.println("getAllUserRole");
        UserRoleDAOImpl instance = new UserRoleDAOImpl();
        int expResult = 0;
        ArrayList<UserRole> result = instance.getAllUserRole();
        assertTrue(expResult <= result.size());
    }

    /**
     * Test of getUserRoleById method, of class UserRoleDAOImpl.
     */
    @Test
    public void testGetUserRoleById() throws Exception {
        System.out.println("getUserRoleById");
        int roleId = 1;
        UserRoleDAOImpl instance = new UserRoleDAOImpl();
        int expResult = 1;
        UserRole result = instance.getUserRoleById(roleId);
        assertTrue(expResult == result.getUserRoleId());
    }

    /**
     * Test of editRole method, of class UserRoleDAOImpl.
     */
    @Test
    public void testEditRole() throws Exception {
        System.out.println("editRole");
        int roleId = 1;
        UserRoleDAOImpl instance = new UserRoleDAOImpl();
        UserRole userRole = instance.getUserRoleById(roleId);
        int expResult = 1;
        int result = instance.editRole(userRole);
        assertTrue(expResult == result);
    }

    /**
     * Test of addRole method, of class UserRoleDAOImpl.
     */
    @Test
    public void testAddRole() throws Exception {
        System.out.println("addRole");
        UserRoleDAOImpl instance = new UserRoleDAOImpl();
        UserRole userRole = instance.getUserRoleById(1);
        int expResult = 1;
        int result = instance.addRole(userRole);
        assertTrue(expResult == result);
    }

    /**
     * Test of deleteRole method, of class UserRoleDAOImpl.
     */
    @Test
    public void testDeleteRole() throws Exception {
        System.out.println("deleteRole");
        int roleId = 6;
        UserRoleDAOImpl instance = new UserRoleDAOImpl();
        int expResult = 1;
        int result = instance.deleteRole(roleId);
        assertTrue(expResult == result);
    }

}
