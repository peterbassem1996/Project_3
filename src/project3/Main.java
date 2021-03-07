package project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.w3c.dom.events.Event;


import java.awt.*;

public class Main extends Application {

    private void closeProgram(Stage stage){
        stage.close();
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Project 3");
        primaryStage.setScene(new Scene(root, 600, 590));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
