package GuiElements;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;

public class TabSettingsSkin extends SkinBase<TabSettings> {

    protected TabSettingsSkin(TabSettings control) {
        super(control);
        Node content = getSkinnable().getContent();
        if (content != null) {
            getChildren().add(content);
        }
    }
}
