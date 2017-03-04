package ui.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by Sorumi on 17/3/3.
 */
public class MainViewController {

    @FXML
    private BorderPane rootPane;

    private HeaderViewController headerViewController;

    public void showMainView() {
        try {
            FXMLLoader headerLoader = new FXMLLoader();
            headerLoader.setLocation(getClass().getResource("/view/Header.fxml"));
            AnchorPane header = headerLoader.load();

            headerViewController = headerLoader.getController();
            headerViewController.setMainViewController(this);

//            FXMLLoader sectionLoader = new FXMLLoader();
//            sectionLoader.setLocation(getClass().getResource("/component/common/Section.fxml"));
//            ScrollPane section = sectionLoader.load();

            rootPane.setTop(header);
//            rootPane.setCenter(section);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMovieView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MovieView.fxml"));
            VBox root = loader.load();

            MovieViewController movieViewController = loader.getController();
            movieViewController.setMovie("");
            rootPane.setCenter(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
