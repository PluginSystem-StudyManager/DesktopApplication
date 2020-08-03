package tabs;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSerialization<T> {
    protected T data;
    private final Path filePath;

    // TODO: get

    protected FileSerialization(Path filePath) {
        this.filePath = filePath;
    }

    public void save() {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        try {
            Files.write(filePath, json.getBytes());
            System.out.println("Saved config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LoadResult load() {
        Gson gson = new Gson();
        try {
            String json = Files.readString(filePath);
            try {
                data = (T) gson.fromJson(json, data.getClass());
            }catch (JsonSyntaxException e) {
                e.printStackTrace();
                return LoadResult.FORMAT_ERROR;
            }
        } catch (IOException e) {
            // TODO: logger no such file
            return LoadResult.NO_SUCH_FILE;
        }
        return LoadResult.LOADED;
    }

    public static enum LoadResult {
        LOADED, NO_SUCH_FILE, FORMAT_ERROR
    }

}
