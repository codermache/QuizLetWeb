
package dao.impl;

import bean.Dimension;
import bean.DimensionType;
import dao.DBConnection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import dao.DimensionDAO;
import java.sql.Connection;
import java.sql.ResultSet;

public class DimensionDAOImpl extends DBConnection implements DimensionDAO {

    /**
     * Get all dimension 
     * @return
     * @throws Exception 
     */
    @Override
    public ArrayList<Dimension> getAllDimension() throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        ArrayList<Dimension> listDimension = new ArrayList();
        String sql = "SELECT  [dimensionId]\n"
                + "      ,[subjectId]\n"
                + "      ,[dimensionTypeId]\n"
                + "      ,[dimensionName]\n"
                + "      ,[description]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[Dimension]";
        /* Get the dimension */
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            /* Get information from resultset and add it to arrayList */
            while (rs.next()) {
                int dimensionId = rs.getInt("dimensionId");
                int subjectId = rs.getInt("subjectId");
                int dimensionTypeId = rs.getInt("dimensionTypeId");
                String dimensionName = rs.getString("dimensionName");
                String description = rs.getString("description");
                Boolean status = rs.getBoolean("status");

                listDimension.add(new Dimension(dimensionId, subjectId, dimensionTypeId, dimensionName, dimensionName, description, status));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return listDimension;
    }

    /**
     * Get dimensions of a subject
     * @param subjectId
     * @return
     * @throws Exception 
     */
    @Override
    public ArrayList<Dimension> getDimensionBySubject(int subjectId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

 /* Get dimension list of the subject */
        ArrayList<Dimension> dimensions = new ArrayList<>();
        String sql = "SELECT S.[subjectId]\n"
                + "      ,D.[dimensionId]\n"
                + "      ,D.[subjectId]\n"
                + "      ,D.[dimensionTypeId]\n"
                + "      ,D.[dimensionName]\n"
                + "      ,D.[description]\n"
                + "      ,D.[status]\n"
                + "	  ,DT.[dimensionTypeName]\n"
                + "  FROM [QuizSystem].[dbo].[Subject] S \n"
                + "  INNER JOIN [QuizSystem].[dbo].[Dimension] D ON S.subjectId = D.subjectId \n"
                + "  INNER JOIN [QuizSystem].[dbo].DimensionType DT ON DT.dimensionTypeId = D.dimensionTypeId\n"
                + "  WHERE S.subjectId =" + subjectId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                dimensions.add(new Dimension(rs.getInt("dimensionId"),
                        subjectId,
                        rs.getInt("dimensionTypeId"),
                        rs.getString("dimensionTypeName"),
                        rs.getString("dimensionName"),
                        rs.getString("description"),
                        rs.getBoolean("status")));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return dimensions;
    }

    /**
     * Get dimension by a specified id
     * @param dimensionId
     * @return
     * @throws Exception 
     */
    @Override
    public Dimension getDimensionById(int dimensionId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        Dimension dimensionById = null;
        String sql = "SELECT * FROM [Dimension] WHERE [dimensionId] = ?";

        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, dimensionId);
            rs = pre.executeQuery();
            if (rs.next()) {
                int subjectId = rs.getInt("subjectId");
                int dimensionTypeId = rs.getInt("dimensionTypeId");
                String dimensionName = rs.getString("dimensionName");
                String description = rs.getString("description");
                Boolean status = rs.getBoolean("status");
                dimensionById = new Dimension(dimensionId, subjectId, dimensionTypeId, dimensionName, dimensionName, description, status);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return dimensionById;
    }

    /**
     * Add new subject dimension
     * @param dimension
     * @return
     * @throws Exception 
     */
    @Override
    public int addDimension(Dimension dimension) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = "INSERT INTO dbo.Dimension(dimensionName,dimensionTypeId,subjectId,[description],[status]) VALUES(?,?,?,?,?);";
        int check = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setString(1, dimension.getDimensionName());
            pre.setInt(2, dimension.getDimensionTypeId());
            pre.setInt(3, dimension.getSubjectId());
            pre.setString(4, dimension.getDescription());
            pre.setBoolean(5, dimension.isStatus());
            check = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }

        return check;
    }

    /**
     * Delete subject's dimension
     * @param dimensionId
     * @return
     * @throws Exception 
     */
    @Override
    public int deleteDimension(int dimensionId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = " DELETE FROM [Dimension] WHERE dimensionId = ?";
        int check = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, dimensionId);
            check = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }

        return check;
    }

    /**
     * Edit subject's dimension
     * @param dimensionId
     * @param dimension
     * @return
     * @throws Exception 
     */
    @Override
    public int editDimension(int dimensionId, Dimension dimension) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        String sql = " UPDATE [Dimension] set dimensionTypeId = ?, dimensionName = ?,  [description] = ? where dimensionId = ?";
        int check = 0;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, dimension.getDimensionTypeId());
            pre.setString(2, dimension.getDimensionName());
            pre.setString(3, dimension.getDescription());
            pre.setInt(4, dimensionId);
            check = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }

        return check;
    }

    /**
     * Get dimension types of a subject
     * @param subjectId
     * @return
     * @throws Exception 
     */
    @Override
    public ArrayList<DimensionType> getSubjectDimensionType(int subjectId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        ArrayList<DimensionType> dimensionList = new ArrayList<>();
        String sql = "SELECT b.[dimensionTypeId],\n"
                + "  b.[dimensionTypeName]\n"
                + "  FROM [QuizSystem].[dbo].[Dimension] as A "
                + "  inner join [QuizSystem].[dbo].[DimensionType] as B\n"
                + "  on a.dimensionTypeId = b. dimensionTypeId\n"
                + "  where a.subjectId = ? and b.status = 1";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, subjectId);
            rs = pre.executeQuery();
            if (rs.next()) {
                dimensionList.add(new DimensionType(rs.getInt("dimensionTypeId"), rs.getString("dimensionTypeName"), true));
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return dimensionList;
    }
    
//    public static void main(String[] args) {
//        DimensionDAOImpl dao = new DimensionDAOImpl();
//        Dimension d = new Dimension(1, 1, 1, "", "Java Programming2", "Hello", true);
//        try {
//            System.out.println(dao.deleteDimension(5));
//        } catch (Exception ex) {
//            Logger.getLogger(DimensionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
