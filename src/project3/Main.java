package project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    private void closeProgram(Stage stage){
        stage.close();
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        primaryStage.setTitle("Project 3");
        primaryStage.setScene(new Scene(root, 600, 590));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
