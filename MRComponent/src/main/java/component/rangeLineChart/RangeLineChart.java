package component.rangeLineChart;

import component.myrangeslider.MyRangeSlider;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Sorumi on 17/3/4.
 */
public class RangeLineChart extends Pane {

    private static final int paddingLeft = 50;
    private static final int paddingTop = 30;
    private static final int paddingBottom = 40;

    private MyRangeSlider rangeSlider;

    private Pane shapePane;
    private Pane linesPane;
    private Pane bgLinesPane;
    private Pane xLabelPane;

    private List<Polyline> polylines = new ArrayList<>();
    private List<Line> xLines = new ArrayList<>();
    private List<Line> yLines = new ArrayList<>();
    private List<Label> xLabels = new ArrayList<>();

    private List<List<Integer>> datas = new ArrayList<>();

    private List<String> keys;

    private int count;

    public void init() {
        String css = getClass().getResource("/main/Chart.css").toExternalForm();
        getStylesheets().add(css);

        this.getStyleClass().add("root");

        xLabelPane = new Pane();
        xLabelPane.setPrefSize(getPrefWidth()-paddingLeft, paddingBottom);
        xLabelPane.setLayoutX(paddingLeft);
        xLabelPane.setLayoutY(getPrefHeight() - paddingBottom);
        Rectangle xLabelclip = new Rectangle(xLabelPane.getPrefWidth(), xLabelPane.getPrefHeight());
        xLabelPane.setClip(xLabelclip);
        this.getChildren().add(xLabelPane);

        // shape pane
        shapePane = new Pane();
        shapePane.setPrefSize(getPrefWidth() - paddingLeft, getPrefHeight() - paddingTop - paddingBottom);
        shapePane.setLayoutX(paddingLeft);
        shapePane.setLayoutY(paddingTop);
        shapePane.getStyleClass().add("shape-pane");
        this.getChildren().add(shapePane);

        drawAxis();

        // line pane
        bgLinesPane = new Pane();
        bgLinesPane.setPrefSize(shapePane.getPrefWidth(), shapePane.getPrefHeight());
        Rectangle clip1 = new Rectangle(shapePane.getPrefWidth(), shapePane.getPrefHeight());
        bgLinesPane.setClip(clip1);
        shapePane.getChildren().add(bgLinesPane);

        linesPane = new Pane();
        linesPane.setPrefSize(shapePane.getPrefWidth(), shapePane.getPrefHeight());
        Rectangle clip2 = new Rectangle(shapePane.getPrefWidth(), shapePane.getPrefHeight());
        linesPane.setClip(clip2);
        linesPane.getStyleClass().add("line-pane");
        shapePane.getChildren().add(linesPane);


        rangeSlider = new MyRangeSlider();
        rangeSlider.setWidth(getPrefWidth() - 40 - paddingLeft);
        rangeSlider.setLayoutX(paddingLeft + 20 );
        rangeSlider.setOnValueChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                drawLines();
            }
        });
        this.getChildren().add(rangeSlider);


        //init
        rangeSlider.setMaxValue(0.9);
        rangeSlider.setMinValue(0.5);

        //test
        int count = 16;
        Random random = new Random();
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            keys.add(i + "");
        }
        setKeys(keys);

        for (int i = 0; i <= 5; i++) {
            List<Integer> nums = new ArrayList<>();
            for (int j = 0; j < count; j++) {
                nums.add(random.nextInt(200));
            }
            addData(nums);
        }

        drawLines();
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
        this.count = keys.size();

        this.datas.clear();
        this.polylines.clear();
    }

    public void addData(List<Integer> data) {
        if (data.size() != count) return;

        this.datas.add(data);
        Polyline polyline = new Polyline();
        polyline.getStyleClass().addAll("line", "line-" + datas.size());

        linesPane.getChildren().add(polyline);
        polylines.add(polyline);
    }

    private void drawLines() {
        double height = linesPane.getPrefHeight();

        int maxNum = 0;
        for (List<Integer> data : datas) {
            int tmpMax = maxInList(data);
            maxNum = tmpMax > maxNum ? tmpMax : maxNum;
        }
        maxNum = minMultiples(maxNum);

        double min = rangeSlider.getMinValue();
        double max = rangeSlider.getMaxValue();

        double totalWidth = getPrefWidth() / (max - min);
        double intervalWidth = totalWidth / (count - 1);
        double leftX = totalWidth * min;
        double rightX = totalWidth * max;
        int leftIndex = (int) (leftX / intervalWidth);
        int rightIndex = (int) (rightX / intervalWidth);
        if (rightIndex != count - 1) rightIndex++;

        // y
        int t = 0;
        for (int j = leftIndex; j <= rightIndex; j++) {

            Line line;
            if (xLines.size() > t) {
                line = xLines.get(t);
            } else {
                line = new Line();
                line.getStyleClass().add("y-line");
                bgLinesPane.getChildren().add(line);
                xLines.add(line);
            }

            double x = intervalWidth * j - leftX;
            line.setStartX(x);
            line.setStartY(height);
            line.setEndX(x);
            line.setEndY(0.0f);

            Label label;
            if (xLabels.size() > t) {
                label = xLabels.get(t);
            } else {
                label = new Label();
                label.getStyleClass().add("x-label");
                xLabelPane.getChildren().add(label);
                xLabels.add(label);
            }
            label.setLayoutX(x);
            label.setText(keys.get(j));
            t++;
        }


        // line
        for (int i = 0; i < datas.size(); i++) {
            Polyline polyline = polylines.get(i);
            List<Integer> data = datas.get(i);

            Double[] doubles = new Double[(rightIndex - leftIndex + 1) * 2];
            int p = 0;
            for (int j = leftIndex; j <= rightIndex; j++) {
                double x = intervalWidth * j - leftX;
                doubles[p++] = x;
                doubles[p++] = height - height * ((double) data.get(j) / maxNum);
            }

            polyline.getPoints().setAll(doubles);
        }
    }

    private void drawAxis() {
        double width = shapePane.getPrefWidth();
        double height = shapePane.getPrefHeight();

        Line xAxis = new Line();
        xAxis.setStartX(0.0f);
        xAxis.setStartY(height);
        xAxis.setEndX(width);
        xAxis.setEndY(height);
        xAxis.getStyleClass().add("axis");

        Line yAxis = new Line();
        yAxis.setStartX(0.0f);
        yAxis.setStartY(height);
        yAxis.setEndX(0.0f);
        yAxis.setEndY(0.0f);
        yAxis.getStyleClass().add("axis");

        shapePane.getChildren().addAll(xAxis, yAxis);
    }

    private Integer maxInList(List<Integer> nums) {
        if (nums.size() == 0) return 0;
        Integer max = nums.get(0);
        for (int i = 0; i < nums.size(); i++) {
            max = nums.get(i) > max ? nums.get(i) : max;
        }
        return max;
    }

    private Integer minMultiples(Integer num) {
        int length = String.valueOf(num).length();
        if (length <= 1) {
            return 10;
        }
        return 0;

//        char a = String.valueOf(num).charAt(0) + 1;
//        int result = high;



//        System.out.println(num + " " + result);
//        return result;

        // 1-9 : 10 / 5
        // 10-20 : 30 / 5
        // 20-50 : 60 / 5

        // 100-200 :200
        //200-300 400
        //300-400 500
        //
    }
}