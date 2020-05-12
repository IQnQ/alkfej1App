package dao;

import model.Survey;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SurveyDaoImpl implements SurveyDAO {

    private final static String DB_STRING = "jdbc:sqlite:sample.db";
    private static final String CREATE_SURVEY = "CREATE TABLE IF NOT EXISTS Survey (" +
            "id integer PRIMARY KEY AUTOINCREMENT," +
            "name text NOT NULL," +
            "createdAt DATE," +
            "isFilledOut boolean," +
            "from_date DATE," +
            "to_date DATE," +
            "from_time DATE," +
            "to_time DATE," +
            "max_time DATE," +
            "lim INTEGER);";

    private static final String SELECT_SURVEY = "SELECT * FROM Survey;";

    private static final String INSERT_SURVEY = "INSERT INTO Survey (name, createdAt, isFilledOut) VALUES " +
            "(?,?,?);";
    private static final String DELETE_SURVEY = "DELETE FROM Survey WHERE id = ?";

    private static final String UPDATE_SURVEY = "UPDATE Survey SET name=?, from_date=?, to_date=?, from_time=?, to_time=?, max_time=?, lim=? WHERE id=?";

    public SurveyDaoImpl() {
        initializeTables();
    }

    public void initializeTables() {
        try (Connection conn = DriverManager.getConnection(DB_STRING); Statement st = conn.createStatement()) {
            st.executeUpdate(CREATE_SURVEY);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int add(Survey s) {

        try (Connection conn = DriverManager.getConnection(DB_STRING); PreparedStatement st = conn.prepareStatement(INSERT_SURVEY)){
            st.setString(1, s.getName());
            st.setObject(2, s.getCreatedDate());
            st.setBoolean(3, s.isIsFilledOut());
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
    public List<Survey> getAll() {
        List<Survey> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_STRING); Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(SELECT_SURVEY);

            while (rs.next()) {
                Survey p = new Survey(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBoolean(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getInt(10)
                );
                result.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(Survey s) {

        try (Connection conn = DriverManager.getConnection(DB_STRING); PreparedStatement st = conn.prepareStatement(DELETE_SURVEY)){
            st.setInt(1, s.getId());

            TimeUnit.SECONDS.sleep(1);

            int res = st.executeUpdate();

            return res == 1;

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(Survey s) {

        try (Connection conn = DriverManager.getConnection(DB_STRING); PreparedStatement st = conn.prepareStatement(UPDATE_SURVEY)){
            st.setString(1, s.getName());
            st.setObject(2, s.getFromD());
            st.setObject(3, s.getToD());
            st.setObject(4, s.getFromT());
            st.setObject(5, s.getToT());
            st.setObject(6, s.getMaxT());
            st.setInt(7, s.getLimit());
            st.setInt(8, s.getId());

            int res = st.executeUpdate();

            return res == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
