
package dao;

import bean.SubjectCate;
import java.util.ArrayList;
import java.util.HashMap;


public interface SubjectCateDAO {

    /**
     * Get all subject categories where status = 1
     *
     * @return
     * @throws Exception
     */
    public ArrayList<SubjectCate> getAllSubjectCates() throws Exception;

    /**
     * Get subject category by Id
     *
     * @param scId Subject Category ID
     * @return
     * @throws Exception
     */
    public SubjectCate getSubjectCateById(int scId) throws Exception;

    /**
     * Get subject categories of a specified subject
     *
     * @param subjectId
     * @return
     * @throws Exception
     */
    public ArrayList<SubjectCate> getSubjectCateBySubject(int subjectId) throws Exception;

    /**
     * Get subject category ids of a specified subject
     *
     * @param subjectId
     * @return
     * @throws Exception
     */
    public String[] getSubjectCateIdBySubject(int subjectId) throws Exception;

    /**
     * Get subject categories that is not referenced by a specified subject
     *
     * @param subjectId
     * @return
     * @throws Exception
     */
    public ArrayList<SubjectCate> getRemainSubjectCateBySubject(int subjectId) throws Exception;

    /**
     * Add new subject Category
     *
     * @param updatedSubjectCate Subject Category
     * @return
     * @throws Exception
     */
    public int addSubjectCate(SubjectCate updatedSubjectCate) throws Exception;

    /**
     * Add new category of a subject
     *
     * @param subjectId
     * @param categoryId
     * @return
     * @throws Exception
     */
    public int addCategorySubject(int subjectId, int categoryId) throws Exception;

    /**
     * Update subject Category
     *
     * @param updatedSubjectCate Update Subject Category
     * @return
     * @throws Exception
     */
    public int updateSubjectCate(SubjectCate updatedSubjectCate) throws Exception;

    /**
     * Delete subject Category
     *
     * @param scId Subject Category ID
     * @return
     * @throws Exception
     */
    public int deteleSubjectCate(int scId) throws Exception;

    /**
     * Delete relation between subject and subject category
     *
     * @param subjectId
     * @param categoryId
     * @return
     * @throws Exception
     */
    public int deteleCategorySubject(int subjectId, int categoryId) throws Exception;

    /**
     * Get all subject categories
     *
     * @return
     * @throws Exception
     */
    public ArrayList<SubjectCate> getAllStatusSubjectCates() throws Exception;

    /**
     * Get subject count by subject categories
     *
     * @return <code>HashMap</code>
     * @throws Exception
     */
    public HashMap<String, Integer> getSubjectCountByCate() throws Exception;
}
