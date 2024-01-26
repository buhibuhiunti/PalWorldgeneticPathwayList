import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/geneticPathwayList.txt";
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}