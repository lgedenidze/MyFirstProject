package com.example.myfirstproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StartProgram extends Application {
    private static Scene scene;
    private static FXMLLoader fxmlLoader;
    @Override

    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("login.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Employee Management");
        stage.sizeToScene();

       // Image image = new Image("C:\\Users\\gedena\\Desktop\\jinx.png");
        //stage.getIcons().add(ima.ge);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(StartProgram.class.getResource(fxml + ".fxml"));

        return fxmlLoader.load();
    }

    public FXMLLoader fxmlLoader(){
        return fxmlLoader;
    }

    public static void main(String[] args) {
        launch();
    }


    public static void signOut() throws IOException {
        setRoot("Login");
    }
}