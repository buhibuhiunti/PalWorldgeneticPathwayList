import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "src/geneticPathwayList.txt";
        String[] parentArray;
        List<String> parentList = new ArrayList<>();
        List<Pal> palList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        try {
            String line = reader.readLine();
            parentArray = line.split(",");
            for(String e : parentArray) {
                palList.add(new Pal(e));
                parentList.add(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String parent : parentList) {
            try {
                String line = reader.readLine();
                String[] childList = line.split(",");
                int count = 0;
                for(Pal pal : palList) {
                    pal.addCombination(childList[count],parent);
                    count++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for(Pal pal : palList) {
            pal.testPrint();
        }
    }
}