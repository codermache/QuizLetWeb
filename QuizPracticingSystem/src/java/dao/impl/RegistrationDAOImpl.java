
package dao.impl;

import bean.Registration;
import bean.Subject;
import bean.ItemDashboard;
import bean.RegistrationManage;
import com.google.gson.Gson;
import dao.DBConnection;
import dao.PricePackageDAO;
import java.util.ArrayList;
import dao.RegistrationDAO;
import dao.SubjectDAO;
import dao.UserDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;


public class RegistrationDAOImpl extends DBConnection implements RegistrationDAO {

    /**
     * getAllRegistration
     *
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Registration> getAllRegistration() throws Exception {
        ArrayList<Registration> registrationsList = new ArrayList();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pre = null;
        String sql = "SELECT * FROM [Registration]";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                registrationsList.add(new Registration(rs.getInt("regId"),
                        rs.getInt("userId"),
                        rs.getDate("regTime"),
                        rs.getInt("packId"),
                        rs.getDouble("cost"),
                        rs.getDate("validFrom"),
                        rs.getDate("validTo"),
                        rs.getInt("lastUpdatedBy"),
                        rs.getString("note"),
                        rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return registrationsList;
    }

    /**
     * getRegistrationById
     *
     * @param registrationId
     * @return
     * @throws Exception
     */
    @Override
    public Registration getRegistrationById(int registrationId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pre = null;
        String sql = "SELECT * FROM [Registration] WHERE regId=?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, registrationId);
            rs = pre.executeQuery();
            if (rs.next()) {
                return new Registration(rs.getInt("regId"),
                        rs.getInt("userId"),
                        rs.getDate("regTime"),
                        rs.getInt("packId"),
                        rs.getDouble("cost"),
                        rs.getDate("validFrom"),
                        rs.getDate("validTo"),
                        rs.getInt("lastUpdatedBy"),
                        rs.getString("note"),
                        rs.getBoolean("status"));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return null;
    }

    /**
     * addRegistration
     *
     * @param newRegistration
     * @return
     * @throws Exception
     */
    @Override
    public int addRegistration(Registration newRegistration) throws Exception {
        Connection conn = null;
        ResultSet rs = null;/* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */

        String sql = "INSERT INTO [QuizSystem].[dbo].[Registration]"
                + "(userId,regTime,packId,cost,validFrom,validTo,lastUpdatedBy,note,status)"
                + " values (?,GETDATE(),?,?,?,?,?,?,?)";
        int count = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, newRegistration.getUserId());
            pre.setInt(2, newRegistration.getPackId());
            pre.setDouble(3, newRegistration.getCost());
            pre.setDate(4, (Date) newRegistration.getValidFrom());
            pre.setDate(5, (Date) newRegistration.getValidTo());
            pre.setInt(6, newRegistration.getLastUpdatedBy());
            pre.setString(7, newRegistration.getNote());
            pre.setBoolean(8, newRegistration.isStatus());
            count = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return count;
    }

    /**
     * editRegistration
     *
     * @param registrationId
     * @param editedRegistration
     * @return
     * @throws Exception
     */
    @Override
    public int editRegistration(int registrationId, Registration editedRegistration) throws Exception {
        int i = 0;
        Connection conn = null;
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "UPDATE [QuizSystem].[dbo].[Registration] \n"
                + "SET lastUpdatedBy=?,\n"
                + "	note=?,\n"
                + "	[status]=?\n"
                + "	WHERE regId=?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, editedRegistration.getLastUpdatedBy());
            pre.setString(2, editedRegistration.getNote());
            pre.setBoolean(3, editedRegistration.isStatus());
            pre.setInt(4, registrationId);
            i = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return i;
    }

    @Override
    public int deleteRegistration(int registrationId) throws Exception {
        return 0;
    }

    /**
     *  get all subject registed
     * @param userId
     * @return @throws Exception get registed subject by user's Id
     */
    @Override
    public ArrayList<Subject> getRegistedSubject(int userId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        SubjectDAO subjectDAO = new SubjectDAOImpl();
        ArrayList<Subject> registedSubject = new ArrayList<>();
        String sql = "SELECT b.subjectId\n"
                + "  FROM [QuizSystem].[dbo].[Registration] as a "
                + "inner join [QuizSystem].[dbo].[PricePackage] as b "
                + "on a.packId = b.packId where a.userId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, userId);
            rs = pre.executeQuery();
            while (rs.next()) {
                registedSubject.add(subjectDAO.getSubjectbyId(rs.getInt("subjectId")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return registedSubject;
    }

    /**
     *  get all subjects registed by userId
     * @param userId
     * @return @throws Exception get registed subject by user's Id
     */
    @Override
    public ArrayList<Subject> getRegistedSubjectbyUserId(int userId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Subject> registedSubjectbyUserId = new ArrayList();

        /* Sql query */
        String sqlSubject = "SELECT  Subject.[subjectId]\n"
                + "      ,Subject.[subjectName]\n"
                + "      ,Subject.[description]\n"
                + "      ,Subject.[thumbnail]\n"
                + "      ,Subject.[featuredSubject]\n"
                + "      ,Subject.status\n"
                + "  FROM ([QuizSystem].[dbo].[Subject]\n"
                + "inner JOIN PricePackage\n"
                + "ON Subject.subjectId = PricePackage.subjectId)\n"
                + "inner join Registration on Registration.packId=PricePackage.packId\n"
                + "where Registration.userId=? and Subject.status=1";

        /* Get the subject */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sqlSubject);
            pre.setInt(1, userId);
            rs = pre.executeQuery();
            /* Get information from resultset and add it to arrayList */
            while (rs.next()) {
                int subjectId = rs.getInt("subjectId");
                String subjectName = rs.getString("subjectName");
                String description = rs.getString("description");
                String thumbnail = rs.getString("thumbnail");
                Boolean featured = rs.getBoolean("featuredSubject");
                Boolean status = rs.getBoolean("status");

                registedSubjectbyUserId.add(new Subject(subjectId, subjectName, description,
                        thumbnail, featured, status
                ));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return registedSubjectbyUserId;
    }

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
    @Override
    public ArrayList<ItemDashboard> getSubjectStatistics(String from, String to, ArrayList<Subject> subjectList, String type) throws Exception {
        ArrayList<ItemDashboard> list = new ArrayList();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pre = null;
        int[] subjectIdList = new int[subjectList.size()];
        for (int i = 0; i < subjectList.size(); i++) {
            subjectIdList[i] = subjectList.get(i).getSubjectId();
        }
        String string;
        if (type.equalsIgnoreCase("revenue")) {
            string = "SUM(a.cost) AS revenue";
        } else {
            string = "COUNT(regId) AS registrationCount";
        }
        String sql = "SELECT a.validFrom," + string + ",c.subjectName FROM "
                + "[Registration] AS a join [PricePackage] AS b ON a.packId = b.packId "
                + "join [Subject] AS c ON b.subjectId=c.subjectId "
                + "WHERE b.subjectId IN(";
        for (int i = 0; i < subjectIdList.length - 1; i++) {
            sql += subjectIdList[i] + ",";
        }
        sql += subjectIdList[subjectIdList.length - 1] + ")";

        sql += " AND (a.validFrom >= '" + from + "' AND a.validFrom <= '" + to + "') GROUP BY c.subjectName,a.validFrom order By a.validFrom ";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {

                list.add(new ItemDashboard(rs.getString("subjectName"),
                        rs.getDouble(type),
                        rs.getDate("validFrom").getTime()));

            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return list;
    }

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
    @Override
    public ArrayList<ItemDashboard> getRegistrationStatistics(String from, String to) throws Exception {
        ArrayList<ItemDashboard> list = new ArrayList();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pre = null;
        String sql = "SELECT COUNT(regId) AS number, regTime, status "
                + "FROM [Registration] "
                + "WHERE regTime <= ? AND regTime >= ? "
                + "GROUP BY regTime, status "
                + "ORDER BY regTime";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, to);
            pre.setString(2, from);
            rs = pre.executeQuery();
            while (rs.next()) {
                ItemDashboard item = new ItemDashboard();
                boolean status = rs.getBoolean("status");
                if (rs.wasNull()) {
                    item = new ItemDashboard("Submitted",
                            rs.getDouble("number"),
                            rs.getDate("regTime").getTime());
                } else if (status) {
                    item = new ItemDashboard("Paid",
                            rs.getDouble("number"),
                            rs.getDate("regTime").getTime());
                } else if (!status) {
                    item = new ItemDashboard("Unpaid",
                            rs.getDouble("number"),
                            rs.getDate("regTime").getTime());
                }
                list.add(item);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return list;
    }

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
    @Override
    public ArrayList<ItemDashboard> getRevenueStatistics(String from, String to) throws Exception {
        ArrayList<ItemDashboard> list = new ArrayList();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pre = null;
        String sql = "SELECT SUM(cost) AS revenue, validFrom FROM [Registration] "
                + "WHERE validFrom <= ? AND validFrom >= ? GROUP BY validFrom ORDER BY validFrom";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, to);
            pre.setString(2, from);
            rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new ItemDashboard("", rs.getDouble("revenue"),
                        rs.getDate("validFrom").getTime()));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return list;
    }

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
    @Override
    public ArrayList<ItemDashboard> getRevenueStatisticsBySubjectCate(String from, String to) throws Exception {
        String sql = "SELECT SUM(cost) AS revenue,a.validFrom,e.subjectCateName FROM (Registration AS a "
                + "JOIN PricePackage AS b ON a.packId = b.packId) "
                + "JOIN [Subject] AS c ON b.subjectId = c.subjectId "
                + "JOIN CategorySubject AS d ON c.subjectId=d.subjectId "
                + "JOIN SubjectCate AS e ON d.cateId = e.subjectCateId "
                + "WHERE validFrom <= ? AND validFrom >= ? "
                + "GROUP BY a.validFrom,e.subjectCateName ORDER BY a.validFrom ";
        ArrayList<ItemDashboard> list = new ArrayList();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pre = null;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, to);
            pre.setString(2, from);
            rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new ItemDashboard(rs.getString("subjectCateName"), rs.getDouble("revenue"),
                        rs.getDate("validFrom").getTime()));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return list;
    }

    /**
     * get paid registration
     *
     * @param type "true" or "false" == paid or unpaid
     * @return list of registration. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Registration> getPaidRegistration(String type) throws Exception {
        ArrayList<Registration> list = new ArrayList();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pre = null;
        String sql = "SELECT TOP 10 * FROM [Registration] WHERE [status] = '" + type + "' ORDER BY regTime DESC";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new Registration(rs.getInt("regId"),
                        rs.getInt("userId"),
                        rs.getDate("regTime"),
                        rs.getInt("packId"),
                        rs.getDouble("cost"),
                        rs.getDate("validFrom"),
                        rs.getDate("validTo"),
                        rs.getInt("lastUpdatedBy"),
                        rs.getString("note"),
                        rs.getBoolean("status"))
                );
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return list;
    }

    /**
     * get new registration from database
     *
     * @return list of new registrations. It is a
     * <code>java.util.ArrayList</code>object.
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<Registration> get10NewRegistration() throws Exception {
        ArrayList<Registration> list = new ArrayList();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pre = null;
        String sql = "SELECT TOP 10 * FROM [Registration] ORDER BY regTime DESC";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                list.add(new Registration(rs.getInt("regId"),
                        rs.getInt("userId"),
                        rs.getDate("regTime"),
                        rs.getInt("packId"),
                        rs.getDouble("cost"),
                        rs.getDate("validFrom"),
                        rs.getDate("validTo"),
                        rs.getInt("lastUpdateBy"),
                        rs.getString("note"),
                        rs.getBoolean("status"))
                );
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return list;
    }

    /**
     * Convert statistics data into JSon string
     *
     * @param viewList statistics data
     * @return JSon strings of data. It is a <code>java.util.ArrayList</code>
     * object.
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<String> convertJson(ArrayList<ItemDashboard> viewList) throws Exception {
        // create a new Gson instance
        ArrayList<String> ret = new ArrayList();
        Gson gson = new Gson();
        HashMap<String, Integer> map = new HashMap<>();
        int j = 0;
        ArrayList<ArrayList<ItemDashboard>> list = new ArrayList();

        for (ItemDashboard item : viewList) {
            if (!map.containsKey(item.getName())) {
                map.put(item.getName(), j);
                j++;
                list.add(new ArrayList<>());
            }
            list.get(map.get(item.getName())).add(item);
        }

        // convert your list to json
        for (ArrayList<ItemDashboard> item : list) {
            ret.add(gson.toJson(item));
        }
        return ret;
    }

    /**
     * get name of each JSon string
     *
     * @param viewList statistics data
     * @return names of data. It is a <code>java.util.ArrayList</code> object
     * @throws java.lang.Exception
     */
    @Override
    public ArrayList<String> getNameList(ArrayList<ItemDashboard> viewList) throws Exception {
        ArrayList<String> nameList = new ArrayList();
        HashMap<String, Integer> map = new HashMap<>();
        int j = 0;
        for (ItemDashboard subject : viewList) {
            if (!map.containsKey(subject.getName())) {
                map.put(subject.getName(), j);
                nameList.add(subject.getName());
                j++;
            }
        }
        return nameList;
    }

    @Override
    public ArrayList<RegistrationManage> getFilterRegistration(int subjectId, int userId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;/* Result set returned by the sqlserver */
        PreparedStatement pre = null;/* Prepared statement for executing sql queries */
        ArrayList<RegistrationManage> registrationList = new ArrayList<>();
        String sql = "SELECT [regId]\n"
                + "      ,R.[userId]\n"
                + "      ,[regTime]\n"
                + "	  ,S.[subjectId]\n"
                + "      ,R.[packId]\n"
                + "      ,[cost]\n"
                + "      ,[validFrom]\n"
                + "      ,[validTo]\n"
                + "      ,[lastUpdatedBy]\n"
                + "      ,[note]\n"
                + "      ,R.[status]\n"
                + "  FROM [QuizSystem].[dbo].[Registration] R INNER JOIN [QuizSystem].[dbo].[User] U ON R.userId=U.userId\n"
                + "  INNER JOIN [QuizSystem].[dbo].PricePackage PP ON R.packId = PP.packId\n"
                + "  INNER JOIN [QuizSystem].[dbo].[Subject] S ON S.subjectId=PP.subjectId\n"
                + "  WHERE 1=1";
        if (subjectId > 0) {
            sql = sql.concat(" and S.subjectId = " + subjectId);
        }
        if (userId > 0) {
            sql = sql.concat(" and U.userId = " + userId);
        }
        RegistrationManage registrationManage = null;
        SubjectDAO subjectDAO = new SubjectDAOImpl();
        PricePackageDAO pricePackageDAO = new PricePackageDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                boolean status = rs.getBoolean("status");
                if (rs.wasNull()) {
                    registrationManage = new RegistrationManage(rs.getInt("regId"),
                            userDAO.getUserById(rs.getInt("userId")).getUserMail(),
                            rs.getDate("regTime"), subjectDAO.getSubjectbyId(rs.getInt("subjectId")).getSubjectName(),
                            pricePackageDAO.getPricePackageById(rs.getInt("packId")).getPackName(),
                            rs.getDouble("cost"), rs.getDate("validFrom"), rs.getDate("validTo"),
                            userDAO.getUserById(rs.getInt("userId")).getUserName(),
                            rs.getString("note"), "Submitted");
                } else if (status) {
                    registrationManage = new RegistrationManage(rs.getInt("regId"),
                            userDAO.getUserById(rs.getInt("userId")).getUserMail(),
                            rs.getDate("regTime"), subjectDAO.getSubjectbyId(rs.getInt("subjectId")).getSubjectName(),
                            pricePackageDAO.getPricePackageById(rs.getInt("packId")).getPackName(),
                            rs.getDouble("cost"), rs.getDate("validFrom"), rs.getDate("validTo"),
                            userDAO.getUserById(rs.getInt("userId")).getUserName(),
                            rs.getString("note"), "Paid");
                } else if (!status) {
                    registrationManage = new RegistrationManage(rs.getInt("regId"),
                            userDAO.getUserById(rs.getInt("userId")).getUserMail(),
                            rs.getDate("regTime"), subjectDAO.getSubjectbyId(rs.getInt("subjectId")).getSubjectName(),
                            pricePackageDAO.getPricePackageById(rs.getInt("packId")).getPackName(),
                            rs.getDouble("cost"), rs.getDate("validFrom"), rs.getDate("validTo"),
                            userDAO.getUserById(rs.getInt("userId")).getUserName(),
                            rs.getString("note"), "Unpaid");
                }
                registrationList.add(registrationManage);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return registrationList;
    }

}
