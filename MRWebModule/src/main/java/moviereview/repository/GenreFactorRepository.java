package moviereview.repository;

import moviereview.model.GenreFactor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SilverNarcissus on 2017/5/14.
 */
public interface GenreFactorRepository extends JpaRepository<GenreFactor, Integer> {
}
