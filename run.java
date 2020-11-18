import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class run extends FilterClass {
    public static void main(String[] args) throws Exception {
        try {
            FileReader fr = new FileReader("/home/hhimmmmii/Documents/Project/Lyrics.txt");
            BufferedReader input = new BufferedReader(fr);
            String currentLine = "";
            while ((currentLine = input.readLine()) != null) {
                runCheck(currentLine);
            }
        } catch (IOException except) {
        }
    }

    public static void runCheck(String input) {
        String output = FilterClass.getCensoredText(input);
        System.out.println(output);

    }
}
