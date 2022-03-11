package com.example.myfirstproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private ChoiceBox<Integer> officeChose;


    @FXML
    private Label resultLabel;

//start Program
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //for Choice filed
        ObservableList<Integer>officeChoseChooseObsList=FXCollections.observableArrayList();
        ObservableList<String>jobTitleChooseObsList=FXCollections.observableArrayList();
        String jobQuery= """
                select
                distinct jobTitle
                from jobs""";
        String officeQuery="select officeCode from offices;";

        try {

            ResultSet result=DataBase.getResultSet(jobQuery);
            while (result.next()){
                jobTitleChooseObsList.add(result.getString(1));
            }

            result=DataBase.getResultSet(officeQuery);
            while (result.next()){
                officeChoseChooseObsList.add(result.getInt(1));
            }
            jobTitleChoose.setItems(jobTitleChooseObsList);
            officeChose.setItems(officeChoseChooseObsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //save button logic

    public void saveMethods() {
        String queryManager = "select employeeNumber from employees a where jobTitle LIKE '%Manager%' and officeCode=" + officeChose.getValue();
        String queryEmpId = "SELECT MAX(employeeNumber)+1 from employees;";
        Integer empid = null;
        Integer managerId = null;
        try {
            ResultSet rs = DataBase.getResultSet(queryEmpId);
            while (rs.next()) {
                empid = rs.getInt(1);
            }
            rs=DataBase.getResultSet(queryManager);
            while (rs.next()) {
                managerId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        Integer office = officeChose.getValue();
        String job = jobTitleChoose.getValue();
        if (firstName == null || lastName == null || email == null || managerId == null) resultLabel.setText("Check Fields,Something Is Empty");
        else {
            String insertNewEmp = "insert into employees values (" + empid + "," + "'" + lastName + "'" +
                    "," + "'" + firstName + "'" + "," + "'" + email + "'" + "," + office + "," + managerId + "," + "'" + job + "'" + ")";

            try {
                DataBase.updateSelect(insertNewEmp);
                Alerts.throwInfoAlert("Successful","Employee Added Successfully",
                                      "Employee Info : Employee number - " + empid + ", Employee Name - " + firstName + " " + lastName);
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
}
