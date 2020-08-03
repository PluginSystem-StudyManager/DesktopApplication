package tabs;


import java.io.File;

public class FileUtils {

    /**
     * Get extension of any file.
     * file.txt -> ".txt"
     * folder -> ""
     * file.tar.gz -> ".gz"
     */
    public static String getExtension(File file) {
        String name = file.getName();
        if (name.lastIndexOf(".") == -1) {
            return "";
        }
        return name.substring(name.lastIndexOf("."));
    }

}
