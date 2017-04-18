package ui.viewcontroller;

import bl.NetworkBLFactory;
import blservice.NetworkBLService;
import component.spinner.Spinner;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import java.util.concurrent.TimeUnit;

/**
 * Created by Sorumi on 17/4/16.
 */
public class NetworkViewController {

    @FXML
    private Spinner spinner;

    @FXML
    private StackPane failedPane;

    private MainViewController mainViewController;

    private NetworkBLService networkBLService = NetworkBLFactory.getNetworkBLService();

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void checkNetwork() {
        spinner.start();
        failedPane.setVisible(false);
        boolean isConnected = networkBLService.checkNetwork();

//        try {
//
//            TimeUnit.SECONDS.sleep(1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
        spinner.stop();
        spinner.setVisible(false);
        if (isConnected) {
            System.out.println("connect success");
            mainViewController.showMainView();
        } else {
            failedPane.setVisible(true);
            System.out.println("connect fail");
        }
    }
}
