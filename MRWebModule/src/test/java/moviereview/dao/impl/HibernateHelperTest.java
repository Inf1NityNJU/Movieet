package moviereview.dao.impl;

import junit.framework.TestCase;
import moviereview.dao.DataHelper;
import moviereview.dao.DataHelperFactory;
import moviereview.model.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by SilverNarcissus on 2017/5/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class HibernateHelperTest extends TestCase {

    @Autowired
    DataHelper<Card> cardDataHelper;
//    DataHelper<Card> cardDataHelper = DataHelperFactory.getHibernateHelper(Card.class);

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