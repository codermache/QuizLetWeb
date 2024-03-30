
package dao.impl;

import bean.Blog;
import bean.PostCate;
import bean.User;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import dao.BlogDAO;
import dao.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlogDAOImpl extends DBConnection implements BlogDAO {

    /**
     * Get all blog from database
     *
     * @return a list of <code>Blog</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Blog> getAllBlog() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        ArrayList<Blog> allBlog = new ArrayList();
        String sql = "SELECT * FROM [Blog] ORDER BY created DESC";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                BlogDAOImpl blogDAO = new BlogDAOImpl();
                User author = blogDAO.getAuthor(rs.getInt("blogId"));
                allBlog.add(new Blog(rs.getInt("blogId"),
                        rs.getString("blogTitle"),
                        rs.getDate("created"),
                        rs.getDate("lastEdited"),
                        author,
                        rs.getString("detail"),
                        rs.getString("thumbnail"),
                        rs.getBoolean("status")));
            }
            return allBlog;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    /**
     * Get all blog from database by blog's category
     *
     * @param postCateIdList the list of filter categories. It is an array of
     * String
     * @return a list of <code>Blog</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Blog> getBlogByCategory(String[] postCateIdList) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        ArrayList<Blog> blogList = new ArrayList();
        int[] cateList = null;
        for (int i = 0; i < postCateIdList.length; i++) {
            cateList[i] = Integer.parseInt(postCateIdList[i]);
        }
        String sql = "SELECT * FROM [Blog] as a join [BlogCate] as b on a.blogId = b.blogId WHERE b.postCateId in (";
        for (int i = 0; i < cateList.length; i++) {
            sql += "," + cateList[i];
        }
        sql += ")";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                BlogDAOImpl blogDAO = new BlogDAOImpl();
                User author = blogDAO.getAuthor(rs.getInt("blogId"));
                blogList.add(new Blog(rs.getInt("blogId"),
                        rs.getString("blogTitle"),
                        rs.getDate("created"),
                        rs.getDate("lastEdited"),
                        author,
                        rs.getString("detail"),
                        rs.getString("thumbnail"),
                        rs.getBoolean("status")));
            }
            return blogList;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    /**
     * Get all blog from database by blog's author
     *
     * @param userId author's user ID. It is a <code>Integer</code>
     * @return a list of <code>Blog</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Blog> getBlogByUser(int userId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        ArrayList<Blog> userBlog = new ArrayList();
        String sql = "SELECT * FROM Blog WHERE author =" + userId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                BlogDAOImpl blogDAO = new BlogDAOImpl();
                User author = blogDAO.getAuthor(rs.getInt("blogId"));
                userBlog.add(new Blog(rs.getInt("blogId"),
                        rs.getString("blogTitle"),
                        rs.getDate("created"),
                        rs.getDate("lastEdited"),
                        author,
                        rs.getString("detail"),
                        rs.getString("thumbnail"),
                        rs.getBoolean("status")));
            }
            return userBlog;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    /**
     * Get blog from database by blog's id
     *
     * @param blogId blog's ID. It is a <code>Integer</code>
     * @return a <code>Blog</code> objects
     * @throws java.lang.Exception
     */
    @Override
    public Blog getBlogById(int blogId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        String sql = "SELECT * FROM Blog WHERE blogId =" + blogId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                BlogDAOImpl blogDAO = new BlogDAOImpl();
                User author = blogDAO.getAuthor(rs.getInt("blogId"));
                return (new Blog(rs.getInt("blogId"),
                        rs.getString("blogTitle"),
                        rs.getDate("created"),
                        rs.getDate("lastEdited"),
                        author,
                        rs.getString("detail"),
                        rs.getString("thumbnail"),
                        rs.getBoolean("status")));
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

    /**
     * Get blog from database by blog's title
     *
     * @param title blog's title. It is a <code>String</code>
     * @return a <code>Blog</code> objects
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Blog> getBlogByTitle(String title) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        ArrayList<Blog> titleBlog = new ArrayList();
        String sql = "SELECT * FROM [Blog] WHERE blogTitle like '%" + title + "%'";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                BlogDAOImpl blogDAO = new BlogDAOImpl();
                User author = blogDAO.getAuthor(rs.getInt("blogId"));
                titleBlog.add(new Blog(rs.getInt("blogId"),
                        rs.getString("blogTitle"),
                        rs.getDate("created"),
                        rs.getDate("lastEdited"),
                        author,
                        rs.getString("detail"),
                        rs.getString("thumbnail"),
                        rs.getBoolean("status")));
            }
            return titleBlog;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    /**
     * Get all blog from database where blog's status is true
     *
     * @return List of <code>Blog</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Blog> getAllTrueBlog() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */
        ArrayList<Blog> allTrueBlog = new ArrayList();

        String sql = "SELECT * FROM [Blog] where status = 1 ORDER BY created DESC";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                BlogDAOImpl blogDAO = new BlogDAOImpl();
                User author = blogDAO.getAuthor(rs.getInt("blogId"));
                allTrueBlog.add(new Blog(rs.getInt("blogId"),
                        rs.getString("blogTitle"),
                        rs.getDate("created"),
                        rs.getDate("lastEdited"),
                        author,
                        rs.getString("detail"),
                        rs.getString("thumbnail"),
                        rs.getBoolean("status")));
            }
            return allTrueBlog;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }

    }

    /**
     * Get 3 latest blog from database
     *
     * @return List of <code>Blog</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Blog> getLastBlogs() throws Exception {
        Connection conn = null;
        ResultSet rs = null;/* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        ArrayList<Blog> lastBlog = new ArrayList();
        String sql = "SELECT TOP 3 * FROM [Blog] where status = 1 ORDER BY created DESC";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                BlogDAOImpl blogDAO = new BlogDAOImpl();
                User author = blogDAO.getAuthor(rs.getInt("blogId"));
                lastBlog.add(new Blog(rs.getInt("blogId"),
                        rs.getString("blogTitle"),
                        rs.getDate("created"),
                        rs.getDate("lastEdited"),
                        author,
                        rs.getString("detail"),
                        rs.getString("thumbnail"),
                        rs.getBoolean("status")));
            }
            return lastBlog;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    /**
     * Get blog from database filter by category and title
     *
     * @param postCateIdList filter blog's categories. It is a
     * <code>java.util.ArrayList</code>
     * @param search search string. It is a <code>String</code>
     * @return List of <code>Blog</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Blog> getBlogByCategoryAndTitle(String[] postCateIdList, String search) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        ArrayList<Blog> blogList = new ArrayList();
        String sql = "SELECT a.blogId,a.blogTitle,a.created,a.detail,a.lastEdited,a.status,a.thumbnail,a.author "
                + "FROM [Blog] as a join [BlogCate] as b on a.blogId = b.blogId WHERE a.status = 1";
        if (postCateIdList != null) {
            int[] cateList = new int[postCateIdList.length];
            for (int i = 0; i < postCateIdList.length; i++) {
                cateList[i] = Integer.parseInt(postCateIdList[i]);
            }
            sql += " AND b.postCateId in (";
            for (int i = 0; i < cateList.length; i++) {
                sql += cateList[i] + ",";
            }
            sql += cateList[postCateIdList.length - 1];
            sql += ")";
        }
        String checkSearch = search.replaceAll(" ", "");
        if (checkSearch.length() != 0) {
            sql += " AND a.blogTitle like '%" + search.toLowerCase() + "%'";
        }
        sql += " GROUP BY a.blogId,a.blogTitle,a.created,a.detail,a.lastEdited,a.status,a.thumbnail,a.author "
                + "ORDER BY created DESC";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                BlogDAOImpl blogDAO = new BlogDAOImpl();
                User author = blogDAO.getAuthor(rs.getInt("blogId"));
                blogList.add(new Blog(rs.getInt("blogId"),
                        rs.getString("blogTitle"),
                        rs.getDate("created"),
                        rs.getDate("lastEdited"),
                        author,
                        rs.getString("detail"),
                        rs.getString("thumbnail"),
                        rs.getBoolean("status")));
            }
            return blogList;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    /**
     * Edit blog information in database
     *
     * @param blogId id of the target blog. It is a <code>int</code>
     * @param blog carry edited information. It is a <code>Blog</code> object
     * @return number of changes in database. It is a <code>int</code> object
     * @throws java.lang.Exception
     */
    @Override
    public int editBlog(int blogId, Blog blog) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        String sql = "UPDATE [Blog] SET blogTitle =?, created =?, lastEdited =?, author =?, detail =?, thumbnail =?, status =? WHERE blogId =?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, blog.getBlogTitle());
            pre.setDate(2, blog.getCreated());
            pre.setDate(3, blog.getLastEdited());
            pre.setInt(4, blog.getAuthor().getUserId());
            pre.setString(5, blog.getDetail());
            pre.setString(6, blog.getThumbnail());
            pre.setInt(7, blog.getStatus() == true ? 1 : 0);
            pre.setInt(8, blogId);
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
     * add new blog into database
     *
     * @param blog adding target. It is a <code>Blog</code> object
     * @return number of changes in database. It is a <code>int</code> object
     * @throws java.lang.Exception
     */
    @Override
    public int addBlog(Blog blog) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        String sql = "insert into Blog(blogTitle,created,lastEdited,author,detail,thumbnail,status)"
                + " values(?,GETDATE(),GETDATE(),?,?,?,1)";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, blog.getBlogTitle());
            pre.setInt(2, blog.getAuthor().getUserId());
            pre.setString(3, blog.getDetail());
            pre.setString(4, blog.getThumbnail());
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
     * delete in database
     *
     * @param blogId the target blog. It is a <code>int</code>
     * @return number of changes in database. It is a <code>int</code> object
     * @throws java.lang.Exception
     */
    @Override
    public int deleteBlog(int blogId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        String sql = "DELETE FROM [Blog] WHERE blogId =" + blogId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            return pre.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }

    }

    /**
     * get blog's author
     *
     * @param blogId blog target. It is a <code>int</code> object
     * @return blog's author. It is a <code>User</code> object
     * @throws java.lang.Exception
     */
    @Override
    public User getAuthor(int blogId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        String sql = "SELECT b.userId,b.userName,b.password,b.roleId,b.profilePic,b.userMail,b.gender,b.userMobile,b.status "
                + "FROM Blog as a right join [User] as b on a.author=b.userId WHERE a.blogId=" + blogId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getInt("roleId"),
                        rs.getString("profilePic"),
                        rs.getString("userMail"),
                        rs.getBoolean("gender"),
                        rs.getString("userMobile"),
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

    /**
     * get blog's category
     *
     * @param blogId blog target. It is a <code>int</code> object
     * @return blog's category. It is a <code>PostCate</code> object
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<PostCate> getBlogCategory(int blogId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */
        ArrayList<PostCate> categoryList = new ArrayList<>();
        PostCate add = null;
        String sql = "SELECT * FROM [BlogCate] as a join [PostCate] as b ON a.postCateId=b.postCateId WHERE a.blogId=" + blogId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                add = new PostCate(rs.getInt("postCateId"),
                        rs.getString("postCateName"),
                        rs.getBoolean("status"));
                categoryList.add(add);
            }
            return categoryList;
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    /**
     * divide a list of blog into many sublist(page)
     *
     * @param page blog target. It is a <code>int</code> object
     * @param list the target list. It is a <code>java.util.ArrayList</code>
     *
     * @return sublist of blog list. It is a <code>java.util.ArrayList</code>
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Blog> Paging(int page, ArrayList<Blog> list) throws Exception {
        //start: index of first element of the sublist
        //end: index of the last element of the sublist
        int start, end;
        int numberpage = 9;// 9 blog a sublist;
        start = (page - 1) * numberpage;
        if (page * numberpage > list.size()) {
            end = list.size();
        } else {
            end = page * numberpage;
        }
        ArrayList<Blog> t = new ArrayList<>();
        for (int i = start; i < end; i++) {
            t.add(list.get(i));
        }
        return t;
    }

    /**
     * get id of blog that have the same attribute with the search blog
     *
     * @param searchBlog. It is a <code>Blog</code> object
     * @return a blog id. It is a <code>int</code> object
     * @throws Exception
     */
    @Override
    public int getCreatedBlogID(Blog searchBlog) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        String sql = "SELECT TOP 1 [blogId]\n"
                + "      ,[blogTitle]\n"
                + "      ,[created]\n"
                + "      ,[lastEdited]\n"
                + "      ,[author]\n"
                + "      ,[detail]\n"
                + "      ,[thumbnail]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[Blog]\n"
                + "  WHERE [blogTitle] = ?\n"
                + "      AND [author] = ?\n"
                + "      AND [detail] = ?\n"
                + "      AND [thumbnail] = ?\n"
                + "ORDER BY blogId DESC";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, searchBlog.getBlogTitle());
            pre.setInt(2, searchBlog.getAuthor().getUserId());
            pre.setString(3, searchBlog.getDetail());
            pre.setString(4, searchBlog.getThumbnail());
            rs = pre.executeQuery();
            while (rs.next()) {
                return rs.getInt("blogId");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return -1;
    }

    /**
     * add blog category to database
     *
     * @param blogId. It is a <code>int</code> object
     * @param categoryId. It is a <code>int</code> object
     * @return number of row change. It is a <code>int</code> object
     * @throws Exception
     */
    @Override
    public int addBlogCategory(int blogId, int categoryId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        String sql = "insert into BlogCate values(?,?,1)";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, blogId);
            pre.setInt(2, categoryId);
            return pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    @Override
    public int removeAllBlogCategory(int blogId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        String sql = " DELETE FROM [QuizSystem].[dbo].[BlogCate] WHERE [blogId] = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, blogId);
            return pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }
}
