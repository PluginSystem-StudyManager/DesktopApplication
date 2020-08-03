package Calendar.Logic;

import javafx.collections.ObservableList;

import java.util.List;

public interface ITimetable {


    public void addSubject(Subject subject);

    public Subject getSubject(Lesson lesson);

    public void deleteSubject(Subject subject);

    public ObservableList<Subject> getSubjectList();

    public void addLesson(Lesson lesson, int row, int col);

    public Lesson getLesson(int row, int col);

    public void deleteLesson(int row, int col);

    public List<String> getLocationList();

}
