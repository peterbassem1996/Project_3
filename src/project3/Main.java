package project3;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class that runs the JavaFX application
 * @author Maharshi Patel
 * @author Peter Dawoud
 */


public class Main extends Application {

    /**
     * A method to close the JavaFX window
     *
     * @param stage The current stage of the application
     */
    private void closeProgram(Stage stage){
        stage.close();
    }

    /**
     * A method to start a JavaFX application
     *
     * @param primaryStage The primary stage of the application
     *
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        primaryStage.setTitle("Project 3");
        primaryStage.setScene(new Scene(root, 600, 590));
        primaryStage.show();
    }

    /**
     * The main method to launch the application
     *
     * @param args The arguments provided by the user
     */
    public static void main(String[] args) {
        launch(args);
    }
}
