package ui.componentcontroller;

import javafx.fxml.FXML;
import ui.viewcontroller.MainViewController;

/**
 * Created by Sorumi on 17/3/27.
 */
public class NavBarViewController {

    private MainViewController mainViewController;


    @FXML
    public void initialize() {
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }
}
