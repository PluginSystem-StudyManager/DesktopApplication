package GuiElements;

import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

import java.beans.EventHandler;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

@DefaultProperty("content")
public class TabSettings extends Control {

    private TabWindow tabWindow;

    @Override
    protected Skin<?> createDefaultSkin() {
        return new TabSettingsSkin(this);
    }

    private ObjectProperty<Node> content;
    public void setContent(Node value) { contentProperty().set(value); }
    public Node getContent() { return contentProperty().get(); }

    public ObjectProperty<Node> contentProperty() {
        if (content == null) {
            content = new SimpleObjectProperty<>(this, "content");
        }
        return content;
    }

    private Consumer<Void> closeSettingsAction;

    public void setCloseSettingsAction(Consumer<Void> e) {
        this.closeSettingsAction = e;
    }

    public void closeSettings() {
        closeSettingsAction.accept(null);
    }

    public void setTabWindow(TabWindow tabWindow) {
        this.tabWindow = tabWindow;
    }

    public TabWindow getTabWindow() {
        return tabWindow;
    }
}
