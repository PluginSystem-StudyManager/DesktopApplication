package themes;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ThemePickerWidget extends ComboBox<Theme> {

    public ThemePickerWidget() {
        super(ThemeLoader.get().getThemes());
        Callback<ListView<Theme>, ListCell<Theme>> cellFactory = f -> new ListCell<Theme>() {
            @Override
            protected void updateItem(Theme item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getDisplayName());
                    setOnMouseClicked(e -> {

                    });
                }
            }
        };
        setCellFactory(cellFactory);
        setValue(ThemeLoader.get().getCurrentTheme());
        setButtonCell(cellFactory.call(null));
        valueProperty().addListener(l -> {
            ThemeLoader.get().setTheme(getValue());
        });
    }
}
