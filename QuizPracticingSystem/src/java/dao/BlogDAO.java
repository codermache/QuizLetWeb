
package dao;

import bean.Blog;
import bean.PostCate;
import bean.User;
import java.util.ArrayList;


public interface BlogDAO {
    
    /**
     * Get all blog from database
     *
     * @return a list of <code>Blog</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    public ArrayList<Blog> getAllBlog() throws Exception;
    
    
    /**
     * Get all blog from database by blog's category
     *
     * @param postCateIdList the list of filter categories. It is an array of
     * String
     * @return a list of <code>Blog</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    public ArrayList<Blog> getBlogByCategory(String[] postCateIdList) throws Exception;
    
    
    /**
     * Get all blog from database by blog's author
     *
     * @param userId author's user ID. It is a <code>Integer</code>
     * @return a list of <code>Blog</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    public ArrayList<Blog> getBlogByUser(int userId) throws Exception;
    
    
    /**
     * Get blog from database by blog's id
     *
     * @param blogId blog's ID. It is a <code>Integer</code>
     * @return a <code>Blog</code> objects
     * @throws java.lang.Exception
     */
    public Blog getBlogById(int blogId) throws Exception;
    
    
    /**
     * Get blog from database by blog's title
     *
     * @param title blog's title. It is a <code>String</code>
     * @return a <code>Blog</code> objects
     * @throws java.lang.Exception
     */
    public ArrayList<Blog> getBlogByTitle(String title) throws Exception;
    
    
     /**
     * Get blog from database filter by category and title
     *
     * @param postCateIdList filter blog's categories. It is a
     * <code>java.util.ArrayList</code>
     * @param search search string. It is a <code>String</code>
     * @return List of <code>Blog</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     * 
     */
    public ArrayList<Blog> getBlogByCategoryAndTitle(String[] postCateIdList, String search) throws Exception;
    
    
    /**
     * Get all blog from database where blog's status is true
     *
     * @return List of <code>Blog</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    public ArrayList<Blog> getAllTrueBlog() throws Exception;
    
    
     /**
     * Edit blog information in database
     *
     * @param blogId id of the target blog. It is a <code>int</code>
     * @param blog carry edited information. It is a <code>Blog</code> object
     * @return number of changes in database. It is a <code>int</code> object
     * @throws java.lang.Exception
     */
    public int editBlog(int blogId, Blog blog) throws Exception;
    
    
    /**
     * add new blog into database
     *
     * @param blog adding target. It is a <code>Blog</code> object
     * @return number of changes in database. It is a <code>int</code> object
     * @throws java.lang.Exception
     */
    public int addBlog(Blog blog) throws Exception;
    
    
    /**
     * delete in database
     *
     * @param blogId the target blog. It is a <code>int</code>
     * @return number of changes in database. It is a <code>int</code> object
     * @throws java.lang.Exception
     */
    public int deleteBlog(int blogId) throws Exception;
    
    
    /**
     * Get 3 latest blog from database
     *
     * @return List of <code>Blog</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    public ArrayList<Blog> getLastBlogs() throws Exception;
    
    
    /**
     * get blog's author
     *
     * @param blogId blog target. It is a <code>int</code> object
     * @return blog's author. It is a <code>User</code> object
     * @throws java.lang.Exception
     */
    public User getAuthor(int blogId) throws Exception;
    
    
    /**
     * get blog's category
     *
     * @param blogId blog target. It is a <code>int</code> object
     * @return blog's category. It is a <code>PostCate</code> object
     * @throws java.lang.Exception
     */
    public ArrayList<PostCate> getBlogCategory(int blogId) throws Exception;
            
    
    /**
     * divide a list of blog into many sublist(page)
     *
     * @param page blog target. It is a <code>int</code> object
     * @param list the target list. It is a <code>java.util.ArrayList</code>
     *
     * @return sublist of blog list. It is a <code>java.util.ArrayList</code>
     * @throws java.lang.Exception
     */
    public ArrayList<Blog> Paging(int page, ArrayList<Blog> list) throws Exception;
    
    /**
     * get id of blog that have the same attribute with the search blog
     * @param searchBlog. It is a <code>Blog</code> object
     * @return a blog id. It is a <code>int</code> object
     * @throws Exception 
     */
    public int getCreatedBlogID(Blog searchBlog) throws Exception;
    
    /**
     * add blog category to database
     * @param blogId. It is a <code>int</code> object
     * @param categoryId. It is a <code>int</code> object
     * @return number of row change. It is a <code>int</code> object
     * @throws Exception 
     */
    public int addBlogCategory(int blogId,int categoryId) throws Exception;
    
    /**
     * remove all blog's category
     * @param blogId. It is a <code>int</code> object
     * @return number of row change. It is a <code>int</code> object
     * @throws Exception 
     */
    public int removeAllBlogCategory(int blogId) throws Exception;
}
