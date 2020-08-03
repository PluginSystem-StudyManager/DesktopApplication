package tabs;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TabsDataStore extends FileSerialization<TabsDataStore.TabsData> {

    private static final TabsDataStore instance = new TabsDataStore();

    public static TabsDataStore get() {
        return TabsDataStore.instance;
    }

    public static TabsData data() {
        return instance.data;
    }

    private TabsDataStore() {
        super(Paths.get("tabs_data.json"));
        this.data = new TabsData();
    }

    public static class TabsData {
        private final Map<String, TabData> tabs = new HashMap<>();

        public TabsData() {}

        public TabData get(String tabName) {
            return tabs.get(tabName);
        }

        public void add(TabData tab) {
            tabs.put(tab.name, tab);
        }
    }

}
