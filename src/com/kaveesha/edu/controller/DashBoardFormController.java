package com.kaveesha.edu.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashBoardFormController {
    public AnchorPane dashboardFormContext;

    public void studentClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("StudentForm");
    }

    public void programClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("ProgramsForm");
    }

    public void trainersClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("TrainerForm");
    }

    public void intakeClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("IntakeForm");
    }

    public void incomeClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("IncomeForm");
    }

    public void registrationClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("RegistrationForm");
    }

    public void reportsClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("ReportsForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) dashboardFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void makeBackupOnAction(ActionEvent actionEvent) {
        String username = "root";
        String password = "Kavee@95";
        String database = "eduSmart";


        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String timeStamp = simpleDateFormat.format(new Date());
            String fileName = "backup" + timeStamp + ".sql";

            String sqlCommand = "mysqldump --user="+username+" --password="+password+" --host=localhost "+database+" --result-file="+fileName;

            Process process = Runtime.getRuntime().exec(sqlCommand);
            int exitCode = process.waitFor();

            if(exitCode == 0 ){

                File backup = new File(fileName);
                if(backup.exists()){

                    FileInputStream fileInputStream = new FileInputStream(backup);
                    FileOutputStream fileOutputStream = new FileOutputStream("src/"+fileName);

                    byte[] buffer = new byte[1024];
                    int bytesRead ;

                    while ((bytesRead=fileInputStream.read(buffer))!=-1){
                        fileOutputStream.write(buffer,0,bytesRead);
                    }

                    new Alert(Alert.AlertType.INFORMATION,"Backup File was Created!").show();

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
