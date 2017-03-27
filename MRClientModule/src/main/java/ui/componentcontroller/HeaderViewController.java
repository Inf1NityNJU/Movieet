package ui.componentcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import ui.viewcontroller.MainViewController;

/**
 * Created by Sorumi on 17/3/3.
 */
public class HeaderViewController {


    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    private MainViewController mainViewController;


    @FXML
    public void initialize() {
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @FXML
    public void clickSearchButton() {
        String keyword = searchField.getText();
    }

}
