package moviereview.repository;

import moviereview.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Kray on 2017/5/16.
 */
public interface DirectorRepository extends JpaRepository<Director, String> {

    @Query(value = "SELECT iddirector FROM director WHERE iddirector LIKE ?1 LIMIT ?2, ?3", nativeQuery = true)
    public List<Director> findDirectorByTitle(String title, int start, int count);

    @Query(value = "select * from director where iddirector in" +
            "(select iddirector from is_director where idmovie = ?1)")
    public List<Director> findDirectorsByIdMovie(String idmovie);
}