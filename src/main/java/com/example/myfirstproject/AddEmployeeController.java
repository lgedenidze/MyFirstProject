package com.example.myfirstproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private ChoiceBox<String> jobTitleChoose;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private ChoiceBox<Integer> managerIdChoose;

    @FXML
    private ChoiceBox<Integer> officeChose;

    @FXML
    private Button saveButton;

    @FXML
    private Label resultLabel;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer>officeChoseChooseObsList=FXCollections.observableArrayList();
        ObservableList<String>jobTitleChooseObsList=FXCollections.observableArrayList();
        String jobQuery="select\n" +
                "distinct jobTitle\n" +
                "from jobs";
        String officeQuery="select officeCode from offices;";
        DataBase db=new DataBase();
        Connection con=db.getConnections();

        try {
            Statement stm=db.con.createStatement();

            ResultSet resultJob=stm.executeQuery(jobQuery);
            while (resultJob.next()){
                jobTitleChooseObsList.add(resultJob.getString(1));
            }

            ResultSet resultOffice=stm.executeQuery(officeQuery);
            while (resultOffice.next()){
                officeChoseChooseObsList.add(resultOffice.getInt(1));
            }

            jobTitleChoose.setItems(jobTitleChooseObsList);
            officeChose.setItems(officeChoseChooseObsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void saveMethods() {
        DataBase dataBase = new DataBase();
        Connection con = dataBase.getConnections();
        String queryManager = "select employeeNumber from employees a where jobTitle LIKE '%Manager%' and officeCode=" + officeChose.getValue();
        String queryEmpId = "SELECT MAX(employeeNumber)+1 from employees;";
        Integer empid = null;
        Integer managerId = null;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(queryEmpId);
            while (rs.next()) {
                empid = rs.getInt(1);
            }
            ResultSet resultSet = stmt.executeQuery(queryManager);
            while (resultSet.next()) {
                managerId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        Integer office = officeChose.getValue();
        String job = jobTitleChoose.getValue();
        if (firstName == null || lastName == null || email == null || managerId == null) resultLabel.setText("Add Procedure Failed");
        else {
            String insertNewEmp = "insert into employees values (" + empid + "," + "'" + lastName + "'" +
                    "," + "'" + firstName + "'" + "," + "'" + email + "'" + "," + office + "," + managerId + "," + "'" + job + "'" + ")";

            try {
                Statement stm = con.createStatement();
                int resultSet = stm.executeUpdate(insertNewEmp);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setHeaderText("Employee Added Successfully");
                alert.setContentText("Employee Info : Employee number - " + empid + ", Employee Name - " + firstName + " " + lastName);
                alert.show();
                emailTextField.setText(" ");
                firstNameTextField.setText(" ");
                lastNameTextField.setText(" ");
            } catch (SQLException e) {
                e.printStackTrace();
                resultLabel.setText("Add Procedure Failed");
                resultLabel.setTextFill(Color.RED);
            }
        }

    }
    @FXML

    public void signOut () throws IOException {
        StartProgram.signOut();
    }
    @FXML

    public void goBack() throws Exception{
        StartProgram.setRoot("WelcomePage");
    }

}
