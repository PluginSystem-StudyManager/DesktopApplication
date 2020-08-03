package entryPoint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tabs.Config;
import tabs.TabsDataStore;
import themes.Theme;
import themes.ThemeLoader;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public static Main application;

    @Override
    public void start(Stage primaryStage) throws Exception{
        application = this;

        // Load files
        TabsDataStore.get().load();
        Config.get().load();

        // Theme initiation
        ThemeLoader.get().addThemes(
                new Theme("Dark", "/css/theme_dark.css", "dark"),
                new Theme("Light", "/css/theme_light.css", "light"),
                new Theme("Material", "/css/theme_material.css", "material"),
                new Theme("Reddish", "/css/theme_reddish.css", "reddish")
        );
        ThemeLoader.get().setPermanentStylesheets("/css/MainDesign.css", "/css/css_classes.css");
        String theme = Config.data().getUi().getTheme();
        if (theme != null && theme.length() > 0) {
            ThemeLoader.get().setTheme(theme);
        } else {
            ThemeLoader.get().setTheme("dark");
        }


        setUserAgentStylesheet(getClass().getResource("debug_styles.css").toString());

        Locale locale = new Locale("en", "US");
        ResourceBundle bundle = ResourceBundle.getBundle("strings", locale);
        Parent root = FXMLLoader.load(getClass().getResource("mainPage/MainPage.fxml"), bundle);

        ThemeLoader.get().addRootNode(root);


        primaryStage.setTitle("Time Manager");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        Config.get().save();
        super.stop();
    }
}
