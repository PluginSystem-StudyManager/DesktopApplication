package KanBan.Logic;

import java.util.List;

public interface IKanBan {

    public void addNote(Note note);

    public List<Note> getAllOpenNotes();

    public List<Note> getAllDoneNotes();

    public boolean deleteNote(Note note);

    public Note getUrgentNote();

    public boolean moveNoteToDone(Note note);

    public void deleteAllDoneNotes();
}
