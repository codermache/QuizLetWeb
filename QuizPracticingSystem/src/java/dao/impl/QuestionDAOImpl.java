
package dao.impl;

import bean.Question;
import bean.QuestionManage;
import dao.DBConnection;
import dao.DimensionDAO;
import dao.LessonDAO;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import dao.QuestionDAO;
import dao.SubjectDAO;
import java.sql.Connection;
import java.sql.ResultSet;


public class QuestionDAOImpl extends DBConnection implements QuestionDAO {

    /**
     * get all question from database
     *
     * @return list of all question. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Question> getAllQuestion() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Question> questionList = new ArrayList();
        String sql = "SELECT * FROM Question";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                questionList.add(new Question(rs.getInt("questionId"),
                        rs.getInt("subjectId"),
                        rs.getInt("dimensionId"),
                        rs.getInt("lessonId"),
                        rs.getString("content"),
                        rs.getString("media"),
                        rs.getString("explanation"),
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

    /**
     * turn a list of question into list of question quiz handle
     *
     * @param questionId the target question's id. It is a <code>int</code>
     * primitive type
     * @return a question. It is a <code>Question</code> object.
     * @throws java.lang.Exception
     */
    @Override
    public Question getQuestionById(int questionId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String sql = "SELECT * FROM Question WHERE questionId=" + questionId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                return new Question(rs.getInt("questionId"),
                        rs.getInt("subjectId"),
                        rs.getInt("dimensionId"),
                        rs.getInt("lessonId"),
                        rs.getString("content"),
                        rs.getString("media"),
                        rs.getString("explanation"),
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
     * get list of question of the target quiz
     *
     * @param quizId the target quiz's id. It is a <code>int</code> primitive
     * type
     * @return a list of question. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Question> getQuestionByQuizId(int quizId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Question> questionList = new ArrayList();
        ArrayList<Integer> idList = new ArrayList();
        String sql = "SELECT * FROM [QuizQuestion] WHERE quizId=" + quizId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                idList.add(rs.getInt("questionId"));
            }
            for (int id : idList) {
                questionList.add(getQuestionById(id));
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

    /**
     * get list of question by content
     *
     * @param content the target questionContent. It is a <code>int</code>
     * primitive type
     * @return a list of question. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<QuestionManage> getQuestionByContent(String content) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        ArrayList<QuestionManage> questionManageList = new ArrayList<>();
        QuestionManage questionManage = null;
        SubjectDAO subjectDAO = new SubjectDAOImpl();
        LessonDAO lessonDAO = new LessonDAOImpl();
        DimensionDAO dimensionDAO = new DimensionDAOImpl();
        String sql = "SELECT * FROM [Question]";
        if (content != null) {
            sql = sql.concat("WHERE content like '%" + content + "%'");
        }
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                questionManage = new QuestionManage(rs.getInt("questionId"),
                        subjectDAO.getSubjectbyId(rs.getInt("subjectId")).getSubjectName(),
                        dimensionDAO.getDimensionById(rs.getInt("dimensionId")).getDimensionName(),
                        lessonDAO.getLessonById(rs.getInt("lessonId")).getLessonName(),
                        rs.getString("content"), rs.getString("media"),
                        rs.getString("explanation"), rs.getBoolean("status"));
                questionManageList.add(questionManage);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return questionManageList;
    }

    /**
     * get New QuestionId added
     *
     * @param question It is a <code>Object</code> primitive type
     * @return questionId. It is a <code>int</code> object.
     * @throws java.lang.Exception
     */
    @Override
    public int getQuestionIdCreated(Question question) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        int questionId = -1;
        String sql = "SELECT TOP 1 [questionId]\n"
                + "      ,[subjectId]\n"
                + "      ,[dimensionId]\n"
                + "      ,[lessonId]\n"
                + "      ,[content]\n"
                + "      ,[media]\n"
                + "      ,[explanation]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[Question]\n"
                + "  WHERE [subjectId] = ? \n"
                + " and	[dimensionId] = ? \n"
                + " and	[lessonId] = ? \n"
                + " and	[content] = ? \n"
                + " and	[status] = ? \n"
                + "ORDER BY questionId DESC";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, question.getSubjectId());
            pre.setInt(2, question.getDimensionId());
            pre.setInt(3, question.getLessonId());
            pre.setString(4, question.getContent());
            pre.setBoolean(5, question.isStatus());
            rs = pre.executeQuery();
            if (rs.next()) {
                questionId = rs.getInt("questionId");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return questionId;
    }

    /**
     * get New QuestionId added
     *
     * @param newQuestion It is a <code>Object</code> primitive type
     * @return count. It is a <code>int</code> object.
     */
    @Override
    public int addQuestion(Question newQuestion) throws Exception {
        Connection conn = null;
        ResultSet rs = null;/* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        String sql = "INSERT INTO [Question](subjectId,dimensionId,lessonId,content,media,explanation,status) "
                + "values (?,?,?,?,?,?,?)";
        int count = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, newQuestion.getSubjectId());
            pre.setInt(2, newQuestion.getDimensionId());
            pre.setInt(3, newQuestion.getLessonId());
            pre.setString(4, newQuestion.getContent());
            pre.setString(5, newQuestion.getMedia());
            pre.setString(6, newQuestion.getExplanation());
            pre.setBoolean(7, newQuestion.isStatus());
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

    /**
     * edit Question
     *
     * @param questionId the target questionId. It is a <code>int</code>
     * @param question the target questionId. It is a <code>Object</code>
     * @return i. It is a <code>int</code>
     * @throws Exception
     */
    @Override
    public int editQuestion(int questionId, Question question) throws Exception {
        int i = 0;
        Connection conn = null;
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "UPDATE [Question]\n"
                + "SET [subjectId] = ?\n"
                + "      ,[dimensionId] =? \n"
                + "      ,[lessonId] = ?\n"
                + "      ,[content] = ?\n"
                + "      ,[media]	= ?\n"
                + "      ,[explanation] = ?\n"
                + "      ,[status] = ?\n"
                + " WHERE [questionId] = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, question.getSubjectId());
            pre.setInt(2, question.getDimensionId());
            pre.setInt(3, question.getLessonId());
            pre.setString(4, question.getContent());
            pre.setString(5, question.getMedia());
            pre.setString(6, question.getExplanation());
            pre.setBoolean(7, question.isStatus());
            pre.setInt(8, questionId);
            i = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return i;

    }

    @Override
    public int deleteQuestion(int questionId) throws Exception {
        return 0;
    }

    @Override
    public int importQuestion(ArrayList<Question> questionList) throws Exception {
        return 0;
    }

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
    @Override
    public ArrayList<QuestionManage> getQuestionManage(int subjectId, int dimensionId, int lessonId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;/* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */
        ArrayList<QuestionManage> questionManageList = new ArrayList<>();
        String sql = "SELECT * FROM Question WHERE 1=1";
        if (subjectId > 0) {
            sql = sql.concat("and subjectId = " + subjectId);
        }
        if (lessonId > 0) {
            sql = sql.concat("and lessonId = " + lessonId);
        }
        if (dimensionId > 0) {
            sql = sql.concat("and dimensionId = " + dimensionId);
        }
        QuestionManage questionManage = null;
        SubjectDAO subjectDAO = new SubjectDAOImpl();
        LessonDAO lessonDAO = new LessonDAOImpl();
        DimensionDAO dimensionDAO = new DimensionDAOImpl();
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                questionManage = new QuestionManage(rs.getInt("questionId"),
                        subjectDAO.getSubjectbyId(rs.getInt("subjectId")).getSubjectName(),
                        dimensionDAO.getDimensionById(rs.getInt("dimensionId")).getDimensionName(),
                        lessonDAO.getLessonById(rs.getInt("lessonId")).getLessonName(),
                        rs.getString("content"), rs.getString("media"),
                        rs.getString("explanation"), rs.getBoolean("status"));
                questionManageList.add(questionManage);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return questionManageList;
    }

    /**
     *
     * @param numberOfQuestion
     * @param subjectId
     * @param dimensionId
     * @return <code>ArrayList<DimensionType></code> object
     * @throws Exception
     */
    @Override
    public ArrayList<Question> getQuestionForCreateQuiz(int numberOfQuestion, int subjectId, int dimensionId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;/* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */
        ArrayList<Question> questionList = new ArrayList<>();
        String sql = "SELECT [questionId]\n"
                + "      ,[subjectId]\n"
                + "      ,[dimensionId]\n"
                + "      ,[lessonId]\n"
                + "      ,[content]\n"
                + "      ,[media]\n"
                + "      ,[explanation]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[Question]\n"
                + "  WHERE subjectId = ? AND dimensionId = ? AND [status] = 1"
                + "ORDER BY NEWID()";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, subjectId);
            pre.setInt(2, dimensionId);
            rs = pre.executeQuery();
            while (rs.next() && questionList.size() < numberOfQuestion) {
                Question pro = new Question();
                pro.setQuestionId(rs.getInt("questionId"));
                pro.setSubjectId(rs.getInt("subjectId"));
                pro.setDimensionId(rs.getInt("dimensionId"));
                pro.setLessonId(rs.getInt("lessonId"));
                pro.setContent(rs.getString("content"));
                pro.setMedia(rs.getString("media"));
                pro.setStatus(rs.getBoolean("status"));
                questionList.add(pro);
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
