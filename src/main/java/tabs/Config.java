package tabs;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Config extends FileSerialization<Config.ConfigData> {

    private static final Config instance = new Config();

    public static Config get() {
        return Config.instance;
    }

    public static ConfigData data() {
        return Config.instance.data;
    }

    private Config() {
        super(Paths.get("config.json"));
        this.data = new ConfigData();
    }

    public static class ConfigData {

        private UI ui = new UI();
        private List<String> tabs = new ArrayList<>();

        public static class UI {
            private String theme;


            public String getTheme() {
                return theme;
            }

            public void setTheme(String theme) {
                this.theme = theme;
            }
        }

        public ConfigData() {
        }

        public UI getUi() {
            return ui;
        }

        public List<String> getTabs() {
            return tabs;
        }

        public void setTabs(List<String> tabs) {
            this.tabs = tabs;
        }
    }
}
