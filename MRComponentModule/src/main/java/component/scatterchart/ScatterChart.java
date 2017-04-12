package component.scatterchart;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
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

    private List<PointData> data = new ArrayList<>();

    private Integer xMaxValue;
    private int xTick;

    private Integer yMaxValue = 10;
    private int yTick = 10;

    private String color = "#6ED3D8";
    private double circleWidth = 10;

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

        //
        canvas = new Canvas(width, height);
        shapePane.getChildren().add(canvas);

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
            double x = (double)point.x / xMaxValue * width;
            double y = height - point.y / yMaxValue * height;
            gc.fillOval(x, y, circleWidth, circleWidth);
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

    private void  setXLabels() {
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

    public double getCircleWidth() {
        return circleWidth;
    }

    public void setCircleWidth(double circleWidth) {
        this.circleWidth = circleWidth;
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
