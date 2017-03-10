package ui.viewcontroller;

import component.meterbar.MeterBar;
import component.ratestarpane.RateStarPane;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Created by Sorumi on 17/3/4.
 */
public class MovieViewController {

    @FXML
    private Label movieIdLabel;

    @FXML
    private Label movieNameLabel;

    @FXML
    private RateStarPane starPane;

    @FXML
    private Label averageScoreLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label varianceLabel;

    @FXML
    private MeterBar fiveMeterBar;

    @FXML
    private MeterBar fourMeterBar;

    @FXML
    private MeterBar threeMeterBar;

    @FXML
    private MeterBar twoMeterBar;

    @FXML
    private MeterBar oneMeterBar;

    @FXML
    private VBox chartVBox;

    public void setMovie(String movieId) {

        // init!!!

        // add chart to chartVBox

    }


}
