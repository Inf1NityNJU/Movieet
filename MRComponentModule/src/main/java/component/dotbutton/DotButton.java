package component.dotbutton;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;


/**
 * Created by Sorumi on 17/3/10.
 */
public class DotButton extends Button {

    @FXML
    private Circle dot;

    private StringProperty color = new SimpleStringProperty("22CCCC");
    private BooleanProperty active = new SimpleBooleanProperty(true);

    public DotButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DotButton.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public StringProperty colorProperty() {
        return this.color;
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.setValue(color);
        dot.setFill(Color.web(color));
    }



    public BooleanProperty activeProperty() {
        return this.active;
    }

    public Boolean getActive() {
        return active.get();
    }

    public void setActive(Boolean active) {
        this.active.setValue(active);
        this.setOpacity(active ? 1 : 0.5);
    }


}
