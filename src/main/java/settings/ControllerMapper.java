package settings;

import java.util.HashMap;

/**
 * Allows connecting a controller responsible for a settings page, to connect to another controller.
 * The order in which they add themselves and get is important. If used in a fxml file the one that registers itself
 * must come before the one that gets it.
 */
public class ControllerMapper {

    private final HashMap<String, Object> controllers;

    public ControllerMapper() {
        this.controllers = new HashMap<>();
    }

    public void registerController(String id, Object controller) {
        this.controllers.put(id, controller);
    }

    public Object getController(String id) {
        return this.controllers.get(id);
    }

    private static ControllerMapper instance;

    public static ControllerMapper get() {
        if (instance == null) {
            instance = new ControllerMapper();
        }
        return instance;
    }
}
