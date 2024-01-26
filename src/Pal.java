import jdk.internal.net.http.common.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Pal {
    final String fastParent;
    List<Pair> combinationList = new ArrayList<Pair>();

    Pal(String fastParent) {
        this.fastParent = fastParent;
    }

    String addCombination(String palName,String secondParent) {
        try {
            Pair<String,Pal> combination = Pair.of(palName,Class.forName(secondParent));
        } catch (ClassNotFoundException e) {

        }
    }
}
