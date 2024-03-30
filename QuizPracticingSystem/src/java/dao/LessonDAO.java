
package dao;

import bean.Lesson;
import java.util.ArrayList;

public interface LessonDAO {

    /**
     * Get all lessons from database
     *
     * @return a list of <code>Lesson</code> objects. It is a
     * <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    public ArrayList<Lesson> getAllLessons() throws Exception;

    /**
     * Get lessons of a subject
     *
     * @param subjectId
     * @return
     * @throws Exception
     */
    public ArrayList<Lesson> getAllLessonBySubjectId(int subjectId) throws Exception;

    public ArrayList<Lesson> getAllLessonByTypeId(int typeId) throws Exception;

    /**
     * Get lesson from database by lesson's id
     *
     * @param lessonId lesson's ID. It is a <code>Integer</code>
     * @return a <code>Lesson</code> objects
     * @throws java.lang.Exception
     */
    public Lesson getLessonById(int lessonId) throws Exception;

    /**
     * update Lesson
     *
     * @param lessonId the target lessonId. It is a <code>int</code>
     * @param updatedLesson the target updatedLesson. It is a
     * <code>Object</code>
     * @return check. It is a <code>int</code>
     * @throws Exception
     */
    public int updateLesson(int lessonId, Lesson updatedLesson) throws Exception;

    public int deleteLesson(int lessonId) throws Exception;

    /**
     * add New lesson
     *
     * @param newLesson It is a <code>Object</code> primitive type
     * @return count. It is a <code>int</code> object.
     * @throws java.lang.Exception
     */
    public int addLesson(Lesson newLesson) throws Exception;
}
