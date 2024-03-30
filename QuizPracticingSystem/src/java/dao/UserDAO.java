
package dao;

import bean.User;
import java.util.ArrayList;
import java.util.HashMap;


public interface UserDAO {

    /**
     * Get all user
     *
     * @return
     * @throws Exception
     */
    public ArrayList<User> getUserAllUser() throws Exception;

    /**
     * get new users
     *
     * @return <code>ArrayList<Use>r</code> object.
     * @throws java.lang.Exception
     */
    public ArrayList<User> get10NewUser() throws Exception;

    /**
     * get user from User table Using mail and password
     *
     * @param userMail is an String
     * @param password is an String
     * @return <code>User</code> object.
     * @throws java.lang.Exception
     */
    public User getUserLogin(String userMail, String password) throws Exception;

    /**
     * get user from User table using userId
     *
     * @param userId is an <code>int</code>
     * @return <code>User</code> object.
     * @throws java.lang.Exception
     */
    public User getUserById(int userId) throws Exception;

    /**
     * get user from User table using userMail
     *
     * @param userMail is an String
     * @return <code>User</code> object.
     * @throws java.lang.Exception
     */
    public User getUserByMail(String userMail) throws Exception;

    /**
     * get user from User table using userMobile
     *
     * @param Mobile is an String
     * @return <code>User</code> object.
     */
    public User getUserByMobile(String Moblie) throws Exception;

    /**
     * update a user from User table
     *
     * @param updatedUser is a <code>User</code> object
     * @return a int.
     * @throws java.lang.Exception
     */
    public int updateUser(User updatedUser) throws Exception;

    /**
     * change a user status from User table
     *
     * @param userId is an int
     * @param newStatus is a boolean object
     * @return a int.
     * @throws java.lang.Exception
     */
    public int changeStatus(int userId, boolean newStatus) throws Exception;

    /**
     * add a user to User table
     *
     * @param newUser is an <code>User</code> object
     * @return a int.
     * @throws java.lang.Exception
     */
    public int addUser(User newUser) throws Exception;

    /**
     * delete a user from User table
     *
     * @param user is an <code>User</code> object
     * @return a int.
     * @throws java.lang.Exception
     */
    public int deleteUser(User user) throws Exception;

    /**
     * Get user count by user role
     *
     * @return <code>HashMap</code>
     * @throws Exception
     */
    public HashMap<String, Integer> getUserCountByRole() throws Exception;
    
    /**
     * Get all user regardless of status, based on criteria and sort in a paginated form
     * @param page
     * @param criteriaType
     * @param criteria
     * @param sortCriteria
     * @param sort
     * @return
     * @throws Exception 
     */
    public ArrayList<User> getTrueAllUserPaging(int page, String criteriaType, String criteria, String sortCriteria, String sort) throws Exception;
    
    /**
     * Get filtered and sort Users paginated
     * @param page
     * @param gender
     * @param role
     * @param status
     * @param sortCriteria
     * @param sort
     * @return
     * @throws Exception 
     */
    public ArrayList<User> getFilteredUserPaging(int page, int gender, int role, int status, String sortCriteria, String sort) throws Exception;
}
