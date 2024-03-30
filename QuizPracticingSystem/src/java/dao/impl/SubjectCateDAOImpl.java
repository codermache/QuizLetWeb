
package dao.impl;

import bean.SubjectCate;
import dao.DBConnection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import dao.SubjectCateDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

public class SubjectCateDAOImpl extends DBConnection implements SubjectCateDAO {

    /**
     * Get all subject categories
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<SubjectCate> getAllSubjectCates() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
 /* Get category list */
        ArrayList<SubjectCate> allCategory = new ArrayList<>();
        String sql = "SELECT [subjectCateId]\n"
                + "      ,[subjectCateName]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[SubjectCate] where status = 1";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                allCategory.add(new SubjectCate(rs.getInt("subjectCateId"),
                        rs.getString("subjectCateName"),
                        rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return allCategory;
    }

    /**
     * Get subject count by subject categories
     *
     * @return <code>HashMap</code>
     * @throws Exception
     */
    @Override
    public HashMap<String, Integer> getSubjectCountByCate() throws Exception {
        HashMap<String, Integer> map = new HashMap();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pre = null;
        String sql = "SELECT subjectCateName, COUNT(cateId) AS number "
                + "FROM SubjectCate AS a JOIN CategorySubject AS b "
                + "ON a.subjectCateId = b.cateId "
                + "GROUP BY cateId,subjectCateName";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                map.put(rs.getString("subjectCateName"), rs.getInt("number"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return map;
    }

    /**
     * Get subject category by Id
     *
     * @param scId Subject Category ID
     * @return
     * @throws Exception
     */
    @Override
    public SubjectCate getSubjectCateById(int scId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
 /* Get category list */
        String sql = "SELECT [subjectCateId]\n"
                + "      ,[subjectCateName]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[SubjectCate]\n where subjectCateId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, scId);
            rs = pre.executeQuery();
            if (rs.next()) {
                return new SubjectCate(rs.getInt("subjectCateId"), rs.getString("subjectCateName"), rs.getBoolean("status"));
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
     * Get subject categories of a specified subject
     *
     * @param subjectId
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<SubjectCate> getSubjectCateBySubject(int subjectId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
 /* Getcategory list of the subject */
        ArrayList<SubjectCate> categories = new ArrayList<>();
        String sql = "SELECT C.[subjectId]\n"
                + "      ,C.[cateId]\n"
                + "	   ,S.[status]\n"
                + "	   ,S.subjectCateName\n"
                + "  FROM [QuizSystem].[dbo].[CategorySubject] C \n"
                + "  INNER JOIN [QuizSystem].[dbo].SubjectCate S\n"
                + "  ON C.cateId = S.subjectCateId WHERE C.subjectId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, subjectId);
            rs = pre.executeQuery();
            while (rs.next()) {
                categories.add(new SubjectCate(rs.getInt("cateId"), rs.getString("subjectCateName"), rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return categories;
    }

    /**
     * Get subject category ids of a specified subject
     *
     * @param subjectId
     * @return
     * @throws Exception
     */
    @Override
    public String[] getSubjectCateIdBySubject(int subjectId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
 /* Getcategory list of the subject */
        ArrayList<String> categoryId = new ArrayList<>();
        String sql = "SELECT C.[cateId]\n"
                + "  FROM [QuizSystem].[dbo].[CategorySubject] C \n"
                + "  INNER JOIN [QuizSystem].[dbo].SubjectCate S\n"
                + "  ON C.cateId = S.subjectCateId WHERE C.subjectId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, subjectId);
            rs = pre.executeQuery();
            while (rs.next()) {
                categoryId.add(rs.getString("cateId"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }

        String[] idString = new String[categoryId.size()];
        return categoryId.toArray(idString);
    }

    /**
     * Get subject categories that is not referenced by a specified subject
     *
     * @param subjectId
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<SubjectCate> getRemainSubjectCateBySubject(int subjectId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
 /* Getcategory list of the subject */
        ArrayList<SubjectCate> remainCategories = new ArrayList<>();
        String sql = "SELECT [subjectCateId]\n"
                + "      ,[subjectCateName]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[SubjectCate]\n"
                + "  WHERE [subjectCateId] NOT IN (SELECT C.[cateId]\n"
                + "				FROM [QuizSystem].[dbo].[CategorySubject] C\n"
                + "				INNER JOIN [QuizSystem].[dbo].SubjectCate S\n"
                + "				ON C.cateId = S.subjectCateId WHERE C.subjectId = ?)";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, subjectId);
            rs = pre.executeQuery();
            while (rs.next()) {
                remainCategories.add(new SubjectCate(rs.getInt("subjectCateId"), rs.getString("subjectCateName"), rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return remainCategories;
    }

    /**
     * Add new subject Category
     *
     * @param updatedSubjectCate Subject Category
     * @return
     * @throws Exception
     */
    @Override
    public int addSubjectCate(SubjectCate newSubjectCate) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String sql = "INSERT INTO dbo.SubjectCate(subjectCateName,status) VALUES(?,1)";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, newSubjectCate.getSubjectCateName());
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
     * Add new category of a subject
     *
     * @param subjectId
     * @param categoryId
     * @return
     * @throws Exception
     */
    @Override
    public int addCategorySubject(int subjectId, int categoryId) throws Exception {
        Connection conn = null;
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "INSERT INTO dbo.CategorySubject(subjectId,cateId) "
                + "VALUES(?,?)";
        int check = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, subjectId);
            pre.setInt(2, categoryId);
            check = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return check;
    }

    /**
     * Update subject Category
     *
     * @param subjectCategoryId
     * @param updatedSubjectCate Update Subject Category
     * @return
     * @throws Exception
     */
    @Override
    public int updateSubjectCate(SubjectCate updatedSubjectCate) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String sql = "  UPDATE [QuizSystem].[dbo].[SubjectCate]\n"
                + "  SET [subjectCateName] = ?\n"
                + "      ,[status] = ?\n"
                + "WHERE [subjectCateId] = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, updatedSubjectCate.getSubjectCateName());
            pre.setBoolean(2, updatedSubjectCate.isStatus());
            pre.setInt(3, updatedSubjectCate.getSubjectCateId());
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
     * Delete subject Category
     *
     * @param scId Subject Category ID
     * @return
     * @throws Exception
     */
    @Override
    public int deteleSubjectCate(int scId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Delete relation between subject and subject category
     *
     * @param subjectId
     * @param categoryId
     * @return
     * @throws Exception
     */
    @Override
    public int deteleCategorySubject(int subjectId, int categoryId) throws Exception {
        Connection conn = null;
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "DELETE FROM dbo.CategorySubject "
                + "WHERE subjectId = ? AND "
                + "cateId = ?";
        int check = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, subjectId);
            pre.setInt(2, categoryId);
            check = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return check;
    }

    /**
     * Get all subject categories
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<SubjectCate> getAllStatusSubjectCates() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
 /* Get category list */
        ArrayList<SubjectCate> allCategory = new ArrayList<>();
        String sql = "SELECT [subjectCateId]\n"
                + "      ,[subjectCateName]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[SubjectCate]";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                allCategory.add(new SubjectCate(rs.getInt("subjectCateId"),
                        rs.getString("subjectCateName"),
                        rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return allCategory;
    }
}
