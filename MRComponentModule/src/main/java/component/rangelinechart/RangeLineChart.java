package component.rangelinechart;

import component.dotbutton.DotButton;
import component.myrangeslider.MyRangeSlider;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.shape.Circle;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import util.ChartScale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sorumi on 17/3/4.
 */
public class RangeLineChart extends Pane {

    //    private static final String[] colors = {"#F6807A", "#F3B7C0", "#FAE5BA", "#6ED8CD", "#57BEDC", "#B799E6"};
    private static final String[] colors = {"#FF6158", "#FF8597", "#F7D080", "#18C2B0", "#23ACD4", "#9F7ADA"};

    private static final int maxTick = 10;

    private static final int paddingLeft = 50;
    private static final int paddingRight = 50;
    private static final int paddingTop = 30;
    private static final int paddingBottom = 40;

    private static final int buttonPaneHeight = 60;
    private static final int minXLabelWidth = 90;
    private static final int activeLabelWidth = 90;

    private MyRangeSlider rangeSlider;

    private Pane shapePane;
    private Pane polylinePane;
    private Pane xLinesPane;
    private Pane yLinesPane;
    private Pane xLabelPane;
    private Pane yLabelPane;
    private HBox buttonPane;

    private Line activeYLine;
    private Label activeXLabel;
    private VBox dataLabelsBox;

    private List<Polyline> polylines = new ArrayList<>();
    private List<Circle> circles = new ArrayList<>();
    private List<Line> xLines = new ArrayList<>();
    //    private List<Line> yLines = new ArrayList<>();
    private List<Label> xLabels = new ArrayList<>();
    //    private List<Label> yLabels = new ArrayList<>();
    private List<Label> dateLabels = new ArrayList<>();
    private List<DotButton> dotButtons = new ArrayList<>();

    private List<List<Integer>> datas = new ArrayList<>();

    private List<String> keys;
    private List<String> lineNames = new ArrayList<>();

    private int keyCount;
    private Integer maxValue;
    private int tick;
    private double start;
    private double end;

    private ObjectProperty<EventHandler<Event>> onValueChanged = new SimpleObjectProperty<EventHandler<Event>>();
    private DoubleProperty minRange = new SimpleDoubleProperty();
    private DoubleProperty maxRange = new SimpleDoubleProperty();

    public void init() {
        String css = getClass().getResource("/main/Chart.css").toExternalForm();
        getStylesheets().add(css);

        this.getStyleClass().add("root");

        Rectangle clip = new Rectangle(getPrefWidth(), getPrefHeight());
        setClip(clip);

        xLabelPane = new Pane();
        xLabelPane.setPrefSize(getPrefWidth() - paddingLeft, paddingBottom);
        xLabelPane.setLayoutX(paddingLeft);
        xLabelPane.setLayoutY(getPrefHeight() - paddingBottom - buttonPaneHeight + 5);

        yLabelPane = new Pane();
        yLabelPane.setPrefSize(paddingLeft, getPrefHeight());
        yLabelPane.setLayoutY(paddingTop);

        buttonPane = new HBox();
        buttonPane.setPrefSize(getPrefWidth() - paddingLeft, buttonPaneHeight);
        buttonPane.setLayoutX(paddingLeft);
        buttonPane.setLayoutY(getPrefHeight() - buttonPaneHeight);
        buttonPane.setSpacing(20);

        // shape pane
        shapePane = new Pane();
        shapePane.setPrefSize(getPrefWidth() - paddingLeft, getPrefHeight() - paddingTop - paddingBottom - buttonPaneHeight);
        shapePane.setLayoutX(paddingLeft);
        shapePane.setLayoutY(paddingTop);
        shapePane.getStyleClass().add("shape-pane");

        // line pane
        double width = shapePane.getPrefWidth();
        double height = shapePane.getPrefHeight();

        xLinesPane = new Pane();
        xLinesPane.setPrefSize(width, height);
        Rectangle clipX = new Rectangle(width, height);
        xLinesPane.setClip(clipX);
        shapePane.getChildren().add(xLinesPane);

        yLinesPane = new Pane();
        yLinesPane.setPrefSize(width, height);
        Rectangle clipY = new Rectangle(width, height);
        yLinesPane.setClip(clipY);
        shapePane.getChildren().add(yLinesPane);

        polylinePane = new Pane();
        polylinePane.setPrefSize(width, height);
        Rectangle polylineClip = new Rectangle(width, height);
        polylinePane.setClip(polylineClip);
        polylinePane.getStyleClass().add("line-pane");
        shapePane.getChildren().add(polylinePane);

        rangeSlider = new MyRangeSlider();
        rangeSlider.setWidth(getPrefWidth() - paddingLeft - paddingRight);
        rangeSlider.setLayoutX(paddingLeft);

        rangeSlider.setOnValueChanged(event -> {
            draw();
        });

        // mouse move
        activeYLine = new Line();
        activeYLine.getStyleClass().add("active-line");
        activeYLine.setStartY(0);
        activeYLine.setEndY(height);
        shapePane.getChildren().add(activeYLine);

        activeXLabel = new Label();
        activeXLabel.getStyleClass().add("active-label");
        activeXLabel.setPrefSize(activeLabelWidth, 30);
        activeXLabel.setLayoutY(getPrefHeight() - paddingBottom - buttonPaneHeight);
        activeXLabel.setLayoutX(getPrefWidth() + 100);

        dataLabelsBox = new VBox();
        dataLabelsBox.getStyleClass().add("data-vbox");
        dataLabelsBox.setPadding(new Insets(10));
        dataLabelsBox.setSpacing(3);
        dataLabelsBox.setLayoutX(getPrefWidth() + 100);
        shapePane.getChildren().add(dataLabelsBox);

        shapePane.setOnMouseEntered(event -> {
            shapeOnMouseEntered(event);
        });
        shapePane.setOnMouseMoved(event -> {
            shapeOnMouseMoved(event);
        });
        shapePane.setOnMouseExited(event -> {
            shapeOnMouseExited(event);
        });

        this.getChildren().addAll(xLabelPane, yLabelPane, buttonPane, shapePane, rangeSlider, activeXLabel);

        drawAxis();

        start = 0;
        end = 1;

        setMinRange(0);
        setMaxRange(1);
    }


    public void setStartAndEnd(double start, double end) {
        if (0 <= start && start <= 1 && 0 <= end && end <= 1 && start < end) {
            this.start = start;
            this.end = end;
//            reloadData();
            draw();
        }
    }

    // 设置横坐标
    public void setKeys(List<String> keys) {
        this.keys = keys;
        this.keyCount = keys.size();

        clearData();
    }

    public void addData(List<Integer> data, String name) {

        if (data.size() != keyCount) return;

        int index = datas.size();
        String color = colors[index % colors.length];

        if (keyCount == 1) {
            Circle circle;
            if (circles.size() > index) {
                circle = circles.get(index);
            } else {
                circle = new Circle(5.0f);
                circles.add(circle);
            }
            circle.setFill(Color.web(color));
            polylinePane.getChildren().add(circle);

        } else {

            Polyline polyline;
            if (polylines.size() > index) {
                polyline = polylines.get(index);
            } else {
                polyline = new Polyline();
                polyline.getStyleClass().addAll("line");

                polylines.add(polyline);
            }
            polyline.setStroke(Color.web(color));
            polylinePane.getChildren().add(polyline);
        }

        lineNames.add(name);
        datas.add(data);

        DotButton dotButton;
        if (dotButtons.size() > index) {
            dotButton = dotButtons.get(index);
        } else {
            dotButton = new DotButton();
            dotButtons.add(dotButton);
        }
        dotButton.setPrefWidth(80);
        dotButton.setText(name);
        dotButton.setColor(color);
        dotButton.setOnAction(event -> {
            boolean active = !dotButton.getActive();
            dotButton.setActive(active);
            if (keyCount == 1) {
                circles.get(index).setVisible(active);
            } else {
                polylines.get(index).setVisible(active);
            }
            Node node = dataLabelsBox.getChildren().get(index);
            node.setManaged(active);
            node.setVisible(active);

        });
        buttonPane.getChildren().add(dotButton);

        Label dataLabel;
        if (dateLabels.size() > index) {
            dataLabel = dateLabels.get(index);
        } else {
            dataLabel = new Label();
            dateLabels.add(dataLabel);
        }
        dataLabel.getStyleClass().add("data-label");
        Label colorLabel = new Label();
        colorLabel.getStyleClass().add("data");
        colorLabel.setText(name);
        colorLabel.setPrefWidth(40);
        colorLabel.setTextFill(Color.web(color));

        dataLabel.setGraphic(colorLabel);
        dataLabelsBox.getChildren().add(dataLabel);
    }

    public void clearData() {
        datas.clear();
        lineNames.clear();

        polylinePane.getChildren().clear();

        buttonPane.getChildren().clear();
        dataLabelsBox.getChildren().clear();
    }

    public void reloadData() {
//        yIntervalCount = 10;
        calculateMaxValue();
        setYLabels();
        draw();
    }

    private void calculateMaxValue() {
        maxValue = 0;
        for (List<Integer> data : datas) {
            int tmpMax = maxInList(data);
            maxValue = tmpMax > maxValue ? tmpMax : maxValue;
        }

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

        double height = shapePane.getPrefHeight();
        double width = shapePane.getPrefWidth();

        double intervalHeight = height / tick;
        for (int j = 0; j <= tick; j++) {
            Line line = new Line();
            line.getStyleClass().add("y-line");
            yLinesPane.getChildren().add(line);
            line.setStartX(0);
            line.setStartY(intervalHeight * j);
            line.setEndX(width);
            line.setEndY(intervalHeight * j);

            Label label = new Label();
            label.getStyleClass().add("y-label");
            yLabelPane.getChildren().add(label);
            label.setText(interval * j + "");
            label.setLayoutY(height - intervalHeight * j - 10);
            label.setPrefSize(paddingLeft - 5, 20);
            label.setAlignment(Pos.CENTER_RIGHT);
        }

    }

    private void draw() {
        if (keyCount == 1) drawPoint();
        else drawLines();
    }

    private void drawPoint() {
        if (keyCount != 1 || datas.size() <= 0 || maxValue == null) return;
        double height = shapePane.getPrefHeight();
        double width = shapePane.getPrefWidth();
        double x = width / 2;

        //x
        Line line;
        if (xLinesPane.getChildren().size() > 0) {
            line = (Line) xLinesPane.getChildren().get(0);
        } else if (xLines.size() > 0) {
            line = xLines.get(0);
            xLinesPane.getChildren().add(line);
        } else {
            line = new Line();
            line.getStyleClass().add("x-line");
            xLinesPane.getChildren().add(line);
            xLines.add(line);
        }
        line.setStartX(x);
        line.setStartY(height);
        line.setEndX(x);
        line.setEndY(0.0f);

        Label label;
        if (xLabelPane.getChildren().size() > 0) {
            label = (Label) xLabelPane.getChildren().get(0);
        } else if (xLabels.size() > 0) {
            label = xLabels.get(0);
            xLabelPane.getChildren().add(label);
        } else {
            label = new Label();
            label.setPrefSize(minXLabelWidth, 20);
            label.setAlignment(Pos.TOP_CENTER);
            label.getStyleClass().add("x-label");
            xLabelPane.getChildren().add(label);
            xLabels.add(label);
        }
        label.setLayoutX(x - minXLabelWidth / 2);
        label.setText(keys.get(0));
        xLinesPane.getChildren().removeAll(xLines.subList(1, xLines.size()));
        xLabelPane.getChildren().removeAll(xLabels.subList(1, xLabels.size()));

        for (int i = 0; i < datas.size(); i++) {
            Circle circle = circles.get(i);
            List<Integer> data = datas.get(i);
            circle.setCenterX(x);
            circle.setCenterY(height - height * ((double) data.get(0) / maxValue));

        }
    }


    // core
    private void drawLines() {
        if (datas.size() <= 0 || keyCount == 0 || maxValue == null) return;
        double height = shapePane.getPrefHeight();
        double width = shapePane.getPrefWidth();

        double min = rangeSlider.getMinValue();
        double max = rangeSlider.getMaxValue();

        if (min == max) return;

        double totalWidth = (width - paddingRight) / (max - min);
        double startLeft = totalWidth * start;
        double intervalWidth = totalWidth * (end - start) / (keyCount - 1);

        double leftX = totalWidth * min - startLeft;
        double rightX = totalWidth * max - startLeft;
        int leftIndex = (int) (leftX / intervalWidth);
        int rightIndex = (int) (rightX / intervalWidth);
        // fix
        if (rightIndex < keyCount - 1) rightIndex++;
        if (rightIndex < keyCount - 1) rightIndex++;
        if (leftIndex < 0) leftIndex = 0;
        if (rightIndex > keyCount - 1) rightIndex = keyCount - 1;

        if (rightIndex < leftIndex) rightIndex = leftIndex;

//        if (intervalWidth < 1) return;

        int intervalX = (int)(minXLabelWidth / intervalWidth) + 1;

        // x
        int t = 0;
        for (int j = leftIndex; j <= rightIndex; j += intervalX) {
            Line line;
            if (xLinesPane.getChildren().size() > t) {
                line = (Line) xLinesPane.getChildren().get(t);
            } else if (xLines.size() > t) {
                line = xLines.get(t);
                xLinesPane.getChildren().add(line);
            } else {
                line = new Line();
                line.getStyleClass().add("x-line");
                xLinesPane.getChildren().add(line);
                xLines.add(line);
            }

            double x = intervalWidth * j - leftX;
            line.setStartX(x);
            line.setStartY(height);
            line.setEndX(x);
            line.setEndY(0.0f);

            Label label;
            if (xLabelPane.getChildren().size() > t) {
                label = (Label) xLabelPane.getChildren().get(t);
            } else if (xLabels.size() > t) {
                label = xLabels.get(t);
                xLabelPane.getChildren().add(label);
            } else {
                label = new Label();
                label.setPrefSize(minXLabelWidth, 20);
                label.setAlignment(Pos.TOP_CENTER);
                label.getStyleClass().add("x-label");
                xLabelPane.getChildren().add(label);
                xLabels.add(label);
            }
            label.setLayoutX(x - minXLabelWidth / 2);
            label.setText(keys.get(j));
            t++;
        }
        xLinesPane.getChildren().removeAll(xLines.subList(t, xLines.size()));
        xLabelPane.getChildren().removeAll(xLabels.subList(t, xLabels.size()));

        // line


        for (int i = 0; i < datas.size(); i++) {
            Polyline polyline = polylines.get(i);
            List<Integer> data = datas.get(i);

            Double[] doubles = new Double[(rightIndex - leftIndex + 1) * 2];
            int p = 0;
            for (int j = leftIndex; j <= rightIndex; j++) {
                double x = intervalWidth * j - leftX;
                doubles[p++] = x;
                doubles[p++] = height - height * ((double) data.get(j) / maxValue);
            }

            polyline.getPoints().setAll(doubles);
        }
    }

    private void shapeOnMouseMoved(MouseEvent event) {
        if (keyCount == 0) return;
        double offsetX = event.getX();
        double offsetY = event.getY();
        //
        double width = shapePane.getPrefWidth();
        double min = rangeSlider.getMinValue();
        double max = rangeSlider.getMaxValue();

        if (min == max) return;

        double resultX;
        int index;
        double totalWidth = (width - paddingRight) / (max - min);

        if (keyCount > 1) {
            double startLeft = totalWidth * start;
            double intervalWidth = totalWidth * (end - start) / (keyCount - 1);
            double leftX = totalWidth * min - startLeft;

            double absoluteX = leftX + offsetX;
            index = Math.toIntExact(Math.round(absoluteX / intervalWidth));
            if (index < 0) index = 0;
            if (index > keyCount - 1) index = keyCount - 1;
            resultX = index * intervalWidth - leftX;
        } else {
            resultX = width / 2;
            index = 0;
        }

        if (resultX < 0) return;

        activeYLine.setStartX(resultX);
        activeYLine.setEndX(resultX);
        activeXLabel.setText(keys.get(index));
        activeXLabel.setLayoutX(resultX + paddingLeft - activeLabelWidth / 2);

        for (int i = 0; i < datas.size(); i++) {
//            if (i < 0 || i > keyCount-1) continue;
            Label label = (Label) dataLabelsBox.getChildren().get(i);
            label.setText(datas.get(i).get(index) + "");
        }

        dataLabelsBox.setLayoutX(resultX <= totalWidth / 2 ? resultX + 7 : resultX - dataLabelsBox.getWidth() - 7);
        dataLabelsBox.setLayoutY(offsetY);
    }

    private void shapeOnMouseEntered(MouseEvent event) {
        activeYLine.setVisible(true);
        activeXLabel.setVisible(true);
        dataLabelsBox.setVisible(true);
    }

    private void shapeOnMouseExited(MouseEvent event) {
        activeYLine.setVisible(false);
        activeXLabel.setVisible(false);
        dataLabelsBox.setVisible(false);
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

    public final ObjectProperty<EventHandler<Event>> onValueChangedProperty() {
        return rangeSlider.onValueChangedProperty();
    }

    public final void setOnValueChanged(EventHandler<Event> handler) {

        rangeSlider.setOnValueChanged(event -> {

            handler.handle(event);
            draw();
        });
    }

    public final EventHandler<Event> getOnValueChanged() {
        return rangeSlider.getOnValueChanged();
    }

    public final DoubleProperty minRangeProperty() {
        return rangeSlider.minValueProperty();
    }

    public final double getMinRange() {
        return rangeSlider.getMinValue();
    }

    public final void setMinRange(double minRange) {
        if (minRange > 1 || minRange < 0) return;
        rangeSlider.setMinValue(minRange);
        draw();
    }

    public final DoubleProperty maxRangeProperty() {
        return rangeSlider.maxValueProperty();
    }

    public final double getMaxRange() {
        return rangeSlider.getMaxValue();
    }

    public final void setMaxRange(double maxRange) {
        if (maxRange > 1 || maxRange < 0) return;
        rangeSlider.setMaxValue(maxRange);
        draw();
    }
}