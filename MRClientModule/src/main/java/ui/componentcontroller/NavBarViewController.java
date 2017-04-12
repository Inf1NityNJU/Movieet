package ui.componentcontroller;

import component.navbutton.NavButton;
import javafx.fxml.FXML;
import ui.viewcontroller.MainViewController;

/**
 * Created by Sorumi on 17/3/27.
 */
public class NavBarViewController {

    @FXML
    private NavButton movieButton;

    @FXML
    private NavButton statisticButton;

    private MainViewController mainViewController;

    private char movieCode = '\uf008';
    private char statisticCode = '\uf080';

    @FXML
    public void initialize() {
        movieButton.setIcon(movieCode + "");
        statisticButton.setIcon(statisticCode + "");
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @FXML
    public void clickMovieButton() {
        movieButton.setActive(true);
        statisticButton.setActive(false);
        mainViewController.showMovieView();
    }

    @FXML
    public void clickStatisticButton() {
        movieButton.setActive(false);
        statisticButton.setActive(true);
        mainViewController.showStatisticView();
    }
}
