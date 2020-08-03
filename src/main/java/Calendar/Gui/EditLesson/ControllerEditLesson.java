package Calendar.Gui.EditLesson;

import Calendar.Gui.ControllerCalender;
import Calendar.Gui.NewLesson.ControllerLesson;
import Calendar.Logic.Lesson;
import Calendar.Logic.Position;
import Calendar.Logic.Subject;
import Calendar.Logic.Timetable;
import entryPoint.SceneLoader;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerEditLesson implements Initializable {


    @FXML
    private AnchorPane anchorPaneEditLesson;


    @FXML
    private TableView<Subject> tableViewSubjects;

    @FXML
    private TableColumn colColor;

    @FXML
    private TableColumn colSubjectName;

    @FXML
    private TableColumn colLecturer;


    @FXML
    private TextField textFieldProfessor;

    @FXML
    private TextField textFieldSubject;

    @FXML
    private ColorPicker colorPickerSubjectColor;


    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonSave_Create;


    @FXML
    private Button buttonSaveAndBack;

    private Subject selectedSubject;
    private boolean buttonSave_Create_inEditMode = false;

    private ObservableList<Subject> subjectObservableList;

    private final Node nodeTabCalendar;
    private final Timetable timetable;
    private final GridPane gridPaneTimetable;
    private final VBox vBoxLesson;
    private final ControllerCalender controllerCalender;


    public ControllerEditLesson(Node node, Timetable timetable, GridPane gridPane, VBox vBoxLesson, ControllerCalender calender) {

        this.nodeTabCalendar = node;
        this.timetable = timetable;
        this.gridPaneTimetable = gridPane;
        this.vBoxLesson = vBoxLesson;
        this.controllerCalender = calender;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.subjectObservableList = timetable.getSubjectList();
        tableViewSubjects.setItems(this.subjectObservableList);
        bindDataToTableView();

        setDesignButtons();
        makeFadeInTransition(0,1, anchorPaneEditLesson);
    }

    private void makeFadeInTransition(int startValue, int targetValue, AnchorPane anchorPane) {

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(anchorPane);
        fadeTransition.setFromValue(startValue);
        fadeTransition.setToValue(targetValue);
        fadeTransition.play();
    }



    /**
     * ########################## Table View ##########################################################################
     */


    private void bindDataToTableView() {

        colSubjectName.setCellValueFactory(
                new PropertyValueFactory<Subject, String>("subjectName"));
        colLecturer.setCellValueFactory(
                new PropertyValueFactory<Subject, String>("professor"));
        colColor.setCellValueFactory(
                new PropertyValueFactory<Subject, String>("paneSubjectColor"));

    }


    public void setData() {

        Position position = getPosition(this.vBoxLesson);
        Lesson lesson = timetable.getLesson(position.getRow(), position.getCol());
        Subject subject = timetable.getSubject(lesson);

        int index = 0;
        for (Subject s : subjectObservableList) {

            if (subject.getId() == s.getId())
                break;
            index++;
        }

        tableViewSubjects.getSelectionModel().select(index);
    }


    private Position getPosition(VBox vBox) {

        return new Position(GridPane.getRowIndex(vBox), GridPane.getColumnIndex(this.vBoxLesson));
    }


    private void setDesignButtons() {


        buttonDesign("/Icons/icons8-edit-48.png", buttonEdit);
        buttonDesign("/Icons/icons8-delete-48.png", buttonDelete);
        buttonDesign("/Icons/icons8-plus-48.png", buttonSave_Create);
        buttonDesign("/Icons/icons8-back-48.png", buttonSaveAndBack);
    }


    private void buttonDesign(String path, Button button) {

        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);

        button.setGraphic(imageView);
        button.setId("buttonAddLesson");

    }


    /**
     * ###################################### TableView Events #######################################################
     */


    @FXML
    private void addNewSubject() {

        if (!buttonSave_Create_inEditMode) {
            String lessonColor = colorToHexCode(colorPickerSubjectColor.getValue());
            Subject subject = new Subject(lessonColor, textFieldProfessor.getText(), textFieldSubject.getText());
            clearFields();

            subjectObservableList.add(subject);
            timetable.addSubject(subject);
        }
        else {

            // Clear Fields.... Save Changes... Change Button
            saveSubjectChanges();
            buttonSave_Create_inEditMode = false;

            buttonSave_Create.setText("erstellen");
            buttonDesign("/Icons/icons8-plus-48.png", buttonSave_Create);
        }
    }

    private String colorToHexCode(Color color) {

        return "#" + color.toString().substring(2, 8);
    }


    private void clearFields() {

        textFieldProfessor.clear();
        textFieldSubject.clear();
    }



    @FXML
    private void editSubject() {

        this.selectedSubject = tableViewSubjects.getSelectionModel().getSelectedItem();

        if (selectedSubject != null) {
            textFieldSubject.setText(this.selectedSubject.getSubjectName());
            textFieldProfessor.setText(this.selectedSubject.getProfessor());
            colorPickerSubjectColor.setValue(Color.web(this.selectedSubject.getColor()));
        }

      buttonSave_Create_inEditMode = true;
      buttonSave_Create.setText("Speichern");
      buttonDesign("/Icons/icons8-save-48.png",buttonSave_Create);

    }



    @FXML
    private void saveSubjectChanges() {

        if (this.selectedSubject != null) {
            this.selectedSubject.setSubjectName(textFieldSubject.getText());
            this.selectedSubject.setProfessor(textFieldProfessor.getText());
            this.selectedSubject.setColor(colorToHexCode(colorPickerSubjectColor.getValue()));

            selectedSubject.notifyAllObservers();
            this.selectedSubject = null;
        }

        tableViewSubjects.getSelectionModel().select(null);
        clearFields();
    }




    @FXML
    private void deleteSubject() {

        Subject subject = tableViewSubjects.getSelectionModel().getSelectedItem();
        ArrayList<Position> positionArrayList;

        if (subject != null) {
            positionArrayList = subject.deleteAllObject();
            subjectObservableList.remove(subject);
            timetable.deleteSubject(subject);

            for (Position p : positionArrayList) {

                VBox vBox = controllerCalender.generateEmptyVBox(p.getCol(), p.getRow());
                gridPaneTimetable.add(vBox, p.getCol(), p.getRow());
            }
        }
    }


    /**
     * ###################################### Windows Navigation #######################################################
     */


    @FXML
    private void loadEditLessonWindow() {

        SceneLoader sceneLoader = SceneLoader.getInstance();
        sceneLoader.loadAnimationPopupWindow(buttonSaveAndBack, anchorPaneEditLesson,
                SceneLoader.CalendarScene.NEW_LESSON, new ControllerLesson(nodeTabCalendar, timetable, gridPaneTimetable, vBoxLesson, controllerCalender));

    }



    @FXML
    private void closeSubjectWindow() {

        nodeTabCalendar.setEffect(null);
        makeFadeInTransition(1, 0);
    }

    private void makeFadeInTransition(int startValue, int targetValue) {

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(700));
        fadeTransition.setNode(anchorPaneEditLesson);
        fadeTransition.setFromValue(startValue);
        fadeTransition.setToValue(targetValue);
        fadeTransition.play();
    }


}
