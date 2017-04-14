package ui.viewcontroller;

import component.modeimageview.ModeImageView;
import component.rangelinechart.RangeLineChart;
import component.ratestarpane.RateStarPane;
import component.taglabel.TagLabel;
import component.topmenu.TopMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import vo.MovieVO;

import java.io.IOException;
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

    private VBox reviewListVBox;

    private RangeLineChart scoreLineChart;

    private MovieViewController movieViewController;

    private ReviewListViewController reviewListViewController;

    private MovieVO movieVO;

    public void setMovieViewController(MovieViewController movieViewController) {
        this.movieViewController = movieViewController;
    }

    @FXML
    public void initialize() {

    }

    public void setMovie(MovieVO movieVO) {
        this.movieVO = movieVO;

        otherMenu.setItemIndex(0);

        // image
        posterImageView.setImage(new Image(getClass().getResource("/images/example.png").toExternalForm()));
        posterImageView.setMode(ModeImageView.ContentMode.Fill);

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
        directorLabel.setText(directors.substring(0, directors.length()-2));

        String writers = "";
        for (String writer : movieVO.writers) {
            writers += writer + ", ";
        }
        writerLabel.setText(writers.substring(0, writers.length()-2));

        String actors = "";
        for (String actor : movieVO.actors) {
            actors += actor + ", ";
        }
        actorLabel.setText(actors.substring(0, actors.length()-2));

        storylineText.setText(movieVO.plot);

        // reviews
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ReviewListView.fxml"));
            reviewListVBox = loader.load();

            reviewListViewController = loader.getController();
            reviewListViewController.setMovieInfoViewController(this);
            reviewListViewController.showReviews();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // statistic
        initChart();
    }


    /* private */

    private void initChart() {
        scoreLineChart = new RangeLineChart();
        scoreLineChart.setPrefSize(920, 500);
        scoreLineChart.init();
        scoreLineChart.setMinRange(0);
        scoreLineChart.setMaxRange(1);
//        scoreLineChart.setOnValueChanged(event -> {
//
//            int years = Math.toIntExact(ChronoUnit.YEARS.between(startDate, endDate));
//            int months = Math.toIntExact(ChronoUnit.MONTHS.between(startDate, endDate));
//            int days = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));
//
//            double dis = scoreLineChart.getMaxRange() - scoreLineChart.getMinRange();
//
//            if (dis == 1) {
//                chartSetYear();
//            } else if (dis < 3.0 / months) {
//                chartSetDay();
//            } else if (dis < 3.0 / years) {
//                chartSetMonth();
//            } else {
//                chartSetYear();
//            }
//        });

        // TODO test
        int count = 10;
        Random random = new Random();
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            keys.add(i + "");
        }
        // 设置x坐标
        scoreLineChart.setKeys(keys);

        for (int i = 0; i <= 5; i++) {
            List<Integer> nums = new ArrayList<>();
            for (int j = 0; j < count; j++) {
                nums.add(random.nextInt(15));
            }
            // 增加数据
            scoreLineChart.addData(nums, i + "");
        }
        // 载入数据
        scoreLineChart.reloadData();

        statisticVBox.getChildren().add(scoreLineChart);
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
}
