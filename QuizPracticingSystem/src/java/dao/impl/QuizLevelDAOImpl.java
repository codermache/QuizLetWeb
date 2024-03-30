
package dao.impl;

import java.util.ArrayList;
import bean.QuizLevel;
import dao.DBConnection;
import java.sql.PreparedStatement;
import dao.QuizLevelDAO;
import java.sql.Connection;
import java.sql.ResultSet;


public class QuizLevelDAOImpl extends DBConnection implements QuizLevelDAO {

    /**
     * get all quizlevel in the database where status = 1
     *
     * @return <code>ArrayList<QuizLevel></code>
     * @throws Exception
     */
    @Override
    public ArrayList<QuizLevel> getAllQuizLevel() throws Exception {
        ArrayList<QuizLevel> quizLevels = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        QuizLevel quizLevel = null;
        String sql = "SELECT [quizLevelId]\n"
                + "      ,[quizLevelName]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[QuizLevel] where status = 1";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                quizLevel = new QuizLevel(rs.getInt("quizLevelId"),
                        rs.getString("quizLevelName"),
                        rs.getBoolean("status"));
                quizLevels.add(quizLevel);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return quizLevels;
    }

    /**
     * update existed quiz level
     *
     * @param quizLevelId
     * @return
     * @throws Exception
     */
    @Override
    public QuizLevel getQuizLevelById(int quizLevelId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "SELECT * FROM [QuizLevel] where quizLevelId=" + quizLevelId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                return new QuizLevel(rs.getInt("quizLevelId"),
                        rs.getString("quizLevelName"),
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
     * update existed quiz level
     *
     * @param quizLevel
     * @return
     * @throws Exception
     */
    @Override
    public int editQuizLevel(QuizLevel quizLevel) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "UPDATE [QuizSystem].[dbo].[QuizLevel]\n"
                + "  SET [quizLevelName] = ?\n"
                + "      ,[status] = ? \n"
                + "  WHERE quizLevelId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, quizLevel.getQuizLevelName());
            pre.setBoolean(2, quizLevel.isStatus());
            pre.setInt(3, quizLevel.getQuizLevelId());
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
     * add new quiz level to the database
     *
     * @param quizLevel
     * @return
     * @throws Exception
     */
    @Override
    public int addQuizLevel(QuizLevel quizLevel) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "INSERT INTO dbo.QuizLevel(quizLevelName,status) VALUES(?,1);";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, quizLevel.getQuizLevelName());
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
     * delete a quiz level from a database
     *
     * @param quizLevelId
     * @return
     * @throws Exception
     */
    @Override
    public int deleteQuizLevel(int quizLevelId) throws Exception {
        int i = 0;

        return i;
    }

    /**
     * get all quizlevel in the database
     *
     * @return <code>ArrayList<QuizLevel></code>
     * @throws Exception
     */
    @Override
    public ArrayList<QuizLevel> getAllStatusQuizLevel() throws Exception {
        ArrayList<QuizLevel> quizLevels = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        QuizLevel quizLevel = null;
        String sql = "SELECT [quizLevelId]\n"
                + "      ,[quizLevelName]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[QuizLevel]";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                quizLevel = new QuizLevel(rs.getInt("quizLevelId"),
                        rs.getString("quizLevelName"),
                        rs.getBoolean("status"));
                quizLevels.add(quizLevel);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return quizLevels;
    }
}
