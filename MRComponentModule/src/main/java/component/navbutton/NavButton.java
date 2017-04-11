package component.navbutton;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by Sorumi on 17/4/11.
 */
public class NavButton extends AnchorPane {

    @FXML
    private Label iconLabel;

    @FXML
    private Label textLabel;

    @FXML
    private Rectangle activeRect;

    private BooleanProperty active = new SimpleBooleanProperty(false);

    public NavButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NavButton.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        active = new SimpleBooleanProperty(true);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setActive(false);
    }

    public StringProperty textProperty() {
        return textLabel.textProperty();
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }

    public StringProperty iconProperty() {
        return iconLabel.textProperty();
    }

    public String getIcon() {
        return iconProperty().get();
    }

    public void setIcon(String value) {
        iconProperty().set(value);
    }

    public BooleanProperty activeProperty() {
        return this.active;
    }

    public Boolean getActive() {
        return active.get();
    }

    public void setActive(Boolean active) {

        if (active) {
            activeRect.setVisible(true);
            this.getStyleClass().add("active");
        } else {
            activeRect.setVisible(false);
            this.getStyleClass().remove("active");
        }
        this.active.setValue(active);
    }

}
