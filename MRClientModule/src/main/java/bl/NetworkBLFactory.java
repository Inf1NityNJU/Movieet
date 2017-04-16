package bl;

import blservice.MovieBLService;
import blservice.NetworkBLService;

/**
 * Created by Sorumi on 17/4/16.
 */
public class NetworkBLFactory {

    private static Network network = new Network();
    private static NetworkBLService networkBLService;

    public synchronized static NetworkBLService getNetworkBLService() {
        if (networkBLService == null) {
            networkBLService = new NetworkBLServiceImpl(network);
        }
        return networkBLService;
    }
}
