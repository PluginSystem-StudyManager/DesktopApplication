package themes;

public class Theme {

    private final String displayName;
    private final String path;
    private final String id;

    public Theme(String displayName, String path, String id) {
        this.displayName = displayName;
        this.path = path;
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPath() {
        return path;
    }

    public String getId() {
        return id;
    }

    public String getStylesheet() {
        return path.toString();
    }
}
