package tabs;

import javafx.scene.Node;
import javafx.scene.layout.Region;

public class SingleChildLayout extends Region {

    public void set(Node node) {
        if (getChildren().size() > 0) {
            getChildren().remove(0);
        }
        getChildren().add(node);
    }
}
