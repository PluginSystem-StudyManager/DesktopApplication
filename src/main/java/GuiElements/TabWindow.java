package GuiElements;

import javafx.beans.property.*;
import javafx.geometry.Side;
import javafx.scene.control.Skin;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabWindow extends TabPane {

    private BooleanProperty showingText;


    public TabWindow() {
        this((Tab[])null);
    }

    public TabWindow(Tab... tabs) {
        super(tabs);
        this.setSide(Side.LEFT);
    }

    @Override protected Skin<?> createDefaultSkin() {
        return new TabPaneSkinSide(this);
    }

    public final void setShowingText(boolean value) {
        this.showingTextProperty().set(value);
    }

    public final boolean isShowingText() {
        return this.showingText != null && this.showingText.get();
    }

    public final BooleanProperty showingTextProperty() {
        if (this.showingText == null) {
            this.showingText = new SimpleBooleanProperty(this, "showingText", false);
        }
        return this.showingText;
    }

    private BooleanProperty showingSettingsGeneral;
    public void setShowingSettingsGeneral(boolean value) { showingSettingsGeneralProperty().set(value); }
    public boolean getShowingSettingsGeneral() { return showingSettingsGeneralProperty().get(); }

    public BooleanProperty showingSettingsGeneralProperty() {
        if (showingSettingsGeneral == null) {
            showingSettingsGeneral = new SimpleBooleanProperty(this, "showingSettingsGeneral", false);
        }
        return showingSettingsGeneral;
    }

    private BooleanProperty showingSettingsTab;
    public void setShowingSettingsTab(boolean value) { showingSettingsTabProperty().set(value); }
    public boolean getShowingSettingsTab() { return showingSettingsTabProperty().get(); }

    public BooleanProperty showingSettingsTabProperty() {
        if (showingSettingsTab == null) {
            showingSettingsTab = new SimpleBooleanProperty(this, "showingSettingsTab", false);
        }
        return showingSettingsTab;
    }

    private DoubleProperty imageSize;

    public final void setImageSize(double value) {
        this.imageSizeProperty().set(value);
    }

    public final double getImageSize() {
        return imageSizeProperty().get();
    }

    public final DoubleProperty imageSizeProperty() {
        if(imageSize == null) {
            imageSize = new SimpleDoubleProperty(this, "imageSize", 48);
        }
        return imageSize;
    }

    private BooleanProperty contentResizing;

    public final void setContentResizing(boolean value) {
        this.contentResizingProperty().set(value);
    }
    public final boolean isContentResizing() {
        return this.contentResizingProperty().get();
    }

    public BooleanProperty contentResizingProperty() {
        if(contentResizing == null) {
            contentResizing = new SimpleBooleanProperty(this, "contentResizing", false);
        }
        return contentResizing;
    }

    private BooleanProperty closeMenuAfterSelect;

    public void setCloseMenuAfterSelect(boolean value) { closeMenuAfterSelectProperty().set(value);}
    public boolean isCloseMenuAfterSelect() { return closeMenuAfterSelectProperty().get(); }

    public BooleanProperty closeMenuAfterSelectProperty() {
        if (closeMenuAfterSelect == null) {
            closeMenuAfterSelect = new SimpleBooleanProperty(this, "closeMenuAfterClose", true);
        }
        return closeMenuAfterSelect;
    }

    private ObjectProperty<TabSettings> settings;

    public void setSettings(TabSettings value) { settingsProperty().set(value); }
    public TabSettings getSettings() { return settingsProperty().get(); }

    public ObjectProperty<TabSettings> settingsProperty() {
        if (settings == null) {
            settings = new SimpleObjectProperty<>(this, "settings");
        }
        return settings;
    }

}
