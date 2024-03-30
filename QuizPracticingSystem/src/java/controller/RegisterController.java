
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

public class RegisterController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * Function Register: allow the new user to register a account 
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
            //get all atribute from page then check validate and save to database
            if (service.equalsIgnoreCase("register")) {
                UserDAO userDAO = new UserDAOImpl();
                String mess = "";
                String userName = request.getParameter("userName").trim();
                String password = request.getParameter("password").trim();
                String confirmPass = request.getParameter("confirmPass").trim();
                String userMail = request.getParameter("userMail").trim();
                String userMobile = request.getParameter("userMobile").trim();
                String txtGender = request.getParameter("gender").trim();
                boolean gender;
                User addUser = new User();

                //check blank input fields
                if (userName.length() == 0 || password.length() == 0
                        || confirmPass.length() == 0
                        || userMail.length() == 0 || userMobile.length() == 0
                        || txtGender.length() == 0) {
                    mess = "You have to input all information!";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/register.jsp").forward(request, response);
                    return;
                }

                //check max length
                if (userName.length() > 63 || password.length() > 255
                        || confirmPass.length() > 255
                        || userMail.length() > 255
                        || userMobile.length() > 10) {
                    mess = "Your input have reached max length!";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/register.jsp").forward(request, response);
                    return;
                }

                //check if comfirm pass is the same as pass
                if (!password.equals(confirmPass)) {
                    mess = "The confirm-password is not match with the password!";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/register.jsp").forward(request, response);
                    return;
                }

                //check validate mail
                String mailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
                if (!userMail.matches(mailRegex)) {
                    mess = "The Email is invalid !";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/register.jsp").forward(request, response);
                    return;
                }

                //check if this email already existed in the system
                if (userDAO.getUserByMail(userMail) != null) {
                    mess = "This email have already been used!";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/register.jsp").forward(request, response);
                    return;
                }

                //check if this Moblie already existed in the system
                if (userDAO.getUserByMobile(userMobile) != null) {
                    mess = "The phone number is already been used";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/register.jsp").forward(request, response);
                    return;
                }

                //check if the moblie is in right fomat and length
                String moblieRegex = "(09|03|07|08|05)+([0-9]{8})";
                if (!userMobile.matches(moblieRegex) || userMobile.length() != 10) {
                    mess = "The phone number is invalid";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/register.jsp").forward(request, response);
                    return;
                }

                //convert gender to booolean type
                if (txtGender.equalsIgnoreCase("Male")) {
                    gender = true;
                } else {
                    gender = false;
                }

                //setup information and add to the database
                addUser.setUserName(userName);
                addUser.setPassword(password);
                addUser.setUserMobile(userMobile);
                addUser.setUserMail(userMail);
                addUser.setGender(gender);
                userDAO.addUser(addUser);

                SystemEmail se = new SystemEmail();
                //create confirm account link
                String confirmLink = "http://localhost:8080/QuizPracticingSystem"
                        + "/register?service=confirmAccount&userMail="
                        + userMail;
                se.sendEmail(userMail, "Confirm Your Account", confirmLink);
                mess = "An confirm mail have been sent to your email address!";
                request.setAttribute("mess", mess);
                request.getRequestDispatcher("login/register.jsp").forward(request, response);
            }

            //change status for user account
            if (service.equalsIgnoreCase("confirmAccount")) {
                UserDAO userDAO = new UserDAOImpl();
                String userMail = request.getParameter("userMail");
                User user = userDAO.getUserByMail(userMail);
                userDAO.changeStatus(user.getUserId(), true);
                request.setAttribute("mess", "Your account have been confirmed, please login!");
                request.getRequestDispatcher("login/login.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
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
