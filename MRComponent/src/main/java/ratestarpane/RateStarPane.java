package ratestarpane;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Sorumi on 16/11/28.
 */
public class RateStarPane extends HBox {

    @FXML
    private Label star1;

    @FXML
    private Label star2;

    @FXML
    private Label star3;

    @FXML
    private Label star4;

    @FXML
    private Label star5;

    private Label[] starLabels;

    private IntegerProperty score = new SimpleIntegerProperty(5);
    private BooleanProperty abled = new SimpleBooleanProperty(true);

    private char fullStarCode = '\ue803';
    private char emptyStarCode = '\ue804';

    public RateStarPane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RateStarPane.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        starLabels = new Label[]{star1, star2, star3, star4, star5};

        for (int i = 0; i < 5; i++) {
            starLabels[i].setText(fullStarCode + "");
        }
    }

    @FXML
    private void clickStar(Event event) {
        if (getAbled()) {
            Label label = (Label) event.getSource();
            int index = Arrays.asList(starLabels).indexOf(label);
            setScore(index + 1);
        }
    }

    public BooleanProperty abledProperty() {
        return this.abled;
    }

    public Boolean getAbled() {
        return abled.get();
    }

    public void setAbled(Boolean abled) {
        this.abled.setValue(abled);
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public Integer getScore() {
        return score.getValue();
    }

    public void setScore(Integer value) {
        if (value < 0 || value > 5) {
            return;
        }
        updateView(value.intValue());
        score.setValue(value);
    }

    private void updateView(int curScore) {
        int lastScore = getScore().intValue();
        if (curScore > lastScore) {
            for (int i = lastScore; i < curScore; i++) {
                starLabels[i].setText(fullStarCode + "");
            }
        } else {
            for (int i = curScore; i < lastScore; i++) {
                starLabels[i].setText(emptyStarCode + "");
            }
        }
    }
}
