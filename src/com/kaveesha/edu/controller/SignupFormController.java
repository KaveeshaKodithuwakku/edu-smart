package com.kaveesha.edu.controller;

import com.kaveesha.edu.bo.BoFactory;
import com.kaveesha.edu.bo.custom.UserBo;
import com.kaveesha.edu.dao.DaoFactory;
import com.kaveesha.edu.dao.custom.UserDao;
import com.kaveesha.edu.dto.UserDto;
import com.kaveesha.edu.entity.User;
import com.kaveesha.edu.util.GlobalVar;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignupFormController {
    public AnchorPane registerFormContext;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public TextField txtFirstName;
    public TextField txtLastName;
    public Button btnSignUp;
    private UserBo userBo = BoFactory.getBo(BoFactory.BoType.USER);

    public void initialize(){
        txtFirstName.requestFocus();
    }

    public void signupOnAction(ActionEvent actionEvent) throws IOException {

        UserDto user = new UserDto(
                txtFirstName.getText(),
                txtLastName.getText(),
                txtEmail.getText(),
                txtPassword.getText(),
                true
        );

        try {

            if(userBo.saveUser(user)){
                new Alert(Alert.AlertType.INFORMATION,"User saved success").show();
                GlobalVar.userEmail = user.getEmail().trim();
                setUi("DashBoardForm");
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) registerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void emailNextOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void passwordNextOnAction(ActionEvent actionEvent) {
        btnSignUp.requestFocus();
    }

    public void fNameNextOnAction(ActionEvent actionEvent) {
        txtLastName.requestFocus();
    }

    public void lNameNextOnAction(ActionEvent actionEvent) {
            txtEmail.requestFocus();
    }
}
