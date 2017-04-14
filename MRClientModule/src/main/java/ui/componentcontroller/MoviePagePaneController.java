package ui.componentcontroller;

import component.pagepane.PagePane;
import javafx.fxml.FXML;
import ui.viewcontroller.MovieListViewController;

/**
 * Created by Sorumi on 17/3/28.
 */
public class MoviePagePaneController {

    @FXML
    private PagePane pagePane;

    private MovieListViewController movieListViewController;

    public void setMovieListViewController(MovieListViewController movieListViewController) {
        this.movieListViewController = movieListViewController;
    }

    @FXML
    private void pageChanged() {
        movieListViewController.turnPage(pagePane.getCurrentPage());
    }

    public void setPageCount(int count) {
        pagePane.setPageCount(count);
    }

    public void setPageNum(int num) {
        pagePane.setCurrentPage(num);
    }
}
