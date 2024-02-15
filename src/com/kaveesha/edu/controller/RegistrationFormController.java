package com.kaveesha.edu.controller;

import com.kaveesha.edu.database.DbConnection;
import com.kaveesha.edu.model.Intake;
import com.kaveesha.edu.model.Payment;
import com.kaveesha.edu.model.Registration;
import com.kaveesha.edu.model.Student;
import com.kaveesha.edu.view.tm.IntakeTM;
import com.kaveesha.edu.view.tm.RegistrationTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

public class RegistrationFormController {
    public AnchorPane registrationFormContext;
    public Button btnRegister;
    public TextField txtSearch;
    public TableView tblRegistration;
    public TableColumn colIntakeId;
    public TableColumn colProgram;
    public TableColumn colName;
    public TableColumn colRegDate;
    public TableColumn colOption;
    public ComboBox<String> cmbProgram;
    public ComboBox<String> cmbIntake;
    public TextField txtStudentAutoSearch;
    String searchText = "";
    String selectedIntakeId = "";
    String selectedProgramId = "";
    String getSelectedRegId = "";

    public void initialize(){

        colIntakeId.setCellValueFactory(new PropertyValueFactory<>("intakeId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("buttonBar"));

        getStudentDetails();
        loadPrograms();
        loadTableData(searchText);

//        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
//            searchText = newValue;
//            if(newValue != null){
//                loadTableData(searchText);
//            }
//        });

        cmbProgram.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedProgramId = newValue.split(" : ")[0];
                    loadIntakes(Integer.parseInt(selectedProgramId));
                });

        cmbIntake.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedIntakeId = newValue.split(" : ")[0];
                });

    }

    public void getStudentDetails(){
        try {

            Connection connection = DbConnection.getInstance().getConnection();
            String query = "SELECT email FROM student";
         //   String query = "SELECT student_id,student_name FROM student";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<String> list = FXCollections.observableArrayList();

            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }

            TextFields.bindAutoCompletion(txtStudentAutoSearch,list);

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }
    }


    public void saveUpdateOnAction(ActionEvent actionEvent) {

        double amount = getProgramAmount(Long.parseLong(selectedProgramId));
        if (amount==0){
            new Alert(Alert.AlertType.WARNING,"Program not found!").show();
            return;
        }

        Long studentId = getStudentId(txtStudentAutoSearch.getText());
        if (studentId==0){
            new Alert(Alert.AlertType.WARNING,"Student not found!").show();
            return;
        }

        Registration registration = new Registration(
                0,
                LocalDate.now(),
                amount,
                Long.parseLong(selectedIntakeId),
               studentId
        );

       boolean isSaved = saveRegistration(registration);

       if(isSaved){

           Long regId = getRegistrationId(registration);
           if (regId==0){
               new Alert(Alert.AlertType.WARNING,"Registration is not found!").show();
               return;
           }

           Payment payment = new Payment(
                   0L,
                   LocalDate.now(),
                    true,
                   amount,
                   regId
           );


           boolean isPaymentSaved = savePayment(payment);
           if (isPaymentSaved){
               new Alert(Alert.AlertType.INFORMATION,"Registration is success").show();
               loadTableData(searchText);
               clearFields();
               return;
           }
       }


    }

    private boolean savePayment(Payment payment) {
        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String query = "INSERT INTO  payment(date,is_verified,amount,registration_registration_id) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setObject(1, payment.getPayDate());
            preparedStatement.setObject(2, payment.isVerified());
            preparedStatement.setObject(3, payment.getAmount());
            preparedStatement.setObject(4, payment.getRegistration_id());

            return preparedStatement.executeUpdate()>0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Long getRegistrationId(Registration registration) {

        try {

            Connection connection = DbConnection.getInstance().getConnection();
            String query = "SELECT registration_id FROM registration WHERE intake_intake_id=? AND student_student_id=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,registration.getIntakeId());
            preparedStatement.setLong(2,registration.getStudentId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    private Long getStudentId(String text) {
        try {

            Connection connection = DbConnection.getInstance().getConnection();
            String query = "SELECT student_id FROM student WHERE email=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,text);

            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<String> list = FXCollections.observableArrayList();

            while (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public boolean saveRegistration(Registration obj){

        if(btnRegister.getText().equalsIgnoreCase("Register")){
            try {
                Connection connection = DbConnection.getInstance().getConnection();
                String query = "INSERT INTO registration(register_date,program_amount,intake_intake_id,student_student_id) VALUES(?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setDate(1,java.sql.Date.valueOf(obj.getRegDate()));
                preparedStatement.setDouble(2,obj.getAmount());
                preparedStatement.setLong(3,obj.getIntakeId());
                preparedStatement.setLong(4,obj.getStudentId());

               return preparedStatement.executeUpdate()>0;

            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                e.printStackTrace();
            }

        }else{

//            if (selectedIntakeId==0){
//                new Alert(Alert.AlertType.ERROR, "Please verify the intake id").show();
//                return;
//            }

//            try {
//                  Connection connection = DbConnection.getInstance().getConnection();
//                String query = "UPDATE intake SET intake_name =?, start_date =?, program_program_id =? WHERE intake_id =?";
//                PreparedStatement preparedStatement = connection.prepareStatement(query);
//                preparedStatement.setString(1,intake.getIntakeName());
//                preparedStatement.setDate(2,java.sql.Date.valueOf(intake.getStartDate()));
//                preparedStatement.setLong(3,intake.getProgramId());
//                preparedStatement.setLong(4,selectedIntakeId);
//
//
//                if(preparedStatement.executeUpdate() > 0){
//                    new Alert(Alert.AlertType.INFORMATION,"Intake updated").show();
//                    clearFields();
//                    loadTableData(searchText);
//                    btnSaveUpdate.setText("Save Intake");
//                }else{
//                    new Alert(Alert.AlertType.WARNING,"Try again").show();
//                }
//            } catch (ClassNotFoundException | SQLException e) {
//                e.printStackTrace();
//            }
        }
        return false;
    }

    public void newRegistrationOnAction(ActionEvent actionEvent) {
        clearFields();
        btnRegister.setText("Registration");
    }

    private void loadTableData(String searchText) {
        searchText = "%"+searchText+"%";

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String query = "Select r.registration_id, r.intake_intake_id, p.program_name, s.student_name, r.register_date FROM registration r,program p, student s\n" +
                    " WHERE r.student_student_id = s.student_id AND p.program_id = (SELECT program_program_id FROM intake i WHERE \n" +
                    " i.intake_id = r.intake_intake_id) ";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
           // preparedStatement.setString(1,searchText);

            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<RegistrationTM> obRegistrationList = FXCollections.observableArrayList();

            while (resultSet.next()){

                Button deleteBtn = new Button("Delete");
                Button updateBtn = new Button("Update");
                ButtonBar buttonBar = new ButtonBar();
                buttonBar.getButtons().addAll(deleteBtn,updateBtn);

                RegistrationTM registrationTM = new RegistrationTM(
                        resultSet.getInt(1),
                        LocalDate.parse(resultSet.getString(5)),
                        resultSet.getString(3),
                        resultSet.getLong(2),
                        resultSet.getString(4),
                        buttonBar
                );

                obRegistrationList.add(registrationTM);

                updateBtn.setOnAction(e -> {
                  //  txtStudentAutoSearch.setText(registrationTM.getStudentName());
                    cmbProgram.setValue(registrationTM.getProgramName());
                   // cmbIntake.setValue(registrationTM.getIntakeId());
                    btnRegister.setText("Update Registration");
                    selectedIntakeId = String.valueOf(registrationTM.getRegistrationId());

                });

//                deleteBtn.setOnAction(e -> {
//                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES,ButtonType.NO);
//                    Optional<ButtonType> buttonType = alert.showAndWait();
//
//                    if(buttonType.get() == ButtonType.YES){
//                        try {
//                            Connection connection1 = DbConnection.getInstance().getConnection();
//                            String query1 = "DELETE FROM registration WHERE registration_id = ?";
//                            PreparedStatement preparedStatement1 = connection1.prepareStatement(query1);
//                            preparedStatement1.setLong(1,intakeTM.getIntakeId());
//
//                            if(preparedStatement1.executeUpdate() > 0){
//                                new Alert(Alert.AlertType.INFORMATION,"Intake deleted").show();
//                                loadTableData("");
//                                clearFields();
//                            }else {
//                                new Alert(Alert.AlertType.INFORMATION,"Try again!").show();
//                            }
//                        } catch (ClassNotFoundException | SQLException ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                });
            }
            tblRegistration.setItems(obRegistrationList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    public double getProgramAmount(long programId){
        try {

            Connection connection = DbConnection.getInstance().getConnection();
            String query = "SELECT amount FROM program WHERE program_id =?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,programId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
               return resultSet.getDouble(1);
            }

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }

        return 0;
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

    private void loadIntakes(int id) {
        try {

            Connection connection = DbConnection.getInstance().getConnection();
            String query = "SELECT intake_id,intake_name FROM Intake WHERE program_program_id =?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<String> list = FXCollections.observableArrayList();

            while (resultSet.next()) {
                list.add(resultSet.getString(1) + " : " + resultSet.getString(2));
            }

            cmbIntake.setItems(list);

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtStudentAutoSearch.clear();
        cmbIntake.setValue(null);
        cmbProgram.setValue(null);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashBoardForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) registrationFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

}
