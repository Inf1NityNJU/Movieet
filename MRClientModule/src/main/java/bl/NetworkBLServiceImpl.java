package bl;

import blservice.NetworkBLService;

/**
 * Created by vivian on 2017/4/16.
 */
public class NetworkBLServiceImpl implements NetworkBLService {
    private Network network;

    public NetworkBLServiceImpl(Network network) {
        this.network = network;
    }

    public boolean checkNetwork(){
        return network.checkNetWork();
    }
}
