package component.myrangeslider;

import javafx.beans.property.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by Sorumi on 16/11/28.
 */
public class MyRangeSlider extends Pane {

    @FXML
    private Label leftThumb;

    @FXML
    private Label rightThumb;

    @FXML
    private Label leftLabel;

    @FXML
    private Label rightLabel;

    @FXML
    private Rectangle bgRect;

    @FXML
    private Rectangle activeRect;

    private DoubleProperty width;

    private DoubleProperty minValue = new SimpleDoubleProperty();
    private DoubleProperty maxValue = new SimpleDoubleProperty();

    private ObjectProperty<EventHandler<Event>> onValueChanging = new SimpleObjectProperty<EventHandler<Event>>();
    private ObjectProperty<EventHandler<Event>> onValueChanged = new SimpleObjectProperty<EventHandler<Event>>();

    private double orgSceneX;
    private double orgTranslateX;

    public MyRangeSlider() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyRangeSlider.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setLabel();

        EventHandler eventHandler = event -> {};

        setOnValueChanged(eventHandler);
        setOnValueChanging(eventHandler);
    }

    @FXML
    private void pressLeftThumb(MouseEvent event) {
        Label thumb = (Label) event.getSource();
        orgSceneX = event.getSceneX();
        orgTranslateX = thumb.getTranslateX();
    }

    @FXML
    private void dragLeftThumb(MouseEvent event) {
        Label thumb = (Label) event.getSource();

        double offsetX = event.getSceneX() - orgSceneX;
        double newTranslateX = orgTranslateX + offsetX;

        double minX = -10 - thumb.getLayoutX();
        double maxX = rightThumb.getLayoutX() + rightThumb.getTranslateX() - thumb.getLayoutX();

        if (newTranslateX < minX) {
            newTranslateX = minX;
        } else if (newTranslateX > maxX) {
            newTranslateX = maxX;
        }

        thumb.setTranslateX(newTranslateX);
        activeRect.setTranslateX(newTranslateX);
        activeRect.setWidth(250 - newTranslateX + rightThumb.getTranslateX());
        setLabel();

        onValueChangingProperty().getValue().handle(event);
    }

    @FXML
    private void releaseLeftThumb(MouseEvent event) {
        onValueChangedProperty().getValue().handle(event);
    }

    @FXML
    private void pressRightThumb(MouseEvent event) {
        Label thumb = (Label) event.getSource();
        orgSceneX = event.getSceneX();
        orgTranslateX = thumb.getTranslateX();
    }

    @FXML
    private void dragRightThumb(MouseEvent event) {
        Label thumb = (Label) event.getSource();

        double offsetX = event.getSceneX() - orgSceneX;
        double newTranslateX = orgTranslateX + offsetX;

        double minX = leftThumb.getLayoutX() + leftThumb.getTranslateX() - thumb.getLayoutX();
        double maxX = getPrefWidth() - 10 - thumb.getLayoutX();

        if (newTranslateX < minX) {
            newTranslateX = minX;
        } else if (newTranslateX > maxX) {
            newTranslateX = maxX;
        }
//        System.out.println(250+newTranslateX);

        thumb.setTranslateX(newTranslateX);
        activeRect.setWidth(250 - leftThumb.getTranslateX() + newTranslateX);
        setLabel();

        onValueChangingProperty().getValue().handle(event);
    }

    @FXML
    private void releaseRightThumb(MouseEvent event) {
        onValueChangedProperty().getValue().handle(event);
    }

    /******/

    public void setWidth(double width) {
        this.setPrefWidth(width);
        bgRect.setWidth(width);
        rightLabel.setLayoutX(width - 50);
    }

    private void setLabel() {
        double width = bgRect.getWidth();
        double leftX = (leftThumb.getLayoutX() + leftThumb.getTranslateX() + 10) / width;
        minValue.setValue(leftX);
        leftLabel.setText(String.format("%.2f", leftX));

        double rightX = (rightThumb.getLayoutX() + rightThumb.getTranslateX() + 10) / width;
        maxValue.setValue(rightX);
        rightLabel.setText(String.format("%.2f", rightX));
    }

//    public StringProperty prefixProperty() {
//        return prefix;
//    }
//
//    public String getPrefix() {
//        return prefix.get();
//    }
//
//    public void setPrefix(String value) {
//        prefix.set(value);
//        setLabel();
//    }

//    public IntegerProperty minRangeProperty() {
//        return minRange;
//    }
//
//    public Integer getMinRange() {
//        return minRange.getValue();
//    }
//
//    public void setMinRange(Integer value) {
//        minRange.setValue(value);
//        setLabel();
//    }
//
//    public IntegerProperty maxRangeProperty() {
//        return maxRange;
//    }
//
//    public Integer getMaxRange() {
//        return maxRange.getValue();
//    }
//
//    public void setMaxRange(Integer value) {
//        maxRange.setValue(value);
//        setLabel();
//    }

    public DoubleProperty minValueProperty() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        if (minValue >= 0 && minValue < getMaxValue()) {
            double width = bgRect.getWidth();
            double leftX = width * minValue;
            leftThumb.setTranslateX(leftX-10-leftThumb.getLayoutX());

            activeRect.setTranslateX(leftX-10-leftThumb.getLayoutX());
            activeRect.setWidth(250 - (leftX-10-leftThumb.getLayoutX()) + rightThumb.getTranslateX());

            leftLabel.setText(String.format("%.2f", minValue));
            this.minValue.set(minValue);
        }
    }

    public double getMinValue() {
        return minValue.getValue();
    }

    public DoubleProperty maxValueProperty() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        if (maxValue > getMinValue() && maxValue <= 1) {
            double width = bgRect.getWidth();
            double rightX = width * maxValue;
            rightThumb.setTranslateX(rightX-10-rightThumb.getLayoutX());

            activeRect.setWidth(250 - leftThumb.getTranslateX() + rightX-10-rightThumb.getLayoutX());

            rightLabel.setText(String.format("%.2f", maxValue));
            this.maxValue.set(maxValue);
        }
    }

    public double getMaxValue() {
        return maxValue.getValue();
    }

    public final ObjectProperty<EventHandler<Event>> onValueChangedProperty() {
        return onValueChanged;
    }

    public final void setOnValueChanged(EventHandler<Event> handler) {
        onValueChanged.set(handler);
    }

    public final EventHandler<Event> getOnValueChanged() {
        return onValueChanged.get();

    }

    public final ObjectProperty<EventHandler<Event>> onValueChangingProperty() {
        return onValueChanging;
    }

    public final void setOnValueChanging(EventHandler<Event> handler) {
        onValueChanging.set(handler);
    }

    public final EventHandler<Event> getOnValueChanging() {
        return onValueChanging.get();

    }
}
