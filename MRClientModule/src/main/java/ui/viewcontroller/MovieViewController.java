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
    private VBox chartPane;

    @FXML
    private LineChart<String, Integer> reviewTrendChart ;

    @FXML
    private CategoryAxis reviewTrendChartXAxis;

    @FXML
    private NumberAxis reviewTrendChartYAxis;

    @FXML
    private Button chartButton;

    @FXML
    private BarChart<Integer, Integer> reviewDistributionChart;

    public void setMovie(String movieId) {

        setReviewTrendChart();

    }

    public void clickChartButton() {
        reviewTrendChart.getData().remove(0);
    }

    private void setReviewTrendChart() {
//        final IntegerAxis xAxis = new IntegerAxis();
//        final IntegerAxis yAxis = new IntegerAxis();
        reviewTrendChartXAxis.setLabel("Month");
        //creating the chart
//        final LineChart<Integer,Integer> lineChart =
//                new LineChart<Integer,Integer>(xAxis,yAxis);

        reviewTrendChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("My portfolio");
        //populating the series with data
        series.getData().add(new XYChart.Data<String, Integer>("1", 23));
        series.getData().add(new XYChart.Data<String, Integer>("2", 14));
        series.getData().add(new XYChart.Data<String, Integer>("3", 15));
        series.getData().add(new XYChart.Data<String, Integer>("4", 24));
        series.getData().add(new XYChart.Data<String, Integer>("5", 34));
        series.getData().add(new XYChart.Data<String, Integer>("6", 36));
        series.getData().add(new XYChart.Data<String, Integer>("7", 22));
        series.getData().add(new XYChart.Data<String, Integer>("8", 45));
        series.getData().add(new XYChart.Data<String, Integer>("9", 43));
        series.getData().add(new XYChart.Data<String, Integer>("10", 17));
        series.getData().add(new XYChart.Data<String, Integer>("11", 29));
        series.getData().add(new XYChart.Data<String, Integer>("12", 25));

        reviewTrendChart.getData().add(series);

//        series.getData().add(new XYChart.Data<String,Integer>(String,integer))
    }
}
