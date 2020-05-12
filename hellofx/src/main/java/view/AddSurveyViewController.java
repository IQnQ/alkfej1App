package view;

import controller.QuestionController;
import controller.SurveyController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Question;
import model.QuestionType;
import utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class AddSurveyViewController implements Initializable {

    //Survey survey;

    @FXML
    private TableView<Question> table;

    @FXML
    private ComboBox typeBox;

    @FXML
    private TableColumn<Question, Integer> idCol;

    @FXML
    private TableColumn<Question, String> qCol, typeCol;

    @FXML
    private DatePicker fromPicker, toPicker;

    @FXML
    private TextField fromTime, toTime, maxTime, limitCount, nameText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Question> list = QuestionController.getInstance().getSurveyQuestions(SurveyController.getInstance().survey.getId());
        table.setItems(FXCollections.observableList(list));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        qCol.setCellValueFactory(new PropertyValueFactory<>("question"));
        typeCol.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getType().toString()));

        QuestionController qc = new QuestionController();

        typeBox.getItems().addAll("Single answer - Text",
                                        "Single answer - Number",
                                        "Single answer - Date",
                                        "Single answer - Options",
                                        "Multiple answer - Options");
    }

    public AddSurveyViewController() {}

    @FXML
    public void addQuestionBtn(ActionEvent event) {

        if (typeBox.getValue() == null) {
            Utils.showWarning("Choose question type");
            return;
        }

        try {
            createHelperQuestion();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addQuestion.fxml"));
            Parent root = loader.load();
            AddQuestionViewController controller = loader.<AddQuestionViewController>getController();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            List<Question> list = QuestionController.getInstance().getSurveyQuestions(SurveyController.getInstance().survey.getId());
            table.setItems(FXCollections.observableList(list));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveSurvey(ActionEvent event) {
        if (table.getItems().isEmpty()) {
            Utils.showWarning("Add at least one question!");
            return;
        }
        LocalDate fromD = fromPicker.getValue();
        LocalDate toD = toPicker.getValue();
        try {
            String sName = nameText.getText();
            LocalTime fromT = LocalTime.parse(fromTime.getText());
            LocalTime toT = LocalTime.parse(toTime.getText());
            LocalTime maxT = LocalTime.parse(maxTime.getText());
            int limit = Integer.parseInt(limitCount.getText());
            if (    fromD != null &&
                    toD != null &&
                    fromT != null &&
                    toT != null &&
                    maxT != null &&
                    limit != 0 &&
                    sName.length() != 0
            ) {
                SurveyController sc = SurveyController.getInstance();
                sc.survey.set(sName, fromD, toD, fromT, toT, maxT, limit);
                sc.update();

            } else {
                Utils.showWarning("Fill al fields");
            }
        } catch (Exception e) {
            Utils.showWarning("Missing field or format error: " + e);
        }
    }


    public void createHelperQuestion() {
        QuestionType type = Question.typeConvert(typeBox.getValue().toString());
        QuestionController.getInstance().createNew(SurveyController.getInstance().survey.getId(), type);

    }

}
