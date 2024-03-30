
package controller;

import bean.Dimension;
import bean.DimensionType;
import bean.Question;
import bean.Quiz;
import bean.QuizLevel;
import bean.Subject;
import bean.TestType;
import bean.User;
import bean.UserRole;
import dao.CustomerQuizDAO;
import dao.DimensionDAO;
import dao.DimensionTypeDAO;
import dao.QuestionDAO;
import dao.QuizDAO;
import dao.QuizLevelDAO;
import dao.SubjectDAO;
import dao.TestTypeDAO;
import dao.impl.CustomerQuizDAOImpl;
import dao.impl.DimensionDAOImpl;
import dao.impl.DimensionTypeDAOImpl;
import dao.impl.QuestionDAOImpl;
import dao.impl.QuizDAOImpl;
import dao.impl.QuizLevelDAOImpl;
import dao.impl.SubjectDAOImpl;
import dao.impl.TestTypeDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class QuizListController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. Function Quiz List: allow the user to view all quiz in the
     * system Function Quiz Detail: allow the user to create new quiz Function
     * Quiz Update: allow the user to update the quiz information
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

            /**
             * Get information to display in the quizDetail page
             */
            if (service.equalsIgnoreCase("getQuizDetailInformation")) {
                User currUser = (User) request.getSession().getAttribute("currUser");
                String message = (String) request.getAttribute("message");
                String role = ((UserRole) request.getSession().getAttribute("role")).getUserRoleName();
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                QuizLevelDAO quizLevelDAO = new QuizLevelDAOImpl();
                TestTypeDAO testTypeDAO = new TestTypeDAOImpl();
                DimensionDAO dimensionDAO = new DimensionDAOImpl();
                ArrayList<Subject> subjectList = new ArrayList<>();
                ArrayList<QuizLevel> quizLevelList = quizLevelDAO.getAllQuizLevel();
                ArrayList<TestType> testTypeList = testTypeDAO.getAllTestTypes();
                ArrayList<Dimension> dimensionList = dimensionDAO.getAllDimension();
                if (role.equalsIgnoreCase("admin")) { //if user is a admin then get all subject
                    subjectList = subjectDAO.getAllSubjects();
                } else if (role.equalsIgnoreCase("expert")) { //if user is a expert then get all asigned subject
                    subjectList = subjectDAO.getSubjectsAssigned(currUser.getUserId());
                }
                request.setAttribute("subjectList", subjectList);
                request.setAttribute("quizLevelList", quizLevelList);
                request.setAttribute("testTypeList", testTypeList);
                request.setAttribute("dimensionTypeList", dimensionList);
                if (message != null) {
                    request.setAttribute("message", message);
                }
                request.getRequestDispatcher("jsp/quizDetail.jsp").forward(request, response);
            }

            /**
             * Get information from quizDetail to create quiz then add to the
             * database
             */
            if (service.equalsIgnoreCase("createQuiz")) {
                // get all parameter that user input
                String quizName = (String) request.getParameter("quizName").trim();
                int subjectId = Integer.parseInt(request.getParameter("subject"));
                int duration = Integer.parseInt(request.getParameter("duration")) * 60;
                int quizLevelId = Integer.parseInt(request.getParameter("examLevel"));
                int passRate = Integer.parseInt(request.getParameter("passRate"));
                int testTypeId = Integer.parseInt(request.getParameter("testType"));
                int numberOfQuestion = Integer.parseInt(request.getParameter("numbetOfQuestion"));
                int dimensionId = Integer.parseInt(request.getParameter("dimensionType"));
                String description = request.getParameter("description").trim();
                QuestionDAO questionDAO = new QuestionDAOImpl();
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                QuizDAO quizDAO = new QuizDAOImpl();
                Quiz createdQuiz = new Quiz();
                ArrayList<Question> questionList = questionDAO.getQuestionForCreateQuiz(numberOfQuestion, subjectId, dimensionId);
                //if quizNmae have yet been enter, return mesaage
                if (quizName.length() == 0) {
                    request.setAttribute("message", "You have to enter quiz name");
                    request.getRequestDispatcher("QuizListController?service=getQuizDetailInformation")
                            .forward(request, response);
                }
                // if the aren't any question that meet user requirement return message
                if (questionList.size() == 0) {
                    request.setAttribute("message", "There aren't any question that meet your requirement");
                    request.getRequestDispatcher("QuizListController?service=getQuizDetailInformation")
                            .forward(request, response);
                }
                //prepare quiz information to add to database
                createdQuiz.setSubject(subjectDAO.getSubjectbyId(subjectId));
                createdQuiz.setQuizName(quizName);
                createdQuiz.setQuizLevelId(quizLevelId);
                createdQuiz.setQuizDuration(duration);
                createdQuiz.setPassRate(passRate);
                createdQuiz.setTestTypeId(testTypeId);
                createdQuiz.setDescription(description);
                createdQuiz.setNumberQuestion(numberOfQuestion);
                createdQuiz.setDimensionTypeId(dimensionId);
                quizDAO.addQuiz(createdQuiz);//add quiz to the database
                int quizId = quizDAO.getQuizIdCreated(createdQuiz);
                // add all quiz's questions
                for (Question question : questionList) {
                    quizDAO.addQuizQuestion(quizId, question.getQuestionId());
                }
                request.setAttribute("message", "Add quiz successfull!!(" + questionList.size() + " questions)");
                request.getRequestDispatcher("QuizListController?service=getQuizDetailInformation")
                        .forward(request, response);
            }

            //Get information to display in the quizList page
            if (service.equalsIgnoreCase("getQuizListInformation")) {
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                TestTypeDAO testTypeDAO = new TestTypeDAOImpl();
                QuizDAO quizDAO = new QuizDAOImpl();
                ArrayList<Subject> subjectList = subjectDAO.getAllSubjects();
                ArrayList<TestType> testTypeList = testTypeDAO.getAllTestTypes();
                ArrayList<Quiz> quizList = quizDAO.getAllQuiz();
                String message = request.getParameter("message");
                request.getSession().setAttribute("subjectQuizList", subjectList);
                request.getSession().setAttribute("testTypeQuizList", testTypeList);
                request.setAttribute("quizQuizList", quizList);
                if (message != null) {
                    request.setAttribute("message", message);
                }
                request.getRequestDispatcher("jsp/quizList.jsp").forward(request, response);
            }

            //Get all quiz that have name contain search value
            if (service.equalsIgnoreCase("searchQuizByName")) {
                String quizName = request.getParameter("quizName");
                QuizDAO quizDAO = new QuizDAOImpl();
                ArrayList<Quiz> quizList = new ArrayList<>();
                if (quizName == null) {
                    //if user don't enter anything then get all quiz
                    quizList = quizDAO.getQuizByName(null);
                } else if (quizName.trim().length() == 0) {
                    //if user don't enter anything then get all quiz
                    quizList = quizDAO.getQuizByName(null);
                } else {
                    quizList = quizDAO.getQuizByName(quizName.trim());
                }
                request.setAttribute("quizQuizList", quizList);
                request.getRequestDispatcher("jsp/quizList.jsp").forward(request, response);
            }

            //Filter quiz according to user requirement
            if (service.equalsIgnoreCase("filterQuiz")) {
                int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                int testTypeId = Integer.parseInt(request.getParameter("testTypeId"));
                QuizDAO quizDAO = new QuizDAOImpl();
                //get all quiz that have the same subject and test type
                ArrayList<Quiz> quizList = quizDAO.getFilteredQuiz(subjectId, testTypeId);
                request.setAttribute("quizQuizList", quizList);
                request.getRequestDispatcher("jsp/quizList.jsp").forward(request, response);
            }

            //if quiz can be editted redirect user to update page or delete quiz from database 
            if (service.equalsIgnoreCase("editQuiz")) {
                int quizId = Integer.parseInt(request.getParameter("quizId"));
                String editTYpe = request.getParameter("type");
                CustomerQuizDAO customerQuizDAO = new CustomerQuizDAOImpl();
                QuizDAO quizDAO = new QuizDAOImpl();
                if (!customerQuizDAO.checkTeakedQuiz(quizId)) { // if this test haven't been taken then allow change
                    if (editTYpe.equalsIgnoreCase("update")) {//if admin want to edit quiz
                        //get information to display in the update page
                        SubjectDAO subjectDAO = new SubjectDAOImpl();
                        QuizLevelDAO quizLevelDAO = new QuizLevelDAOImpl();
                        TestTypeDAO testTypeDAO = new TestTypeDAOImpl();
                        DimensionTypeDAO dimensionTypeDAO = new DimensionTypeDAOImpl();
                        Quiz updateQuiz = quizDAO.getQuizById(quizId);
                        ArrayList<Subject> subjectList = subjectDAO.getAllSubjects();
                        ArrayList<QuizLevel> quizLevelList = quizLevelDAO.getAllQuizLevel();
                        ArrayList<TestType> testTypeList = testTypeDAO.getAllTestTypes();
                        ArrayList<DimensionType> dimensionTypeList = dimensionTypeDAO.getAllDimensionTypes();
                        request.setAttribute("subjectList", subjectList);
                        request.setAttribute("quizLevelList", quizLevelList);
                        request.setAttribute("testTypeList", testTypeList);
                        request.setAttribute("dimensionTypeList", dimensionTypeList);
                        request.setAttribute("updateQuiz", updateQuiz);
                        request.getRequestDispatcher("jsp/updateQuiz.jsp").forward(request, response);
                    } else if (editTYpe.equalsIgnoreCase("delete")) {//if admin want to delete quiz
                        quizDAO.removeQuizQuestion(quizId);//delete all quiz's questions
                        quizDAO.deleteQuiz(quizId);//delete quiz
                        request.setAttribute("message", "Update quiz successfull!!");
                        request.getRequestDispatcher("QuizListController?service=getQuizListInformation")
                                .forward(request, response);
                    }
                } else {// if this test have already been taken then don't allow change
                    ArrayList<Quiz> quizList = quizDAO.getAllQuiz();
                    request.setAttribute("quizQuizList", quizList);
                    request.setAttribute("message", "You can't change this quiz!");
                    request.getRequestDispatcher("jsp/quizList.jsp").forward(request, response);
                }

            }

            //edit quiz information then update to the database
            if (service.equalsIgnoreCase("updateQuizInformation")) {
                //get all parameter from updateQuiz page
                String quizName = (String) request.getParameter("quizName").trim();
                int updateQuizId = Integer.parseInt(request.getParameter("updateQuizId"));
                int subjectId = Integer.parseInt(request.getParameter("subject"));
                int duration = Integer.parseInt(request.getParameter("duration")) * 60;
                int quizLevelId = Integer.parseInt(request.getParameter("examLevel"));
                int passRate = Integer.parseInt(request.getParameter("passRate"));
                int testTypeId = Integer.parseInt(request.getParameter("testType"));
                int numberOfQuestion = Integer.parseInt(request.getParameter("numberOfQuestion"));
                int dimensionId = Integer.parseInt(request.getParameter("dimensionType"));
                String description = request.getParameter("description").trim();
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                QuizDAO quizDAO = new QuizDAOImpl();
                Quiz updateQuiz = quizDAO.getQuizById(updateQuizId);
                //if quizName have yet been enter, return mesaage
                if (quizName.length() == 0) {
                    request.setAttribute("message", "You have to enter quiz name");
                    request.getRequestDispatcher("QuizListController?service=getQuizListInformation")
                            .forward(request, response);
                }
                // setup quiz information to edit
                updateQuiz.setSubject(subjectDAO.getSubjectbyId(subjectId));
                updateQuiz.setQuizName(quizName);
                updateQuiz.setQuizLevelId(quizLevelId);
                updateQuiz.setQuizDuration(duration);
                updateQuiz.setPassRate(passRate);
                updateQuiz.setTestTypeId(testTypeId);
                updateQuiz.setDescription(description);
                updateQuiz.setNumberQuestion(numberOfQuestion);
                updateQuiz.setDimensionTypeId(dimensionId);
                quizDAO.editQuiz(updateQuiz.getQuizId(), updateQuiz);
                request.setAttribute("message", "Update quiz successfull!!");
                request.getRequestDispatcher("QuizListController?service=getQuizListInformation")
                        .forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(QuizListController.class.getName()).log(Level.SEVERE, null, ex);
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
