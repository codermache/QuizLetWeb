
package dao.impl;

import bean.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import dao.AnswerDAO;
import dao.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;

public class AnswerDAOImpl extends DBConnection implements AnswerDAO {
    
    /**
     * get all answers
     * @return
     * @throws Exception 
     */
    @Override
    public ArrayList<Answer> getAllAnswers() throws Exception {
        return null;
    }
    
    /**
     * Get answers by Id 
     * @param answerId It is a <code>int</code>
     * @return 
     * @throws Exception 
     */
    @Override
    public Answer getAnswersById(int answerId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        
        String sql = "SELECT * FROM Answer WHERE answerId= ? ";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, answerId);
            rs = pre.executeQuery();   
           if (rs.next()) {
                return new Answer(rs.getInt("answerId"), 
                        rs.getInt("questionId"), 
                        rs.getString("answerContent"), 
                        rs.getBoolean("isCorrect"), 
                        rs.getBoolean("status"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return null;
    }

    /**
     * get Answer by QuestionId
     *
     * @param questionId the target question id. It is a <code>int</code>
     * primitive
     * @return a list of Answer. It is a <code>java.util.ArrayList</code>
     * @throws Exception
     */
    @Override
    public ArrayList<Answer> getAnswersByQuenstionId(int questionId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Answer> answerList = new ArrayList();
        String sql = "SELECT * FROM Answer WHERE questionId=" + questionId;
        /* Sql query */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                answerList.add(new Answer(rs.getInt("answerId"),
                        rs.getInt("questionId"),
                        rs.getString("answerContent"),
                        rs.getBoolean("isCorrect"),
                        rs.getBoolean("status")));
            }
            return answerList;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    @Override
    public int deleteAnswerById(int aId) throws Exception {
        return 0;
    }

    @Override
    public int deleteAnswerByQuestionId(int qId) throws Exception {
        return 0;
    }

    /**
     * Update Answer
     *
     * @param answerId It is a <code>int</code>
     * @param updatedAnswer It is a <code>object</code>
     * @return check. It is a <code>int</code>
     * @throws Exception
     */
    @Override
    public int updateAnswer(int answerId, Answer updatedAnswer) throws Exception {
        Connection conn = null;
        ResultSet rs = null;/* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */
        int check = 0;
        String sql = "UPDATE [Answer]\n"
                + "SET [questionId] = ?\n"
                + "      ,[answerContent] =? \n"
                + "      ,[isCorrect] = ?\n"
                + "      ,[status] = ?\n"
                + " WHERE [answerId] = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, updatedAnswer.getQuestionId());
            pre.setString(2, updatedAnswer.getAnswerContent());
            pre.setBoolean(3, updatedAnswer.isIsCorrect());
            pre.setBoolean(4, updatedAnswer.isStatus());
            pre.setInt(5, updatedAnswer.getAnswerId());
            check = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return check;
    }

    /**
     * Add New Answer
     *
     * @param newAnswer It is a <code>Object</code> primitive type
     * @return count. It is a <code>int</code>
     * @throws Exception
     */
    @Override
    public int addAnswer(Answer newAnswer) throws Exception {
        Connection conn = null;
        ResultSet rs = null;/* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        String sql = "INSERT INTO [Answer](questionId,answerContent,isCorrect,status) "
                + "values (?,?,?,?)";
        int count = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, newAnswer.getQuestionId());
            pre.setString(2, newAnswer.getAnswerContent());
            pre.setBoolean(3, newAnswer.isIsCorrect());
            pre.setBoolean(4, newAnswer.isStatus());
            count = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return count;

    }

}
