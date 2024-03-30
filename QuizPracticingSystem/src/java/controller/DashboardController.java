
package controller;

import bean.ItemDashboard;
import bean.Registration;
import bean.Subject;
import bean.SubjectCate;
import bean.User;
import bean.UserRole;
import static controller.MarketingController.MILISECOND_PER_WEEK;
import dao.RegistrationDAO;
import dao.SubjectCateDAO;
import dao.SubjectDAO;
import dao.UserDAO;
import dao.UserRoleDAO;
import dao.ViewDAO;
import dao.impl.RegistrationDAOImpl;
import dao.impl.SubjectCateDAOImpl;
import dao.impl.SubjectDAOImpl;
import dao.impl.UserDAOImpl;
import dao.impl.UserRoleDAOImpl;
import dao.impl.ViewDAOImpl;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class DashboardController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Service for viewing system's statistics
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            //authorization
            HttpSession session = request.getSession();
            Object currUser = session.getAttribute("currUser");
            if (currUser == null) {
                response.sendRedirect("index.jsp");
            } else {
                User user = (User) currUser;
                if (user.getRoleId() ==1) {
                    response.sendRedirect("index.jsp");
                } else {
                    RegistrationDAO IRegistration = new RegistrationDAOImpl();
                    SubjectDAO ISubject = new SubjectDAOImpl();
                    ViewDAO IView = new ViewDAOImpl();
                    UserDAO IUser = new UserDAOImpl();
                    SubjectCateDAO ISubjectCate = new SubjectCateDAOImpl();
                    UserRoleDAO IUserRole = new UserRoleDAOImpl();
                    String option = request.getParameter("option");
                    String target = request.getParameter("target");
                    String attribute = request.getParameter("attribute");
                    String from = request.getParameter("from");
                    String to = request.getParameter("to");
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDate = formatter.format(date.getTime());
                    
                    //default date range 1 week
                    if (to == null) {
                        to = currentDate;
                    }
                    if (from == null) {
                        from = formatter.format(date.getTime() - MILISECOND_PER_WEEK);
                    }
                    request.setAttribute("currentDate", currentDate);
                    request.setAttribute("from", from);
                    request.setAttribute("to", to);

                    //default view option
                    if (option == null) {
                        option = "subject";
                        target = "new";
                        attribute = "revenue";
                    }

                    request.setAttribute("option", option);
                    request.setAttribute("target", target);
                    ArrayList<String> statistics = new ArrayList();
                    ArrayList<String> nameList = new ArrayList();

                    //view subject statistics
                    if (option.equals("subject")) {

                        request.setAttribute("attribute", attribute);
                        ArrayList<Subject> subjectList = new ArrayList();
                        if (target.equals("new")) {
                            subjectList = ISubject.get5LastAddedSubject();
                        } else if (target.equals("all")) {
                            subjectList = ISubject.getAllSubjects();
                        }

                        ArrayList<ItemDashboard> subjectStatistics = IRegistration.getSubjectStatistics(from, to, subjectList, attribute);
                        statistics = IRegistration.convertJson(subjectStatistics);
                        nameList = IRegistration.getNameList(subjectStatistics);
                    }

                    //registration statistics
                    if (option.equals("registration")) {
                        ArrayList<ItemDashboard> registrationStatistics = IRegistration.getRegistrationStatistics(from, to);
                        statistics = IRegistration.convertJson(registrationStatistics);
                        nameList = IRegistration.getNameList(registrationStatistics);
                    }

                    //revenue statistics
                    if (option.equals("revenue")) {
                        ArrayList<ItemDashboard> revenueStatistics = new ArrayList();
                        if (target.equals("total")) {
                            revenueStatistics = IRegistration.getRevenueStatistics(from, to);
                        } else if (target.equals("bySubjectCate")) {
                            revenueStatistics = IRegistration.getRevenueStatisticsBySubjectCate(from, to);
                        }
                        statistics = IRegistration.convertJson(revenueStatistics);
                        nameList = IRegistration.getNameList(revenueStatistics);
                    }

                    //customer statistics
                    if (option.equals("customer")) {
                        if (target.equals("newlyRegistered")) {
                            ArrayList<User> userList = IUser.get10NewUser();
                            request.setAttribute("userList", userList);
                        } else if (target.equals("newlyBought")) {
                            ArrayList<Registration> registrationList = IRegistration.get10NewRegistration();
                            request.setAttribute("registrationList", registrationList);
                        }
                    }

                    //view statistics   
                    if (option.equals("view")) {
                        int totalView = IView.getTotalView();
                        String totalViewFormated = String.format("%06d", totalView);
                        request.setAttribute("totalView", totalViewFormated);

                        int totalRegistrationCount = IRegistration.getAllRegistration().size();
                        request.setAttribute("totalRegistrationCount", totalRegistrationCount);
                        int paidRegistrationCount = IRegistration.getPaidRegistration("true").size();
                        request.setAttribute("paidRegistrationCount", paidRegistrationCount);
                        int unpaidRegistrationCount = IRegistration.getPaidRegistration("false").size();
                        request.setAttribute("unpaidRegistrationCount", unpaidRegistrationCount);

                        int totalSubjectCount = ISubject.getAllSubjects().size();
                        request.setAttribute("totalSubjectCount", totalSubjectCount);

                        HashMap<String, Integer> subjectCateCountMap = ISubjectCate.getSubjectCountByCate();
                        request.setAttribute("subjectCateCountMap", subjectCateCountMap);
                        ArrayList<SubjectCate> subjectCateList = ISubjectCate.getAllStatusSubjectCates();
                        request.setAttribute("subjectCateList", subjectCateList);

                        HashMap<String, Integer> userRoleCountMap = IUser.getUserCountByRole();
                        request.setAttribute("userRoleCountMap", userRoleCountMap);
                        ArrayList<UserRole> userRoleList = IUserRole.getAllUserRole();
                        request.setAttribute("userRoleList", userRoleList);

                        int totalUserCount = IUser.getUserAllUser().size();
                        request.setAttribute("totalUserCount", totalUserCount);

                        ArrayList<ItemDashboard> viewStatistics = IView.getViewStatistics(from, to);
                        statistics = IView.convertJson(viewStatistics);
                        nameList = IRegistration.getNameList(viewStatistics);
                    }

                    request.setAttribute("statistics", statistics);
                    request.setAttribute("nameList", nameList);
                    request.getRequestDispatcher("jsp/dashboard.jsp").forward(request, response);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMess", ex.toString());
            request.getRequestDispatcher("error.jsp").forward(request, response);
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
