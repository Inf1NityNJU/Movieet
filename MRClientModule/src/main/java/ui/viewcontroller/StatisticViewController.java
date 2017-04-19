package ui.viewcontroller;

import bl.MovieBLFactory;
import blservice.MovieBLService;
import component.intervalbarchart.IntervalBarChart;
import component.ringchart.NameData;
import component.ringchart.RingChart;
import component.scatterchart.PointData;
import component.scatterchart.ScatterChart;
import component.spinner.Spinner;
import component.taglabel.TagLabel;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import ui.componentcontroller.SimilarMovieCellController;
import util.MovieGenre;
import vo.MovieGenreVO;
import vo.MovieVO;
import vo.ScoreAndReviewAmountVO;

import java.io.IOException;
import java.util.*;

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

    private TagLabel sortButton;

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
        sortButton.setOnMouseClicked(event -> {
            boolean active = !sortButton.getActive();
            sortButton.setActive(active);
            onClickSortButton(movieGenreVO);
        });
        onClickSortButton(movieGenreVO);

        // scatter chart
        onClickTagLabel((TagLabel) genrePane.getChildren().get(0));
    }

    /* private */

    public class NameDataComparator implements Comparator<NameData> {

        public int compare(NameData nameData1, NameData nameData2) {
            return nameData2.value - nameData1.value;
        }

    }

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

        sortButton = new TagLabel();
        sortButton.setText("Sort");
        genreChartVBox.getChildren().add(sortButton);

    }

    private void initScoreScatterChart() {
        scatterChart = new ScatterChart();
        scatterChart.setPrefSize(920, 500);
        scatterChart.setLayoutX(0);
        scatterChart.setLayoutY(50);
//        scoreChartVBox.getChildren().add(scatterChart);

        scatterChart.init();

        scatterChart.setCircleRadius(4);
        scatterChart.setxName("Reviews amount");
        scatterChart.setyName("Rating score");

        genrePane = new TilePane();
        genrePane.setHgap(10);
        genrePane.setVgap(10);
        genrePane.setTileAlignment(Pos.CENTER_LEFT);

        genrePane.getChildren().clear();
        for (MovieGenre genre : MovieGenre.values()) {
            TagLabel tagLabel = new TagLabel();
            tagLabel.setOnMouseClicked(event -> onClickTagLabel(tagLabel));
            tagLabel.setText(genre.getGenreName());
            tagLabel.setCursor(Cursor.HAND);
            genrePane.getChildren().add(tagLabel);
        }
//        scoreChartVBox.getChildren().add(genrePane);
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

    private void onClickSortButton(MovieGenreVO movieGenreVO) {
        boolean active = sortButton.getActive();
        if (active) {
            List<NameData> nameDatas = new ArrayList<>();
            for (int i = 0; i < movieGenreVO.tags.size(); i++) {
                NameData nameData = new NameData(movieGenreVO.tags.get(i), movieGenreVO.amounts.get(i));
                nameDatas.add(nameData);
            }
            nameDatas.sort(new NameDataComparator());

            List<String> keys = new ArrayList<>();
            List<Integer> datas = new ArrayList<>();

            for (NameData nameData : nameDatas) {
                keys.add(nameData.name);
                datas.add(nameData.value);
            }

            intervalBarChart.setKeys(keys);
            intervalBarChart.setData(datas);
            intervalBarChart.reloadData();
        } else {
            intervalBarChart.setKeys(movieGenreVO.tags);
            intervalBarChart.setData(movieGenreVO.amounts);
            intervalBarChart.reloadData();
        }

    }

    private void refreshScatterChart() {
        scoreChartVBox.getChildren().remove(scatterChart);
        scoreChartVBox.getChildren().remove(genrePane);

        Pane spinnerPane = new Pane();
        spinnerPane.setPrefSize(920, 200);
        spinnerPane.setVisible(true);
        spinnerPane.setManaged(true);
        Spinner spinner = new Spinner();
        spinner.setCenterX(460);
        spinner.setCenterY(100);
        spinnerPane.getChildren().add(spinner);
        scoreChartVBox.getChildren().add(spinnerPane);
        spinner.start();

        Task<Integer> similarTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {

                ScoreAndReviewAmountVO scoreAndReviewAmountVO = movieBLService.findRelationBetweenScoreAndReviewAmount(tags);

                int count = scoreAndReviewAmountVO.names.size();
                List<PointData> data = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    PointData point = new PointData(scoreAndReviewAmountVO.names.get(i), scoreAndReviewAmountVO.reviewAmounts.get(i), scoreAndReviewAmountVO.scores.get(i));
                    data.add(point);
                }

                Platform.runLater(() -> {
                    scatterChart.setData(data);
                    scatterChart.reloadData();
                    scoreChartVBox.getChildren().add(scatterChart);
                    scoreChartVBox.getChildren().add(genrePane);
                    scoreChartVBox.getChildren().remove(spinnerPane);
                    spinner.stop();

                });

                return 1;
            }
        };

        new Thread(similarTask).start();

    }

}
