package ui.viewcontroller;

import bl.MovieBLFactory;
import blservice.MovieBLService;

import component.meterbar.MeterBar;
import component.rangelinechart.RangeLineChart;
import component.ratestarpane.RateStarPane;
import component.spinner.Spinner;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.componentcontroller.MovieSearchPaneController;
import vo.MovieVO;
import vo.ReviewCountVO;
import vo.ScoreDistributionVO;
import vo.WordVO;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Stack;


/**
 * Created by Sorumi on 17/3/4.
 */
public class MovieViewController {

    private Node initView;
    private Stack<Node> stack = new Stack<Node>();

    private MainViewController mainViewController;

    public MovieViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    /**
     * 返回上一界面
     */
    public void back() {
        if (!stack.empty()) {
            Node node = stack.pop();
            mainViewController.setCenter(node);
            if (stack.empty()) {
                mainViewController.showBackButton(false);
            }
        }
    }

    public void showMovieList() {
        if (initView != null) {
            stack.clear();
            mainViewController.setCenter(initView);
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MovieListView.fxml"));
            ScrollPane node = loader.load();

            MovieListViewController movieListViewController = loader.getController();
            movieListViewController.setMovieViewController(this);

            mainViewController.setCenter(node);
            movieListViewController.showMovieGenreList();
            initView = node;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMovieInfo(MovieVO movieVO) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/MovieView.fxml"));
            ScrollPane node = loader.load();

            MovieInfoViewController movieInfoViewController = loader.getController();
            movieInfoViewController.setMovieViewController(this);
            movieInfoViewController.setMovie(movieVO);

            Node oldNode = mainViewController.getCenter();
            stack.push(oldNode);
            mainViewController.showBackButton(true);


            mainViewController.setCenter(node);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
