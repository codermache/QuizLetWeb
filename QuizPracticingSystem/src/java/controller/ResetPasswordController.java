
package controller;

import bean.SystemEmail;
import bean.User;
import dao.UserDAO;
import dao.impl.UserDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class ResetPasswordController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * Function Reset Password: allow the user to reset their old password using a confirm link via email 
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
            UserDAO userDAO = new UserDAOImpl();
            //get email from page and send a resetPass mail to the address
            if (service.equalsIgnoreCase("resetPassword")) {
                String userMail = request.getParameter("enteredUserMail").trim();
                String mess = "";
                //check email if it is true
                if (userMail.length() == 0 || userMail == null) {
                    mess = "You have to input your email";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/resetPass.jsp").forward(request, response);
                    return;
                } else if (userDAO.getUserByMail(userMail) == null) {//if email have yet existed in the system
                    mess = "Email not existed!";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/resetPass.jsp").forward(request, response);
                    return;
                } else {// send reset email to the customer email
                    SystemEmail se = new SystemEmail();
                    long milis = System.currentTimeMillis(); //save send email time to the link (to check in valid time)
                    String resetPassLink = "http://localhost:8080/QuizPracticingSystem/login/resetPass.jsp?userMail="
                            + userMail + "&createTime=" + milis;
                    se.sendEmail(userMail, "Reset password link", resetPassLink);
                    mess = "An reset password link have been sent to your email address";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/resetPass.jsp").forward(request, response);
                    return;
                }
            }

            //get new pass and save to the database
            if (service.equalsIgnoreCase("resetPage")) {
                String mess;
                String userMail = request.getParameter("userMail");
                String newPass = request.getParameter("newPass");
                String confirmNewPass = request.getParameter("confirmNewPass");
                User user = userDAO.getUserByMail(userMail);
                if (newPass.equals(confirmNewPass)) { // if cofirm password match the password then change pass
                    user.setPassword(newPass);
                    userDAO.updateUser(user);
                    mess = "Your password have been reset";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/resetPass.jsp").forward(request, response);
                    return;
                } else { // if cofirm password dont match the password then print out alert mess
                    mess = "Confirm password dont match";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/resetPass.jsp").forward(request, response);
                    return;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ResetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
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
