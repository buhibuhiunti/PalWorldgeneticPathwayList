import java.io.*;
import java.util.*;

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
        scanner = new Scanner(System.in);
        while(true) {
            System.out.print("どの検索方法を使用しますか\n" + "1.個体値遺伝検索\n>");
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
        boolean doesItExist = false;
        Scanner scanner = new Scanner(System.in);
        List<String> parentsList = new ArrayList<>();
        String childPal = "NODATA";
        List<String> searchList;
        List<List<List<String>>> threeDimensionalGenerationList = new ArrayList<List<List<String>>>();
        while(!doesItExist) {
            System.out.print("欲しいパルを言え\n>");
            String wantPal = scanner.nextLine();
            doesItExist = Arrays.stream(combinationTable)
                                .flatMap(Arrays::stream)
                                .anyMatch(wantPal::equals);
            if(!doesItExist) {
                System.out.println("だめで～す");
            }
            childPal = wantPal;
        }
        boolean hasParentInputBeenCompleted = false;
        while(!hasParentInputBeenCompleted) {
            doesItExist = false;
            String wantPal = "NODATA";
            while(!doesItExist) {
                System.out.print("親にするパルを言え\n>");
                wantPal = scanner.nextLine();
                doesItExist = Arrays.stream(combinationTable)
                                    .flatMap(Arrays::stream)
                                    .anyMatch(wantPal::equals);
                if(!doesItExist) {
                    System.out.println("だめで～す");
                }
            }
            parentsList.add(wantPal);
            boolean wereAbleAnswer = false;
            while(!wereAbleAnswer) {
                System.out.print("他にも親にするやついる？\n1.いる\n2.いない\n>");
                int i = scanner.nextInt();
                switch (i) {
                    case 1:
                        wereAbleAnswer = true;
                        wantPal = scanner.nextLine();
                        break;
                    case 2:
                        wereAbleAnswer = true;
                        hasParentInputBeenCompleted = true;
                        break;
                    default:
                        System.out.print("だめで～す");
                        break;
                }
            }
        }
        boolean foundGeneticPathway = false;
        searchList = new ArrayList<>(parentsList);
        int parentTypesNum = 0;
        while(!foundGeneticPathway) {
            List<List<String>> twoDimensionalGenerationList = new ArrayList<List<String>>();
            List<String> copySearchList = new ArrayList<>(searchList);
            for(String firstParent : searchList) {
                for(String secondParent : searchList) {
                    if(!copySearchList.contains(mixingResult(firstParent,secondParent,combinationTable))) {
                        copySearchList.add(mixingResult(firstParent,secondParent,combinationTable));
                        List<String> oneDimensionalGenerationList = new ArrayList<String>();
                        oneDimensionalGenerationList.add(firstParent);
                        oneDimensionalGenerationList.add(secondParent);
                        oneDimensionalGenerationList.add(mixingResult(firstParent,secondParent,combinationTable));
                        twoDimensionalGenerationList.add(0,oneDimensionalGenerationList);
                    }
                }
            }
            threeDimensionalGenerationList.add(0,twoDimensionalGenerationList);
            searchList = new ArrayList<>(copySearchList);
            for(String s : searchList) {

                if(s.equals(childPal)) {
                    foundGeneticPathway = true;
                }
            }
            if(parentTypesNum < searchList.size()) {
                parentTypesNum = searchList.size();
            } else {
                System.out.println("この親から産まれてくることはないで\n産まれてくるパルは以下のやつだけ");
                searchList.forEach(System.out::println);
                System.exit(0);
            }
        }
        Queue<String> parentSearchWaitQueue = new LinkedList<>();
        List<List<String>> twoListOfNamesAssociatedWithInheritancePaths = new ArrayList<List<String>>();
        List<String> searchedList = new ArrayList<>();
        parentSearchWaitQueue.offer(childPal);
        while(!parentSearchWaitQueue.isEmpty()) {
            for(List<List<String>> twoList : threeDimensionalGenerationList) {
                for(List<String> oneList : twoList) {
                    if(oneList.get(2).equals(parentSearchWaitQueue.peek())) {
                        List<String> oneListOfNamesAssociatedWithInheritancePaths = new ArrayList<>();
                        oneListOfNamesAssociatedWithInheritancePaths.add(oneList.get(0));
                        oneListOfNamesAssociatedWithInheritancePaths.add(oneList.get(1));
                        oneListOfNamesAssociatedWithInheritancePaths.add(parentSearchWaitQueue.poll());
                        if(!parentsList.contains(oneList.get(0)) && !searchedList.contains(oneList.get(0))) {
                            parentSearchWaitQueue.offer(oneList.get(0));
                            searchedList.add(oneList.get(0));
                        }
                        if(!parentsList.contains(oneList.get(1)) && !searchedList.contains(oneList.get(1))) {
                            parentSearchWaitQueue.offer(oneList.get(1));
                            searchedList.add(oneList.get(1));
                        }
                        twoListOfNamesAssociatedWithInheritancePaths.add(oneListOfNamesAssociatedWithInheritancePaths);
                    }
                }
            }
        }
        List<String> listOfParentsAssociatedWithTheCombinationToOutput = new ArrayList<>(parentsList);
        List<List<String>> copyTwoListOfNamesAssociatedWithInheritancePaths = new ArrayList<>(twoListOfNamesAssociatedWithInheritancePaths);
        while (!twoListOfNamesAssociatedWithInheritancePaths.isEmpty()) {
            for(List<String> one : twoListOfNamesAssociatedWithInheritancePaths) {
                if(listOfParentsAssociatedWithTheCombinationToOutput.contains(one.get(0)) &&
                        listOfParentsAssociatedWithTheCombinationToOutput.contains(one.get(1))) {
                    listOfParentsAssociatedWithTheCombinationToOutput.add(one.get(2));
                    System.out.println(one.get(0) + "と" + one.get(1) + "を配合させて" + one.get(2) + "を作る");
                    Iterator<List<String>> iterator = copyTwoListOfNamesAssociatedWithInheritancePaths.listIterator();
                    while(iterator.hasNext()) {
                        List<String> subList = iterator.next();
                        if(subList.contains(one.get(0)) && subList.contains(one.get(1)) && subList.contains(one.get(2))) {
                            iterator.remove();
                        }
                    }
                }
            }
            twoListOfNamesAssociatedWithInheritancePaths = new ArrayList<>(copyTwoListOfNamesAssociatedWithInheritancePaths);
        }

    }
    static String mixingResult(String firstParent, String secondParent, String[][] combinationTable) {
        int firstParentNum = -1;
        int secondParentNum = -1;
        for(int i = 0;i < combinationTable.length;i++) {
            if(combinationTable[0][i].equals(firstParent)) {
                firstParentNum = i;
            }
            if(combinationTable[0][i].equals(secondParent)) {
                secondParentNum = i;
            }
        }
        return combinationTable[firstParentNum][secondParentNum];
    }
}