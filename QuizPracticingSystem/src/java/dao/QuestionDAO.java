
package dao;

import bean.*;
import java.util.ArrayList;

public interface QuestionDAO {
    /**
     * get all question from database
     *
     * @return list of all question. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    ArrayList<Question> getAllQuestion() throws Exception;
    
    /**
     * turn a list of question into list of question quiz handle
     *
     * @param questionId the target question's id. It is a <code>int</code>
     * primitive type
     * @return a question. It is a <code>Question</code> object.
     * @throws java.lang.Exception
     */
    public Question getQuestionById(int questionId) throws Exception;
    
    /**
     * get list of question of the target quiz
     *
     * @param quizId the target quiz's id. It is a <code>int</code> primitive
     * type
     * @return a list of question. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    public ArrayList<Question> getQuestionByQuizId(int quizId) throws Exception;
    
    /**
     * get list of question by content
     *
     * @param content the target questionContent. It is a <code>int</code>
     * primitive type
     * @return a list of question. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    public ArrayList<QuestionManage> getQuestionByContent(String content) throws Exception;
    
    /**
     * get New QuestionId added
     *
     * @param newQuestion It is a <code>Object</code> primitive type
     * @return count. It is a <code>int</code> object.
     * @throws java.lang.Exception
     */
    public int addQuestion(Question newQuestion) throws Exception;
    
    /**
     * edit Question
     *
     * @param questionId the target questionId. It is a <code>int</code>
     * @param editedQuestion the target questionId. It is a <code>Object</code>
     * @return i. It is a <code>int</code>
     * @throws Exception
     */
    public int editQuestion(int questionId, Question editedQuestion) throws Exception;

    public int deleteQuestion(int questionId) throws Exception;

    public int importQuestion(ArrayList<Question> questionList) throws Exception;
    
    /**
     * get list of question by subject,lesson,dimension
     *
     * @param subjectId the target questionManage. It is a <code>int</code>
     * primitive
     * @param lessonId target questionManage. It is a <code>int</code> primitive
     * @param dimensionId the target questionManage. It is a <code>int</code>
     * primitive type
     * @return a list of question. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    public ArrayList<QuestionManage> getQuestionManage(int subjectId, int dimensionId, int lessonId) throws Exception;
    
    /**
     *
     * @param numberOfQuestion
     * @param subjectId
     * @param dimensionId
     * @return <code>ArrayList<DimensionType></code> object
     * @throws Exception
     */
    public ArrayList<Question> getQuestionForCreateQuiz(int numberOfQuestion,int subjectId, int dimensionId) throws Exception;
    
    /**
     * get New QuestionId added
     *
     * @param question It is a <code>Object</code> primitive type
     * @return questionId. It is a <code>int</code> object.
     */
    public int getQuestionIdCreated(Question question) throws Exception;
}
