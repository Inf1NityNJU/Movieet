package ui.viewcontroller;

import bl.MovieBLFactory;
import blservice.MovieBLService;
import component.pagepane.PagePane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import po.MoviePO;
import po.PagePO;
import ui.componentcontroller.MovieCellController;
import ui.componentcontroller.MoviePagePaneController;
import ui.componentcontroller.MovieSearchPaneController;
import util.MovieGenre;
import vo.MovieVO;
import vo.PageVO;

import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by Sorumi on 17/3/27.
 */
public class MovieListViewController {

    private static final int NUM_OF_CELL = 10;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox contentVBox;

    private TilePane tilePane;
    private StackPane pagePane;

    private FXMLLoader[] cellLoaders = new FXMLLoader[NUM_OF_CELL];
    private Node[] cells = new Node[NUM_OF_CELL];

    private MovieViewController movieViewController;

    private MovieSearchPaneController movieSearchPaneController;
    private MoviePagePaneController moviePagePaneController;

    private MovieBLService movieBLService = MovieBLFactory.getMovieBLService();

    public void setMovieViewController(MovieViewController movieViewController) {
        this.movieViewController = movieViewController;
    }

    @FXML
    public void initialize() {
        setSearchPane();
        setListPaneAndPagePane();
    }

    public void showMovieGenreList() {
        movieSearchPaneController.showGenre(true);

        //TODO
        testList();
//        System.out.println(movieSearchPaneController.sortType.toString());
//        PageVO<MovieVO> moviePagePO = movieBLService.findMoviesByTagInPage(EnumSet.of(MovieGenre.Action), movieSearchPaneController.sortType, 0);
//
//        System.out.print(moviePagePO.list);
//        for (int i = 0; i < moviePagePO.list.size(); i++) {
//            FXMLLoader fxmlLoader = cellLoaders[i];
//            MovieCellController movieCellController = fxmlLoader.getController();
//            movieCellController.setMovie(moviePagePO.list.get(i));
//            Node cell = cells[i];
//            tilePane.getChildren().add(cell);
//        }

        scrollPane.setVvalue(0.0);
    }

    public void showMovieSearchList(String keyword) {
        movieSearchPaneController.showGenre(false);

        //TODO
        testList();

        scrollPane.setVvalue(0.0);
    }

    public void showMovieInfo(String movieId) {
        movieViewController.showMovieInfo(movieId);
    }


    /* private */

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

    private void setListPaneAndPagePane() {

        try {

            tilePane = new TilePane();
            tilePane.setPrefColumns(2);
            tilePane.setHgap(40);
            tilePane.setVgap(40);

            FXMLLoader pageLoader = new FXMLLoader();
            pageLoader.setLocation(getClass().getResource("/component/MoviePagePane.fxml"));
            pagePane = pageLoader.load();

            moviePagePaneController = pageLoader.getController();
            moviePagePaneController.setMovieListViewController(this);

            for (int i = 0; i < NUM_OF_CELL; i++) {
                FXMLLoader cellLoader = new FXMLLoader();
                cellLoader.setLocation(getClass().getResource("/component/MovieCell.fxml"));
                HBox cell = cellLoader.load();

                MovieCellController movieCellController = cellLoader.getController();
                movieCellController.setMovieListViewController(this);

                cellLoaders[i] = cellLoader;
                cells[i] = cell;
            }

            contentVBox.getChildren().addAll(tilePane, pagePane);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // TODO
    private void testList() {
        tilePane.getChildren().clear();
        for (int i = 0; i < NUM_OF_CELL; i++) {
            tilePane.getChildren().addAll(cells[i]);
        }
    }
}
