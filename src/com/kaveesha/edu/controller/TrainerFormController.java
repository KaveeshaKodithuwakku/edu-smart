package com.kaveesha.edu.controller;

import com.kaveesha.edu.bo.BoFactory;
import com.kaveesha.edu.bo.custom.TrainerBo;
import com.kaveesha.edu.dto.TrainerDto;
import com.kaveesha.edu.view.tm.TrainerTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class TrainerFormController {
    public AnchorPane trainerFormContext;
    public TextField txtTrainerName;
    public TextField txtAddress;
    public TextField txtEmail;
    public TableView<TrainerTM> tblTrainer;
    public TableColumn colTid;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colNic;
    public TableColumn colAddress;
    public TableColumn colState;
    public TableColumn colOption;
    public TextField txtNic;
    public TextField txtSearch;
    public RadioButton rBtnActive;
    public ToggleGroup tgpStatus;
    public RadioButton rBtnInactive;
    public Label lblTStatus;
    public Button btnSaveUpdate;

    String searchText = "";
    int selectedTrainerId = 0;

    private TrainerBo trainerBo = BoFactory.getBo(BoFactory.BoType.TRAINER);

    public void initialize(){

        colTid.setCellValueFactory(new PropertyValueFactory<>("trainerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colState.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("buttonBar"));

        manageTrainerStatus(false);
        loadTrainers(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            if(newValue != null){
                loadTrainers(searchText);
            }
        });
    }

    private void manageTrainerStatus(boolean status) {
        lblTStatus.setVisible(status);
        rBtnInactive.setVisible(status);
        rBtnActive.setVisible(status);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashBoardForm");
    }

    public void btnSaveUpdateOnAction(ActionEvent actionEvent) {

        TrainerDto trainer = new TrainerDto(
                0,
                txtTrainerName.getText(),
                txtEmail.getText(),
                txtNic.getText(),
                txtAddress.getText(),
                true
        );

        if(btnSaveUpdate.getText().equalsIgnoreCase("Save Trainer")){
            try {
                if(trainerBo.saveTrainer(trainer)){
                    new Alert(Alert.AlertType.INFORMATION, "Trainer was Saved!").show();
                    clearFields();
                    loadTrainers(searchText);
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
               e.printStackTrace();
            }
        }else {

            if (selectedTrainerId==0){
                new Alert(Alert.AlertType.ERROR, "Please verify the trainer id").show();
                return;
            }

            try {
              if(trainerBo.updateTrainer(trainer, rBtnActive.isSelected(), selectedTrainerId )){
                  new Alert(Alert.AlertType.INFORMATION,"Trainer updated successfully").show();
                  clearFields();
                  loadTrainers(searchText);
                  btnSaveUpdate.setText("Save Trainer");
                  manageTrainerStatus(false);
              }else {
                  new Alert(Alert.AlertType.WARNING,"Try Again").show();
              }
            } catch (ClassNotFoundException | SQLException e) {
               e.printStackTrace();
            }
        }
    }

    private void loadTrainers(String searchText) {

        ObservableList<TrainerTM> obTrainerList = FXCollections.observableArrayList();
        searchText = "%"+searchText+"%";

        try {

            for (TrainerDto trainer: trainerBo.findAllTrainers(searchText)
                 ) {
                Button deleteBtn = new Button("Delete");
                Button updateBtn = new Button("Update");
                ButtonBar buttonBar = new ButtonBar();
                buttonBar.getButtons().addAll(deleteBtn,updateBtn);

                TrainerTM trainerTM = new TrainerTM(
                        trainer.getTrainerId(),
                        trainer.getTrainerName(),
                        trainer.getTrainerEmail(),
                        trainer.getNic(),
                        trainer.getAddress(),
                        trainer.isStatus()?"Active":"InActive",
                        buttonBar
                );

                obTrainerList.add(trainerTM);

                updateBtn.setOnAction(e -> {

                    txtTrainerName.setText(trainerTM.getTrainerName());
                    txtAddress.setText(trainerTM.getAddress());
                    txtEmail.setText(trainerTM.getEmail());
                    txtNic.setText(trainerTM.getNic());
                    btnSaveUpdate.setText("Update Trainer");
                    selectedTrainerId = trainerTM.getTrainerId();
                    if(trainerTM.getStatus().equals("Active")){
                        rBtnActive.setSelected(true);
                    }else {
                        rBtnInactive.setSelected(true);
                    }
                    manageTrainerStatus(true);
                });

                deleteBtn.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if(buttonType.get() == ButtonType.YES){
                        try {
                            if(trainerBo.deleteTrainer(trainerTM.getTrainerId())){
                                new Alert(Alert.AlertType.INFORMATION,"Trainer deleted").show();
                                loadTrainers("");
                                manageTrainerStatus(false);
                                clearFields();
                            }else {
                                new Alert(Alert.AlertType.INFORMATION,"Try again!").show();
                            }
                        } catch (ClassNotFoundException | SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }

            tblTrainer.setItems(obTrainerList);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtNic.clear();
        txtEmail.clear();
        txtAddress.clear();
        txtTrainerName.clear();
    }

    public void newTrainerOnAction(ActionEvent actionEvent) {
        clearFields();
        manageTrainerStatus(false);
        btnSaveUpdate.setText("Save Trainer");
        selectedTrainerId = 0;
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) trainerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
