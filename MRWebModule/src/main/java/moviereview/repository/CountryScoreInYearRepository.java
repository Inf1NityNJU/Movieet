package moviereview.repository;

import moviereview.bean.CountryScoreInYearBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Kray on 2017/6/7.
 */
public interface CountryScoreInYearRepository extends JpaRepository<CountryScoreInYearBean, Integer> {

    @Query(value = "SELECT AVG(tmdb_movie.imdb_score) FROM tmdb_movie WHERE tmdbid IN (" +
            "SELECT tmdb_movie_country.tmdbid " +
            "FROM tmdb_movie_country " +
            "WHERE tmdb_movie_country.countryid_new = ?1" +
            ") AND YEAR(tmdb_movie.release_date) = ?2", nativeQuery = true)
    public double findCountryScoreInYear(int countryid, int year);

}
