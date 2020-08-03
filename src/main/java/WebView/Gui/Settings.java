package WebView.Gui;

import GuiElements.TabSettings;
import GuiElements.TabWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Settings implements Initializable {

    @FXML
    TextField tfTabName;
    @FXML
    Pane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.parentProperty().addListener(l -> {
            TabSettings tabSettings = (TabSettings) root.getParent();
            TabWindow tabWindow = tabSettings.getTabWindow();
            for (Tab t : tabWindow.getTabs()) {
                if (t.isSelected()) {
                    tfTabName.setText(t.getText());
                    t.textProperty().bindBidirectional(tfTabName.textProperty());
                }
            }
        });
    }
}
