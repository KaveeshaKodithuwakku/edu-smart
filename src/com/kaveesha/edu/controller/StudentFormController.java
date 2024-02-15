package com.kaveesha.edu.controller;

import com.kaveesha.edu.bo.BoFactory;
import com.kaveesha.edu.bo.custom.StudentBo;
import com.kaveesha.edu.dto.StudentDto;
import com.kaveesha.edu.validates.SimpleTextValidation;
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
    private StudentBo studentBo = BoFactory.getBo(BoFactory.BoType.STUDENT);

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

        txtName.getStyleClass().remove("error");

        if(SimpleTextValidation.validateName(txtName.getText())){
            txtName.getStyleClass().add("error");
            new Alert(Alert.AlertType.WARNING, "Wrong Student name").show();
            return;
        }

        StudentDto student= new StudentDto(0,txtName.getText(),
                txtEmail.getText(),
                txtDob.getValue(),txtAddress.getText(),true);

        if(btnSaveUpdate.getText().equalsIgnoreCase("Save Student")){
            try{
                if(studentBo.saveStudent(student)){
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
                if(studentBo.updateStudent(student,rBtnActive.isSelected(),selectedId)){
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

            ObservableList<StudentTM> obStudentList = FXCollections.observableArrayList();

            for (StudentDto student:studentBo.findAllStudent(searchText)
                 ) {

                Button deleteBtn = new Button("Delete");
                Button updateBtn = new Button("Update");
                ButtonBar buttonBar = new ButtonBar();
                buttonBar.getButtons().addAll(deleteBtn,updateBtn);

                deleteBtn.getStyleClass().add("delete-button");
                updateBtn.getStyleClass().add("update-button");

                StudentTM studentTM = new StudentTM(
                        student.getStudentId(),
                        student.getStudentName(),
                        student.getEmail(),
                        student.getDate().toString(),
                        student.getAddress(),
                        student.isStatus()?"Active":"InActive",
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
                            if(studentBo.deleteStudent(studentTM.getId()) ){
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
