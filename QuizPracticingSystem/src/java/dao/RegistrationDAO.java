
package dao;

import bean.*;
import java.util.ArrayList;


public interface RegistrationDAO {

    /**
     * getAllRegistration
     *
     * @return
     * @throws Exception
     */
    public ArrayList<Registration> getAllRegistration() throws Exception;

    /**
     * getRegistrationById
     *
     * @param registrationId
     * @return
     * @throws Exception
     */
    public Registration getRegistrationById(int registrationId) throws Exception;

    /**
     * addRegistration
     *
     * @param newRegistration
     * @return
     * @throws Exception
     */
    public int addRegistration(Registration newRegistration) throws Exception;

    /**
     * editRegistration
     *
     * @param registrationId
     * @param editedRegistration
     * @return
     * @throws Exception
     */
    public int editRegistration(int registrationId, Registration editedRegistration) throws Exception;

    public int deleteRegistration(int registrationId) throws Exception;

    /**
     * get statistic from database
     *
     *
     * @param from Lower range limit. <code>String</code>
     * @param to Upper range limit. <code>String</code>
     * @param subjectList list of target subject.
     * <code>java.util.ArrayList</code> object
     * @param type revenue or registration. <code>String</code>
     * @return list of statistics data. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    public ArrayList<ItemDashboard> getSubjectStatistics(String from, String to, ArrayList<Subject> subjectList, String type) throws Exception;

    /**
     * get statistic from database
     *
     *
     * @param from Lower range limit. <code>String</code>
     * @param to Upper range limit. <code>String</code>
     * @return list of statistics data. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    public ArrayList<ItemDashboard> getRegistrationStatistics(String from, String to) throws Exception;

    /**
     * get statistic from database
     *
     *
     * @param from Lower range limit. <code>String</code>
     * @param to Upper range limit. <code>String</code>
     * @return list of statistics data. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    public ArrayList<ItemDashboard> getRevenueStatistics(String from, String to) throws Exception;

    /**
     * get statistic from database
     *
     *
     * @param from Lower range limit. <code>String</code>
     * @param to Upper range limit. <code>String</code>
     * @return list of statistics data. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    public ArrayList<ItemDashboard> getRevenueStatisticsBySubjectCate(String from, String to) throws Exception;

    /**
     * get new registration from database
     *
     * @return list of new registrations. It is a
     * <code>java.util.ArrayList</code>object.
     * @throws java.lang.Exception
     */
    public ArrayList<Registration> get10NewRegistration() throws Exception;

    /**
     * Convert statistics data into JSon string
     *
     * @param viewList statistics data
     * @return JSon strings of data. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    public ArrayList<String> convertJson(ArrayList<ItemDashboard> viewList) throws Exception;

    /**
     * get name of each JSon string
     *
     * @param viewList statistics data
     * @return names of data. It is a <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    public ArrayList<String> getNameList(ArrayList<ItemDashboard> viewList) throws Exception;

    /**
     *
     * @param userId
     * @return @throws Exception get registed subject by user's Id
     */
    public ArrayList<Subject> getRegistedSubject(int userId) throws Exception;

    /**
     *
     * @param userId
     * @return @throws Exception get registed subject by user's Id
     */
    public ArrayList<Subject> getRegistedSubjectbyUserId(int userId) throws Exception;

    /**
     * get paid registration
     *
     * @param type "true" or "false" == paid or unpaid
     * @return list of registration. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    public ArrayList<Registration> getPaidRegistration(String type) throws Exception;

    /**
     * getFilterRegistration
     *
     * @param subjectId
     * @param userId
     * @return
     * @throws Exception
     */
    public ArrayList<RegistrationManage> getFilterRegistration(int subjectId, int userId) throws Exception;
}
