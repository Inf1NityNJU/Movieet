package bl;

import data.DataServiceFactory;
import dataservice.ReviewDataService;

/**
 * Created by vivian on 2017/4/16.
 */
public class Network {
    private ReviewDataService reviewDataService = DataServiceFactory.getJsonService();

    /**
     * 检查网络通信是否正常
     *
     * @return 网络通信正常与否
     */
    public boolean checkNetWork(){
        return reviewDataService.checkNetWork();
    }
}
