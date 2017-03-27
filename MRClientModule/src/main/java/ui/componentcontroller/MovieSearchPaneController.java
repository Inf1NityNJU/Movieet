package ui.componentcontroller;

import component.sequencebutton.SequenceButton;
import component.sequencebutton.SequenceButton.SequenceButtonState;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import ui.viewcontroller.MovieListViewController;

import java.beans.EventHandler;

/**
 * Created by Sorumi on 17/3/27.
 */
public class MovieSearchPaneController {

    @FXML
    private HBox genreHBox;

    @FXML
    private HBox sortHBox;


    private MovieListViewController movieListViewController;

    @FXML
    public void initialize() {


    }

    public void setMovieListViewController(MovieListViewController movieListViewController) {
        this.movieListViewController = movieListViewController;
    }


    public void showGenre(boolean show) {
        if (!show) {
            genreHBox.setManaged(false);
            genreHBox.setVisible(false);
            sortHBox.setManaged(false);
            sortHBox.setVisible(false);
            return;
        }
        for (Node node : sortHBox.getChildren()) {
            SequenceButton sequenceButton = (SequenceButton) node;
            sequenceButton.setOnMouseClicked(event -> onClickSequenceButton(sequenceButton));
        }
        onClickSequenceButton((SequenceButton) sortHBox.getChildren().get(0));
    }


    private void onClickSequenceButton(SequenceButton sequenceButton) {
        for (Node node : sortHBox.getChildren()) {
            SequenceButton oldSequenceButton = (SequenceButton) node;
            if (oldSequenceButton == sequenceButton) {
                if (sequenceButton.getActive()) {
                    if (sequenceButton.getState() == SequenceButtonState.Ascending) {
                        sequenceButton.setState(SequenceButtonState.Descending);
                    } else {
                        sequenceButton.setState(SequenceButtonState.Ascending);
                    }
                } else {
                    sequenceButton.setActive(true);
                    sequenceButton.setState(SequenceButtonState.Descending);
                }

            } else {
                oldSequenceButton.setActive(false);
            }

            sequenceButton.setOnMouseClicked(event -> onClickSequenceButton(sequenceButton));
        }
    }
}
