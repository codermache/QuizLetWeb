
package dao;

import bean.DimensionType;
import java.util.ArrayList;

public interface DimensionTypeDAO {
    
    /**
     * Get all dimension type with status = 1
     * @return <code>ArrayList</code>
     * @throws Exception 
     */
    public ArrayList<DimensionType> getAllDimensionTypes() throws Exception;
    
    /**
     * Get dimension type by a specified id
     * @param dimensionTypeId
     * @return
     * @throws Exception 
     */
    public DimensionType getDimensionTypeById(int dimensionTypeId) throws Exception;
    
    /**
     * Update dimension type
     * @param updatedDimensionType
     * @return
     * @throws Exception 
     */
    public int updateDimensionType (DimensionType updatedDimensionType) throws Exception;
    
    /**
     * Delete dimension type
     * @param dtId dimensionID
     * @return
     * @throws Exception 
     */
    public int deteteDimensionType(int dtId) throws Exception;
    
    /**
     * Add new dimension Type
     * @param newDimensionType
     * @return
     * @throws Exception 
     */
    public int addDimensionType(DimensionType newDimensionType ) throws Exception;
    
    /**
     * Get all dimension type
     * @return <code>ArrayList</code>
     * @throws Exception 
     */
    public ArrayList<DimensionType> getAllStatusDimensionTypes() throws Exception;
}
