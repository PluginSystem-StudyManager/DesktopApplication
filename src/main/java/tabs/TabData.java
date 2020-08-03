package tabs;

public class TabData {
    public String name;
    public String[] authors;
    public String shortDescription;
    public String longDescription;

    public TabData() {
    }

    public TabData(String name, String[] authors, String shortDescription, String longDescription) {
        this.name = name;
        this.authors = authors;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }
}
