package moviereview.service.impl;

import moviereview.bean.User;
import moviereview.dao.DataHelper;
import moviereview.model.*;
import moviereview.util.MovieGenre;
import moviereview.util.RecommendType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by SilverNarcissus on 2017/5/12.
 */
public class RecommendServiceImpl {
    @Autowired
    private DataHelper<User> dataHelper;

    public ArrayList<MoviePO> everyDayRecommend(String UserId){
        return null;
    }

    public ArrayList<MoviePO> finishSeeingRecommend(String UserId, RecommendType type, String content){
        return null;
    }

    
    private MovieGenre favoriteGenre(UserPO user){
        ArrayList<Genre_factor> factors = new ArrayList<>(user.getGenre_factors());
        Collections.sort(factors);
        return factors.get(0).getMovieGenre();
    }

    private String favoriteActor(UserPO user){
        ArrayList<Actor_factor> factors = new ArrayList<>(user.getActor_factors());
        Collections.sort(factors);
        return factors.get(0).getName();
    }

    private String favoriteDirector(UserPO user){
        ArrayList<Director_factor> factors = new ArrayList<>(user.getDirector_factors());
        Collections.sort(factors);
        return factors.get(0).getName();
    }
}
