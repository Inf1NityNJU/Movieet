package moviereview.service.impl;

import moviereview.bean.PeopleFull;
import moviereview.bean.PeopleMini;
import moviereview.model.Actor;
import moviereview.model.Director;
import moviereview.model.Page;
import moviereview.repository.ActorRepository;
import moviereview.repository.DirectorRepository;
import moviereview.service.MovieService;
import moviereview.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2017/5/16.
 */
@Service
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MovieService movieService;


    @Override
    public Page<PeopleMini> findDirectorByKeyword(String keyword, String orderBy, String order, int size, int page) {
        List<Integer> directorIds = new ArrayList<>();
        List<Integer> directorAll = new ArrayList<>();

        page--;
        if (order.toLowerCase().equals("asc")) {
            directorIds = directorRepository.findDirectorByKeywordPopularityAsc("%" + keyword + "%", size * page, size);
        } else {
            directorIds = directorRepository.findDirectorByKeywordPopularityDesc("%" + keyword + "%", size * page, size);
        }
        page++;

        if (directorIds != null) {
            directorAll = directorRepository.findDirectorByKeyword("%" + keyword + "%");
            return new Page<>(page, size, orderBy, order, directorAll.size(), this.peopleIdsToPeopleMiniList(directorIds, "d"));
        }
        return new Page<>(page, size, orderBy, order, 0, null);
    }

    @Override
    public Page<PeopleMini> findActorByKeyword(String keyword, String orderBy, String order, int size, int page) {
        List<Integer> actorIds = new ArrayList<>();
        List<Integer> actorAll = new ArrayList<>();
        page--;
        if (order.toLowerCase().equals("asc")) {
            actorIds = actorRepository.findActorByKeywordPopularityAsc("%" + keyword + "%", size * page, size);
        } else {
            actorIds = actorRepository.findActorByKeywordPopularityDesc("%" + keyword + "%", size * page, size);
        }
        page++;

        if (actorIds != null) {
            actorAll = actorRepository.findActorByKeyword("%" + keyword + "%");
            return new Page<>(page, size, orderBy, order, actorAll.size(), this.peopleIdsToPeopleMiniList(actorIds, "a"));
        }
        return new Page<>(page, size, orderBy, order, 0, null);
    }

    @Override
    public PeopleFull findDirectorById(int directorId) {
        Director director = directorRepository.findDirectorByDirectorId(directorId);
        if (director != null) {
            return new PeopleFull(director, movieService.findMoviesByDirector(director.getName()));
        }
        return null;
    }

    @Override
    public PeopleFull findActorById(int actorId) {
        Actor actor = actorRepository.findActorByActorId(actorId);
        if (actor != null) {
            return new PeopleFull(actor, movieService.findMoviesByActor(actor.getName()));
        }
        return null;
    }

    private List<PeopleMini> peopleIdsToPeopleMiniList(List<Integer> peopleIds, String people) {
        List<PeopleMini> peopleMinis = new ArrayList<>();
        if (peopleIds != null) {
            for (Integer id : peopleIds) {
                PeopleMini peopleMini = new PeopleMini();
                if (people.equals("d")) {
                    Director director = directorRepository.findDirectorByDirectorId(id);
                    peopleMini = new PeopleMini(id, director.getName(), director.getProfile());
                } else if (people.equals("a")) {
                    Actor actor = actorRepository.findActorByActorId(id);
                    peopleMini = new PeopleMini(id, actor.getName(), actor.getProfile());
                }
                peopleMinis.add(peopleMini);
            }
        }
        return peopleMinis;
    }

}
