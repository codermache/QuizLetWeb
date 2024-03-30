
package controller;

import bean.*;
import dao.DimensionTypeDAO;
import dao.LessonTypeDAO;
import dao.PostCateDAO;
import dao.QuizLevelDAO;
import dao.SubjectCateDAO;
import dao.TestTypeDAO;
import dao.UserRoleDAO;
import dao.impl.DimensionTypeDAOImpl;
import dao.impl.LessonTypeDAOImpl;
import dao.impl.PostCateDAOImpl;
import dao.impl.QuizLevelDAOImpl;
import dao.impl.SubjectCateDAOImpl;
import dao.impl.TestTypeDAOImpl;
import dao.impl.UserRoleDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SystemSettingController extends HttpServlet {

    /**
     * Function Setting List: Display all system setting Function Setting
     * Function Setting Detail: Allow admin to edit or add new system settings
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
            String service = request.getParameter("service").trim();
            //declare all DAO
            UserRoleDAO userRoleDAO = new UserRoleDAOImpl();
            PostCateDAO postCateDAO = new PostCateDAOImpl();
            SubjectCateDAO subjectCateDAO = new SubjectCateDAOImpl();
            TestTypeDAO testTypeDAO = new TestTypeDAOImpl();
            QuizLevelDAO quizLevelDAO = new QuizLevelDAOImpl();
            LessonTypeDAO lessonTypeDAO = new LessonTypeDAOImpl();
            DimensionTypeDAO dimensionTypeDAO = new DimensionTypeDAOImpl();

            /**
             * get information to display in the setting list page
             */
            if (service.equalsIgnoreCase("getInformation")) {
                //get all user's roles
                ArrayList<UserRole> userRoleList = userRoleDAO.getAllStatusUserRole();
                //get all post's categories
                ArrayList<PostCate> postCateList = postCateDAO.getAllStatusPostCates();
                //get all subject's categories
                ArrayList<SubjectCate> subjectCateList = subjectCateDAO.getAllStatusSubjectCates();
                //get all test's type
                ArrayList<TestType> testTypeList = testTypeDAO.getAllStatusTestTypes();
                //get all quiz's level
                ArrayList<QuizLevel> quizLevelList = quizLevelDAO.getAllStatusQuizLevel();
                //get all lesson's type
                ArrayList<LessonType> lessonTypeList = lessonTypeDAO.getAllStatusLessonType();
                //get all dimesion's type
                ArrayList<DimensionType> dimensionTypeList = dimensionTypeDAO.getAllStatusDimensionTypes();
                String message = request.getParameter("message");
                if (message != null) {//if there is a message
                    request.setAttribute("message", message);
                }
                request.setAttribute("userRoleList", userRoleList);
                request.setAttribute("postCateList", postCateList);
                request.setAttribute("subjectCateList", subjectCateList);
                request.setAttribute("testTypeList", testTypeList);
                request.setAttribute("quizLevelList", quizLevelList);
                request.setAttribute("lessonTypeList", lessonTypeList);
                request.setAttribute("dimensionTypeList", dimensionTypeList);
                request.getRequestDispatcher("jsp/settingList.jsp").forward(request, response);
            }

            /**
             * Filter setting list by type of system setting.
             */
            if (service.equalsIgnoreCase("filter")) {
                String field = request.getParameter("field").trim();
                if (field.equalsIgnoreCase("userRole")) { // if admin  want to get all user role list
                    ArrayList<UserRole> userRoleList = userRoleDAO.getAllStatusUserRole();
                    request.setAttribute("userRoleList", userRoleList);
                } else if (field.equalsIgnoreCase("postCate")) {// if admin only want to get all post category list
                    ArrayList<PostCate> postCateList = postCateDAO.getAllStatusPostCates();
                    request.setAttribute("postCateList", postCateList);
                } else if (field.equalsIgnoreCase("subjectCate")) {// if admin only want to get all subject category list
                    ArrayList<SubjectCate> subjectCateList = subjectCateDAO.getAllStatusSubjectCates();
                    request.setAttribute("subjectCateList", subjectCateList);
                } else if (field.equalsIgnoreCase("testType")) {// if admin only want to get all test type list
                    ArrayList<TestType> testTypeList = testTypeDAO.getAllStatusTestTypes();
                    request.setAttribute("testTypeList", testTypeList);
                } else if (field.equalsIgnoreCase("quizLevel")) {// if admin only want to get all quiz level list
                    ArrayList<QuizLevel> quizLevelList = quizLevelDAO.getAllStatusQuizLevel();
                    request.setAttribute("quizLevelList", quizLevelList);
                } else if (field.equalsIgnoreCase("lessonType")) {// if admin only want to get all lesson type list
                    ArrayList<LessonType> lessonTypeList = lessonTypeDAO.getAllStatusLessonType();
                    request.setAttribute("lessonTypeList", lessonTypeList);
                } else if (field.equalsIgnoreCase("dimensionType")) {// if admin only want to get all dimension type list
                    ArrayList<DimensionType> dimensionTypeList = dimensionTypeDAO.getAllStatusDimensionTypes();
                    request.setAttribute("dimensionTypeList", dimensionTypeList);
                } else if (field.equalsIgnoreCase("all")) {
                    response.sendRedirect("SystemSettingController?service=getInformation");
                }                
                request.getRequestDispatcher("jsp/settingList.jsp").forward(request, response);
            }

            /**
             * Get information to edit a system setting
             */
            if (service.equalsIgnoreCase("getEditInformation")) {
                String field = request.getParameter("field").trim();
                int objectId = Integer.parseInt(request.getParameter("id").trim());
                String message = request.getParameter("message");
                String fieldName = ""; //save the field name
                String objectName = ""; //save settings's name
                boolean objectStatus = false; //save settings's status
                
                if (message != null) {
                    request.setAttribute("message", message);
                }
                
                if (field.equalsIgnoreCase("userRole")) { // if admin  want to edit a user role setting
                    UserRole editObject = userRoleDAO.getUserRoleById(objectId);
                    objectName = editObject.getUserRoleName();
                    objectStatus = editObject.isStatus();
                    fieldName = "User Role";
                } else if (field.equalsIgnoreCase("postCate")) {// if admin want to edit post category setting
                    PostCate editObject = postCateDAO.getPostCateById(objectId);
                    objectName = editObject.getPostCateName();
                    objectStatus = editObject.isStatus();
                    fieldName = "Post Category";
                } else if (field.equalsIgnoreCase("subjectCate")) {// if admin want to edit subject category setting
                    SubjectCate editObject = subjectCateDAO.getSubjectCateById(objectId);
                    objectName = editObject.getSubjectCateName();
                    objectStatus = editObject.isStatus();
                    fieldName = "Subject Category";
                } else if (field.equalsIgnoreCase("testType")) {// if admin want to edit test type setting
                    TestType editObject = testTypeDAO.getTestTypeById(objectId);
                    objectName = editObject.getTestTypeName();
                    objectStatus = editObject.isStatus();
                    fieldName = "Test Type";
                } else if (field.equalsIgnoreCase("quizLevel")) {// if admin want to edit quiz level setting
                    QuizLevel editObject = quizLevelDAO.getQuizLevelById(objectId);
                    objectName = editObject.getQuizLevelName();
                    objectStatus = editObject.isStatus();
                    fieldName = "Quiz Level";
                } else if (field.equalsIgnoreCase("lessonType")) {// if admin want to edit lesson type setting
                    LessonType editObject = lessonTypeDAO.getLessonTypeById(objectId);
                    objectName = editObject.getLessonTypeName();
                    objectStatus = editObject.isStatus();
                    fieldName = "Lesson Type";
                } else if (field.equalsIgnoreCase("dimensionType")) {// if admin want to edit dimension type list
                    DimensionType editObject = dimensionTypeDAO.getDimensionTypeById(objectId);
                    objectName = editObject.getDimensionTypeName();
                    objectStatus = editObject.isStatus();
                    fieldName = "Dimension Type";
                }
                
                request.setAttribute("objectId", objectId);
                request.setAttribute("objectName", objectName);
                request.setAttribute("objectStatus", objectStatus);
                request.setAttribute("field", field);
                request.setAttribute("fieldName", fieldName);
                request.getRequestDispatcher("jsp/settingDetail.jsp").forward(request, response);
            }

            /**
             * Edit a system setting
             */
            if (service.equalsIgnoreCase("editSetting")) {
                String field = request.getParameter("settingType");
                int objectId = Integer.parseInt(request.getParameter("objectId"));
                String settingName = request.getParameter("settingName");
                boolean settingStatus = Boolean.parseBoolean(request.getParameter("settingStatus").trim());
                int checkEdit = 0;

                //check for blank input
                if (field == null || settingName == null) {
                    request.getRequestDispatcher("SystemSettingController?service=getInformation&message=You have to input all field")
                            .forward(request, response);
                    return;
                }
                field = field.trim();
                settingName = settingName.trim();
                //check for blank input
                if (field.length() == 0 || settingName.length() == 0) {
                    request.getRequestDispatcher("SystemSettingController?service=getInformation&message=You have to input all field")
                            .forward(request, response);
                    return;
                }

                //check input length
                if (settingName.length() > 100) {
                    request.setAttribute("messge", "Your setting name is too long");
                    request.getRequestDispatcher("SystemSettingController?field="
                            + field + "&id=" + objectId)
                            .forward(request, response);
                }
                
                if (field.equalsIgnoreCase("userRole")) { // if admin  want to edit a user role setting
                    UserRole editObject = userRoleDAO.getUserRoleById(objectId);
                    //setup edited information
                    editObject.setUserRoleName(settingName);
                    editObject.setStatus(settingStatus);
                    //update information to database 
                    checkEdit = userRoleDAO.editRole(editObject);
                } else if (field.equalsIgnoreCase("postCate")) {// if admin want to edit post category setting
                    PostCate editObject = postCateDAO.getPostCateById(objectId);
                    //setup edited information
                    editObject.setPostCateName(settingName);
                    editObject.setStatus(settingStatus);
                    //update information to database 
                    checkEdit = postCateDAO.updatePostCate(editObject);
                } else if (field.equalsIgnoreCase("subjectCate")) {// if admin want to edit subject category setting
                    SubjectCate editObject = subjectCateDAO.getSubjectCateById(objectId);
                    //setup edited information
                    editObject.setStatus(settingStatus);
                    editObject.setSubjectCateName(settingName);
                    //update information to database 
                    checkEdit = subjectCateDAO.updateSubjectCate(editObject);
                } else if (field.equalsIgnoreCase("testType")) {// if admin want to edit test type setting
                    TestType editObject = testTypeDAO.getTestTypeById(objectId);
                    //setup edited information
                    editObject.setStatus(settingStatus);
                    editObject.setTestTypeName(settingName);
                    //update information to database 
                    checkEdit = testTypeDAO.updateTestType(editObject);
                } else if (field.equalsIgnoreCase("quizLevel")) {// if admin want to edit quiz level setting
                    QuizLevel editObject = quizLevelDAO.getQuizLevelById(objectId);
                    //setup edited information
                    editObject.setStatus(settingStatus);
                    editObject.setQuizLevelName(settingName);
                    //update information to database 
                    checkEdit = quizLevelDAO.editQuizLevel(editObject);
                } else if (field.equalsIgnoreCase("lessonType")) {// if admin want to edit lesson type setting
                    LessonType editObject = lessonTypeDAO.getLessonTypeById(objectId);
                    //setup edited information
                    editObject.setStatus(settingStatus);
                    editObject.setLessonTypeName(settingName);
                    //update information to database 
                    checkEdit = lessonTypeDAO.updateLessonType(editObject);
                } else if (field.equalsIgnoreCase("dimensionType")) {// if admin want to edit dimension type list
                    DimensionType editObject = dimensionTypeDAO.getDimensionTypeById(objectId);
                    //setup edited information
                    editObject.setStatus(settingStatus);
                    editObject.setDimensionTypeName(settingName);
                    //update information to database 
                    checkEdit = dimensionTypeDAO.updateDimensionType(editObject);
                }
                
                if (checkEdit == 0) {
                    request.setAttribute("message", "Edit setting not successfull !!!");
                } else {
                    request.setAttribute("message", "Edit setting successfull !!!");
                }
                
                request.getRequestDispatcher("SystemSettingController?service=getInformation").forward(request, response);
            }
            
            /**
             * Add a system setting
             */
            if (service.equalsIgnoreCase("addSetting")) {
                String field = request.getParameter("settingType");
                String settingName = request.getParameter("settingName");
                boolean settingStatus = Boolean.parseBoolean(request.getParameter("settingStatus").trim());
                int checkAdd = 0;

                //check for blank input
                if (field == null || settingName == null) {
                    request.setAttribute("messge", "You have to input all field");
                    request.getRequestDispatcher("SystemSettingController?service=getInformation&message=You have to input all field").forward(request, response);
                }
                
                field = field.trim();
                settingName = settingName.trim();
                //check for blank input
                if (field.length() == 0 || settingName.length() == 0) {
                    request.getRequestDispatcher("SystemSettingController?service=getInformation&message=You have to input all field").forward(request, response);
                }

                //check input length
                if (settingName.length() > 100) {
                    request.getRequestDispatcher("jsp/settingList.jsp").forward(request, response);
                }
                
                if (field.equalsIgnoreCase("userRole")) { // if admin  want to edit a user role setting
                    UserRole addObject = new UserRole();
                    //setup new setting information
                    addObject.setUserRoleName(settingName);
                    addObject.setStatus(settingStatus);
                    //update information to database 
                    checkAdd = userRoleDAO.addRole(addObject);
                } else if (field.equalsIgnoreCase("postCate")) {// if admin want to edit post category setting
                    PostCate addObject = new PostCate();
                    //setup new setting information
                    addObject.setPostCateName(settingName);
                    addObject.setStatus(settingStatus);
                    //update information to database 
                    checkAdd = postCateDAO.addPostCate(addObject);
                } else if (field.equalsIgnoreCase("subjectCate")) {// if admin want to edit subject category setting
                    SubjectCate addObject = new SubjectCate();
                    //setup new setting information
                    addObject.setStatus(settingStatus);
                    addObject.setSubjectCateName(settingName);
                    //update information to database 
                    checkAdd = subjectCateDAO.addSubjectCate(addObject);
                } else if (field.equalsIgnoreCase("testType")) {// if admin want to edit test type setting
                    TestType addObject = new TestType();
                    //setup new setting information
                    addObject.setStatus(settingStatus);
                    addObject.setTestTypeName(settingName);
                    //update information to database 
                    checkAdd = testTypeDAO.addTestType(addObject);
                } else if (field.equalsIgnoreCase("quizLevel")) {// if admin want to edit quiz level setting
                    QuizLevel addObject = new QuizLevel();
                    //setup new setting information
                    addObject.setStatus(settingStatus);
                    addObject.setQuizLevelName(settingName);
                    //update information to database 
                    checkAdd = quizLevelDAO.addQuizLevel(addObject);
                } else if (field.equalsIgnoreCase("lessonType")) {// if admin want to edit lesson type setting
                    LessonType addObject = new LessonType();
                    //setup new setting information
                    addObject.setStatus(settingStatus);
                    addObject.setLessonTypeName(settingName);
                    //update information to database 
                    checkAdd = lessonTypeDAO.addLessonType(addObject);
                } else if (field.equalsIgnoreCase("dimensionType")) {// if admin want to edit dimension type list
                    DimensionType addObject = new DimensionType();
                    //setup new setting information
                    addObject.setStatus(settingStatus);
                    addObject.setDimensionTypeName(settingName);
                    //update information to database 
                    checkAdd = dimensionTypeDAO.addDimensionType(addObject);
                }
                
                if (checkAdd == 0) {
                    request.setAttribute("message", "Add setting not successfull !!!");
                } else {
                    request.setAttribute("message", "Add setting successfull !!!");
                }
                
                request.getRequestDispatcher("SystemSettingController?service=getInformation").forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(SystemSettingController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMess", ex.toString());
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
