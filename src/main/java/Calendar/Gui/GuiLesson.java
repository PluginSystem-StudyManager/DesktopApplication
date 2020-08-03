package Calendar.Gui;

import Calendar.Gui.NewLesson.ControllerLesson;
import Calendar.Logic.Position;
import Calendar.Logic.Subject;
import Calendar.Logic.Timetable;
import entryPoint.SceneLoader;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GuiLesson extends VBox {

    Label subjectName;
    Label lecturer;
    Label subjectLocation;
    String color;
    boolean tutorial;

    GridPane gridPaneCalendar;
    HBox hBoxEditButtons;

    ControllerCalender controllerCalender;
    Timetable timetable;


    public GuiLesson(Subject subject, String subjectLocation, GridPane pane, ControllerCalender calender, Timetable timetable, boolean tutorial) {

        this.subjectName = new Label(subject.getSubjectName());
        this.lecturer = new Label();
        this.tutorial = tutorial;
        checkTutorial(subject.getProfessor());

        this.subjectLocation = new Label(subjectLocation);
        this.color = subject.getColor();

        this.hBoxEditButtons = generateMenuButtons();
        addEventOnMouseOver();
        this.getChildren().addAll( this.subjectName, this.lecturer, this.subjectLocation);

        this.gridPaneCalendar = pane;
        this.controllerCalender = calender;
        this.timetable = timetable;
        designGuiLesson();
    }

    private void checkTutorial(String lecturer){

        if (this.tutorial) {
            this.lecturer.setText("Tutorium");
        } else {
            this.lecturer.setText(lecturer);
        }
    }


    private void designGuiLesson() {

        this.setId("SettingsLesson");
        this.setAlignment(Pos.CENTER);

        updateDesign();
       // generateContextMenu();
    }

    private void updateDesign() {

        this.setStyle("-fx-background-color: " + this.color);
        subjectName.setStyle("-fx-padding: 2 2 2 2; -fx-font-size: 1.5em; " +
                "-fx-background-color:" + this.color);

        this.lecturer.setStyle("-fx-padding: 3 5 3 5; -fx-background-color:" + this.color);
        this.subjectLocation.setStyle("-fx-padding: 3 5 3 5; -fx-background-color: " + this.color);
    }

    public void setSubjectName(String subjectName) {
        this.subjectName.setText(subjectName);
    }

    public void setLecturer(String lecturer) {
        this.lecturer.setText(lecturer);
    }

    public void setSubjectLocation(String subjectLocation) {
        this.subjectLocation.setText(subjectLocation);
    }

    public void setColor(String color) {

        this.color = color;
        updateDesign();

    }

    public void updateUserInformation(String subjectName, String lecturer, String subjectLocation, String color) {

        setSubjectName(subjectName);
        checkTutorial(lecturer);
        setSubjectLocation(subjectLocation);
        setColor(color);

    }

    public Position deleteGuiLesson() {

        Position position = new Position(GridPane.getRowIndex(this), GridPane.getColumnIndex(this));
        gridPaneCalendar.getChildren().remove(this);
        return position;

    }



    /**
     *
     *  create delete and edit Buttons for Gui-Lesson
     */

    private HBox generateMenuButtons(){

        HBox hBox = new HBox();
        hBox.setSpacing(7);
        hBox.setStyle("-fx-padding: 5,5,5,5");
        hBox.setAlignment(Pos.TOP_RIGHT);
        ImageView edit = createImageView("/Icons/icons8-edit-48.png");
        edit.setStyle("-fx-cursor: hand");
        generateEditLessonEvent(edit);
        ImageView delete = createImageView("/Icons/icons8-delete-48.png");
        delete.setStyle("-fx-cursor: hand");
        generateDeleteLessonEvent(delete);
        hBox.getChildren().addAll(edit ,delete);
        return hBox;
    }

    private ImageView createImageView(String imagePath){

        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);

        return imageView;

    }


    /**
     *   addEvent OnMouse Over
     */


    private void addEventOnMouseOver(){


        this.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        addButtons();
                    }
                });

        this.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        removeButtons();
                    }
                });
    }


    private void  addButtons(){

        this.getChildren().add(0,hBoxEditButtons);
    }


    private void  removeButtons(){

        this.getChildren().remove(hBoxEditButtons);
    }



 /**

    private void generateContextMenu() {

        ContextMenu contextMenu = new ContextMenu();

        MenuItem menuItemEditSubject = new MenuItem("Bearbeiten");
        generateEditLesson(menuItemEditSubject);
        MenuItem menuItemDelete = new MenuItem("Löschen");
        generateDeleteLessonEvent(menuItemDelete);

        contextMenu.getItems().addAll(menuItemEditSubject ,menuItemDelete);
        this.setOnMouseClicked(mouseEvent -> {

            contextMenu.show(this, Side.BOTTOM, -this.getHeight() *0.20, -this.getHeight() / 2);
        });
    }

*/

    private void generateEditLessonEvent(ImageView imageView){

        imageView.setOnMouseClicked(actionEvent -> {

            SceneLoader sceneLoader = SceneLoader.getInstance();
            ControllerLesson controllerLesson = new ControllerLesson(gridPaneCalendar, this.timetable, this.gridPaneCalendar, this, controllerCalender);
            sceneLoader.loadSceneInNewWindowWithoutButtons(SceneLoader.CalendarScene.NEW_LESSON, controllerLesson, this, 0.2, 0.2);
            controllerLesson.setData();
        });
    }



    private void generateDeleteLessonEvent(ImageView imageViewDelete) {

        imageViewDelete.setOnMouseClicked(actionEvent -> {

            Position position = new Position(GridPane.getRowIndex(this), GridPane.getColumnIndex(this));

            gridPaneCalendar.getChildren().remove(this);
            timetable.deleteLesson(position.getRow(), position.getCol());

            VBox vBox = controllerCalender.generateEmptyVBox(position.getCol(), position.getRow());
            gridPaneCalendar.add(vBox, position.getCol(), position.getRow());

        });
    }

}
