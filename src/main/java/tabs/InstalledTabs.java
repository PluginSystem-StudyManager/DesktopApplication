package tabs;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class InstalledTabs {

    // TODO: move to async function (don't block at start)
    private final ObservableList<Tabs.TabWrapper> tabs;

    private List<String> tabNames = new ArrayList<>();

    public InstalledTabs() {
        this.tabs = FXCollections.observableList(Tabs.loadInstalledTabs());
        for (Tabs.TabWrapper t: tabs) {
            this.tabNames.add(t.tabData.name);
        }
    }

    private static final InstalledTabs instance = new InstalledTabs();

    public static InstalledTabs get() {
        return instance;
    }

    public void addListener(ListChangeListener<Tabs.TabWrapper> listener) {
        tabs.addListener(listener);
    }

    public List<Tabs.TabWrapper> getTabs() {
        return tabs;
    }

    public void addTab(Tabs.TabWrapper tab) {
        this.tabs.add(tab);
        this.tabNames.add(tab.tabData.name);
    }

    public boolean isInstalled(String tab) {
        return this.tabNames.contains(tab);
    }
}
