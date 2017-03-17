package ui.viewcontroller;

import bl.UserBLFactory;
import blservice.UserBLService;
import component.intervalbarchart.IntervalBarChart;
import component.rangelinechart.RangeLineChart;
import component.spinner.Spinner;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import vo.ReviewCountVO;
import vo.ReviewWordsVO;
import vo.UserVO;
import vo.WordVO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by Sorumi on 17/3/16.
 */
public class UserViewController {


    @FXML
    private Pane infoPane;

    @FXML
    private Label userIdLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label reviewAmountLabel;

    @FXML
    private VBox chartVBox;

    @FXML
    private HBox wordsHBox;

    @FXML
    private Pane spinnerPane;


    private RangeLineChart rangeLineChart;

    private IntervalBarChart intervalBarChart;

    private UserVO userVO;

    private WordVO wordVO;

    private LocalDate startDate;

    private LocalDate endDate;

    private MainViewController mainViewController;

    /**
     * UserBL
     */
    private UserBLService userBLService;

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void setUser(String userId) {
        infoPane.setVisible(false);
        userBLService = UserBLFactory.getUserBLService();

        spinnerPane.setPrefHeight(0);
        Spinner spinner = new Spinner();
        spinner.setCenterX(540);
        spinner.setCenterY(100);
        spinnerPane.getChildren().add(spinner);
        chartVBox.getChildren().add(spinnerPane);
        spinner.start();

        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                userVO = userBLService.findUserById(userId);
                wordVO = userBLService.findWordsByUserId(userId);

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


        if (userVO == null) {
            mainViewController.showAlertView("Valid User ID!");
            return;
        }

        startDate = LocalDate.parse(userVO.getFirstReviewDate());
        endDate = LocalDate.parse(userVO.getLastReviewDate());

        userIdLabel.setText(userVO.getId());
        userNameLabel.setText(userVO.getName());
        reviewAmountLabel.setText(userVO.getReviewAmounts() + " reviews");

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

        //IntercalBarChart
        intervalBarChart = new IntervalBarChart();
        intervalBarChart.setPrefSize(1000, 600);
        intervalBarChart.init();
        intervalBarChart.setOffset(true);
        ReviewWordsVO reviewWords = userBLService.getReviewWordsVO(userVO.getId());

        intervalBarChart.setKeys(reviewWords.getKeys());
        intervalBarChart.addData(reviewWords.getReviewAmounts());

        intervalBarChart.reloadData();
        chartVBox.getChildren().add(intervalBarChart);
    }

    private void chartSetYear() {
        ReviewCountVO[] reviewCountVO = this.userBLService.findYearCountByUserId(userVO.getId(), "2011", "2012");
        setReviewCount(reviewCountVO);
        rangeLineChart.setStartAndEnd(0, 1);
        rangeLineChart.reloadData();
    }

    private void chartSetMonth(String startMonth, String endMonth) {
        ReviewCountVO[] reviewCountVO = this.userBLService.findMonthCountByUserId(userVO.getId(), startMonth, endMonth);
        setReviewCount(reviewCountVO);
        rangeLineChart.setStartAndEnd(rangeLineChart.getMinRange(), rangeLineChart.getMaxRange());
        rangeLineChart.reloadData();
    }


    private void chartSetDay(String startDay, String endDay) {
        ReviewCountVO[] reviewCountVO = this.userBLService.findDayCountByUserId(userVO.getId(), startDay, endDay);
        setReviewCount(reviewCountVO);
        rangeLineChart.setStartAndEnd(rangeLineChart.getMinRange(), rangeLineChart.getMaxRange());
        rangeLineChart.reloadData();
    }

    private void setReviewCount(ReviewCountVO[] reviewCountVO) {
        System.out.println(reviewCountVO[0].getKeys());
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
