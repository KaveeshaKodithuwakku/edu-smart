package com.kaveesha.edu.controller;

import com.kaveesha.edu.database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.sql.*;

public class ProgramDetailFormController {

    public AnchorPane context;
    public ListView<String> lstProgramDetails;

    public void setId(int id){
        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String query = "SELECT * FROM program_content WHERE program_program_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<String> obDetailList = FXCollections.observableArrayList();

            while (resultSet.next()){
                obDetailList.add(resultSet.getString(2));
            }

            lstProgramDetails.setItems(obDetailList);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
