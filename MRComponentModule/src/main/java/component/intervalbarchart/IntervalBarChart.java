package component.intervalbarchart;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import util.ChartScale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sorumi on 17/3/17.
 */
public class IntervalBarChart extends Pane {

    private static final int paddingLeft = 50;
    private static final int paddingRight = 50;
    private static final int paddingTop = 30;
    private static final int paddingBottom = 80;

    private static final int chartPaddingTop = 20;

    private static final int minXLabelWidth = 90;

    private Pane shapePane;
    private Pane barPane;
    private Pane yLinesPane;
    private Pane xLabelPane;
    private Pane yLabelPane;

    private Label activeXLabel;
    private Label activeDataLabel;

    private List<Rectangle> rectangles = new ArrayList<>();
    private List<Line> xLines = new ArrayList<>();
    private List<Label> xLabels = new ArrayList<>();

    private List<Integer> data = new ArrayList<>();
    private List<String> keys;

    private int keyCount;
    private Integer maxValue;
    private int tick;

    private boolean isOffset = false;
    private static final String[] colors = {
            "#E3645A", "#F48984", "#FDB8A1", "#F7CC9B",
            "#F8D76E", "#FEE9A5", "#F0E0BC", "#D1CCC6",
            "#B6D7B3", "#BEE1DA", "#A7DAD8", "#92BCC3",
            "#93A9BD", "#B9CDDC", "#BABBDE", "#928BA9",
            "#CA9ECE", "#EFCEED", "#FECEDC", "#FAA5B3"
    };

    //    private String[] colors = {"#9E0041", "#C32F4B", "#E1514B", "#F47245", "#FB9F59", "#FEC574", "#FAE38C", "#EAF195", "#C7E89E", "#9CD6A4", "#6CC4A4", "#4D9DB4", "#4776B4", "#5E4EA1"};
    private String color = "#6ED3D8";
    private double spaceRatio = 0.1;
    private boolean isSingle = false;

    public void init() {
        String css = getClass().getResource("/main/Chart.css").toExternalForm();
        getStylesheets().add(css);

        this.getStyleClass().add("root");

        Rectangle clip = new Rectangle(getPrefWidth(), getPrefHeight());
        setClip(clip);

        xLabelPane = new Pane();
        xLabelPane.setPrefSize(getPrefWidth() - paddingLeft, paddingBottom);
        xLabelPane.setLayoutX(paddingLeft);
        xLabelPane.setLayoutY(getPrefHeight() - paddingBottom + 5);

        yLabelPane = new Pane();
        yLabelPane.setPrefSize(paddingLeft, getPrefHeight());
        yLabelPane.setLayoutY(paddingTop);

        // shape pane
        shapePane = new Pane();
        shapePane.setPrefSize(getPrefWidth() - paddingLeft, getPrefHeight() - paddingTop - paddingBottom);
        shapePane.setLayoutX(paddingLeft);
        shapePane.setLayoutY(paddingTop);
        shapePane.getStyleClass().add("shape-pane");

        // line pane
        double width = shapePane.getPrefWidth();
        double height = shapePane.getPrefHeight();

        yLinesPane = new Pane();
        yLinesPane.setPrefSize(width, height);
        Rectangle clipX = new Rectangle(width, height);
        yLinesPane.setClip(clipX);
        shapePane.getChildren().add(yLinesPane);

        barPane = new Pane();
        barPane.setPrefSize(width, height);
        barPane.getStyleClass().add("line-pane");
        shapePane.getChildren().add(barPane);


        // mouse move
        activeXLabel = new Label();
        activeXLabel.getStyleClass().add("active-label");
        activeXLabel.setPrefSize(minXLabelWidth, 30);
        activeXLabel.setLayoutY(getPrefHeight() - paddingBottom);
        activeXLabel.setLayoutX(getPrefWidth() + 100);

        activeDataLabel = new Label();
        activeDataLabel.getStyleClass().add("active-data-label");
        activeDataLabel.setPrefSize(minXLabelWidth, 30);
        activeDataLabel.setLayoutX(getPrefWidth() + 100);

        shapePane.setOnMouseEntered(event -> {
            shapeOnMouseEntered(event);
        });
        shapePane.setOnMouseMoved(event -> {
            shapeOnMouseMoved(event);
        });
        shapePane.setOnMouseExited(event -> {
            shapeOnMouseExited(event);
        });

        shapePane.getChildren().add(activeDataLabel);
        this.getChildren().addAll(xLabelPane, yLabelPane, shapePane, activeXLabel);

        drawAxis();

    }

    // 设置横坐标
    public void setKeys(List<String> keys) {
        this.keys = keys;
        this.keyCount = keys.size();

        clearData();
    }

    public void addData(List<Integer> data) {
        if (data.size() != keyCount) return;

        this.data = data;

        Rectangle rectangle;

        for (int i = 0; i < keyCount; i++) {
            if (rectangles.size() > i) {
                rectangle = rectangles.get(i);
            } else {
                rectangle = new Rectangle();
                rectangle.getStyleClass().addAll("line");

                rectangles.add(rectangle);
            }
            String color = isSingle ? this.color : colors[i % colors.length];
            rectangle.setFill(Color.web(color));

            barPane.getChildren().add(rectangle);
        }
    }

    public void clearData() {
        data = null;
        barPane.getChildren().clear();
    }

    public void reloadData() {
        calculateMaxValue();
        setYLabels();
        draw();
    }

    private void calculateMaxValue() {
        maxValue = maxInList(data);

        if (maxValue < 10) {
            maxValue = maxValue + 1;
            tick = maxValue;
        } else {
            ChartScale chartScale = new ChartScale(0, maxValue);
            chartScale.setMaxTicks(10);
            maxValue = (int) chartScale.getNiceMax();
            tick = (int) (chartScale.getNiceMax() / chartScale.getTickSpacing());
        }
    }

    private void setYLabels() {
        yLinesPane.getChildren().clear();
        yLabelPane.getChildren().clear();

        int interval = maxValue / tick;

        double height = shapePane.getPrefHeight() - chartPaddingTop;
        double width = shapePane.getPrefWidth();

        double intervalHeight = height / tick;
        for (int j = 0; j <= tick; j++) {
            Line line = new Line();
            line.getStyleClass().add("y-line");
            yLinesPane.getChildren().add(line);
            line.setStartX(0);
            line.setStartY(chartPaddingTop + intervalHeight * j);
            line.setEndX(width);
            line.setEndY(chartPaddingTop + intervalHeight * j);

            Label label = new Label();
            label.getStyleClass().add("y-label");
            yLabelPane.getChildren().add(label);
            label.setText(interval * j + "");
            label.setLayoutY(height + chartPaddingTop - intervalHeight * j - 10);
            label.setPrefSize(paddingLeft - 5, 20);
            label.setAlignment(Pos.CENTER_RIGHT);
        }

    }

    private void draw() {
        if (data == null || maxValue == null) return;
        double height = shapePane.getPrefHeight() - chartPaddingTop;
        double width = shapePane.getPrefWidth();

        double intervalWidth = (width - paddingRight) / data.size();

        // x label

        int t = 0;
        for (int i = 0; i < keyCount; i++) {
            double x;

            if (!isOffset) {
                x = intervalWidth * (i + 1) - intervalWidth * (1 - spaceRatio) / 2;
            } else {
                x = intervalWidth * i + intervalWidth * spaceRatio / 2;
            }
            Label label;
            if (xLabelPane.getChildren().size() > t) {
                label = (Label) xLabelPane.getChildren().get(t);
            } else if (xLabels.size() > t) {
                label = xLabels.get(t);
                xLabelPane.getChildren().add(label);
            } else {
                label = new Label();
                label.setPrefSize(minXLabelWidth, 20);

                label.getStyleClass().add("x-label");
                xLabelPane.getChildren().add(label);
                xLabels.add(label);
            }

            label.setText(keys.get(i));
            label.setLayoutX(x - minXLabelWidth / 2);

            if (intervalWidth < minXLabelWidth/2) {
                label.getStyleClass().add("vertical-label");
                label.setLayoutY(40);
            }
            t++;
        }
        xLabelPane.getChildren().removeAll(xLabels.subList(t, xLabels.size()));


        // bar
        for (int i = 0; i < data.size(); i++) {
            Rectangle rectangle = rectangles.get(i);
            int num = data.get(i);

            double x = intervalWidth * i + intervalWidth * spaceRatio;
            double rectWidth = intervalWidth * (1 - spaceRatio);
            double rectHeight = height * ((double) num / maxValue);
            double y = chartPaddingTop + height - rectHeight;
            rectangle.setWidth(rectWidth);
            rectangle.setHeight(rectHeight);
            rectangle.setLayoutX(x);
            rectangle.setLayoutY(y);
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


    private void shapeOnMouseMoved(MouseEvent event) {
        if (keyCount == 0) return;
//        int size = data.size();
        double offsetX = event.getX();

        //
        double width = shapePane.getPrefWidth();
        double height = shapePane.getPrefHeight() - chartPaddingTop;

        double intervalWidth = (width - paddingRight) / keyCount;
        double absoluteX = offsetX - intervalWidth * spaceRatio / 2;
        int index = (int) Math.floor(absoluteX / intervalWidth);

//        System.out.println(offsetX + " " + absoluteX + " " + intervalWidth);

        if (index < 0) index = 0;
        if (index > keyCount - 1) index = keyCount - 1;
        double resultX = (1 + index) * intervalWidth - intervalWidth * (1 - spaceRatio) / 2;

        double x = resultX - minXLabelWidth / 2;

        if (!isOffset) {
            activeXLabel.setText(keys.get(index));
        } else {
            if (index < keyCount - 1) {
                activeXLabel.setText(keys.get(index) + " - " + keys.get(index + 1));
            } else {
                activeXLabel.setText(keys.get(index) + " + ");
            }

        }
//        activeXLabel.setLayoutX(x + paddingLeft);

        activeDataLabel.setText(data.get(index) + "");
        activeDataLabel.setLayoutX(x);
        activeDataLabel.setLayoutY(chartPaddingTop + height - height * ((double) data.get(index) / maxValue) - 30);

        Timeline timeline = new Timeline();

        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(300), new KeyValue(activeXLabel.layoutXProperty(), x + paddingLeft))
        );
        timeline.play();
    }


    private void shapeOnMouseEntered(MouseEvent event) {
        activeXLabel.setVisible(true);
        activeDataLabel.setVisible(true);
    }

    private void shapeOnMouseExited(MouseEvent event) {
        activeXLabel.setVisible(false);
        activeDataLabel.setVisible(false);
    }

    private Integer maxInList(List<Integer> nums) {
        if (nums == null || nums.size() == 0) return 0;
        Integer max = nums.get(0);
        for (int i = 0; i < nums.size(); i++) {
            max = nums.get(i) > max ? nums.get(i) : max;
        }
        return max;
    }

    public boolean isOffset() {
        return isOffset;
    }

    public void setOffset(boolean offset) {
        isOffset = offset;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        if (isSingle) {
            for (Rectangle rectangle : rectangles) {
                rectangle.setFill(Color.web(color));
            }
        }
    }

    public double getSpaceRatio() {
        return spaceRatio;
    }

    public void setSpaceRatio(double spaceRatio) {
        this.spaceRatio = spaceRatio;
        draw();
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        if (single != isSingle) {
            isSingle = single;
            for (int i = 0; i < rectangles.size(); i++) {
                Rectangle rectangle = rectangles.get(i);
                String color = isSingle ? this.color : colors[i % colors.length];
                rectangle.setFill(Color.web(color));
            }
        }
    }
}
