package ui.viewcontroller;

import bl.MovieBLFactory;
import blservice.MovieBLService;
import component.intervalbarchart.IntervalBarChart;
import component.ringchart.NameData;
import component.ringchart.RingChart;
import component.scatterchart.PointData;
import component.scatterchart.ScatterChart;
import component.taglabel.TagLabel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import util.MovieGenre;
import vo.MovieGenreVO;
import vo.ScoreAndReviewAmountVO;

import java.util.ArrayList;
import java.util.EnumSet;
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

    private TilePane genrePane;

    private RingChart ringChart;
    private IntervalBarChart intervalBarChart;
    private ScatterChart scatterChart;

    private MainViewController mainViewController;

    private MovieBLService movieBLService = MovieBLFactory.getMovieBLService();

    private List<TagLabel> tagLabels = new ArrayList<>();
    public EnumSet tags = EnumSet.of(MovieGenre.All);

    public StatisticViewController() {

    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void initCharts() {
        initGenreRingChart();
        initGenreBarChart();
        initScoreScatterChart();
    }

    public void showStatisticView() {
        mainViewController.setCenter(root);
        MovieGenreVO movieGenreVO = movieBLService.findMovieGenre();

        // ring chart
        int count = movieGenreVO.tags.size();
        List<NameData> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            NameData nameData = new NameData(movieGenreVO.tags.get(i), movieGenreVO.amounts.get(i));
            data.add(nameData);
        }

        ringChart.setData(data);
        ringChart.reloadData();

        // interval bar chart
        intervalBarChart.setKeys(movieGenreVO.tags);
        intervalBarChart.addData(movieGenreVO.amounts);
        intervalBarChart.reloadData();

        // scatter chart
        onClickTagLabel((TagLabel) genrePane.getChildren().get(0));
    }

    /* private */

    private void initGenreRingChart() {
        ringChart = new RingChart();
        ringChart.setPrefSize(920, 300);
        ringChart.setLayoutX(0);
        ringChart.setLayoutY(50);
        genreChartVBox.getChildren().add(ringChart);

        ringChart.init();

    }

    private void initGenreBarChart() {
        intervalBarChart = new IntervalBarChart();

        intervalBarChart.setPrefSize(920, 500);
        intervalBarChart.setLayoutX(0);
        intervalBarChart.setLayoutY(50);
        genreChartVBox.getChildren().add(intervalBarChart);

        intervalBarChart.init();

        intervalBarChart.setSpaceRatio(0.2);
        intervalBarChart.setSingle(false);
    }

    private void initScoreScatterChart() {
        scatterChart = new ScatterChart();
        scatterChart.setPrefSize(920, 500);
        scatterChart.setLayoutX(0);
        scatterChart.setLayoutY(50);
        scoreChartVBox.getChildren().add(scatterChart);

        scatterChart.init();

        scatterChart.setCircleWidth(6);

        genrePane = new TilePane();
        genrePane.setHgap(10);
        genrePane.setVgap(10);
        genrePane.setTileAlignment(Pos.CENTER_LEFT);

        for (MovieGenre genre : MovieGenre.values()) {
            TagLabel tagLabel = new TagLabel();
            tagLabel.setOnMouseClicked(event -> onClickTagLabel(tagLabel));
            tagLabel.setText(genre.getGenreName());
            tagLabel.setCursor(Cursor.HAND);
            genrePane.getChildren().add(tagLabel);
        }
        scoreChartVBox.getChildren().add(genrePane);
    }

    private void onClickTagLabel(TagLabel tagLabel) {
        TagLabel allTagLabel = (TagLabel) genrePane.getChildren().get(0);
        MovieGenre genre = MovieGenre.getMovieGenreByName(tagLabel.getText());

        if (tagLabel != allTagLabel) {
            boolean active = !tagLabel.getActive();
            tagLabel.setActive(active);
            if (active) {
                tagLabels.add(tagLabel);
                if (genre != null) {
                    tags.add(genre);
                }
            } else {
                tagLabels.remove(tagLabel);
                tags.remove(genre);

            }
            if (tagLabels.size() == 0) {
                System.out.println("0");
                allTagLabel.setActive(true);
                tags.add(MovieGenre.All);
                tagLabels.add(allTagLabel);
            } else {
                allTagLabel.setActive(false);
                tags.remove(MovieGenre.All);
                tagLabels.remove(allTagLabel);
            }

        } else {
            for (TagLabel tmpTag : tagLabels) {
                tmpTag.setActive(false);
            }
            allTagLabel.setActive(true);
            tags.clear();
            tagLabels.clear();
            tags.add(MovieGenre.All);
            tagLabels.add(allTagLabel);
        }
        refreshScatterChart();
    }

    private void refreshScatterChart() {
        ScoreAndReviewAmountVO scoreAndReviewAmountVO = movieBLService.findRelationBetweenScoreAndReviewAmount(tags);
        // test
        int count = scoreAndReviewAmountVO.names.size();
        List<PointData> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            PointData point = new PointData(scoreAndReviewAmountVO.reviewAmounts.get(i), scoreAndReviewAmountVO.scores.get(i));
            data.add(point);
        }

        scatterChart.setData(data);
        scatterChart.reloadData();
    }

}
