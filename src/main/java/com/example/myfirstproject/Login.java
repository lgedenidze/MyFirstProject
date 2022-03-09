package com.example.myfirstproject;

import com.mysql.cj.log.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;


public class Login implements Initializable {
    @FXML
    ImageView logo;
    @FXML
    TextField userName;
    @FXML
    PasswordField password;
    @FXML
    Label resultLabel;



     public void login_check (){
         String userN=userName.getText();
         String pass=password.getText();
         DataBase connectNow = new DataBase();
         Connection connectDBS = connectNow.getConnection();
         String verifyLoginQuery = "select count(1) from users where username='"
                 + userN +"' and password = SHA2(CONCAT('"+
                 pass+"','250'),256)";
         try {
             Statement statement = connectDBS.createStatement();
             ResultSet resultSet = statement.executeQuery(verifyLoginQuery);

             while (resultSet.next()){
                 if (resultSet.getInt(1) == 1){
                     StartProgram.setRoot("WelcomePage");
                 }else {
                     resultLabel.setText("არასწორი username ან password ახლიდან სცადე");
                 }
             }

             //

         }   catch (Exception e){
             e.printStackTrace();
             e.getCause();
         }




     }
    @FXML
    public void signIn() {
        if (userName.getText() == null || userName.getText().trim().isEmpty()||
        password.getText() == null || password.getText().trim().isEmpty())
            resultLabel.setText("არასწორი username ან password ახლიდან სცადე");
        else {
            login_check();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image logos =new Image("C:\\Users\\gedena\\IdeaProjects\\FirstProject\\src\\main\\resources\\com\\example\\myfirstproject\\Pictures\\logo-light.png");
        logo.setImage(logos);
     }
}
