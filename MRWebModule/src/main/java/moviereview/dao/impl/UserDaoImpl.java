package moviereview.dao.impl;

import moviereview.dao.UserDao;
import moviereview.dao.util.DataHelper;
import moviereview.model.User;
import moviereview.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Sorumi on 17/5/12.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private DataHelper<User> dataHelper;

    @Override
    public User findById(int id) {
        return dataHelper.exactlyQuery("id", id, User.class);
    }

    @Override
    public User findByUsername(String username) {
        return dataHelper.exactlyQuery("username", username, User.class);
    }

    @Override
    public ResultMessage create(User user) {
        return dataHelper.save(user, User.class);
    }
}
