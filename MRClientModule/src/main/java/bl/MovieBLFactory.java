package bl;

/**
 * Created by Kray on 2017/3/10.
 */
public class MovieBLFactory {

    private static Movie movie = new Movie();
    private static MovieBLServiceImpl movieBLService;

    public synchronized static MovieBLServiceImpl getMovieBLService(){
        if(movieBLService == null){
            movieBLService = new MovieBLServiceImpl(movie);
        }
        return movieBLService;
    }
}
