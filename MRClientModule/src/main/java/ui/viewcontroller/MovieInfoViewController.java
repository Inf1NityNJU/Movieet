package ui.viewcontroller;

import component.modeimageview.ModeImageView;
import component.ratestarpane.RateStarPane;
import component.topmenu.TopMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * Created by Sorumi on 17/4/10.
 */
public class MovieInfoViewController {

    @FXML
    private VBox contentVBox;

    @FXML
    private ModeImageView posterImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private HBox tagHBox;

    @FXML
    private Label scoreLabel;

    @FXML
    private RateStarPane scoreStarPane;

    @FXML
    private Label reviewCountLabel;

    @FXML
    private GridPane meterBarPane;

    @FXML
    private Label releaseDateLabel;

    @FXML
    private Label durationLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label languageLabel;

    @FXML
    private Label directorLabel;

    @FXML
    private Label writerLabel;

    @FXML
    private Label actorLabel;

    @FXML
    private TopMenu otherMenu;

    @FXML
    private StackPane contentPane;

    @FXML
    private Text storylineText;

    private VBox reviewListVBox;

    private MovieViewController movieViewController;

    private ReviewListViewController reviewListViewController;

    public void setMovieViewController(MovieViewController movieViewController) {
        this.movieViewController = movieViewController;
    }

    @FXML
    public void initialize() {

    }

    public void setMovie(String movieId) {
        otherMenu.setItemIndex(0);

        // image
        posterImageView.setImage(new Image(getClass().getResource("/images/example.png").toExternalForm()));
        posterImageView.setMode(ModeImageView.ContentMode.Fill);
        // reviews

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ReviewListView.fxml"));
            reviewListVBox = loader.load();

            reviewListViewController = loader.getController();
            reviewListViewController.setMovieInfoViewController(this);
            reviewListViewController.showReviews();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /* private */

    private void showStoryline() {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(storylineText);
    }

    private void showReviews() {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(reviewListVBox);

    }

    @FXML
    private void clickMenuItem() {
        int index = otherMenu.getItemIndex();
        switch (index) {
            case 0:
                showStoryline();
                break;
            case 1:
                showReviews();
                break;
        }
    }
}
