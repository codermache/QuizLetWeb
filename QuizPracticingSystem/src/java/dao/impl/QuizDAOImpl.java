
package dao.impl;

import bean.DimensionType;
import bean.Lesson;
import bean.Quiz;
import bean.QuizLevel;
import bean.Subject;
import bean.TestType;
import dao.DBConnection;
import dao.LessonDAO;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import dao.QuizDAO;
import dao.RegistrationDAO;
import dao.SubjectDAO;
import java.sql.Connection;
import java.sql.ResultSet;


public class QuizDAOImpl extends DBConnection implements QuizDAO {

    /**
     * Find all quiz in the database
     *
     * @return <code>ArrayList<Quiz></code>
     * @throws Exception
     */
    @Override
    public ArrayList<Quiz> getAllQuiz() throws Exception {
        ArrayList<Quiz> allQuiz = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String quizLevelName = null;
        String testTypeName = null;
        String dimensionTypeName = null;
        Quiz quiz = null;
        String sql = "SELECT [quizId]\n"
                + "      ,[lessonId]\n"
                + "      ,[subjectId]\n"
                + "      ,[quizName]\n"
                + "      ,[quizLevelId]\n"
                + "      ,[quizDuration]\n"
                + "      ,[passRate]\n"
                + "      ,[testTypeId]\n"
                + "      ,[description]\n"
                + "      ,[numberQuestion]\n"
                + "      ,[dimensionTypeId]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[Quiz]";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                if (rs.getObject("quizLevelId") != null) {
                    QuizLevelDAOImpl quizLevelDAO = new QuizLevelDAOImpl();
                    QuizLevel quizLevel = quizLevelDAO.getQuizLevelById(rs.getInt("quizLevelId"));
                    quizLevelName = quizLevel.getQuizLevelName();
                }
                if (rs.getObject("testTypeId") != null) {
                    TestTypeDAOImpl testTypeDAO = new TestTypeDAOImpl();
                    TestType testType = testTypeDAO.getTestTypeById(rs.getInt("testTypeId"));
                    testTypeName = testType.getTestTypeName();
                }
                if (rs.getObject("dimensionTypeId") != null) {
                    DimensionTypeDAOImpl dimensionTypeDAO = new DimensionTypeDAOImpl();
                    DimensionType dimensionType = dimensionTypeDAO.getDimensionTypeById(rs.getInt("dimensionTypeId"));
                    dimensionTypeName = dimensionType.getDimensionTypeName();
                }
                LessonDAO lessonDAO = new LessonDAOImpl();
                Lesson lesson = lessonDAO.getLessonById(rs.getInt("lessonId"));
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                Subject subject = subjectDAO.getSubjectbyId(rs.getInt("subjectId"));
                quiz = new Quiz(rs.getInt("quizId"),
                        lesson,
                        subject,
                        rs.getString("quizName"),
                        rs.getInt("quizLevelId"),
                        quizLevelName,
                        rs.getInt("quizDuration"),
                        rs.getInt("passRate"),
                        rs.getInt("testTypeId"),
                        testTypeName,
                        rs.getString("description"),
                        rs.getInt("numberQuestion"),
                        rs.getInt("dimensionTypeId"),
                        dimensionTypeName,
                        rs.getBoolean("status"));
                allQuiz.add(quiz);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return allQuiz;
    }

    /**
     * get quiz by Id
     *
     * @param quizId the target quiz's id. It is a <code>int</code> primitive
     * type
     * @return a quiz <code>Quiz</code> object.
     * @throws java.lang.Exception
     */
    @Override
    public Quiz getQuizById(int quizId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String quizLevelName = null;
        String testTypeName = null;
        String dimensionTypeName = null;
        String sql = "SELECT * FROM [Quiz] WHERE quizId=" + quizId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                if (rs.getObject("quizLevelId") != null) {
                    QuizLevelDAOImpl quizLevelDAO = new QuizLevelDAOImpl();
                    QuizLevel quizLevel = quizLevelDAO.getQuizLevelById(rs.getInt("quizLevelId"));
                    quizLevelName = quizLevel.getQuizLevelName();
                }
                if (rs.getObject("testTypeId") != null) {
                    TestTypeDAOImpl testTypeDAO = new TestTypeDAOImpl();
                    TestType testType = testTypeDAO.getTestTypeById(rs.getInt("testTypeId"));
                    testTypeName = testType.getTestTypeName();
                }
                if (rs.getObject("dimensionTypeId") != null) {
                    DimensionTypeDAOImpl dimensionTypeDAO = new DimensionTypeDAOImpl();
                    DimensionType dimensionType = dimensionTypeDAO.getDimensionTypeById(rs.getInt("dimensionTypeId"));
                    dimensionTypeName = dimensionType.getDimensionTypeName();
                }
                LessonDAO lessonDAO = new LessonDAOImpl();
                Lesson lesson = lessonDAO.getLessonById(rs.getInt("lessonId"));
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                Subject subject = subjectDAO.getSubjectbyId(rs.getInt("subjectId"));
                return new Quiz(rs.getInt("quizId"),
                        lesson,
                        subject,
                        rs.getString("quizName"),
                        rs.getInt("quizLevelId"),
                        quizLevelName,
                        rs.getInt("quizDuration"),
                        rs.getInt("passRate"),
                        rs.getInt("testTypeId"),
                        testTypeName,
                        rs.getString("description"),
                        rs.getInt("numberQuestion"),
                        rs.getInt("dimensionTypeId"),
                        dimensionTypeName,
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
     * get all simulation quiz by user search
     *
     * @param userId user's id. <code>int</code> primitive type
     * @param subjectId user's registered subject's id. <code>int</code> primitive type
     * @param quizName search string to search quiz by name. <code>String</code> primitive type
     * @return all simulation quiz by user. <code>ArrayList<Quiz></code> object
     * @throws Exception
     */
    @Override
    public ArrayList<Quiz> getAllSimulationQuizByUser(int userId, int subjectId, String quizName) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */

        RegistrationDAO IRegistration = new RegistrationDAOImpl();
        ArrayList<Subject> subjectList = IRegistration.getRegistedSubject(userId);
        ArrayList<Quiz> quizList = new ArrayList();
        if (!subjectList.isEmpty()) {
            String sql = "SELECT * from Quiz WHERE testTypeId=1 AND status =1";
            if (subjectId == 0) {
                int subjectIdList[] = new int[subjectList.size()];
                for (int i = 0; i < subjectIdList.length; i++) {
                    subjectIdList[i] = subjectList.get(i).getSubjectId();
                }
                sql += " AND subjectId IN(";
                for (int i = 0; i < subjectList.size() - 1; i++) {
                    sql += subjectIdList[i] + ",";
                }
                sql += subjectIdList[subjectList.size() - 1] + ")";

            } else {
                sql += " AND subjectId=" + subjectId;
            }
            if (quizName != null && !quizName.equalsIgnoreCase("")) {
                sql += " AND quizName like '%" + quizName.toLowerCase().trim() + "%'";
            }
            try {
                conn = getConnection();
                pre = conn.prepareStatement(sql);
                rs = pre.executeQuery();
                while (rs.next()) {
                    QuizLevelDAOImpl quizLevelDAO = new QuizLevelDAOImpl();
                    QuizLevel quizLevel = quizLevelDAO.getQuizLevelById(rs.getInt("quizLevelId"));
                    String quizLevelName = quizLevel.getQuizLevelName();
                    TestTypeDAOImpl testTypeDAO = new TestTypeDAOImpl();
                    TestType testType = testTypeDAO.getTestTypeById(rs.getInt("testTypeId"));
                    String testTypeName = testType.getTestTypeName();
                    DimensionTypeDAOImpl dimensionTypeDAO = new DimensionTypeDAOImpl();
                    DimensionType dimensionType = dimensionTypeDAO.getDimensionTypeById(rs.getInt("dimensionTypeId"));
                    String dimensionTypeName = dimensionType.getDimensionTypeName();
                    LessonDAO lessonDAO = new LessonDAOImpl();
                    Lesson lesson = lessonDAO.getLessonById(rs.getInt("lessonId"));
                    SubjectDAO subjectDAO = new SubjectDAOImpl();
                    Subject subject = subjectDAO.getSubjectbyId(rs.getInt("subjectId"));
                    quizList.add(new Quiz(rs.getInt("quizId"),
                            lesson,
                            subject,
                            rs.getString("quizName"),
                            rs.getInt("quizLevelId"),
                            quizLevelName,
                            rs.getInt("quizDuration"),
                            rs.getInt("passRate"),
                            rs.getInt("testTypeId"),
                            testTypeName,
                            rs.getString("description"),
                            rs.getInt("numberQuestion"),
                            rs.getInt("dimensionTypeId"),
                            dimensionTypeName,
                            rs.getBoolean("status")));
                }
            } catch (Exception e) {
            }
        }
        return quizList;
    }

    /**
     * get taken quiz by takeQuiz's Id
     *
     * @param quizTakeId the target quiz's id. It is a <code>int</code>
     * primitive type
     * @return a quiz <code>Quiz</code> object.
     * @throws java.lang.Exception
     */
    @Override
    public Quiz getQuizByQuizTakeId(int quizTakeId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String quizLevelName = null;
        String testTypeName = null;
        String dimensionTypeName = null;
        String sql = "select * from Quiz as a join CustomerQuiz as b on a.quizId = b.quizId where quizTakeId=" + quizTakeId;
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                if (rs.getObject("quizLevelId") != null) {
                    QuizLevelDAOImpl quizLevelDAO = new QuizLevelDAOImpl();
                    QuizLevel quizLevel = quizLevelDAO.getQuizLevelById(rs.getInt("quizLevelId"));
                    quizLevelName = quizLevel.getQuizLevelName();
                }
                if (rs.getObject("testTypeId") != null) {
                    TestTypeDAOImpl testTypeDAO = new TestTypeDAOImpl();
                    TestType testType = testTypeDAO.getTestTypeById(rs.getInt("testTypeId"));
                    testTypeName = testType.getTestTypeName();
                }
                if (rs.getObject("dimensionTypeId") != null) {
                    DimensionTypeDAOImpl dimensionTypeDAO = new DimensionTypeDAOImpl();
                    DimensionType dimensionType = dimensionTypeDAO.getDimensionTypeById(rs.getInt("dimensionTypeId"));
                    dimensionTypeName = dimensionType.getDimensionTypeName();
                }
                LessonDAO lessonDAO = new LessonDAOImpl();
                Lesson lesson = lessonDAO.getLessonById(rs.getInt("lessonId"));
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                Subject subject = subjectDAO.getSubjectbyId(rs.getInt("subjectId"));
                return new Quiz(rs.getInt("quizId"),
                        lesson,
                        subject,
                        rs.getString("quizName"),
                        rs.getInt("quizLevelId"),
                        quizLevelName,
                        rs.getInt("quizDuration"),
                        rs.getInt("passRate"),
                        rs.getInt("testTypeId"),
                        testTypeName,
                        rs.getString("description"),
                        rs.getInt("numberQuestion"),
                        rs.getInt("dimensionTypeId"),
                        dimensionTypeName,
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

    @Override
    public ArrayList<Quiz> getQuizBySubject(int subjectId) throws Exception {
        ArrayList<Quiz> subjectQuiz = null;

        return subjectQuiz;
    }

    @Override
    public ArrayList<Quiz> getQuizByLesson(int lessonId) throws Exception {
        ArrayList<Quiz> lessonQuiz = null;

        return lessonQuiz;
    }

    /**
     * edit existed quiz in the database
     *
     * @param quizId
     * @param quiz
     * @return
     * @throws Exception
     */
    @Override
    public int editQuiz(int quizId, Quiz quiz) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        int check = -1;
        String sql = "UPDATE [QuizSystem].[dbo].[Quiz] \n"
                + " SET [lessonId] = ?\n"
                + "	  ,[subjectId] = ?\n"
                + "	  ,[quizName] = ?\n"
                + "	  ,[quizLevelId] = ?\n"
                + "	  ,[quizDuration] = ?\n"
                + "	  ,[passRate] = ?\n"
                + "	  ,[testTypeId] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[numberQuestion] = ?\n"
                + "      ,[dimensionTypeId] = ?\n"
                + "      ,[status] =?\n"
                + " WHERE [quizId] = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            if (quiz.getLesson() != null) {
                pre.setInt(1, quiz.getLesson().getLessonId());
            } else {
                pre.setObject(1, null);
            }
            pre.setInt(2, quiz.getSubject().getSubjectId());
            pre.setString(3, quiz.getQuizName());
            if (quiz.getQuizLevelId() != 0) {
                pre.setInt(4, quiz.getQuizLevelId());
            } else {
                pre.setObject(4, null);
            }
            pre.setInt(5, quiz.getQuizDuration());
            if (quiz.getPassRate() != 0) {
                pre.setInt(6, quiz.getPassRate());
            } else {
                pre.setObject(6, null);
            }
            pre.setInt(7, quiz.getTestTypeId());
            pre.setString(8, quiz.getDescription());
            pre.setInt(9, quiz.getNumberQuestion());
            pre.setInt(10, quiz.getDimensionTypeId());
            pre.setBoolean(11, quiz.getStatus());
            pre.setInt(12, quiz.getQuizId());
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
     * add a quiz into the database
     *
     * @param quiz
     * @return
     * @throws Exception
     */
    @Override
    public int addQuiz(Quiz quiz) throws Exception {
        int i = 0;
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String sql = "INSERT INTO dbo.Quiz(lessonId,"
                + "subjectId,"
                + "quizName,"
                + "quizLevelId,"
                + "quizDuration,"
                + "passRate,"
                + "testTypeId,"
                + "[description],"
                + "numberQuestion,"
                + "dimensionTypeId,"
                + "[status])\n"
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);

            if (quiz.getLesson() != null) {
                pre.setInt(1, quiz.getLesson().getLessonId());
            } else {
                pre.setObject(1, null);
            }

            pre.setInt(2, quiz.getSubject().getSubjectId());
            pre.setString(3, quiz.getQuizName());

            if (quiz.getQuizLevelId() != 0) {
                pre.setInt(4, quiz.getQuizLevelId());
            } else {
                pre.setObject(4, null);
            }

            pre.setInt(5, quiz.getQuizDuration());

            if (quiz.getPassRate() != 0) {
                pre.setInt(6, quiz.getPassRate());
            } else {
                pre.setObject(6, null);
            }
            pre.setInt(7, quiz.getTestTypeId());
            pre.setString(8, quiz.getDescription());
            pre.setInt(9, quiz.getNumberQuestion());
            pre.setInt(10, quiz.getDimensionTypeId());
            pre.setInt(11, 1);
            i = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return i;
    }

    /**
     * delete a quiz from the database
     *
     * @param quizId
     * @return
     * @throws Exception
     */
    @Override
    public int deleteQuiz(int quizId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        int check = -1;
        String sql = "delete from [QuizSystem].[dbo].[Quiz] where quizId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, quizId);
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
     * Get quiz that have some same attribute
     *
     * @param quiz
     * @return
     * @throws Exception
     */
    @Override
    public int getQuizIdCreated(Quiz quiz) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        int quizId = -1;
        String sql = "SELECT TOP 1 [quizId]\n"
                + "      ,[lessonId]\n"
                + "      ,[subjectId]\n"
                + "      ,[quizName]\n"
                + "      ,[quizLevelId]\n"
                + "      ,[quizDuration]\n"
                + "      ,[passRate]\n"
                + "      ,[testTypeId]\n"
                + "      ,[description]\n"
                + "      ,[numberQuestion]\n"
                + "      ,[dimensionTypeId]\n"
                + "      ,[status]\n"
                + "FROM [QuizSystem].[dbo].[Quiz]\n"
                + "WHERE subjectId = ? \n"
                + "AND quizDuration = ? \n"
                + "AND testTypeId = ? \n"
                + "AND numberQuestion = ? \n"
                + "AND dimensionTypeId = ?\n"
                + "ORDER BY [quizId] DESC";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, quiz.getSubject().getSubjectId());
            pre.setInt(2, quiz.getQuizDuration());
            pre.setInt(3, quiz.getTestTypeId());
            pre.setInt(4, quiz.getNumberQuestion());
            pre.setInt(5, quiz.getDimensionTypeId());
            rs = pre.executeQuery();
            if (rs.next()) {
                quizId = rs.getInt("quizId");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return quizId;
    }

    /**
     * add quiz's question to the database
     *
     * @param quizId
     * @param questionId
     * @return
     * @throws Exception
     */
    @Override
    public int addQuizQuestion(int quizId, int questionId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        int question = 0;
        String sql = "INSERT INTO dbo.QuizQuestion(quizId,questionId,[status]) VALUES(?,?,1)";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, quizId);
            pre.setInt(2, questionId);
            question = pre.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return question;
    }

    /**
     * Get all quiz that have the same subjectId and quizTypeId
     *
     * @param subjectId
     * @param quizTypeId
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Quiz> getFilteredQuiz(int subjectId, int quizTypeId) throws Exception {
        ArrayList<Quiz> allQuiz = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String quizLevelName = null;
        String testTypeName = null;
        String dimensionTypeName = null;
        Quiz quiz = null;
        String sql = "SELECT [quizId]\n"
                + "      ,[lessonId]\n"
                + "      ,[subjectId]\n"
                + "      ,[quizName]\n"
                + "      ,[quizLevelId]\n"
                + "      ,[quizDuration]\n"
                + "      ,[passRate]\n"
                + "      ,[testTypeId]\n"
                + "      ,[description]\n"
                + "      ,[numberQuestion]\n"
                + "      ,[dimensionTypeId]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[Quiz]\n"
                + "  WHERE 1=1";
        try {
            conn = getConnection();
            if (subjectId > 0) {
                sql = sql.concat(" and [subjectId] = " + subjectId);
            }
            if (quizTypeId > 0) {
                sql = sql.concat(" and [testTypeId] = " + quizTypeId);
            }
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                if (rs.getObject("quizLevelId") != null) {
                    QuizLevelDAOImpl quizLevelDAO = new QuizLevelDAOImpl();
                    QuizLevel quizLevel = quizLevelDAO.getQuizLevelById(rs.getInt("quizLevelId"));
                    quizLevelName = quizLevel.getQuizLevelName();
                }
                if (rs.getObject("testTypeId") != null) {
                    TestTypeDAOImpl testTypeDAO = new TestTypeDAOImpl();
                    TestType testType = testTypeDAO.getTestTypeById(rs.getInt("testTypeId"));
                    testTypeName = testType.getTestTypeName();
                }
                if (rs.getObject("dimensionTypeId") != null) {
                    DimensionTypeDAOImpl dimensionTypeDAO = new DimensionTypeDAOImpl();
                    DimensionType dimensionType = dimensionTypeDAO.getDimensionTypeById(rs.getInt("dimensionTypeId"));
                    dimensionTypeName = dimensionType.getDimensionTypeName();
                }
                LessonDAO lessonDAO = new LessonDAOImpl();
                Lesson lesson = lessonDAO.getLessonById(rs.getInt("lessonId"));
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                Subject subject = subjectDAO.getSubjectbyId(rs.getInt("subjectId"));
                quiz = new Quiz(rs.getInt("quizId"),
                        lesson,
                        subject,
                        rs.getString("quizName"),
                        rs.getInt("quizLevelId"),
                        quizLevelName,
                        rs.getInt("quizDuration"),
                        rs.getInt("passRate"),
                        rs.getInt("testTypeId"),
                        testTypeName,
                        rs.getString("description"),
                        rs.getInt("numberQuestion"),
                        rs.getInt("dimensionTypeId"),
                        dimensionTypeName,
                        rs.getBoolean("status"));
                allQuiz.add(quiz);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return allQuiz;
    }

    /**
     * Get all quiz that have the name similar to searchName
     *
     * @param searchName
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Quiz> getQuizByName(String searchName) throws Exception {
        ArrayList<Quiz> allQuiz = new ArrayList<>();
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        String quizLevelName = null;
        String testTypeName = null;
        String dimensionTypeName = null;
        Quiz quiz = null;
        String sql = "SELECT [quizId]\n"
                + "      ,[lessonId]\n"
                + "      ,[subjectId]\n"
                + "      ,[quizName]\n"
                + "      ,[quizLevelId]\n"
                + "      ,[quizDuration]\n"
                + "      ,[passRate]\n"
                + "      ,[testTypeId]\n"
                + "      ,[description]\n"
                + "      ,[numberQuestion]\n"
                + "      ,[dimensionTypeId]\n"
                + "      ,[status]\n"
                + "  FROM [QuizSystem].[dbo].[Quiz]\n";
        try {
            conn = getConnection();
            if (searchName != null) {
                sql = sql.concat(" WHERE [quizName] like '%" + searchName + "%'");
            }
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                if (rs.getObject("quizLevelId") != null) {
                    QuizLevelDAOImpl quizLevelDAO = new QuizLevelDAOImpl();
                    QuizLevel quizLevel = quizLevelDAO.getQuizLevelById(rs.getInt("quizLevelId"));
                    quizLevelName = quizLevel.getQuizLevelName();
                }
                if (rs.getObject("testTypeId") != null) {
                    TestTypeDAOImpl testTypeDAO = new TestTypeDAOImpl();
                    TestType testType = testTypeDAO.getTestTypeById(rs.getInt("testTypeId"));
                    testTypeName = testType.getTestTypeName();
                }
                if (rs.getObject("dimensionTypeId") != null) {
                    DimensionTypeDAOImpl dimensionTypeDAO = new DimensionTypeDAOImpl();
                    DimensionType dimensionType = dimensionTypeDAO.getDimensionTypeById(rs.getInt("dimensionTypeId"));
                    dimensionTypeName = dimensionType.getDimensionTypeName();
                }
                LessonDAO lessonDAO = new LessonDAOImpl();
                Lesson lesson = lessonDAO.getLessonById(rs.getInt("lessonId"));
                SubjectDAO subjectDAO = new SubjectDAOImpl();
                Subject subject = subjectDAO.getSubjectbyId(rs.getInt("subjectId"));
                quiz = new Quiz(rs.getInt("quizId"),
                        lesson,
                        subject,
                        rs.getString("quizName"),
                        rs.getInt("quizLevelId"),
                        quizLevelName,
                        rs.getInt("quizDuration"),
                        rs.getInt("passRate"),
                        rs.getInt("testTypeId"),
                        testTypeName,
                        rs.getString("description"),
                        rs.getInt("numberQuestion"),
                        rs.getInt("dimensionTypeId"),
                        dimensionTypeName,
                        rs.getBoolean("status"));
                allQuiz.add(quiz);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            closeResultSet(rs);
            closePreparedStatement(pre);
            closeConnection(conn);
        }
        return allQuiz;
    }

    /**
     * delete all question of a quiz
     *
     * @param quizId
     * @return
     * @throws Exception
     */
    @Override
    public int removeQuizQuestion(int quizId) throws Exception {
        Connection conn = null;
        ResultSet rs = null;
        /* Result set returned by the sqlserver */
        PreparedStatement pre = null;
        /* Prepared statement for executing sql queries */
        int check = 0;
        String sql = "  delete from [QuizSystem].[dbo].[QuizQuestion] where quizId = ?";
        try {
            conn = getConnection();
            pre = conn.prepareStatement(sql);
            pre.setInt(1, quizId);
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
    
    public static void main(String[] args) throws Exception {
        QuizDAOImpl dao = new QuizDAOImpl();
        ArrayList<Quiz> list = dao.getQuizByName("pra");
        for (Quiz quiz : list) {
            System.out.println(quiz.getQuizId());
        }
    }
}
