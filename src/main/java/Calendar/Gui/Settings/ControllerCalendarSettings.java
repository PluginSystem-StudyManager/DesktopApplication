package Calendar.Gui.Settings;

import Calendar.Gui.ControllerCalender;
import Calendar.Logic.SettingsCalendar;
import GuiElements.SettingsController;
import GuiElements.TabSettings;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import settings.ControllerMapper;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCalendarSettings implements Initializable, SettingsController {

    @FXML
    private Label labelInformation;

    @FXML
    private AnchorPane anchorPaneSettings;

    private TabSettings tabSettings;

    @FXML
    private ComboBox<Integer> comboBoxCurrentSemester;

    @FXML
    private TextField textFieldSemesterName;

    @FXML
    private ComboBox<Integer> comboBoxNumberOfDays;

    @FXML
    private ComboBox<Integer> comboBoNumberOfLessons;


    @FXML
    private ComboBox<Integer> comboBoxShortBreakMin;

    @FXML
    private ComboBox<Integer> comboBoxLunchBreakMin;

    @FXML
    private ComboBox<Integer> comboBoxLunchBreakAfterNumberOfLessons;

    @FXML
    private ComboBox<Integer> comboBoxDurationOfLectures;

    private int currentSemester;
    private String semesterName;

    private int numberOfDays;
    private int numberOfLessons;
    private int shortBreak;
    private int lunchBreakMin;
    private int lunchBreakAfterNumberOfLessons;
    private int durationOfLectures;

    private SettingsCalendar instance_Calendar = SettingsCalendar.getInstance();
    private ControllerCalender controllerCalender;

    private ObservableList<Integer> numberOfSemestersObservableList = FXCollections.observableArrayList();

    private ObservableList<Integer> numberOfDaysObservableList = FXCollections.observableArrayList();
    private ObservableList<Integer> numberOfLessonsObservableList = FXCollections.observableArrayList();

    private ObservableList<Integer> shortBreakObservableList = FXCollections.observableArrayList();
    private ObservableList<Integer> lunchBreakObservableList = FXCollections.observableArrayList();
    private ObservableList<Integer> lunchBreakAfterNumberOfLessonsObservableList = FXCollections.observableArrayList();
    private ObservableList<Integer> durationOfLecturesObservableList = FXCollections.observableArrayList();


    public ControllerCalendarSettings() {
        this.numberOfDays = instance_Calendar.getNumberOfDays();
        this.numberOfLessons = instance_Calendar.getNumberOfLessons();
        this.shortBreak = (int) instance_Calendar.getShortBreakMin();
        this.lunchBreakMin = (int) instance_Calendar.getLunchBreakMin();
        this.lunchBreakAfterNumberOfLessons = instance_Calendar.getLunchBreakAfterNumberOfLessons();
        this.durationOfLectures = (int) instance_Calendar.getDurationOfLectures();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPaneSettings.parentProperty().addListener(l -> {
            this.tabSettings = (TabSettings) anchorPaneSettings.getParent();
        });
        this.controllerCalender = (ControllerCalender) ControllerMapper.get().getController("calendar");


        comboBoxCurrentSemester.setItems(numberOfSemestersObservableList);

        comboBoxNumberOfDays.setItems(numberOfDaysObservableList);
        comboBoNumberOfLessons.setItems(numberOfLessonsObservableList);

        comboBoxShortBreakMin.setItems(shortBreakObservableList);
        comboBoxLunchBreakMin.setItems(lunchBreakObservableList);
        comboBoxLunchBreakAfterNumberOfLessons.setItems(lunchBreakAfterNumberOfLessonsObservableList);
        comboBoxDurationOfLectures.setItems(durationOfLecturesObservableList);

        makeFadeInTransition(0, 1);
        filComboboxWithContent();
        setSelectedSettingsInComboBoxes();

    }

    /**
     * ##################################### Settings Calendar ########################################################
     */

    private void updateSettings(){

        instance_Calendar.setCurrentSemester(comboBoxCurrentSemester.getValue());
        instance_Calendar.setSemesterName(textFieldSemesterName.getText());

        instance_Calendar.setNumberOfDays(comboBoxNumberOfDays.getValue());
        instance_Calendar.setNumberOfLessons(comboBoNumberOfLessons.getValue());

        instance_Calendar.setShortBreakMin(comboBoxShortBreakMin.getValue());
        instance_Calendar.setLunchBreakMin(comboBoxLunchBreakMin.getValue());
        instance_Calendar.setLunchBreakAfterNumberOfLessons(comboBoxLunchBreakAfterNumberOfLessons.getValue());
        instance_Calendar.setDurationOfLectures(comboBoxDurationOfLectures.getValue());
    }


    private void setSelectedSettingsInComboBoxes(){

        comboBoxCurrentSemester.getSelectionModel().select(instance_Calendar.getCurrentSemester() -1);

        textFieldSemesterName.setText(instance_Calendar.getSemesterName());

        comboBoxNumberOfDays.getSelectionModel().select(instance_Calendar.getNumberOfDays()-1);
        comboBoNumberOfLessons.getSelectionModel().select(instance_Calendar.getNumberOfLessons()-1);

        comboBoxShortBreakMin.getSelectionModel().select((int) instance_Calendar.getShortBreakMin() / instance_Calendar.getShortBreakInterval() - 1);
        comboBoxLunchBreakMin.getSelectionModel().select((int) (instance_Calendar.getLunchBreakMin() / instance_Calendar.getLunchBreakInterval() - 1));

        comboBoxLunchBreakAfterNumberOfLessons.getSelectionModel().select(instance_Calendar.getLunchBreakAfterNumberOfLessons() - 1);
        comboBoxDurationOfLectures.getSelectionModel().select((int) (instance_Calendar.getDurationOfLectures() / instance_Calendar.getDurationOfLectureInterval() -1));
    }


    /**
     * ##################################### generate Gui elements #####################################################
     */


    private void filComboboxWithContent() {

        addElementsToTheObservableList(numberOfSemestersObservableList, 12, 1);

        addElementsToTheObservableList(numberOfDaysObservableList, 7, 1);
        addElementsToTheObservableList(numberOfLessonsObservableList, 8, 1);

        addElementsToTheObservableList(shortBreakObservableList, 25, instance_Calendar.getShortBreakInterval());
        addElementsToTheObservableList(lunchBreakObservableList, 70, instance_Calendar.getLunchBreakInterval());
        addElementsToTheObservableList(lunchBreakAfterNumberOfLessonsObservableList, 5, 1);
        addElementsToTheObservableList(durationOfLecturesObservableList, 100, instance_Calendar.getDurationOfLectureInterval());
    }


    private void addElementsToTheObservableList(ObservableList<Integer> observableList, int maximum, int interval) {

        for (int i = 0; i <= maximum; i = i + interval) {

            if (i == 0)
                continue;
            observableList.add(i);
        }
    }


    /**
     * ################################   Window Navigation #############################################################
     */

    private void makeFadeInTransition(int startValue, int targetValue) {

        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(800));
        fadeTransition.setNode(anchorPaneSettings);
        fadeTransition.setFromValue(startValue);
        fadeTransition.setToValue(targetValue);
        fadeTransition.play();
    }


    @FXML
    private void closeWindow() {
        tabSettings.closeSettings();
    }

    @FXML void saveButtons(){
        updateSettings();
        controllerCalender.updateCalendar(true);
        closeWindow();
    }


    @Override
    public ControllerCalender getController() {
        return this.controllerCalender;
    }

    @Override
    public void setController(Object controller) {
        this.controllerCalender = (ControllerCalender) controller;
    }
}


