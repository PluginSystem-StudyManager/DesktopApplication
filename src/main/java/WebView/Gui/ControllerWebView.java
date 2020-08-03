package WebView.Gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerWebView implements Initializable {


    @FXML
    private AnchorPane anchorPaneWebview;

    @FXML private Button btnToggleEdit;

    private SplitWindow startView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        startView = new SplitWindow(editingProperty());
        anchorPaneWebview.getChildren().add(0, startView);
        AnchorPane.setTopAnchor(startView, 0.0);
        AnchorPane.setBottomAnchor(startView, 0.0);
        AnchorPane.setLeftAnchor(startView, 0.0);
        AnchorPane.setRightAnchor(startView, 0.0);

        btnToggleEdit.setOnMousePressed(e -> {
            setEditing(!isEditing());
        });
    }

    private BooleanProperty editing;
    public void setEditing(boolean value) { editingProperty().set(value); }
    public boolean isEditing() { return editingProperty().get(); }

    public BooleanProperty editingProperty() {
        if (editing == null) {
            editing = new SimpleBooleanProperty(this, "editing");
        }
        return editing;
    }


}
