package com.kaveesha.edu.controller;

import com.kaveesha.edu.database.DbConnection;
import com.kaveesha.edu.view.tm.IncomeTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class IncomeFormController {
    public AnchorPane incomeFormContext;
    public RadioButton rBtnLastMonth;
    public RadioButton rBtnToday;
    public RadioButton rBtnAll;
    public AnchorPane chartPane;

    public void initialize(){
        loadChart();
    }

    private void loadChart() {

        ObservableList<IncomeTM> obIncomeList = FXCollections.observableArrayList();

        try{
            Connection connection = DbConnection.getInstance().getConnection();
            String query = "SELECT date,SUM(amount) as income FROM payment WHERE is_verified = true GROUP BY date ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                obIncomeList.add(new IncomeTM(LocalDate.parse(resultSet.getString(1)),
                        resultSet.getDouble(2)));
            }


        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }


        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<String,Number> lineChart = new LineChart<>(xAxis,yAxis);
        XYChart.Series<String,Number> series = new XYChart.Series<>();


        obIncomeList.forEach(e -> {
            series.getData().add(new XYChart.Data<>(e.getDate().toString(),e.getAmount()));
        });


        lineChart.getData().add(series);
        chartPane.getChildren().add(lineChart);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashBoardForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) incomeFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }
}
