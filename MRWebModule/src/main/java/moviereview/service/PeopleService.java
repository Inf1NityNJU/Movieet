package moviereview.service;

import moviereview.bean.ActorBean;
import moviereview.bean.DirectorBean;

import java.util.List;

/**
 * Created by Kray on 2017/5/16.
 */
public interface PeopleService {

    public List<DirectorBean> findDirectorByKeyword(String keyword, int size, int page);

    public List<ActorBean> findActorByKeyword(String keyword, int size, int page);
}

