package com.kaveesha.edu.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashBoardFormController {
    public AnchorPane dashboardFormContext;
    public Label lblTime;
    public Label lblDate;

    public void initialize(){
        setData();
    }

    private void setData() {

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1), event -> {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    String currentTime = simpleDateFormat.format(new Date());
                    lblTime.setText(currentTime);
                }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


        //-------------------------
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(simpleDateFormat.format(date));

    }

    public void studentClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("StudentForm","student");
    }

    public void programClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("ProgramsForm","program");
    }

    public void trainersClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("TrainerForm","trainer");
    }

    public void intakeClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("IntakeForm","intake");
    }

    public void incomeClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("IncomeForm","income");
    }

    public void registrationClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("RegistrationForm","registration");
    }

    public void reportsClickOnAction(MouseEvent mouseEvent) throws IOException {
        setUi("ReportsForm","reports");
    }

    private void setUi(String location, String styleSheet) throws IOException {

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml")));
        scene.getStylesheets().add(getClass().getResource("../view/styles/"+styleSheet+".css").toExternalForm());
        Stage stage = (Stage) dashboardFormContext.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();

//        Stage stage = (Stage) dashboardFormContext.getScene().getWindow();
//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
//        stage.centerOnScreen();
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
