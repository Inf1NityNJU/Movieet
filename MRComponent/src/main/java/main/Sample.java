package main;/**
 * Created by Sorumi on 16/11/17.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Sample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            FXMLLoader rootLoader = new FXMLLoader();
            rootLoader.setLocation(Sample.class.getResource("Sample.fxml"));
            AnchorPane root = rootLoader.load();

            Controller controller = rootLoader.getController();
            controller.initialize();

            primaryStage.setTitle("Hello World");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
