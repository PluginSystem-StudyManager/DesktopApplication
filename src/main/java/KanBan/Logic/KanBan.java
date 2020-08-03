package KanBan.Logic;

import java.util.ArrayList;
import java.util.List;

public class KanBan implements IKanBan{

    private List<Note> openNotes = new ArrayList<>();
    private List<Note> doneNotes = new ArrayList<>();

    @Override
    public void addNote(Note note) {
        openNotes.add(note);
        InsertionSort.sort(openNotes);
    }

    @Override
    public List<Note> getAllOpenNotes() {
        return openNotes;
    }

    @Override
    public List<Note> getAllDoneNotes() {
        return doneNotes;
    }

    @Override
    public boolean deleteNote(Note note) {
        return openNotes.remove(note) || doneNotes.remove(note);
    }

    @Override
    public void deleteAllDoneNotes() {
        doneNotes.clear();
    }

    @Override
    public Note getUrgentNote() {
        return openNotes.get(0);
    }

    @Override
    public boolean moveNoteToDone(Note note) {
        if (!openNotes.contains(note))
            return false;
        openNotes.remove(note);
        doneNotes.add(note);
        return true;
    }
}
