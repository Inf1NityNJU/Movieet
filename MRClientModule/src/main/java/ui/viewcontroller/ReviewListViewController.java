package ui.viewcontroller;

import bl.MovieBLFactory;
import blservice.MovieBLService;
import component.pagepane.PagePane;
import component.sequencebutton.SequenceButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.componentcontroller.MovieCellController;
import ui.componentcontroller.ReviewCellController;
import util.ReviewSortType;
import vo.PageVO;
import vo.ReviewVO;

import java.io.IOException;
import java.util.List;

/**
 * Created by Sorumi on 17/4/11.
 */
public class ReviewListViewController {

    private static final int NUM_OF_CELL = 10;

    @FXML
    private HBox sortHBox;

    @FXML
    private SequenceButton postDateButton;

    @FXML
    private SequenceButton helpfulnessButton;

    @FXML
    private VBox contentVBox;

    @FXML
    private PagePane pagePane;

    private FXMLLoader[] cellLoaders = new FXMLLoader[NUM_OF_CELL];
    private Node[] cells = new Node[NUM_OF_CELL];

    private MovieInfoViewController movieInfoViewController;

    private MovieBLService movieBLService = MovieBLFactory.getMovieBLService();

    private ReviewSortType sortType;
    private String movieId;
    private List<ReviewVO> reviewVOs;

    public void setMovieInfoViewController(MovieInfoViewController movieInfoViewController) {
        this.movieInfoViewController = movieInfoViewController;
    }

    @FXML
    public void initialize() {
        setListPane();

        for (Node node : sortHBox.getChildren()) {
            SequenceButton sequenceButton = (SequenceButton) node;
            sequenceButton.setOnMouseClicked(event -> onClickSequenceButton(sequenceButton));
        }

        onClickSequenceButton((SequenceButton) sortHBox.getChildren().get(0));
    }

    public void showReviewsByMovieId(String movieId) {
        this.movieId = movieId;
        findReviewByPage();
    }

    private void onClickSequenceButton(SequenceButton sequenceButton) {
        for (Node node : sortHBox.getChildren()) {
            SequenceButton oldSequenceButton = (SequenceButton) node;
            if (oldSequenceButton == sequenceButton) {
                if (sequenceButton.getActive()) {
                    if (sequenceButton.getState() == SequenceButton.SequenceButtonState.Ascending) {
                        sequenceButton.setState(SequenceButton.SequenceButtonState.Descending);
                        sortType = sequenceButton == postDateButton ? ReviewSortType.DATE_DESC : ReviewSortType.HELPFULNESS_DESC;
                    } else {
                        sequenceButton.setState(SequenceButton.SequenceButtonState.Ascending);
                        sortType = sequenceButton == postDateButton ? ReviewSortType.DATE_ASC : ReviewSortType.HELPFULNESS_ASC;
                    }
                } else {
                    sequenceButton.setActive(true);
                    sequenceButton.setState(SequenceButton.SequenceButtonState.Descending);
                    sortType = sequenceButton == postDateButton ? ReviewSortType.DATE_DESC : ReviewSortType.HELPFULNESS_DESC;
                }

            } else {
                oldSequenceButton.setActive(false);
            }


            findReviewByPage();
        }
    }

    /* private */

    private void setListPane() {

        try {

            for (int i = 0; i < NUM_OF_CELL; i++) {
                FXMLLoader cellLoader = new FXMLLoader();
                cellLoader.setLocation(getClass().getResource("/component/ReviewCell.fxml"));
                HBox cell = cellLoader.load();

                ReviewCellController reviewCellController = cellLoader.getController();
                reviewCellController.setReviewListViewController(this);

                cellLoaders[i] = cellLoader;
                cells[i] = cell;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void pageChanged() {
        findReviewByPage();
    }

    private void findReviewByPage() {
        if (movieId == null) return;
        contentVBox.getChildren().clear();

        PageVO reviewPageVO = movieBLService.findReviewsByMovieIdInPageFromAmazon(movieId, sortType, pagePane.getCurrentPage() - 1);
        this.reviewVOs = reviewPageVO.list;
        pagePane.setPageCount(reviewPageVO.totalPage);
        pagePane.setCurrentPage(reviewPageVO.currentPage + 1);

        refreshList();
    }

    private void refreshList() {

        int length = Math.min(reviewVOs.size(), 10);

        for (int i = 0; i < length; i++) {
            FXMLLoader fxmlLoader = cellLoaders[i];
            ReviewCellController reviewCellController = fxmlLoader.getController();
            reviewCellController.setReview(reviewVOs.get(i));
            Node cell = cells[i];
            contentVBox.getChildren().add(cell);
        }

    }

    // TODO
    private void testList() {
        contentVBox.getChildren().clear();
        for (int i = 0; i < NUM_OF_CELL; i++) {
            contentVBox.getChildren().addAll(cells[i]);
        }
    }


}
