
package dao;

import bean.LessonType;
import java.util.ArrayList;

public interface LessonTypeDAO {
    /**
     * get all lesson type from the database have status = 1
     * @return
     * @throws Exception 
     */
    public ArrayList<LessonType> getAllLessonType() throws Exception;
    
    /**
     * get lesson type by id
     * @param ltId
     * @return
     * @throws Exception 
     */
    public LessonType getLessonTypeById(int ltId) throws Exception;
    
    /**
     * insert new lesson type
     * @param newLessonType
     * @return
     * @throws Exception 
     */
    public int addLessonType(LessonType newLessonType) throws Exception;
    
    /**
     * update existed lesson type
     * @param updatedLessonType
     * @return
     * @throws Exception 
     */
    public int updateLessonType(LessonType updatedLessonType) throws Exception;
    
    /**
     * delete existed lesson 
     * @param lessonTypeId
     * @return
     * @throws Exception 
     */
    public int deleteLessonType(int lessonTypeId) throws Exception;
    
     /**
     * get all lesson type from the database
     * @return
     * @throws Exception 
     */
    public ArrayList<LessonType> getAllStatusLessonType() throws Exception;
}
