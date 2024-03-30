
package dao;

import bean.PostCate;
import java.util.ArrayList;


public interface PostCateDAO {

    /**
     * get all psot categories where status = 1
     * @return
     * @throws Exception 
     */
    public ArrayList<PostCate> getAllPostCates() throws Exception;

    /**
     * get post categoory by id
     * @param postCateId
     * @return
     * @throws Exception 
     */
    public PostCate getPostCateById(int postCateId) throws Exception;

    /**
     * update a existed post category in the database
     * @param updatedPostCate
     * @return
     * @throws Exception 
     */
    public int updatePostCate(PostCate updatedPostCate) throws Exception;

    /**
     * get blog category id by blog id
     * @param blogId
     * @return
     * @throws Exception 
     */
    public int getBlogCateByBlogId(int blogId) throws Exception;

    /**
     * delete a post category from database
     * @param postCateId
     * @return
     * @throws Exception 
     */
    public int deletePostCate(int postCateId) throws Exception;

    /**
     * add new post category to database
     * @param newPostCate
     * @return
     * @throws Exception 
     */
    public int addPostCate(PostCate newPostCate) throws Exception;
    
    /**
     * get all psot categories
     * @return
     * @throws Exception 
     */
    public ArrayList<PostCate> getAllStatusPostCates() throws Exception;
}
