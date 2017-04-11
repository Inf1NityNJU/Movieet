package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.viewcontroller.MainViewController;

/**
 * Created by Sorumi on 17/3/3.
 */
public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {

        FXMLLoader rootLoader = new FXMLLoader();
        rootLoader.setLocation(getClass().getResource("/view/Main.fxml"));
        Pane root = rootLoader.load();
        MainViewController mainViewController = rootLoader.getController();

        primaryStage.setTitle("Movie Review");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        mainViewController.showMainView();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
