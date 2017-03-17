package main;

import component.meterbar.MeterBar;
import component.rangelinechart.RangeLineChart;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sorumi on 16/11/17.
 */
public class Controller {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private MeterBar meterBar;

    private RangeLineChart rangeLineChart;

    @FXML
    public void initialize() {
        rangeLineChart = new RangeLineChart();

        rangeLineChart.setPrefSize(1000, 700);
        rangeLineChart.setLayoutX(0);
        rangeLineChart.setLayoutY(50);
        rootPane.getChildren().add(rangeLineChart);

        rangeLineChart.init();

        rangeLineChart.setMinRange(0);
        rangeLineChart.setMaxRange(1);

        //test
        int count = 1;
        Random random = new Random();
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            keys.add(i + "");
        }
        // 设置x坐标
        rangeLineChart.setKeys(keys);

        for (int i = 0; i <= 5; i++) {
            List<Integer> nums = new ArrayList<>();
            for (int j = 0; j < count; j++) {
                nums.add(random.nextInt(15));
            }
            // 增加数据
            rangeLineChart.addData(nums, i + "");
        }
        // 载入数据
        rangeLineChart.reloadData();

//        count = 1;
//        rangeLineChart.clearData();
//        keys = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            keys.add(i + "");
//        }
////         重新设置x坐标
//        rangeLineChart.setKeys(keys);
//
//        for (int i = 0; i <= 5; i++) {
//            List<Integer> nums = new ArrayList<>();
//            for (int j = 0; j < count; j++) {
//                nums.add(random.nextInt(7));
//            }
//            rangeLineChart.addData(nums, i + "");
//        }
//        // 重新载入数据
//        rangeLineChart.reloadData();

        // 设置 slider 初值 0-1


        //
        rangeLineChart.setOnValueChanged(event -> {
//            System.out.print(rangeLineChart.getMinRange() + " " + rangeLineChart.getMaxRange());
        });

    }

    @FXML
    public void click() {
//        rangelinechart.scale();
    }
}
