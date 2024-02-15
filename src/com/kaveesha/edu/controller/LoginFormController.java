package com.kaveesha.edu.controller;

import com.kaveesha.edu.bo.BoFactory;
import com.kaveesha.edu.bo.custom.UserBo;
import com.kaveesha.edu.dao.DaoFactory;
import com.kaveesha.edu.dao.custom.UserDao;
import com.kaveesha.edu.entity.User;
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
    private UserBo userBo = BoFactory.getBo(BoFactory.BoType.USER);


    public void signInOnAction(ActionEvent actionEvent) throws IOException {
        try {

            User user = userBo.findUser(txtEmail.getText());

            if(!user.getEmail().equals(null) && !user.getPassword().equals(null)){
                if(PasswordManager.checkPassword(txtPassword.getText(),user.getPassword())){
                    GlobalVar.userEmail = user.getEmail();
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
