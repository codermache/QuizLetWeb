
package dao.impl;

import java.util.ArrayList;
import bean.PostCate;
import dao.DBConnection;
import java.sql.PreparedStatement;
import dao.PostCateDAO;
import java.sql.Connection;
import java.sql.ResultSet;

public class PostCateDAOImpl extends DBConnection implements PostCateDAO {

    /**
     * get all psot categories where status = 1
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<PostCate> getAllPostCates() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<PostCate> allPostCate = new ArrayList();
        String sql = "SELECT * FROM [PostCate] where status = 1 ";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                allPostCate.add(new PostCate(rs.getInt("postCateId"),
                        rs.getString("postCateName"),
                        rs.getBoolean("status")));
            }
            return allPostCate;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    /**
     * get post categoory by id
     *
     * @param postCateId
     * @return
     * @throws Exception
     */
    @Override
    public PostCate getPostCateById(int pcId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "SELECT * FROM [PostCate] WHERE postCateId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, pcId);
            rs = pre.executeQuery();
            while (rs.next()) {
                return new PostCate(rs.getInt("postCateId"),
                        rs.getString("postCateName"),
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
     * get blog category id by blog id
     *
     * @param blogId
     * @return
     * @throws Exception
     */
    @Override
    public int getBlogCateByBlogId(int blogId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "SELECT * FROM [BlogCate] WHERE blogId=" + blogId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt("postCateId");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return 0;
    }

    /**
     * update a existed post category in the database
     *
     * @param updatedPostCate
     * @return
     * @throws Exception
     */
    @Override
    public int updatePostCate(PostCate updatedPostCate) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "UPDATE [QuizSystem].[dbo].[PostCate]\n"
                + "  SET [postCateName] = ?\n"
                + "      ,[status] = ?\n"
                + "WHERE [postCateId] = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, updatedPostCate.getPostCateName());
            pre.setBoolean(2, updatedPostCate.isStatus());
            pre.setInt(3, updatedPostCate.getPostCateId());
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
     * delete a post category from database
     *
     * @param postCateId
     * @return
     * @throws Exception
     */
    @Override
    public int deletePostCate(int pcId) throws Exception {
        return 0;
    }

    /**
     * add new post category to database
     *
     * @param newPostCate
     * @return
     * @throws Exception
     */
    @Override
    public int addPostCate(PostCate newPostCate) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "INSERT INTO dbo.PostCate(postCateName,status) VALUES(?,1)";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, newPostCate.getPostCateName());
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
     * get all psot categories
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<PostCate> getAllStatusPostCates() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<PostCate> allPostCate = new ArrayList();
        String sql = "SELECT * FROM [PostCate]";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                allPostCate.add(new PostCate(rs.getInt("postCateId"),
                        rs.getString("postCateName"),
                        rs.getBoolean("status")));
            }
            return allPostCate;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }
}
