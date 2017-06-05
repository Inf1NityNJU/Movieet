package moviereview.service;

import moviereview.bean.PeopleFull;
import moviereview.bean.PeopleMini;
import moviereview.model.Page;

/**
 * Created by Kray on 2017/5/16.
 */
public interface PeopleService {

    public Page<PeopleMini> findDirectorByKeyword(String keyword, String orderBy, String order, int size, int page);

    public Page<PeopleMini> findActorByKeyword(String keyword, String orderBy, String order, int size, int page);

    public PeopleFull findDirectorById(int directorId);

    public PeopleFull findActorById(int actorId);

    public Page<PeopleMini> getDirectorRank(int size);

    public Page<PeopleMini> getActorRank(int size);
}

