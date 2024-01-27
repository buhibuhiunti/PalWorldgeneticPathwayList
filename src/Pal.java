import org.apache.commons.lang3.tuple.Pair;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pal {
    final String fastParent;
    List<Pair> combinationList = new ArrayList<Pair>();

    Pal(String fastParent) {
        this.fastParent = fastParent;
    }

    void addCombination(String palName,String secondParent) {
        Pair<String,String> combination = Pair.of(palName,secondParent);
        this.combinationList.add(combination);
    }
    void testPrint() {
        System.out.println(this.fastParent);
        for(Pair pair : this.combinationList) {
            System.out.print(pair.getLeft());
        }
        System.out.println();
    }
}
