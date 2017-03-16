package bl;

import blservice.MovieBLService;

/**
 * Created by Kray on 2017/3/10.
 */
public class MovieBLFactory {

    private static Movie movie = new Movie();
    private static MovieBLService movieBLService;

    public synchronized static MovieBLService getMovieBLService() {
        if (movieBLService == null) {
            movieBLService = new MovieBLServiceImpl(movie);
        }
        return movieBLService;
    }
}
