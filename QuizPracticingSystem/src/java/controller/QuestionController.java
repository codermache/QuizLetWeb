
package controller;

import bean.Answer;
import bean.Dimension;
import bean.Lesson;
import bean.Question;
import bean.QuestionManage;
import bean.Subject;
import bean.User;
import bean.UserRole;
import dao.AnswerDAO;
import dao.DimensionDAO;
import dao.LessonDAO;
import dao.QuestionDAO;
import dao.SubjectDAO;
import dao.impl.AnswerDAOImpl;
import dao.impl.DimensionDAOImpl;
import dao.impl.LessonDAOImpl;
import dao.impl.QuestionDAOImpl;
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

public class QuestionController extends HttpServlet {

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
            QuestionDAO questionInterface = new QuestionDAOImpl();
            /**
             * Service: Search Question by Content
             */
            if (service.equalsIgnoreCase("searchQuestionByContent")) {
                /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, or not admin/expert, redirect to index */
                if ((currUser == null) || (currRole == null)
                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
                    sendDispatcher(request, response, "error.jsp");
                } /* Else: get questions by content and redirect to page*/ else {
                    String content = request.getParameter("content").trim();
                    ArrayList<QuestionManage> listQuestionManage = new ArrayList<>();
                    if (content.length() == 0) {
                        listQuestionManage = questionInterface.getQuestionByContent(null);
                    } else {
                        listQuestionManage = questionInterface.getQuestionByContent(content);
                    }
                    request.setAttribute("listQuestionManage", listQuestionManage);
                    request.getRequestDispatcher("jsp/questionList.jsp").forward(request, response);
                }
            }

            /**
             * Service: filter Question by subjectId, dimensionId, lessonId
             */
            if (service.equalsIgnoreCase("filterQuestion")) {
                /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, or not admin/expert, redirect to index */
                if ((currUser == null) || (currRole == null)
                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
                    sendDispatcher(request, response, "error.jsp");
                } /* Else: get questions filter by subject,lesson,dimension and redirect to page*/ else {
                    int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                    int lessonId = Integer.parseInt(request.getParameter("lessonId"));
                    int dimensionId = Integer.parseInt(request.getParameter("dimensionId"));
                    ArrayList<QuestionManage> listQuestionManage = questionInterface.getQuestionManage(subjectId, dimensionId, lessonId);
                    request.setAttribute("listQuestionManage", listQuestionManage);
                    request.getRequestDispatcher("jsp/questionList.jsp").forward(request, response);
                }
            }

            /**
             * Service: get all Subject, Dimension, Lesson Information
             */
            if (service.equalsIgnoreCase("getFilterInformation")) {
                String message = (String) request.getAttribute("message");
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                DimensionDAO dimensionDAO = new DimensionDAOImpl();
                LessonDAO lessonDAO = new LessonDAOImpl();
                ArrayList<Subject> listSubject = subjectDAO.getAllSubjects();
                ArrayList<Dimension> listDimension = dimensionDAO.getAllDimension();
                ArrayList<Lesson> listLesson = lessonDAO.getAllLessons();
                request.getSession().setAttribute("listFilterSubject", listSubject);
                request.getSession().setAttribute("listFilterDimension", listDimension);
                request.getSession().setAttribute("listFilterLesson", listLesson);
                request.getRequestDispatcher("jsp/questionList.jsp").forward(request, response);
                if (message != null) {
                    request.setAttribute("message", message);
                }
            }

            /**
             * Service question: add new question, answer to database
             */
            if (service.equalsIgnoreCase("addQuestion")) {
                /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, or not admin/expert, redirect to index */
                if ((currUser == null) || (currRole == null)
                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
                    sendDispatcher(request, response, "error.jsp");
                } else {
                    /* Else: get the Question detail  */
 /* Get parameters from jsp */
                    int subjectId = Integer.parseInt(request.getParameter("subject"));
                    int dimensionId = Integer.parseInt(request.getParameter("dimension"));
                    int lessonId = Integer.parseInt(request.getParameter("lesson"));
                    boolean status = request.getParameter("questionStatus").equals("1");
                    String content = (String) request.getParameter("content").trim();
                    String media = (String) request.getParameter("media").trim();
                    String explanation = (String) request.getParameter("explanation").trim();
                    //get parameters answer contents
                    String trueAnswer = (String) request.getParameter("trueAnswer").trim();
                    String wrongAnswer1 = (String) request.getParameter("wrongAnswer1").trim();
                    String wrongAnswer2 = (String) request.getParameter("wrongAnswer2").trim();
                    String wrongAnswer3 = (String) request.getParameter("wrongAnswer3").trim();
                    AnswerDAO answerDAO = new AnswerDAOImpl();
                    QuestionDAO questionDAO = new QuestionDAOImpl();
                    String message = "";
                    String color = "red";
                    if (content == null || content.length() == 0) {
                        message = "content can not be empty";
                    } else if (content.length() > 1023 || media.length() > 255) {
                        message = "content is too long";
                    } else if (content.length() > 1023) {
                        message = "explanation is too long";
                    } else if (subjectId == 0) {
                        message = "subject can not be empty";
                    } else if (dimensionId == 0) {
                        message = "dimension can not be empty";
                    } else if (lessonId == 0) {
                        message = "lesson can not be empty";
                    } else {
                        /* Add new question */
                        Question question = new Question(0, subjectId, dimensionId, lessonId, content, media, explanation, status);
                        int check = questionDAO.addQuestion(question);
                        if (check > 0) {
                            color = "green";
                            message = "Add question successfully.";
                            int questionId = questionDAO.getQuestionIdCreated(question);
                            if (trueAnswer == null || trueAnswer.length() == 0) {
                                message = "content can not be empty";
                            } else if (wrongAnswer1 == null || wrongAnswer1.length() == 0) {
                                message = "content can not be empty";
                            } else if (trueAnswer.length() > 1023 || wrongAnswer1.length() > 1023
                                    || wrongAnswer2.length() > 1023 || wrongAnswer3.length() > 1023) {
                                message = "content is too long";
                            } else {
                                /* Add new answer */
                                answerDAO.addAnswer(new Answer(0, questionId, trueAnswer, true, true));
                                answerDAO.addAnswer(new Answer(0, questionId, wrongAnswer1, false, true));
                                answerDAO.addAnswer(new Answer(0, questionId, wrongAnswer2, false, true));
                                answerDAO.addAnswer(new Answer(0, questionId, wrongAnswer3, false, true));
                            }
                        } else {
                            message = "Add question failed.";
                        }
                    }
                    request.setAttribute("questionColor", color);
                    request.setAttribute("questionMessage", message);
                    sendDispatcher(request, response, "jsp/questionDetail.jsp");
                }
            }

            /**
             * Service: get edit question information
             */
            if (service.equalsIgnoreCase("editQuestion")) {
                /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, or not admin/expert, redirect to index */
                if ((currUser == null) || (currRole == null)
                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
                    sendDispatcher(request, response, "error.jsp");
                } else {
                    int questionId = Integer.parseInt(request.getParameter("questionId"));
                    String type = request.getParameter("type");
                    QuestionDAO questionDAO = new QuestionDAOImpl();
                    if (type.equalsIgnoreCase("update")) {
                        SubjectDAO subjectDAO = new SubjectDAOImpl();
                        DimensionDAO dimensionDAO = new DimensionDAOImpl();
                        LessonDAO lessonDAO = new LessonDAOImpl();
                        AnswerDAO answerDAO = new AnswerDAOImpl();
                        Question updateQuestion = questionDAO.getQuestionById(questionId);
                        ArrayList<Subject> subjectList = subjectDAO.getAllSubjects();
                        ArrayList<Dimension> dimensionList = dimensionDAO.getAllDimension();
                        ArrayList<Lesson> lessonList = lessonDAO.getAllLessons();
                        ArrayList<Answer> answerList = answerDAO.getAnswersByQuenstionId(questionId);
                        ArrayList<Answer> wrongAnswer = new ArrayList<>();
                        Answer trueAnswer = new Answer();
                        for (Answer answer : answerList) {
                            if (answer.isIsCorrect()) {
                                trueAnswer = answer;
                            } else {
                                wrongAnswer.add(answer);
                            }
                        }

                        request.setAttribute("trueAnswer", trueAnswer);
                        request.setAttribute("wrongAnswer", wrongAnswer);
                        request.setAttribute("listSubject", subjectList);
                        request.setAttribute("listDimension", dimensionList);
                        request.setAttribute("listLesson", lessonList);
                        request.setAttribute("listAnswer", answerList);
                        request.setAttribute("updateQuestion", updateQuestion);
                        request.getRequestDispatcher("jsp/updateQuestion.jsp").forward(request, response);
                    }
                }
            }

            /**
             * Service: update edit Question information
             */
            if (service.equalsIgnoreCase("updateQuestionInformation")) {
                /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, or not admin/expert, redirect to index */
                if ((currUser == null) || (currRole == null)
                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
                    sendDispatcher(request, response, "error.jsp");
                } else {
                    int updateQuestionId = Integer.parseInt(request.getParameter("updateQuestionId"));
                    int subjectId = Integer.parseInt(request.getParameter("subject"));
                    int dimensionId = Integer.parseInt(request.getParameter("dimension"));
                    int lessonId = Integer.parseInt(request.getParameter("lesson"));
                    boolean status = request.getParameter("questionStatus").equals("1");
                    String content = (String) request.getParameter("content").trim();
                    String media = (String) request.getParameter("media").trim();
                    String explanation = (String) request.getParameter("explanation").trim();
                    String trueAnswerContent = (String) request.getParameter("trueAnswer").trim();
                    String wrongAnswer1Content = (String) request.getParameter("wrongAnswer1").trim();
                    String wrongAnswer2Content = (String) request.getParameter("wrongAnswer2").trim();
                    String wrongAnswer3Content = (String) request.getParameter("wrongAnswer3").trim();
                    int trueAnswerId = Integer.parseInt(request.getParameter("trueAnswerId"));
                    int wrongAnswer1Id = Integer.parseInt(request.getParameter("wrongAnswer1Id"));
                    int wrongAnswer2Id = Integer.parseInt(request.getParameter("wrongAnswer2Id"));
                    int wrongAnswer3Id = Integer.parseInt(request.getParameter("wrongAnswer3Id"));
                    AnswerDAO answerDAO = new AnswerDAOImpl();
                    String message = "";
                    String color = "red";
                    QuestionDAO questionDAO = new QuestionDAOImpl();
                    Question updateQuestion = questionDAO.getQuestionById(updateQuestionId);
                    if (content.length() == 0) {
                        request.setAttribute("message", "You have to enter question content");
                        request.getRequestDispatcher("quizController?service=getQuestionDetailsInformation")
                                .forward(request, response);
                    } else if (subjectId == 0) {
                        message = "subject can not be empty";
                    } else if (dimensionId == 0) {
                        message = "dimension can not be empty";
                    } else if (lessonId == 0) {
                        message = "lesson can not be empty";
                    } else if (trueAnswerContent == null || trueAnswerContent.length() == 0) {
                        message = "content can not be empty";
                    } else if (wrongAnswer1Content == null || wrongAnswer1Content.length() == 0) {
                        message = "content can not be empty";
                    } else if (trueAnswerContent.length() > 1023 || wrongAnswer1Content.length() > 1023
                            || wrongAnswer2Content.length() > 1023 || wrongAnswer3Content.length() > 1023) {
                        message = "content is too long";
                    } else {
                        /* update answer */
                        Answer trueAnswer = answerDAO.getAnswersById(trueAnswerId);
                        trueAnswer.setAnswerContent(trueAnswerContent);
                        Answer wrongAnswer1 = answerDAO.getAnswersById(wrongAnswer1Id);
                        wrongAnswer1.setAnswerContent(wrongAnswer1Content);
                        Answer wrongAnswer2 = answerDAO.getAnswersById(wrongAnswer2Id);
                        wrongAnswer2.setAnswerContent(wrongAnswer2Content);
                        Answer wrongAnswer3 = answerDAO.getAnswersById(wrongAnswer3Id);
                        wrongAnswer3.setAnswerContent(wrongAnswer3Content);
                        answerDAO.updateAnswer(trueAnswerId, trueAnswer);
                        answerDAO.updateAnswer(trueAnswerId, wrongAnswer1);
                        answerDAO.updateAnswer(trueAnswerId, wrongAnswer2);
                        answerDAO.updateAnswer(trueAnswerId, wrongAnswer3);
                    }
                    updateQuestion.setSubjectId(subjectId);
                    updateQuestion.setDimensionId(dimensionId);
                    updateQuestion.setLessonId(lessonId);
                    updateQuestion.setContent(content);
                    updateQuestion.setMedia(media);
                    updateQuestion.setStatus(status);
                    updateQuestion.setExplanation(explanation);
                    questionDAO.editQuestion(updateQuestion.getQuestionId(), updateQuestion);
                    request.setAttribute("message", "Update successfully!!");
                    request.getRequestDispatcher("quizController?service=getQuestionDetailsInformation")
                            .forward(request, response);
                }
            }

            /**
             * Service: get Question Details Information
             */
            if (service.equalsIgnoreCase("getQuestionDetailsInformation")) {
                String message = (String) request.getAttribute("message");
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                DimensionDAO dimensionDAO = new DimensionDAOImpl();
                LessonDAO lessonDAO = new LessonDAOImpl();
                ArrayList<Subject> listSubject = subjectDAO.getAllSubjects();
                ArrayList<Dimension> listDimension = dimensionDAO.getAllDimension();
                ArrayList<Lesson> listLesson = lessonDAO.getAllLessons();
                request.getSession().setAttribute("listSubject", listSubject);
                request.getSession().setAttribute("listDimension", listDimension);
                request.getSession().setAttribute("listLesson", listLesson);
                request.getRequestDispatcher("jsp/questionList.jsp").forward(request, response);
                if (message != null) {
                    request.setAttribute("message", message);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, ex);
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
