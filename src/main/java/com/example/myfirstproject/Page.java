package com.example.myfirstproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
        DataBase db=new DataBase();
        Connection con=db.getConnections();
        String query="select employeeNumber,firstName,lastName,jobTitle,email,reportsTo from employees;";
        try{
            Statement stm=con.createStatement();
            ResultSet emp=stm.executeQuery(query);
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
            reportsTo.setCellValueFactory(new PropertyValueFactory<Employee,Integer>("reportsTo"));


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


    public static Employee selectedEmployee;
    @FXML
    TextArea infoTextArea;

    @FXML
    private void displayInformation (ActionEvent event){
        selectedEmployee = table.getSelectionModel().getSelectedItem();
        infoTextArea.setText(selectedEmployee.toString() + "\n");
    }
    public  Employee getSelectedEmployee(){
        return selectedEmployee;
    }

    public void profilePage(ActionEvent event) throws IOException {
//        int employeeNum = table.getSelectionModel().getSelectedItem().getEmployeeNumber();
//        String fName =table.getSelectionModel().getSelectedItem().getFirstName();
//        String lName =table.getSelectionModel().getSelectedItem().getLastName();
//        String email =table.getSelectionModel().getSelectedItem().getEmail();
//        String jTitle =table.getSelectionModel().getSelectedItem().getJobTitle();
       // int reportsTo =table.getSelectionModel().getSelectedItem().getReportsTo();
//        int reportsTo =table.getSelectionModel().getSelectedItem().getReportsTo();
//        ProfileController profileController=getClass().getCo
//       // profileController.setFields(employeeNum,fName,lName,jTitle,email,reportsTo);
//        profileController.employeeNumbers=table.getSelectionModel().getSelectedItem().getEmployeeNumber();
//        StartProgram.setRoot("Profile");
//        profileController.employee=new Employee(employeeNum,fName,lName,jTitle,email,reportsTo);
//        profileController.employeeNumber.setText(String.valueOf(employeeNum));

//        profileController.employeeNumbers=table.getSelectionModel().getSelectedItem().getEmployeeNumber();

//
//        FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("Profile.fxml"));
//        Parent root=fxmlLoader.load();
//        ProfileController controller=fxmlLoader.getController();
//        controller.setFields(employeeNum,fName,lName,jTitle,email,reportsTo);
//        controller.employee=new Employee(employeeNum,fName,lName,jTitle,email,reportsTo);
//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        Scene  scene = new Scene(root);
//        stage.setScene(scene);
       try { if(selectedEmployee == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Employee is selected!");
            alert.setHeaderText("Select the employee!");
            alert.setContentText("To view the profile page of the employee, you have to select them from the table!");

            alert.showAndWait();
        }else {
            StartProgram.setRoot("Profile");
        }
    }
        catch (IOException e){
        e.printStackTrace();
    }
    }
    public void addEmployeePage () throws IOException {
        StartProgram.setRoot("AddEmployee");
    }
}
