
package dao.impl;

import bean.LessonType;
import dao.DBConnection;
import java.util.ArrayList;
import dao.LessonTypeDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LessonTypeDAOImpl extends DBConnection implements LessonTypeDAO {

    /**
     * get all lesson type from the database
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<LessonType> getAllLessonType() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        ArrayList<LessonType> lessonTypesList = new ArrayList<>();
        String sql = "SELECT [lessonTypeId]\n"
                + "      ,[lessonTypeName]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[LessonType]\n"
                + "  WHERE status = 1 ";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                lessonTypesList.add(
                        new LessonType(rs.getInt("lessonTypeId"),
                                rs.getString("lessonTypeName"),
                                rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return lessonTypesList;
    }

    /**
     * get lesson type by id
     *
     * @param lessonTypeId
     * @return
     * @throws Exception
     */
    @Override
    public LessonType getLessonTypeById(int lessonTypeId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String sql = "SELECT [lessonTypeId]\n"
                + "      ,[lessonTypeName]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[LessonType] where lessonTypeId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, lessonTypeId);
            rs = pre.executeQuery();
            if (rs.next()) {
                return new LessonType(rs.getInt("lessonTypeId"),
                        rs.getString("lessonTypeName"),
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
     * insert new lesson type
     *
     * @param newLessonType
     * @return
     * @throws Exception
     */
    @Override
    public int addLessonType(LessonType newLessonType) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String sql = "INSERT INTO dbo.LessonType(lessonTypeName,status) VALUES(?,1)";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, newLessonType.getLessonTypeName());
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
     * update existed lesson type
     *
     * @param updatedLessonType
     * @return
     * @throws Exception
     */
    @Override
    public int updateLessonType(LessonType updatedLessonType) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String sql = "  UPDATE [QuizSystem].[dbo].[LessonType] \n"
                + "  SET [lessonTypeName] = ?\n"
                + "      ,[status] = ? \n"
                + "  WHERE [lessonTypeId] = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, updatedLessonType.getLessonTypeName());
            pre.setBoolean(2, updatedLessonType.isStatus());
            pre.setInt(3, updatedLessonType.getLessonTypeId());
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
     * delete existed lesson
     *
     * @param ltId
     * @return
     * @throws Exception
     */
    @Override
    public int deleteLessonType(int ltId) throws Exception {
        return 0;
    }

    /**
     * get all lesson type from the database
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<LessonType> getAllStatusLessonType() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        ArrayList<LessonType> lessonTypesList = new ArrayList<>();
        String sql = "SELECT [lessonTypeId]\n"
                + "      ,[lessonTypeName]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[LessonType]";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                lessonTypesList.add(
                        new LessonType(rs.getInt("lessonTypeId"),
                                rs.getString("lessonTypeName"),
                                rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return lessonTypesList;
    }
}
