package ui.viewcontroller;

import component.meterbar.MeterBar;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;

/**
 * Created by Sorumi on 17/3/4.
 */
public class MovieViewController {

    @FXML
    private Label movieIdLabel;

    @FXML
    private Label movieNameLabel;

    @FXML
    private Label starPane;

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
    private LineChart<String, Integer> reviewTrendChart;

    @FXML
    private BarChart<Integer, Integer> reviewDistributionChart;

    public void setMovie(String movieId) {

    }
}
