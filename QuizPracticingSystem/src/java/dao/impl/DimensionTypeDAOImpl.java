
package dao.impl;

import bean.DimensionType;
import dao.DBConnection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import dao.DimensionTypeDAO;
import java.sql.Connection;
import java.sql.ResultSet;


public class DimensionTypeDAOImpl extends DBConnection implements DimensionTypeDAO {

    /**
     * Get all dimension type with status = 1
     *
     * @return <code>ArrayList</code>
     * @throws Exception
     */
    @Override
    public ArrayList<DimensionType> getAllDimensionTypes() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        ArrayList<DimensionType> dimensionTypesList = new ArrayList<>();
        String sql = "SELECT [dimensionTypeId]\n"
                + "      ,[dimensionTypeName]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[DimensionType]\n"
                + "  WHERE status = 1 ";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                dimensionTypesList.add(
                        new DimensionType(rs.getInt("dimensionTypeId"),
                                rs.getString("dimensionTypeName"),
                                rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return dimensionTypesList;
    }

    /**
     * Get dimension type by a specified id
     *
     * @param dimensionTypeId
     * @return
     * @throws Exception
     */
    @Override
    public DimensionType getDimensionTypeById(int dimensionTypeId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "SELECT * FROM [DimensionType] WHERE dimensionTypeId=" + dimensionTypeId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                return new DimensionType(rs.getInt("dimensionTypeId"),
                        rs.getString("dimensionTypeName"),
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
     * Update dimension type
     *
     * @param updatedDimensionType
     * @return
     * @throws Exception
     */
    @Override
    public int updateDimensionType(DimensionType updatedDimensionType) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "  UPDATE [QuizSystem].[dbo].[DimensionType] \n"
                + "  SET [dimensionTypeName] = ?\n"
                + "      ,[status] = ?\n"
                + "  WHERE dimensionTypeId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, updatedDimensionType.getDimensionTypeName());
            pre.setBoolean(2, updatedDimensionType.isStatus());
            pre.setInt(3, updatedDimensionType.getDimensionTypeId());
            return pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    /**
     * Delete dimension type
     *
     * @param dtId dimensionID
     * @return
     * @throws Exception
     */
    @Override
    public int deteteDimensionType(int dtId) throws Exception {
        return 0;
    }

    /**
     * Add new dimension Type
     *
     * @param newDimensionType
     * @return
     * @throws Exception
     */
    @Override
    public int addDimensionType(DimensionType newDimensionType) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "INSERT INTO dbo.DimensionType(dimensionTypeName,status) VALUES(?,1)";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, newDimensionType.getDimensionTypeName());
            return pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
    }

    /**
     * Get all dimension type
     *
     * @return <code>ArrayList</code>
     * @throws Exception
     */
    @Override
    public ArrayList<DimensionType> getAllStatusDimensionTypes() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        ArrayList<DimensionType> dimensionTypesList = new ArrayList<>();
        String sql = "SELECT [dimensionTypeId]\n"
                + "      ,[dimensionTypeName]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[DimensionType]";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                dimensionTypesList.add(
                        new DimensionType(rs.getInt("dimensionTypeId"),
                                rs.getString("dimensionTypeName"),
                                rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return dimensionTypesList;
    }
}
