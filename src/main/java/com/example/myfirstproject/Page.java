package com.example.myfirstproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Page implements Initializable {
    @FXML
    private TableView<Employee> table;
    @FXML
    TextField empId;
    @FXML
    private TextField searchEmail;

    @FXML
    private TextField searchFirstName;

    @FXML
    private TextField searchJob;

    @FXML
    private TextField searchLastName;

    @FXML
    private TextField searchManager;

    @FXML
    private TableColumn<Employee, Integer> employeeNumber;

    @FXML
    private TableColumn<Employee, String> firstName;

    @FXML
    private TableColumn<Employee, String> lastName;

    @FXML
    private TableColumn<Employee, String> jobTitle;

    @FXML
    private TableColumn<Employee, String> email;

    @FXML
    private TableColumn<Employee,Integer> reportsTo;


    public void logOut () throws IOException {
        StartProgram.setRoot("Login");
    }



    ObservableList<Employee> employeeSearchObservableList= FXCollections.observableArrayList(

    );
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        String query="select employeeNumber,firstName,lastName,jobTitle,email,reportsTo from employees;";
        try{
            ResultSet emp=DataBase.getResultSet(query);
            while (emp.next()) {
                Integer queryEmployeeNumber=emp.getInt("employeeNumber");
                String queryFirstName=emp.getString("firstName");
                String queryLastName=emp.getString("lastName");
                String queryJobTitle=emp.getString("jobTitle");
                String queryEmail=emp.getString("email");
                Integer queryReportsTo=emp.getInt("reportsTo");

                employeeSearchObservableList.add(new Employee(queryEmployeeNumber,
                                                              queryFirstName,
                                                              queryLastName,
                                                              queryJobTitle,
                                                              queryEmail,queryReportsTo));
            }

            employeeNumber.setCellValueFactory(new PropertyValueFactory<>("employeeNumber"));
            firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            jobTitle.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            reportsTo.setCellValueFactory(new PropertyValueFactory<>("reportsTo"));


            table.setItems(employeeSearchObservableList);

        }catch (Exception e){
            e.getCause();
            e.printStackTrace();
        }
        table.skinProperty().addListener(((obs, oldSkin, newSkin) -> {
            final TableHeaderRow header = (TableHeaderRow) table.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((o, oldVal, newVal) -> header.setReordering(false));
        }
        ));

        //Searching





        FilteredList<Employee> filteredData = new FilteredList<>(employeeSearchObservableList, b ->true);

        //Filter for ID search
        empId.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(Employee -> {
            if (newValue.isEmpty() || newValue.isBlank()) {
                return true;
            }
            String searchKeyword = newValue.toLowerCase();

            //filtering out by each element of Employee class
            return Employee.getEmployeeNumber().toString().contains(searchKeyword); //Found a match is first name

        }));

        searchFirstName.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(Employee -> {
            if (newValue.isEmpty() || newValue.isBlank()) {
                return true;
            }
            String searchKeyword = newValue.toLowerCase();

            //filtering out by each element of Employee class
            return Employee.getFirstName().toLowerCase().contains(searchKeyword);




        }));

        searchLastName.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(Employee -> {
            if (newValue.isEmpty() || newValue.isBlank()) {
                return true;
            }
            String searchKeyword = newValue.toLowerCase();

            //filtering out by each element of Employee class
            return Employee.getLastName().toLowerCase().contains(searchKeyword);




        }));

        searchJob.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(Employee -> {
            if (newValue.isEmpty() || newValue.isBlank()) {
                return true;
            }
            String searchKeyword = newValue.toLowerCase();

            //filtering out by each element of Employee class
            return Employee.getJobTitle().toLowerCase().contains(searchKeyword);




        }));

        searchEmail.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(Employee -> {
            if (newValue.isEmpty() || newValue.isBlank()) {
                return true;
            }
            String searchKeyword = newValue.toLowerCase();

            //filtering out by each element of Employee class
            return Employee.getEmail().toLowerCase().contains(searchKeyword);




        }));

        searchManager.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(Employee -> {
            if (newValue.isEmpty() || newValue.isBlank()) {
                return true;
            }
            String searchKeyword = newValue.toLowerCase();

            //filtering out by each element of Employee class
            return Employee.getReportsTo().toString().contains(searchKeyword);




        }));


        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        //binding sorted data with tvEmployee
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        //Applying filter
        table.setItems(sortedData);

    }


    private static Employee selectedEmployee;
    @FXML
    TextArea infoTextArea;

    @FXML
    private void displayInformation (){
        selectedEmployee = table.getSelectionModel().getSelectedItem();
        if (selectedEmployee!=null)
        infoTextArea.setText(selectedEmployee + "\n");
    }

    public  Employee getSelectedEmployee(){
        return selectedEmployee;
    }

    public void profilePage()  {
        selectedEmployee = table.getSelectionModel().getSelectedItem();
       try { if(selectedEmployee == null){
           Alerts.warningInfoAlert("No Employee is selected!",null,
                   "To view the profile page of the employee, you have to select them from the table!");
        }else {
            StartProgram.newPage("Profile","Profile Page");
        }
    }
        catch (IOException e){
        e.printStackTrace();
    }

    }

    //add  Employee
    public void addEmployeePage () throws IOException {
        Stage stage=StartProgram.newPage("AddEmployee","Add New Employee");
        stage.setResizable(false);
    }


}
