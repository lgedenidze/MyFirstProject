package com.example.myfirstproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class StartProgram extends Application {
    private static Scene scene;

    @Override

    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("login.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Employee Management");
        stage.sizeToScene();
        Image icon=new Image("C:\\Users\\gedena\\IdeaProjects\\FirstProject\\src\\main\\resources\\com\\example\\myfirstproject\\Pictures\\icon.png");
        stage.getIcons().add(icon);
        // Image image = new Image("C:\\Users\\gedena\\Desktop\\jinx.png");
        //stage.getIcons().add(ima.ge);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource(fxml + ".fxml"));

        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }


    public static void signOut() throws IOException {
        setRoot("Login");
    }

    public static Stage newPage (String fxml,String Title) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource(fxml+".fxml"));
        root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle(Title);
        stage.setScene(new Scene(root, 600, 550));
        stage.show();
        Image icon=new Image("C:\\Users\\gedena\\IdeaProjects\\FirstProject\\src\\main\\resources\\com\\example\\myfirstproject\\Pictures\\icon.png");
        stage.getIcons().add(icon);
        // Hide this current window (if this is what you want)
        return stage;
    }

}