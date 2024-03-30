
package dao;

import bean.QuizLevel;
import java.util.ArrayList;

public interface QuizLevelDAO {

    /**
     * get all quizlevel in the database where status = 1 
     *
     * @return <code>ArrayList<QuizLevel></code>
     * @throws Exception
     */
    public ArrayList<QuizLevel> getAllQuizLevel() throws Exception;

    /**
     * get quiz level by id
     * @param quizLevelId
     * @return
     * @throws Exception 
     */
    public QuizLevel getQuizLevelById(int quizLevelId) throws Exception;

    /**
     * update existed quiz level
     * @param quizLevelId
     * @param quizLevel
     * @return
     * @throws Exception 
     */
    public int editQuizLevel(QuizLevel quizLevel) throws Exception;

    /**
     * add new quiz level to the database
     * @param quizLevel
     * @return
     * @throws Exception 
     */
    public int addQuizLevel(QuizLevel quizLevel) throws Exception;

    /**
     * delete a quiz level from a database
     * @param quizLevelId
     * @return
     * @throws Exception 
     */
    public int deleteQuizLevel(int quizLevelId) throws Exception;
    
    /**
     * get all quizlevel in the database
     *
     * @return <code>ArrayList<QuizLevel></code>
     * @throws Exception
     */
    public ArrayList<QuizLevel> getAllStatusQuizLevel() throws Exception;
}
