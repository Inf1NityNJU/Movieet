package ui.viewcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import ui.componentcontroller.MovieSearchPaneController;

import java.io.IOException;

/**
 * Created by Sorumi on 17/3/27.
 */
public class MovieListViewController {

    @FXML
    private VBox contentVBox;

    private MovieViewController movieViewController;

    private MovieSearchPaneController movieSearchPaneController;

    public void setMovieViewController(MovieViewController movieViewController) {
        this.movieViewController = movieViewController;
    }

    public void showMovieGenreList() {
        setSearchPane();
        movieSearchPaneController.showGenre(true);
    }

    private void setSearchPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/component/MovieSearchPane.fxml"));
            VBox node = loader.load();

            movieSearchPaneController = loader.getController();
            movieSearchPaneController.setMovieListViewController(this);

            contentVBox.getChildren().add(node);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
