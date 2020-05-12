package model;

import javafx.beans.property.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Question {

    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty surveyId = new SimpleIntegerProperty();
    private StringProperty question = new SimpleStringProperty();
    private QuestionType type;
    private StringProperty stringAnswer = new SimpleStringProperty();
    private IntegerProperty intAnswer = new SimpleIntegerProperty();
    private LocalDate dateAnswer;
    private List<String> multiAnswers;
    private BooleanProperty b1 = new SimpleBooleanProperty();
    private BooleanProperty b2 = new SimpleBooleanProperty();
    private BooleanProperty b3 = new SimpleBooleanProperty();
    private BooleanProperty b4 = new SimpleBooleanProperty();
    private BooleanProperty b5 = new SimpleBooleanProperty();

    public Question(int id, int surveyId, String type, String question, String sAnswer, int intAnswer, Date dateAnswer, String mult1, String multi2, String multi3, String multi4, String multi5, boolean b1, boolean b2, boolean b3, boolean b4, boolean b5) {
        this.id.set(id);
        this.question.set(question);
        this.type = Question.typeConvert(type);
        this.stringAnswer.set(sAnswer);
        this.multiAnswers = new ArrayList<String>();
        this.b1.set(b1);
        this.b2.set(b2);
        this.b3.set(b3);
        this.b4.set(b4);
        this.b5.set(b5);
    }

    public Question(int surveyId, QuestionType type) {
        this.surveyId.set(surveyId);
        this.type = type;
    }

    public static QuestionType typeConvert(String string) {
        switch (string) {
            case "Single answer - Text":
            case "SIMPLE":
                return QuestionType.SIMPLE;
            case "Single answer - Number":
            case "NUMBER":
                return  QuestionType.NUMBER;
            case "Single answer - Date":
            case "DATE":
                return QuestionType.DATE;
            case "Single answer - Options":
            case "MULTIPLECHOICEONE":
                return QuestionType.MULTIPLECHOICEONE;
            case "Multiple answer - Options":
            case "MULTIPLECHOICEMULTIPLE":
                return QuestionType.MULTIPLECHOICEMULTIPLE;
        }
        return null;
    }

    public List<String> getMultiAnswers() {
        return multiAnswers;
    }

    public void setMultiAnswers(List<String> multiAnswers) {
        this.multiAnswers = multiAnswers;
    }

    public boolean isB1() {
        return b1.get();
    }

    public BooleanProperty b1Property() {
        return b1;
    }

    public void setB1(boolean b1) {
        this.b1.set(b1);
    }

    public boolean isB2() {
        return b2.get();
    }

    public BooleanProperty b2Property() {
        return b2;
    }

    public void setB2(boolean b2) {
        this.b2.set(b2);
    }

    public boolean isB3() {
        return b3.get();
    }

    public BooleanProperty b3Property() {
        return b3;
    }

    public void setB3(boolean b3) {
        this.b3.set(b3);
    }

    public boolean isB4() {
        return b4.get();
    }

    public BooleanProperty b4Property() {
        return b4;
    }

    public void setB4(boolean b4) {
        this.b4.set(b4);
    }

    public boolean isB5() {
        return b5.get();
    }

    public BooleanProperty b5Property() {
        return b5;
    }

    public void setB5(boolean b5) {
        this.b5.set(b5);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getSurveyId() {
        return surveyId.get();
    }

    public IntegerProperty surveyIdProperty() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId.set(surveyId);
    }

    public String getQuestion() {
        return question.get();
    }

    public StringProperty questionProperty() {
        return question;
    }

    public void setQuestion(String name) {
        this.question.set(name);
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getStringAnswer() {
        return stringAnswer.get();
    }

    public StringProperty stringAnswerProperty() {
        return stringAnswer;
    }

    public void setStringAnswer(String stringAnswer) {
        this.stringAnswer.set(stringAnswer);
    }

    public int getIntAnswer() {
        return intAnswer.get();
    }

    public IntegerProperty intAnswerProperty() {
        return intAnswer;
    }

    public void setIntAnswer(int intAnswer) {
        this.intAnswer.set(intAnswer);
    }

    public LocalDate getDateAnswer() {
        return dateAnswer;
    }

    public void setDateAnswer(LocalDate dateAnswer) {
        this.dateAnswer = dateAnswer;
    }
}


