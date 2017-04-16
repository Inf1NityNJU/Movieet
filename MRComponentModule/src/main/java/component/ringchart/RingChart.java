package component.ringchart;

import component.dotbutton.DotButton;
import component.scatterchart.PointData;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sorumi on 17/4/13.
 */
public class RingChart extends Pane {

    private static final int paddingLeftRight = 50;

    private TilePane dotButtonPane;

    private Pane shapePane;

    private List<Arc> ringArcs = new ArrayList();
    private Circle circle;

    private Label nameLabel;
    private Label valueLabel;
    private Label percentLabel;

    private List<NameData> data;
    private Integer totalValue;

    private static final String[] colors = {
            "#E3645A", "#F48984", "#FDB8A1", "#F7CC9B",
            "#F8D76E", "#FEE9A5", "#F0E0BC", "#D1CCC6",
            "#B6D7B3", "#BEE1DA", "#A7DAD8", "#92BCC3",
            "#93A9BD", "#B9CDDC", "#BABBDE", "#928BA9",
            "#CA9ECE", "#EFCEED", "#FECEDC", "#FAA5B3"
    };
    private String circleColor = "#FFFFFF";

    private double radius = 100;
    private double innerRatio = 0.6;

    public void init() {
        String css = getClass().getResource("/main/Chart.css").toExternalForm();
        getStylesheets().add(css);

        this.getStyleClass().add("root");

        // tile pane
        dotButtonPane = new TilePane();
        dotButtonPane.setPrefSize(getPrefWidth() - paddingLeftRight * 2, USE_COMPUTED_SIZE);
        dotButtonPane.setLayoutX(paddingLeftRight);
        dotButtonPane.setLayoutY(getPrefHeight());
        dotButtonPane.setVgap(10);
        dotButtonPane.setHgap(10);
        dotButtonPane.setPrefColumns(10);
        dotButtonPane.setTileAlignment(Pos.CENTER_LEFT);
        dotButtonPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setPrefSize(getPrefWidth(), shapePane.getPrefHeight() + newValue.doubleValue());
            }
        });

        double height = getPrefHeight();
        double width = getPrefWidth();

        double centerX = width / 2;
        double centerY = height / 2;
        double innerRadius = radius * innerRatio;

        // shape pane
        shapePane = new Pane();
        shapePane.setPrefSize(getPrefWidth(), getPrefHeight());
        shapePane.getStyleClass().add("shape-pane");

        //label
        double labelWidth = radius * innerRatio * 2;
        double labelHeight = 24;

        circle = new Circle(centerX, centerY, innerRadius);
        circle.setFill(Color.web(circleColor));
        shapePane.getChildren().add(circle);

        nameLabel = new Label();
        nameLabel.getStyleClass().add("name-label");
        nameLabel.setPrefSize(labelWidth, labelHeight);
        nameLabel.setLayoutX(centerX - labelWidth / 2);
        nameLabel.setLayoutY(centerY - labelHeight*3/2);
        nameLabel.setVisible(false);

        valueLabel = new Label();
        valueLabel.getStyleClass().add("value-label");
        valueLabel.setPrefSize(labelWidth, labelHeight);
        valueLabel.setLayoutX(centerX - labelWidth / 2);
        valueLabel.setLayoutY(centerY - labelHeight/2);
        valueLabel.setVisible(false);

        percentLabel = new Label();
        percentLabel.getStyleClass().add("percent-label");
        percentLabel.setPrefSize(labelWidth, labelHeight);
        percentLabel.setLayoutX(centerX - labelWidth / 2);
        percentLabel.setLayoutY(centerY + labelHeight/2);
        percentLabel.setVisible(false);

        this.getChildren().addAll(shapePane, circle, nameLabel, valueLabel, percentLabel, dotButtonPane);
    }

    public void setData(List<NameData> nameData) {
        this.data = nameData;

        double height = shapePane.getPrefHeight();
        double width = shapePane.getPrefWidth();

        double centerX = width / 2;
        double centerY = height / 2;

        ringArcs.clear();
        shapePane.getChildren().clear();
        dotButtonPane.getChildren().clear();

        for (int i = 0; i < nameData.size(); i++) {
            NameData data = nameData.get(i);
            String color = colors[i % colors.length];

            Arc arc = new Arc(centerX, centerY, radius, radius, 0, 0);
            arc.setType(ArcType.ROUND);
            arc.setFill(Color.web(color));
            arc.setStroke(Color.WHITE);
            arc.setStrokeWidth(2);

            shapePane.getChildren().add(arc);
            ringArcs.add(arc);

            arc.setOnMouseEntered(event -> {
                showNameDate(data);
            });
            arc.setOnMouseExited(event -> {
                hideNameData(data);
            });

            //doc button
            DotButton dotButton = new DotButton();
            dotButton.setText(data.name);
            dotButton.setColor(color);
            dotButton.setCursor(Cursor.HAND);

            dotButton.setOnAction(event -> {
                dotButton.setActive(!dotButton.getActive());
                draw();
                if (dotButton.getActive()) {
                    showNameDate(data);
                } else {
                    hideNameData(data);
                }
            });
            dotButton.setOnMouseEntered(event -> {
                if (dotButton.getActive()) {
                    showNameDate(data);
                }
            });
            dotButton.setOnMouseExited(event -> {
                hideNameData(data);
            });
            dotButtonPane.getChildren().add(dotButton);
        }


    }

    public void reloadData() {
        if (data == null) return;
        draw();
    }

    public void draw() {
        if (data == null) return;
        totalValue = totalInList(data);

        Timeline timeline = new Timeline();

        double startAngle = 0;

        for (int i = 0; i < data.size(); i++) {

            Arc arc = ringArcs.get(i);
            DotButton dotButton = (DotButton) dotButtonPane.getChildren().get(i);

            if (dotButton.getActive()) {
                NameData nameData = data.get(i);
                double length = ((double) nameData.value) / totalValue * 360;

                KeyFrame keyFrame1 = new KeyFrame(Duration.millis(300), new KeyValue(arc.startAngleProperty(), startAngle));
                KeyFrame keyFrame2 = new KeyFrame(Duration.millis(300), new KeyValue(arc.lengthProperty(), length));

                timeline.getKeyFrames().addAll(keyFrame1, keyFrame2);

                startAngle += length;

            } else {
                KeyFrame keyFrame1 = new KeyFrame(Duration.millis(300), new KeyValue(arc.startAngleProperty(), startAngle));
                KeyFrame keyFrame2 = new KeyFrame(Duration.millis(300), new KeyValue(arc.lengthProperty(), 0));
                timeline.getKeyFrames().addAll(keyFrame1, keyFrame2);
            }

        }
        timeline.play();

    }

    private void showNameDate(NameData nameData) {
        int index = data.indexOf(nameData);
        ringArcs.get(index).setOpacity(0.8);
        nameLabel.setText(nameData.name);
        nameLabel.setTextFill(Color.web(colors[index % colors.length]));
        nameLabel.setVisible(true);
        valueLabel.setText(nameData.value + "");
        valueLabel.setVisible(true);
        percentLabel.setText(String.format("%.2f", (double)nameData.value/totalValue*100) + " %");
        percentLabel.setVisible(true);
    }

    private void hideNameData(NameData nameData) {
        int index = data.indexOf(nameData);
        ringArcs.get(index).setOpacity(1);
        nameLabel.setVisible(false);
        valueLabel.setVisible(false);
        percentLabel.setVisible(false);
    }

    private Integer totalInList(List<NameData> nameDatas) {
        if (nameDatas == null || nameDatas.size() == 0) return 0;
        Integer total = 0;
        for (int i = 0; i < nameDatas.size(); i++) {
            NameData nameData = nameDatas.get(i);
            DotButton dotButton = (DotButton) dotButtonPane.getChildren().get(i);
            if (dotButton.getActive()) {
                total += nameData.value;
            }
        }
        return total;
    }

    public String getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(String circleColor) {
        this.circleColor = circleColor;
        circle.setFill(Color.web(circleColor));
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        for (Arc arc : ringArcs) {
            arc.setRadiusX(radius);
            arc.setRadiusY(radius);
        }
        circle.setRadius(innerRatio * radius);
    }

    public double getInnerRatio() {
        return innerRatio;
    }

    public void setInnerRatio(double innerRatio) {
        this.innerRatio = innerRatio;
        circle.setRadius(innerRatio * radius);
    }
}
