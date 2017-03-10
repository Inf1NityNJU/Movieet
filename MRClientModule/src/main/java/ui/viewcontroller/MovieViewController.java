package ui.viewcontroller;

import bl.MovieBLFactory;
import bl.MovieBLServiceImpl;
import blservice.MovieBLService;
import component.meterbar.MeterBar;
import component.rangeLineChart.RangeLineChart;
import component.ratestarpane.RateStarPane;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import vo.MovieVO;

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

    /**
     * MovieBL
     */
    private MovieBLServiceImpl movieBLService;

    public void setMovie(String movieId) {

        this.movieBLService = MovieBLFactory.getMovieBLService();
        this.movieVO = this.movieBLService.findMovieById(movieId);

        movieIdLabel.setText(movieId);
        movieNameLabel.setText(this.movieVO.getName());
        averageScoreLabel.setText(this.movieVO.getAverageScore() + "");
        amountLabel.setText(this.movieVO.getAmountOfReview() + "");
        varianceLabel.setText(this.movieVO.getVariance() + "");
        starPane.setScore((int)this.movieVO.getAverageScore());

        //TODO: 等 movievo 的 distributionvo 做好以后设置 meterbars
        //TODO: add chart to chartVBox
    }


}
