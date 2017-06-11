package moviereview.service.impl;

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

    @Test
    public void findAcorRank() {
        peopleService.getActorRank(5);
    }

    @Test
    public void directorAvgMovie() {
        int movie = movieRepository.movieCount();
        System.out.println(movie);
        int director = directorRepository.directorCount();
        System.out.println(director);
        int avg = movie / director;
        System.out.println(avg);
    }

    @Test
    public void addDirectorRank() {
        //导演平均有多少部电影
        int c = 1;
        //所有电影的votes*score的平均值(imdb score)
        double m = 68657.96;
        List<Integer> directorIds = directorRepository.findAllDirector();
//        Map<Integer, Double> directorIdAndScore = new HashMap<>();
        int count1 = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        for (Integer id : directorIds) {
                if (movieRepository.voteMulScoreAvgForDirector(id) != null) {
                    double x = movieRepository.voteMulScoreAvgForDirector(id);
                    int n = movieRepository.findMovieCountByDirector(directorRepository.findDirectorNameById(id));
                    double score = (c * m + x) / (n + c);
//                directorIdAndScore.put(id, score);
                    score = Double.parseDouble(df.format(score));
                    directorRepository.addRank(id, score);

                }
            System.err.println(count1);
            count1++;
        }

    }
}
