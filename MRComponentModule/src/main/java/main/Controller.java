package main;

import component.autocompletebox.AutoCompleteBox;
import component.boxplotchart.BoxPlotChart;
import component.intervalbarchart.IntervalBarChart;
import component.meterbar.MeterBar;
import component.modeimageview.ModeImageView;
import component.rangelinechart.RangeLineChart;
import component.ringchart.NameData;
import component.ringchart.RingChart;
import component.scatterchart.ScatterChart;
import component.scatterchart.PointData;
import component.spinner.Spinner;
import component.topmenu.TopMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    private VBox contentVBox;

    @FXML
    private MeterBar meterBar;

    @FXML
    private TopMenu topMenu;

    @FXML
    private AutoCompleteBox<String> searchBox;

    @FXML
    private ModeImageView imageView;

    private RangeLineChart rangeLineChart;

    private IntervalBarChart intervalBarChart;

    private ScatterChart scatterChart;

    private RingChart ringChart;

    private BoxPlotChart boxPlotChart;

    //

    boolean moveCaretToPos = false;
    int caretPos;

    @FXML
    public void initialize() {
        testComboBox();
//        initBoxPlotChart();
//        initRingChart();
//        initRangeLineChart();
//        initScatterChart();
//        initIntervalBarChart();
//        topMenu.setItemIndex(0);

//        System.out.println(label.getWidth());

//        Spinner spinner = new Spinner();
//        spinner.setCenterX(100);
//        spinner.setCenterY(100);
//
//        rootPane.getChildren().add(spinner);
//
//        spinner.start();


//        imageView.setImage(new Image(getClass().getResource("/images/example.png").toExternalForm()));
//        imageView.setMode(ModeImageView.ContentMode.Fill);
    }

    private void testComboBox() {


        ObservableList<String> data = FXCollections.observableArrayList(
                "jacob.smith@example.com",
                "isabella.johnson@example.com",
                "ethan.williams@example.com",
                "ethan.williams@example.co",
                "ethan.williams@example.cm",
                "ethan.williams@example.om",
                "ethan.williams@examplecom",
                "emma.jones@example.com",
                "michael.brown@example.com");

        searchBox.setPromptText("Email address");
        searchBox.setData(data);

//        searchBox.setEditable(true);
//
//        searchBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
//
//            String text = searchBox.getEditor().getText();
//
//            ObservableList list = FXCollections.observableArrayList();
//            for (String str : data) {
//                if(str.toLowerCase().startsWith(text.toLowerCase())) {
//                    list.add(str);
//                }
//            }
//            searchBox.setItems(list);
//
//
//            if(!list.isEmpty() && !text.equals("")) {
//                searchBox.setVisibleRowCount(Math.min(3, list.size()));
//                searchBox.show();
//            } else {
//                searchBox.hide();
//            }
//
//        });
//        searchBox.setOnKeyReleased(event-> {
//
//            searchBox.getEditor().setText(searchBox.getSelectionModel().getSelectedItem().toString());
//            searchBox.setSelectionModel(null);
//            System.out.println(searchBox.getSelectionModel().getSelectedItem().toString());
//        });

    }



    private void initBoxPlotChart() {
        boxPlotChart = new BoxPlotChart();
        boxPlotChart.setPrefSize(500, 500);
        boxPlotChart.setLayoutX(0);
        boxPlotChart.setLayoutY(50);
        contentVBox.getChildren().add(boxPlotChart);

        boxPlotChart.init();

        ArrayList<Double> quartiles = new ArrayList<>();
        quartiles.add(-6.0);
        quartiles.add(3.0);
        quartiles.add(7.0);
        quartiles.add(9.0);
        quartiles.add(18.0);

        ArrayList<Double> outliers = new ArrayList<>();
        outliers.add(-8.0);
        outliers.add(-9.0);
        boxPlotChart.setData(-10, 19, quartiles, outliers);
        boxPlotChart.reloadData();


    }

    private void initRingChart() {
        ringChart = new RingChart();
        ringChart.setPrefSize(900, 500);
        ringChart.setLayoutX(0);
        ringChart.setLayoutY(50);
        contentVBox.getChildren().add(ringChart);

        ringChart.init();

        // test
        int count = 20;
        Random random = new Random();
        List<NameData> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            NameData nameData = new NameData(i + "     ", random.nextInt(100));
            data.add(nameData);
        }
//        ringChart.setCircleWidth(6);
        ringChart.setData(data);
        ringChart.reloadData();
    }

    private void initScatterChart() {
        scatterChart = new ScatterChart();
        scatterChart.setPrefSize(900, 500);
        scatterChart.setLayoutX(0);
        scatterChart.setLayoutY(50);
        contentVBox.getChildren().add(scatterChart);

        scatterChart.init();
        scatterChart.setxName("reviews amount");
        scatterChart.setyName("rating score");

        // test
        int count = 20;
        Random random = new Random();
        List<PointData> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name = "";
            int times = random.nextInt(30);
            while (times > 0) {
                name += i;
                times--;
            }
            PointData point = new PointData(name, random.nextInt(100), random.nextDouble() * 10);
            data.add(point);
        }
        scatterChart.setCircleRadius(5);
        scatterChart.setData(data);
        scatterChart.reloadData();
    }

    private void initIntervalBarChart() {
        intervalBarChart = new IntervalBarChart();

        intervalBarChart.setPrefSize(1000, 700);
        intervalBarChart.setLayoutX(0);
        intervalBarChart.setLayoutY(50);
        contentVBox.getChildren().add(intervalBarChart);

        intervalBarChart.init();

//        intervalBarChart.setOffset(true);
        intervalBarChart.setSpaceRatio(0.2);
        intervalBarChart.setSingle(false);
        // test
        int count = 31;
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
        intervalBarChart.setData(nums);

        // 载入数据
        intervalBarChart.reloadData();
    }

    private void initRangeLineChart() {
        rangeLineChart = new RangeLineChart();

        rangeLineChart.setPrefSize(1000, 700);
        rangeLineChart.setLayoutX(0);
        rangeLineChart.setLayoutY(50);
        contentVBox.getChildren().add(rangeLineChart);

        rangeLineChart.init();

        rangeLineChart.setMinRange(0);
        rangeLineChart.setMaxRange(1);

        //test
        int count = 20;
        Random random = new Random();
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            keys.add(i + "");
        }
        // 设置x坐标
        rangeLineChart.setKeys(keys);

        for (int i = 0; i <= 10; i++) {
            List<Double> nums = new ArrayList<>();
            for (int j = 0; j < count; j++) {
                if (j % 5 == 1) {
                    nums.add(null);
                } else {
                    nums.add(random.nextDouble() * 10);
                }
            }
            rangeLineChart.addData(nums, i + "");
        }
        // 载入数据
        rangeLineChart.reloadData();

//        count = 20;
//        rangeLineChart.clearData();
//        keys = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            keys.add(i + "");
//        }
//         重新设置x坐标
//        rangeLineChart.setKeys(keys);
//
//        for (int i = 0; i <= 5; i++) {
//            List<Double> nums = new ArrayList<>();
//            for (int j = 0; j < count; j++) {
//                nums.add(random.nextDouble()*10);
//            }
//            rangeLineChart.addData(nums, i + "");
//        }
//        // 重新载入数据
//        rangeLineChart.reloadData();

        rangeLineChart.setOnValueChanged(event -> {
            System.out.println(rangeLineChart.getMinRange() + " " + rangeLineChart.getMaxRange());
        });

    }

    @FXML
    private void clickItem() {
        System.out.println(topMenu.getItemIndex());
    }
}
