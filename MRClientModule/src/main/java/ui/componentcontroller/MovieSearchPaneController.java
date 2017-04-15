package ui.componentcontroller;

import component.sequencebutton.SequenceButton;
import component.sequencebutton.SequenceButton.SequenceButtonState;
import component.taglabel.TagLabel;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import ui.viewcontroller.MovieListViewController;
import util.MovieGenre;
import util.MovieSortType;

import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by Sorumi on 17/3/27.
 */
public class MovieSearchPaneController {


    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private TilePane genrePane;

    @FXML
    private HBox sortHBox;

    @FXML
    private HBox keywordHBox;

    @FXML
    private Label keywordLabel;

    @FXML
    private Label clearLabel;

    @FXML
    private SequenceButton releaseDateButton;

    @FXML
    private SequenceButton averageScoreButton;


    private MovieListViewController movieListViewController;

    public MovieSortType sortType = MovieSortType.DATE_ASC;
    private List<TagLabel> tagLabels = new ArrayList<>();
    public EnumSet tags = EnumSet.of(MovieGenre.All);

    private final char clearCode = '\uf00d';

    @FXML
    public void initialize() {
        clearLabel.setText(clearCode + "");
    }

    public void setMovieListViewController(MovieListViewController movieListViewController) {
        this.movieListViewController = movieListViewController;

        for (Node node : sortHBox.getChildren()) {
            SequenceButton sequenceButton = (SequenceButton) node;
            sequenceButton.setOnMouseClicked(event -> onClickSequenceButton(sequenceButton));
        }
        onClickSequenceButton((SequenceButton) sortHBox.getChildren().get(0));

        for (MovieGenre genre : MovieGenre.values()) {
            TagLabel tagLabel = new TagLabel();
            tagLabel.setOnMouseClicked(event -> onClickTagLabel(tagLabel));
            tagLabel.setText(genre.getGenreName());
            tagLabel.setCursor(Cursor.HAND);
            genrePane.getChildren().add(tagLabel);
        }
        onClickTagLabel((TagLabel) genrePane.getChildren().get(0));
    }

    public void showGenre(boolean show) {
        if (!show) {
            genrePane.setManaged(false);
            genrePane.setVisible(false);
            sortHBox.setManaged(false);
            sortHBox.setVisible(false);
            keywordHBox.setManaged(true);
            keywordHBox.setVisible(true);
        } else {
            genrePane.setManaged(true);
            genrePane.setVisible(true);
            sortHBox.setManaged(true);
            sortHBox.setVisible(true);
            keywordHBox.setManaged(false);
            keywordHBox.setVisible(false);
        }

    }

    private void onClickSequenceButton(SequenceButton sequenceButton) {
        for (Node node : sortHBox.getChildren()) {
            SequenceButton oldSequenceButton = (SequenceButton) node;
            if (oldSequenceButton == sequenceButton) {
                if (sequenceButton.getActive()) {
                    if (sequenceButton.getState() == SequenceButtonState.Ascending) {
                        sequenceButton.setState(SequenceButtonState.Descending);
                        sortType = sequenceButton == releaseDateButton ? MovieSortType.DATE_DESC : MovieSortType.SCORE_DESC;
                    } else {
                        sequenceButton.setState(SequenceButtonState.Ascending);
                        sortType = sequenceButton == releaseDateButton ? MovieSortType.DATE_ASC : MovieSortType.SCORE_ASC;
                    }
                } else {
                    sequenceButton.setActive(true);
                    sequenceButton.setState(SequenceButtonState.Descending);
                    sortType = sequenceButton == releaseDateButton ? MovieSortType.DATE_DESC : MovieSortType.SCORE_DESC;
                }
            } else {
                oldSequenceButton.setActive(false);
            }
        }

        movieListViewController.showMovieGenreList();
    }

    private void onClickTagLabel(TagLabel tagLabel) {
        TagLabel allTagLabel = (TagLabel) genrePane.getChildren().get(0);
        MovieGenre genre = MovieGenre.getMovieGenreByName(tagLabel.getText());

        if (tagLabel != allTagLabel) {
            boolean active = !tagLabel.getActive();
            tagLabel.setActive(active);
            if (active) {
                tagLabels.add(tagLabel);
                if (genre != null) {
                    tags.add(genre);
                }
            } else {
                tagLabels.remove(tagLabel);
                tags.remove(genre);
            }

            if (tagLabels.size() == 0) {
                System.out.println("0");
                allTagLabel.setActive(true);
                tags.add(MovieGenre.All);
                tagLabels.add(allTagLabel);
            } else {
                allTagLabel.setActive(false);
                tags.remove(MovieGenre.All);
                tagLabels.remove(allTagLabel);
            }

            allTagLabel.setActive(false);
            tags.remove(MovieGenre.All);
            tagLabels.remove(allTagLabel);

        } else {
            for (TagLabel tmpTag : tagLabels) {
                tmpTag.setActive(false);
            }
            allTagLabel.setActive(true);
            tags.clear();
            tagLabels.clear();
            tags.add(MovieGenre.All);
            tagLabels.add(allTagLabel);
        }
        movieListViewController.showMovieGenreList();
    }

    @FXML
    private void clickSearchButton() {
        String keyword = searchField.getText();
        keywordLabel.setText(keyword);
        movieListViewController.showMovieSearchList(keyword);

    }

    @FXML
    private void clickClearButton() {
        searchField.setText("");
        movieListViewController.showMovieGenreList();
    }
}
