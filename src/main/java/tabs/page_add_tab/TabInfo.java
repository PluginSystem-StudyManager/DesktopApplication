package tabs.page_add_tab;

import GuiElements.CustomWidget;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tabs.*;

public abstract class TabInfo extends CustomWidget {

    @FXML
    private VBox container;

    @FXML
    private Label lblName;

    @FXML
    private Label lblShortDescription;

    protected TabData tab;
    protected SingleChildLayout paneTabInfo;


    public TabInfo(TabData tab, SingleChildLayout paneTabInfo) {
        super("tabInfo.fxml");
        this.tab = tab;
        this.paneTabInfo = paneTabInfo;

        lblName.setText(tab.name);
        lblShortDescription.setText(tab.shortDescription);
        lblShortDescription.prefWidthProperty().bind(this.widthProperty());

        this.setOnMouseClicked(e -> openDescription());
    }


    public abstract void openDescription();

    public abstract void installTab();

    private StringProperty name;
    public void setName(String value) { nameProperty().set(value); }
    public String getName() { return nameProperty().get(); }

    public StringProperty nameProperty() {
        return lblName.textProperty();
    }

    private StringProperty shortDescription;
    public void setShortDescription(String value) { shortDescriptionProperty().set(value); }
    public String getShortDescription() { return shortDescriptionProperty().get(); }

    public StringProperty shortDescriptionProperty() {
        return lblShortDescription.textProperty();
    }


}
