
package controller;

import bean.DimensionType;
import bean.Lesson;
import bean.LessonType;
import bean.Subject;
import bean.SubjectCate;
import bean.User;
import bean.UserRole;
import dao.DimensionTypeDAO;
import dao.LessonDAO;
import dao.LessonTypeDAO;
import dao.SubjectCateDAO;
import dao.SubjectDAO;
import dao.impl.DimensionTypeDAOImpl;
import dao.impl.LessonDAOImpl;
import dao.impl.LessonTypeDAOImpl;
import dao.impl.SubjectCateDAOImpl;
import dao.impl.SubjectDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "LessonController", urlPatterns = {"/lessonController"})
public class LessonController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            LessonDAO lessonInterface = new LessonDAOImpl();
            SubjectDAO subjectInterface = new SubjectDAOImpl();
            LessonTypeDAO lessonTypeInterface = new LessonTypeDAOImpl();
            
            /**
             * Service: get update lesson information
             */
            if (service.equalsIgnoreCase("updateLesson")) {
                /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, or not admin/expert, redirect to index */
                if ((currUser == null) || (currRole == null)
                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
                    sendDispatcher(request, response, "error.jsp");
                } else {
                    int lessonId = Integer.parseInt(request.getParameter("lessonId"));
                    int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                    String type = request.getParameter("type");
                    if (type.equalsIgnoreCase("update")) {
                        Lesson updateLesson = lessonInterface.getLessonById(lessonId);
                        ArrayList<Lesson> lessonList = lessonInterface.getAllLessons();
                        request.setAttribute("listLesson", lessonList);
                        ArrayList<LessonType> listLessonType = lessonTypeInterface.getAllLessonType();
                        request.setAttribute("listLessonType", listLessonType);
                        request.setAttribute("subjectId", subjectId);
                        request.setAttribute("updateLesson", updateLesson);
                        request.getRequestDispatcher("jsp/lessonDetails.jsp").forward(request, response);
                    }
                }
            }
            
            /**
             * Service: update Lesson information
             */
            if (service.equalsIgnoreCase("updateLessonInformation")) {
                /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, or not admin/expert, redirect to index */
                if ((currUser == null) || (currRole == null)
                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
                    sendDispatcher(request, response, "error.jsp");
                } else {
                    int updateLessonId = Integer.parseInt(request.getParameter("updateLessonId"));
                    int lessonOrder = Integer.parseInt(request.getParameter("lessonOrder"));
                    int lessonTypeId = Integer.parseInt(request.getParameter("lessonTypeId"));
                    boolean status = request.getParameter("lessonStatus").equals("1");
                    String content = (String) request.getParameter("content").trim();
                    String videoLink = (String) request.getParameter("videoLink").trim();
                    String lessonName = (String) request.getParameter("lessonName").trim();
                    int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                    String message = "";
                    String color = "red";
                    Lesson updatedLesson = lessonInterface.getLessonById(updateLessonId);
                    if (content.length() == 0 || lessonName.length() == 0) {
                        ArrayList<Lesson> lessonList = lessonInterface.getAllLessons();
                        request.setAttribute("listLesson", lessonList);
                        ArrayList<LessonType> listLessonType = lessonTypeInterface.getAllLessonType();
                        request.setAttribute("listLessonType", listLessonType);
                        request.setAttribute("subjectId", subjectId);
                        request.setAttribute("updateLesson", updatedLesson);
                        request.setAttribute("message", "You have to enter this");
                        request.getRequestDispatcher("jsp/lessonDetails.jsp")
                                .forward(request, response);
                    } else if (lessonTypeId == 0) {
                        message = "Lesson type can not be empty";
                    } else if (lessonOrder == 0) {
                        message = "Lesson Order can not be empty";
                    } else {
                        /* Perform the updates on subject lesson */
                        updatedLesson.setLessonName(lessonName);
                        updatedLesson.setLessonOrder(lessonOrder);
                        updatedLesson.setLessonTypeId(lessonTypeId);
                        updatedLesson.setVideoLink(videoLink);
                        updatedLesson.setContent(content);
                        updatedLesson.setStatus(status);
                        lessonInterface.updateLesson(updatedLesson.getLessonId(), updatedLesson);
                        Lesson updateLesson = lessonInterface.getLessonById(updatedLesson.getLessonId());
                        request.setAttribute("updateLesson", updateLesson);
                    }
                    
                    request.setAttribute("message", "Update successfully!!");
                    request.getRequestDispatcher("courseContentDetail?service=viewDetail&subjectId="+subjectId)
                            .forward(request, response);
                }
            }

            /**
             * Service course content detail: add new subject lessons
             */
            if (service.equalsIgnoreCase("addLesson")) {
                SubjectCateDAO subjectCateDAO = new SubjectCateDAOImpl();
                DimensionTypeDAO dimensionTypeDAO = new DimensionTypeDAOImpl();
                /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, or not admin/expert, redirect to index */
                if ((currUser == null) || (currRole == null)
                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
                    sendDispatcher(request, response, "error.jsp");
                } else {
                    int subjectId = Integer.parseInt(request.getParameter("subjectId").trim());
                    String message = "";
                    String color = "red";
                    /* Get parameters from jsp */

                    String lessonName = request.getParameter("lessonName").trim();
                    int lessonOrder = Integer.parseInt(request.getParameter("lessonOrder").trim());
                    int lessonTypeId = Integer.parseInt(request.getParameter("lessonTypeId").trim());
                    boolean status = request.getParameter("lessonStatus").equals("1");
                    /* Check boundaries */
                    if (lessonName == null || lessonName.length() == 0) {
                        message = "Lesson Name can not be empty";
                    } else if (lessonName.length() > 255) {
                        message = "Lesson Name is too long";
                    } else {
                        /* Add new subject lesson */
                        Lesson updateLesson = new Lesson(0, subjectId, lessonName, lessonOrder, lessonTypeId, "", "", status, "");
                        int check = lessonInterface.addLesson(updateLesson);
                        if (check > 0) {
                            color = "green";
                            message = "Add lesson successfully.";
                        } else {
                            message = "Add lesson failed.";
                        }
                    }

                    /* Get the needed lists and redirect to the courseContentJsp */
                    Subject courseContent = subjectInterface.getSubjectbyId(subjectId);
                    request.setAttribute("subject", courseContent);
                    ArrayList<SubjectCate> categoryList = subjectCateDAO.getSubjectCateBySubject(subjectId);
                    request.setAttribute("categoryList", categoryList);
                    ArrayList<SubjectCate> categoryRemainList = subjectCateDAO.getRemainSubjectCateBySubject(subjectId);
                    request.setAttribute("categoryRemainList", categoryRemainList);
                    ArrayList<LessonType> lessonTypes = lessonTypeInterface.getAllLessonType();
                    request.setAttribute("lessonTypes", lessonTypes);
                    ArrayList<Lesson> listLesson = lessonInterface.getAllLessonBySubjectId(subjectId);
                    request.setAttribute("listLesson", listLesson);
                    ArrayList<DimensionType> dimensionTypes = dimensionTypeDAO.getAllDimensionTypes();
                    request.setAttribute("dimensionTypes", dimensionTypes);
                    request.setAttribute("lessonColor", color);
                    request.setAttribute("lessonMessage", message);
                    request.setAttribute("displayTab", "lesson");
                    sendDispatcher(request, response, "jsp/courseContentDetail.jsp");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LessonController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMess", ex.toString());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    public void sendDispatcher(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.forward(request, response);

        } catch (ServletException | IOException ex) {
            Logger.getLogger(SubjectController.class
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
