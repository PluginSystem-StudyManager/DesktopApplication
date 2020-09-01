package tabs.page_add_tab;


import GuiElements.ButtonIcon;
import GuiElements.CustomWidget;
import entryPoint.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import tabs.*;

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
    @FXML ScrollPane scrollPaneUserPlugins;
    @FXML Pane containerTabInfo;
    @FXML ProgressIndicator progressIndicator;
    @FXML VBox containerError;

    private SingleChildLayout tabInfoPane;

    public AddTabPage() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabInfoPane = new SingleChildLayout();
        containerTabInfo.getChildren().add(tabInfoPane);
        loadAvailableTabs();

        // Standard tabs
        TabData tabDataWebsite = new TabData("Webseite", new String[]{},
                "Ein Fenster in das Webseiten geladen werden",
                "Ein Fenster in das Webseiten geladen werden. Diese können beliebig angeordnet werden.");
        TabInfo tabInfoWebsite = new TabInfoBuiltin(tabDataWebsite, tabInfoPane,
                "/WebView/Gui/WebView.fxml", "/WebView/Gui/settings.fxml",
                "/Icons/icons8-web-64.png", "/images/website-plugin-preview.png");
        containerStandardPlugins.getChildren().add(tabInfoWebsite);

        TabData tabDataCalendar= new TabData("Vorlesungsplan", new String[]{},
                "Dein komplett individualisierbarer Vorlesungsplan.",
                "Dein komplett individualisierbarer Vorlesungsplan. Schnelles " +
                        "erstellen, bearbeiten und anzeigen des Vorlesungsplans mit allen nötigen Informationen.");
        TabInfo tabInfoCalendar = new TabInfoBuiltin(tabDataCalendar, tabInfoPane,
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
    }

    private void loadAvailableTabs() {

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
                    Main.application.getHostServices().showDocument("http://127.0.0.1:8080");
                });
                showError(true, lblNoTabs, link);
            } else {
                // Show all tabs
                clearPane(containerUserPlugins);
                for(TabData tab : availableTabs) {
                    if(!InstalledTabs.get().isInstalled(tab.name)) {
                        containerUserPlugins.getChildren().add(new TabInfoUser(tab, tabInfoPane, this::loadAvailableTabs));
                    }
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
