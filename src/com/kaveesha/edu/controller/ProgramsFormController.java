package com.kaveesha.edu.controller;

import com.kaveesha.edu.database.DbConnection;
import com.kaveesha.edu.model.Program;
import com.kaveesha.edu.model.ProgramContent;
import com.kaveesha.edu.model.Trainer;
import com.kaveesha.edu.util.GlobalVar;
import com.kaveesha.edu.view.tm.ProgramTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class ProgramsFormController {
    public AnchorPane programFormContext;
    public TextField txtProgramName;
    public TextField txtSearch;
    public TextField txtContent;
    public TextField txtHours;
    public TableView<ProgramTM> tblProgram;
    public TableColumn colTrainerId;
    public TableColumn colProgram;
    public TableColumn colHours;
    public TableColumn colAmount;
    public TableColumn colOption;
    public TextField txtAmount;
    public ComboBox<String> cmbTrainer;
    public ListView lstProgramContent;
    public Button btnSaveUpdate;
    private String selectedTrainerId = "";
    private String searchText = "";
    private int selectedProgramId = 0;
    private ArrayList<String> trainerList = new ArrayList<>();
    ObservableList<String> contents = FXCollections.observableArrayList();

    public void initialize(){

        colTrainerId.setCellValueFactory(new PropertyValueFactory<>("ProgramId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("buttonBar"));

        getTrainerDetails();
        loadPrograms(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            if(newValue != null){
                loadPrograms(searchText);
            }
        });

        cmbTrainer.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
            selectedTrainerId = newValue.split(":")[0];
        });

    }


    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashBoardForm");
    }

    public void btnSaveUpdateOnAction(ActionEvent actionEvent) {
        Program program = new Program(
                0,
                txtProgramName.getText(),
                Integer.parseInt(txtHours.getText()),
                Double.parseDouble(txtAmount.getText()),
                GlobalVar.userEmail,
                Long.parseLong(cmbTrainer.getValue().split(" : ")[0]),
                contents
        );

        if(btnSaveUpdate.getText().equalsIgnoreCase("Save Program")){
            try {
                Connection connection = DbConnection.getInstance().getConnection();
                String query = "INSERT INTO program(program_name,hours,amount,user_email,trainer_trainer_id)" +
                        " VALUES (?,?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,program.getProgramName());
                preparedStatement.setInt(2,program.getHours());
                preparedStatement.setDouble(3,program.getAmount());
                preparedStatement.setString(4, program.getUserEmail());
                preparedStatement.setLong(5, program.getTrainerId());

                boolean isSaved = preparedStatement.executeUpdate() > 0;

                PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT program_id FROM program ORDER BY program_id DESC LIMIT 1");
                ResultSet resultSet = preparedStatement1.executeQuery();

                if(resultSet.next()){
                    long pId = resultSet.getLong(1);
                    if(isSaved){
                        for(String cnt : contents){
                            PreparedStatement preparedStatement2 =
                                    connection.prepareStatement("INSERT INTO program_content(header,program_program_id) VALUES(?,?)");
                            preparedStatement2.setObject(1, cnt);
                            preparedStatement2.setObject(2, pId);

                            preparedStatement2.executeUpdate();
                        }
                        new Alert(Alert.AlertType.INFORMATION, "Program Saved!").show();
                        clearFields();
                        loadPrograms(searchText);
                    }else{
                        new Alert(Alert.AlertType.WARNING, "Try Again").show();
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }else {

            if (selectedProgramId==0){
                new Alert(Alert.AlertType.ERROR, "Please verify the program id").show();
                return;
            }

            try {
                Connection connection = DbConnection.getInstance().getConnection();
                String query = "UPDATE program SET program_name =?, amount=?, hours=? WHERE program_id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,program.getProgramName());
                preparedStatement.setDouble(2,program.getAmount());
                preparedStatement.setInt(3,program.getHours());
                preparedStatement.setInt(4,selectedProgramId);

                if(preparedStatement.executeUpdate() > 0){
                    new Alert(Alert.AlertType.INFORMATION,"Program updated successfully").show();
                    clearFields();
                    loadPrograms(searchText);
                    btnSaveUpdate.setText("Save Program");
                }else {
                    new Alert(Alert.AlertType.WARNING,"Try Again").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void newProgramOnAction(ActionEvent actionEvent) {
        clearFields();
        btnSaveUpdate.setText("Save Program");
    }

    private void getTrainerDetails(){

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String query = "SELECT * FROM trainer";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                trainerList.add(resultSet.getInt(1) + " : " +resultSet.getString(2));
            }

            ObservableList<String> obTrainerList = FXCollections.observableArrayList(trainerList);
            cmbTrainer.setItems(obTrainerList);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadPrograms(String searchText) {

        ObservableList<ProgramTM> obProgramList = FXCollections.observableArrayList();
        searchText = "%"+searchText+"%";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            String query = "SELECT * FROM program WHERE program_name LIKE ? OR trainer_trainer_id LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,searchText);
            preparedStatement.setString(2,searchText);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                Button showBtn = new Button("Show");
                Button deleteBtn = new Button("Delete");
                Button updateBtn = new Button("Update");
                ButtonBar buttonBar = new ButtonBar();
                buttonBar.getButtons().addAll(showBtn,deleteBtn,updateBtn);

                ProgramTM programTM = new ProgramTM(
                        resultSet.getInt(1),
                        resultSet.getString(3),
                        resultSet.getInt(2),
                        resultSet.getDouble(4),
                        buttonBar
                );

                obProgramList.add(programTM);

                showBtn.setOnAction(e -> {
                    try{
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/ProgramDetailForm.fxml"));
                        Parent parent = fxmlLoader.load();
                        ProgramDetailFormController controller = fxmlLoader.getController();
                        controller.setId(programTM.getProgramId());
                        Stage stage = (Stage) programFormContext.getScene().getWindow();
                        stage.setScene(new Scene(parent));
                        stage.show();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                updateBtn.setOnAction(e -> {

                    txtProgramName.setText(programTM.getProgramName());
                    txtHours.setText(String.valueOf(programTM.getHours()));
                    txtAmount.setText(String.valueOf(programTM.getAmount()));
                    btnSaveUpdate.setText("Update Program");
                    selectedProgramId = programTM.getProgramId();
                    lstProgramContent.setItems(getSavedContentList(programTM.getProgramId()));

                });

                deleteBtn.setOnAction(e -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES,ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if(buttonType.get() == ButtonType.YES){
                        try {
                            Connection connection1 = DbConnection.getInstance().getConnection();
                            String query1 = "DELETE FROM program WHERE program_id = ?";
                            PreparedStatement preparedStatement1 = connection1.prepareStatement(query1);
                            preparedStatement1.setInt(1,programTM.getProgramId());

                            if(preparedStatement1.executeUpdate() > 0){
                                new Alert(Alert.AlertType.INFORMATION,"Program deleted").show();
                                loadPrograms("");
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
            tblProgram.setItems(obProgramList);

        } catch (ClassNotFoundException | SQLException e) {
          e.printStackTrace();
        }
    }

    private ObservableList<String> getSavedContentList(int id) {

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String query = "SELECT header FROM program_content WHERE program_program_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
               resultSet.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<String> getProgramContentDetails(int id) {

        ArrayList<ProgramContent>  list = new ArrayList<>();
        ProgramContent programContent = new ProgramContent();

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            String query = "SELECT * FROM program_content WHERE program_program_id =?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                programContent.setPropertyId(resultSet.getLong(1));
                programContent.setHeader(resultSet.getString(2));
                programContent.setProgramId(resultSet.getLong(3));
            }

            list.add(programContent);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void clearFields() {
        txtAmount.clear();
        txtContent.clear();
        txtHours.clear();
        txtProgramName.clear();
        txtContent.clear();
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) programFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/" + location + ".fxml"))));
        stage.centerOnScreen();
    }

    public void txtContentOnAction(ActionEvent actionEvent) {
        contents.add(txtContent.getText());
        txtContent.clear();
        lstProgramContent.setItems(contents);
    }
}
