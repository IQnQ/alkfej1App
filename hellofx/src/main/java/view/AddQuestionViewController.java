package view;

import controller.QuestionController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.QuestionType;
import utils.Utils;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddQuestionViewController implements Initializable {

    ToggleGroup tg = new ToggleGroup();


    @FXML
    private TextArea questionTextArea;

    @FXML
    private TextField tf1, tf2, tf3, tf4, tf5, answerField;

    @FXML
    private RadioButton rb1, rb2, rb3, rb4, rb5;

    @FXML
    private CheckBox cb1, cb2, cb3, cb4, cb5;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rb1.setToggleGroup(tg);
        rb1.setUserData("1");
        rb2.setToggleGroup(tg);
        rb2.setUserData("2");
        rb3.setToggleGroup(tg);
        rb3.setUserData("3");
        rb4.setToggleGroup(tg);
        rb4.setUserData("4");
        rb5.setToggleGroup(tg);
        rb5.setUserData("5");

        setUIByType(QuestionController.getInstance().question.getType());

    }

    @FXML
    public void save(ActionEvent event) {

        QuestionController qc = QuestionController.getInstance();
        qc.question.setQuestion(questionTextArea.getText());

        switch (qc.question.getType()) {
            case SIMPLE:
                if (checkInput()) { return; }
                qc.question.setStringAnswer(answerField.getText());
                qc.update1();
                Utils.showSuccess("Question saved");
                break;
            case NUMBER:
                if (checkInput()) { return; }
                try {
                    int i = Integer.parseInt(answerField.getText());
                    qc.question.setIntAnswer(i);
                    qc.update2();
                    Utils.showSuccess("Question saved");
                    break;
                } catch (Exception e) {
                    Utils.showWarning("Wrong format: " + e);
                    return;
                }
            case MULTIPLECHOICEONE:

                qc.question.setMultiAnswers(setAnswers());
                qc.question.setB1(false);
                qc.question.setB2(false);
                qc.question.setB3(false);
                qc.question.setB4(false);
                qc.question.setB5(false);
                try {
                    String s = tg.getSelectedToggle().getUserData().toString();
                    switch (s) {
                        case "1":
                            qc.question.setB1(true);
                            qc.update3();
                            break;
                        case "2":
                            qc.question.setB2(true);
                            qc.update3();
                            break;
                        case "3":
                            qc.question.setB3(true);
                            qc.update3();
                            break;
                        case "4":
                            qc.question.setB4(true);
                            qc.update3();
                            break;
                        case "5":
                            qc.question.setB5(true);
                            qc.update3();
                            break;

                    }
                    Utils.showSuccess("Question saved");
                }
                catch (Exception e) {
                    Utils.showWarning("Select an option");
                }
            case MULTIPLECHOICEMULTIPLE:
                qc.question.setMultiAnswers(setAnswers());
                qc.question.setB1(cb1.isSelected());
                qc.question.setB2(cb2.isSelected());
                qc.question.setB3(cb3.isSelected());
                qc.question.setB4(cb4.isSelected());
                qc.question.setB5(cb5.isSelected());
                qc.update3();
                Utils.showSuccess("Question saved");
        }

    }

    private List<String> setAnswers() {
        List<String> a = new ArrayList<>();
        a.add(tf1.getText());
        a.add(tf2.getText());
        a.add(tf3.getText());
        a.add(tf4.getText());
        a.add(tf5.getText());
        return a;
    }

    private boolean checkInput() {
        if (questionTextArea.getText().length() == 0 && answerField.getText().length() == 0) {
            Utils.showWarning("Fill all fields");
            return true;
        }
        return false;
    }

    private void setUIByType(QuestionType type) {
        switch (type) {
            case MULTIPLECHOICEONE:
                setTextFieldsVis(true);
                rb1.setVisible(true);
                rb2.setVisible(true);
                rb3.setVisible(true);
                rb4.setVisible(true);
                rb5.setVisible(true);
                break;
            case MULTIPLECHOICEMULTIPLE:
                setTextFieldsVis(true);
                cb1.setVisible(true);
                cb2.setVisible(true);
                cb3.setVisible(true);
                cb4.setVisible(true);
                cb5.setVisible(true);
                break;
        }
    }

    private void setTextFieldsVis(boolean visible) {
        answerField.setVisible(false);
        tf1.setVisible(visible);
        tf2.setVisible(visible);
        tf3.setVisible(visible);
        tf4.setVisible(visible);
        tf5.setVisible(visible);
    }

}
