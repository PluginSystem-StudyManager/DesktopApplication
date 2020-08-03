package Calendar.Gui;

import Calendar.Gui.NewLesson.ControllerLesson;
import Calendar.Logic.Position;
import Calendar.Logic.SettingsCalendar;
import Calendar.Logic.Timetable;
import Calendar.Logic.Weekdays;
import entryPoint.SceneLoader;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import themes.ThemePickerWidget;
import settings.ControllerMapper;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class ControllerCalender implements Initializable {

    @FXML
    private AnchorPane anchorPaneCalendar;

    @FXML
    private Label labelCurrentSemester;

    @FXML
    private Label labelCourseOfStudies;

    @FXML
    private Label labelCurrentTimeAndDate;

    @FXML
    private GridPane gridPaneTimetable;

    private double cellPercentageWidth;
    private double cellPercentageHeight;

    private int numberOfDays = 5;
    private int numberOfLessons = 6;

    private LocalTime startOfLessons;
    private long shortBreakMin = 15;
    private long lunchBreakMin = 60;
    private int lunchBreakAfterNumberOfLessons = 3;
    private long durationOfLectures = 90;

    private Timetable timetable = new Timetable();


    public ControllerCalender() {


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ControllerMapper.get().registerController("calendar", this);

        updateValuesFromSettings();
        DateTimeFormatter f = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        startOfLessons.format(f);

        getTimeAndDate();
        updateCalendar(false);
    }

    /**
     * ###################### GUI Initialization #######################################################################
     */


    public void updateCalendar(boolean userUpdateTimetable) {

        if (userUpdateTimetable)
            deleteRowsAndColumnsFromGridPane();
        updateValuesFromSettings();
        generateGridPaneTimetable(gridPaneTimetable);
        generateLabelsDays();
        generateLabelTimes();
        generateEmptyLessons();
    }


    private void deleteRowsAndColumnsFromGridPane() {

        ObservableList list = gridPaneTimetable.getChildren();
        gridPaneTimetable.getChildren().removeAll(list);

        while (gridPaneTimetable.getRowConstraints().size() > 0) {
            gridPaneTimetable.getRowConstraints().remove(0);
        }

        while (gridPaneTimetable.getColumnConstraints().size() > 0) {
            gridPaneTimetable.getColumnConstraints().remove(0);
        }
    }

    private void updateValuesFromSettings() {

        SettingsCalendar settings = SettingsCalendar.getInstance();

        labelCurrentSemester.setText("IN  " + settings.getCurrentSemester());
        labelCourseOfStudies.setText(settings.getSemesterName());

        this.numberOfDays = settings.getNumberOfDays();
        this.numberOfLessons = settings.getNumberOfLessons();
        this.startOfLessons = settings.getStartOfLessons();
        this.shortBreakMin = settings.getShortBreakMin();
        this.lunchBreakMin = settings.getLunchBreakMin();
        this.lunchBreakAfterNumberOfLessons = settings.getLunchBreakAfterNumberOfLessons();
        this.durationOfLectures = settings.getDurationOfLectures();

    }


    // neuer Thread / Task der immer die Uhrzeit abfragt und updatet
    // THread muss in eine Liste eingetragen werden, um sauber mit dem Programm beendet werden zu können.. !!!


    /**
     * ########## generate Headline for Timetable ##########
     */

    private void getTimeAndDate() {

    }

    private void generateGridPaneTimetable(GridPane gridPane) {

        cellPercentageWidth = 100 / numberOfDays;
        cellPercentageHeight = 100 / numberOfLessons;

        for (int i = 0; i <= numberOfDays; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(cellPercentageWidth);
            gridPane.getColumnConstraints().add(col);
        }

        for (int i = 0; i <= numberOfLessons; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(cellPercentageHeight);
            gridPane.getRowConstraints().add(row);
        }
    }


    /**
     * ########## generate TIMETABLE days / Times ##########
     */

    private void generateLabelsDays() {

        int dayCounter = 1;
        for (Weekdays dayName : Weekdays.values()) {
            Label labelDay = new Label(dayName.toString());
            labelDay.setId("labelDays");

            gridPaneTimetable.add(labelDay, dayCounter, 0);
            GridPane.setHalignment(labelDay, javafx.geometry.HPos.CENTER);
            dayCounter++;
            if (dayCounter > numberOfDays) break;
        }
    }

    private void generateLabelTimes() {

        int lessonCounter = 0;
        for (int i = 1; i <= numberOfLessons; i++) {

            String time;
            if (i == 1)
                time = startOfLessons.plusMinutes(durationOfLectures * (i - 1)) + " - " + startOfLessons.plusMinutes(durationOfLectures + durationOfLectures * lessonCounter);
            else if (i > 1 && i <= lunchBreakAfterNumberOfLessons)
                time = startOfLessons.plusMinutes(durationOfLectures * (i - 1) + (shortBreakMin * lessonCounter)) + " - " + startOfLessons.plusMinutes((shortBreakMin * lessonCounter) + durationOfLectures + durationOfLectures * lessonCounter);
            else if (i == lunchBreakAfterNumberOfLessons + 1)
                time = startOfLessons.plusMinutes(durationOfLectures * (i - 1) + (shortBreakMin * (lessonCounter - 1) + lunchBreakMin)) + " - " + startOfLessons.plusMinutes((shortBreakMin * (lessonCounter - 1) + lunchBreakMin) + durationOfLectures + durationOfLectures * lessonCounter);
            else
                time = startOfLessons.plusMinutes(durationOfLectures * (i - 1) + (shortBreakMin * lessonCounter) + lunchBreakMin) + " - " + startOfLessons.plusMinutes((shortBreakMin * lessonCounter + lunchBreakMin) + durationOfLectures + durationOfLectures * lessonCounter);
            Label labelTime = new Label(time);
            labelTime.setId("labelTimes");
            lessonCounter++;

            gridPaneTimetable.add(labelTime, 0, i);
            GridPane.setHalignment(labelTime, javafx.geometry.HPos.CENTER);
        }
    }


    /**
     * ########## generate Empty Lessons ##########
     */


    private void generateEmptyLessons() {

        for (int i = 1; i <= numberOfDays; i++) {
            for (int j = 1; j <= numberOfLessons; j++) {

                VBox vBoxEmpty = generateEmptyVBox(i, j);
                gridPaneTimetable.add(vBoxEmpty, i, j);
            }
        }
    }


    public VBox generateEmptyVBox(int day, int block) {

        VBox vBoxLessonBasicLayout = new VBox();
        vBoxLessonBasicLayout.setAlignment(Pos.CENTER);
        timetable.setEmptyLesson(new Position(block, day), vBoxLessonBasicLayout);

        vBoxLessonBasicLayout.setId("SettingsEmptyLesson");
        generateButtonAddLesson(vBoxLessonBasicLayout);
        vBoxLessonBasicLayout.setBackground(new Background(new BackgroundFill(Color.rgb(135, block * 15, day * 10),
                new CornerRadii(13),
                new Insets(0.0, 0.0, 0.0, 0.0))));

        return vBoxLessonBasicLayout;
    }

    private void generateButtonAddLesson(VBox emptyVBox) {

        Image image = new Image("/Icons/icons8-plus-48.png");
        ImageView imageView = new ImageView(image);
        imageView.setStyle("-fx-cursor: hand");
        imageView.setFitWidth(35);
        imageView.setFitHeight(35);

        emptyVBox.getChildren().add(imageView);
        generateEventAddLesson(imageView, emptyVBox);

    }


    private void generateEventAddLesson(ImageView imageView, VBox emptyVBox) {

        imageView.setOnMouseClicked(actionEvent -> {

            SceneLoader sceneLoader = SceneLoader.getInstance();
            ControllerLesson controllerLesson = new ControllerLesson(this.anchorPaneCalendar, this.timetable,
                    this.gridPaneTimetable, emptyVBox, this);
            sceneLoader.loadSceneInNewWindowWithoutButtons(SceneLoader.CalendarScene.NEW_LESSON, controllerLesson,
                    anchorPaneCalendar,0.2, 0.2);
        });

    }

}
