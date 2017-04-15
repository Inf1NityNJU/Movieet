package ui.viewcontroller;

import bl.MovieBLFactory;
import blservice.MovieBLService;
import component.modeimageview.ModeImageView;
import component.rangelinechart.RangeLineChart;
import component.ratestarpane.RateStarPane;
import component.taglabel.TagLabel;
import component.topmenu.TopMenu;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import vo.MovieStatisticsVO;
import vo.MovieVO;
import vo.ScoreDateVO;
import vo.ScoreDistributionVO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sorumi on 17/4/10.
 */
public class MovieInfoViewController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox contentVBox;

    @FXML
    private ModeImageView posterImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private HBox tagHBox;

    @FXML
    private Label scoreLabel;

    @FXML
    private RateStarPane scoreStarPane;

    @FXML
    private Label reviewCountLabel;

    @FXML
    private GridPane meterBarPane;

    @FXML
    private Label releaseDateLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label languageLabel;

    @FXML
    private Label directorLabel;

    @FXML
    private Label writerLabel;

    @FXML
    private Label actorLabel;

    @FXML
    private TopMenu otherMenu;

    @FXML
    private StackPane contentPane;

    @FXML
    private Text storylineText;

    @FXML
    private VBox statisticVBox;

    @FXML
    private TagLabel allScoreTag;

    private TagLabel activeScoreTag;

    private VBox reviewListVBox;

    private RangeLineChart scoreLineChart;

    private MovieViewController movieViewController;

    private ReviewListViewController reviewListViewController;


    private MovieBLService movieBLService = MovieBLFactory.getMovieBLService();

    private MovieVO movieVO;
    private MovieStatisticsVO movieStatisticsVO;
    private LocalDate startDate;
    private LocalDate endDate;

    public void setMovieViewController(MovieViewController movieViewController) {
        this.movieViewController = movieViewController;
    }

    @FXML
    public void initialize() {

    }

    public void setMovie(MovieVO movieVO) {
        this.movieVO = movieVO;

        otherMenu.setItemIndex(0);

        nameLabel.setText(movieVO.name);
        for (String genre : movieVO.genre) {
            TagLabel tagLabel = new TagLabel();
            tagLabel.setText(genre);
            tagLabel.setBackgroundColor("EFF6F6");
            tagLabel.setTextColor("6ED3D8");
            tagHBox.getChildren().add(tagLabel);
        }

        releaseDateLabel.setText(movieVO.releaseDate);
        durationLabel.setText(movieVO.duration + " min");
        countryLabel.setText(movieVO.country);
        languageLabel.setText(movieVO.language);

        String directors = "";
        for (String director : movieVO.director) {
            directors += director + ", ";
        }
        directorLabel.setText(directors.substring(0, directors.length() - 2));

        String writers = "";
        for (String writer : movieVO.writers) {
            writers += writer + ", ";
        }
        writerLabel.setText(writers.substring(0, writers.length() - 2));

        String actors = "";
        for (String actor : movieVO.actors) {
            actors += actor + ", ";
        }
        actorLabel.setText(actors.substring(0, actors.length() - 2));

        storylineText.setText(movieVO.plot);

        // score


        // reviews
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ReviewListView.fxml"));
            reviewListVBox = loader.load();

            reviewListViewController = loader.getController();
            reviewListViewController.setMovieInfoViewController(this);
            reviewListViewController.showReviewsByMovieId(movieVO.id);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // statistic
        initChart();

        // poster
        Task<Integer> posterTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {

                Image poster = movieBLService.findPosterByMovieId(movieVO.id, 280);

                Platform.runLater(() -> {
                    if (poster != null) {
                        posterImageView.setImage(poster);
                        posterImageView.setMode(ModeImageView.ContentMode.Fill);
                    }
                });

                return 1;
            }
        };

        scoreLabel.setVisible(false);
        scoreStarPane.setVisible(false);
        reviewCountLabel.setVisible(false);
        activeScoreTag = allScoreTag;
        Task<Integer> scoreTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {

                movieStatisticsVO = movieBLService.findMovieStatisticsVOByMovieId(movieVO.id);
                startDate = LocalDate.parse(movieStatisticsVO.firstReviewDate);
                endDate = LocalDate.parse(movieStatisticsVO.lastReviewDate);

                ScoreDistributionVO scoreDistributionVO = movieBLService.findScoreDistributionByMovieId(movieVO.id);
                System.out.println(scoreDistributionVO);

                Platform.runLater(() -> {
                    scoreLabel.setText(String.format("%.1f", movieStatisticsVO.averageScore));
                    scoreStarPane.setScore(movieStatisticsVO.averageScore / 2);
                    reviewCountLabel.setText(movieStatisticsVO.amountOfReview + "");
                    scoreLabel.setVisible(true);
                    scoreStarPane.setVisible(true);
                    reviewCountLabel.setVisible(true);
                    // TODO score

                    clickAllTagLabel(null);

                    statisticVBox.getChildren().add(scoreLineChart);
                });

                return 1;
            }
        };

        new Thread(posterTask).start();
        new Thread(scoreTask).start();
    }


    /* private */

    private void initChart() {
        scoreLineChart = new RangeLineChart();
        scoreLineChart.setPrefSize(920, 500);
        scoreLineChart.init();
        scoreLineChart.setMinRange(0);
        scoreLineChart.setMaxRange(1);
        scoreLineChart.setColors(new String[]{"#6ED3D8"});
        scoreLineChart.setOnValueChanged(event -> {

            if (!allScoreTag.getActive()) return;

            int years = Math.toIntExact(ChronoUnit.YEARS.between(startDate, endDate));
            int months = Math.toIntExact(ChronoUnit.MONTHS.between(startDate, endDate));
            int days = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));

            double dis = scoreLineChart.getMaxRange() - scoreLineChart.getMinRange();

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

    }

    private void showStoryline() {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(storylineText);
    }

    private void showReviews() {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(reviewListVBox);
    }

    private void showStatistic() {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(statisticVBox);
    }

    private void chartSetYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String startYear = startDate.format(formatter);
        String endYear = endDate.format(formatter);

        ScoreDateVO scoreDateVO = this.movieBLService.findScoreDateByYear(movieVO.id, startYear, endYear);
        setScore(scoreDateVO);
        scoreLineChart.setStartAndEnd(0, 1);
        scoreLineChart.reloadData();
    }

    private void chartSetMonth() {
        int months = Math.toIntExact(ChronoUnit.MONTHS.between(startDate, endDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String startMonth = startDate.plusMonths((int) (months * scoreLineChart.getMinRange())).format(formatter);
        String endMonth = endDate.plusMonths(-(int) (months * (1 - scoreLineChart.getMaxRange()))).format(formatter);

        ScoreDateVO scoreDateVO = this.movieBLService.findScoreDateByMonth(movieVO.id, startMonth, endMonth);
        setScore(scoreDateVO);
        scoreLineChart.setStartAndEnd(scoreLineChart.getMinRange(), scoreLineChart.getMaxRange());
        scoreLineChart.reloadData();
    }


    private void chartSetDay() {
        int days = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDay = startDate.plusDays((int) (days * scoreLineChart.getMinRange())).format(formatter);
        String endDay = endDate.plusDays(-(int) (days * (1 - scoreLineChart.getMaxRange()))).format(formatter);

        ScoreDateVO scoreDateVO = this.movieBLService.findScoreDateByDay(movieVO.id, startDay, endDay);
        setScore(scoreDateVO);
        scoreLineChart.setStartAndEnd(scoreLineChart.getMinRange(), scoreLineChart.getMaxRange());
        scoreLineChart.reloadData();
    }

    private void charSetDayInLastMonth(int lastMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDay = endDate.minusMonths(lastMonth).format(formatter);
        String endDay = endDate.format(formatter);

        ScoreDateVO scoreDateVO = this.movieBLService.findScoreDateByDay(movieVO.id, startDay, endDay);
        setScore(scoreDateVO);
        scoreLineChart.setStartAndEnd(0, 1);
        scoreLineChart.setMinRange(0);
        scoreLineChart.setMinRange(1);
        scoreLineChart.reloadData();
    }

    private void chartSetAllMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String startMonth = startDate.format(formatter);
        String endMonth = endDate.format(formatter);

        ScoreDateVO scoreDateVO = this.movieBLService.findScoreDateByMonth(movieVO.id, startMonth, endMonth);
        setScore(scoreDateVO);
        scoreLineChart.setStartAndEnd(0, 1);
        scoreLineChart.setMinRange(0);
        scoreLineChart.setMinRange(1);
        scoreLineChart.reloadData();
    }

    private void setScore(ScoreDateVO scoreDateVO) {
        scoreLineChart.setKeys(scoreDateVO.dates);
        scoreLineChart.addData(scoreDateVO.scores, "score");
    }

    @FXML
    private void clickMenuItem() {
        int index = otherMenu.getItemIndex();
        switch (index) {
            case 0:
                showStoryline();
                break;
            case 1:
                showReviews();
                break;
            case 2:
                showStatistic();
                break;
        }
    }

    @FXML
    private void clickAllTagLabel(Event event) {
        activeScoreTag.setActive(false);
        allScoreTag.setActive(true);
        activeScoreTag = allScoreTag;
        chartSetYear();
        scoreLineChart.setMinRange(0);
        scoreLineChart.setMinRange(1);
    }

    @FXML
    private void clickMonthTagLabel(Event event) {
        activeScoreTag.setActive(false);
        TagLabel tagLabel = (TagLabel) event.getSource();
        tagLabel.setActive(true);
        activeScoreTag = tagLabel;
        chartSetAllMonth();
    }

    @FXML
    private void clickLast3MonthTagLabel(Event event) {
        activeScoreTag.setActive(false);
        TagLabel tagLabel = (TagLabel) event.getSource();
        tagLabel.setActive(true);
        activeScoreTag = tagLabel;
        charSetDayInLastMonth(3);
    }

    @FXML
    private void clickLastMonthTagLabel(Event event) {
        activeScoreTag.setActive(false);
        TagLabel tagLabel = (TagLabel) event.getSource();
        tagLabel.setActive(true);
        activeScoreTag = tagLabel;
        charSetDayInLastMonth(1);
    }
}
