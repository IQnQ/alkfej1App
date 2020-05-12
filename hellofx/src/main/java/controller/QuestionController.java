package controller;

import dao.QuestionDAO;
import dao.QuestionDaoImpl;
import model.Question;
import model.QuestionType;


import java.util.List;

public class QuestionController {


    public Question question;
    private QuestionDAO dao = new QuestionDaoImpl();
    private static QuestionController single_instance = null;

    public static QuestionController getInstance() {
        if (single_instance == null) {
            single_instance = new QuestionController();
        }
        return single_instance;
    }

    public Question createNew(int surveyId, QuestionType type) {
        question = new Question(surveyId, type);
        question.setId(dao.add(question));
        question.setType(type);
        return question;
    }

    public Question getQuestion() {
        return question;
    }

    public boolean update1() { return dao.update1(question); }

    public boolean update2() {
        return dao.update2(question);
    }

    public boolean update3() { return dao.update3(question); }

    public int add(Question survey) {
        return dao.add(survey);
    }

    public List<Question> getSurveyQuestions(int surveyId) { return dao.getSurveyQuestions(surveyId); }

    public List<Question> getAll() {
        return dao.getAll();
    }
}
