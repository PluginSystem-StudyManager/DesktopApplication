package entryPoint.general_settings;

import GuiElements.TabSettings;
import GuiElements.TabWindow;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralSettingsController implements Initializable {

    @FXML private TabSettings tabSettings;
    @FXML private CheckBox cbResizeContent;
    @FXML private CheckBox cbCloseOnSelect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabSettings.parentProperty().addListener(l -> {
            TabWindow tabWindow = tabSettings.getTabWindow();
            cbResizeContent.setSelected(tabWindow.isContentResizing());
            cbCloseOnSelect.setSelected(tabWindow.isCloseMenuAfterSelect());
            tabWindow.contentResizingProperty().bind(cbResizeContent.selectedProperty());
            tabWindow.closeMenuAfterSelectProperty().bind(cbCloseOnSelect.selectedProperty());
        });
    }
}
