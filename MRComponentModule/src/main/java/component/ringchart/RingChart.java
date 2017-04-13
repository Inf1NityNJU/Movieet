package component.ringchart;

import component.dotbutton.DotButton;
import component.scatterchart.PointData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sorumi on 17/4/13.
 */
public class RingChart extends Pane {

    private static final int paddingLeftRight = 50;

    private TilePane dotButtonPane;

    private Pane shapePane;

    private List<Shape> ringArcs = new ArrayList();

    private Label nameLabel;
    private Label valueLabel;

    private List<NameData> data;
    private Integer totalValue;

    private static final String[] colors = {
            "#E3645A", "#F48984", "#FDB8A1", "#F7CC9B",
            "#F8D76E", "#FEE9A5", "#F0E0BC", "#D1CCC6",
            "#B6D7B3", "#BEE1DA", "#A7DAD8", "#92BCC3",
            "#93A9BD", "#B9CDDC", "#BABBDE", "#928BA9",
            "#CA9ECE", "#EFCEED", "#FECEDC", "#FAA5B3"
    };

    private double radius = 100;
    private double innerRatio = 0.6;

    public void init() {
        String css = getClass().getResource("/main/Chart.css").toExternalForm();
        getStylesheets().add(css);

        this.getStyleClass().add("root");

        // tile pane
        dotButtonPane = new TilePane();
        dotButtonPane.setPrefSize(getPrefWidth() - paddingLeftRight *2, USE_COMPUTED_SIZE);
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

        // shape pane
        shapePane = new Pane();
        shapePane.setPrefSize(getPrefWidth(), getPrefHeight());
        shapePane.getStyleClass().add("shape-pane");

        //label
        double labelWidth = radius * innerRatio * 2;
        double labelHeight = 30;

        nameLabel = new Label();
        nameLabel.getStyleClass().add("name-label");
        nameLabel.setPrefSize(labelWidth, labelHeight);
        nameLabel.setLayoutX(centerX - labelWidth / 2);
        nameLabel.setLayoutY(centerY - labelHeight);
        nameLabel.setVisible(false);

        valueLabel = new Label();
        valueLabel.getStyleClass().add("value-label");
        valueLabel.setPrefSize(labelWidth, labelHeight);
        valueLabel.setLayoutX(centerX - labelWidth / 2);
        valueLabel.setLayoutY(centerY);
        valueLabel.setVisible(false);

        this.getChildren().addAll(shapePane, nameLabel, valueLabel, dotButtonPane);
    }


    public void setData(List<NameData> nameData) {
        this.data = nameData;
        totalValue = totalInList(data);
    }

    public void reloadData() {
        if (data == null) return;
//        calculateXMaxValue();
//        setXLabels();
//        setYLabels();
        draw();
    }

    public void draw() {
        shapePane.getChildren().clear();
        ringArcs.clear();

        double height = shapePane.getPrefHeight();
        double width = shapePane.getPrefWidth();

        double centerX = width / 2;
        double centerY = height / 2;
        double innerRadius = radius * innerRatio;

        double startAngle = 0;

        for (int i = 0; i < data.size(); i++) {
            NameData nameData = data.get(i);
            double length = ((double) nameData.value) / totalValue * 360;
            String color = colors[i % colors.length];
            Arc outterArc = new Arc(centerX, centerY, radius, radius, startAngle, length);
            outterArc.setType(ArcType.ROUND);

            Arc innerArc = new Arc(centerX, centerY, innerRadius, innerRadius, startAngle, length);
            innerArc.setType(ArcType.ROUND);

            Shape ringArc = Path.subtract(outterArc, innerArc);
            ringArc.setFill(Color.web(color));
            ringArc.setStroke(Color.WHITE);
            ringArc.setStrokeWidth(2);

            ringArc.setOnMouseEntered(event -> {
               showNameDate(nameData);
            });
            ringArc.setOnMouseExited(event -> {
               hideNameData(nameData);
            });

            shapePane.getChildren().add(ringArc);
            ringArcs.add(ringArc);

            // dot button
            DotButton dotButton = new DotButton();
            dotButton.setText(nameData.name);
            dotButton.setColor(color);

            dotButton.setOnMouseEntered(event -> {
                showNameDate(nameData);
            });
            dotButton.setOnMouseExited(event -> {
                hideNameData(nameData);
            });
            dotButtonPane.getChildren().add(dotButton);

            startAngle += length;
        }

    }

    private void showNameDate(NameData nameData) {
        int index = data.indexOf(nameData);
        ringArcs.get(index).setOpacity(0.9);
        nameLabel.setText(nameData.name);
        nameLabel.setTextFill(Color.web(colors[index % colors.length]));
        nameLabel.setVisible(true);
        valueLabel.setText(nameData.value + "");
        valueLabel.setVisible(true);
    }

    private void hideNameData(NameData nameData) {
        int index = data.indexOf(nameData);
        ringArcs.get(index).setOpacity(1);
        nameLabel.setVisible(false);
        valueLabel.setVisible(false);
    }

    private Integer totalInList(List<NameData> nameDatas) {
        if (nameDatas == null || nameDatas.size() == 0) return 0;
        Integer total = 0;
        for (NameData nameData : nameDatas) {
            total += nameData.value;
        }
        return total;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getInnerRatio() {
        return innerRatio;
    }

    public void setInnerRatio(double innerRatio) {
        this.innerRatio = innerRatio;
    }
}
