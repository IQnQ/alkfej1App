package dao;

import model.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoImpl implements QuestionDAO {


    private final static String DB_STRING = "jdbc:sqlite:sample.db";
    private static final String CREATE_QUESTION = "CREATE TABLE IF NOT EXISTS Question (" +
            "id integer PRIMARY KEY AUTOINCREMENT," +
            "survey_id integer NOT NULL," +
            "type text NOT NULL," +
            "question text," +
            "string_answer text," +
            "int_answer integer,"+
            "date_answer Date," +
            "multi_answer_1 text," +
            "multi_answer_2 text," +
            "multi_answer_3 text," +
            "multi_answer_4 text," +
            "multi_answer_5 text," +
            "right_1 boolean," +
            "right_2 boolean," +
            "right_3 boolean," +
            "right_4 boolean," +
            "right_5 boolean);";

    private static final String SELECT_QUESTION = "SELECT * FROM Question WHERE survey_id = ?;";

    private static final String INSERT_QUESTION = "INSERT INTO Question (survey_id, type) VALUES " +
            "(?,?);";

    private static final String UPDATE_QUESTION1 = "UPDATE Question SET question=?, string_answer=? WHERE id=?";
    private static final String UPDATE_QUESTION2 = "UPDATE Question SET question=?, int_answer=? WHERE id=?";
    private static final String UPDATE_QUESTION3 = "UPDATE Question SET question=?, multi_answer_1=?, multi_answer_2=?, multi_answer_3=?, multi_answer_4=?, multi_answer_5=?, right_1=?, right_2=?, right_3=?, right_4=?, right_5=? WHERE id=?";

    public QuestionDaoImpl() {
        initializeTables();
    }

    public void initializeTables() {
        try (Connection conn = DriverManager.getConnection(DB_STRING); Statement st = conn.createStatement()) {
            st.executeUpdate(CREATE_QUESTION);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int add(Question q) {
        try (Connection conn = DriverManager.getConnection(DB_STRING); PreparedStatement st = conn.prepareStatement(INSERT_QUESTION)) {
            st.setInt(1, q.getSurveyId());
            st.setString(2, q.getType().toString());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            while (rs.next()) {
                int result = rs.getInt(1);
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public boolean update1(Question q) {
        try (Connection conn = DriverManager.getConnection(DB_STRING); PreparedStatement st = conn.prepareStatement(UPDATE_QUESTION1)) {

            st.setString(1, q.getQuestion());
            st.setString(2, q.getStringAnswer());
            st.setInt(3, q.getId());
            int res = st.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update2(Question q) {
        try (Connection conn = DriverManager.getConnection(DB_STRING); PreparedStatement st = conn.prepareStatement(UPDATE_QUESTION2)) {

            st.setString(1, q.getQuestion());
            st.setInt(2, q.getIntAnswer());
            st.setInt(3, q.getId());
            int res = st.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update3(Question q) {
        try (Connection conn = DriverManager.getConnection(DB_STRING); PreparedStatement st = conn.prepareStatement(UPDATE_QUESTION3)) {

            st.setString(1, q.getQuestion());
            st.setString(2,q.getMultiAnswers().get(0));
            st.setString(3,q.getMultiAnswers().get(1));
            st.setString(4,q.getMultiAnswers().get(2));
            st.setString(5,q.getMultiAnswers().get(3));
            st.setString(6,q.getMultiAnswers().get(4));
            st.setBoolean(7,q.isB1());
            st.setBoolean(8,q.isB2());
            st.setBoolean(9,q.isB3());
            st.setBoolean(10,q.isB4());
            st.setBoolean(11,q.isB5());
            st.setInt(12, q.getId());
            int res = st.executeUpdate();
            if (res == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Question> getSurveyQuestions(int surveyID) {
        List<Question> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_STRING); PreparedStatement st = conn.prepareStatement(SELECT_QUESTION)) {
            st.setInt(1,surveyID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Question q = new Question(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDate(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getBoolean(13),
                        rs.getBoolean(14),
                        rs.getBoolean(15),
                        rs.getBoolean(16),
                        rs.getBoolean(17)
                );
                result.add(q);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public List<Question> getAll() {
        return null;
    }
}
