package component.topmenu;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sorumi on 17/4/10.
 */
public class TopMenu extends VBox {

    @FXML
    private HBox labelHBox;

    @FXML
    private Rectangle activeRect;

    @FXML
    private Rectangle backgroundRect;


    private ObservableList<Node> labels;

    private IntegerProperty itemIndex = new SimpleIntegerProperty();

    private DoubleProperty lineWidth = new SimpleDoubleProperty();

    private DoubleProperty activeWidth = new SimpleDoubleProperty(0);

    private ObjectProperty<EventHandler<Event>> onItemClick = new SimpleObjectProperty<EventHandler<Event>>();

    public TopMenu() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TopMenu.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setOnItemClick(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {

            }
        });

        labels = labelHBox.getChildren();

        labels.addListener(new ListChangeListener() {

            @Override
            public void onChanged(Change c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        List list = c.getAddedSubList();

                        for (Object object : list) {
                            if (object instanceof Label) {
                                Label label = (Label) object;
                                label.setOnMouseClicked(new EventHandler<Event>() {
                                    @Override
                                    public void handle(Event event) {
                                        int index = labels.indexOf(object);
                                        setItemIndex(index);
                                        getOnItemClick().handle(event);
                                    }
                                });
                            }

                        }
                    }
                }

            }
        });

        activeWidth.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Label label = (Label) labels.get(getItemIndex());
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().addAll(
                        new KeyFrame(Duration.millis(300), new KeyValue(activeRect.xProperty(), label.getLayoutX(), Interpolator.EASE_BOTH)),
                        new KeyFrame(Duration.millis(300), new KeyValue(activeRect.widthProperty(), activeWidth.getValue()))
                );
                timeline.play();
            }
        });

    }

    public ObservableList<Node> getLabels() {
        return labelHBox.getChildren();
    }

    public DoubleProperty lineWidthProperty() {
        return lineWidth;
    }

    public Double getLineWidth() {
        return lineWidth.getValue();
    }

    public void setLineWidth(Double lineWidth) {
        this.lineWidth.setValue(lineWidth);
        this.backgroundRect.setWidth(lineWidth);
    }

    public IntegerProperty itemIndexProperty() {
        return itemIndex;
    }

    public Integer getItemIndex() {
        return itemIndex.getValue();
    }

    public void setItemIndex(Integer itemIndex) {
        if (itemIndex >= labels.size()) return;
        if (this.itemIndex != null) {
            Label label = (Label) labels.get(getItemIndex());
            label.getStyleClass().remove("active");
        }
        this.itemIndex.setValue(itemIndex);
        Label label = (Label) labels.get(itemIndex);
        label.getStyleClass().add("active");
        activeWidth.bind(label.widthProperty());

    }

    public final ObjectProperty<EventHandler<Event>> onItemClickProperty() {
        return onItemClick;
    }

    public final void setOnItemClick(EventHandler<Event> handler) {
        onItemClick.set(handler);
    }

    public final EventHandler<Event> getOnItemClick() {
        return onItemClick.get();
    }

}
