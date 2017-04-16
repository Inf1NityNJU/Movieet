package component.spinner;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.util.Duration;

/**
 * Created by Sorumi on 17/3/17.
 */
public class Spinner extends Arc {

    private Timeline timeline;

    private double radius = 50;
    private double lineWidth = 10;
    private String color = "#6ED3D8";


    public Spinner() {
        this.setRadiusX(radius);
        this.setRadiusY(radius);
        this.setStartAngle(90);
        this.setLength(0);
        this.setFill(Color.TRANSPARENT);
        this.setStrokeWidth(lineWidth);
        this.setStroke(Color.web(color));
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(1000), new KeyValue(startAngleProperty(), -270)),
                new KeyFrame(Duration.millis(1000), new KeyValue(lengthProperty(), 180)),
                new KeyFrame(Duration.millis(2000), new KeyValue(startAngleProperty(), -450)),
                new KeyFrame(Duration.millis(2000), new KeyValue(lengthProperty(), 0)),
                new KeyFrame(Duration.millis(3000), new KeyValue(startAngleProperty(), -810)),
                new KeyFrame(Duration.millis(3000), new KeyValue(lengthProperty(), 180)),
                new KeyFrame(Duration.millis(4000), new KeyValue(startAngleProperty(), -990)),
                new KeyFrame(Duration.millis(4000), new KeyValue(lengthProperty(), 0))
        );
    }


    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        this.setStroke(Color.web(color));
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        this.setRadiusX(radius);
        this.setRadiusY(radius);
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
        this.setStrokeWidth(lineWidth);
    }
}
