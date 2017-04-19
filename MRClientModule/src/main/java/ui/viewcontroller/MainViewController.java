package ui.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.componentcontroller.HeaderViewController;
import ui.componentcontroller.NavBarViewController;

import java.io.IOException;
import java.util.Stack;

/**
 * Created by Sorumi on 17/3/3.
 */
public class MainViewController {

    @FXML
    private BorderPane rootPane;

    private Stage primaryStage;

    private HeaderViewController headerViewController;
    private NavBarViewController navBarViewController;

    private MovieViewController movieViewController = new MovieViewController(this);
    private UserSearchViewController userSearchViewController;
    private StatisticViewController statisticViewController;


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public MainViewController() {

        try {
            // init user view

            FXMLLoader userLoader = new FXMLLoader();
            userLoader.setLocation(getClass().getResource("/view/UserSearchView.fxml"));
            ScrollPane userNode = userLoader.load();

            userSearchViewController = userLoader.getController();
            userSearchViewController.setMainViewController(this);

            // init statistic view
            FXMLLoader statisticLoader = new FXMLLoader();
            statisticLoader.setLocation(getClass().getResource("/view/StatisticView.fxml"));
            ScrollPane statisticNode = statisticLoader.load();

            statisticViewController = statisticLoader.getController();
            statisticViewController.setMainViewController(this);

            statisticViewController.initCharts();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNetworkView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/NetworkView.fxml"));
            Pane node = loader.load();

            NetworkViewController networkViewController = loader.getController();
            networkViewController.setMainViewController(this);

            networkViewController.checkNetwork();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainView() {
        try {
            FXMLLoader headerLoader = new FXMLLoader();
            headerLoader.setLocation(getClass().getResource("/component/Header.fxml"));
            AnchorPane header = headerLoader.load();

            headerViewController = headerLoader.getController();
            headerViewController.setMainViewController(this);
            headerViewController.setPrimaryStage(primaryStage);

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

        //TODO
        navBarViewController.clickMovieButton();
    }

    public void setCenter(Node node) {
        rootPane.setCenter(node);
    }

    public Node getCenter() {
        return rootPane.getCenter();
    }

    public void showBackButton(boolean isShow) {
        headerViewController.showBackLabel(isShow);
    }

    public void back() {
        movieViewController.back();
    }

    public void showMovieView() {
        movieViewController.showMovieList();
    }

    public void showStatisticView() {
        statisticViewController.showStatisticView();

        showBackButton(false);
    }

    public void showUserView() {
        userSearchViewController.showUserSearchView();

        showBackButton(false);
    }


    /*Z
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
