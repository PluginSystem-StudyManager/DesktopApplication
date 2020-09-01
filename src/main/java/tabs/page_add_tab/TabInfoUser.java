package tabs.page_add_tab;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tabs.*;

public class TabInfoUser extends TabInfo {

    private AddTabPage.ReloadList parentReload;

    public TabInfoUser(TabData tab, SingleChildLayout paneTabInfo, AddTabPage.ReloadList reloadList) {
        super(tab, paneTabInfo);
        this.parentReload = reloadList;
    }

    public void openDescription() {
        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();
        engine.load("http://127.0.0.1:8080/api/plugins/info/" + tab.name + "/html");
        paneTabInfo.set(browser);
        browser.setStyle("-fx-border-color: red; -fx-border-width: 2px");

        Sizing.setHeight(browser.prefHeightProperty(), paneTabInfo, 1);
        Sizing.setWidth(browser.prefWidthProperty(), paneTabInfo, 1);
    }

    public void installTab() {
        Tabs.TabWrapper tab = Tabs.installTab(this.tab);
        InstalledTabs.get().addTab(tab);

        parentReload.reloadList();
    }
}
