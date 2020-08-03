package tabs;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletionException;

// TODO: handle no connection to server, 404, wrong formatted data

public class Tabs {

    private static final Tabs instance = new Tabs();
    private final File tabsFolder;

    public Tabs() {
        String rootDir = System.getProperty("user.dir");
        tabsFolder = new File(rootDir + "/installed_tabs/");
        if (!tabsFolder.exists()) {
            boolean created = tabsFolder.mkdir();
            if (!created) {
                System.err.println("Could not create Tabs folder");
            }
        }
    }

    public static List<TabWrapper> loadInstalledTabs() {
        return Tabs.instance._loadInstalledTabs();
    }

    public static List<TabData> getAvailableTabs() throws ConnectException {
        return Tabs.instance._getAvailableTabs();
    }

    public static TabWrapper installTab(TabData tab) {
        return Tabs.instance._installTab(tab);
    }

    private TabWrapper _installTab(TabData tabData) {
        // Serialize in files
        TabsDataStore.data().add(tabData);
        TabsDataStore.get().save();
        Config.data().getTabs().add(tabData.name);
        Config.get().save();

        // get jar from server
        // TODO: handle jar not found
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://127.0.0.1:8080/plugins/download/" + tabData.name))
                .build();
        Path jarPath = new File(tabsFolder.toURI().getPath() + tabData.name + ".jar").toPath();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofFile(jarPath))
                .thenAccept(res -> System.out.println(res.statusCode()))
                .join();

        // load
        return loadJar(new File(jarPath.toUri()), tabData);
    }

    private List<TabWrapper> _loadInstalledTabs() {
        List<TabWrapper> tabsList = new ArrayList<>();
        List<String> installedTabs = Config.data().getTabs();
        for(String tabName : installedTabs) {
            // Filename must be {tabName}.jar
            File jarFile = new File(tabsFolder, tabName + ".jar");
            TabWrapper tab = loadJar(jarFile, TabsDataStore.data().get(tabName));
            if (tab != null) {
                tabsList.add(tab);
            }
        }
        return tabsList;
    }

    private TabWrapper loadJar(File jarFile, TabData tabData) {
        try {
            // Create class loader for tab
            URLClassLoader loader = new URLClassLoader(
                    new URL[]{jarFile.toURI().toURL()},
                    this.getClass().getClassLoader());

            // Get plugin load info
            InputStream in = loader.getResourceAsStream("plugin_load.json");
            if (in == null) {
                // TODO: log invalid plugin
                return null;
            }
            Scanner s = new Scanner(in).useDelimiter("\\A");
            String json = s.hasNext() ? s.next() : "";
            in.close();
            Gson gson = new Gson();
            LoadInfoData data = gson.fromJson(json, LoadInfoData.class);
            String windowFilePath = data.windowFxml;
            String settingsFilePath = data.settingsFxml;

            // Load window fxml file
            URL fxmlWindow = loader.getResource(windowFilePath);
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlWindow);
            fxmlLoader.setClassLoader(loader);
            Pane fxml = fxmlLoader.load();

            // Load settings fxml file
            URL settingsWindow = loader.getResource(settingsFilePath);
            FXMLLoader fxmlLoaderSettings = new FXMLLoader(settingsWindow);
            fxmlLoaderSettings.setClassLoader(loader);
            Pane fxmlSettings = fxmlLoaderSettings.load();

            // Get controller
            Object controller = fxmlLoader.getController();
            Object settingsController = fxmlLoaderSettings.getController(); // TODO: change type. Catch null
            return new TabWrapper(controller, fxml, settingsController, fxmlSettings, tabData, "");
            // TODO: Add iconpath

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private List<TabData> _getAvailableTabs() throws ConnectException {
        List<TabData> availableTabs = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://127.0.0.1:8080/plugins/list/"))
                .header("Content-Type", "application/json")
                .build();
        try {
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(s -> {
                        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
                        AllPluginsData allPluginsData = gson.fromJson(s, AllPluginsData.class);
                        availableTabs.addAll(allPluginsData.plugins.values());
                    })
                    .join();
        }catch (CompletionException e) {
            throw new ConnectException(e.getMessage());
        }
        return availableTabs;
    }

    public static class TabWrapper {
        public final Object controllerMain;
        public final Pane fxmlMain;
        public final TabData tabData;
        public final Object settingsController;
        public final Pane fxmlSettings;
        public final String iconPath;

        public TabWrapper(Object controllerMain, Pane fxmlMain, Object settingsController, Pane fxmlSettings,
                          TabData tabData, String iconPath) {
            this.controllerMain = controllerMain;
            this.fxmlMain = fxmlMain;
            this.tabData = tabData;
            this.settingsController = settingsController;
            this.fxmlSettings = fxmlSettings;
            this.iconPath = iconPath;
        }
    }

    static class LoadInfoData {

        String windowFxml;
        String settingsFxml;

        public LoadInfoData() {
        }
    }

    static class AllPluginsData {
        HashMap<String, TabData> plugins;

        public AllPluginsData() {
        }
    }

}
