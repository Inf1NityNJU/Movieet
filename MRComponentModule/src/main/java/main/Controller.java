package main;

import component.intervalbarchart.IntervalBarChart;
import component.meterbar.MeterBar;
import component.modeimageview.ModeImageView;
import component.rangelinechart.RangeLineChart;
import component.spinner.Spinner;
import component.topmenu.TopMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

    @FXML
    private TopMenu topMenu;

    @FXML
    private ModeImageView imageView;

    private RangeLineChart rangeLineChart;

    private IntervalBarChart intervalBarChart;

    @FXML
    public void initialize() {

        topMenu.setItemIndex(0);

//        System.out.println(label.getWidth());

//        Spinner spinner = new Spinner();
//        spinner.setCenterX(100);
//        spinner.setCenterY(100);
//
//        rootPane.getChildren().add(spinner);
//
//        spinner.start();

//        initRangeLineChart();

//        imageView.setImage(new Image(getClass().getResource("/images/example.png").toExternalForm()));
//        imageView.setMode(ModeImageView.ContentMode.Fill);
    }


    private void initIntervalBarChart() {
        intervalBarChart = new IntervalBarChart();

        intervalBarChart.setPrefSize(1000, 700);
        intervalBarChart.setLayoutX(0);
        intervalBarChart.setLayoutY(50);
        rootPane.getChildren().add(intervalBarChart);

        intervalBarChart.init();

        intervalBarChart.setOffset(true);
        // test
        int count = 12;
        Random random = new Random();
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            keys.add(i + "");
        }
        intervalBarChart.setKeys(keys);

        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            nums.add(random.nextInt(30));
        }
        // 增加数据
        intervalBarChart.addData(nums);

        // 载入数据
        intervalBarChart.reloadData();
    }

    private void initRangeLineChart() {
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

        count = 20;
        rangeLineChart.clearData();
        keys = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            keys.add(i + "");
        }
//         重新设置x坐标
        rangeLineChart.setKeys(keys);

        for (int i = 0; i <= 5; i++) {
            List<Integer> nums = new ArrayList<>();
            for (int j = 0; j < count; j++) {
                nums.add(random.nextInt(7));
            }
            rangeLineChart.addData(nums, i + "");
        }
        // 重新载入数据
        rangeLineChart.reloadData();

        rangeLineChart.setOnValueChanged(event -> {
            System.out.print(rangeLineChart.getMinRange() + " " + rangeLineChart.getMaxRange());
        });

    }

    @FXML
    private void clickItem() {
        System.out.println(topMenu.getItemIndex());
    }
}
