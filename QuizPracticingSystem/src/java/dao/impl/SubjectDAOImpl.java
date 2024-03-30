
package dao.impl;

import bean.Subject;
import dao.DBConnection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import dao.DimensionDAO;
import dao.SubjectCateDAO;
import dao.SubjectDAO;
import java.sql.Connection;
import java.sql.ResultSet;

public class SubjectDAOImpl extends DBConnection implements SubjectDAO {

    /**
     *  Get all available subject in the Subject table (status = 1)
     * @return @throws Exception 
     */
    @Override
    public ArrayList<Subject> getAllSubjects() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Subject> allSubject = new ArrayList();
        DimensionDAO dimensionDAO = new DimensionDAOImpl();
        SubjectCateDAO subjectCateDAO = new SubjectCateDAOImpl();

        String sqlSubject = "SELECT * FROM [Subject] WHERE status=1";
        /* Get the subject */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sqlSubject);
            rs = pre.executeQuery();
            /* Get information from resultset and add it to arrayList */
            while (rs.next()) {
                int subjectId = rs.getInt("subjectId");
                String subjectName = rs.getString("subjectName");
                String description = rs.getString("description");
                String thumbnail = rs.getString("thumbnail");
                Boolean featured = rs.getBoolean("featuredSubject");
                Boolean status = rs.getBoolean("status");

                allSubject.add(new Subject(subjectId, subjectName, description,
                        thumbnail, featured, status,
                        dimensionDAO.getDimensionBySubject(subjectId),
                        subjectCateDAO.getSubjectCateBySubject(subjectId)));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return allSubject;
    }
    
    /**
     *  Get all subject in the Subject table
     * @return @throws Exception 
     */
    @Override
    public ArrayList<Subject> getTrueAllSubjects() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Subject> allSubject = new ArrayList();
        DimensionDAO dimensionDAO = new DimensionDAOImpl();
        SubjectCateDAO subjectCateDAO = new SubjectCateDAOImpl();

        String sqlSubject = "SELECT * FROM [Subject]";
        /* Get the subject */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sqlSubject);
            rs = pre.executeQuery();
            /* Get information from resultset and add it to arrayList */
            while (rs.next()) {
                int subjectId = rs.getInt("subjectId");
                String subjectName = rs.getString("subjectName");
                String description = rs.getString("description");
                String thumbnail = rs.getString("thumbnail");
                Boolean featured = rs.getBoolean("featuredSubject");
                Boolean status = rs.getBoolean("status");

                allSubject.add(new Subject(subjectId, subjectName, description,
                        thumbnail, featured, status,
                        dimensionDAO.getDimensionBySubject(subjectId),
                        subjectCateDAO.getSubjectCateBySubject(subjectId)));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return allSubject;
    }
    
    /**
     *
     * @return @throws Exception Get 5 las added subject in the Subject table
     */
    @Override
    public ArrayList<Subject> get5LastAddedSubject() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Subject> allSubject = new ArrayList();
        DimensionDAO dimensionDAO = new DimensionDAOImpl();
        SubjectCateDAO subjectCateDAO = new SubjectCateDAOImpl();

        String sqlSubject = "SELECT TOP 5 * FROM [Subject] ORDER BY subjectId DESC";
        /* Get the subject */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sqlSubject);
            rs = pre.executeQuery();
            /* Get information from resultset and add it to arrayList */
            while (rs.next()) {
                int subjectId = rs.getInt("subjectId");
                String subjectName = rs.getString("subjectName");
                String description = rs.getString("description");
                String thumbnail = rs.getString("thumbnail");
                Boolean featured = rs.getBoolean("featuredSubject");
                Boolean status = rs.getBoolean("status");

                allSubject.add(new Subject(subjectId, subjectName, description,
                        thumbnail, featured, status,
                        dimensionDAO.getDimensionBySubject(subjectId),
                        subjectCateDAO.getSubjectCateBySubject(subjectId)));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return allSubject;
    }

    /**
     *
     * @return @throws Exception Get featured subjects
     */
    @Override
    public ArrayList<Subject> getFeaturedSubjects() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Subject> featuredSubjects = new ArrayList();
        DimensionDAO dimensionDAO = new DimensionDAOImpl();
        SubjectCateDAO subjectCateDAO = new SubjectCateDAOImpl();

        String sqlSubject = "SELECT * FROM [Subject] WHERE featuredSubject = 1";

        /* Get the subject */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sqlSubject);
            rs = pre.executeQuery();
            /* Get information from resultset and add it to arrayList */
            while (rs.next()) {
                int subjectId = rs.getInt("subjectId");
                String subjectName = rs.getString("subjectName");
                String description = rs.getString("description");
                String thumbnail = rs.getString("thumbnail");
                Boolean featured = rs.getBoolean("featuredSubject");
                Boolean status = rs.getBoolean("status");

                featuredSubjects.add(new Subject(subjectId, subjectName, description,
                        thumbnail, featured, status,
                        dimensionDAO.getDimensionBySubject(subjectId),
                        subjectCateDAO.getSubjectCateBySubject(subjectId)));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return featuredSubjects;
    }

    /**
     *
     * @param userId
     * @return
     * @throws Exception Get subjects assigned by certain expert
     */
    @Override
    public ArrayList<Subject> getSubjectsAssigned(int userId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Subject> assignedSubjects = new ArrayList();
        DimensionDAO dimensionDAO = new DimensionDAOImpl();
        SubjectCateDAO subjectCateDAO = new SubjectCateDAOImpl();
        /* Sql query */
        String sqlSubject = "SELECT S.[subjectId],[subjectName],[description],[thumbnail],[featuredSubject],S.[status],SE.[userId]\n"
                + "  FROM [QuizSystem].[dbo].[Subject] S INNER JOIN [QuizSystem].dbo.[SubjectExpert] SE\n"
                + "  ON S.subjectId = SE.subjectId WHERE SE.userId = ?";

        /* Get the subject */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sqlSubject);
            pre.setInt(1, userId);
            rs = pre.executeQuery();
            /* Get information from resultset and add it to arrayList */
            while (rs.next()) {
                int subjectId = rs.getInt("subjectId");
                String subjectName = rs.getString("subjectName");
                String description = rs.getString("description");
                String thumbnail = rs.getString("thumbnail");
                Boolean featured = rs.getBoolean("featuredSubject");
                Boolean status = rs.getBoolean("status");

                assignedSubjects.add(new Subject(subjectId, subjectName, description,
                        thumbnail, featured, status,
                        dimensionDAO.getDimensionBySubject(subjectId),
                        subjectCateDAO.getSubjectCateBySubject(subjectId)));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return assignedSubjects;
    }

    /**
     *
     * @param subjectId
     * @return
     * @throws Exception Get subject with a certain Id
     */
    @Override
    public Subject getSubjectbyId(int subjectId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        Subject subjectById = null;
        DimensionDAO dimensionDAO = new DimensionDAOImpl();
        SubjectCateDAO subjectCateDAO = new SubjectCateDAOImpl();

        String sqlSubject = "SELECT * FROM [Subject] WHERE [subjectId] = ?";

        /* Get the subject */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sqlSubject);
            pre.setInt(1, subjectId);
            rs = pre.executeQuery();
            /* Get information from resultset and set it to the created pointer */
            if (rs.next()) {
                int subjectIdResult = rs.getInt("subjectId");
                String subjectName = rs.getString("subjectName");
                String description = rs.getString("description");
                String thumbnail = rs.getString("thumbnail");
                Boolean featuredSubject = rs.getBoolean("featuredSubject");
                Boolean status = rs.getBoolean("status");

                subjectById = new Subject(subjectId, subjectName, description,
                        thumbnail, featuredSubject, status,
                        dimensionDAO.getDimensionBySubject(subjectId),
                        subjectCateDAO.getSubjectCateBySubject(subjectId));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return subjectById;
    }

    /**
     *
     * @param cateId
     * @return
     * @throws Exception Get the subject list that has a certain category id
     */
    @Override
    public ArrayList<Subject> getSubjectbyCateId(int cateId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        ArrayList<Subject> subjectByCate = new ArrayList();

        String sql = "SELECT S.[subjectId]\n"
                + "  FROM [QuizSystem].[dbo].[Subject] S\n"
                + "  INNER JOIN [QuizSystem].[dbo].CategorySubject CS ON S.subjectId = CS.subjectId\n"
                + "  WHERE CS.cateId = ?";
        
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, cateId);
            rs = pre.executeQuery();
            /* Get information from resultset, get the according subject and add it to arrayList */
            while (rs.next()) {
                subjectByCate.add(getSubjectbyId(rs.getInt("subjectId")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return subjectByCate;
    }

    /**
     * Update subject with certain id
     * @param subjectId
     * @param subject
     * @return
     * @throws Exception 
     */
    @Override
    public int updateSubject(int subjectId, Subject subject) throws Exception {
        int i = 0;
        return i;
    }

    /**
     *  Method to perform the single-value parameters of subject
     * @param subjectId
     * @param subject
     * @return
     * @throws Exception 
     */
    @Override
    public int updateSubjectBasic(int subjectId, Subject subject) throws Exception {
        int i = 0;
        Connection conn = null;
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "UPDATE Subject\n"
                + "  SET subjectName = ?,\n"
                + "  description = ?,\n"
                + "  thumbnail = ?,\n"
                + "  featuredSubject = ?,\n"
                + "  status = ?\n"
                + "  WHERE subjectId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, subject.getSubjectName());
            pre.setString(2, subject.getDescription());
            pre.setString(3, subject.getThumbnail());
            pre.setBoolean(4, subject.isFeaturedSubject());
            pre.setBoolean(5, subject.isStatus());
            pre.setInt(6, subjectId);
            i = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return i;
    }

    /**
     * Add new subject into database
     * @param newSubject
     * @return
     * @throws Exception 
     */
    @Override
    public int addSubject(Subject newSubject) throws Exception {
        int check = 0;
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String sql = "  insert into Subject(subjectName, description,thumbnail,featuredSubject,status) values (?,?,?,?,?)";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, newSubject.getSubjectName());
            pre.setString(2, newSubject.getDescription());
            pre.setString(3, newSubject.getThumbnail());
            pre.setBoolean(4, newSubject.isFeaturedSubject());
            pre.setBoolean(5, newSubject.isStatus());

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
     * Delete subject with a certain id
     * @param subjectId
     * @return
     * @throws Exception 
     */
    @Override
    public int deleteSubject(int subjectId) throws Exception {
        int i = 0;
        return i;
    }

    /**
     * Get available subject paginated
     * @param page
     * @return
     * @throws Exception 
     */
    @Override
    public ArrayList<Subject> getSubjectsPaging(int page) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Subject> allSubject = new ArrayList();
        DimensionDAO dimensionDAO = new DimensionDAOImpl();
        SubjectCateDAO subjectCateDAO = new SubjectCateDAOImpl();

        String sqlSubject = "SELECT * FROM \n" +
                            "(SELECT * \n" +
                            "		,ROW_NUMBER()  OVER(ORDER BY subjectId ASC) as num\n" +
                            "		FROM [Subject] WHERE status=1) A\n" +
                            "WHERE A.num BETWEEN ? AND ?;";
        /* Get the subject */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sqlSubject);
            pre.setInt(1, (page-1)*7+1);
            pre.setInt(2, page*7);
            rs = pre.executeQuery();
            /* Get information from resultset and add it to arrayList */
            while (rs.next()) {
                int subjectId = rs.getInt("subjectId");
                String subjectName = rs.getString("subjectName");
                String description = rs.getString("description");
                String thumbnail = rs.getString("thumbnail");
                Boolean featured = rs.getBoolean("featuredSubject");
                Boolean status = rs.getBoolean("status");

                allSubject.add(new Subject(subjectId, subjectName, description,
                        thumbnail, featured, status,
                        dimensionDAO.getDimensionBySubject(subjectId),
                        subjectCateDAO.getSubjectCateBySubject(subjectId)));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return allSubject;
    }
    
    /**
     * Get all subject paginated
     * @param page
     * @return
     * @throws Exception 
     */
    @Override
    public ArrayList<Subject> getTrueSubjectsPaging(int page) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Subject> allSubject = new ArrayList();
        DimensionDAO dimensionDAO = new DimensionDAOImpl();
        SubjectCateDAO subjectCateDAO = new SubjectCateDAOImpl();

        String sqlSubject = "SELECT * FROM \n" +
                            "(SELECT * \n" +
                            "		,ROW_NUMBER()  OVER(ORDER BY subjectId ASC) as num\n" +
                            "		FROM [Subject]) A\n" +
                            "WHERE A.num BETWEEN ? AND ?;";
        /* Get the subject */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sqlSubject);
            pre.setInt(1, (page-1)*7+1);
            pre.setInt(2, page*7);
            rs = pre.executeQuery();
            /* Get information from resultset and add it to arrayList */
            while (rs.next()) {
                int subjectId = rs.getInt("subjectId");
                String subjectName = rs.getString("subjectName");
                String description = rs.getString("description");
                String thumbnail = rs.getString("thumbnail");
                Boolean featured = rs.getBoolean("featuredSubject");
                Boolean status = rs.getBoolean("status");

                allSubject.add(new Subject(subjectId, subjectName, description,
                        thumbnail, featured, status,
                        dimensionDAO.getDimensionBySubject(subjectId),
                        subjectCateDAO.getSubjectCateBySubject(subjectId)));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return allSubject;
    }
    
    /**
     * Get All subject assigned to an expert paginated
     * @param userId
     * @param page
     * @return
     * @throws Exception 
     */
    @Override
    public ArrayList<Subject> getSubjectsAssignedPaging(int userId, int page) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Subject> subjectAssigned = new ArrayList();
        DimensionDAO dimensionDAO = new DimensionDAOImpl();
        SubjectCateDAO subjectCateDAO = new SubjectCateDAOImpl();

        String sqlSubject = "SELECT * FROM (SELECT ROW_NUMBER()  OVER(ORDER BY S.subjectId ASC) as num\n" +
                            "			,S.[subjectId],[subjectName],[description],[thumbnail],[featuredSubject],S.[status],SE.[userId]\n" +
                            "           	FROM [QuizSystem].dbo.[Subject] S INNER JOIN [QuizSystem].dbo.[SubjectExpert] SE\n" +
                            "                   ON S.subjectId = SE.subjectId WHERE SE.userId = ?) A\n" +
                            "	WHERE A.num BETWEEN ? AND ?;";
        /* Get the subject */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sqlSubject);
            pre.setInt(1, userId);
            pre.setInt(2, (page-1)*7+1);
            pre.setInt(3, page*7);
            rs = pre.executeQuery();
            /* Get information from resultset and add it to arrayList */
            while (rs.next()) {
                int subjectId = rs.getInt("subjectId");
                String subjectName = rs.getString("subjectName");
                String description = rs.getString("description");
                String thumbnail = rs.getString("thumbnail");
                Boolean featured = rs.getBoolean("featuredSubject");
                Boolean status = rs.getBoolean("status");

                subjectAssigned.add(new Subject(subjectId, subjectName, description,
                        thumbnail, featured, status,
                        dimensionDAO.getDimensionBySubject(subjectId),
                        subjectCateDAO.getSubjectCateBySubject(subjectId)));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return subjectAssigned;
    }
    
    /* Test DAO */
//    public static void main(String[] args) {
//        try {
//            SubjectDAOImpl dao = new SubjectDAOImpl();
//            ArrayList<Subject> subjectA = dao.getSubjectsAssignedPaging(6,1);
//            for (Subject subject : subjectA) {
//                if (subject==null) System.out.println("null");
//                else {
//                    System.out.println(subject.toString());
//                }
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(SubjectDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
