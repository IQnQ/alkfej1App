package view;

import controller.SurveyController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Survey;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    List<Survey> list;

    @FXML
    private TableView<Survey> table;

    @FXML
    private TableColumn<Survey, Integer> idCol, limitCol;

    @FXML
    private TableColumn<Survey, String> questionCol, fromCol, toCol, maxCol;

    @FXML
    private TableColumn<Survey, Boolean> filledOutCol;

    @FXML
    private TableColumn<Survey, Void> actionCol;

    @FXML
    public void refreshTable() {
        List<Survey> list = SurveyController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));
    }

    public MainViewController() { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm");
        final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("hh:mm");

        list = SurveyController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        questionCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        filledOutCol.setCellValueFactory(new PropertyValueFactory<>("isFilledOut"));
        fromCol.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getFrom().format(formatter)));
        toCol.setCellValueFactory(t -> new SimpleStringProperty(t.getValue().getTo().format(formatter)));
        maxCol.setCellValueFactory(m -> new SimpleStringProperty(m.getValue().getMaxT().format(formatter2)));
        limitCol.setCellValueFactory(new PropertyValueFactory<>("limit"));

        actionCol.setCellFactory(param -> {
            return new TableCell<>() {
                private final Button deleteBtn = new Button("Delete");
                private final Button editBtn = new Button("Edit");

                {
                    deleteBtn.setOnAction(event -> {
                        Survey s = getTableView().getItems().get(getIndex());
                        deletePerson(s);

                    });

                    editBtn.setOnAction(event -> {
                        openAddView(true, getTableView().getItems().get(getIndex()));

                    });
                }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox container = new HBox();
                        container.getChildren().addAll(deleteBtn, editBtn);
                        setGraphic(container);
                    }

                }

            };

        });
    }


    private void deletePerson(Survey s) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete Survey: '" + s.getName() + "'",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType.equals(ButtonType.YES)) {
                Task task = new Task<Boolean>() {
                    @Override
                    protected Boolean call() throws Exception {
                        return SurveyController.getInstance().delete(s);
                    }
                };

                Thread deleteThread = new Thread(task);
                deleteThread.start();

                try {
                    deleteThread.join();
                    refreshTable();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @FXML
    public void filterExpired(ActionEvent event) {
        table.getItems().clear();
        List<Survey> list = SurveyController.getInstance().getAll();
        for (Survey s: list) {
            if (!LocalDateTime.now().isBefore(s.getTo())) {
                table.getItems().add(s);
            }
        }
    }

    @FXML
    public void filterFuture(ActionEvent event) {
        table.getItems().clear();
        List<Survey> list = SurveyController.getInstance().getAll();
        for (Survey s: list) {
            if (!LocalDateTime.now().isAfter(s.getFrom())) {
                table.getItems().add(s);
            }
        }
    }

    @FXML
    public void filterFilled(ActionEvent event) {
        table.getItems().clear();
        List<Survey> list = SurveyController.getInstance().getAll();
        for (Survey s: list) {
            if (s.isIsFilledOut()) {
                table.getItems().add(s);
            }
        }
    }

    @FXML
    public void showAll(ActionEvent event) {
        refreshTable();
    }

    @FXML
    public void addSimpleSurvey(ActionEvent event) {
        openAddView(false, null);
    }

    private void openAddView(boolean setSurvey, Survey s) {
        if (setSurvey) {
            SurveyController.getInstance().setSurvey(s);
        } else {
            SurveyController.getInstance().createNew();
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addSurvey.fxml"));
            Parent root = loader.load();
            //AddSurveyViewController controller = loader.<AddSurveyViewController>getController();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            refreshTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

