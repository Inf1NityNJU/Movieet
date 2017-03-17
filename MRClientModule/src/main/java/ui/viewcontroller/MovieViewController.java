package ui.viewcontroller;

import bl.MovieBLFactory;
import blservice.MovieBLService;

import component.meterbar.MeterBar;
import component.rangelinechart.RangeLineChart;
import component.ratestarpane.RateStarPane;
import component.spinner.Spinner;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import vo.MovieVO;
import vo.ReviewCountVO;
import vo.ScoreDistributionVO;
import vo.WordVO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;


/**
 * Created by Sorumi on 17/3/4.
 */
public class MovieViewController {

    @FXML
    private Pane infoPane;

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

    @FXML
    private HBox wordsHBox;

    @FXML
    private Pane spinnerPane;

    private RangeLineChart rangeLineChart;

    private MovieVO movieVO;

    private WordVO wordVO;

    private LocalDate startDate;

    private LocalDate endDate;

    private ScoreDistributionVO scoreDistributionVO;

    private MainViewController mainViewController;

    /**
     * MovieBL
     */
    private MovieBLService movieBLService;


    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setMovie(String movieId) {
        infoPane.setVisible(false);
        movieBLService = MovieBLFactory.getMovieBLService();

        spinnerPane.setPrefHeight(200);
        Spinner spinner = new Spinner();
        spinner.setCenterX(540);
        spinner.setCenterY(100);
        spinnerPane.getChildren().add(spinner);
        chartVBox.getChildren().add(spinnerPane);
        spinner.start();

        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                movieVO = movieBLService.findMovieById(movieId);
                wordVO = movieBLService.findWordsByMovieId(movieId);

                Platform.runLater(() -> {
                    initMovie();
                    infoPane.setVisible(true);
                    spinnerPane.setPrefHeight(0);
                    spinner.stop();
                });

                return 1;
            }
        };

        new Thread(task).start();
    }

    private void initMovie() {

        if (movieVO == null) {
            mainViewController.showAlertView("Valid Movie ID!");
            return;
        }

        startDate = LocalDate.parse(movieVO.getFirstReviewDate());
        endDate = LocalDate.parse(movieVO.getLastReviewDate());

        movieIdLabel.setText(movieVO.getId());
        movieNameLabel.setText(this.movieVO.getName());
        averageScoreLabel.setText(String.format("%.2f", this.movieVO.getAverageScore()));
        varianceLabel.setText(String.format("%.2f", this.movieVO.getVariance()));
        amountLabel.setText(this.movieVO.getAmountOfReview() + " reviews");
        starPane.setScore((int) this.movieVO.getAverageScore());

        //MeterBars
        MeterBar meterBars[] = new MeterBar[]{oneMeterBar, twoMeterBar, threeMeterBar, fourMeterBar, fiveMeterBar};
        this.scoreDistributionVO = this.movieBLService.findScoreDistributionByMovieId(movieVO.getId());
        for (int i = 0; i < meterBars.length; i++) {
            meterBars[i].setAllNum(scoreDistributionVO.getTotalAmount());
            meterBars[i].setNum(scoreDistributionVO.getReviewAmounts()[i]);
        }

        //Words
        List<String> words = wordVO.getTopWords();
        Label label = new Label("High frequency words: ");
        label.getStyleClass().add("for-label");
        wordsHBox.getChildren().add(label);
        for (String word : words) {
            Label wordLabel = new Label(word);
            wordLabel.getStyleClass().add("word-label");
            wordsHBox.getChildren().add(wordLabel);
        }

        //RangeLineChart
        rangeLineChart = new RangeLineChart();
        rangeLineChart.setPrefSize(1000, 600);
        rangeLineChart.init();
        rangeLineChart.setMinRange(0);
        rangeLineChart.setMaxRange(1);
        rangeLineChart.setOnValueChanged(event -> {

            int years = Math.toIntExact(ChronoUnit.YEARS.between(startDate, endDate));
            int months = Math.toIntExact(ChronoUnit.MONTHS.between(startDate, endDate));
            int days = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));

            double dis = rangeLineChart.getMaxRange() - rangeLineChart.getMinRange();

            if (dis == 1) {
                chartSetYear();
            } else if (dis < 3.0 / months) {
                LocalDate startDay = startDate.plusDays((int) (days * rangeLineChart.getMinRange()));
                LocalDate endDay = startDate.plusDays((int) (days * rangeLineChart.getMaxRange()));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                chartSetDay(startDay.format(formatter), endDay.format(formatter));
            } else if (dis < 3.0 / years) {
                LocalDate startMonth = startDate.plusMonths((int) (months * rangeLineChart.getMinRange()));
                LocalDate endMonth = startDate.plusMonths((int) (months * rangeLineChart.getMaxRange()));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                chartSetMonth(startMonth.format(formatter), endMonth.format(formatter));
            } else {
                chartSetYear();
            }
        });

        chartSetYear();

        chartVBox.getChildren().add(rangeLineChart);
    }


    private void chartSetYear() {
        ReviewCountVO[] reviewCountVO = this.movieBLService.findYearCountByMovieId(movieVO.getId());
        setReviewCount(reviewCountVO);
        rangeLineChart.setStartAndEnd(0, 1);
        rangeLineChart.reloadData();
    }

    private void chartSetMonth(String startMonth, String endMonth) {
        ReviewCountVO[] reviewCountVO = this.movieBLService.findMonthCountByMovieId(movieVO.getId(), startMonth, endMonth);
        setReviewCount(reviewCountVO);
        rangeLineChart.setStartAndEnd(rangeLineChart.getMinRange(), rangeLineChart.getMaxRange());
        rangeLineChart.reloadData();
    }


    private void chartSetDay(String startDay, String endDay) {
        ReviewCountVO[] reviewCountVO = this.movieBLService.findDayCountByMovieId(movieVO.getId(), startDay, endDay);
        setReviewCount(reviewCountVO);
        rangeLineChart.setStartAndEnd(rangeLineChart.getMinRange(), rangeLineChart.getMaxRange());
        rangeLineChart.reloadData();
    }

    private void setReviewCount(ReviewCountVO[] reviewCountVO) {
        rangeLineChart.setKeys(reviewCountVO[0].getKeys());

        for (int i = 0; i < 6; i++) {
            String name;
            if (i == 0) {
                name = "All";
            } else {
                name = i + "";
            }
            rangeLineChart.addData(reviewCountVO[i].getReviewAmounts(), name);
        }

    }

}
