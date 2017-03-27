package ui.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import ui.componentcontroller.HeaderViewController;
import ui.componentcontroller.NavBarViewController;

import java.io.IOException;

/**
 * Created by Sorumi on 17/3/3.
 */
public class MainViewController {

    @FXML
    private BorderPane rootPane;

    private HeaderViewController headerViewController;
    private NavBarViewController navBarViewController;

    private MovieViewController movieViewController = new MovieViewController(this);

    public void showMainView() {
        try {
            FXMLLoader headerLoader = new FXMLLoader();
            headerLoader.setLocation(getClass().getResource("/component/Header.fxml"));
            AnchorPane header = headerLoader.load();

            headerViewController = headerLoader.getController();
            headerViewController.setMainViewController(this);

            FXMLLoader navbarLoader = new FXMLLoader();
            navbarLoader.setLocation(getClass().getResource("/component/NavBar.fxml"));
            AnchorPane navbar = navbarLoader.load();

            navBarViewController = navbarLoader.getController();
            navBarViewController.setMainViewController(this);



            FXMLLoader sectionLoader = new FXMLLoader();
            sectionLoader.setLocation(getClass().getResource("/component/Section.fxml"));
            ScrollPane section = sectionLoader.load();

            rootPane.setTop(header);
            rootPane.setLeft(navbar);
            rootPane.setCenter(section);

            rootPane.setTop(header);

        } catch (IOException e) {
            e.printStackTrace();
        }

        movieViewController.showMovieGenreList();
    }

    public void setCenter(Node node) {
        rootPane.setCenter(node);
    }

    /*
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

    */

    public void showMovieGenreView() {

    }

    /*
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
    */
}
