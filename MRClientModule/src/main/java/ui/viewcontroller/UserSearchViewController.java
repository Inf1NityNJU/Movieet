package ui.viewcontroller;

import bl.UserBLFactory;
import blservice.UserBLService;
import component.intervalbarchart.IntervalBarChart;
import component.modeimageview.ModeImageView;
import component.rangelinechart.RangeLineChart;
import component.spinner.Spinner;
import component.topmenu.TopMenu;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import vo.ReviewCountVO;
import vo.ReviewWordsVO;
import vo.UserVO;
import vo.WordVO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sorumi on 17/4/18.
 */
public class UserSearchViewController {

    @FXML
    private ScrollPane root;

    @FXML
    private VBox contentVBox;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private VBox infoPane;

    @FXML
    private ModeImageView avatarImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label reviewAmountLabel;

    @FXML
    private HBox wordsHBox;

    @FXML
    private TopMenu otherMenu;

    @FXML
    private StackPane contentPane;

    @FXML
    private VBox statisticVBox;

    private Pane spinnerPane;

    private VBox reviewListVBox;

    private RangeLineChart rangeLineChart;
    private IntervalBarChart intervalBarChart;

    private UserVO userVO;
    private WordVO wordVO;

    private LocalDate startDate;
    private LocalDate endDate;

    private ReviewListViewController reviewListViewController;

    private MainViewController mainViewController;

    /**
     * UserBL
     */
    private UserBLService userBLService = UserBLFactory.getUserBLService();


    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void showUserSearchView() {
        mainViewController.setCenter(root);
//
        contentVBox.getChildren().remove(infoPane);

    }

    public void setUser(String userId) {

        spinnerPane = new Pane();
        spinnerPane.setPrefSize(920, 400);
        spinnerPane.getStyleClass().add("card");
        Spinner spinner = new Spinner();
        spinner.setCenterX(460);
        spinner.setCenterY(200);
        spinnerPane.getChildren().add(spinner);
        contentVBox.getChildren().add(spinnerPane);
        spinner.start();


        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {


                userVO = userBLService.findUserById(userId);
                wordVO = userBLService.findWordsByUserId(userId);

                if (userVO == null) {
                    // TODO
                    System.out.println("Invalid User ID!");
                }


                Platform.runLater(() -> {
                    initUser();

                    contentVBox.getChildren().remove(spinnerPane);
                    spinner.stop();

                    contentVBox.getChildren().add(infoPane);
                });

                return 1;
            }
        };

        new Thread(task).start();
    }

    private void initUser() {

        startDate = LocalDate.parse(userVO.firstReviewDate);
        endDate = LocalDate.parse(userVO.lastReviewDate);

        nameLabel.setText(userVO.name);
        reviewAmountLabel.setText(userVO.reviewAmounts + "");

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

        // reviews
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ReviewListView.fxml"));
            reviewListVBox = loader.load();

            reviewListViewController = loader.getController();
            reviewListViewController.setUserSearchViewController(this);
            reviewListViewController.showReviewsByUserId(userVO.id);

        } catch (IOException e) {
            e.printStackTrace();
        }

        initChart();

        //RangeLineChart
        Label timeChartLabel = new Label("Reviews with time ");
        timeChartLabel.getStyleClass().add("for-label");

        chartSetYear();

        //IntervalBarChart
        Label wordChartLabel = new Label("Reviews with words ");
        wordChartLabel.getStyleClass().add("for-label");

        ReviewWordsVO reviewWords = userBLService.getReviewWordsVO(userVO.getId());
        intervalBarChart.setKeys(reviewWords.getKeys());
        intervalBarChart.addData(reviewWords.getReviewAmounts());
        intervalBarChart.reloadData();

        statisticVBox.getChildren().addAll(timeChartLabel, rangeLineChart, wordChartLabel, intervalBarChart);

        otherMenu.setItemIndex(0);
        clickMenuItem();
    }

    private void initChart() {
        // review amount line chart
        rangeLineChart = new RangeLineChart();
        rangeLineChart.setPrefSize(920, 500);
        rangeLineChart.init();
        rangeLineChart.setMinRange(0);
        rangeLineChart.setMaxRange(1);
        rangeLineChart.setCircleRadius(3);
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

        // word bar chart
        intervalBarChart = new IntervalBarChart();
        intervalBarChart.setPrefSize(920, 400);
        intervalBarChart.init();
        intervalBarChart.setOffset(true);

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

        ReviewCountVO[] reviewCountVO = this.userBLService.findYearCountByUserId(userVO.getId(), startYear, endYear);
        setReviewCount(reviewCountVO);
        rangeLineChart.setStartAndEnd(0, 1);
        rangeLineChart.reloadData();
    }

    private void chartSetMonth() {

        int months = Math.toIntExact(ChronoUnit.MONTHS.between(startDate, endDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String startMonth = startDate.plusMonths((int) (months * rangeLineChart.getMinRange())).format(formatter);
        String endMonth = endDate.plusMonths(-(int) (months * (1 - rangeLineChart.getMaxRange()))).format(formatter);

        ReviewCountVO[] reviewCountVO = this.userBLService.findMonthCountByUserId(userVO.getId(), startMonth, endMonth);
        setReviewCount(reviewCountVO);
        rangeLineChart.setStartAndEnd(rangeLineChart.getMinRange(), rangeLineChart.getMaxRange());
        rangeLineChart.reloadData();
    }


    private void chartSetDay() {
        int days = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDay = startDate.plusDays((int) (days * rangeLineChart.getMinRange())).format(formatter);
        String endDay = endDate.plusDays(-(int) (days * (1 - rangeLineChart.getMaxRange()))).format(formatter);

        ReviewCountVO[] reviewCountVO = this.userBLService.findDayCountByUserId(userVO.getId(), startDay, endDay);
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
            rangeLineChart.addIntegerData(reviewCountVO[i].getReviewAmounts(), name);
        }

    }

    @FXML
    private void clickSearchButton() {
        String id = searchField.getText().trim();
        setUser(id);
        searchField.setText(id);
    }

    @FXML
    private void clickMenuItem() {
        int index = otherMenu.getItemIndex();
        switch (index) {
            case 0:
                showReviews();
                break;
            case 1:
                showStatistic();
                break;
        }
    }

}
