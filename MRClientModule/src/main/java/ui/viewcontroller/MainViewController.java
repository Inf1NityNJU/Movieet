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

            rootPane.setTop(header);

        } catch (IOException e) {
            e.printStackTrace();
        }

        showBlankView();
    }

    public void showBlankView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ValidView.fxml"));
            VBox root = loader.load();

            rootPane.setCenter(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAlertView(String alert) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ValidView.fxml"));
            VBox root = loader.load();

            ValidViewController validViewController = loader.getController();
            validViewController.setAlert(alert);
            rootPane.setCenter(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMovieView(String movieID) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MovieView.fxml"));
            VBox root = loader.load();

            MovieViewController movieViewController = loader.getController();
            movieViewController.setMainViewController(this);
            rootPane.setCenter(root);
            movieViewController.setMovie(movieID);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUserView(String userID) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UserView.fxml"));
            VBox root = loader.load();

            UserViewController userViewController = loader.getController();
            userViewController.setMainViewController(this);
            rootPane.setCenter(root);
            userViewController.setUser(userID);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
