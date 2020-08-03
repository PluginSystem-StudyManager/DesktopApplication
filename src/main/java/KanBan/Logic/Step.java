package KanBan.Logic;

public class Step {

    private String title;
    private boolean status;
    private String description;

    public Step(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void finish() {
        this.status = true;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public boolean isFinished() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
