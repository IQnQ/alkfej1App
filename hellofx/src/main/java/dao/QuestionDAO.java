package dao;

import model.Question;


import java.util.List;

public interface QuestionDAO {

    public int add(Question q);

    public boolean update1(Question q);
    public boolean update2(Question q);
    public boolean update3(Question q);

    public List<Question> getSurveyQuestions(int surveyID);

    public List<Question> getAll();
}
