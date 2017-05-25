package moviereview.service.impl;

import moviereview.bean.ActorBean;
import moviereview.bean.DirectorBean;
import moviereview.model.Actor;
import moviereview.model.Director;
import moviereview.repository.ActorRepository;
import moviereview.repository.DirectorRepository;
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

    public List<DirectorBean> findDirectorByKeyword(String keyword, int size, int page) {
        page--;
        return transformDirector(directorRepository.findDirectorByTitle("%" + keyword + "%", page * size, size));
    }

    public List<ActorBean> findActorByKeyword(String keyword, int size, int page) {
        page--;
        return transformActor(actorRepository.findActorByTitle("%" + keyword + "%", page * size, size));
    }

    private List<ActorBean> transformActor(List<Actor> actors) {
        ArrayList<ActorBean> actorBeans = new ArrayList<>();
        for (Actor actor : actors) {
//            actorBeans.add(new ActorBean(actor.getIdactor()));
        }
        return actorBeans;
    }

    private List<DirectorBean> transformDirector(List<Director> directors) {
        ArrayList<DirectorBean> directorBeans = new ArrayList<>();
        for (Director director : directors) {
//            directorBeans.add(new DirectorBean(director.getIddirector()));
        }
        return directorBeans;
    }

}
