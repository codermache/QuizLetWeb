
package dao;

import bean.Quiz;
import java.util.ArrayList;


public interface QuizDAO {

    /**
     * Find all quiz in the database
     *
     * @return <code>ArrayList<Quiz></code>
     * @throws Exception
     */
    public ArrayList<Quiz> getAllQuiz() throws Exception;

    /**
     * get quiz by Id
     *
     * @param quizId the target quiz's id. It is a <code>int</code> primitive
     * type
     * @return a quiz <code>Quiz</code> object.
     * @throws java.lang.Exception
     */
    public Quiz getQuizById(int quizId) throws Exception;

    /**
     * get taken quiz by takeQuiz's Id
     *
     * @param quizTakeId the target quiz's id. It is a <code>int</code>
     * primitive type
     * @return a quiz <code>Quiz</code> object.
     * @throws java.lang.Exception
     */
    public Quiz getQuizByQuizTakeId(int quizTakeId) throws Exception;
    
    /**
     * get quiz by subjectId
     * @param subjectId
     * @return
     * @throws Exception 
     */
    public ArrayList<Quiz> getQuizBySubject(int subjectId) throws Exception;
    
    /**
     * get quiz by lessonId
     * @param lessonId
     * @return
     * @throws Exception 
     */
    public ArrayList<Quiz> getQuizByLesson(int lessonId) throws Exception;

    /**
     * get all simulation quiz by user search
     *
     * @param userId user's id. <code>int</code> primitive type
     * @param subjectId user's registered subject's id. <code>int</code>
     * primitive type
     * @param quizName search string to search quiz by name. <code>String</code>
     * primitive type
     * @return all simulation quiz by user. <code>ArrayList<Quiz></code> object
     * @throws Exception
     */
    public ArrayList<Quiz> getAllSimulationQuizByUser(int userId, int subjectId, String quizName) throws Exception;

    /**
     * edit existed quiz in the database
     *
     * @param quizId
     * @param quiz
     * @return
     * @throws Exception
     */
    public int editQuiz(int quizId, Quiz quiz) throws Exception;

    /**
     * add a quiz into the database
     *
     * @param quiz
     * @return
     * @throws Exception
     */
    public int addQuiz(Quiz quiz) throws Exception;

    /**
     * delete a quiz from the database
     *
     * @param quizId
     * @return
     * @throws Exception
     */
    public int deleteQuiz(int quizId) throws Exception;

    /**
     * Get quiz that have some same attribute
     *
     * @param quiz
     * @return
     * @throws Exception
     */
    public int getQuizIdCreated(Quiz quiz) throws Exception;

    /**
     * add quiz's question to the database
     *
     * @param quizId
     * @param questionId
     * @return
     * @throws Exception
     */
    public int addQuizQuestion(int quizId, int questionId) throws Exception;

    /**
     * Get all quiz that have the same subjectId and quizTypeId
     *
     * @param subjectId
     * @param quizTypeId
     * @return
     * @throws Exception
     */
    public ArrayList<Quiz> getFilteredQuiz(int subjectId, int quizTypeId) throws Exception;

    /**
     * Get all quiz that have the name similar to searchName
     *
     * @param searchName
     * @return
     * @throws Exception
     */
    public ArrayList<Quiz> getQuizByName(String searchName) throws Exception;

    /**
     * delete all question of a quiz
     *
     * @param quizId
     * @return
     * @throws Exception
     */
    public int removeQuizQuestion(int quizId) throws Exception;
}
