package GuiElements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ButtonIcon extends Button {

    private ImageView imageView;

    public ButtonIcon(String url, double size) {
        this();
        setIcon(url);
        setSize(size);
    }

    public ButtonIcon() {
        super();
        getStyleClass().setAll("btn-icon-only");

        imageView = new ImageView();
        imageView.setPreserveRatio(false);
        iconProperty().addListener(e -> {
            imageView.setImage(new Image(getIcon()));
        });
        imageView.fitWidthProperty().bind(sizeProperty());
        imageView.fitHeightProperty().bind(sizeProperty());

        setGraphic(imageView);
    }

    private StringProperty icon;
    public void setIcon(String value) { iconProperty().set(value); }
    public String getIcon() { return iconProperty().get(); }

    public StringProperty iconProperty() {
        if (icon == null) {
            icon = new SimpleStringProperty(this, "icon");
        }
        return icon;
    }

    private DoubleProperty size;
    public void setSize(Double value) { sizeProperty().set(value); }
    public Double getSize() { return sizeProperty().get(); }

    public DoubleProperty sizeProperty() {
        if (size == null) {
            size = new SimpleDoubleProperty(this, "size");
        }
        return size;
    }


}
