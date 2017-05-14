package moviereview.dao.impl;

import junit.framework.TestCase;
import moviereview.model.Card;
import moviereview.repository.CardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;

/**
 * Created by SilverNarcissus on 2017/5/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/applicationContext.xml"})
public class HibernateHelperTest extends TestCase {

//    @Autowired
//    DataHelper<Card> cardDataHelper;
//
//    @Autowired
//    DataHelper<AnotherCard> anotherCardDataHelper;
//
//    @Autowired
//    DataHelper<User> userDataHelper;
//    DataHelper<Card> cardDataHelper = DataHelperFactory.getHibernateHelper(Card.class);
    @Autowired
    CardRepository cardRepository;

    @Test
    public void save() throws Exception {
//        cardDataHelper.save(new Card(3, "Silver", LocalDate.now()), Card.class);
//
//
//        anotherCardDataHelper.save(new AnotherCard(1, "Narcissus"), AnotherCard.class);
//
//
//        ArrayList<AnotherCard> cards = anotherCardDataHelper.fullMatchQuery("name", "Narcissus", AnotherCard.class);
//
//        for (AnotherCard card : cards) {
//            System.out.println(card.getName());
//        }
    }

    @Test
    public void save1() throws Exception {
        //userDataHelper.save(new User(1, "a", "123"), User.class);
    }

    @Test
    public void save2() throws Exception {
        cardRepository.save(new Card(3, "Silver", LocalDate.now()));
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