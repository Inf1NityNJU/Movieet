package component.boxplotchart;

import component.scatterchart.PointData;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
 * Created by Sorumi on 17/4/16.
 */
public class BoxPlotChart extends Pane {
    private static final int paddingLeft = 50;
    private static final int paddingRight = 30;
    private static final int paddingTop = 30;
    private static final int paddingBottom = 40;

    private static final int chartPaddingTop = 20;
    private static final int chartPaddingLeftAndRight = 30;

    private Pane shapePane;
    private Pane yLinesPane;
    private Pane yLabelPane;
    private Canvas canvas;

    private Line activeYLine;
    private VBox dataLabelsBox;
    private Label activeYLabel;

    private Integer min;
    private Integer max;
    private List<Double> quartiles;
    private List<Double> outliers;
    private List<Double> quartileYs;
    private List<Double> outlierYs;

//    private int
    private int tick;

    private int circleRadius = 3;
    private int lineWidth = 2;
    private String color = "#6ED3D8";

    public void init() {
        String css = getClass().getResource("/main/Chart.css").toExternalForm();
        getStylesheets().add(css);

        this.getStyleClass().add("root");

        Rectangle clip = new Rectangle(getPrefWidth(), getPrefHeight());
        setClip(clip);

        yLabelPane = new Pane();
        yLabelPane.setPrefSize(paddingLeft, getPrefHeight());
        yLabelPane.setLayoutY(paddingTop);

        // shape pane
        shapePane = new Pane();
        shapePane.setPrefSize(getPrefWidth() - paddingLeft - paddingRight, getPrefHeight() - paddingTop - paddingBottom);
        shapePane.setLayoutX(paddingLeft);
        shapePane.setLayoutY(paddingTop);
        shapePane.getStyleClass().add("shape-pane");

        double width = shapePane.getPrefWidth();
        double height = shapePane.getPrefHeight();


        yLinesPane = new Pane();
        yLinesPane.setPrefSize(width, height);
        Rectangle clipX = new Rectangle(width, height);
        yLinesPane.setClip(clipX);
        shapePane.getChildren().add(yLinesPane);

        // canvas
        canvas = new Canvas(width, height);
        shapePane.getChildren().add(canvas);

        // active box
        activeYLine = new Line();
        activeYLine.getStyleClass().add("active-line");
        activeYLine.setStartX(0);
        activeYLine.setEndX(width);

        activeYLabel = new Label();
        activeYLabel.getStyleClass().add("data-label");

        dataLabelsBox = new VBox();
        dataLabelsBox.getStyleClass().add("data-vbox");
        dataLabelsBox.setPadding(new Insets(10));
        dataLabelsBox.setSpacing(3);
        dataLabelsBox.setLayoutX(getPrefWidth() + 100);
        dataLabelsBox.getChildren().addAll(activeYLabel);

        shapePane.getChildren().addAll(activeYLine, dataLabelsBox);


        shapePane.setOnMouseEntered(event -> {
            shapeOnMouseEntered(event);
        });
        shapePane.setOnMouseMoved(event -> {
            shapeOnMouseMoved(event);
        });
        shapePane.setOnMouseExited(event -> {
            shapeOnMouseExited(event);
        });


        this.getChildren().addAll(yLabelPane, shapePane);

        drawAxis();
    }

    public void setData(Integer min, Integer max, List<Double> quartiles, List<Double> outliers) {
        if (quartiles.size() != 5) return;
        this.min = min;
        this.max = max;
        this.quartiles = quartiles;
        this.outliers = outliers;
    }

    public void reloadData() {
        calculateTick();
        setYLabels();
        draw();
    }

    private void setYLabels() {
        yLinesPane.getChildren().clear();
        yLabelPane.getChildren().clear();

        int interval = (max - min) / tick;

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
            label.setText(min + interval * j + "");
            label.setLayoutY(height + chartPaddingTop - intervalHeight * j - 10);
            label.setPrefSize(paddingLeft - 5, 20);
            label.setAlignment(Pos.CENTER_RIGHT);
        }

    }

    private void draw() {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = shapePane.getPrefWidth();
        double height = shapePane.getPrefHeight() - chartPaddingTop;
        gc.clearRect(0, 0, width, height);

        gc.setLineWidth(lineWidth);
        gc.setFill(Color.web(color));
        gc.setStroke(Color.web(color));

        quartileYs = new ArrayList<>();
        for (Double quartile : quartiles) {
            double y = chartPaddingTop + height - (quartile - min) / (max - min) * height;
            gc.strokeLine(chartPaddingLeftAndRight, y, width - chartPaddingLeftAndRight, y);
            quartileYs.add(y);
        }

        gc.strokeLine(chartPaddingLeftAndRight, quartileYs.get(1), chartPaddingLeftAndRight, quartileYs.get(3));
        gc.strokeLine(width - chartPaddingLeftAndRight, quartileYs.get(1), width - chartPaddingLeftAndRight, quartileYs.get(3));
        gc.strokeLine(width / 2, quartileYs.get(0), width / 2, quartileYs.get(1));
        gc.strokeLine(width / 2, quartileYs.get(3), width / 2, quartileYs.get(4));

        if (outliers != null) {
            outlierYs = new ArrayList<>();
            double x = width / 2;
            for (Double outlier : outliers) {
                double y = chartPaddingTop + height - (outlier - min) / (max - min) * height;
                gc.fillOval(x - circleRadius, y - circleRadius, circleRadius * 2, circleRadius * 2);
                outlierYs.add(y);
            }
        } else {
            outlierYs = null;
        }
    }


    private void shapeOnMouseMoved(MouseEvent event) {
        if (quartiles == null) return;
        double offsetX = event.getX();
        double offsetY = event.getY();


        double minDistance = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < quartileYs.size(); i++) {
            double distance = offsetY - quartileYs.get(i);
            distance = Math.abs(distance);
            if (distance < minDistance) {
                minDistance = distance;
                index = i;
//                y = quartileYs.get(i);
            }
        }

        if (outlierYs != null) {
            for (int i = 0; i < outlierYs.size(); i++) {
                double distance = offsetY - outlierYs.get(i);
                distance = Math.abs(distance);
                if (distance < minDistance) {
                    minDistance = distance;
//                    y = outlierYs.get(i);
                    index = i + 5;
                }
            }
        }

        double y = index < 5 ? quartileYs.get(index) : outlierYs.get(index - 5);
        double value = index < 5 ? quartiles.get(index) : outliers.get(index - 5);
        String text = "";

        switch (index) {
            case 0:
                text = "minimum";
                break;
            case 1:
                text = "first quartile";
                break;
            case 2:
                text = "median";
                break;
            case 3:
                text = "third quartile";
                break;
            case 4:
                text = "maximum";
                break;
            default:
                text = "outlier";
        }
        activeYLabel.setText(text + ": " + value);

        double height = shapePane.getPrefHeight();
        double boxX = offsetX < shapePane.getPrefWidth() / 2 ? offsetX + 5 : offsetX - dataLabelsBox.getWidth() - 5;
        double boxY = y < shapePane.getPrefHeight() / 2 ? y + 5 : y - dataLabelsBox.getHeight() - 5;
        Timeline timeline = new Timeline();

        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(300), new KeyValue(activeYLine.startYProperty(), y)),
                new KeyFrame(Duration.millis(300), new KeyValue(activeYLine.endYProperty(), y)),
                new KeyFrame(Duration.millis(300), new KeyValue(dataLabelsBox.layoutXProperty(), boxX)),
                new KeyFrame(Duration.millis(300), new KeyValue(dataLabelsBox.layoutYProperty(), boxY))
        );
        timeline.play();
    }

    private void shapeOnMouseEntered(MouseEvent event) {
        double offsetX = event.getX();
        double offsetY = event.getY();

        dataLabelsBox.setVisible(true);
        activeYLine.setVisible(true);

        activeYLine.setStartY(offsetY);
        activeYLine.setEndY(offsetY);
        dataLabelsBox.setLayoutX(offsetX);
        dataLabelsBox.setLayoutY(offsetY);

        shapeOnMouseMoved(event);

    }

    private void shapeOnMouseExited(MouseEvent event) {
        dataLabelsBox.setVisible(false);
        activeYLine.setVisible(false);
    }

    private void calculateTick() {
        if ((max - min) < 10) {
            tick = max - min;
        } else {
            ChartScale chartScale = new ChartScale(min, max);
            chartScale.setMaxTicks(10);
            min = (int) chartScale.getNiceMin();
            max = (int) chartScale.getNiceMax();
            tick = (int) ((max - min) / chartScale.getTickSpacing());

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        draw();
    }
}
