
package controller;

import bean.Answer;
import bean.Lesson;
import bean.Question;
import bean.Subject;
import bean.User;
import bean.UserRole;
import dao.AnswerDAO;
import dao.LessonDAO;
import dao.QuestionDAO;
import dao.SubjectDAO;
import dao.impl.AnswerDAOImpl;
import dao.impl.LessonDAOImpl;
import dao.impl.QuestionDAOImpl;
import dao.impl.SubjectDAOImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)

public class ImportQuestionController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * Import question: allow the user to import questions from .xml files, select
     * lesson and dimension, change any mistakes and add to the database
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

            /*Service is null, redirect user to index*/
            if (service == null) {
                sendDispatcher(request, response, "index.jsp");
            }
            /**
             * Service loadSubjectList: get the subjectList to load the page
             * questionImport.jsp
             */
            if ("loadSubjectList".equalsIgnoreCase(service)) {
                /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, or not admin/expert, redirect to index */
                if ((currUser == null) || (currRole == null)
                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
                    sendDispatcher(request, response, "error.jsp");
                } /* Else: get the subject list*/ else {
                    SubjectDAO subjectDAO = new SubjectDAOImpl();
                    /*Subject DAO*/
                    ArrayList<Subject> subjectList = null;
                    if (currRole.getUserRoleName().equalsIgnoreCase("admin")) {
                        subjectList = subjectDAO.getTrueAllSubjects();
                    }
                    if (currRole.getUserRoleName().equalsIgnoreCase("expert")) {
                        subjectList = subjectDAO.getSubjectsAssigned(currUser.getUserId());
                    }
                    request.setAttribute("subjectList", subjectList);
                    sendDispatcher(request, response, "jsp/questionImport.jsp");
                }
            }
            /**
             * Service upload question: get the information from file uploaded
             * and turn into list of question, redirect back to the jsp
             */
            if ("uploadQuestion".equalsIgnoreCase(service)) {
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                /*Subject DAO*/
                LessonDAO lessonDAO = new LessonDAOImpl();
                /*Lesson DAO*/
 /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, or not admin/expert, redirect to index */
                if ((currUser == null) || (currRole == null)
                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
                    sendDispatcher(request, response, "error.jsp");
                } /* Else: get the subject list*/ else {
                    ArrayList<Subject> subjectList = null;
                    if (currRole.getUserRoleName().equalsIgnoreCase("admin")) {
                        subjectList = subjectDAO.getTrueAllSubjects();
                    }
                    if (currRole.getUserRoleName().equalsIgnoreCase("expert")) {
                        subjectList = subjectDAO.getSubjectsAssigned(currUser.getUserId());
                    }
                    request.setAttribute("subjectList", subjectList);
                }
                String uploadFile = (String) request.getParameter("questionContent");
                ArrayList<Question> questionList = new ArrayList<>();
                int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                Subject subjectImport = subjectDAO.getSubjectbyId(subjectId);
                request.setAttribute("subjectImport", subjectImport);
                ArrayList<Lesson> lessonList = lessonDAO.getAllLessonBySubjectId(subjectId);
                request.setAttribute("lessonList", lessonList);

                Document doc = loadXMLFromString(uploadFile);
                doc.getDocumentElement().normalize();
                //System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
                NodeList nodeList = doc.getElementsByTagName("question");
                // nodeList is not iterable, so we are using for loop  
                for (int itr = 0; itr < nodeList.getLength(); itr++) {
                    Node node = nodeList.item(itr);
                    System.out.println("\nNode Name :" + node.getNodeName());
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        ArrayList<Answer> answerList = new ArrayList<>();
                        Element eElement = (Element) node;
                        Node content = eElement.getElementsByTagName("content").item(0);
                        Node explanation = eElement.getElementsByTagName("explanation").item(0);
                        Node trueAnswer = eElement.getElementsByTagName("trueAnswer").item(0);
                        Node wrongAnswer1 = eElement.getElementsByTagName("wrongAnswer1").item(0);
                        Node wrongAnswer2 = eElement.getElementsByTagName("wrongAnswer2").item(0);
                        Node wrongAnswer3 = eElement.getElementsByTagName("wrongAnswer3").item(0);
                        //If content, true answer and wrong answer 1 is not null
                        if ((content != null && !"".equals(content.getTextContent().trim()))
                                && (trueAnswer != null || !"".equals(trueAnswer.getTextContent().trim()))
                                && (wrongAnswer1 != null || !"".equals(wrongAnswer1.getTextContent().trim()))) {
                            Question addQuestion = new Question(itr+1, 
                                    subjectId, 
                                    -1, 
                                    -1, 
                                    content.getTextContent().trim(), 
                                    "", 
                                    "", 
                                    true);
                            if (explanation != null || !"".equals(explanation.getTextContent().trim())){
                                addQuestion.setExplanation(explanation.getTextContent().trim());
                            }
                            answerList.add(new Answer(0, itr+1, trueAnswer.getTextContent().trim(), true, true));
                            answerList.add(new Answer(1, itr+1, wrongAnswer1.getTextContent().trim(), false, true));
                            if (wrongAnswer2 != null || !"".equals(wrongAnswer2.getTextContent().trim())){
                                answerList.add(new Answer(2, itr+1, wrongAnswer2.getTextContent().trim(), false, true));
                            }
                            if (wrongAnswer3 != null || !"".equals(wrongAnswer3.getTextContent().trim())){
                                answerList.add(new Answer(2, itr+1, wrongAnswer3.getTextContent().trim(), false, true));
                            }
                            addQuestion.setAnswers(answerList);
                            questionList.add(addQuestion);
                        }
                        
                    }
                }

                request.setAttribute("importedQuestions", questionList);
                sendDispatcher(request, response, "jsp/questionImport.jsp");
            }

            if ("importQuestions".equalsIgnoreCase(service)) {
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                /*Subject DAO*/
                QuestionDAO questionDAO = new QuestionDAOImpl();
                /*Question DAO*/
                AnswerDAO answerDAO = new AnswerDAOImpl();
                /*Answer DAO*/
                /* Get user and role on session scope */
                User currUser = (User) request.getSession().getAttribute("currUser");
                UserRole currRole = (UserRole) request.getSession().getAttribute("role");
                /* If user is not logged in, or not admin/expert, redirect to index */
                if ((currUser == null) || (currRole == null)
                        || ((!currRole.getUserRoleName().equalsIgnoreCase("admin"))
                        && (!currRole.getUserRoleName().equalsIgnoreCase("expert")))) {
                    sendDispatcher(request, response, "error.jsp");
                } /* Else: get the subject list*/ else {
                    ArrayList<Subject> subjectList = null;
                    if (currRole.getUserRoleName().equalsIgnoreCase("admin")) {
                        subjectList = subjectDAO.getTrueAllSubjects();
                    }
                    if (currRole.getUserRoleName().equalsIgnoreCase("expert")) {
                        subjectList = subjectDAO.getSubjectsAssigned(currUser.getUserId());
                    }
                    request.setAttribute("subjectList", subjectList);
                }

                ArrayList<Question> addQuestionList = new ArrayList<>();
                /*Question list to add*/
                /**
                 * Get all the attributes needed for question adding
                 */
                int subjectId = Integer.parseInt(request.getParameter("subjectId"));
                String[] questionContent = request.getParameterValues("questionContent");
                String[] questionExplanation = request.getParameterValues("questionExplanation");
                String[] questionAnswerRight = request.getParameterValues("questionAnswerRight");
                String[] questionAnswerWrong1 = request.getParameterValues("questionAnswerWrong1");
                String[] questionAnswerWrong2 = request.getParameterValues("questionAnswerWrong2");
                String[] questionAnswerWrong3 = request.getParameterValues("questionAnswerWrong3");
                String[] lesson = request.getParameterValues("lesson");
                String[] dimension = request.getParameterValues("dimension");
                /**
                 * Create the question adding list
                 */
                for (int i = 0; i < questionContent.length; i++) {
                    int dimensionId = Integer.parseInt(dimension[i]);
                    int lessonId = Integer.parseInt(lesson[i]);

                    ArrayList<Answer> answerList = new ArrayList<>();
                    /*Answer List*/
                    if (((questionAnswerRight[i] == null) || (!"".equals(questionAnswerRight[i].trim())))
                            && ((questionAnswerWrong1[i] == null) || (!"".equals(questionAnswerWrong1[i].trim())))) {
                        answerList.add(new Answer(1, lessonId, questionAnswerRight[i], true, true));
                        answerList.add(new Answer(1, lessonId, questionAnswerWrong1[i], false, true));
                    } else {
                        continue;
                    }

                    if (((questionAnswerWrong2[i] == null) || (!"".equals(questionAnswerWrong2[i].trim())))) {
                        answerList.add(new Answer(1, lessonId, questionAnswerWrong2[i], false, true));
                    }
                    if (((questionAnswerWrong3[i] == null) || (!"".equals(questionAnswerWrong3[i].trim())))) {
                        answerList.add(new Answer(1, lessonId, questionAnswerWrong2[i], false, true));
                    }
                    String newQuestionContent = questionContent[i].trim();
                    String newQuestionExplanation = questionExplanation[i].trim();
                    if ((newQuestionContent == null) || (!"".equals(newQuestionContent))) {
                        Question addQuestion = new Question(i, subjectId, dimensionId, lessonId, newQuestionContent, "", newQuestionExplanation, true);
                        addQuestion.setAnswers(answerList);
                        addQuestionList.add(addQuestion);
                    }
                }

                int checkQuestion = 0;
                int checkAnswer = 0;
                for (Question question : addQuestionList) {
                    checkQuestion += questionDAO.addQuestion(question);
                    int newAddQuestion = questionDAO.getQuestionIdCreated(question);
                    for (Answer answer : question.getAnswers()) {
                        answer.setQuestionId(newAddQuestion);
                        checkAnswer += answerDAO.addAnswer(answer);
                    }
                }
                String questionImportMessage = "Out of your " + questionContent.length
                        + ", there are " + addQuestionList.size()
                        + " valid question, out of which we added successfully " + checkQuestion
                        + " questions and " + checkAnswer + " answers";
                request.setAttribute("questionImportMessage", questionImportMessage);
                sendDispatcher(request, response, "jsp/questionImport.jsp");
            }
        } catch (Exception ex) {
            Logger.getLogger(ImportQuestionController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMess", ex.toString());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /**
     * Forward the request to the destination, catch any unexpected exceptions
     * and log it
     *
     * @param request Request of the servlet
     * @param response Response of the servlet
     * @param path Forward address
     */
    public void sendDispatcher(HttpServletRequest request, HttpServletResponse response, String path) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.forward(request, response);

        } catch (ServletException | IOException ex) {
            Logger.getLogger(ImportQuestionController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Parse the string xml into xml document
     * @param xml
     * @return
     * @throws Exception 
     */
    public Document loadXMLFromString(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
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
