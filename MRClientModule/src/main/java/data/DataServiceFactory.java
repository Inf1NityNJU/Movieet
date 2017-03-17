package data;

import dataservice.ReviewDataService;

/**
 * Created by SilverNarcissus on 2017/3/17.
 */
public class DataServiceFactory {
    private static ReviewDataFromFileServiceImpl fileService;
    private static ReviewDataFromJsonServiceImpl jsonService;

    /**
     * 获得从json中读取数据的data service
     * @return data service
     */
    public synchronized static ReviewDataService getJsonService() {
        if (jsonService == null) {
            jsonService = new ReviewDataFromJsonServiceImpl();
        }
        return jsonService;
    }

    /**
     * 获得从文件中读取数据的data service
     * @param fileLocation 源文件路径
     * @return data service
     */
    public synchronized static ReviewDataService getFileService(String fileLocation) {
        if (fileService == null) {
            fileService = new ReviewDataFromFileServiceImpl(fileLocation);
        }
        return fileService;
    }
}
