package main;

import component.meterbar.MeterBar;
import component.rangeLineChart.RangeLineChart;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Sorumi on 16/11/17.
 */
public class Controller {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private MeterBar meterBar;

    private RangeLineChart rangeLineChart;

    @FXML
    public void initialize() {
        rangeLineChart = new RangeLineChart();
        rangeLineChart.setPrefSize(1000, 600);
        rangeLineChart.setLayoutX(0);
        rangeLineChart.setLayoutY(100);
        rootPane.getChildren().add(rangeLineChart);
        rangeLineChart.init();
    }

    @FXML
    public void click() {
//        rangeLineChart.scale();
    }
}
