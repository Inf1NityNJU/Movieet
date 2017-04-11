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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.componentcontroller.MovieSearchPaneController;
import vo.MovieVO;
import vo.ReviewCountVO;
import vo.ScoreDistributionVO;
import vo.WordVO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;


/**
 * Created by Sorumi on 17/3/4.
 */
public class MovieViewController {

    private MainViewController mainViewController;

    public MovieViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void showMovieGenreList() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MovieListView.fxml"));
            ScrollPane node = loader.load();

            MovieListViewController movieListViewController = loader.getController();
            movieListViewController.setMovieViewController(this);

            mainViewController.setCenter(node);
            movieListViewController.showMovieGenreList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMovieInfo(String movieId) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MovieView.fxml"));
            ScrollPane node = loader.load();

            MovieInfoViewController movieInfoViewController = loader.getController();
            movieInfoViewController.setMovieViewController(this);
            movieInfoViewController.setMovie(movieId);

            mainViewController.setCenter(node);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




   /*
    public void setMovie(String movieId) {

        infoPane.setVisible(false);
        movieBLService = MovieBLFactory.getMovieBLService();

        spinnerPane.setVisible(true);
        spinnerPane.setManaged(true);
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
                    spinnerPane.setVisible(false);
                    spinnerPane.setManaged(false);
                    spinner.stop();
                });

                return 1;
            }
        };

        new Thread(task).start();

    }
 */
    /*
    private void initMovie() {

        if (movieVO == null) {
            mainViewController.showAlertView("Invalid Movie ID!");
            return;
        }

        startDate = LocalDate.parse(movieVO.getFirstReviewDate());
        endDate = LocalDate.parse(movieVO.getLastReviewDate());

        movieIdLabel.setText(movieVO.getId());
        movieNameLabel.setText(this.movieVO.getName());
        averageScoreLabel.setText(String.format("%.2f", this.movieVO.getAverageScore()));
        varianceLabel.setText(String.format("%.2f", this.movieVO.getVariance()));
        amountLabel.setText(this.movieVO.getAmountOfReview() + " review" + (this.movieVO.getAmountOfReview() > 1 ? "s" : ""));
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
        Label chartLabel = new Label("Reviews with time ");
        chartLabel.getStyleClass().add("for-label");
        chartVBox.getChildren().add(chartLabel);

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
                chartSetDay();
            } else if (dis < 3.0 / years) {
                chartSetMonth();
            } else {
                chartSetYear();
            }
        });

        chartSetYear();

        chartVBox.getChildren().add(rangeLineChart);
    }


    private void chartSetYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String startYear = startDate.format(formatter);
        String endYear = endDate.format(formatter);

        ReviewCountVO[] reviewCountVO = this.movieBLService.findYearCountByMovieId(movieVO.getId(), startYear, endYear);
        setReviewCount(reviewCountVO);
        rangeLineChart.setStartAndEnd(0, 1);
        rangeLineChart.reloadData();
    }

    private void chartSetMonth() {
        int months = Math.toIntExact(ChronoUnit.MONTHS.between(startDate, endDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String startMonth = startDate.plusMonths((int) (months * rangeLineChart.getMinRange())).format(formatter);
        String endMonth = endDate.plusMonths(-(int) (months * (1 - rangeLineChart.getMaxRange()))).format(formatter);

        ReviewCountVO[] reviewCountVO = this.movieBLService.findMonthCountByMovieId(movieVO.getId(), startMonth, endMonth);
        setReviewCount(reviewCountVO);
        rangeLineChart.setStartAndEnd(rangeLineChart.getMinRange(), rangeLineChart.getMaxRange());
        rangeLineChart.reloadData();
    }


    private void chartSetDay() {
        int days = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDay = startDate.plusDays((int) (days * rangeLineChart.getMinRange())).format(formatter);
        String endDay = endDate.plusDays(-(int) (days * (1 - rangeLineChart.getMaxRange()))).format(formatter);

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
            } else if (i == 1) {
                name = i + " star";
            } else {
                name = i + " stars";
            }
            rangeLineChart.addData(reviewCountVO[i].getReviewAmounts(), name);
        }

    }
    */

}
