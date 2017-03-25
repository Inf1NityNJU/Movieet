import moviereview.model.MovieJson;
import org.junit.Test;
import moviereview.util.GsonUtil;

/**
 * Created by Kray on 2017/3/25.
 */
public class MovieJsonTest {

    @Test
    public void testTransformJson(){
        String json = "{\"Title\":\"Zootopia\",\"Year\":\"2016\",\"Rated\":\"PG\",\"Released\":\"04 Mar 2016\",\"Runtime\":\"108 min\",\"Genre\":\"Animation, Adventure, Comedy\",\"Director\":\"Byron Howard, Rich Moore, Jared Bush\",\"Writer\":\"Byron Howard (story by), Rich Moore (story by), Jared Bush (story by), Jim Reardon (story by), Josie Trinidad (story by), Phil Johnston (story by), Jennifer Lee (story by), Jared Bush (screenplay), Phil Johnston (screenplay)\",\"Actors\":\"Ginnifer Goodwin, Jason Bateman, Idris Elba, Jenny Slate\",\"Plot\":\"From the largest elephant to the smallest shrew, the city of Zootopia is a mammal metropolis where various animals live and thrive. When Judy Hopps becomes the first rabbit to join the police force, she quickly learns how tough it is to enforce the law. Determined to prove herself, Judy jumps at the opportunity to solve a mysterious case. Unfortunately, that means working with Nick Wilde, a wily fox who makes her job even harder.\",\"Language\":\"English\",\"Country\":\"USA\",\"Awards\":\"Nominated for 1 Oscar. Another 26 wins & 52 nominations.\",\"Poster\":\"https://images-na.ssl-images-amazon.com/images/M/MV5BOTMyMjEyNzIzMV5BMl5BanBnXkFtZTgwNzIyNjU0NzE@._V1_SX300.jpg\",\"Metascore\":\"78\",\"imdbRating\":\"8.1\",\"imdbVotes\":\"268,192\",\"imdbID\":\"tt2948356\",\"Type\":\"movie\",\"Response\":\"True\"}";
        MovieJson movieJson = GsonUtil.parseJson(json, MovieJson.class);
        System.out.println(movieJson.toString());
    }
}
