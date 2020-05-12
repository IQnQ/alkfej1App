package dao;

import model.Survey;

import java.util.List;

public interface SurveyDAO {

    public int add(Survey s);

    public boolean delete(Survey s);

    public boolean update(Survey s);

    public List<Survey> getAll();


}
