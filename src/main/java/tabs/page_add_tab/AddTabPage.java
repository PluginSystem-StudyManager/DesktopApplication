package tabs.page_add_tab;


import GuiElements.ButtonIcon;
import entryPoint.Main;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tabs.InstalledTabs;
import tabs.SingleChildLayout;
import tabs.TabData;
import tabs.Tabs;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Page that shows all available Tabs
 */
public class AddTabPage extends StackPane implements Initializable {

    @FXML
    private Pane root;

    @FXML FlowPane containerStandardPlugins;
    @FXML FlowPane containerUserPlugins;
    @FXML ScrollPane scrollPaneStandardPlugins;
    @FXML
    ScrollPane scrollPaneUserPlugins;
    @FXML
    SingleChildLayout containerTabInfo;
    @FXML
    ProgressIndicator progressIndicator;
    @FXML
    VBox containerError;
    @FXML
    ButtonIcon btnReload;

    public AddTabPage() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAvailableTabs();

        // Standard tabs
        TabData tabDataWebsite = new TabData("Webseite", new String[]{},
                "Ein Fenster in das Webseiten geladen werden",
                "Ein Fenster in das Webseiten geladen werden. Diese können beliebig angeordnet werden.");
        TabInfo tabInfoWebsite = new TabInfoBuiltin(tabDataWebsite, containerTabInfo,
                "/WebView/Gui/WebView.fxml", "/WebView/Gui/settings.fxml",
                "/Icons/icons8-web-64.png", "/images/website-plugin-preview.png");
        containerStandardPlugins.getChildren().add(tabInfoWebsite);

        TabData tabDataCalendar = new TabData("Vorlesungsplan", new String[]{},
                "Dein komplett individualisierbarer Vorlesungsplan.",
                "Dein komplett individualisierbarer Vorlesungsplan. Schnelles " +
                        "erstellen, bearbeiten und anzeigen des Vorlesungsplans mit allen nötigen Informationen.");
        TabInfo tabInfoCalendar = new TabInfoBuiltin(tabDataCalendar, containerTabInfo,
                "/Calendar/Gui/Calender.fxml", "/Calendar/Gui/Settings/CalendarSettings.fxml",
                "/Icons/icons8-zeitplan-64.png", "/images/calendar-plugin-preview.png");
        containerStandardPlugins.getChildren().add(tabInfoCalendar);

        // Sizing
        scrollPaneStandardPlugins.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            containerStandardPlugins.prefWrapLengthProperty().setValue(newValue.getWidth());
        });
        scrollPaneUserPlugins.viewportBoundsProperty().addListener((observable, oldValue, newValue) -> {
            containerUserPlugins.prefWrapLengthProperty().setValue(newValue.getWidth());
        });

        // Events
        btnReload.setOnMouseClicked(e -> loadAvailableTabs());
    }

    public void loadAvailableTabs() {

        Task<List<TabData>> task = new Task<>() {
            @Override
            protected List<TabData> call() throws Exception {
                return Tabs.getAvailableTabs();
            }
        };
        task.setOnRunning(v -> {
            showError(false);
            showProgress(true);
        });
        task.setOnCancelled(value -> {
            showNoConnection();
        });
        task.setOnFailed(v -> {
            showNoConnection();
        });
        task.setOnSucceeded(v -> {
            showProgress(false);
            List<TabData> availableTabs = task.getValue();
            if (availableTabs.size() == 0) {
                // Show no tabs available
                Label lblNoTabs = new Label("No Plugins available");
                Hyperlink link = new Hyperlink("Create new plugin");
                link.setOnAction(e -> {
                    // TODO: Correct link
                    Main.application.getHostServices().showDocument("http://127.0.0.1:8080/devguide");
                });
                showError(true, lblNoTabs, link);
            } else {
                int shownTabs = 0;
                // Show all tabs
                clearPane(containerUserPlugins);
                for (TabData tab : availableTabs) {
                    if (!InstalledTabs.get().isInstalled(tab.name)) {
                        containerUserPlugins.getChildren().add(new TabInfoUser(tab, containerTabInfo, this::loadAvailableTabs));
                        shownTabs++;
                    }
                }
                if (shownTabs == 0) { // All available tabs are already installed
                    Label lblNoTabs = new Label("You have already installed all available plugins.");
                    Hyperlink link = new Hyperlink("But you can create a new one.");
                    link.setOnAction(e -> {
                        // TODO: Correct link
                        Main.application.getHostServices().showDocument("http://127.0.0.1:8080/devguide");
                    });
                    showError(true, lblNoTabs, link);
                }
            }
        });
        new Thread(task).start();


    }

    private void showError(boolean show, Node... children) {
        containerError.setVisible(show);
        clearPane(containerError);
        for (Node n : children) {
            containerError.getChildren().add(n);
        }
    }

    private void showNoConnection() {
        showProgress(false);
        Label lblError = new Label("No connection to the server. Try again later.");
        ButtonIcon btnTryAgain = new ButtonIcon("/Icons/reload-48.png", 24);


        btnTryAgain.setOnMouseClicked(mouseEvent -> {
            loadAvailableTabs();
        });
        showError(true, lblError, btnTryAgain);
    }

    private void showProgress(boolean show) {
        progressIndicator.setVisible(show);
    }

    private void clearPane(Pane pane) {
        pane.getChildren().remove(0, pane.getChildren().size());
    }

    public static interface ReloadList {
        void reloadList();
    }

}
