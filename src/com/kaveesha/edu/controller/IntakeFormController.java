package com.kaveesha.edu.controller;

import com.kaveesha.edu.database.DbConnection;
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
    
    public void initialize(){

        colIntakeId.setCellValueFactory(new PropertyValueFactory<>("intakeId"));
        colIntakeName.setCellValueFactory(new PropertyValueFactory<>("intakeName"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("buttonBar"));

        loadPrograms();
        loadTableData(searchText);

        cmbProgram.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
               selectedProgramId = newValue.split(" : ")[0];
                });
    }

    private void loadPrograms() {
        try {

            Connection connection = DbConnection.getInstance().getConnection();
            String query = "SELECT program_id,program_name FROM program";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<String> list = FXCollections.observableArrayList();

            while (resultSet.next()) {
                list.add(resultSet.getString(1) + " : " + resultSet.getString(2));
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

        Intake intake = new Intake(
                0,
                txtIntakeName.getText(),
                txtDate.getValue(),
                Long.parseLong(selectedProgramId)
                );

        if(btnSaveUpdate.getText().equalsIgnoreCase("Save Intake")){
               try {
                   Connection connection = DbConnection.getInstance().getConnection();
                    String query = "INSERT INTO intake(intake_name,start_date,program_program_id) VALUES(?,?,?)";

                    PreparedStatement preparedStatement = connection.prepareStatement(query);

                    preparedStatement.setString(1,intake.getIntakeName());
                    preparedStatement.setDate(2,java.sql.Date.valueOf(intake.getStartDate()));
                    preparedStatement.setLong(3,intake.getProgramId());

                    boolean isSaved = preparedStatement.executeUpdate()>0;
                    if(isSaved){
                        new Alert(Alert.AlertType.INFORMATION,"Intake Saved!").show();
                        clearFields();
                        loadTableData(searchText);
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
                Connection connection = DbConnection.getInstance().getConnection();
                String query = "UPDATE intake SET intake_name =?, start_date =?, program_program_id =? WHERE intake_id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,intake.getIntakeName());
                preparedStatement.setDate(2,java.sql.Date.valueOf(intake.getStartDate()));
                preparedStatement.setLong(3,intake.getProgramId());
                preparedStatement.setLong(4,selectedIntakeId);


                if(preparedStatement.executeUpdate() > 0){
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
            Connection connection = DbConnection.getInstance().getConnection();

            String query = "SELECT i.intake_id,i.intake_name, i.start_date, p.program_name FROM intake i INNER JOIN" +
                    " program p ON i.program_program_id=p.program_id WHERE intake_name LIKE ? ";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,searchText);

            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<IntakeTM> obIntakeList = FXCollections.observableArrayList();

            while (resultSet.next()){

                Button deleteBtn = new Button("Delete");
                Button updateBtn = new Button("Update");
                ButtonBar buttonBar = new ButtonBar();
                buttonBar.getButtons().addAll(deleteBtn,updateBtn);

                IntakeTM intakeTM = new IntakeTM(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(4),
                        LocalDate.parse(resultSet.getString(3)),
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
                            Connection connection1 = DbConnection.getInstance().getConnection();
                            String query1 = "DELETE FROM intake WHERE intake_id = ?";
                            PreparedStatement preparedStatement1 = connection1.prepareStatement(query1);
                            preparedStatement1.setLong(1,intakeTM.getIntakeId());

                            if(preparedStatement1.executeUpdate() > 0){
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
