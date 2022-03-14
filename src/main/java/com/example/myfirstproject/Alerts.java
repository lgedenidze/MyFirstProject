package com.example.myfirstproject;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.util.Optional;

public class Alerts {

    public static void throwInfoAlert(String title,String headerText, String contextText) {
        Alert alert =new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public static void warningInfoAlert(String title,String headerText, String contextText) {
        Alert alert =new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public static void errorInfoAlert(String title,String headerText, String contextText) {
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public static Alert confirmAlert(String title,String headerText, String contextText) {
        Alert alert =new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
        return  alert;
        /*This Is how to use alert
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
        } else {
            // ... user chose CANCEL or closed the dialog
        }*/
    }

    public static String choiceDialogAlertStrings(ObservableList<String> observableList,
                                                  String title,
                                                  String headerText,
                                                  String contextText){

        try{ChoiceDialog<String> alert=new ChoiceDialog<>(null,observableList);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.initStyle(StageStyle.UTILITY);
        alert.initModality(Modality.APPLICATION_MODAL);
        Optional<String> result = alert.showAndWait();

        return result.isEmpty()? null : result.get();
       }catch (Exception e){
        e.printStackTrace();
        return null;
    }

    }

    /**
     *
     * @param observableList
     * @param title
     * @param headerText
     * @param contextText
     * @return Throws Alerts of Choice boxes
     *
     */
    public static Integer choiceDialogAlertInteger(ObservableList<Integer> observableList,
                                                  String title,
                                                  String headerText,
                                                  String contextText) {

        try {ChoiceDialog<Integer> alert=new ChoiceDialog<>(null,observableList);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.initStyle(StageStyle.UTILITY);
        Optional<Integer> result = alert.showAndWait();

        return result.isEmpty()? 0 : result.get();
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }



    }




}