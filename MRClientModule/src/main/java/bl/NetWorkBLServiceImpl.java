package bl;

import blservice.NetWorkBLService;

/**
 * Created by vivian on 2017/4/16.
 */
public class NetWorkBLServiceImpl implements NetWorkBLService{
    private Network network;

    public boolean netWork(){
        return network.checkNetWork();
    }
}
