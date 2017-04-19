package bl;

/**
 * Created by vivian on 2017/4/18.
 * <p>
 * 该类用于比较目标电影与待选电影之间的评分差距
 * 实现Comparable接口，使得可排序
 * 在获得推荐电影时要用到该类
 */
public class MovieScoreCompare implements Comparable<MovieScoreCompare> {
    /**
     * 待选电影ID
     */
    public String movieId;

    /**
     * 该待选电影与目标电影之间的分差
     */
    public Double scoreDifference;

    public MovieScoreCompare(String movieId, Double scoreDifference) {
        this.movieId = movieId;
        this.scoreDifference = scoreDifference;
    }

    @Override
    public int compareTo(MovieScoreCompare o) {
        return this.scoreDifference.compareTo(o.scoreDifference);
    }
}
