
package controller;

import bean.Dimension;
import bean.DimensionType;
import bean.PricePackage;
import bean.Subject;
import bean.SubjectCate;
import bean.User;
import bean.UserRole;
import dao.DimensionDAO;
import dao.DimensionTypeDAO;
import dao.PricePackageDAO;
import dao.RegistrationDAO;
import dao.SubjectCateDAO;
import dao.impl.SubjectDAOImpl;
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
import dao.SubjectDAO;
import dao.impl.DimensionDAOImpl;
import dao.impl.DimensionTypeDAOImpl;
import dao.impl.PricePackageDAOImpl;
import dao.impl.RegistrationDAOImpl;
import dao.impl.SubjectCateDAOImpl;

public class SubjectController extends HttpServlet {

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
            /* Get service and initialise the subjectDAO */
            String service = request.getParameter("service");
            SubjectDAO subjectDAO = new SubjectDAOImpl();
            SubjectCateDAO subjectCateDAO = new SubjectCateDAOImpl();
            DimensionTypeDAO dimensionTypeDAO = new DimensionTypeDAOImpl();
            DimensionDAO dimensionDAO = new DimensionDAOImpl();
            PricePackageDAO pricePackageDAO = new PricePackageDAOImpl();
            RegistrationDAO registrationDAO = new RegistrationDAOImpl();

//            /**
//             * Service course content list: for admin and expert to check the
//             * proper subject, depends on the role
//             */
//            if (service.equalsIgnoreCase("courseContentList")) {
//                /* Get user and role on session scope */
//                User currUser = (User) request.getSession().getAttribute("currUser");
//                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
//                /* If user is not logged in, redirect to index */
//                if ((currUser == null) || (currRole == null)) {
//                    sendDispatcher(request, response, "index.jsp");
//                } else if (currRole.getUserRoleName().equalsIgnoreCase("expert")) {
//                    /* Role is expert: get the assigned subjects */
//                    /* Get assigned list */
//                    ArrayList<Subject> featuredSubjectList = subjectDAO.getSubjectsAssigned(currUser.getUserId());
//                    /* Set attribute and send it to course Content page */
//                    request.setAttribute("courseContentSubjectList", featuredSubjectList);
//                    sendDispatcher(request, response, "jsp/courseContentList.jsp");
//                } else if (currRole.getUserRoleName().equalsIgnoreCase("admin")) {
//                    /* Role is admin: load all subject */
//                    /* Get all subject */
//                    ArrayList<Subject> allSubject = subjectDAO.getTrueSubjectsPaging(2);
//                    /* Set attribute and send it to course content page */
//                    request.setAttribute("courseContentSubjectList", allSubject);
//                    sendDispatcher(request, response, "jsp/courseContentList.jsp");
//                } else {
//                    /* If the user is logged in but not admin or expert, send back to index.jsp */
//                    sendDispatcher(request, response, "index.jsp");
//                }
//            }

//            /**
//             * Service course content detail: for admin and expert to check the
//             * subject detail and edit it
//             */
//            if (service.equalsIgnoreCase("courseContentDetail")) {
//                /* Get user and role on session scope */
//                User currUser = (User) request.getSession().getAttribute("currUser");
//                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
//                /* If user is not logged in, or not admin/expert, redirect to index */
//                if ((currUser == null) || (currRole == null)
//                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
//                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
//                    sendDispatcher(request, response, "error.jsp");
//                } /* Else: get the subject with the set id and redirect to courseContentDetail page*/ else {
//                    int subjectId = Integer.parseInt(request.getParameter("subjectId"));
//                    Subject courseContent = subjectDAO.getSubjectbyId(subjectId);
//                    request.setAttribute("subject", courseContent);
//                    ArrayList<SubjectCate> categoryList = subjectCateDAO.getSubjectCateBySubject(subjectId);
//                    request.setAttribute("categoryList", categoryList);
//                    ArrayList<SubjectCate> categoryRemainList = subjectCateDAO.getRemainSubjectCateBySubject(subjectId);
//                    request.setAttribute("categoryRemainList", categoryRemainList);
//                    ArrayList<DimensionType> dimensionTypes = dimensionTypeDAO.getAllDimensionTypes();
//                    request.setAttribute("dimensionTypes", dimensionTypes);
//                    sendDispatcher(request, response, "jsp/courseContentDetail.jsp");
//                }
//            }

//            /**
//             * Service course content detail: update subject
//             */
//            if (service.equalsIgnoreCase("updateSubject")) {
//                /* Get user and role on session scope */
//                User currUser = (User) request.getSession().getAttribute("currUser");
//                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
//                /* If user is not logged in, or not admin/expert, redirect to index */
//                if ((currUser == null) || (currRole == null)
//                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
//                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
//                    sendDispatcher(request, response, "error.jsp");
//                } else {
//                    /* Else: get the subject detail  */
//                    /* Get parameters from jsp */
//                    int subjectId = Integer.parseInt(request.getParameter("subjectId").trim());
//                    String subjectName = request.getParameter("subjectName").trim();
//                    String subjectDescription = request.getParameter("subjectDescription").trim();
//                    String subjectThumbnail = request.getParameter("subjectThumbnail");
//                    boolean isFeatured = request.getParameter("isFeaturedSubject") != null;
//                    boolean status = request.getParameter("subjectStatus").equals("1");
//                    String[] updatedSubjectCateId = request.getParameterValues("subjectCategory");
//                    /* Check boundaries */
//                    String message = "";
//                    String color = "red";
//                    if (subjectName == null || subjectName.length() == 0) {
//                        message = "SubjectName can not be empty";
//                    } else if (subjectName.length() > 255) {
//                        message = "Subject Name is too long";
//                    } else if (subjectDescription == null || subjectDescription.length() == 0) {
//                        message = "Subject Description can not be empty";
//                    } else if (subjectDescription.length() > 1023) {
//                        message = "Subject Description is too long";
//                    } else {
//                        color = "green";
//                        /* Perform the updates on subject basic data and categories */
//                        Subject updateSubject = new Subject(subjectId, subjectName, subjectDescription, subjectThumbnail, isFeatured, status);
//                        int updateNumber = 0;
//                        updateNumber += subjectDAO.updateSubjectBasic(subjectId, updateSubject);
//                        String[] currentCategorySubject = subjectCateDAO.getSubjectCateIdBySubject(subjectId);
//                        /* Terminates: eliminates all the unchanged tuples */
//                        if (updatedSubjectCateId != null) { /* If the updated subject category list is not null */
//                            for (int i = 0; i < updatedSubjectCateId.length; i++) {
//                                for (int j = 0; j < currentCategorySubject.length; j++) {
//                                    if (updatedSubjectCateId[i].equals(currentCategorySubject[j])) {
//                                        updatedSubjectCateId[i]= "-1";
//                                        currentCategorySubject[j] = "-1";
//                                    }
//                                }
//                            }
//                            /* Add categories in the updated list */
//                            for (String categoryId : updatedSubjectCateId) {
//                                if (!categoryId.equals("-1"))
//                                updateNumber += subjectCateDAO.addCategorySubject(subjectId, Integer.parseInt(categoryId));
//                            }
//                            /* Delete categories in the current list */
//                            for (String categoryId : currentCategorySubject) {
//                                if (!categoryId.equals("-1"))
//                                updateNumber += subjectCateDAO.deteleCategorySubject(subjectId, Integer.parseInt(categoryId));
//                            }
//                        } else { /* If the updated subject category list is null: delete all category */
//                            for (String categoryId : currentCategorySubject) {
//                                    if (!categoryId.equals("-1"))
//                                    updateNumber += subjectCateDAO.deteleCategorySubject(subjectId, Integer.parseInt(categoryId));
//                                }
//                        }
////                         subjectCateDAO.updateSubjectContentCate(subjectId, categoryId);
////                        int updateNumber = basicUpdate + categoryUpdate;
//                        message = "Performed " + updateNumber + " update(s) successfully.";
//                    }
//                    /* Get the needed lists and redirect to the courseContentJsp */
//                    Subject courseContent = subjectDAO.getSubjectbyId(subjectId);
//                    request.setAttribute("subject", courseContent);
//                    ArrayList<SubjectCate> categoryList = subjectCateDAO.getSubjectCateBySubject(subjectId);
//                    request.setAttribute("categoryList", categoryList);
//                    ArrayList<SubjectCate> categoryRemainList = subjectCateDAO.getRemainSubjectCateBySubject(subjectId);
//                    request.setAttribute("categoryRemainList", categoryRemainList);
//                    ArrayList<DimensionType> dimensionTypes = dimensionTypeDAO.getAllDimensionTypes();
//                    request.setAttribute("dimensionTypes", dimensionTypes);
//                    request.setAttribute("detailColor", color);
//                    request.setAttribute("detailMessage", message);
//                    request.setAttribute("displayTab", "overview");
//                    sendDispatcher(request, response, "jsp/courseContentDetail.jsp");
//                }
//            }
//
//            /**
//             * Service course content detail: update subject dimension (edit and
//             * delete)
//             */
//            if (service.equalsIgnoreCase("updateDimension")) {
//                /* Get user and role on session scope */
//                User currUser = (User) request.getSession().getAttribute("currUser");
//                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
//                /* If user is not logged in, or not admin/expert, redirect to index */
//                if ((currUser == null) || (currRole == null)
//                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
//                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
//                    sendDispatcher(request, response, "error.jsp");
//                } else {
//                    int subjectId = Integer.parseInt(request.getParameter("subjectId").trim());
//                    String message = "";
//                    String color = "red";
//                    int dimensionId = Integer.parseInt(request.getParameter("dimensionId").trim());
//                    /* Check if the sub-service is update or delete */
//                    String subService = request.getParameter("subService").trim();
//                    if (subService.equalsIgnoreCase("Delete")) {
//                        /* Perform deletion on subject dimension */
//                        int check = dimensionDAO.deleteDimension(dimensionId);
//                        if (check > 0) {
//                            color = "green";
//                            message = "Delete dimension successfully.";
//                        } else {
//                            message = "Delete dimension failed.";
//                        }
//                    } else if (subService.equalsIgnoreCase("Update")) {
//                        /* Get parameters from jsp */
//                        int dimensionTypeId = Integer.parseInt(request.getParameter("dimensionType").trim());
//                        String dimensionName = request.getParameter("dimensionName").trim();
//                        String description = request.getParameter("description").trim();
//                        /* Check boundaries */
//
//                        if (dimensionName == null || dimensionName.length() == 0) {
//                            message = "Dimension Name can not be empty";
//                        } else if (dimensionName.length() > 255) {
//                            message = "Dimension Name is too long";
//                        } else if (description.length() > 511) {
//                            message = "Dimension Description is too long";
//                        } else {
//
//                            /* Perform the updates on subject dimension */
//                            Dimension updateDimension = new Dimension(dimensionId, subjectId, dimensionTypeId, "", dimensionName, description, true);
//                            int check = dimensionDAO.editDimension(dimensionId, updateDimension);
//                            if (check > 0) {
//                                color = "green";
//                                message = "Update dimension successfully.";
//                            } else {
//                                message = "Update dimension failed.";
//                            }
//                        }
//                    }
//
//                    /* Get the needed lists and redirect to the courseContentJsp */
//                    Subject courseContent = subjectDAO.getSubjectbyId(subjectId);
//                    request.setAttribute("subject", courseContent);
//                    ArrayList<SubjectCate> categoryList = subjectCateDAO.getSubjectCateBySubject(subjectId);
//                    request.setAttribute("categoryList", categoryList);
//                    ArrayList<SubjectCate> categoryRemainList = subjectCateDAO.getRemainSubjectCateBySubject(subjectId);
//                    request.setAttribute("categoryRemainList", categoryRemainList);
//                    ArrayList<DimensionType> dimensionTypes = dimensionTypeDAO.getAllDimensionTypes();
//                    request.setAttribute("dimensionTypes", dimensionTypes);
//                    request.setAttribute("dimensionColor", color);
//                    request.setAttribute("dimensionMessage", message);
//                    request.setAttribute("displayTab", "dimension");
//                    sendDispatcher(request, response, "jsp/courseContentDetail.jsp");
//                }
//            }

            /**
             * Service course content detail: add new subject dimension
             */
            if (service.equalsIgnoreCase("addDimension")) {
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
                    int dimensionTypeId = Integer.parseInt(request.getParameter("dimensionType").trim());
                    String dimensionName = request.getParameter("dimensionName").trim();
                    String description = request.getParameter("description").trim();
                    /* Check boundaries */
                    if (dimensionName == null || dimensionName.length() == 0) {
                        message = "Dimension Name can not be empty";
                    } else if (dimensionName.length() > 255) {
                        message = "Dimension Name is too long";
                    } else if (description.length() > 511) {
                        message = "Dimension Description is too long";
                    } else {
                        /* Add new subject dimension */
                        Dimension updateDimension = new Dimension(0, subjectId, dimensionTypeId, "", dimensionName, description, true);
                        int check = dimensionDAO.addDimension(updateDimension);
                        if (check > 0) {
                            color = "green";
                            message = "Add dimension successfully.";
                        } else {
                            message = "Add dimension failed.";
                        }
                    }

                    /* Get the needed lists and redirect to the courseContentJsp */
                    Subject courseContent = subjectDAO.getSubjectbyId(subjectId);
                    request.setAttribute("subject", courseContent);
                    ArrayList<SubjectCate> categoryList = subjectCateDAO.getSubjectCateBySubject(subjectId);
                    request.setAttribute("categoryList", categoryList);
                    ArrayList<SubjectCate> categoryRemainList = subjectCateDAO.getRemainSubjectCateBySubject(subjectId);
                    request.setAttribute("categoryRemainList", categoryRemainList);
                    ArrayList<DimensionType> dimensionTypes = dimensionTypeDAO.getAllDimensionTypes();
                    request.setAttribute("dimensionTypes", dimensionTypes);
                    request.setAttribute("dimensionColor", color);
                    request.setAttribute("dimensionMessage", message);
                    request.setAttribute("displayTab", "dimension");
                    sendDispatcher(request, response, "jsp/courseContentDetail.jsp");
                }
            }

            /**
             * Service subject : subject detail
             */
            if (service.equalsIgnoreCase("subjectDetail")) {
                int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                Subject subject = subjectDAO.getSubjectbyId(subjectId);
                request.setAttribute("subject", subject);
                ArrayList<PricePackage> pricePackage = pricePackageDAO.getAllPricePackagesBySubject(subjectId);
                request.setAttribute("pricePackage", pricePackage);

                request.getRequestDispatcher("jsp/subjectDetail.jsp").forward(request, response);
            }
            /**
             * Service subject : add subject
             */
            if (service.equalsIgnoreCase("addSubject")) {
                /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, or not admin/expert, redirect to index */
                if ((currUser == null) || (currRole == null)
                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
                    sendDispatcher(request, response, "error.jsp");
                } else {
                    int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                    Subject subject = subjectDAO.getSubjectbyId(subjectId);
//                    ArrayList<SubjectCate> categoryList = subjectCateDAO.getSubjectCateBySubject(subjectId);
//                    request.setAttribute("categoryList", categoryList);
                    request.getRequestDispatcher("jsp/addSubject.jsp").forward(request, response);
                }
            }
            /**
             * Service subject : My Registration
             */
            if (service.equalsIgnoreCase("myRegistration")) {
                /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, redirect to index */
                if ((currUser == null) || (currRole == null)) {
                    sendDispatcher(request, response, "index.jsp");
                } else {
                    ArrayList<Subject> registrationList = registrationDAO.getRegistedSubjectbyUserId(currUser.getUserId());
                    request.setAttribute("registrationList", registrationList);

                    request.getRequestDispatcher("jsp/myRegistration.jsp").forward(request, response);

                }
            }
        } catch (Exception ex) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMess", ex.toString());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /* Forward the request to the destination, catch any unexpected exceptions and log it */
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
