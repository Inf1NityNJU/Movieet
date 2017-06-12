package moviereview.service.impl;

import moviereview.repository.ActorRepository;
import moviereview.repository.DirectorRepository;
import moviereview.repository.MovieRepository;
import moviereview.service.PeopleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by vivian on 2017/6/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class PeopleServiceTest {
    @Autowired
    private PeopleService peopleService;

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ActorRepository actorRepository;

    @Test
    public void findAcorRank() {
        peopleService.getActorRank(5);
    }

    @Test
    public void directorAvgMovie() {
//        int movie = movieRepository.movieCountForDirector();
        int movie = 75267;
        System.out.println(movie);
        int director = directorRepository.directorCount();
        System.out.println(director);
        int avg = movie / director;
        System.out.println(avg);
    }

    @Test
    public void actorAvgMovie() {
        int movie = 304597;
        System.out.println(movie);
        int actor = 112116;
        System.out.println(actor);
        int avg = movie / actor;
        System.out.println(avg);
    }

    @Test
    public void addDirectorRank() {
        //导演平均有多少部电影
        int c = 1;
        //所有电影的votes*score的平均值(imdb score)
        double m = 68657.96;
        List<Integer> directorIds = directorRepository.findAllDirector();
        int count1 = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        for (Integer id : directorIds) {
                if (movieRepository.voteMulScoreSumForDirector(id) != null) {
                    double x = movieRepository.voteMulScoreSumForDirector(id);
                    int n = movieRepository.findMovieCountByDirector(directorRepository.findDirectorNameById(id));
                    double score = (c * m + x) / (n + c);
                    score = Double.parseDouble(df.format(score));
                    directorRepository.addRank(id, score);

                }
            System.err.println(count1);
            count1++;
        }
    }

    @Test
    public void addActorRank() {
        //演员平均有多少部电影
        int c = 2;
        //所有电影的votes*score的平均值(imdb score)
        double m = 68657.96;
        List<Integer> actorIds = actorRepository.findAllActor();
//        int count1 = 32455;
        DecimalFormat df = new DecimalFormat("#.##");
        for (int i = 45924; i<actorIds.size();i++ ) {
            int id = actorIds.get(i);
            if (movieRepository.voteMulScoreSumForActor(id) != null) {
                double x = movieRepository.voteMulScoreSumForActor(id);
                int n = movieRepository.findMovieCountByActor(actorRepository.findActorById(id));
                double score = (c * m + x) / (n + c);
                score = Double.parseDouble(df.format(score));
                actorRepository.addRank(id, score);
            }
            System.err.println(i);
//            count1++;
        }
    }
}
