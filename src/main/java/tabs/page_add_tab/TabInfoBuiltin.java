package tabs.page_add_tab;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tabs.InstalledTabs;
import tabs.SingleChildLayout;
import tabs.TabData;
import tabs.Tabs;

import java.io.IOException;

public class TabInfoBuiltin extends TabInfo{

    private String mainPageFile;
    private String settingsPageFile;
    private String iconPath;
    private String previewImg;

    public TabInfoBuiltin(TabData tab, SingleChildLayout paneTabInfo, String mainPageFile, String settingsPageFile,
                          String iconPath, String previewImg) {
        super(tab, paneTabInfo);
        this.mainPageFile = mainPageFile;
        this.settingsPageFile = settingsPageFile;
        this.iconPath = iconPath;
        this.previewImg = previewImg;
    }

    @Override
    public void openDescription() {
        VBox longDescription = new VBox();
        longDescription.setAlignment(Pos.CENTER);
        longDescription.setSpacing(10);

        Label lblHeader = new Label(tab.name);
        lblHeader.getStyleClass().add("header-1");
        longDescription.getChildren().add(lblHeader);

        Label lblDescription = new Label(tab.longDescription);
        lblDescription.setWrapText(true);
        longDescription.getChildren().add(lblDescription);

        ImageView imageView = new ImageView(new Image(previewImg));
        imageView.setFitWidth(500);
        imageView.setPreserveRatio(true);
        longDescription.getChildren().add(imageView);
        longDescription.maxWidthProperty().bind(paneTabInfo.widthProperty());
        paneTabInfo.set(longDescription);
    }

    @Override
    public void installTab() {
        FxmlController mainPage = loadFxml(mainPageFile);
        FxmlController settingsPage = loadFxml(settingsPageFile);
        Tabs.TabWrapper tabWrapper = new Tabs.TabWrapper(mainPage.controller, mainPage.fxml,
                settingsPage.controller, settingsPage.fxml, tab, this.iconPath);
        InstalledTabs.get().addTab(tabWrapper);
    }

    private FxmlController loadFxml(String filePath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filePath));
            Pane fxml = fxmlLoader.load();
            Object controller = fxmlLoader.getController();
            return new FxmlController(controller, fxml);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        return new FxmlController(null, null);
    }

    static class FxmlController {
        Object controller;
        Pane fxml;

        public FxmlController(Object controller, Pane fxml) {
            this.controller = controller;
            this.fxml = fxml;
        }
    }
}
