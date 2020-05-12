package controller;

import dao.SurveyDAO;
import dao.SurveyDaoImpl;
import model.Survey;
import java.util.List;

public class SurveyController {

    public Survey survey;
    private SurveyDAO dao = new SurveyDaoImpl();
    private static SurveyController single_instance = null;

    private SurveyController(){}

    public static SurveyController getInstance() {
        if (single_instance == null) {
            single_instance = new SurveyController();
        }
        return single_instance;
    }

    public Survey createNew() {
        survey = new Survey();
        int surveyId = dao.add(survey);
        survey.setId(surveyId);
        return survey;
    }

    public int add(Survey survey) {
        return dao.add(survey);
    }

    public boolean delete(Survey survey) { return dao.delete(survey); }

    public List<Survey> getAll() {
        return dao.getAll();
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public boolean update() { return dao.update(survey); }
}
