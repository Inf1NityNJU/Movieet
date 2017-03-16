package ui.viewcontroller;

import bl.UserBLFactory;
import bl.UserBLServiceImpl;
import blservice.UserBLService;
import component.rangelinechart.RangeLineChart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import vo.UserVO;

import java.time.LocalDate;

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

    private UserVO userVO;

    private LocalDate startDate;

    private LocalDate endDate;

    /**
     * UserBL
     */
    private UserBLService userBLService;

    public void setUser(String userId) {
        userBLService = UserBLFactory.getUserBLService();
        userVO = userBLService.findUserById(userId);

        startDate = LocalDate.parse(userVO.getFirstReviewDate());
        endDate = LocalDate.parse(userVO.getLastReviewDate());

//        System.out.println(userVO.getFirstReviewDate() + " " + userVO.getLastReviewDate());

        userIdLabel.setText(userId);
//        userNameLabel.setText(this.userVO.g());
//        amountLabel.setText(this.userVO.getAmountOfReview() + " reviews");
//        starPane.setScore((int) this.userVO.getAverageScore());

    }
}
