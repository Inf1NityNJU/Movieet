package ui.componentcontroller;

import bl.MovieBLFactory;
import blservice.MovieBLService;
import component.modeimageview.ModeImageView;
import component.ratestarpane.RateStarPane;
import component.taglabel.TagLabel;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
    private HBox root;

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

    private MovieVO movieVO;

    private MovieListViewController movieListViewController;

    private MovieBLService movieBLService = MovieBLFactory.getMovieBLService();


    public void setMovieListViewController(MovieListViewController movieListViewController) {
        this.movieListViewController = movieListViewController;
    }

    @FXML
    public void initialize() {
        root.setOnMouseEntered(event -> {
            root.getStyleClass().add("active");
        });
        root.setOnMouseExited(event -> {
            root.getStyleClass().remove("active");
        });

//        posterImageView.setImage(new Image(getClass().getResource("/images/example.png").toExternalForm()));
//        posterImageView.setMode(ModeImageView.ContentMode.Fill);
    }

    public void setMovie(MovieVO movieVO) {
        this.movieVO = movieVO;
        // Poster
        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                posterImageView.setImage(null);
                Image poster = movieBLService.findPosterByMovieId(movieVO.id, 140);

                Platform.runLater(() -> {
                    if (poster != null) {
                        posterImageView.setImage(poster);
                        posterImageView.setMode(ModeImageView.ContentMode.Fill);
                    }
                });

                return 1;
            }
        };

        new Thread(task).start();


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
        scoreStarPane.setScore(movieVO.score / 2);
        scoreLabel.setText(movieVO.score + "");
//        releaseDateLabel.setText();


    }

    @FXML
    private void clickPane() {
        movieListViewController.showMovieInfo(movieVO);
    }

}
