package ui.viewcontroller;

import bl.NetworkBLFactory;
import blservice.NetworkBLService;
import component.spinner.Spinner;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

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

        spinner.stop();
        spinner.setVisible(false);
        if (isConnected) {
            mainViewController.showMainView();
        } else {
            failedPane.setVisible(true);
        }
    }
}
