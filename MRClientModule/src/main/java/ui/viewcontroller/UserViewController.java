package ui.viewcontroller;

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

}
