
package dao.impl;

import bean.User;
import dao.DBConnection;
import java.util.ArrayList;
import dao.UserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class UserDAOImpl extends DBConnection implements UserDAO {
    /**
     * Get all user
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<User> getUserAllUser() throws Exception {
        ArrayList<User> newUserList = new ArrayList();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pre = null;
        String sql = "SELECT * FROM [User]";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                newUserList.add(new User(rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getInt("roleId"),
                        rs.getString("profilePic"),
                        rs.getString("userMail"),
                        rs.getBoolean("gender"),
                        rs.getString("userMobile"),
                        rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return newUserList;
    }

    /**
     * get new users
     *
     * @return <code>ArrayList<Use>r</code> object.
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<User> get10NewUser() throws Exception {
        ArrayList<User> newUserList = new ArrayList();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pre = null;
        String sql = "SELECT TOP 10 * FROM [User] ORDER BY userId DESC";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                newUserList.add(new User(rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getInt("roleId"),
                        rs.getString("profilePic"),
                        rs.getString("userMail"),
                        rs.getBoolean("gender"),
                        rs.getString("userMobile"),
                        rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return newUserList;
    }

    /**
     * get user from User table Using mail and password
     *
     * @param userMail is an String
     * @param password is an String
     * @return <code>User</code> object.
     * @throws java.lang.Exception
     */
    @Override
    public User getUserLogin(String userMail, String password) throws Exception {
        Connection conn = null;
        /* Result set returned by the sqlserver */
        ResultSet rs = null;
        /* Prepared statement for executing sql queries */
        PreparedStatement pre = null;

        String sql = "SELECT * FROM [User] WHERE userMail = ? and password = ? and status = 1";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, userMail);
            pre.setString(2, password);
            rs = pre.executeQuery();
            if (rs.next()) {
                User loginUser = new User(rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getInt("roleId"),
                        rs.getString("profilePic"),
                        rs.getString("userMail"),
                        rs.getBoolean("gender"),
                        rs.getString("userMobile"),
                        rs.getBoolean("status"));
                return loginUser;
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return null;
    }

    /**
     * get user from User table using userId
     *
     * @param userId is an int
     * @return <code>User</code> object.
     * @throws java.lang.Exception
     */
    @Override
    public User getUserById(int userId) throws Exception {
        Connection conn = null;
        /* Result set returned by the sqlserver */
        ResultSet rs = null;
        /* Prepared statement for executing sql queries */
        PreparedStatement pre = null;

        String sql = "SELECT * FROM [User] WHERE userId = ?";
        User user = null;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, userId);
            rs = pre.executeQuery();
            while (rs.next()) {
                user = new User(rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getInt("roleId"),
                        rs.getString("profilePic"),
                        rs.getString("userMail"),
                        rs.getBoolean("gender"),
                        rs.getString("userMobile"),
                        rs.getBoolean("status"));
                return user;
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return null;
    }

    /**
     * get user from User table using userMail
     *
     * @param userMail is an String
     * @return <code>User</code> object.
     */
    @Override
    public User getUserByMail(String userMail) throws Exception {
        Connection conn = null;
        /* Result set returned by the sqlserver */
        ResultSet rs = null;
        /* Prepared statement for executing sql queries */
        PreparedStatement pre = null;

        String sql = "SELECT * FROM [User] WHERE userMail = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, userMail);
            rs = pre.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getInt("roleId"),
                        rs.getString("profilePic"),
                        rs.getString("userMail"),
                        rs.getBoolean("gender"),
                        rs.getString("userMobile"),
                        rs.getBoolean("status"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return null;
    }

    /**
     * get user from User table using userMobile
     *
     * @param userMobile is an String
     * @return <code>User</code> object.
     */
    @Override
    public User getUserByMobile(String userMobile) throws Exception {
        Connection conn = null;
        /* Result set returned by the sqlserver */
        ResultSet rs = null;
        /* Prepared statement for executing sql queries */
        PreparedStatement pre = null;
        String sql = "SELECT * FROM [User] WHERE userMobile = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, userMobile);
            rs = pre.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getInt("roleId"),
                        rs.getString("profilePic"),
                        rs.getString("userMail"),
                        rs.getBoolean("gender"),
                        rs.getString("userMobile"),
                        rs.getBoolean("status"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return null;
    }

    /**
     * update a user from User table
     *
     * @param updatedUser is a <code>User</code> object
     * @return a int.
     */
    @Override
    public int updateUser(User updatedUser) throws Exception {
        Connection conn = null;
        /* Result set returned by the sqlserver */
        ResultSet rs = null;
        /* Prepared statement for executing sql queries */
        PreparedStatement pre = null;

        String sql = " UPDATE [User] set userName = ?, "
                + "[password] = ?,  "
                + "roleId = ?, "
                + "profilePic = ?, "
                + "userMail = ?, "
                + "gender = ?, "
                + "userMobile = ?, "
                + "status = ? "
                + "where userId = ?";
        int check = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, updatedUser.getUserName());
            pre.setString(2, updatedUser.getPassword());
            pre.setInt(3, updatedUser.getRoleId());
            pre.setString(4, updatedUser.getProfilePic());
            pre.setString(5, updatedUser.getUserMail());
            pre.setBoolean(6, updatedUser.isGender());
            pre.setString(7, updatedUser.getUserMobile());
            pre.setBoolean(8, updatedUser.isStatus());
            pre.setInt(9, updatedUser.getUserId());
            check = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }

        return check;
    }

    /**
     * change a user status from User table
     *
     * @param userId is an int
     * @param newStatus is a boolean object
     * @return a int.
     */
    @Override
    public int changeStatus(int userId, boolean newStatus) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "UPDATE [User] set [status] = ? where userId = ?";
        int check = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setBoolean(1, newStatus);
            pre.setInt(2, userId);
            check = pre.executeUpdate();

        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return check;
    }

    /**
     * add a user to User table
     *
     * @param newUser is an <code>User</code> object
     * @return a int.
     */
    @Override
    public int addUser(User newUser) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "INSERT INTO [User](userName,[password],roleId,userMail,gender,userMobile,[status])"
                + "values(?,?,?,?,?,?,?)";
        int check = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, newUser.getUserName());
            pre.setString(2, newUser.getPassword());
            pre.setInt(3, 1);
            pre.setString(4, newUser.getUserMail());
            pre.setBoolean(5, newUser.isGender());
            pre.setString(6, newUser.getUserMobile());
            pre.setBoolean(7, false);
            check = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }

        return check;
    }

    /**
     * delete a user from User table
     *
     * @param user is an <code>User</code> object
     * @return a int.
     */
    @Override
    public int deleteUser(User user) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = " delete from [User] where userId = ?";

        int check = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, user.getUserId());
            check = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return check;

    }

    public HashMap<String, Integer> getUserCountByRole() throws Exception {
        HashMap<String, Integer> map = new HashMap();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pre = null;
        String sql = "SELECT userRoleName, COUNT(userRoleId) AS number "
                + "FROM [UserRole] AS a JOIN [User] AS b ON a.userRoleId = b.roleId "
                + "GROUP BY a.userRoleId,a.userRoleName ";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("userRoleName"), rs.getInt("number"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return map;
    }

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
    @Override
    public ArrayList<User> getTrueAllUserPaging(int page, String criteriaType, String criteria, String sortCriteria, String sort) throws Exception {
        ArrayList<User> newUserList = new ArrayList();
        /*Database Connection*/
        Connection conn = null;
        /*Result Set got from executing sql query*/
        ResultSet rs = null;
        /*Prepared Statement for executing query*/
        PreparedStatement pre = null;
        String queryCriteria = "";
        /*Set criteria based on the search type*/
        switch (criteriaType) {
            case "userName": queryCriteria = " WHERE userName LIKE ? ";
                             criteria = criteria + "%";
                             criteria = "%" + criteria;
                            break;
            case "userMail": queryCriteria =" WHERE userMail LIKE ? ";
                             criteria = criteria + "%";
                             criteria = "%" + criteria;
                            break;
            case "userMobile": queryCriteria =" WHERE userMobile = ? ";
                            break;
            default:    criteriaType = null;
                        break;
        }
        switch (sortCriteria){
            case "sortId": sort = " Order By userId " + sort;
                        break;
            case "sortName": sort = " Order By userName " + sort;
                        break;
            case "sortGender": sort = " Order By gender " + sort;
                        break;
            case "sortMail": sort = " Order By userMail " + sort;
                        break;
            case "sortMobile": sort = " Order By userMobile " + sort;
                        break;
            case "sortRole": sort = " Order By roleId " + sort;
                        break;
            case "sortStatus": sort = " Order By status " + sort;
                        break;
            default: sort = " Order By userId ASC";
        }
        
        /*Set page boundaries*/
        String pageBoundary = "";
        
        if (page > 0){
            pageBoundary = "  WHERE A.num BETWEEN ? AND ?";
        }
        
        /*SQL query with the criteria*/
        String sql = "  SELECT * FROM (SELECT ROW_NUMBER()  OVER("+ sort +") as num\n"
                        + "				  ,[userId]\n"
                        + "				  ,[userName]\n"
                        + "				  ,[password]\n"
                        + "				  ,[roleId]\n"
                        + "				  ,[profilePic]\n"
                        + "				  ,[userMail]\n"
                        + "				  ,[gender]\n"
                        + "				  ,[userMobile]\n"
                        + "				  ,[status]\n"
                        + "				  FROM [QuizSystem].dbo.[User]\n"
                        + queryCriteria + ") A\n" + pageBoundary;
        
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            if (page > 0){
                if (criteriaType != null){
                    pre.setString(1, criteria);
                    pre.setInt(2, (page-1)*7+1);
                    pre.setInt(3, page*7);
                } else if (page > 0){
                pre.setInt(1, (page-1)*7+1);
                pre.setInt(2, page*7);
                }
            } else if (criteriaType != null) {
                pre.setString(1, criteria);
            }
            
            rs = pre.executeQuery();
            while (rs.next()) {
                newUserList.add(new User(rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getInt("roleId"),
                        rs.getString("profilePic"),
                        rs.getString("userMail"),
                        rs.getBoolean("gender"),
                        rs.getString("userMobile"),
                        rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return newUserList;
    }
    
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
    @Override
    public ArrayList<User> getFilteredUserPaging(int page, int gender, int role, int status, String sortCriteria, String sort) throws Exception {
        ArrayList<User> newUserList = new ArrayList();
        /*Database Connection*/
        Connection conn = null;
        /*Result Set got from executing sql query*/
        ResultSet rs = null;
        /*Prepared Statement for executing query*/
        PreparedStatement pre = null;
        /*Set filter depends on the criteria*/
        String filter = "";
        if (gender != -1){
            filter += " WHERE gender = ?";
            if (role != -1){
                filter += " AND roleId = ?";
            }
            if (status != -1){
                filter += " AND [status] = ?";
            }
        } else if (role != -1){
            filter += "WHERE roleId = ?";
            if (status != -1){
                filter += " AND [status] = ?";
            }
        } else if (status != -1){
                filter += "WHERE [status] = ?";
        }
        /**
         * Sort
         */
        switch (sortCriteria){
            case "sortId": sort = " Order By userId " + sort;
                        break;
            case "sortName": sort = " Order By userName " + sort;
                        break;
            case "sortGender": sort = " Order By gender " + sort;
                        break;
            case "sortMail": sort = " Order By userMail " + sort;
                        break;
            case "sortMobile": sort = " Order By userMobile " + sort;
                        break;
            case "sortRole": sort = " Order By roleId " + sort;
                        break;
            case "sortStatus": sort = " Order By status " + sort;
                        break;
            default: sort = " Order By userId ASC";
        }
        
        /*Set page boundaries*/
        String pageBoundary = "";
        
        if (page > 0){
            pageBoundary = "  WHERE A.num BETWEEN ? AND ?";
        }
        
        /*SQL query with the criteria*/
        String sql = "  SELECT * FROM (SELECT ROW_NUMBER()  OVER("+ sort +") as num\n"
                        + "				  ,[userId]\n"
                        + "				  ,[userName]\n"
                        + "				  ,[password]\n"
                        + "				  ,[roleId]\n"
                        + "				  ,[profilePic]\n"
                        + "				  ,[userMail]\n"
                        + "				  ,[gender]\n"
                        + "				  ,[userMobile]\n"
                        + "				  ,[status]\n"
                        + "				  FROM [QuizSystem].dbo.[User]\n"
                        + filter + ") A\n" + pageBoundary;
        
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            int parameterCount = 1;
            /*Set filter parrameter*/
            if (gender != -1){
                pre.setInt(parameterCount++, gender);
            } 
            if (role != -1){
                pre.setInt(parameterCount++, role);
            }
            if (status != -1){
                pre.setInt(parameterCount++, status);
            }
            /*Set paging parameter*/
            if (page > 0){
                pre.setInt(parameterCount++, (page-1)*7+1);
                pre.setInt(parameterCount, page*7);
            }
            
            rs = pre.executeQuery();
            while (rs.next()) {
                newUserList.add(new User(rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getInt("roleId"),
                        rs.getString("profilePic"),
                        rs.getString("userMail"),
                        rs.getBoolean("gender"),
                        rs.getString("userMobile"),
                        rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return newUserList;
    }

//    public static void main(String[] args) throws Exception {
//        UserDAOImpl dao = new UserDAOImpl();
//        for (User u : dao.getTrueAllUserPaging(1, "userMail", "fpt", "sortMail", "asc")) {
//            System.out.println(u.getUserMail());
//        }
//    }
}
