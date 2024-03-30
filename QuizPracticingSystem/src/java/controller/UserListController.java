
package controller;

import bean.User;
import bean.UserRole;
import dao.UserDAO;
import dao.UserRoleDAO;
import dao.impl.UserDAOImpl;
import dao.impl.UserRoleDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class UserListController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * User list function: show the list of user paginated.
     * - Can search with name, mail and mobile
     * - Can filter with status, gender and role
     * - Can sort the list with id, fullname, gender, email, mobile, role, status
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String service = request.getParameter("service");
            /*Service is null, redirect user to index*/
            if ((service == null) || (service.trim().equals(""))) {
                service = "loadCriteria";
            }
            
            UserDAO userDAO = new UserDAOImpl();
            UserRoleDAO userRoleDAO = new UserRoleDAOImpl();
            /* Get user and role on session scope */
            User currUser = (User) request.getSession().getAttribute("currUser");
            UserRole currRole = (UserRole) request.getSession().getAttribute("role");
            /* If user is not logged in, redirect to index */
            if ((currUser == null) || (currRole == null)) {
                sendDispatcher(request, response, "index.jsp");
            } else if (currRole.getUserRoleName().equalsIgnoreCase("admin")) {
                /*Get page number*/
                int page;
                if (request.getParameter("page") == null) {
                    page = 1;
                } else {
                    page = Integer.parseInt(request.getParameter("page"));
                }
                request.setAttribute("page", page);
                
                /*Get sort criteria*/
                String sortCriteria = request.getParameter("sortCriteria");
                System.out.println(sortCriteria);
                if ((sortCriteria == null) || (sortCriteria.trim().equals(""))){
                    sortCriteria = "sortId";
                }
                String sort = request.getParameter("sort");
                System.out.println(sort);
                if ((sort == null) || (sort.trim().equals(""))){
                    sort = "asc";
                }
                /*Set sorting information*/
                request.setAttribute("sortCriteria", sortCriteria);
                request.setAttribute("sort", sort);
                
                /**
                 * Service loadCriteria : load default lists and searched lists
                 */
                if (service.equalsIgnoreCase("loadCriteria")){
                    /* Role is admin: load all user */
                    String criteriaType = request.getParameter("criteriaType");
                    if (criteriaType == null){
                        criteriaType = "";
                    }
                    String criteria = request.getParameter("criteria");
                    if (criteria == null){
                        criteria = "";
                    }
                    request.setAttribute("criteriaType", criteriaType);
                    request.setAttribute("criteria", criteria);
                    /* Get user with criteria, paginated */
                    ArrayList<User> userPage = userDAO.getTrueAllUserPaging(page,criteriaType,criteria,sortCriteria,sort);
                    for (User user : userPage) {
                        user.setUserRole(userRoleDAO.getUserRoleById(user.getRoleId()));
                    }
                    int maxPage = (int) Math.ceil((double) userDAO.getTrueAllUserPaging(-1,criteriaType,criteria,sortCriteria,sort).size() / 7);
                    request.setAttribute("maxPage", maxPage);
                    
                    /*Reset filter*/
                    request.setAttribute("genderFilter", -1);
                    request.setAttribute("roleFilter", -1);
                    request.setAttribute("statusFilter", -1);
                    
                    request.setAttribute("userRoleList", userRoleDAO.getAllStatusUserRole());
                    /* Set attribute and send it to user list page */
                    request.setAttribute("userPageList", userPage);
                    request.setAttribute("service", "loadCriteria");
                    sendDispatcher(request, response, "jsp/userList.jsp");
                }
                
                /**
                 * Service Filter: load userList filtered
                 */
                if (service.equalsIgnoreCase("filter")){
                    int gender = Integer.parseInt(request.getParameter("genderFilter"));
                    int role = Integer.parseInt(request.getParameter("roleFilter"));
                    int status = Integer.parseInt(request.getParameter("statusFilter"));
                    request.setAttribute("genderFilter", gender);
                    request.setAttribute("roleFilter", role);
                    request.setAttribute("statusFilter", status);
                    /*Get user list*/
                    ArrayList<User> userPage = userDAO.getFilteredUserPaging(page, gender, role, status, sortCriteria, sort);
                    for (User user : userPage) {
                        user.setUserRole(userRoleDAO.getUserRoleById(user.getRoleId()));
                    }
                    
                    int maxPage = (int) Math.ceil((double) userDAO.getFilteredUserPaging(-1, gender, role, status, sortCriteria, sort).size() / 7);
                    request.setAttribute("maxPage", maxPage);
                    request.setAttribute("userRoleList", userRoleDAO.getAllStatusUserRole());
                    /* Set attribute and send it to user list page */
                    request.setAttribute("userPageList", userPage);
                    request.setAttribute("service", "filter");
                    sendDispatcher(request, response, "jsp/userList.jsp");
                }
                
                
            } else {
                /* If the user is logged in but not admin, send back to index.jsp */
                sendDispatcher(request, response, "index.jsp");
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMess", ex.toString());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /**
     * Forward the request to the destination, catch any unexpected exceptions and log it
     * @param request   Request of the servlet
     * @param response  Response of the servlet
     * @param path      Forward address
     */
    public void sendDispatcher(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.forward(request, response);

        } catch (ServletException | IOException ex) {
            Logger.getLogger(UserListController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
