package com.kaveesha.edu.controller;

import com.kaveesha.edu.bo.BoFactory;
import com.kaveesha.edu.bo.custom.IntakeBo;
import com.kaveesha.edu.bo.custom.ProgramBo;
import com.kaveesha.edu.database.DbConnection;
import com.kaveesha.edu.dto.IntakeDto;
import com.kaveesha.edu.dto.ProgramDto;
import com.kaveesha.edu.dto.StudentDto;
import com.kaveesha.edu.model.Intake;
import com.kaveesha.edu.util.GlobalVar;
import com.kaveesha.edu.view.tm.IntakeTM;
import com.kaveesha.edu.view.tm.StudentTM;
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
import java.time.LocalDate;
import java.util.Optional;

public class IntakeFormController {
    public AnchorPane intakeFormContext;
    public Button btnSaveUpdate;
    public TextField txtIntakeName;
    public TextField txtSearch;
    public TableView tblIntake;
    public TableColumn colIntakeId;
    public TableColumn colProgram;
    public TableColumn colIntakeName;
    public TableColumn colStartDate;
    public TableColumn colOption;
    public ComboBox<String> cmbProgram;
    public DatePicker txtDate;
    String selectedProgramId = "";
    String searchText = "";
    long selectedIntakeId = 0;
    private IntakeBo intakeBo = BoFactory.getBo(BoFactory.BoType.INTAKE);
    private ProgramBo programBo = BoFactory.getBo(BoFactory.BoType.PROGRAM);
    
    public void initialize(){

        colIntakeId.setCellValueFactory(new PropertyValueFactory<>("intakeId"));
        colIntakeName.setCellValueFactory(new PropertyValueFactory<>("intakeName"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("buttonBar"));

    //    loadPrograms();
        loadTableData(searchText);

        cmbProgram.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
               selectedProgramId = newValue.split(" : ")[0];
                });
    }

    private void loadPrograms() {
        try {
            ObservableList<String> list = FXCollections.observableArrayList();

            for (ProgramDto programDto: programBo.loadPrograms()) {
                list.add(programDto.getProgramId() + " : " + programDto.getProgramName());
            }
            cmbProgram.setItems(list);
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashBoardForm");
    }

    public void saveUpdateOnAction(ActionEvent actionEvent) {

        IntakeDto intake = new IntakeDto(
                0,
                txtIntakeName.getText(),
                txtDate.getValue(),
                Long.parseLong(selectedProgramId),
                ""
                );

        if(btnSaveUpdate.getText().equalsIgnoreCase("Save Intake")){
               try {
                   if(intakeBo.saveIntake(intake)){
                        new Alert(Alert.AlertType.INFORMATION,"Intake Saved!").show();
                        clearFields();
                        loadTableData(searchText);
                   }else{
                       new Alert(Alert.AlertType.WARNING, "Try Again").show();
                   }
                } catch (ClassNotFoundException | SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                    e.printStackTrace();
                }
        }else{

            if (selectedIntakeId==0){
                new Alert(Alert.AlertType.ERROR, "Please verify the intake id").show();
                return;
            }

            try {
                if(intakeBo.updateIntake(intake,selectedIntakeId)){
                    new Alert(Alert.AlertType.INFORMATION,"Intake updated").show();
                    clearFields();
                    loadTableData(searchText);
                    btnSaveUpdate.setText("Save Intake");
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try again").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void newIntakeOnAction(ActionEvent actionEvent) {
        clearFields();
        btnSaveUpdate.setText("Save Intake");
    }

    private void loadTableData(String searchText) {
        searchText = "%"+searchText+"%";

        try {

            ObservableList<IntakeTM> obIntakeList = FXCollections.observableArrayList();

            for (IntakeDto intakeDto:intakeBo.findAllIntakes(searchText)
            ) {

                Button deleteBtn = new Button("Delete");
                Button updateBtn = new Button("Update");
                ButtonBar buttonBar = new ButtonBar();
                buttonBar.getButtons().addAll(deleteBtn,updateBtn);

                IntakeTM intakeTM = new IntakeTM(
                        intakeDto.getIntakeId(),
                        intakeDto.getIntakeName(),
                        intakeDto.getProgramName(),
                        intakeDto.getStartDate(),
                        buttonBar
                );

                obIntakeList.add(intakeTM);

                updateBtn.setOnAction(e -> {
                    txtIntakeName.setText(intakeTM.getIntakeName());
                    txtDate.setValue(LocalDate.parse(intakeTM.getStartDate().toString()));
                    cmbProgram.setValue(intakeTM.getProgramName());
                    btnSaveUpdate.setText("Update Intake");
                    selectedIntakeId = intakeTM.getIntakeId();

                });

                deleteBtn.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if(buttonType.get() == ButtonType.YES){
                        try {
                            if(intakeBo.deleteIntake(intakeTM.getIntakeId()) ){
                                new Alert(Alert.AlertType.INFORMATION,"Intake deleted").show();
                                loadTableData("");
                                clearFields();
                            }else {
                                new Alert(Alert.AlertType.INFORMATION,"Try again!").show();
                            }
                        } catch (ClassNotFoundException | SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
            tblIntake.setItems(obIntakeList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    private void clearFields() {
        txtIntakeName.clear();
        cmbProgram.setValue(null);
        txtDate.setValue(null);
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) intakeFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
