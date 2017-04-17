package component.rangelinechart;

import component.dotbutton.DotButton;
import component.myrangeslider.MyRangeSlider;
import component.scatterchart.PointData;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.TilePane;
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
import javafx.util.Duration;
import util.ChartScale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sorumi on 17/3/4.
 */
public class RangeLineChart extends Pane {

    //    private static final String[] colors = {"#F6807A", "#F3B7C0", "#FAE5BA", "#6ED8CD", "#57BEDC", "#B799E6"};

    private static final int maxTick = 10;

    private static final int paddingLeft = 50;
    private static final int paddingTop = 30;
    private static final int paddingBottom = 40;

    private static final int chartPaddingTop = 20;
    private static final int chartPaddingRight = 50;

    private static final int minXLabelWidth = 90;
    private static final int activeLabelWidth = 90;

    private MyRangeSlider rangeSlider;

    private Pane shapePane;
    private Pane polylinePane;
    private Pane xLinesPane;
    private Pane yLinesPane;
    private Pane xLabelPane;
    private Pane yLabelPane;
    private TilePane dotButtonPane;

    private Line activeYLine;
    private Label activeXLabel;
    private VBox dataLabelsBox;

    private List<Line> xLines = new ArrayList<>();
    //    private List<Line> yLines = new ArrayList<>();
    private List<Label> xLabels = new ArrayList<>();
    //    private List<Label> yLabels = new ArrayList<>();
    private List<Label> dateLabels = new ArrayList<>();
    private List<DotButton> dotButtons = new ArrayList<>();

    private List<List<Double>> datas = new ArrayList<>();

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

    private int circleRadius = 4;
    private int lineWidth = 2;

    private String[] colors = {
            "#E3645A", "#F48984", "#FDB8A1", "#F7CC9B",
            "#F8D76E", "#FEE9A5", "#F0E0BC", "#D1CCC6",
            "#B6D7B3", "#BEE1DA", "#A7DAD8", "#92BCC3",
            "#93A9BD", "#B9CDDC", "#BABBDE", "#928BA9",
            "#CA9ECE", "#EFCEED", "#FECEDC", "#FAA5B3"
    };


    private double initHeight = 0;

    public void init() {
        String css = getClass().getResource("/main/Chart.css").toExternalForm();
        getStylesheets().add(css);

        this.getStyleClass().add("root");

        initHeight = getPrefHeight();

        Rectangle clip = new Rectangle(getPrefWidth(), getPrefHeight());
        setClip(clip);

        xLabelPane = new Pane();
        xLabelPane.setPrefSize(getPrefWidth() - paddingLeft, paddingBottom);
        xLabelPane.setLayoutX(paddingLeft);
        xLabelPane.setLayoutY(getPrefHeight() - paddingBottom  + 5);

        yLabelPane = new Pane();
        yLabelPane.setPrefSize(paddingLeft, getPrefHeight());
        yLabelPane.setLayoutY(paddingTop);

        dotButtonPane = new TilePane();
        dotButtonPane.setPrefSize(getPrefWidth() - paddingLeft , USE_COMPUTED_SIZE);
        dotButtonPane.setLayoutX(paddingLeft);
        dotButtonPane.setLayoutY(getPrefHeight());
        dotButtonPane.setVgap(10);
        dotButtonPane.setHgap(10);
        dotButtonPane.setPrefColumns(10);
        dotButtonPane.setTileAlignment(Pos.CENTER_LEFT);
        dotButtonPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setPrefSize(getPrefWidth(), initHeight + newValue.doubleValue());
                Rectangle clip = new Rectangle(getPrefWidth(), initHeight + newValue.doubleValue());
                setClip(clip);}
        });

        // shape pane
        shapePane = new Pane();
        shapePane.setPrefSize(getPrefWidth() - paddingLeft, getPrefHeight() - paddingTop - paddingBottom);
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

        // canvas


        rangeSlider = new MyRangeSlider();
        rangeSlider.setWidth(getPrefWidth() - paddingLeft - chartPaddingRight);
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
        activeXLabel.setLayoutY(getPrefHeight() - paddingBottom);
        activeXLabel.setLayoutX(getPrefWidth() + 100);

        dataLabelsBox = new VBox();
        dataLabelsBox.getStyleClass().add("data-vbox");
        dataLabelsBox.setPadding(new Insets(10));
        dataLabelsBox.setSpacing(3);
        dataLabelsBox.setLayoutX(getPrefWidth() + 100);

        shapePane.setOnMouseEntered(event -> {
            shapeOnMouseEntered(event);
        });
        shapePane.setOnMouseMoved(event -> {
            shapeOnMouseMoved(event);
        });
        shapePane.setOnMouseExited(event -> {
            shapeOnMouseExited(event);
        });

        this.getChildren().addAll(xLabelPane, yLabelPane, dotButtonPane, shapePane, rangeSlider, activeXLabel);

        drawAxis();

        shapePane.getChildren().add(dataLabelsBox);

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

    public void addIntegerData(List<Integer> data, String name) {

        List<Double> newData = new ArrayList<>(data.size());
        for (Integer integer : data) {
            newData.add(integer + 0.0);
        }
        addData(newData, name);
    }

    public void addData(List<Double> data, String name) {

        if (data.size() != keyCount) return;

        int index = datas.size();
        String color = colors[index % colors.length];

        Canvas canvas = new Canvas(shapePane.getPrefWidth(), shapePane.getPrefHeight());

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(lineWidth);
        gc.setFill(Color.web(color));
        gc.setStroke(Color.web(color));
        polylinePane.getChildren().add(canvas);
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
            polylinePane.getChildren().get(index).setVisible(active);

            Node node = dataLabelsBox.getChildren().get(index);
            node.setManaged(active);
            node.setVisible(active);

        });
        dotButtonPane.getChildren().add(dotButton);

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
        colorLabel.setMinWidth(40);
        colorLabel.setPrefWidth(minXLabelWidth);
        colorLabel.setTextFill(Color.web(color));

        dataLabel.setGraphic(colorLabel);
        dataLabelsBox.getChildren().add(dataLabel);
    }

    public void clearData() {
        datas.clear();
        lineNames.clear();

        polylinePane.getChildren().clear();

        dotButtonPane.getChildren().clear();
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
        double tmpMaxValue = 0.0;
        for (List<Double> data : datas) {
            double tmpMax = maxInList(data);

            tmpMaxValue = tmpMax > tmpMaxValue ? tmpMax : tmpMaxValue;
        }
        if (tmpMaxValue < 10) {
            maxValue = (int) Math.ceil(tmpMaxValue);
            tick = maxValue;
        } else {
            ChartScale chartScale = new ChartScale(0, tmpMaxValue);
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
            label.setLayoutY(chartPaddingTop + height - intervalHeight * j - 10);
            label.setPrefSize(paddingLeft - 5, 20);
            label.setAlignment(Pos.CENTER_RIGHT);
        }

    }

    // core
    private void draw() {
        if (datas.size() <= 0 || keyCount == 0 || maxValue == null || tick <= 0) return;
        double height = shapePane.getPrefHeight() - chartPaddingTop;
        double width = shapePane.getPrefWidth();

        double min = rangeSlider.getMinValue();
        double max = rangeSlider.getMaxValue();

        if (min == max) return;

        double totalWidth = (width - chartPaddingRight) / (max - min);
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

        int intervalX = (int) (minXLabelWidth / intervalWidth) + 1;

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
            line.setStartY(height + chartPaddingTop);
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
            Canvas canvas = (Canvas) polylinePane.getChildren().get(i);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, width, height + chartPaddingTop);

            List<Double> data = datas.get(i);
            Color color = Color.web(colors[i % colors.length]);
            gc.setFill(color);
            gc.setStroke(color);

            Double lastX = null;
            Double lastY = null;

            for (int j = leftIndex; j <= rightIndex; j++) {
                if (data.get(j) != null) {
                    double x = intervalWidth * j - leftX;
                    double y = chartPaddingTop + height - height * ((double) data.get(j) / maxValue);
                    gc.fillOval(x - circleRadius, y - circleRadius, circleRadius * 2, circleRadius * 2);
                    if (lastX != null) {
                        gc.strokeLine(lastX, lastY, x, y);
                    }
                    lastX = x;
                    lastY = y;
                } else {
                    lastX = null;
                    lastY = null;
                }
            }
        }
    }

    private void shapeOnMouseMoved(MouseEvent event) {
        if (keyCount == 0) return;
        double offsetX = event.getX();
        double offsetY = event.getY();
        //
        double width = shapePane.getPrefWidth();
        double height = shapePane.getPrefHeight();
        double min = rangeSlider.getMinValue();
        double max = rangeSlider.getMaxValue();

        if (min == max) return;

        double resultX;
        int index;
        double totalWidth = (width - chartPaddingRight) / (max - min);

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

//        activeYLine.setStartX(resultX);
//        activeYLine.setEndX(resultX);
        activeXLabel.setText(keys.get(index));
//        activeXLabel.setLayoutX(resultX + paddingLeft - activeLabelWidth / 2);

        for (int i = 0; i < datas.size(); i++) {
            Label label = (Label) dataLabelsBox.getChildren().get(i);
            Double data = datas.get(i).get(index);
            label.setText(data != null ? data + "" : "");
        }

        double boxX = resultX <= width / 2 ? resultX + 7 : resultX - dataLabelsBox.getWidth() - 7;
        double boxY = offsetY <= height / 2 ? offsetY + 7 : offsetY - dataLabelsBox.getHeight() - 7;
//        dataLabelsBox.setLayoutX();
        dataLabelsBox.setLayoutY(boxY);

        Timeline timeline = new Timeline();

        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(300), new KeyValue(activeYLine.startXProperty(), resultX)),
                new KeyFrame(Duration.millis(300), new KeyValue(activeYLine.endXProperty(), resultX)),
                new KeyFrame(Duration.millis(300), new KeyValue(activeXLabel.layoutXProperty(), resultX + paddingLeft - activeLabelWidth / 2)),
                new KeyFrame(Duration.millis(300), new KeyValue(dataLabelsBox.layoutXProperty(), boxX))
//                new KeyFrame(Duration.millis(300), new KeyValue(dataLabelsBox.layoutYProperty(), boxY))
        );
        timeline.play();


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

    private double maxInList(List<Double> nums) {
        if (nums.size() == 0) return 0;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < nums.size(); i++) {
            Double num = nums.get(i);
            if (num != null) {

                max = num > max ? num : max;
            }
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

    public int getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
        draw();
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        draw();
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }
}