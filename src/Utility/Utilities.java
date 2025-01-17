package Utility;
import java.io.IOException;
public class Utilities {


    /**
     * Function to clear the terminal
     */
    public static void clear_Screen(){

        String os = System.getProperty("os.name").toLowerCase();

        try {
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
                new ProcessBuilder("/bin/bash", "-c", "clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong : " + e.getMessage());
        }
    }
    public static void sleep(int seconds) {
        try {

            Thread.sleep(seconds);
        } catch ( Exception e) {
            // This is thrown if the sleep is interrupted
            System.err.println("Sleep was interrupted: " );
        }
    }

}
