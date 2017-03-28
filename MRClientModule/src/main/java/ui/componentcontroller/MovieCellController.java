package ui.componentcontroller;

import component.modeimageview.ModeImageView;
import component.ratestarpane.RateStarPane;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
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

    @FXML
    private Label reviewCountLabel;

    private MovieVO movie;

    @FXML
    public void initialize() {
        posterImageView.setImage(new Image(getClass().getResource("/images/example.png").toExternalForm()));
    }

    public void setMovie(MovieVO movie) {

    }

}
