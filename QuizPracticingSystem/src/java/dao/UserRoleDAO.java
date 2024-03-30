
package dao;

import bean.UserRole;
import java.util.ArrayList;

public interface UserRoleDAO {
    
    /**
     * Get all user role where status = 1 
     * @return
     * @throws Exception 
     */
    public ArrayList<UserRole> getAllUserRole() throws Exception;
    
    /**
     * Get user role with a specified id
     * @param roleId
     * @return
     * @throws Exception 
     */
    public UserRole getUserRoleById(int roleId) throws Exception;
    
    /**
     * Edit user Role
     * @param userRole
     * @return
     * @throws Exception 
     */
    public int editRole(UserRole userRole) throws Exception;
    
    /**
     * Add new user role
     * @param userRole
     * @return
     * @throws Exception 
     */
    public int addRole(UserRole userRole) throws Exception;
    
    /**
     * Delete user Role
     * @param roleId
     * @return
     * @throws Exception 
     */
    public int deleteRole(int roleId) throws Exception;
    
    /**
     * Get all user role
     * @return
     * @throws Exception 
     */
    public ArrayList<UserRole> getAllStatusUserRole() throws Exception;
}
