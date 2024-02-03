package com.kaveesha.edu.controller;

import com.kaveesha.edu.database.DbConnection;
import com.kaveesha.edu.model.Student;
import com.kaveesha.edu.model.Trainer;
import com.kaveesha.edu.util.GlobalVar;
import com.kaveesha.edu.view.tm.StudentTM;
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
import java.time.LocalDate;
import java.util.Optional;

public class StudentFormController {
    public AnchorPane studentFormContext;
    public Button btnSaveUpdate;
    public TextField txtName;
    public TextField txtSearch;
    public TextField txtAddress;
    public DatePicker txtDob;
    public TextField txtEmail;
    public TableView<StudentTM> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colDob;
    public TableColumn colAddress;
    public TableColumn colStatus;
    public TableColumn colOption;
    public RadioButton rBtnInactive;
    public Label lblStatus;
    public RadioButton rBtnActive;
    private String searchText ="";
    private int selectedId = 0;

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("buttonBar"));

        manageTrainerStatus(false);

        loadStudents(searchText);

        txtSearch.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    searchText = newValue;
                    if(newValue != null){
                        loadStudents(searchText);
                    }
                });
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashBoardForm");
    }

    public void saveUpdateOnAction(ActionEvent actionEvent) {
        Student student= new Student(0,txtName.getText(),
                txtEmail.getText(),
                txtDob.getValue(),txtAddress.getText(),true);

        if(btnSaveUpdate.getText().equalsIgnoreCase("Save Student")){
            try{

                Connection connection = DbConnection.getInstance().getConnection();

                String query = "INSERT INTO student(student_name,email,dob,address,status,user_email)" +
                        " VALUES (?,?,?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,student.getStudentName());
                preparedStatement.setString(2,student.getEmail());
                preparedStatement.setDate(3,java.sql.Date.valueOf(student.getDate()));
                preparedStatement.setString(4,student.getAddress());
                preparedStatement.setBoolean(5, student.isStatus());
                preparedStatement.setString(6, GlobalVar.userEmail);


                if(preparedStatement.executeUpdate()>0){
                    new Alert(Alert.AlertType.INFORMATION, "Student was Saved!").show();
                    clearFields();
                    loadStudents(searchText);
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again").show();
                }

            }catch (ClassNotFoundException | SQLException e){
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
                e.printStackTrace();
            }
        }else{

            if (selectedId==0){
                new Alert(Alert.AlertType.ERROR, "Please verify the student id").show();
                return;
            }

            try {
                Connection connection = DbConnection.getInstance().getConnection();
                String query = "UPDATE student SET student_name =?, email =?, dob =?, address =?, status=? WHERE student_id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,student.getStudentName());
                preparedStatement.setString(2,student.getEmail());
                preparedStatement.setDate(3,java.sql.Date.valueOf(student.getDate()));
                preparedStatement.setString(4,student.getAddress());
                preparedStatement.setBoolean(5,rBtnActive.isSelected());
                preparedStatement.setInt(6,selectedId);


                if(preparedStatement.executeUpdate() > 0){
                    new Alert(Alert.AlertType.INFORMATION,"Student updated").show();
                    clearFields();
                    loadStudents(searchText);
                    btnSaveUpdate.setText("Save Student");
                    manageTrainerStatus(false);
                }else{
                    new Alert(Alert.AlertType.WARNING,"Try again").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
               e.printStackTrace();
            }
        }
    }

    private void clearFields() {
        txtName.clear();
        txtEmail.clear();
        txtDob.setValue(null);
        txtAddress.clear();
    }

    private void loadStudents(String searchText){
        searchText = "%"+searchText+"%";

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String query = "SELECT * FROM student WHERE student_name LIKE ? OR email LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,searchText);
            preparedStatement.setString(2,searchText);

            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<StudentTM> obStudentList = FXCollections.observableArrayList();

            while (resultSet.next()){

                Button deleteBtn = new Button("Delete");
                Button updateBtn = new Button("Update");
                ButtonBar buttonBar = new ButtonBar();
                buttonBar.getButtons().addAll(deleteBtn,updateBtn);

               StudentTM studentTM = new StudentTM(
                       resultSet.getInt(1),
                       resultSet.getString(2),
                       resultSet.getString(3),
                       resultSet.getString(4),
                       resultSet.getString(5),
                       resultSet.getBoolean(6)?"Active":"InActive",
                       buttonBar
               );

                obStudentList.add(studentTM);

               updateBtn.setOnAction(e -> {

                   txtName.setText(studentTM.getName());
                   txtAddress.setText(studentTM.getAddress());
                   txtEmail.setText(studentTM.getEmail());
                   txtDob.setValue(LocalDate.parse(studentTM.getDob()));
                   btnSaveUpdate.setText("Update Student");
                   selectedId = studentTM.getId();
                   if(studentTM.getStatus().equals("Active")){
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
                           Connection connection1 = DbConnection.getInstance().getConnection();
                           String query1 = "DELETE FROM student WHERE student_id = ?";
                           PreparedStatement preparedStatement1 = connection1.prepareStatement(query1);
                           preparedStatement1.setInt(1,studentTM.getId());

                           if(preparedStatement1.executeUpdate() > 0){
                                new Alert(Alert.AlertType.INFORMATION,"Student deleted").show();
                                loadStudents("");
                                manageTrainerStatus(false);
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
            tblStudent.setItems(obStudentList);
        } catch (ClassNotFoundException | SQLException e) {
           e.printStackTrace();
           new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }

    private void manageTrainerStatus(boolean b) {
        lblStatus.setVisible(b);
        rBtnActive.setVisible(b);
        rBtnInactive.setVisible(b);
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) studentFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void newStudentOnAction(ActionEvent actionEvent) {
        clearFields();
        manageTrainerStatus(false);
        btnSaveUpdate.setText("Save Student");
        selectedId = 0;
    }
}
