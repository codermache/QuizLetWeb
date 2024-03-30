
package controller;

import bean.Answer;
import bean.CustomerQuiz;
import bean.Dimension;
import bean.Lesson;
import bean.Question;
import bean.QuestionManage;
import bean.QuestionQuizHandle;
import bean.Quiz;
import bean.QuizLevel;
import bean.QuizQuizHandle;
import bean.Subject;
import bean.TestType;
import bean.User;
import bean.UserRole;
import dao.AnswerDAO;
import dao.impl.CustomerQuizDAOImpl;
import dao.impl.QuestionDAOImpl;
import dao.impl.QuestionQuizHandleDAOImpl;
import dao.impl.QuizDAOImpl;
import dao.impl.QuizQuizHandleDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.CustomerQuizDAO;
import dao.DimensionDAO;
import dao.LessonDAO;
import dao.QuestionDAO;
import dao.QuestionQuizHandleDAO;
import dao.QuizDAO;
import dao.QuizLevelDAO;
import dao.QuizQuizHandleDAO;
import dao.RegistrationDAO;
import dao.SubjectDAO;
import dao.TestTypeDAO;
import dao.impl.AnswerDAOImpl;
import dao.impl.DimensionDAOImpl;
import dao.impl.LessonDAOImpl;
import dao.impl.QuizLevelDAOImpl;
import dao.impl.RegistrationDAOImpl;
import dao.impl.SubjectDAOImpl;
import dao.impl.TestTypeDAOImpl;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.RequestDispatcher;

public class QuizController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    static final int IMAGE_MEDIA_TYPE = 1;
    static final int VIDEO_MEDIA_TYPE = 2;
    static final int EXAM_TYPE_ID = 1;
    static final int PRACTICE_TYPE_ID = 2;
    static final int DEFAULT_PAGE = 1;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            QuizQuizHandleDAO quizQHInterface = new QuizQuizHandleDAOImpl();
            QuestionQuizHandleDAO questionQHInterface = new QuestionQuizHandleDAOImpl();
            QuestionDAO questionInterface = new QuestionDAOImpl();
            QuizDAO quizInterface = new QuizDAOImpl();
            String service = request.getParameter("service");

            /**
             * Service quiz entrance: create a session for doing quiz
             */
            if (service.equalsIgnoreCase("quizEntrance")) {
                HttpSession session = request.getSession();
                int quizId = Integer.parseInt(request.getParameter("quizId"));
                if (session.getAttribute("doingQuiz") == null) {
                    User user = (User) session.getAttribute("currUser");
                    ArrayList<Question> questionList = questionInterface.getQuestionByQuizId(quizId);
                    QuizQuizHandle doingQuiz = quizQHInterface.generateQuiz(questionList, quizId, user);
                    session.setAttribute("doingQuiz", doingQuiz);
                }
                response.sendRedirect("quizController?service=quizHandle&quizId=" + quizId + "&questionNumber=1");
            }

            /**
             * Service quiz handle: handle with all situation happen in a quiz
             * taking session
             */
            if (service.equalsIgnoreCase("quizHandle")) {
                //get quiz from session or generate new quiz (not yet have userId)
                HttpSession session = request.getSession(true);
                Object object = session.getAttribute("doingQuiz");
                if (object != null) {
                    QuizQuizHandle doingQuiz = (QuizQuizHandle) object;
                    int quizId = doingQuiz.getQuiz().getQuizId();
                    request.setAttribute("quizId", quizId);
                    int quizType = doingQuiz.getQuiz()
                            .getTestTypeId();
                    request.setAttribute("quizType", quizType);

                    ArrayList<QuestionQuizHandle> quiz = doingQuiz.getQuestions();
                    request.setAttribute("quiz", quiz);

                    //get question id
                    int questionNumber;
                    if (request.getParameter("questionNumber") == null) {
                        questionNumber = DEFAULT_PAGE;
                    } else {
                        questionNumber = Integer.parseInt(request.getParameter("questionNumber"));
                    }
                    QuestionQuizHandle questionQH = doingQuiz.getQuestionByNumber(questionNumber);

                    String media = questionQH.getQuestion().getMedia();
                    if (media != null) {
                        int mediaType = VIDEO_MEDIA_TYPE;
                        String[] imageExtensions = {".jpg", ".gif", ".jpeg", ".jfif", ".pjpeg", ".png", ".pjps"};
                        for (String extension : imageExtensions) {
                            if (media.contains(extension)) {
                                mediaType = IMAGE_MEDIA_TYPE;
                            }
                        }
                        request.setAttribute("mediaType", mediaType);
                    }
                    //send being taking question's information
                    request.setAttribute("questionQH", questionQH);
                    //true answer of question
                    Answer trueAnswer = questionQHInterface.getRightAnswer(questionQH);
                    request.setAttribute("trueAnswer", trueAnswer.getAnswerContent());
                    //number of question in this quiz
                    request.setAttribute("questionNumber", questionNumber);

                    //Mark this question 
                    String marked = request.getParameter("marked");
                    if (marked != null && marked.equalsIgnoreCase("yes")) {
                        questionQHInterface.markQuestion(questionQH);
                    }

                    //send quiz infomation
                    //Number of answered question in quiz
                    int answeredQuestionNumber = quizQHInterface.getAnsweredQuestion(doingQuiz);
                    request.setAttribute("answeredNumber", answeredQuestionNumber);
                    //length of this quiz
                    request.setAttribute("duration", doingQuiz.getQuiz().getQuizDuration());
                    //Next quiz, Previous quiz, Score Exams handle
                    String action = request.getParameter("action");
                    String autoSubmit = request.getParameter("autoSubmit");
                    if (action != null) {
                        //information of recently submit question include questionNumber in this quiz and answer id in database
                        String answerTakenIdRaw = request.getParameter("answerTakenId");
                        String questionTakenNumberRaw = request.getParameter("questionTakenNumber");

                        //set answerId of this question
                        if (answerTakenIdRaw != null && questionTakenNumberRaw != null) {
                            int answerTakenId = Integer.parseInt(answerTakenIdRaw);
                            questionQH.setAnsweredId(answerTakenId);
                        }

                        //prepare for next action
                        //previous question
                        if (action.equalsIgnoreCase("Previous Question")) {
                            response.sendRedirect("quizController?service=quizHandle&quizId=" + quizId + "&questionNumber=" + --questionNumber);

                            //next question    
                        } else if (action.equalsIgnoreCase("Next Question")) {
                            response.sendRedirect("quizController?service=quizHandle&quizId=" + quizId + "&questionNumber=" + ++questionNumber);

                            //score exam
                        } else if ((action.charAt(0) != 'P') && (action.charAt(0) != 'N')
                                && (action.charAt(0) != 'S') && (action.charAt(0) != 'E') && (action.charAt(0) != 'F')) {

                            response.sendRedirect("quizController?service=quizHandle&quizId=" + quizId + "&questionNumber=" + Integer.parseInt(action));
                        } else if (action.equalsIgnoreCase("Finish Exam")) {
                            int time = Integer.parseInt(request.getParameter("time"));
                            doingQuiz.setTime(time);
                            request.setAttribute("totalsecond", time);
                            response.sendRedirect("quizController?service=quizSummary");
                        }
                        request.setAttribute("doingQuiz", doingQuiz);
                    } else if (autoSubmit != null) {
                        String answerTakenIdRaw = request.getParameter("answerTakenId");
                        String questionTakenNumberRaw = request.getParameter("questionTakenNumber");

                        //set answerId of this question
                        if (answerTakenIdRaw != null && questionTakenNumberRaw != null) {
                            int answerTakenId = Integer.parseInt(answerTakenIdRaw);
                            questionQH.setAnsweredId(answerTakenId);
                        }

                        if (autoSubmit.equalsIgnoreCase("yes")) {
                            doingQuiz = (QuizQuizHandle) object;
                            int time = Integer.parseInt(request.getParameter("time"));
                            doingQuiz.setTime(time);
                            session.removeAttribute("doingQuiz");
                            CustomerQuizDAO customerQuizInterface = new CustomerQuizDAOImpl();
                            int latestTakeQuizId = customerQuizInterface.getLastAddedCustomerQuiz().getQuizTakeId();
                            //redirect user to review quiz page  
                            response.sendRedirect("quizController?service=quizReview&quizTakeId=" + latestTakeQuizId + "&questionNumber=1");
                            return;
                        }
                    } else {
                        request.getRequestDispatcher("quizhandle/quizHandle.jsp").forward(request, response);
                    }

                } else {
                    response.sendRedirect("homeController");
                }
            }
            /**
             * Service quiz summary: review quiz progress while taking a quiz
             */

            if (service.equalsIgnoreCase("quizSummary")) {
                HttpSession session = request.getSession();
                QuizQuizHandle doingQuiz = null;
                Object object = session.getAttribute("doingQuiz");
                //co roi
                if (object != null) {
                    doingQuiz = (QuizQuizHandle) object;
                    request.setAttribute("doingQuiz", doingQuiz);
                    request.setAttribute("quizType", doingQuiz.getQuiz().getTestTypeId());
                    request.setAttribute("total", doingQuiz.getTime());
                    int answeredQuestionNumber = quizQHInterface.getAnsweredQuestion(doingQuiz);
                    request.setAttribute("answeredNumber", answeredQuestionNumber);
                    request.getRequestDispatcher("quizhandle/quizSummary.jsp").forward(request, response);
                } else {
                    response.sendRedirect("homeController");
                }
            }

            if (service.equalsIgnoreCase("submit")) {
                HttpSession session = request.getSession(true);
                QuizQuizHandle doingQuiz = null;
                Object object = session.getAttribute("doingQuiz");
                int latestTakeQuizId = 0;
                if (object != null) {
                    doingQuiz = (QuizQuizHandle) object;
                    int time = Integer.parseInt(request.getParameter("time"));
                    doingQuiz.setTime(time);
                    session.removeAttribute("doingQuiz");
                    CustomerQuizDAO customerQuizInterface = new CustomerQuizDAOImpl();
                    latestTakeQuizId = customerQuizInterface.getLastAddedCustomerQuiz().getQuizTakeId();
                    //redirect user to review quiz page  
                    response.sendRedirect("quizController?service=quizReview&quizTakeId=" + latestTakeQuizId + "&questionNumber=1");
                    return;
                }
            }
            /**
             * Service quiz review: review the quiz after taking it
             */
            if (service.equalsIgnoreCase("quizReview")) {
                //prepare quiz information
                int quizTakeId = Integer.parseInt(request.getParameter("quizTakeId"));
                request.setAttribute("quizTakeId", quizTakeId);
                CustomerQuizDAO customerQuizInterface = new CustomerQuizDAOImpl();
                QuizDAO quizDAOInterface = new QuizDAOImpl();
                QuizQuizHandle doingQuiz = quizQHInterface.getReviewQuiz(quizTakeId);
                ArrayList<QuestionQuizHandle> quizReview = doingQuiz.getQuestions();
                Quiz quiz = quizDAOInterface.getQuizByQuizTakeId(quizTakeId);
                request.setAttribute("quizReview", quizReview);
                request.setAttribute("quizSize", quizReview.size());
                CustomerQuiz customerQuiz = customerQuizInterface.getLastAddedCustomerQuiz();
                long startedAt = customerQuiz.getSubmitedAt().getTime() - customerQuiz.getTime() * 1000;;
                long submitedAt = customerQuiz.getSubmitedAt().getTime();
                Timestamp submitTime = new Timestamp(submitedAt);
                Timestamp startTime = new Timestamp(startedAt);
                String startedAtTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(startTime);
                String submitedAtTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(submitTime);
                request.setAttribute("submitedAt", submitedAtTime);
                request.setAttribute("startedAt", startedAtTime);
                request.setAttribute("score", customerQuiz.getScore());

                int questionNumber;
                if (request.getParameter("questionNumber") == null) {
                    questionNumber = DEFAULT_PAGE;
                } else {
                    questionNumber = Integer.parseInt(request.getParameter("questionNumber"));
                }
                QuestionQuizHandle questionQH = doingQuiz.getQuestionByNumber(questionNumber);
                String media = questionQH.getQuestion().getMedia();
                if (media != null) {
                    int mediaType = VIDEO_MEDIA_TYPE;
                    String[] imageExtensions = {".jpg", ".gif", ".jpeg", ".jfif", ".pjpeg", ".png", ".pjps"};
                    for (String extension : imageExtensions) {
                        if (media.contains(extension)) {
                            mediaType = IMAGE_MEDIA_TYPE;
                        }
                    }
                    request.setAttribute("mediaType", mediaType);
                }
                //send being reviewing question's information
                request.setAttribute("answered", questionQH.getAnsweredId());
                request.setAttribute("questionQH", questionQH);
                String questionContent = questionQH.getQuestion().getContent();
                request.setAttribute("questionContent", questionContent);
                //answer List of question
                ArrayList<Answer> answerList = questionQH.getAnswerList();
                request.setAttribute("answerList", answerList);
                //true answer of question
                Answer trueAnswer = questionQHInterface.getRightAnswer(questionQH);
                request.setAttribute("trueAnswer", trueAnswer.getAnswerContent());
                //number of question in this quiz
                request.setAttribute("questionNumber", questionNumber);
                //question id in database
                request.setAttribute("questionId", questionQH.getQuestion()
                        .getQuestionId());
                //explanation of this question
                request.setAttribute("explanation", questionQH.getQuestion()
                        .getExplanation());

                String action = request.getParameter("action");
                if (action != null) {

                    //prepare for next action
                    //previous question
                    if (action.equalsIgnoreCase("Previous Question")) {
                        response.sendRedirect("quizController?service=quizReview&quizTakeId=" + quizTakeId + "&questionNumber=" + --questionNumber);

                        //next question    
                    } else if (action.equalsIgnoreCase("Next Question")) {
                        response.sendRedirect("quizController?service=quizReview&quizTakeId=" + quizTakeId + "&questionNumber=" + ++questionNumber);

                        //score exam
                    } else if ((action.charAt(0) != 'P') && (action.charAt(0) != 'N')
                            && (action.charAt(0) != 'S')) {

                        response.sendRedirect("quizController?service=quizReview&quizTakeId=" + quizTakeId + "&questionNumber=" + Integer.parseInt(action));
                    } else if (action.equalsIgnoreCase("Finish Review")) {
                        response.sendRedirect("homeController");
                    }
                } else {
                    request.getRequestDispatcher("quizhandle/quizReview.jsp").forward(request, response);
                }
            }
            /**
             * Service simulation exam: view all exam quiz avaliable from the
             * subject customer had registered
             */
            if (service.equalsIgnoreCase("simulationExam")) {
                HttpSession session = request.getSession();
                QuizQuizHandle doingQuiz = (QuizQuizHandle) session.getAttribute("doingQuiz");
                if (doingQuiz != null) {
                    request.setAttribute("doingQuiz", doingQuiz);
                }
                RegistrationDAO IRegistration = new RegistrationDAOImpl();
                User currUser = (User) session.getAttribute("currUser");
                String subjectSearchIdRaw = request.getParameter("subjectSearchId");
                int subjectSearchId = 0;
                if (subjectSearchIdRaw != null && !subjectSearchIdRaw.equalsIgnoreCase("")) {
                    subjectSearchId = Integer.parseInt(subjectSearchIdRaw);
                    request.setAttribute("subjectSearchId", +subjectSearchId);
                }

                String searchQuizName = request.getParameter("subjectSearchName");
                ArrayList<Subject> subjectList = IRegistration.getRegistedSubject(currUser.getUserId());
                ArrayList<Quiz> simulationList = quizInterface.getAllSimulationQuizByUser(currUser.getUserId(), subjectSearchId, searchQuizName);

                request.setAttribute("subjectList", subjectList);
                request.setAttribute("simulationList", simulationList);
                request.getRequestDispatcher("quizhandle/simulationExam.jsp").forward(request, response);
            }

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
                } /* Else: get the subject with the set id and redirect to page*/ else {
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
                } /* Else: get the subject with the set id and redirect to page*/ else {
                    int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                    int lessonId = Integer.parseInt(request.getParameter("lessonId"));
                    int dimensionId = Integer.parseInt(request.getParameter("dimensionId"));
                    ArrayList<QuestionManage> listQuestionManage = questionInterface.getQuestionManage(subjectId, dimensionId, lessonId);
                    request.setAttribute("listQuestionManage", listQuestionManage);
                    request.getRequestDispatcher("jsp/questionList.jsp").forward(request, response);
//                out.println(questionManage.size());
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
                    } else if (content.length() > 1023 || media.length()>255) {
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

            /**
             * Service: update edit Question information
             */
            if (service.equalsIgnoreCase("updateQuestionInformation")) {
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
                }
                if (trueAnswerContent == null || trueAnswerContent.length() == 0) {
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
                    request.getRequestDispatcher("quizController?service=getQuizDetailInformation")
                            .forward(request, response);
                }
                // if the aren't any question that meet user requirement return message
                if (questionList.size() == 0) {
                    request.setAttribute("message", "There aren't any question that meet your requirement");
                    request.getRequestDispatcher("quizController?service=getQuizDetailInformation")
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
                request.getRequestDispatcher("quizController?service=getQuizDetailInformation")
                        .forward(request, response);
            }

        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
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
