
package dao.impl;

import bean.CustomerQuiz;
import bean.QuestionQuizHandle;
import bean.Quiz;
import bean.QuizQuizHandle;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import dao.CustomerQuizDAO;
import dao.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;

public class CustomerQuizDAOImpl extends DBConnection implements CustomerQuizDAO {

    @Override
    public ArrayList<CustomerQuiz> getAllCustomerQuiz() throws Exception {
        ArrayList<CustomerQuiz> allCustomerQuiz = null;
        return allCustomerQuiz;
    }

    /**
     *  get all quiz by userId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<CustomerQuiz> getQuizByUser(int userId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        CustomerQuiz add = null;
        QuizDAOImpl quizDAO = new QuizDAOImpl();
        TestTypeDAOImpl testTypeDAO = new TestTypeDAOImpl();
        ArrayList<CustomerQuiz> custormerQuiz = new ArrayList<>();
        String sql = "SELECT [quizTakeId]\n"
                + "      ,[quizId]\n"
                + "      ,[userId]\n"
                + "      ,[score]\n"
                + "      ,[time]\n"
                + "      ,[sumitedAt]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[CustomerQuiz]\n"
                + "  WHERE userId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, userId);
            rs = pre.executeQuery();
            while (rs.next()) {
                Timestamp time = new Timestamp(rs.getTimestamp("sumitedAt").getTime());
                add = new CustomerQuiz(rs.getInt("quizTakeId"),
                        rs.getInt("quizId"),
                        rs.getInt("userId"),
                        rs.getInt("score"),
                        rs.getInt("time"),
                        time,
                        rs.getBoolean("status"));
                Quiz quiz = quizDAO.getQuizById(rs.getInt("quizId"));
                add.setQuizName(quiz.getQuizName());
                add.setSubjectName(quiz.getSubject().getSubjectName());
                add.setTestTypeName(testTypeDAO.getTestTypeById(quiz.getTestTypeId()).getTestTypeName());
                custormerQuiz.add(add);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return custormerQuiz;
    }

    /**
     * get user's taken quiz
     *
     * @param quizTakeId target quiz. It is a <code>int</code> primitive type
     * @return a quiz. It is a <code>CustomerQuiz</code> object
     * @throws Exception
     */
    @Override
    public CustomerQuiz getQuizByTakeQuizId(int quizTakeId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String sql = "SELECT * FROM [CustomerQuiz] WHERE quizTakeId=" + quizTakeId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                Timestamp time = new Timestamp(rs.getTimestamp("sumitedAt").getTime());
                return new CustomerQuiz(rs.getInt("quizTakeId"),
                        rs.getInt("quizId"),
                        rs.getInt("userId"),
                        rs.getInt("score"),
                        rs.getInt("time"),
                        time,
                        rs.getBoolean("status"));
            }
            return null;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    @Override
    public int editCustomerQuiz(int customerQuizId, CustomerQuiz customerQuiz) throws Exception {
        int i = 0;
        return i;
    }

    /**
     * insert into CustomerQuiz table the quiz that just have taken by user
     *
     * @param customerQuiz the new CustomerQuiz. It is a
     * <code>CustomerQuiz</code> object
     * @return number of changes in database. It is a <code>int</code> primitive
     * type.
     * @throws java.lang.Exception
     */
    @Override
    public int addCustomerQuiz(CustomerQuiz customerQuiz) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        String sql = "INSERT INTO [CustomerQuiz](quizId,userId,score,[time],sumitedAt,[status]) VALUES(?,?,?,?,?,?)";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, customerQuiz.getQuizId());
            pre.setInt(2, customerQuiz.getUserId());
            pre.setInt(3, customerQuiz.getScore());
            pre.setInt(4, customerQuiz.getTime());
            pre.setTimestamp(5, new java.sql.Timestamp(customerQuiz.getSubmitedAt().getTime()));
            pre.setBoolean(6, true);
            return pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    /**
     * get the last added customer quiz
     *
     * @return a customer quiz. It is a <code>CustomerQuiz</code> object.
     * @throws java.lang.Exception
     */
    @Override
    public CustomerQuiz getLastAddedCustomerQuiz() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "SELECT TOP 1 * FROM [CustomerQuiz] ORDER BY quizTakeId DESC";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                Timestamp time = new Timestamp(rs.getTimestamp("sumitedAt").getTime());
                return new CustomerQuiz(rs.getInt("quizTakeId"),
                        rs.getInt("quizId"),
                        rs.getInt("userId"),
                        rs.getInt("score"),
                        rs.getInt("time"),
                        time,
                        rs.getBoolean("status"));
            }
            return null;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    @Override
    public int deleteCustomerQuiz(int customerQuizId) throws Exception {
        int i = 0;

        return i;
    }

    /**
     * add user's answer of the taken quiz into database
     *
     * @param quiz the id of quiz that user have just taken. It is a
     * <code>QuizQuizHandle</code> object
     * @return number of changes in database <code>int</code> primitive type.
     * @throws java.lang.Exception
     */
    @Override
    public int addTakeAnswer(QuizQuizHandle quiz) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        int change = 0;
        ArrayList<QuestionQuizHandle> questionList = quiz.getQuestions();
        int quizTakeId = getLastAddedCustomerQuiz().getQuizTakeId();
        for (QuestionQuizHandle question : questionList) {

            String sql = "INSERT INTO [TakeAnswer](quizTakeId,questionId,answerId,[status]) VALUES(?,?,?,?)";
            try {
                conn = getConnection();
                pre = conn.prepareStatement(sql);
                pre.setInt(1, quizTakeId);
                pre.setInt(2, question.getQuestion().getQuestionId());
                if (question.getAnsweredId() != 0) {
                    pre.setInt(3, question.getAnsweredId());
                } else {
                    pre.setNull(3, Types.INTEGER);
                }
                pre.setBoolean(4, question.isMarked());
                pre.executeUpdate();
                change++;
            } catch (Exception ex) {
                throw ex;
            } finally {
                closeResultSet(rs);
                closePreparedStatement(pre);
                closeConnection(conn);
            }
        }

        return change;
    }

    /**
     * check if this test have already been take
     *
     * @param quizId
     * @return
     * @throws Exception
     */
    @Override
    public boolean checkTeakedQuiz(int quizId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        boolean check = false;
        String sql = "SELECT [quizTakeId]\n"
                + "      ,[quizId]\n"
                + "      ,[userId]\n"
                + "      ,[score]\n"
                + "      ,[time]\n"
                + "      ,[sumitedAt]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[CustomerQuiz]\n"
                + "  WHERE quizId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, quizId);
            rs = pre.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return check;
    }
}
