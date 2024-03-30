package controller;

import bean.User;
import dao.UserDAO;
import dao.impl.UserDAOImpl;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UserProfileController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Function user profile: allow the new user to update personal
     * information
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            String service = request.getParameter("service");
            UserDAO userDAO = new UserDAOImpl();
            //update user information
            if (service.equalsIgnoreCase("editProfile")) {
                String mess = "";
                User currUser = (User) request.getSession().getAttribute("currUser");
                String userName = request.getParameter("userName").trim();
                String userMobile = request.getParameter("userMobile").trim();
                String txtGender = request.getParameter("gender").trim();
                boolean gender;

                //check if any input is blank
                if (userName.length() == 0 || userMobile.length() == 0
                        || txtGender.length() == 0) {
                    mess = "You have to input all information!";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/editProfile.jsp")
                            .forward(request, response);
                    return;
                }

                //check max length
                if (userName.length() > 63 || userMobile.length() > 10) {
                    mess = "Your input have reached max length!";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/editProfile.jsp")
                            .forward(request, response);
                    return;
                }

                //check if this Moblie already existed in the system
                if (userDAO.getUserByMobile(userMobile) != null
                        && !userMobile.equals(userDAO.getUserById(currUser.getUserId()).getUserMobile())) {
                    mess = "The phone number is already been used";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/editProfile.jsp")
                            .forward(request, response);
                    return;
                }

                //check if the moblie is in right fomat and length
                String moblieRegex = "(09|03|07|08|05)+([0-9]{8})";
                if (!userMobile.matches(moblieRegex) || userMobile.length() != 10) {
                    mess = "The phone number is invalid";
                    request.setAttribute("mess", mess);
                    request.getRequestDispatcher("login/editProfile.jsp")
                            .forward(request, response);
                    return;
                }

                //convert gender to booolean type
                if (txtGender.equalsIgnoreCase("Male")) {
                    gender = true;
                } else {
                    gender = false;
                }
                currUser.setUserName(userName);
                currUser.setUserMobile(userMobile);
                currUser.setGender(gender);
                userDAO.updateUser(currUser);
                request.setAttribute("currUser",
                        userDAO.getUserById(currUser.getUserId()));
                request.getRequestDispatcher("login/userProfile.jsp").
                        forward(request, response);
            }

            //upload or change profile image
            if (service.equalsIgnoreCase("uploadImage")) {
                User currUser = (User) request.getSession().getAttribute("currUser");
                String filename = null;
                try {
                    // Create a factory for disk-based file items
                    DiskFileItemFactory factory = new DiskFileItemFactory();
                    ServletContext servletContext = this.getServletConfig().getServletContext();
                    File repository = (File) servletContext.getAttribute("jakarta.servlet.context.tempdir");
                    factory.setRepository(repository);
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    List<FileItem> items = null;
                    Iterator<FileItem> iter = items.iterator();
                    HashMap<String, String> fields = new HashMap<>();
                    while (iter.hasNext()) {
                        FileItem item = iter.next();
                        if (item.isFormField()) {
                            fields.put(item.getFieldName(), item.getString());
                            String name = item.getFieldName();
                            String value = item.getString();
                            System.out.println(name + " " + value);
                        } else {
                            filename = "u_" + currUser.getUserId() + "_" + item.getName();
                            if (filename == null || filename.equals("")) {
                                break;
                            } else {
                                Path path = Paths.get(filename);
                                String storePath = servletContext.getRealPath("/upload");
                                File uploadFile = new File(storePath + "/" + path.getFileName());
                                if (uploadFile.canRead()) {
                                    uploadFile.delete();
                                }
                                item.write(uploadFile);
                            }
                            out.println(filename);
                        }
                    }
                } catch (FileUploadException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
                currUser.setProfilePic(filename);
                userDAO.updateUser(currUser);
                request.getSession().setAttribute("currUser", userDAO.getUserById(currUser.getUserId()));
                response.sendRedirect("login/userProfile.jsp");
            }
        } catch (Exception ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
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
