package moviereview.repository;

import moviereview.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 2017/5/16.
 */
public interface GenreRepository extends JpaRepository<Genre, String> {

    @Query(value = "select tmdbgenreid  from tmdb_movie_genre m where m.tmdbid = ?1", nativeQuery = true)
    public List<Integer> findGenreIdByIdMovie(int idmovie);

    @Query(value = "select tmdbgenre_en from tmdb_genre where tmdbgenreid = ?1", nativeQuery = true)
    public String findGenreById(int idgenre);

    @Query(value = "select score_avg from genre_avg where genre_id = ?1", nativeQuery = true)
    public double findAvgScoreEnById(int idgenre);

    @Query(value = "select cn_score_avg from genre_avg where genre_id = ?1", nativeQuery = true)
    public double findAvgScoreCnById(int idgenre);

    @Query(value = "select vote_cn from genre_avg where genre_id = ?1", nativeQuery = true)
    public double findAvgVoteEnById(int idgenre);

    @Query(value = "select vote_en from genre_avg where genre_id = ?1", nativeQuery = true)
    public double findAvgVoteCnById(int idgenre);

    @Query(value = "select box_office from genre_avg where genre_id = ?1", nativeQuery = true)
    public double findBoxOfficeById(int idgenre);


    /**
     * 找所有的类别
     *
     * @return
     */
    @Query(value = "SELECT tmdbgenreid FROM tmdb_genre", nativeQuery = true)
    public List<Integer> findAllGenreId();

    @Query(value = "SELECT min(factor) from user_genre_factor where user_id = ?1 OR user_id = ?2 " +
            "group by genre having count(factor) = 2", nativeQuery = true)
    public ArrayList<Double> getSimilarGenreFactor(int user1, int user2);

    @Query(value = "SELECT sum(factor) from user_genre_factor where user_id = ?1",
            nativeQuery = true)
    public Double getGenreFactor(int user);

}
