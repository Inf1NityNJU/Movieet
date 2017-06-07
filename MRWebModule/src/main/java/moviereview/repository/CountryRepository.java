package moviereview.repository;

import moviereview.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by vivian on 2017/5/27.
 */
public interface CountryRepository extends JpaRepository<Genre, String> {
    @Query(value = "select countryid_new from tmdb_movie_country_copy where countryid in (select countryid from tmdb_movie_country where tmdbid = ?1)", nativeQuery = true)
    public List<Integer> findCountryIdByIdMovie(int idmovie);

    @Query(value = "select countryname from tmdb_country where countryid = ?1" ,nativeQuery = true)
    public String findCountryByCountryId(int countryid);
}
