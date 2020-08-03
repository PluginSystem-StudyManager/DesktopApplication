package Calendar.Logic;

import Calendar.Gui.GuiLesson;

public class Lesson implements IObserver {

    private final int subjectID;
    private String classroom;
    private Boolean tutorial;


    private final ISubject iSubject;
    private GuiLesson guiLesson;


    public Lesson(ISubject iSubject, GuiLesson guiLesson, String classroom, Boolean tutorial) {

        Subject subject = (Subject) iSubject;

        this.classroom = classroom;
        this.subjectID = subject.getId();
        this.tutorial = tutorial;
        this.iSubject = iSubject;
        this.guiLesson = guiLesson;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public int getSubjectID() {
        return subjectID;
    }


    @Override
    public void update() {

        Subject subject = (Subject) iSubject;
        guiLesson.updateUserInformation(subject.getSubjectName(), subject.getProfessor(), this.classroom, subject.getColor());
    }

    @Override
    public Position delete() {

        Position position = guiLesson.deleteGuiLesson();
        return position;
    }
}
