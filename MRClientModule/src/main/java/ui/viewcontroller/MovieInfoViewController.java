package ui.viewcontroller;

import bl.MovieBLFactory;
import blservice.MovieBLService;
import component.boxplotchart.BoxPlotChart;
import component.intervalbarchart.IntervalBarChart;
import component.modeimageview.ModeImageView;
import component.rangelinechart.RangeLineChart;
import component.ratestarpane.RateStarPane;
import component.spinner.Spinner;
import component.taglabel.TagLabel;
import component.topmenu.TopMenu;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import ui.componentcontroller.SimilarMovieCellController;
import util.MovieGenre;
import vo.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

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
    private TagLabel ratingLabel;

    @FXML
    private Label varianceLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private RateStarPane scoreStarPane;

    @FXML
    private Label reviewCountLabel;

    @FXML
    private TilePane wordsPane;

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
    private HBox sourceHBox;

    @FXML
    private TagLabel amazonButton;

    @FXML
    private TagLabel imdbButton;

    @FXML
    private HBox scoreDateHBox;

    @FXML
    private TagLabel allScoreTag;

    @FXML
    private HBox similarMovieHBox;

    private TagLabel activeScoreTag;

    private VBox reviewListVBox;

    private Pane chartSpinnerPane;
    private Pane similarSpinnerPane;

    private RangeLineChart scoreLineChart;
    private RangeLineChart reviewCountLineChart;
    private IntervalBarChart scoreDistributionBarChart;
    private BoxPlotChart boxPlotChart;

    private String source = "Amazon";

    private MovieVO movieVO;
    private WordVO wordVO;
    private MovieStatisticsVO movieStatisticsVO;
    private ScoreDistributionVO scoreDistributionVOAmazon;
    private ScoreDistributionVO scoreDistributionVOImdb;
    private BoxPlotVO boxPlotVOAmazon;
    private BoxPlotVO boxPlotVOImdb;
    private LocalDate startDate;
    private LocalDate endDate;
//    private EnumSet<MovieGenre> tags = EnumSet.of(MovieGenre.All);

    private MovieViewController movieViewController;

    private ReviewListViewController reviewListViewController;

    private MovieBLService movieBLService = MovieBLFactory.getMovieBLService();

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

//            MovieGenre movieGenre = MovieGenre.getMovieGenreByName(genre);
//            if (movieGenre != null) {
//                tags.add(movieGenre);
//            }
        }
//        tags.remove(MovieGenre.All);

        ratingLabel.setText(movieVO.rating + " / 10");
        releaseDateLabel.setText(movieVO.releaseDate);
        durationLabel.setText(movieVO.duration + " min");
        countryLabel.setText(movieVO.country);
        languageLabel.setText(movieVO.language);

        String directors = "";
        for (String director : movieVO.director) {
            directors += director + ", ";
        }
        directorLabel.setText(directors.length() > 2 ? directors.substring(0, directors.length() - 2) : "");

        String writers = "";
        for (String writer : movieVO.writers) {
            writers += writer + ", ";
        }
        writerLabel.setText(writers.length() > 2 ? writers.substring(0, writers.length() - 2) : "");

        String actors = "";
        for (String actor : movieVO.actors) {
            actors += actor + ", ";
        }
        actorLabel.setText(actors.length() > 2 ? actors.substring(0, actors.length() - 2) : "");

        storylineText.setText(movieVO.plot);

        scoreLabel.setText(movieVO.score + "");
        scoreStarPane.setScore(movieVO.score / 2);
        reviewCountLabel.setText(movieVO.reviewCount + "");

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

        // word
        Task<Integer> wordTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {

                // words
                wordVO = movieBLService.findWordsByMovieId(movieVO.id);

                Platform.runLater(() -> {
                    List<String> words = wordVO.getTopWords();
                    for (String word : words) {
                        Label wordLabel = new Label(word);
                        wordLabel.getStyleClass().add("word-label");
                        wordsPane.getChildren().add(wordLabel);
                    }
                });

                return 1;
            }
        };

        similarSpinnerPane = new Pane();
        similarSpinnerPane.setPrefSize(920, 200);
        similarSpinnerPane.setVisible(true);
        similarSpinnerPane.setManaged(true);
        Spinner similarSpinner = new Spinner();
        similarSpinner.setCenterX(460);
        similarSpinner.setCenterY(100);
        similarSpinnerPane.getChildren().add(similarSpinner);
        similarMovieHBox.getChildren().add(similarSpinnerPane);
        similarSpinner.start();

        // similar
        Task<Integer> similarTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {

                List<MovieVO> movieVOs = movieBLService.findSimilarMovies(movieVO);
                List<Image> images = new ArrayList<>();
                for (MovieVO similarMovie : movieVOs) {
                    Image image = movieBLService.findPosterByMovieId(similarMovie.id, 140);
                    images.add(image);
                }

                Platform.runLater(() -> {

                    for (MovieVO similarMovie : movieVOs) {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/component/SimilarMovieCell.fxml"));
                            VBox vBox = loader.load();
                            similarMovieHBox.getChildren().add(vBox);

                            SimilarMovieCellController similarMovieCellController = loader.getController();
                            similarMovieCellController.setMovie(similarMovie);
                            vBox.setCursor(Cursor.HAND);
                            vBox.setOnMouseClicked(event -> {
                                movieViewController.showMovieInfo(similarMovie);
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    similarMovieHBox.getChildren().remove(similarSpinnerPane);
                    similarSpinner.stop();

                });

                return 1;
            }
        };

        // score and all reviews and chart
        chartSpinnerPane = new Pane();
        chartSpinnerPane.setPrefSize(920, 200);
        chartSpinnerPane.setVisible(true);
        chartSpinnerPane.setManaged(true);
        Spinner chartSpinner = new Spinner();
        chartSpinner.setCenterX(460);
        chartSpinner.setCenterY(100);
        chartSpinnerPane.getChildren().add(chartSpinner);
        statisticVBox.getChildren().add(chartSpinnerPane);
        chartSpinner.start();

        scoreDateHBox.setVisible(false);
        sourceHBox.setVisible(false);
        activeScoreTag = allScoreTag;
        Task<Integer> scoreTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {

                movieStatisticsVO = movieBLService.findMovieStatisticsVOByMovieId(movieVO.id);

                startDate = LocalDate.parse(movieStatisticsVO.firstReviewDate);
                endDate = LocalDate.parse(movieStatisticsVO.lastReviewDate);

                scoreDistributionVOAmazon = movieBLService.findScoreDistributionByMovieIdFromAmazon(movieVO.id);
                scoreDistributionVOImdb = movieBLService.findScoreDistributionByMovieIdFromIMDB(movieVO.id);

//                System.out.println("分布: "+ scoreDistributionVOImdb);

                boxPlotVOAmazon = movieBLService.getBoxPlotVOFromAmazon(movieVO.id);
                boxPlotVOImdb = movieBLService.getBoxPlotVOFromImdb(movieVO.id);

//                System.out.println("Imdb箱型图: " + boxPlotVOImdb);

                Platform.runLater(() -> {
                    varianceLabel.setText(String.format("%.2f", movieStatisticsVO.variance));
                    reviewCountLabel.setText(movieVO.reviewCount + " Amazon: " + movieStatisticsVO.amountOfReviewFromAmazon + " IMDB: " + movieStatisticsVO.amountOfReviewFromImdb);
                    scoreDateHBox.setVisible(true);
                    sourceHBox.setVisible(true);
                    amazonButton.setActive(true);

                    // score line chart
                    Label averageScoreLabel = new Label("Average Score");
                    averageScoreLabel.getStyleClass().addAll("for-label");
                    clickAllTagLabel(null);


                    // review count line chart
                    Label reviewCountLabel = new Label("Review Count");
                    reviewCountLabel.getStyleClass().addAll("for-label");


                    // score distribution bar chart
                    Label scoreDistributionLabel = new Label("Score Distribution");
                    scoreDistributionLabel.getStyleClass().addAll("for-label");

                    // box plot chart
                    Label boxPlotLabel = new Label("Score Box Plot");
                    boxPlotLabel.getStyleClass().addAll("for-label");

                    refreshCharts();

                    statisticVBox.getChildren().remove(chartSpinnerPane);
                    chartSpinner.stop();
                    statisticVBox.getChildren().add(1, averageScoreLabel);
                    statisticVBox.getChildren().add(2, scoreLineChart);
                    statisticVBox.getChildren().addAll(reviewCountLabel, reviewCountLineChart, scoreDistributionLabel, scoreDistributionBarChart, boxPlotLabel, boxPlotChart);

                });

                return 1;
            }
        };

        new Thread(posterTask).start();
        new Thread(wordTask).start();
        new Thread(scoreTask).start();
        new Thread(similarTask).start();
    }


    /* private */
    private void initChart() {
        // score chart

        scoreLineChart = new RangeLineChart();
        scoreLineChart.setPrefSize(920, 500);
        scoreLineChart.init();
        scoreLineChart.setMinRange(0);
        scoreLineChart.setMaxRange(1);
        scoreLineChart.setColors(new String[]{"#6ED3D8"});
        scoreLineChart.setCircleRadius(3);
        scoreLineChart.setOnValueChanged(event -> {

            if (!allScoreTag.getActive()) return;

            int years = Math.toIntExact(ChronoUnit.YEARS.between(startDate, endDate));
            int months = Math.toIntExact(ChronoUnit.MONTHS.between(startDate, endDate));
            int days = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));

            double dis = scoreLineChart.getMaxRange() - scoreLineChart.getMinRange();

            if (dis == 1) {
                scoreLineChartSetYear();
            } else if (dis < 3.0 / months) {
                scoreLineChartSetDay();
            } else if (dis < 3.0 / years) {
                scoreLineChartSetMonth();
            } else {
                scoreLineChartSetYear();
            }
        });

        // reviewCountLineChart
        reviewCountLineChart = new RangeLineChart();
        reviewCountLineChart.setPrefSize(920, 500);
        reviewCountLineChart.init();
        reviewCountLineChart.setMinRange(0);
        reviewCountLineChart.setMaxRange(1);
        reviewCountLineChart.setCircleRadius(3);
        reviewCountLineChart.setOnValueChanged(event -> {

            int years = Math.toIntExact(ChronoUnit.YEARS.between(startDate, endDate));
            int months = Math.toIntExact(ChronoUnit.MONTHS.between(startDate, endDate));
            int days = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));

            double dis = reviewCountLineChart.getMaxRange() - reviewCountLineChart.getMinRange();

            if (dis == 1) {
                reviewCountChartSetYear();
            } else if (dis < 3.0 / months) {
                reviewCountChartSetDay();
            } else if (dis < 3.0 / years) {
                reviewCountChartSetMonth();
            } else {
                reviewCountChartSetYear();
            }
        });


        // score distribution bar chart
        scoreDistributionBarChart = new IntervalBarChart();
        scoreDistributionBarChart.setPrefSize(920, 500);
        scoreDistributionBarChart.init();

        // score box plot chart
        boxPlotChart = new BoxPlotChart();
        boxPlotChart.setPrefSize(500, 300);
        boxPlotChart.init();
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

    private void showSimilar() {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(similarMovieHBox);
    }

    private void scoreLineChartSetYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String startYear = startDate.format(formatter);
        String endYear = endDate.format(formatter);

        ScoreDateVO scoreDateVO = this.movieBLService.findScoreDateByYear(movieVO.id, startYear, endYear);
        setScore(scoreDateVO);
        scoreLineChart.setStartAndEnd(0, 1);
        scoreLineChart.reloadData();
    }

    private void scoreLineChartSetMonth() {
        int months = Math.toIntExact(ChronoUnit.MONTHS.between(startDate, endDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String startMonth = startDate.plusMonths((int) (months * scoreLineChart.getMinRange())).format(formatter);
        String endMonth = endDate.plusMonths(-(int) (months * (1 - scoreLineChart.getMaxRange()))).format(formatter);

        ScoreDateVO scoreDateVO = this.movieBLService.findScoreDateByMonth(movieVO.id, startMonth, endMonth);
        setScore(scoreDateVO);
        scoreLineChart.setStartAndEnd(scoreLineChart.getMinRange(), scoreLineChart.getMaxRange());
        scoreLineChart.reloadData();
    }


    private void scoreLineChartSetDay() {
        int days = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDay = startDate.plusDays((int) (days * scoreLineChart.getMinRange())).format(formatter);
        String endDay = endDate.plusDays(-(int) (days * (1 - scoreLineChart.getMaxRange()))).format(formatter);

        ScoreDateVO scoreDateVO = this.movieBLService.findScoreDateByDay(movieVO.id, startDay, endDay);
        setScore(scoreDateVO);
        scoreLineChart.setStartAndEnd(scoreLineChart.getMinRange(), scoreLineChart.getMaxRange());
        scoreLineChart.reloadData();
    }

    private void scoreLineChartSetDayInLastMonth(int lastMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDay = endDate.minusMonths(lastMonth).format(formatter);
        String endDay = endDate.format(formatter);

        ScoreDateVO scoreDateVO = this.movieBLService.findScoreDateByDay(movieVO.id, startDay, endDay);
        setScore(scoreDateVO);
        scoreLineChart.setStartAndEnd(0, 1);
        scoreLineChart.setMinRange(0);
        scoreLineChart.setMaxRange(1);
        scoreLineChart.reloadData();

    }

    private void scoreLineChartSetAllMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String startMonth = startDate.format(formatter);
        String endMonth = endDate.format(formatter);

        ScoreDateVO scoreDateVO = this.movieBLService.findScoreDateByMonth(movieVO.id, startMonth, endMonth);
        setScore(scoreDateVO);
        scoreLineChart.setStartAndEnd(0, 1);
        scoreLineChart.setMinRange(0);
        scoreLineChart.setMaxRange(1);
        scoreLineChart.reloadData();
    }

    private void setScore(ScoreDateVO scoreDateVO) {
        scoreLineChart.setKeys(scoreDateVO.dates);
        scoreLineChart.addData(scoreDateVO.scores, "score");
    }

    /**
     * 刷新列表读取亚马逊或MDB的评论
     */
    private void refreshCharts() {
        if (source.equals("Amazon")) {
            barChartReload(scoreDistributionVOAmazon);
            boxPlotChartReload(boxPlotVOAmazon);
        } else {
            barChartReload(scoreDistributionVOImdb);
            boxPlotChartReload(boxPlotVOImdb);
        }
        reviewCountChartSetYear();
    }

    private void reviewCountChartSetYear() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        String startYear = startDate.format(formatter);
        String endYear = endDate.format(formatter);

        ReviewCountVO[] reviewCountVO;
        if (source.equals("Amazon")) {
            reviewCountVO = this.movieBLService.findYearCountByMovieIdFromAmazon(movieVO.id, startYear, endYear);
        } else {
            reviewCountVO = this.movieBLService.findYearCountByMovieIdFromImdb(movieVO.id, startYear, endYear);
        }

        setReviewCount(reviewCountVO);
        reviewCountLineChart.setStartAndEnd(0, 1);
        reviewCountLineChart.reloadData();
    }

    private void reviewCountChartSetMonth() {
        int months = Math.toIntExact(ChronoUnit.MONTHS.between(startDate, endDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String startMonth = startDate.plusMonths((int) (months * reviewCountLineChart.getMinRange())).format(formatter);
        String endMonth = endDate.plusMonths(-(int) (months * (1 - reviewCountLineChart.getMaxRange()))).format(formatter);

        ReviewCountVO[] reviewCountVO;
        if (source.equals("Amazon")) {
            reviewCountVO = this.movieBLService.findMonthCountByMovieIdFromAmazon(movieVO.id, startMonth, endMonth);
        } else {
            reviewCountVO = this.movieBLService.findMonthCountByMovieIdFromImdb(movieVO.id, startMonth, endMonth);
        }

        setReviewCount(reviewCountVO);
        reviewCountLineChart.setStartAndEnd(reviewCountLineChart.getMinRange(), reviewCountLineChart.getMaxRange());
        reviewCountLineChart.reloadData();
    }


    private void reviewCountChartSetDay() {
        int days = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDay = startDate.plusDays((int) (days * reviewCountLineChart.getMinRange())).format(formatter);
        String endDay = endDate.plusDays(-(int) (days * (1 - reviewCountLineChart.getMaxRange()))).format(formatter);

        ReviewCountVO[] reviewCountVO;
        if (source.equals("Amazon")) {
            reviewCountVO = this.movieBLService.findDayCountByMovieIdFromAmazon(movieVO.id, startDay, endDay);
        } else {
            reviewCountVO = this.movieBLService.findDayCountByMovieIdFromImdb(movieVO.id, startDay, endDay);
        }

        setReviewCount(reviewCountVO);
        reviewCountLineChart.setStartAndEnd(reviewCountLineChart.getMinRange(), reviewCountLineChart.getMaxRange());
        reviewCountLineChart.reloadData();
    }

    private void setReviewCount(ReviewCountVO[] reviewCountVO) {
        reviewCountLineChart.setKeys(reviewCountVO[0].getKeys());

        for (int i = 0; i < reviewCountVO.length; i++) {
            String name;
            if (i == 0) {
                name = "All";
            } else if (i == 1) {
                name = i + " star";
            } else {
                name = i + " stars";
            }
            reviewCountLineChart.addIntegerData(reviewCountVO[i].getReviewAmounts(), name);
        }

    }

    private void barChartReload(ScoreDistributionVO scoreDistributionVO) {
        scoreDistributionBarChart.setSpaceRatio(0.5);
        List<String> keys = new ArrayList<>();
        for (int i = 1; i <= scoreDistributionVO.getReviewAmounts().size(); i++) {
            keys.add(i + "");
        }
        scoreDistributionBarChart.setKeys(keys);
        scoreDistributionBarChart.setData(scoreDistributionVO.getReviewAmounts());
        scoreDistributionBarChart.reloadData();
    }

    private void boxPlotChartReload(BoxPlotVO boxPlotVO) {
        boxPlotChart.setData(boxPlotVO.minScore, boxPlotVO.maxScore, boxPlotVO.quartiles, boxPlotVO.outerliers);
        boxPlotChart.reloadData();
    }

    @FXML
    private void clickAmazonButton() {
        source = "Amazon";
        amazonButton.setActive(true);
        imdbButton.setActive(false);
        refreshCharts();
    }

    @FXML
    private void clickImdbButton() {
        source = "Imdb";
        amazonButton.setActive(false);
        imdbButton.setActive(true);
        refreshCharts();
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
            case 3:
                showSimilar();
                break;
        }
    }

    @FXML
    private void clickAllTagLabel(Event event) {
        if (endDate == null) return;
        activeScoreTag.setActive(false);
        allScoreTag.setActive(true);
        activeScoreTag = allScoreTag;
        scoreLineChartSetYear();
        scoreLineChart.setMinRange(0);
        scoreLineChart.setMaxRange(1);
    }

    @FXML
    private void clickMonthTagLabel(Event event) {
        if (endDate == null) return;
        activeScoreTag.setActive(false);
        TagLabel tagLabel = (TagLabel) event.getSource();
        tagLabel.setActive(true);
        activeScoreTag = tagLabel;
        scoreLineChartSetAllMonth();
    }

    @FXML
    private void clickLast3MonthTagLabel(Event event) {
        if (endDate == null) return;
        activeScoreTag.setActive(false);
        TagLabel tagLabel = (TagLabel) event.getSource();
        tagLabel.setActive(true);
        activeScoreTag = tagLabel;
        scoreLineChartSetDayInLastMonth(3);
    }

    @FXML
    private void clickLastMonthTagLabel(Event event) {
        if (endDate == null) return;
        activeScoreTag.setActive(false);
        TagLabel tagLabel = (TagLabel) event.getSource();
        tagLabel.setActive(true);
        activeScoreTag = tagLabel;
        scoreLineChartSetDayInLastMonth(1);
    }
}
