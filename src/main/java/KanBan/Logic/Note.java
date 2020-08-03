package KanBan.Logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Note {

    private String title;
    private int stepCount;
    private int priority;
    private List<Step> steps = new ArrayList<>();
    private List<String> stepDescriptions = new ArrayList<>();
    private Date dueDate;
    private List<File> attachments = new ArrayList<>();
    private int currentStep = 1;
    private boolean noteStatus;


    public Note(String title, int stepCount, Date dueDate) {
        this.title = title;
        this.stepCount = stepCount;
        this.dueDate = dueDate;
    }

    public Note(String title, int stepCount, Date dueDate, int priority) {
        this.title = title;
        this.stepCount = stepCount;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public boolean addStepData(String title, String description){
        steps.add(new Step(title,description));
        return true;
    }

    public void addAttachment(File file){
        attachments.add(file);
    }

    public void addAttachments(List<File> files){
        attachments.addAll(files);
    }

    // TODO: move to done somewhere
    public boolean finishStep(int step){
        if(steps.get(step).isFinished())
            return false;
        steps.get(step).finish();
        if(step == stepCount)
            finishNote();
        currentStep++;
        return true;
    }

    public String getTitle() {
        return title;
    }

    public int getStepCount() {
        return stepCount;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Step getStep(int step) {
        return steps.get(step);
    }

    public Date getDueDate() {
        return dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public boolean isNoteFinished() {
        return noteStatus;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    private void finishNote(){
        noteStatus = true;
    }
}
