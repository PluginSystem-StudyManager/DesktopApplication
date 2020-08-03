package GuiElements;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CustomWidget extends Pane {

    protected Pane fxml;

    public CustomWidget(String fxmlFile) {
        Locale locale = new Locale("en", "US");
        ResourceBundle bundle = ResourceBundle.getBundle("strings", locale);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile), bundle);
        loader.setRoot(this);
        loader.setController(this);

        try {
            fxml = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
