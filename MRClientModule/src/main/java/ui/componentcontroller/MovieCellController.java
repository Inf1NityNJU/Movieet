package ui.componentcontroller;

import component.modeimageview.ModeImageView;
import component.ratestarpane.RateStarPane;
import component.taglabel.TagLabel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import ui.viewcontroller.MovieListViewController;
import vo.MovieVO;

/**
 * Created by Sorumi on 17/3/28.
 */
public class MovieCellController {

    @FXML
    private ModeImageView posterImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private HBox genreTagHBox;

    @FXML
    private Label releaseDateLabel;

    @FXML
    private RateStarPane scoreStarPane;

    @FXML
    private Label scoreLabel;

//    @FXML
//    private Label reviewCountLabel;

    private MovieVO movieVO;

    private MovieListViewController movieListViewController;

    public void setMovieListViewController(MovieListViewController movieListViewController) {
        this.movieListViewController = movieListViewController;
    }

    @FXML
    public void initialize() {
//        posterImageView.setImage(new Image(getClass().getResource("/images/example.png").toExternalForm()));
//        posterImageView.setMode(ModeImageView.ContentMode.Fill);
    }

    public void setMovie(MovieVO movieVO) {
        this.movieVO = movieVO;
        // Poster
        posterImageView.setImage(movieVO.poster);
        posterImageView.setMode(ModeImageView.ContentMode.Fill);

        nameLabel.setText(movieVO.name);
        genreTagHBox.getChildren().clear();
        for (String genre : movieVO.genre) {
            TagLabel tagLabel = new TagLabel();
            tagLabel.setText(genre);
            tagLabel.setBackgroundColor("EFF6F6");
            tagLabel.setTextColor("6ED3D8");
            genreTagHBox.getChildren().add(tagLabel);
        }
        releaseDateLabel.setText(movieVO.releaseDate);
//        scoreStarPane.setScore(movie.);

    }

    @FXML
    private void clickPane() {
        movieListViewController.showMovieInfo(movieVO);
    }

}
