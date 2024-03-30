
package dao;

import bean.ItemDashboard;
import java.util.ArrayList;

public interface ViewDAO {

    /**
     * update View when a session is created
     *
     * @return number of lines changed. It is a <code>int</code>
     * @throws java.lang.Exception
     */
    public int updateView() throws Exception;

    /**
     * check if current date view exist
     *
     * @return <code>boolean</code>
     * @throws java.lang.Exception
     */
    public boolean checkCurrentDateViewExist() throws Exception;

    /**
     * get statistic from database
     *
     * @param from Lower range limit. <code>String</code>
     * @param to Upper range limit. <code>String</code>
     * @return list of statistics data. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    public ArrayList<ItemDashboard> getViewStatistics(String from, String to) throws Exception;

    /**
     * get total view count
     *
     * @return <code>int</code>
     * @throws java.lang.Exception
     */
    public int getTotalView() throws Exception;

    /**
     * Convert statistics data into JSon string
     *
     * @param views statistics data
     * @return JSon strings of data. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    public ArrayList<String> convertJson(ArrayList<ItemDashboard> views) throws Exception;

    /**
     * get name of each JSon string
     *
     * @param viewList statistics data
     * @return names of data. It is a <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    public ArrayList<String> getNameList(ArrayList<ItemDashboard> viewList) throws Exception;

}
