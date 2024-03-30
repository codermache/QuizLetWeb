
package dao.impl;

import bean.UserRole;
import dao.DBConnection;
import java.util.ArrayList;
import dao.UserRoleDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserRoleDAOImpl extends DBConnection implements UserRoleDAO {

    /**
     * Get all user role
     * @return
     * @throws Exception 
     */
    @Override
    public ArrayList<UserRole> getAllUserRole() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "SELECT [userRoleId],[userRoleName],[status] FROM [QuizSystem].[dbo].[UserRole] where status = 1";
        ArrayList<UserRole> allUserRole = new ArrayList<>();
        UserRole add = null;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                add = new UserRole(rs.getInt("userRoleId"), rs.getString("userRoleName"), rs.getBoolean("status"));
                allUserRole.add(add);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return allUserRole;
    }

    /**
     * Get user role with a specified id
     * @param roleId
     * @return
     * @throws Exception 
     */
    @Override
    public UserRole getUserRoleById(int roleId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "SELECT [userRoleId],[userRoleName],[status] FROM [QuizSystem].[dbo].[UserRole] WHERE userRoleId = " + roleId;
        UserRole userRole = null;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                userRole = new UserRole(rs.getInt("userRoleId"), rs.getString("userRoleName"), rs.getBoolean("status"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return userRole;
    }

    /**
     * Edit user Role
     * @param userRole
     * @return
     * @throws Exception 
     */
    @Override
    public int editRole(UserRole userRole) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "UPDATE [UserRole] SET userRoleName = ?,[status] = ? WHERE userRoleId = ?";
        int i = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, userRole.getUserRoleName());
            pre.setBoolean(2, userRole.isStatus());
            pre.setInt(3, userRole.getUserRoleId());
            i = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return i;
    }

    /**
     * Add new user role
     * @param userRole
     * @return
     * @throws Exception 
     */
    @Override
    public int addRole(UserRole userRole) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "INSERT INTO [UserRole](userRoleName,status) VALUES(?,?)";
        int i = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, userRole.getUserRoleName());
            pre.setBoolean(2, userRole.isStatus());
            i = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return i;
    }

    /**
     * Delete user Role
     * @param roleId
     * @return
     * @throws Exception 
     */
    @Override
    public int deleteRole(int roleId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "DELETE FROM [UserRole] WHERE userRoleID = " + roleId;
        int i = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            i = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return i;
    }

    /**
     * Get all user role
     * @return
     * @throws Exception 
     */
    @Override
    public ArrayList<UserRole> getAllStatusUserRole() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "SELECT [userRoleId],[userRoleName],[status] FROM [QuizSystem].[dbo].[UserRole]";
        ArrayList<UserRole> allUserRole = new ArrayList<>();
        UserRole add = null;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                add = new UserRole(rs.getInt("userRoleId"), rs.getString("userRoleName"), rs.getBoolean("status"));
                allUserRole.add(add);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return allUserRole;
    }
}
