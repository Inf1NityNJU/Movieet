package moviereview.dao.implementation;

import moviereview.dao.DataHelper;
import moviereview.dao.DataHelperFactory;
import moviereview.model.Card;
import org.junit.Test;

/**
 * Created by SilverNarcissus on 2017/5/5.
 */
public class HibernateHelperTest {
    DataHelper<Card> cardDataHelper = DataHelperFactory.getHibernateHelper(Card.class);

    @Test
    public void save() throws Exception {
        cardDataHelper.save(new Card(2, "Silver"));
    }

    @Test
    public void save1() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void exactlyQuery() throws Exception {

    }

    @Test
    public void fullMatchQuery() throws Exception {

    }

    @Test
    public void prefixMatchQuery() throws Exception {

    }

    @Test
    public void suffixMatchQuery() throws Exception {

    }

    @Test
    public void fuzzyMatchQuery() throws Exception {

    }

    @Test
    public void rangeQuery() throws Exception {

    }

    @Test
    public void multiCriteriaQuery() throws Exception {

    }

}