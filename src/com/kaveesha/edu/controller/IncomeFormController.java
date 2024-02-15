package com.kaveesha.edu.controller;

import com.kaveesha.edu.bo.BoFactory;
import com.kaveesha.edu.bo.custom.IncomeBo;
import com.kaveesha.edu.dao.custom.impl.IncomeDaoImpl;
import com.kaveesha.edu.dto.IncomeDto;
import com.kaveesha.edu.entity.Income;
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
import java.sql.SQLException;

public class IncomeFormController {
    public AnchorPane incomeFormContext;
    public RadioButton rBtnLastMonth;
    public RadioButton rBtnToday;
    public RadioButton rBtnAll;
    public AnchorPane chartPane;

    private IncomeBo incomeBo = BoFactory.getBo(BoFactory.BoType.INCOME);

    public void initialize(){
        loadChart();
    }

    private void loadChart() {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<String,Number> lineChart = new LineChart<>(xAxis,yAxis);
        XYChart.Series<String,Number> series = new XYChart.Series<>();

        try {
            for (IncomeDto income: incomeBo.getIncomeDetailsForChart()) {
                series.getData().add(new XYChart.Data<>(income.getDate().toString(),income.getAmount()));
            }
        } catch (SQLException | ClassNotFoundException e) {
           e.printStackTrace();
        }

//        obIncomeList.forEach(e -> {
//            series.getData().add(new XYChart.Data<>(e.getDate().toString(),e.getAmount()));
//        });


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
