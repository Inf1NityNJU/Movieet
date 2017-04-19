package ui.viewcontroller;

import bl.MovieBLFactory;
import blservice.MovieBLService;
import component.spinner.Spinner;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import ui.componentcontroller.MovieCellController;
import ui.componentcontroller.MoviePagePaneController;
import ui.componentcontroller.MovieSearchPaneController;
import vo.MovieVO;
import vo.PageVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by Sorumi on 17/3/27.
 */
public class MovieListViewController {

    private static final int NUM_OF_CELL = 10;

    @FXML
    private ScrollPane root;

    @FXML
    private VBox contentVBox;


    private Pane spinnerPane;
    private Spinner spinner;
    private TilePane tilePane;
    private StackPane pagePane;

    private FXMLLoader[] cellLoaders = new FXMLLoader[NUM_OF_CELL];
    private Node[] cells = new Node[NUM_OF_CELL];

    private MovieViewController movieViewController;

    private MovieSearchPaneController movieSearchPaneController;
    private MoviePagePaneController moviePagePaneController;

    private MovieBLService movieBLService = MovieBLFactory.getMovieBLService();

    private List<MovieVO> movieVOs;
    private String keyword = "";
    private int page = 0;

//    private boolean isLoading = false;

    public void setMovieViewController(MovieViewController movieViewController) {
        this.movieViewController = movieViewController;
    }

    @FXML
    public void initialize() {
        setSearchPane();
        setListPaneAndPagePane();
    }

    public void showMovieGenreList() {
        page = 0;
        if (keyword != null) {
            movieSearchPaneController.showGenre(true);
        }
        this.keyword = null;

        findListByGenreAndSortAndPage();

    }

    public void showMovieSearchList(String keyword) {
        page = 0;
        if (this.keyword == null) {
            movieSearchPaneController.showGenre(false);
        }
        this.keyword = keyword;

        findListByKeywordAndPage();
    }

    public void showMovieInfo(MovieVO movieVO) {
        movieViewController.showMovieInfo(movieVO);
    }

    public void turnPage(int page) {
        this.page = page - 1;
        if (keyword == null) {
            findListByGenreAndSortAndPage();
        } else {
            findListByKeywordAndPage();
        }
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

    private void findListByKeywordAndPage() {
        if (moviePagePaneController == null) return;

        startSpinner();

        MovieVO movieVO = movieBLService.findMovieById(keyword.replace(" ", ""));
        if (movieVO != null) {
            movieVOs = new ArrayList<>();
            movieVOs.add(movieVO);

        } else {
            Task<Integer> task = new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    PageVO<MovieVO> moviePagePO = movieBLService.findMoviesByKeywordInPage(keyword, page);
                    movieVOs = moviePagePO.list;
                    Platform.runLater(() -> {
                        moviePagePaneController.setPageCount(moviePagePO.totalPage);
                        moviePagePaneController.setPageNum(moviePagePO.currentPage + 1);

                        refreshList();
                        contentVBox.getChildren().remove(spinnerPane);
                        spinner.stop();
                        contentVBox.getChildren().add(pagePane);
                    });

                    movieSearchPaneController.setLoading(false);
                    return 1;
                }
            };

//            isLoading = true;
            movieSearchPaneController.setLoading(true);
            new Thread(task).start();

        }

    }

    private void findListByGenreAndSortAndPage() {
        if (moviePagePaneController == null) return;

        startSpinner();

        Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                PageVO<MovieVO> moviePagePO = movieBLService.findMoviesByTagInPage(movieSearchPaneController.tags, movieSearchPaneController.sortType, page);
                movieVOs = moviePagePO.list;

                Platform.runLater(() -> {
                    moviePagePaneController.setPageCount(moviePagePO.totalPage);
                    moviePagePaneController.setPageNum(moviePagePO.currentPage + 1);

                    refreshList();
                    contentVBox.getChildren().remove(spinnerPane);
                    spinner.stop();

                    contentVBox.getChildren().add(pagePane);
                });

                movieSearchPaneController.setLoading(false);
//                isLoading = false;
                return 1;
            }
        };
        movieSearchPaneController.setLoading(true);
//        isLoading = true;
        new Thread(task).start();
    }

    private void startSpinner() {
        tilePane.getChildren().clear();
        contentVBox.getChildren().remove(pagePane);
        if (spinnerPane != null) {
            spinnerPane.getChildren().remove(spinner);
            contentVBox.getChildren().remove(spinnerPane);
        }

        spinnerPane = new Pane();
        spinnerPane.setPrefSize(920, 320);
        spinnerPane.getStyleClass().add("card");
        spinner = new Spinner();
        spinner.setCenterX(460);
        spinner.setCenterY(100);
        spinnerPane.getChildren().add(spinner);
        contentVBox.getChildren().add(spinnerPane);
        spinner.start();
    }

    private void refreshList() {
        int length = Math.min(movieVOs.size(), 10);
        for (int i = 0; i < length; i++) {
            FXMLLoader fxmlLoader = cellLoaders[i];
            MovieCellController movieCellController = fxmlLoader.getController();
            movieCellController.setMovie(movieVOs.get(i));
            Node cell = cells[i];
            tilePane.getChildren().add(cell);
        }

        root.setVvalue(0.0);

    }

}
