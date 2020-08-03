package themes;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import tabs.Config;

import java.lang.ref.WeakReference;
import java.nio.file.Path;

public class ThemeLoader {

    private static ThemeLoader instance;
    private final ObservableList<WeakReference<Parent>> rootNodes = FXCollections.observableArrayList();
    private final ObservableList<Theme> themes = FXCollections.observableArrayList();
    private Theme currentTheme;
    private String[] defaultStylesheets;

    private ThemeLoader() {
        rootNodes.addListener((ListChangeListener<? super WeakReference<Parent>>) l -> {
            if(currentTheme == null) {
                return;
            }
            while(l.next()) {
                for(WeakReference<Parent> p : l.getAddedSubList()) {
                    Parent parent = p.get();
                    if(parent!= null) {
                        parent.getStylesheets().addAll(defaultStylesheets);
                        parent.getStylesheets().add(currentTheme.getStylesheet());
                    }
                }
            }
        });
    }

    public static ThemeLoader get() {
        if (instance == null) {
            instance = new ThemeLoader();
        }
        return instance;
    }

    public void addTheme(Theme theme) {
        if(currentTheme == null) {
            currentTheme = theme;
        }
        themes.add(theme);
    }

    public void addThemes(Theme... newThemes) {
        if(currentTheme == null && newThemes != null && newThemes.length > 0) {
            currentTheme = newThemes[0];
        }
        themes.addAll(newThemes);
    }

    public void addRootNode(Parent parent) {
        rootNodes.add(new WeakReference<>(parent));
    }

    public ObservableList<Theme> getThemes() {
        return themes;
    }

    public Theme getCurrentTheme() {
        return currentTheme;
    }

    public void loadFromPath(Path path) {
        // TODO: load css files. Name is id and displayName
    }

    public void setTheme(Theme theme) {
        Config.data().getUi().setTheme(theme.getId());
        String old = currentTheme.getStylesheet();
        currentTheme = theme;
        for(WeakReference<Parent> n : rootNodes) {
            Parent p = n.get();
            if (p != null) {
                p.getStylesheets().remove(old);
                p.getStylesheets().add(currentTheme.getStylesheet());
            }
        }
    }

    public void setTheme(String id) {
        for (Theme t : themes) {
            if(t.getId().equals(id)) {
                setTheme(t);
                break;
            }
        }
    }

    public void setPermanentStylesheets(String... stylesheets) {
        this.defaultStylesheets = stylesheets;
    }
}
