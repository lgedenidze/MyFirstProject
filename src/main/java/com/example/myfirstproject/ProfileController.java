package com.example.myfirstproject;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private TableView<Customers> customersTable;

    @FXML
    private Label email;

    @FXML
    Label employeeNumber;

    @FXML
    private Label firstName;


    @FXML
    private Label lastName;

    @FXML
    private Label managerID;

    @FXML
    private TableColumn<Customers, String> cityColum;

    @FXML
    private TableColumn<Customers, String> companyNameColum;

    @FXML
    private TableColumn<Customers, String> contactFirstNameColum;

    @FXML
    private TableColumn<Customers, String> contactLastNameColum;

    @FXML
    private TableColumn<Customers, Integer> customerNumberColum;

    @FXML
    private TableColumn<Customers, Integer> phoneColum;

    @FXML
    private TableColumn<Customers, Integer> salesRepEmployeeNumberColum;

    @FXML
    private ChoiceBox<Integer> managerIdChoiceBox;

    @FXML
    private Button editManagerButton;

    @FXML
    private ChoiceBox<String> jobChoice;
    Page page=new Page();
    Employee employee=page.getSelectedEmployee();

    @FXML
    Button deleteEmployeeButton;


        ObservableList<Customers> customersObservableList= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            String query="SELECT salesRepEmployeeNumber,customerNumber,customerName,contactFirstName,contactLastName,city,phone FROM  customers where salesRepEmployeeNumber="+employee.getEmployeeNumber();
            String jobQuery = """
                    select
                    jobtitle
                    from jobs where jobTitle not in ('Sales Rep','President') order by 1 desc""";
            ObservableList<String> jobsObsList=FXCollections.observableArrayList();
            ResultSet emp=DataBase.getResultSet(query);
            while (emp.next()){
                customersObservableList.add(
                        new Customers(
                                emp.getInt("salesRepEmployeeNumber"),
                                emp.getInt("customerNumber"),
                                emp.getString("customerName"),
                                emp.getString("contactFirstName"),
                                emp.getString("contactLastName"),
                                emp.getString("city"),
                                emp.getString("phone")
                        )
                );}


            salesRepEmployeeNumberColum.setCellValueFactory(new PropertyValueFactory<>("salesRepEmployeeNumber"));
            customerNumberColum.setCellValueFactory(new PropertyValueFactory<>("customerNumber"));
            companyNameColum.setCellValueFactory(new PropertyValueFactory<>("companyName"));
            contactFirstNameColum.setCellValueFactory(new PropertyValueFactory<>("contactFirstName"));
            contactLastNameColum.setCellValueFactory(new PropertyValueFactory<>("contactLastName"));
            cityColum.setCellValueFactory(new PropertyValueFactory<>("city"));
            phoneColum.setCellValueFactory(new PropertyValueFactory<>("phone"));
            customersTable.setItems(customersObservableList);
            emp=DataBase.getResultSet(jobQuery);
            while (emp.next()){
                jobsObsList.add(emp.getString(1));
            }


            //SetFileds

            employeeNumber.setText(String.valueOf(employee.getEmployeeNumber()));
            firstName.setText(employee.getFirstName());
            lastName.setText(employee.getLastName());
            email.setText(employee.getEmail());
            jobChoice.setValue(employee.getJobTitle());
            managerID.setText(String.valueOf(employee.getReportsTo()));
            managerIdChoiceBox.setValue(employee.getReportsTo());

            if(employee.getJobTitle().equals("Sales Rep")) {
                managerIdChoiceBox.setVisible(true);
                editManagerButton.setVisible(true);
                String managerQuery = "select employeeNumber from employees where jobTitle like '%Manager%';";

                ObservableList<Integer> managerObsList = FXCollections.observableArrayList();
                emp = DataBase.getResultSet(managerQuery);
                while (emp.next()) {
                    managerObsList.add(emp.getInt(1));

                }

                managerIdChoiceBox.setItems(managerObsList);
            } else {
                jobsObsList.remove(2);
                managerIdChoiceBox.setVisible(false);
                managerID.setVisible(true);
            }

            jobChoice.setItems(jobsObsList);





        }catch (Exception e){
            e.printStackTrace();

        }

    }

    public void editManager() {
        try {

            int managerIdForUp=managerIdChoiceBox.getValue();
            String changeManagerQuery="update employees set reportsTo="+managerIdForUp+" where employeeNumber="+"'"+employee.getEmployeeNumber()+"'";
            String getOffice="select officeCode from employees where employeeNumber="+managerIdForUp;
            int officeCode=0;
            DataBase.updateSelect(changeManagerQuery);
            ResultSet rs=DataBase.getResultSet(getOffice);
            while (rs.next()){
                officeCode=rs.getInt(1);
            }
            String changeOffice="update employees set officeCode="+officeCode+" where " +
                    "employeeNumber="+employee.getEmployeeNumber();
            DataBase.updateSelect(changeOffice);
            Alerts.throwInfoAlert("Successful",null,"Manager Update Completed");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
@FXML
    public void deleteEmp(){
        try {
            ObservableList<Integer> employeesLists = FXCollections.observableArrayList();
            String checkEmployees = "select count(employeeNumber) from employees where reportsTo=" + employee.getEmployeeNumber();
            String forChoiceBoxManager = "select employeeNumber from employees where reportsTo=" + employee.getEmployeeNumber();
            String checkCustomers="Select count(*) from customers where salesRepEmployeeNumber="+employee.getEmployeeNumber();
            String forChoiceBoxCustomers="select employeeNumber from employees where reportsTo="+employee.getReportsTo()+" and employeeNumber<>"+employee.getEmployeeNumber();
            ResultSet rs = DataBase.getResultSet(checkCustomers);
            int resultCust = 0;
            while (rs.next()){
                resultCust=rs.getInt(1);
            }
            rs = DataBase.getResultSet(checkEmployees);
            int resultEmp = 0;
            while (rs.next()){
                resultEmp=rs.getInt(1);
            }
            if (resultCust>0){//IF DELETED PERSON GOT CUSTOMERS
                rs=DataBase.getResultSet(forChoiceBoxCustomers);
                while (rs.next()){
                    employeesLists.add(rs.getInt(1));
                }

                /*
                Choice box Alert
                */
                Integer choiceAlertResult=Alerts.choiceDialogAlertInteger(employeesLists,"TRANSFER CUSTOMERS","Choice New Employee For Customers","WARNING,This Employee has customers,\n Choice new employee in his office for them");

                if (choiceAlertResult!=0){
                    //now transfer this customers to other customers
                String updateCustomers="update customers set salesRepEmployeeNumber="+choiceAlertResult+" where salesRepEmployeeNumber= " +employee.getEmployeeNumber();
                DataBase.updateSelect(updateCustomers);
                Alerts.throwInfoAlert("Successful",null,"Employee Update For Customers Completed");
                delete(employee.getEmployeeNumber());
                }

            }//IF DELETED PERSON IS MANAGER
            else if (resultEmp>0){
                rs=DataBase.getResultSet(forChoiceBoxManager);
                while (rs.next()) {
                    employeesLists.add(rs.getInt(1));
                }
                Integer newManagerChoice=Alerts.choiceDialogAlertInteger(employeesLists,"Choice New Manager",
                                  "WARNING,This employee - "+employee.getEmployeeNumber()+" is manager," +
                                " \n Choice new manager from them",employee.getEmployeeNumber()+"'s Employees :");
                String forUpdateManager="update employees set reportsTo="+newManagerChoice+" where reportsTo="+employee.getEmployeeNumber();
                String updateJob="update employees set jobTitle="+"'"+employee.getJobTitle()+"'"+" where employeeNumber="+newManagerChoice;

                DataBase.updateSelect(forUpdateManager);
                DataBase.updateSelect(updateJob);

                Alerts.throwInfoAlert("Successful",null,"Employee Update For Customers Completed");

                if (newManagerChoice!=0)

                delete(employee.getEmployeeNumber());
                Stage stage = (Stage) deleteEmployeeButton.getScene().getWindow();
                stage.close();
            }else{ delete(employee.getEmployeeNumber()
            );
                Stage stage = (Stage) deleteEmployeeButton.getScene().getWindow();
                stage.close();
            }


        }catch (Exception e){
            e.printStackTrace();
        }


}
    private static void delete(int EmpId){
       try {

           String DelQuery = "delete from employees where employeeNumber=" + EmpId;
           DataBase.updateSelect(DelQuery);
           Alerts.throwInfoAlert("Successful",null,"Delete Completed successfully");
           StartProgram.setRoot("WelcomePage");
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public void editJob() throws SQLException {
        if (jobChoice.getValue().equals(employee.getJobTitle())){
            Alerts.warningInfoAlert("Change job",null,"Please select new job if you \n want to change employee job");
        }else {
            String updateJobQuery = "update employees set jobTitle=" + "'" + jobChoice.getValue() + "'" +
                    " " + " where employeenumber=" + employee.getEmployeeNumber();
            DataBase.updateSelect(updateJobQuery);
            Alerts.throwInfoAlert("Successful",null,"Employee Job Changed to "+jobChoice.getValue());
        }

    }

}
