package ui.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * Created by Sorumi on 17/3/3.
 */
public class HeaderViewController {

    @FXML
    private Label movieButton;

    @FXML
    private Label userButton;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    private MainViewController mainViewController;

    private Type type;

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @FXML
    public void clickMovieButton() {
        movieButton.setTextFill(Color.web("#6ED3D8"));
        userButton.setTextFill(Color.web("#CCCCCC"));
        type = Type.Movie;


    }

    @FXML
    public void clickUserButton() {
        userButton.setTextFill(Color.web("#6ED3D8"));
        movieButton.setTextFill(Color.web("#CCCCCC"));
        type = Type.User;
    }


    @FXML
    public void clickSearchButton() {
        String id = searchField.getText();
        if (type == Type.Movie) {
            mainViewController.showMovieView(id);
        } else if (type == Type.User) {
            mainViewController.showUserView(id);
        } else {
            // TODO
        }
    }

    private enum Type {
        Movie, User
    }
}
