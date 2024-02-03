package com.kaveesha.edu.controller;

import com.kaveesha.edu.database.DbConnection;
import com.kaveesha.edu.util.GlobalVar;
import com.kaveesha.edu.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginFormController {
    public AnchorPane loginContext;
    public TextField txtEmail;
    public PasswordField txtPassword;

    public void signInOnAction(ActionEvent actionEvent) throws IOException {
        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String query = "SELECT email,password FROM user WHERE email =?";
            System.out.println(query);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,txtEmail.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                if(PasswordManager.checkPassword(txtPassword.getText(),resultSet.getString(2))){
                    GlobalVar.userEmail = resultSet.getString(1);
                    setUi("DashBoardForm");
                    return;
                }else {
                    new Alert(Alert.AlertType.WARNING, "password wrong").show();
                }
            }else {
                new Alert(Alert.AlertType.WARNING,"Please check your credentials and try again").show();
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.WARNING,"Something went wrong!").show();
            e.printStackTrace();
        }
    }

    public void createAnAccountOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignupForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) loginContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
