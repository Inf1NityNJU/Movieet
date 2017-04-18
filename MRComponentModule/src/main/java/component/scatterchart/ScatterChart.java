package component.scatterchart;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import util.ChartScale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sorumi on 17/4/12.
 */
public class ScatterChart extends Pane {

    private static final int paddingLeft = 50;
    private static final int paddingRight = 30;
    private static final int paddingTop = 30;
    private static final int paddingBottom = 40;

    private static final int minXLabelWidth = 90;

    private Pane shapePane;
    private Pane yLinesPane;
    private Pane xLinesPane;
    private Pane xLabelPane;
    private Pane yLabelPane;
    private Canvas canvas;

    private Line activeYLine;
    private Line activeXLine;
    private VBox dataLabelsBox;
    private Label dataNameLabel;
    private Label xNameLabel;
    private Label yNameLabel;
    private PointData activeData = null;

    private List<PointData> data = new ArrayList<>();

    private Integer xMaxValue;
    private int xTick;

    private Integer yMaxValue = 10;
    private int yTick = 10;

    private String xName = "";
    private String yName = "";
    private String color = "#6ED3D8BB";
    private double circleRadius = 10;

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
        shapePane.setPrefSize(getPrefWidth() - paddingLeft - paddingRight, getPrefHeight() - paddingTop - paddingBottom);
        shapePane.setLayoutX(paddingLeft);
        shapePane.setLayoutY(paddingTop);
        shapePane.getStyleClass().add("shape-pane");

        // line pane
        double width = shapePane.getPrefWidth();
        double height = shapePane.getPrefHeight();

        xLinesPane = new Pane();
        xLinesPane.setPrefSize(width, height);
        shapePane.getChildren().add(xLinesPane);

        yLinesPane = new Pane();
        yLinesPane.setPrefSize(width, height);
        shapePane.getChildren().add(yLinesPane);

        // canvas
        canvas = new Canvas(width, height);
        shapePane.getChildren().add(canvas);

        // active box
        activeYLine = new Line();
        activeYLine.getStyleClass().add("active-line");
        activeYLine.setStartY(0);
        activeYLine.setEndY(height);

        activeXLine = new Line();
        activeXLine.getStyleClass().add("active-line");
        activeXLine.setStartX(0);
        activeXLine.setEndX(width);

        dataLabelsBox = new VBox();
        dataLabelsBox.getStyleClass().add("data-vbox");
        dataLabelsBox.setPadding(new Insets(10));
        dataLabelsBox.setSpacing(3);
        dataLabelsBox.setLayoutX(getPrefWidth() + 100);
        shapePane.getChildren().addAll(activeXLine, activeYLine, dataLabelsBox);

        dataNameLabel = new Label();
        dataNameLabel.getStyleClass().add("data-label");
        xNameLabel = new Label();
        xNameLabel.getStyleClass().add("data-label");
        yNameLabel = new Label();
        yNameLabel.getStyleClass().add("data-label");

        dataLabelsBox.getChildren().addAll(dataNameLabel, xNameLabel, yNameLabel);

        shapePane.setOnMouseEntered(event -> {
            shapeOnMouseEntered(event);
        });
        shapePane.setOnMouseMoved(event -> {
            shapeOnMouseMoved(event);
        });
        shapePane.setOnMouseExited(event -> {
            shapeOnMouseExited(event);
        });

        dataLabelsBox.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                moveToPointData(activeData);
            }
        });

        this.getChildren().addAll(xLabelPane, yLabelPane, shapePane);

        drawAxis();
    }

    public void setData(List<PointData> pointData) {
        this.data = pointData;
    }

    public void reloadData() {
        if (data == null) return;
        calculateXMaxValue();
        setXLabels();
        setYLabels();
        draw();
    }

    private void draw() {
        if (data == null) return;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = shapePane.getPrefWidth();
        double height = shapePane.getPrefHeight();
        gc.clearRect(0, 0, width, height);

        gc.setFill(Color.web(color));
        for (PointData point : data) {
            double x = (double) point.x / xMaxValue * width;
            double y = height - point.y / yMaxValue * height;
            gc.fillOval(x - circleRadius, y - circleRadius, circleRadius * 2, circleRadius * 2);
        }
    }

    private void setYLabels() {
        yLinesPane.getChildren().clear();
        yLabelPane.getChildren().clear();

        int interval = yMaxValue / yTick;

        double height = shapePane.getPrefHeight();
        double width = shapePane.getPrefWidth();

        double intervalHeight = height / yTick;
        for (int j = 0; j <= yTick; j++) {
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

    private void setXLabels() {
        xLinesPane.getChildren().clear();
        xLabelPane.getChildren().clear();

        int interval = xMaxValue / xTick;

        double height = shapePane.getPrefHeight();
        double width = shapePane.getPrefWidth();

        double intervalWidth = width / xTick;
        for (int j = 0; j <= xTick; j++) {
            double x = intervalWidth * j;
            Line line = new Line();
            line.getStyleClass().add("x-line");
            xLinesPane.getChildren().add(line);
            line.setStartX(x);
            line.setStartY(0);
            line.setEndX(x);
            line.setEndY(height);

            Label label = new Label();
            label.getStyleClass().add("x-label");
            xLabelPane.getChildren().add(label);
            label.setText(interval * j + "");
            label.setLayoutX(x - minXLabelWidth / 2);
            label.setPrefSize(minXLabelWidth, 20);
            label.setAlignment(Pos.CENTER);
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
        if (data == null) return;
        double offsetX = event.getX();
        double offsetY = event.getY();

        double width = shapePane.getPrefWidth();
        double height = shapePane.getPrefHeight();

        PointData activeData = null;
        double minDistance = Integer.MAX_VALUE;
        for (PointData pointData : data) {
            double x = (double) pointData.x / xMaxValue * width;
            double y = height - pointData.y / yMaxValue * height;

            double distance = Math.pow((offsetX - x), 2) + Math.pow((offsetY - y), 2);
            distance = Math.sqrt(distance);
            if (distance < minDistance) {
                minDistance = distance;
                activeData = pointData;
            }
        }
        if (activeData != null) {
            dataNameLabel.setText(activeData.name);
            xNameLabel.setText(xName + ": " + activeData.x);
            yNameLabel.setText(yName + ": " + activeData.y);
        }

        this.activeData = activeData;
        moveToPointData(activeData);

    }

    private void moveToPointData(PointData pointData) {
        if (pointData == null) return;

        double width = shapePane.getPrefWidth();
        double height = shapePane.getPrefHeight();

        double x = (double) pointData.x / xMaxValue * width;
        double y = height - pointData.y / yMaxValue * height;

        double boxX = x < width / 2 ? x + 5 : x - dataLabelsBox.getWidth() - 5;
        double boxY = y < height / 2 ? y + 5 : y - dataLabelsBox.getHeight() - 5;
        Timeline timeline = new Timeline();

        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(300), new KeyValue(activeYLine.startXProperty(), x)),
                new KeyFrame(Duration.millis(300), new KeyValue(activeYLine.endXProperty(), x)),
                new KeyFrame(Duration.millis(300), new KeyValue(activeXLine.startYProperty(), y)),
                new KeyFrame(Duration.millis(300), new KeyValue(activeXLine.endYProperty(), y)),
                new KeyFrame(Duration.millis(300), new KeyValue(dataLabelsBox.layoutXProperty(), boxX)),
                new KeyFrame(Duration.millis(300), new KeyValue(dataLabelsBox.layoutYProperty(), boxY))
        );
        timeline.play();
    }

    private void shapeOnMouseEntered(MouseEvent event) {
        double offsetX = event.getX();
        double offsetY = event.getY();

        dataLabelsBox.setVisible(true);
        activeXLine.setVisible(true);
        activeYLine.setVisible(true);

        activeXLine.setStartY(offsetY);
        activeXLine.setEndY(offsetY);
        activeYLine.setStartX(offsetX);
        activeYLine.setEndX(offsetX);
        dataLabelsBox.setLayoutX(offsetX);
        dataLabelsBox.setLayoutY(offsetY);


        shapeOnMouseMoved(event);
    }

    private void shapeOnMouseExited(MouseEvent event) {
        dataLabelsBox.setVisible(false);
        activeXLine.setVisible(false);
        activeYLine.setVisible(false);
    }

    private void calculateXMaxValue() {
        xMaxValue = xMaxInList(data);

        if (xMaxValue < 10) {
            xMaxValue = xMaxValue + 1;
            xTick = xMaxValue;
        } else {
            ChartScale chartScale = new ChartScale(0, xMaxValue);
            chartScale.setMaxTicks(10);
            xMaxValue = (int) chartScale.getNiceMax();
            xTick = (int) (chartScale.getNiceMax() / chartScale.getTickSpacing());
        }
    }

    private Integer xMaxInList(List<PointData> pointDatas) {
        if (pointDatas == null || pointDatas.size() == 0) return 0;
        Integer max = pointDatas.get(0).x;
        for (int i = 0; i < pointDatas.size(); i++) {
            max = pointDatas.get(i).x > max ? pointDatas.get(i).x : max;
        }
        return max;
    }

    public double getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(double circleRadius) {
        this.circleRadius = circleRadius;
        draw();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        draw();
    }

    public String getxName() {
        return xName;
    }

    public void setxName(String xName) {
        this.xName = xName;
    }

    public String getyName() {
        return yName;
    }

    public void setyName(String yName) {
        this.yName = yName;
    }
}
