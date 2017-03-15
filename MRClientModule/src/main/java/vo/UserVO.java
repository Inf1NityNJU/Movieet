package vo;

/**
 * Created by vivian on 2017/3/3.
 */
public class UserVO {
    String userID;
    String userName;
    int amountOfreview;
    int amountOfReviewedMovie;
    ReviewCountVO reviewCountVO;
    ReviewWordsVO reviewWordsVO;

    public UserVO(String userID, String userName, int amountOfreview, int amountOfReviewedMovie, ReviewCountVO reviewCountVO, ReviewWordsVO reviewWordsVO) {
        this.userID = userID;
        this.userName = userName;
        this.amountOfreview = amountOfreview;
        this.amountOfReviewedMovie = amountOfReviewedMovie;
        this.reviewCountVO = reviewCountVO;
        this.reviewWordsVO = reviewWordsVO;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAmountOfreview() {
        return amountOfreview;
    }

    public void setAmountOfreview(int amountOfreview) {
        this.amountOfreview = amountOfreview;
    }

    public int getAmountOfReviewedMovie() {
        return amountOfReviewedMovie;
    }

    public void setAmountOfReviewedMovie(int amountOfReviewedMovie) {
        this.amountOfReviewedMovie = amountOfReviewedMovie;
    }

    public ReviewCountVO getReviewCountVO() {
        return reviewCountVO;
    }

    public void setReviewCountVO(ReviewCountVO reviewCountVO) {
        this.reviewCountVO = reviewCountVO;
    }

    public ReviewWordsVO getReviewWordsVO() {
        return reviewWordsVO;
    }

    public void setReviewWordsVO(ReviewWordsVO reviewWordsVO) {
        this.reviewWordsVO = reviewWordsVO;
    }
}
