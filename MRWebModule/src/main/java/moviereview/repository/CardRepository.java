package moviereview.repository;

import moviereview.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by SilverNarcissus on 2017/5/14.
 */
public interface CardRepository extends JpaRepository<Card, Integer> {
}
