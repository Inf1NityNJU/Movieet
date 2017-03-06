package meterbar;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by Sorumi on 17/3/3.
 */
public class MeterBar extends AnchorPane {

    @FXML
    private Rectangle fillRect;

    @FXML
    private Rectangle bgRect;

    @FXML
    private Label percentLabel;

    private DoubleProperty meterWidth;

    private IntegerProperty allNum = new SimpleIntegerProperty(0);

    private IntegerProperty num = new SimpleIntegerProperty(0);


    public MeterBar() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MeterBar.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUp() {
        if (getAllNum() > 0 && getNum() > 0) {
            double percent = (double)getNum() / getAllNum();
            String str = String.format( "%.2f%%", percent * 100);
            percentLabel.setText(str);
            fillRect.setWidth(bgRect.getWidth() * percent);
        }
    }

    public IntegerProperty allNumProperty() {
        return allNum;
    }

    public Integer getAllNum() {
        return allNum.getValue();
    }

    public void setAllNum(Integer allNum) {
        this.allNum.setValue(allNum);
        setUp();
    }

    public IntegerProperty numProperty() {
        return num;
    }

    public Integer getNum() {
        return num.getValue();
    }

    public void setNum(Integer num) {
        this.num.setValue(num);
        setUp();
    }

    public DoubleProperty meterWidthProperty() {
        return bgRect.widthProperty();
    }

    public Double getMeterWidth() {
        return bgRect.getWidth();
    }

    public void setMeterWidth(Double meterWidth) {
        this.bgRect.setWidth(meterWidth);
        setUp();;
    }
}
