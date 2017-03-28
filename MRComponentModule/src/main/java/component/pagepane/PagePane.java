package component.pagepane;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sorumi on 16/12/3.
 */
public class PagePane extends HBox {

    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;

    @FXML
    private Label leftLabel;

    @FXML
    private Label rightLabel;

    @FXML
    private HBox pageHBox;

    private Button currentButton;

    private IntegerProperty pageCount = new SimpleIntegerProperty(1);
    private IntegerProperty currentPage = new SimpleIntegerProperty(1);
    private IntegerProperty maxPageButtonCount = new SimpleIntegerProperty(5);

    private ObjectProperty<EventHandler<Event>> onPageChanged = new SimpleObjectProperty<EventHandler<Event>>();

    private List<Button> pageButtons = new ArrayList<>();

    public PagePane() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PagePane.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        leftButton.setText("\uf104");
        rightButton.setText("\uf105");

        setPageCount();

        setOnPageChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {

            }
        });

        leftButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setCurrentPage(getCurrentPage() - 1);
                onPageChangedProperty().get().handle(event);
            }
        });

        rightButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setCurrentPage(getCurrentPage() + 1);
                onPageChangedProperty().get().handle(event);
            }
        });

    }

    /* pageCount */

    public IntegerProperty pageCountProperty() {
        return pageCount;
    }

    public Integer getPageCount() {
        return pageCount.getValue();
    }

    public void setPageCount(Integer integer) {
        pageCount.setValue(integer);
        setPageCount();
    }

    /* currentPage */

    public IntegerProperty currentPageProperty() {
        return currentPage;
    }

    public Integer getCurrentPage() {
        return currentPage.getValue();
    }

    public void setCurrentPage(Integer page) {
        if (page == 0) {
            leftButton.setDisable(true);
            rightButton.setDisable(true);
            return;
        }
        if (page <= getPageCount()) {
            setCurrentButton(page);
        }

        leftButton.setDisable(page == 1);
        rightButton.setDisable(page.equals(getPageCount()));

        currentPage.setValue(page);
    }

    /* maxPageButtonCount*/
    public IntegerProperty maxPageButtonCountProperty() {
        return maxPageButtonCount;
    }

    public Integer getMaxPageButtonCount() {
        return maxPageButtonCount.getValue();
    }

    public void setMaxPageButtonCount(Integer count) {
        count = count % 2 == 1 ? count : count - 1;
        if (count > 1 && count < 10) {
            maxPageButtonCount.setValue(count);
            setPageCount();
        }
    }


    /* onPageChanged */

    public final ObjectProperty<EventHandler<Event>> onPageChangedProperty() {
        return onPageChanged;
    }

    public final void setOnPageChanged(EventHandler<Event> handler) {
        onPageChanged.set(handler);
    }

    public final EventHandler<Event> getOnPageChanged() {
        return onPageChanged.get();

    }

    /* private */

    private void setPageCount() {
        pageHBox.getChildren().clear();
        pageButtons.clear();

        if (getPageCount() <= 0) {
            setCurrentPage(0);
        } else {
            int buttonCount = Math.min(getMaxPageButtonCount(), getPageCount());

            for (int i = 0; i < buttonCount; i++) {
                Button button = new Button();
                button.getStyleClass().add("page-button");
                pageButtons.add(button);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        setCurrentPage(Integer.parseInt(button.getText()));
                        onPageChangedProperty().get().handle(event);
                    }
                });
                pageHBox.getChildren().add(button);
            }
            setCurrentPage(1);
        }

    }

    private void setCurrentButton(int page) {

        if (page > getPageCount()) return;

        Button newButton;
        if (getPageCount() > getMaxPageButtonCount()) {

            int innerIndex = getMaxPageButtonCount() / 2;
            int minPage = page - innerIndex;
            int maxPage = page + innerIndex;

            if (minPage < 1) {
                int offset = 1 - minPage;
                innerIndex -= offset;
                minPage += offset;
                maxPage += offset;

            } else if (maxPage > getPageCount()) {
                int offset = maxPage - getPageCount();
                innerIndex += offset;
                minPage -= offset;
                maxPage -= offset;
            }

            leftLabel.setManaged(minPage != 1);
            leftLabel.setVisible(minPage != 1);
            rightLabel.setManaged(maxPage != getPageCount());
            rightLabel.setVisible(maxPage != getPageCount());

            for (int i = 0; i < pageButtons.size(); i++) {
                Button button = pageButtons.get(i);
                button.setText(minPage + i + "");
            }
            newButton = pageButtons.get(innerIndex);

        } else {
            for (int i = 0; i < pageButtons.size(); i++) {
                Button button = pageButtons.get(i);
                button.setText(i + 1 + "");
            }
            newButton = pageButtons.get(page - 1);

            leftLabel.setManaged(false);
            leftLabel.setVisible(false);
            rightLabel.setManaged(false);
            rightLabel.setVisible(false);
        }


        if (currentButton != null) {
            currentButton.getStyleClass().remove("selected");
        }

        currentButton = newButton;
        newButton.getStyleClass().add("selected");
    }

}
