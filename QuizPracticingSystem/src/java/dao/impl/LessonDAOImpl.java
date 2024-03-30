
package dao.impl;

import bean.Lesson;
import dao.DBConnection;
import java.util.ArrayList;
import dao.LessonDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LessonDAOImpl extends DBConnection implements LessonDAO {

    /**
     * Get all lessons from database
     *
     * @return a list of <code>Lesson</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Lesson> getAllLessons() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Lesson> listLesson = new ArrayList();
        String sql = "SELECT [lessonId]\n"
                + "      ,[subjectId]\n"
                + "      ,[lessonName]\n"
                + "      ,[lessonOrder]\n"
                + "      ,[lessonTypeId]\n"
                + "      ,[videoLink]\n"
                + "      ,[content]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[Lesson]";
        /* Get the dimension */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            /* Get information from resultset and add it to arrayList */
            while (rs.next()) {
                int lessonId = rs.getInt("lessonId");
                int subjectId = rs.getInt("subjectId");
                String lessonName = rs.getString("lessonName");
                int lessonOrder = rs.getInt("lessonOrder");
                int lessonTypeId = rs.getInt("lessonTypeId");
                String videoLink = rs.getString("videoLink");
                String content = rs.getString("content");
                Boolean status = rs.getBoolean("status");
                listLesson.add(new Lesson(lessonId, subjectId, lessonName, lessonOrder, lessonTypeId, videoLink, content, status, null));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return listLesson;
    }

    /**
     * Get lessons of a subject
     *
     * @param subjectId
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Lesson> getAllLessonBySubjectId(int subjectId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

 /* Get dimension list of the subject */
        ArrayList<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT L.[lessonId]"
                + "      ,S.[subjectId]\n"
                + "      ,L.[lessonName]\n"
                + "      ,L.[lessonOrder]\n"
                + "      ,L.[lessonTypeId]\n"
                + "	  ,LT.lessonTypeName\n"
                + "      ,L.[videoLink]\n"
                + "      ,L.[content]\n"
                + "      ,L.[status]\n"
                + "  FROM [QuizSystem].[dbo].[Subject] S "
                + "  INNER JOIN [QuizSystem].[dbo].[Lesson] L ON S.subjectId = L.subjectId\n"
                + "  INNER JOIN [QuizSystem].[dbo].[LessonType] LT ON LT.lessonTypeId = L.lessonTypeId\n"
                + "  WHERE S.subjectId = " + subjectId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                lessons.add(new Lesson(rs.getInt("lessonId"), 
                        subjectId, 
                        rs.getString("lessonName"),
                        rs.getInt("lessonOrder"),
                        rs.getInt("lessonTypeId"),
                        rs.getString("videoLink"),
                        rs.getString("content"),
                        rs.getBoolean("status"),
                        rs.getString("lessonTypeName")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return lessons;
    }

    @Override
    public ArrayList<Lesson> getAllLessonByTypeId(int typeId) throws Exception {
        return null;
    }

    /**
     * Get lesson from database by lesson's id
     *
     * @param lessonId lesson's ID. It is a <code>Integer</code>
     * @return a <code>Lesson</code> objects
     * @throws java.lang.Exception
     */
    @Override
    public Lesson getLessonById(int lessonId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        Lesson lessonById = null;
        String sql = "SELECT * FROM [Lesson] WHERE [lessonId] = ?";

        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, lessonId);
            rs = pre.executeQuery();
            if (rs.next()) {
                int subjectId = rs.getInt("subjectId");
                String lessonName = rs.getString("lessonName");
                int lessonOrder = rs.getInt("lessonOrder");
                int lessonTypeId = rs.getInt("lessonTypeId");
                String videoLink = rs.getString("videoLink");
                String content = rs.getString("content");
                Boolean status = rs.getBoolean("status");

                lessonById = new Lesson(lessonId, subjectId, lessonName, lessonOrder, lessonTypeId, videoLink, content, status, null);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return lessonById;
    }
    
    /**
     * update Lesson
     *
     * @param lessonId the target lessonId. It is a <code>int</code>
     * @param updatedLesson the target updatedLesson. It is a <code>Object</code>
     * @return check. It is a <code>int</code>
     * @throws Exception
     */
    @Override
    public int updateLesson(int lessonId, Lesson updatedLesson) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = " UPDATE [QuizSystem].[dbo].[Lesson] "
                + "SET lessonName =?,lessonOrder=?,lessonTypeId=?,videoLink=?,content=?,status=?\n" 
                + "WHERE lessonId = ?";
        int check = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, updatedLesson.getLessonName());
            pre.setInt(2, updatedLesson.getLessonOrder());
            pre.setInt(3, updatedLesson.getLessonTypeId());
            pre.setString(4, updatedLesson.getVideoLink());
            pre.setString(5, updatedLesson.getContent());
            pre.setBoolean(6, updatedLesson.isStatus());
            pre.setInt(7, lessonId);
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

    @Override
    public int deleteLesson(int lessonId) throws Exception {
        return 0;
    }
    
    /**
     * add New lesson 
     *
     * @param newLesson It is a <code>Object</code> primitive type
     * @return count. It is a <code>int</code> object.
     * @throws java.lang.Exception
     */
    @Override
    public int addLesson(Lesson newLesson) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "INSERT INTO [QuizSystem].[dbo].[Lesson](subjectId,lessonName,lessonOrder,lessonTypeId,videoLink,content,status) "
                + "values (?,?,?,?,?,?,?);";
        int check = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, newLesson.getSubjectId());
            pre.setString(2, newLesson.getLessonName());
            pre.setInt(3, newLesson.getLessonOrder());
            pre.setInt(4, newLesson.getLessonTypeId());
            pre.setString(5, newLesson.getVideoLink());
            pre.setString(6, newLesson.getContent());
            pre.setBoolean(7, newLesson.isStatus());
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
   
}
