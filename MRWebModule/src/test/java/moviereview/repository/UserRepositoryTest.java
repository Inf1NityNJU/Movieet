package moviereview.repository;

import moviereview.model.ActorFactor;
import moviereview.model.DirectorFactor;
import moviereview.model.GenreFactor;
import moviereview.model.User;
import moviereview.util.MovieGenre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Sorumi on 17/5/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ActorFactorRepository actorFactorRepository;

    @Autowired
    DirectorFactorRepository directorFactorRepository;

    @Autowired
    GenreFactorRepository genreFactorRepository;

    @Test
    public void proxy() throws Exception {
        System.out.println(userRepository.getClass());
    }

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setUsername("1");
        user.setPassword("123");
        //
        ActorFactor actorFactor = new ActorFactor(1.7, "test");
        DirectorFactor directorFactor = new DirectorFactor(2,"test");
        GenreFactor genreFactor =new GenreFactor(5, MovieGenre.Action);
        Set<ActorFactor> actorFactors = new HashSet<>();
        actorFactors.add(actorFactor);
        Set<DirectorFactor> directorFactors = new HashSet<>();
        directorFactors.add(directorFactor);
        Set<GenreFactor> genreFactors=new HashSet<>();
        genreFactors.add(genreFactor);

        user.setActorFactors(actorFactors);
        user.setDirectorFactors(directorFactors);
        user.setGenreFactors(genreFactors);


        //genreFactorRepository.save(genreFactor);
        //actorFactorRepository.save(actorFactor);
        //directorFactorRepository.save(directorFactor);
        userRepository.save(user);
    }


    @Test
    public void findById() throws Exception {
        assertNotNull(userRepository.findUserById(1));
    }


}