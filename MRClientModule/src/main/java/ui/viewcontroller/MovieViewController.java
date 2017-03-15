package ui.viewcontroller;

import bl.MovieBLFactory;
import bl.MovieBLServiceImpl;
import component.meterbar.MeterBar;
import component.rangeLineChart.RangeLineChart;
import component.ratestarpane.RateStarPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import vo.MovieVO;
import vo.ReviewCountVO;
import vo.ScoreDistributionVO;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 当前电影
     */
    private MovieVO movieVO;

    private ScoreDistributionVO scoreDistributionVO;

    /**
     * MovieBL
     */
    private MovieBLServiceImpl movieBLService;

    public void setMovie(String movieId) {
        movieBLService = MovieBLFactory.getMovieBLService();

        //Basic
        movieVO = this.movieBLService.findMovieById(movieId);
        movieIdLabel.setText(movieId);
        movieNameLabel.setText(this.movieVO.getName());
        averageScoreLabel.setText(String.format("%.2f", this.movieVO.getAverageScore()));
        varianceLabel.setText(String.format("%.2f", this.movieVO.getVariance()));
        amountLabel.setText(this.movieVO.getAmountOfReview() + "");
        starPane.setScore((int) this.movieVO.getAverageScore());

        //MeterBars
        MeterBar meterBars[] = new MeterBar[]{oneMeterBar, twoMeterBar, threeMeterBar, fourMeterBar, fiveMeterBar};
        this.scoreDistributionVO = this.movieBLService.findScoreDistributionByMovieId(movieId);
        for (int i = 0; i < meterBars.length; i++) {
            meterBars[i].setAllNum(scoreDistributionVO.getTotalAmount());
            meterBars[i].setNum(scoreDistributionVO.getReviewAmounts()[i]);
        }

        //RangeLineChart
        RangeLineChart rangeLineChart = new RangeLineChart();
        rangeLineChart.setPrefSize(chartVBox.getPrefWidth() - 200, chartVBox.getPrefHeight());
        rangeLineChart.setLayoutX(0);
        rangeLineChart.setLayoutY(0);
        rangeLineChart.init();

        chartVBox.getChildren().add(rangeLineChart);

        yearChartClicked();
    }

    @FXML
    public void yearChartClicked() {
        RangeLineChart rangeLineChart = (RangeLineChart) chartVBox.getChildren().get(0);

        ReviewCountVO[] reviewCountVO = this.movieBLService.findDayCountByMovieId(movieVO.getId(),
                movieVO.getFirstReviewDate(), movieVO.getLastReviewDate());
        List<String> strings = new ArrayList<String>();
        for (String str : reviewCountVO[0].getKeys()) {
            strings.add(str);
        }
        rangeLineChart.setKeys(strings);

        List<Integer> numbers = new ArrayList<Integer>();
        for (int i : reviewCountVO[0].getReviewAmounts()) {
            numbers.add(i);
        }
        rangeLineChart.addData(numbers, movieVO.getName());

        rangeLineChart.reloadData();
    }

    @FXML
    public void monthChartClicked() {
        RangeLineChart rangeLineChart = (RangeLineChart) chartVBox.getChildren().get(0);

    }

    @FXML
    public void dayChartClicked() {
        RangeLineChart rangeLineChart = (RangeLineChart) chartVBox.getChildren().get(0);

    }

}
