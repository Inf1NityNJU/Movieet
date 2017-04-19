package ui.componentcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ui.viewcontroller.MainViewController;

/**
 * Created by Sorumi on 17/3/3.
 */
public class HeaderViewController {

    @FXML
    private AnchorPane root;

    @FXML
    private Label backLabel;

    @FXML
    private Label exitLabel;

    private double xOffset;
    private double yOffset;

    private MainViewController mainViewController;
    private Stage primaryStage;

    private char angleLeftCode = '\uf104';
    private char closeCode = '\uf00d';

    @FXML
    public void initialize() {
        backLabel.setText(angleLeftCode + "");
        backLabel.setVisible(false);
        exitLabel.setText(closeCode + "");


        root.setOnMousePressed(event -> {
            xOffset = primaryStage.getX() - event.getScreenX();
            yOffset = primaryStage.getY() - event.getScreenY();

        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset);
            primaryStage.setY(event.getScreenY() + yOffset);
        });
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void showBackLabel(boolean isShow) {
        backLabel.setVisible(isShow);
    }

    @FXML
    private void clickBackButton() {
        mainViewController.back();
    }

    @FXML
    private void clickExitButton() {
        System.exit(0);
    }
}
