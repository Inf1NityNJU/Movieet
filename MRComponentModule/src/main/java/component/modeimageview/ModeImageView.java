package component.modeimageview;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

/**
 * Created by Sorumi on 17/3/28.
 */
public class ModeImageView extends ImageView {

    private ObjectProperty<ContentMode> mode = new SimpleObjectProperty<>();

    public enum ContentMode {
        Fit, Fill
    }

    public ModeImageView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModeImageView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectProperty<ContentMode> modeProperty() {
        return mode;
    }

    public ContentMode getMode() {
        return mode.getValue();
    }

    public void setMode(ContentMode mode) {

        if (this.getImage() != null) {
            Image image = this.getImage();
            double width = getFitWidth();
            double height = getFitHeight();
            double imageWidth = image.getWidth();
            double imageHeight = image.getHeight();

            double ratio = imageWidth / width > imageHeight / height ? imageHeight / height : imageWidth / width;
            width = width * ratio;
            height = height * ratio;

            if (mode == ContentMode.Fill) {
                Rectangle2D viewportRect = new Rectangle2D((imageWidth - width) / 2, (imageHeight - height) / 2, width, height);
                this.setViewport(viewportRect);
            }

        } else {
            this.setViewport(null);
        }

        this.mode.setValue(mode);
    }
}
