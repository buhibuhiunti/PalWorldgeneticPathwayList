import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String FILEPATH = "src/geneticPathwayList.txt";
        final char DELIMITER = ',';
        String[][] combinationTable;
        Scanner scanner;
        while(true) {
            try {
                scanner = new Scanner(new File(FILEPATH));
                break;
            } catch (FileNotFoundException e) {
                System.out.println("配合表ファイルが見つかりませんでした\nファイルを配置したあとエンターキーを押してください");
                scanner = new Scanner(System.in);
                scanner.nextLine();
            }
        }
        String firstLine = scanner.nextLine();
        long delimiterCount = firstLine.chars().filter(c -> c == DELIMITER).count();
        combinationTable = new String[(int) delimiterCount + 1][(int) delimiterCount + 1];
        String[] firstLineParts = firstLine.split(String.valueOf(DELIMITER));
        for(int i = 0; i <= delimiterCount; i++) {
            combinationTable[0][i] = firstLineParts[i];
        }
        int loopCount = 1;
        while(scanner.hasNextLine()) {
            String[] lineParts = scanner.nextLine().split(String.valueOf(DELIMITER));
            combinationTable[loopCount][0] = combinationTable[0][loopCount];
            for(int i = 1; i <= delimiterCount; i++) {
                combinationTable[loopCount][i] = lineParts[i-1];
            }
            loopCount++;
        }
        while(true) {
            System.out.println("どの検索方法を使用しますか" + "1.個体値遺伝検索");
            scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    individualValueGeneticSearch(combinationTable);
                    break;
                default:
                    System.out.print("だめで～す");
                    break;
            }
        }
    }
    public static void individualValueGeneticSearch(String[][] combinationTable) {

    }
}