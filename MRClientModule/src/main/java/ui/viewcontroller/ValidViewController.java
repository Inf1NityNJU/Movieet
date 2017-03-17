package ui.viewcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by Sorumi on 17/3/17.
 */
public class ValidViewController {

    @FXML
    private Label alertLabel;


    public void setAlert(String alert) {
        alertLabel.setText(alert);
    }
}
