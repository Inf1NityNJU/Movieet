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
    private Label backLabel;

    private MainViewController mainViewController;

    private char angleLeftCode = '\uf104';

    @FXML
    public void initialize() {
        backLabel.setText(angleLeftCode + "");
        backLabel.setVisible(false);
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void showBackLabel(boolean isShow) {
        backLabel.setVisible(isShow);
    }

    @FXML
    private void clickBackButton() {
        mainViewController.back();
    }

}
