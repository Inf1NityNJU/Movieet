package ui.componentcontroller;

import component.modeimageview.ModeImageView;
import component.ratestarpane.RateStarPane;
import component.taglabel.TagLabel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import ui.viewcontroller.ReviewListViewController;
import vo.ReviewVO;

/**
 * Created by Sorumi on 17/4/11.
 */
public class ReviewCellController {

    @FXML
    private ModeImageView avatarImageView;

    @FXML
    private Label nameLabel;

    @FXML
    private Label summaryLabel;

    @FXML
    private RateStarPane scoreStarPane;

    @FXML
    private Label dateLabel;

    @FXML
    private TagLabel helpfulnessLabel;

    @FXML
    private Text contentText;

    private ReviewListViewController reviewListViewController;

    private ReviewVO reviewVO;

    public void setReviewListViewController(ReviewListViewController reviewListViewController) {
        this.reviewListViewController = reviewListViewController;
    }

    @FXML
    public void initialize() {
        avatarImageView.setImage(new Image(getClass().getResource("/images/example.png").toExternalForm()));
        avatarImageView.setMode(ModeImageView.ContentMode.Fill);

        Rectangle clip = new Rectangle(
                avatarImageView.getFitWidth(), avatarImageView.getFitHeight()
        );
        clip.setArcWidth(avatarImageView.getFitWidth());
        clip.setArcHeight(avatarImageView.getFitHeight());
        avatarImageView.setClip(clip);
    }

    public void setReview(ReviewVO reviewVO) {
        this.reviewVO = reviewVO;
//        nameLabel.setText(reviewVO.);
        summaryLabel.setText(reviewVO.summary);
        scoreStarPane.setScore((double)reviewVO.score/2);
//        System.out.print(reviewVO.score + " ");
//        helpfulnessLabel.setText(reviewVO.);
        contentText.setText(reviewVO.context);
        dateLabel.setText(reviewVO.localDate.toString());
    }
}
