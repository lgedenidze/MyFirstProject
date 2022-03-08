package com.example.myfirstproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
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
    private Label jobTitle;

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

    Page page=new Page();
    Employee employee=page.getSelectedEmployee();



        ObservableList<Customers> customersObservableList= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataBase dataBase=new DataBase();
        Connection con= dataBase.getConnections();
        try{
            String query="SELECT salesRepEmployeeNumber,customerNumber,customerName,contactFirstName,contactLastName,city,phone FROM  customers where salesRepEmployeeNumber="+employee.getEmployeeNumber();

            Statement stm=con.createStatement();
            ResultSet emp=stm.executeQuery(query);
            while (emp.next()){
                Integer salesRepEmployeeNumber =emp.getInt("salesRepEmployeeNumber");
                String customerNumber=emp.getString("customerNumber");
                String companyName=emp.getString("customerName");
                String contactFirstName=emp.getString("contactFirstName");
                String contactLastName=emp.getString("contactLastName");
                String city=emp.getString("city");
                String phone=emp.getString("phone");
                customersObservableList.add(
                        new Customers(
                                salesRepEmployeeNumber,Integer.parseInt(customerNumber),companyName,contactFirstName,contactLastName,city,phone
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

            //SetFileds

            employeeNumber.setText(String.valueOf(employee.getEmployeeNumber()));
            firstName.setText(employee.getFirstName());
            lastName.setText(employee.getLastName());
            email.setText(employee.getEmail());
            jobTitle.setText(employee.getJobTitle());
            managerID.setText(String.valueOf(employee.getReportsTo()));
            managerIdChoiceBox.setValue(employee.getReportsTo());

            if(employee.getJobTitle().equals("Sales Rep")&&employee.getJobTitle().equals("Sales Rep")) {
                managerIdChoiceBox.setVisible(true);
                editManagerButton.setVisible(true);
                String managerQuery = "select distinct reportsTo from employees;";
                ObservableList<Integer> managerObsList = FXCollections.observableArrayList();
                ResultSet rs = stm.executeQuery(managerQuery);
                while (rs.next()) {
                    managerObsList.add(rs.getInt(1));

                }
                managerIdChoiceBox.setItems(managerObsList);
            } else {
                managerIdChoiceBox.setVisible(false);
                managerID.setVisible(true);
            }





        }catch (Exception e){
            e.printStackTrace();

        }

    }

    public void editManager() {
        try {
            DataBase db=new DataBase();
            Connection cn=db.getConnections();
            Statement stm=cn.createStatement();
            int managerIdForUp=managerIdChoiceBox.getValue();
            String changeManagerQuery="update employees set reportsTo="+managerIdForUp+" where employeeNumber="+"'"+employee.getEmployeeNumber()+"'";
            String getOffice="select officeCode from employees where employeeNumber="+managerIdForUp;
            int officeCode=0;
            int upManager=stm.executeUpdate(changeManagerQuery);
            ResultSet rs=stm.executeQuery(getOffice);
            while (rs.next()){
                officeCode=rs.getInt(1);
            }
            String changeOffice="update employees set officeCode="+officeCode+" where " +
                    "employeeNumber="+employee.getEmployeeNumber();
            int upOffice=stm.executeUpdate(changeOffice);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Successful");
            alert.setContentText("Manager Update Completed");
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
@FXML
    public void deleteEmp(){
        try {
            DataBase db = new DataBase();
            Connection cn = db.getConnections();
            Statement stm = cn.createStatement();
            ObservableList<Integer> employeesLists = FXCollections.observableArrayList();
            String checkEmployees = "select count(employeeNumber) from employees where reportsTo=" + employee.getEmployeeNumber();
            String forChoiceBoxManager = "select employeeNumber from employees where reportsTo=" + employee.getEmployeeNumber();
            String checkCustomers="Select count(*) from customers where salesRepEmployeeNumber="+employee.getEmployeeNumber();
            String forChoiceBoxCustomers="select employeeNumber from employees where reportsTo="+employee.getReportsTo()+" and employeeNumber<>"+employee.getEmployeeNumber();
            ResultSet resultSetCust = stm.executeQuery(checkCustomers);
            int resultCust = 0;
            while (resultSetCust.next()){
                resultCust=resultSetCust.getInt(1);
            }
            ResultSet resultSetEmp = stm.executeQuery(checkEmployees);
            int resultEmp = 0;
            while (resultSetEmp.next()){
                resultEmp=resultSetEmp.getInt(1);
            }
            if (resultCust>0){
                resultSetCust=stm.executeQuery(forChoiceBoxCustomers);
                while (resultSetCust.next()){
                    employeesLists.add(resultSetCust.getInt(1));
                }
                ChoiceDialog<Integer> newEmpChoice=new ChoiceDialog<>(null,employeesLists);
                newEmpChoice.setTitle("Choice New Employee For Customers");
                newEmpChoice.setHeaderText("WARNING,This Employee has customers, choice new employee in his office for them");

                Optional<Integer> result = newEmpChoice.showAndWait();

                String updateCustomers="update customers set salesRepEmployeeNumber="+result.get()+" where salesRepEmployeeNumber= " +employee.getEmployeeNumber();
                int updateCus=stm.executeUpdate(updateCustomers);
                Alert updateInfo=new Alert(Alert.AlertType.INFORMATION);
                updateInfo.setHeaderText("Successful");
                updateInfo.setContentText("Employee Update For Customers Completed");
                if (result.get() !=0)
                delete(employee.getEmployeeNumber());

            }
            else if (resultEmp>0){
                ResultSet rs=stm.executeQuery(forChoiceBoxManager);
                while (rs.next()) {
                    employeesLists.add(rs.getInt(1));
                }
                ChoiceDialog<Integer> newManagerChoice=new ChoiceDialog<>(null,employeesLists);
                newManagerChoice.setTitle("Choice New Manager");
                newManagerChoice.setHeaderText("WARNING,This employee - "+employee.getEmployeeNumber()+" is manager,Choice new manager from them");
                newManagerChoice.setContentText(employee.getEmployeeNumber()+"'s Employees :");
                Optional<Integer> resultOfChoice = newManagerChoice.showAndWait();
                String forUpdateManager="update employees set reportsTo="+resultOfChoice.get()+" where reportsTo="+employee.getEmployeeNumber();
                String updateJob="update employees set jobTitle="+"'"+employee.getJobTitle()+"'"+" where employeeNumber="+resultOfChoice.get();
                int result=stm.executeUpdate(forUpdateManager);
                result=result+stm.executeUpdate(updateJob);
                System.out.println(result);
                Alert updateInfo=new Alert(Alert.AlertType.INFORMATION);
                updateInfo.setHeaderText("Successful");
                updateInfo.setContentText("Employee Update For Customers Completed");
                if (resultOfChoice.get()!=0)
                delete(employee.getEmployeeNumber());
            }else delete(employee.getEmployeeNumber());


        }catch (Exception e){
            e.printStackTrace();
        };


    }
    private static void delete(int EmpId){
       try {
           String DelQuery = "delete from employees where employeeNumber=" + EmpId;
           DataBase db = new DataBase();
           Connection cn = db.getConnections();
           Statement stm = cn.createStatement();

           int delCount=stm.executeUpdate(DelQuery);
           Alert updateInfo=new Alert(Alert.AlertType.INFORMATION);
           updateInfo.setHeaderText("Successful");
           updateInfo.setContentText("Delete Completed successfully");
           updateInfo.show();
           StartProgram.setRoot("WelcomePage");
       }catch (Exception e){
           e.printStackTrace();
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
