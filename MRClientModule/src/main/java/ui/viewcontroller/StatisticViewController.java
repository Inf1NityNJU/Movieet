package ui.viewcontroller;

import component.intervalbarchart.IntervalBarChart;
import component.scatterchart.PointData;
import component.scatterchart.ScatterChart;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import util.MovieGenre;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sorumi on 17/4/12.
 */
public class StatisticViewController {

    @FXML
    private ScrollPane root;

    @FXML
    private VBox contentVBox;

    @FXML
    private VBox genreChartVBox;

    @FXML
    private VBox scoreChartVBox;

    private IntervalBarChart intervalBarChart;
    private ScatterChart scatterChart;

    private MainViewController mainViewController;

    public StatisticViewController() {

    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void initCharts() {
        initGenreBarChart();
        initScoreScatterChart();
    }

    public void showStatisticView() {
        mainViewController.setCenter(root);
    }

    /* private */

    private void initGenreBarChart() {
        intervalBarChart = new IntervalBarChart();

        intervalBarChart.setPrefSize(920, 500);
        intervalBarChart.setLayoutX(0);
        intervalBarChart.setLayoutY(50);
        genreChartVBox.getChildren().add(intervalBarChart);

        intervalBarChart.init();

        intervalBarChart.setSpaceRatio(0.2);
        intervalBarChart.setSingle(false);

        // test
        int count = MovieGenre.values().length;
        Random random = new Random();
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            keys.add(MovieGenre.values()[i].getGenreName());
        }
        intervalBarChart.setKeys(keys);

        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            nums.add(random.nextInt(100));
        }

        intervalBarChart.addData(nums);
        intervalBarChart.reloadData();
    }

    private void initScoreScatterChart() {
        scatterChart = new ScatterChart();
        scatterChart.setPrefSize(920, 500);
        scatterChart.setLayoutX(0);
        scatterChart.setLayoutY(50);
        scoreChartVBox.getChildren().add(scatterChart);

        scatterChart.init();

        scatterChart.setCircleWidth(6);

        // test
        int count = 120;
        Random random = new Random();
        List<PointData> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            PointData point = new PointData(random.nextInt(100), random.nextDouble() * 10);
            data.add(point);
        }

        scatterChart.setData(data);
        scatterChart.reloadData();
    }

}
