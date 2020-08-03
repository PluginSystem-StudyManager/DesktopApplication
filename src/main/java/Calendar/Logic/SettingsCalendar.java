package Calendar.Logic;

import java.time.LocalTime;

public class SettingsCalendar {

    private int currentSemester = 4;
    private String semesterName = "Software Engeneering";

    private int numberOfDays = 6;
    private int numberOfLessons = 5;

    private LocalTime startOfLessons = LocalTime.of(8, 0);
    private long shortBreakMin = 15;
    private long lunchBreakMin = 60;
    private int lunchBreakAfterNumberOfLessons = 3;
    private long durationOfLectures = 90;

    private int shortBreakInterval = 5;
    private int lunchBreakInterval = 5;
    private int durationOfLectureInterval = 10;

    private static SettingsCalendar single_instance;


    public static SettingsCalendar getInstance() {
        if (single_instance == null)
            single_instance = new SettingsCalendar();

        return single_instance;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public int getNumberOfLessons() {
        return numberOfLessons;
    }

    public void setNumberOfLessons(int numberOfLessons) {
        this.numberOfLessons = numberOfLessons;
    }

    public LocalTime getStartOfLessons() {
        return startOfLessons;
    }

    public void setStartOfLessons(LocalTime startOfLessons) {
        this.startOfLessons = startOfLessons;
    }

    public long getShortBreakMin() {
        return shortBreakMin;
    }

    public void setShortBreakMin(long shortBreakMin) {
        this.shortBreakMin = shortBreakMin;
    }

    public long getLunchBreakMin() {
        return lunchBreakMin;
    }

    public void setLunchBreakMin(long lunchBreakMin) {
        this.lunchBreakMin = lunchBreakMin;
    }

    public int getLunchBreakAfterNumberOfLessons() {
        return lunchBreakAfterNumberOfLessons;
    }

    public void setLunchBreakAfterNumberOfLessons(int lunchBreakAfterNumberOfLessons) {
        this.lunchBreakAfterNumberOfLessons = lunchBreakAfterNumberOfLessons;
    }

    public long getDurationOfLectures() {
        return durationOfLectures;
    }

    public void setDurationOfLectures(long durationOfLectures) {
        this.durationOfLectures = durationOfLectures;
    }

    public int getShortBreakInterval() {
        return shortBreakInterval;
    }

    public void setShortBreakInterval(int shortBreakInterval) {
        this.shortBreakInterval = shortBreakInterval;
    }

    public int getLunchBreakInterval() {
        return lunchBreakInterval;
    }

    public void setLunchBreakInterval(int lunchBreakInterval) {
        this.lunchBreakInterval = lunchBreakInterval;
    }

    public int getDurationOfLectureInterval() {
        return durationOfLectureInterval;
    }

    public void setDurationOfLectureInterval(int durationOfLectureInterval) {
        this.durationOfLectureInterval = durationOfLectureInterval;
    }
}
