
package dao.impl;

import bean.Answer;
import bean.Question;
import bean.QuestionQuizHandle;
import dao.DBConnection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import dao.QuestionQuizHandleDAO;
import java.sql.Connection;
import java.sql.ResultSet;


public class QuestionQuizHandleDAOImpl extends DBConnection implements QuestionQuizHandleDAO {

    /**
     * turn a Question into QuestionQUizHandle type
     *
     * @param questionId the target question's id. It is a <code>int</code>
     * primitive type
     * @return a QuestionQuizHandle <code>QuestionQuizHandle</code> object.
     * @throws java.lang.Exception
     */
    @Override
    public QuestionQuizHandle generateQuestionById(int questionId) throws Exception {
        QuestionDAOImpl questionDAO = new QuestionDAOImpl();
        AnswerDAOImpl answerDAO = new AnswerDAOImpl();
        Question question = questionDAO.getQuestionById(questionId);                        //get question
        ArrayList<Answer> answers = answerDAO.getAnswersByQuenstionId(questionId);          //get question's answer list
        return new QuestionQuizHandle(question, answers, 0, false);                         //constructor(question,question's answers list, user's answerd id,marked)
    }

    /**
     * get right answer of the question
     *
     * @param question the target question's id. It is a
     * <code>QuestionQuizHandle</code> object
     * @return right answer of the question. It is <code>Answer</code> object.
     * @throws java.lang.Exception
     */
    @Override
    public Answer getRightAnswer(QuestionQuizHandle question) throws Exception {
        ArrayList<Answer> answerList = question.getAnswerList();
        for (Answer answer : answerList) {
            if (answer.isIsCorrect()) {
                return answer;
            }
        }
        return null;
    }

    /**
     * mark and unmark question
     *
     * @param question the target question's id. It is a
     * <code>QuestionQuizHandle</code> object
     * @return void.
     */
    @Override
    public void markQuestion(QuestionQuizHandle question) throws Exception {
        if (question.isMarked()) {
            question.setMarked(false);
        } else {
            question.setMarked(true);
        }
    }

    /**
     * get the taken quiz for review action
     *
     * @param quizTakeId the target taken quiz's id. It is a <code>int</code>
     * primitive type
     * @return a <code>QuizQuizHandle</code> object.
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<QuestionQuizHandle> getReviewQuestion(int quizTakeId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        QuestionDAOImpl questionDAO = new QuestionDAOImpl();
        AnswerDAOImpl answerDAO = new AnswerDAOImpl();
        ArrayList<QuestionQuizHandle> questionList = new ArrayList();

        String sql = "SELECT * FROM [TakeAnswer] WHERE quizTakeId=" + quizTakeId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                Question question = questionDAO.getQuestionById(rs.getInt("questionId"));
                ArrayList<Answer> answers = answerDAO.getAnswersByQuenstionId(rs.getInt("questionId"));
                questionList.add(new QuestionQuizHandle(question,
                        answers,
                        rs.getInt("answerId"),
                        rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }

        return questionList;
    }
}
