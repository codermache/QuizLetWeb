
package dao;

import bean.TestType;
import java.util.ArrayList;

public interface TestTypeDAO {

    /**
     * Get all testType in the database where status = 1
     *
     * @return <code>ArrayList<TestType></code>
     * @throws Exception
     */
    public ArrayList<TestType> getAllTestTypes() throws Exception;

    /**
     * get test type by id
     * @param testTypeId
     * @return
     * @throws Exception 
     */
    public TestType getTestTypeById(int testTypeId) throws Exception;

    /**
     * update existed test type in the database
     * @param updatedTestType
     * @return
     * @throws Exception 
     */
    public int updateTestType(TestType updatedTestType) throws Exception;

    /**
     * delete a test type from the database
     * @param testTypeId
     * @return
     * @throws Exception 
     */
    public int deleteTestType(int testTypeId) throws Exception;

    /**
     * add a new test type to the database
     * @param newTestType
     * @return
     * @throws Exception 
     */
    public int addTestType(TestType newTestType) throws Exception;
    
    /**
     * Get all testType in the database
     *
     * @return <code>ArrayList<TestType></code>
     * @throws Exception
     */
    public ArrayList<TestType> getAllStatusTestTypes() throws Exception;
}
