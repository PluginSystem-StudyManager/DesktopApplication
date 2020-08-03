package entryPoint.mainPage;

import GuiElements.TabCustom;
import GuiElements.TabSettings;
import GuiElements.TabWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import tabs.InstalledTabs;
import tabs.Tabs;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerMainPage implements Initializable{

    @FXML TabWindow tabWindow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Tabs.TabWrapper> tabs = InstalledTabs.get().getTabs();
        addTabs(tabs);
        InstalledTabs.get().addListener(change -> {
            while (change.next()) {
                if(change.wasPermutated()) {
                    for(int i = change.getFrom(); i < change.getTo(); i++) {
                        // TODO: permutate
                    }
                }
                else if(change.wasUpdated()) {
                    // TODO: update
                }
                else {
                    removeTabs(change.getRemoved());
                    addTabs(change.getAddedSubList());
                }
            }
        });
    }

    private void removeTabs(List<? extends Tabs.TabWrapper> removed) {
        for (Tabs.TabWrapper tab: removed) {
            // TODO: Only works as long, as tab has only name as text
            tabWindow.getTabs().removeIf(tab1 -> tab1.getText().equals(tab.tabData.name));
        }
    }

    private void addTabs(List<? extends Tabs.TabWrapper> tabs) {
        int i = tabWindow.getTabs().size() - 1;
        for(Tabs.TabWrapper tab : tabs) {
            // TODO: Allow reorder tabs
            // append before + (add) tab
            if (tab != null) {
                tabWindow.getTabs().add(i++, newTab(tab));
            }
        }
        tabWindow.getSelectionModel().select(i - 1);
    }

    private Tab newTab(Tabs.TabWrapper tab) {
        TabCustom tabWidget = new TabCustom();
        tabWidget.setContent(tab.fxmlMain);
        tabWidget.setText(tab.tabData.name);
        tabWidget.setIconPath(tab.iconPath);
        TabSettings settings = new TabSettings();
        settings.setContent(tab.fxmlSettings);
        tabWidget.setSettingsNode(settings);
        return tabWidget;
    }
}
