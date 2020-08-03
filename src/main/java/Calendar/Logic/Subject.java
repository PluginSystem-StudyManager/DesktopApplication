package Calendar.Logic;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Subject implements ISubject {

    private static int idGenerator = 0;

    private final int id;
    private ArrayList<Lesson> lessons = new ArrayList<>();
    private SimpleStringProperty color;
    private SimpleStringProperty professor;
    private SimpleStringProperty subjectName;
    private Pane paneSubjectColor;

    private ArrayList<IObserver> lessonObserver = new ArrayList<>();

    public Subject(String professor, String subjectName) {

        this.professor = new SimpleStringProperty(professor);
        this.subjectName = new SimpleStringProperty(subjectName);
        this.id = idGenerator++;
    }

    public Subject(String color, String professor, String subjectName) {

        this(professor, subjectName);
        this.color = new SimpleStringProperty(color);
        this.paneSubjectColor = new Pane();

        paneSubjectColor.setId("paneSubjectColor");
        paneSubjectColor.setStyle("-fx-background-color:" + color);
    }


    public int getId() {
        return id;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Pane getPaneSubjectColor() {
        return paneSubjectColor;
    }

    public void setPaneSubjectColor(Pane paneSubjectColor) {
        this.paneSubjectColor = paneSubjectColor;
    }

    public String getColor() {

        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    /* set Color also updates Color of pane */
    public void setColor(String color) {
        this.color.set(color);
        paneSubjectColor.setStyle("-fx-background-color:" + color);
    }

    public String getProfessor() {
        return professor.get();
    }

    public SimpleStringProperty professorProperty() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor.set(professor);
    }

    public String getSubjectName() {
        return subjectName.get();
    }

    public SimpleStringProperty subjectNameProperty() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName.set(subjectName);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", lessons=" + lessons +
                ", color=" + color +
                ", professor='" + professor + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }


    @Override
    public void notifyAllObservers() {

        for (IObserver observer : lessonObserver) {
            observer.update();
        }
    }

    @Override
    public void registriesObservers(IObserver observer) {

        lessonObserver.add(observer);
    }

    @Override
    public void deregisterObservers(IObserver observer) {

        lessonObserver.remove(observer);
    }

    @Override
    public ArrayList<Position> deleteAllObject() {

        ArrayList<Position> position = new ArrayList<>();

        for (IObserver observer : lessonObserver) {

            position.add(observer.delete());
        }
        return position;
    }
}
