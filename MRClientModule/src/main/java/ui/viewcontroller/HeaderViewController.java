package ui.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * Created by Sorumi on 17/3/3.
 */
public class HeaderViewController {

    @FXML
    private Label movieButton;

    @FXML
    private Label userButton;

    private MainViewController mainViewController;

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @FXML
    public void clickMovieButton() {
        movieButton.setTextFill(Color.web("#6ED3D8"));
        userButton.setTextFill(Color.web("#CCCCCC"));
        mainViewController.showMovieView();

    }

    @FXML
    public void clickUserButton() {
        userButton.setTextFill(Color.web("#6ED3D8"));
        movieButton.setTextFill(Color.web("#CCCCCC"));
    }
}
