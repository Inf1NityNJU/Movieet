package ui.viewcontroller;

import bl.UserBLFactory;
import blservice.UserBLService;
import component.intervalbarchart.IntervalBarChart;
import component.rangelinechart.RangeLineChart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import vo.ReviewCountVO;
import vo.ReviewWordsVO;
import vo.UserVO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Sorumi on 17/3/16.
 */
public class UserViewController {

    @FXML
    private Label userIdLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label reviewAmountLabel;

    @FXML
    private VBox chartVBox;

    private RangeLineChart rangeLineChart;

    private IntervalBarChart intervalBarChart;

    private UserVO userVO;

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
        userBLService = UserBLFactory.getUserBLService();
        userVO = userBLService.findUserById(userId);

        if (userVO == null) {
            mainViewController.showAlertView("Valid User ID!");
            return;
        }

        startDate = LocalDate.parse(userVO.getFirstReviewDate());
        endDate = LocalDate.parse(userVO.getLastReviewDate());

        userIdLabel.setText(userId);
//        userNameLabel.setText(this.userVO.());
        reviewAmountLabel.setText(this.userVO.getReviewAmounts() + " reviews");


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

            if (dis < 3.0 / months) {
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
        ReviewWordsVO reviewWords = userBLService.getReviewWordsVO(userId);

        intervalBarChart.setKeys(reviewWords.getKeys());
        intervalBarChart.addData(reviewWords.getReviewAmounts());

        intervalBarChart.reloadData();
        chartVBox.getChildren().add(intervalBarChart);
    }

    private void chartSetYear() {
        ReviewCountVO[] reviewCountVO = this.userBLService.findYearCountByUserId(userVO.getId());
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
