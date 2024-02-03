package com.kaveesha.edu.controller;

import com.kaveesha.edu.database.DbConnection;
import com.kaveesha.edu.model.User;
import com.kaveesha.edu.util.GlobalVar;
import com.kaveesha.edu.util.security.PasswordManager;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupFormController {
    public AnchorPane registerFormContext;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public TextField txtFirstName;
    public TextField txtLastName;
    public Button btnSignUp;

    public void initialize(){
        txtFirstName.requestFocus();
    }

    public void signupOnAction(ActionEvent actionEvent) throws IOException {

        User user = new User(
                txtFirstName.getText(),
                txtLastName.getText(),
                txtEmail.getText(),
                txtPassword.getText()
        );

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String query = "INSERT INTO user VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user.getEmail());
           preparedStatement.setString(2,user.getFirst_name());
           preparedStatement.setString(3,user.getLast_name());
           preparedStatement.setString(4, PasswordManager.encrypt(user.getPassword()));
           preparedStatement.setBoolean(5,true);

            if(preparedStatement.executeUpdate() > 0){
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
