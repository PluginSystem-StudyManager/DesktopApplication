import entryPoint.Main;

public class Start {

    public static void main(String[] args) {
        try {
            Main.main(args);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
