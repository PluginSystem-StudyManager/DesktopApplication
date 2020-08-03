package KanBan.Gui;

import GuiElements.CustomWidget;
import KanBan.Logic.Note;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NoteWidget extends CustomWidget {

    @FXML
    private Label lblTitle;
    @FXML
    private Label lblDescription;

    public NoteWidget(Note note) {
        super("/KanBan/Gui/note.fxml");
        lblTitle.setText(note.getTitle());
        // TODO: get current step
        lblDescription.setText(note.getStep(0).getDescription());
    }
}
