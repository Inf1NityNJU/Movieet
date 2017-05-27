package moviereview.repository;

import moviereview.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by vivian on 2017/5/27.
 */
public interface KeywordRepository extends JpaRepository<Genre, String> {
    @Query(value = "select keywordid from tmdb_movie_keyword where tmdbid = ?1", nativeQuery = true)
    public List<Integer> findKeywordIdByMovieId(int movieId);

    @Query(value = "select keyword_en from tmdb_keyword where keywordid = ?1" ,nativeQuery = true)
    public String findKeywordByKeywordId(int keywordid);
}
