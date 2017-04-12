package ui.viewcontroller;

import component.pagepane.PagePane;
import component.sequencebutton.SequenceButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.componentcontroller.ReviewCellController;

import java.io.IOException;

/**
 * Created by Sorumi on 17/4/11.
 */
public class ReviewListViewController {

    private static final int NUM_OF_CELL = 10;

    @FXML
    private HBox sortHBox;

    @FXML
    private VBox contentVBox;

    @FXML
    private PagePane pagePane;

    private FXMLLoader[] cellLoaders = new FXMLLoader[NUM_OF_CELL];
    private Node[] cells = new Node[NUM_OF_CELL];


    private MovieInfoViewController movieInfoViewController;

    public void setMovieInfoViewController(MovieInfoViewController movieInfoViewController) {
        this.movieInfoViewController = movieInfoViewController;
    }

    public void showReviews() {
        setListPane();

        for (Node node : sortHBox.getChildren()) {
            SequenceButton sequenceButton = (SequenceButton) node;
            sequenceButton.setOnMouseClicked(event -> onClickSequenceButton(sequenceButton));
        }
        onClickSequenceButton((SequenceButton) sortHBox.getChildren().get(0));

        // TODO
        pagePane.setPageCount(3);
        testList();
    }

    private void onClickSequenceButton(SequenceButton sequenceButton) {
        for (Node node : sortHBox.getChildren()) {
            SequenceButton oldSequenceButton = (SequenceButton) node;
            if (oldSequenceButton == sequenceButton) {
                if (sequenceButton.getActive()) {
                    if (sequenceButton.getState() == SequenceButton.SequenceButtonState.Ascending) {
                        sequenceButton.setState(SequenceButton.SequenceButtonState.Descending);
                    } else {
                        sequenceButton.setState(SequenceButton.SequenceButtonState.Ascending);
                    }
                } else {
                    sequenceButton.setActive(true);
                    sequenceButton.setState(SequenceButton.SequenceButtonState.Descending);
                }

            } else {
                oldSequenceButton.setActive(false);
            }

            sequenceButton.setOnMouseClicked(event -> onClickSequenceButton(sequenceButton));
        }
    }

    /* private */

    private void setListPane() {

        try {

            for (int i = 0; i < NUM_OF_CELL; i++) {
                FXMLLoader cellLoader = new FXMLLoader();
                cellLoader.setLocation(getClass().getResource("/component/ReviewCell.fxml"));
                HBox cell = cellLoader.load();

                ReviewCellController reviewCellController = cellLoader.getController();
                reviewCellController.setReviewListViewController(this);

                cellLoaders[i] = cellLoader;
                cells[i] = cell;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // TODO
    private void testList() {
        contentVBox.getChildren().clear();
        for (int i = 0; i < NUM_OF_CELL; i++) {
            contentVBox.getChildren().addAll(cells[i]);
        }
    }

    @FXML
    private void pageChanged() {

    }

}
