package moviereview.service;

import moviereview.bean.PeopleMini;
import moviereview.model.Page;

/**
 * Created by Kray on 2017/5/16.
 */
public interface PeopleService {

    public Page<PeopleMini> findDirectorByKeyword(String keyword, String orderBy, String order, int size, int page);

    public Page<PeopleMini> findActorByKeyword(String keyword, String orderBy, String order, int size, int page);
}

