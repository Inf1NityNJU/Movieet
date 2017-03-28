package component.sequencebutton;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;

/**
 * Created by Sorumi on 16/12/17.
 */
public class SequenceButton extends HBox {

    public enum SequenceButtonState {
        Ascending, Descending
    }

    @FXML
    private Label textLabel;

    @FXML
    private Label iconLabel;


    private StringProperty text = new SimpleStringProperty();

    private BooleanProperty active = new SimpleBooleanProperty();

    private ObjectProperty<SequenceButtonState> state = new SimpleObjectProperty<>();

    public SequenceButton() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SequenceButton.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        setState(SequenceButtonState.Descending);
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


    public BooleanProperty activeProperty() {
        return this.active;
    }

    public Boolean getActive() {
        return active.get();
    }

    public void setActive(Boolean active) {
        this.active.setValue(active);
        updateView();
    }

    private void updateView() {
        if (getActive()) {
            textLabel.setTextFill(Color.web("AAAAAA"));
            iconLabel.setVisible(true);
        } else {
            textLabel.setTextFill(Color.web("DDDDDD"));
            iconLabel.setVisible(false);
        }
    }


    public ObjectProperty<SequenceButtonState> stateProperty() {
        return this.state;
    }

    public SequenceButtonState getState() {
        return state.get();
    }

    public void setState(SequenceButtonState state) {
        this.state.setValue(state);
        if (state == SequenceButtonState.Ascending) {
            iconLabel.setText("\uf106");
        } else if (state == SequenceButtonState.Descending) {
            iconLabel.setText("\uf107");
        }
    }


}
