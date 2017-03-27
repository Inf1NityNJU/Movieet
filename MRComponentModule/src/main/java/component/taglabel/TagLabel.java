package component.taglabel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Created by Sorumi on 17/3/27.
 */
public class TagLabel extends Label {

    private StringProperty textColor = new SimpleStringProperty("AAAAAA");
    private StringProperty backgroundColor = new SimpleStringProperty("transparent");

    private StringProperty activeTextColor = new SimpleStringProperty("FFFFFF");
    private StringProperty activeBackgroundColor = new SimpleStringProperty("6ED3D8");

    private BooleanProperty active = new SimpleBooleanProperty(false);

    public TagLabel() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TagLabel.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public StringProperty textColorProperty() {
        return this.textColor;
    }

    public String getTextColor() {
        return textColor.get();
    }

    public void setTextColor(String color) {
        this.textColor.setValue(color);
        update();
    }

    public StringProperty backgroundColorProperty() {
        return this.backgroundColor;
    }

    public String getBackgroundColor() {
        return backgroundColor.get();
    }

    public void setBackgroundColor(String color) {
        this.backgroundColor.setValue(color);
        update();
    }


    public StringProperty activeTextColorProperty() {
        return this.activeTextColor;
    }

    public String getActiveTextColor() {
        return activeTextColor.get();
    }

    public void setActiveTextColor(String color) {
        this.activeTextColor.setValue(color);
        update();
    }

    public StringProperty activeBackgroundColorProperty() {
        return this.activeBackgroundColor;
    }

    public String getActiveBackgroundColor() {
        return activeBackgroundColor.get();
    }

    public void setActiveBackgroundColor(String color) {
        this.activeBackgroundColor.setValue(color);
        update();
    }

    public BooleanProperty activeProperty() {
        return this.active;
    }

    public Boolean getActive() {
        return active.get();
    }

    public void setActive(Boolean active) {
        this.active.setValue(active);
        update();
    }


    private void update() {
        if (getActive()) {
            setTextFill(Color.web(getActiveTextColor()));
            this.setStyle("-fx-background-color: " + "#" + getActiveBackgroundColor() + ";");
        } else {
            setTextFill(Color.web(getTextColor()));
            this.setStyle("-fx-background-color: " + "#" + getBackgroundColor() + ";");

        }
    }
}
