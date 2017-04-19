package ui.componentcontroller;

import bl.MovieBLFactory;
import blservice.MovieBLService;
import component.modeimageview.ModeImageView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import vo.MovieVO;

/**
 * Created by Sorumi on 17/4/19.
 */
public class SimilarMovieCellController {

    @FXML
    private ModeImageView posterImageView;

    @FXML
    private Label nameLabel;

    private MovieVO movieVO;

    private MovieBLService movieBLService = MovieBLFactory.getMovieBLService();

    public void setMovie(MovieVO movieVO) {
        this.movieVO = movieVO;
        nameLabel.setText(movieVO.name);

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
    }
}
