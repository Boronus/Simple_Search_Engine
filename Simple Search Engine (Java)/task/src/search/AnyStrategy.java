package search;

import java.util.ArrayList;
import java.util.HashMap;

public class AnyStrategy  implements FindingStrategy {
    public void find (String[] searchWords, ArrayList<String> people, HashMap<String, ArrayList<Integer>> peopleIndex) {
        ArrayList<Integer> resultIndexes = new ArrayList<>();

        for (String word: searchWords) {
            if (peopleIndex.containsKey(word)) {
                ArrayList<Integer> indexes = peopleIndex.get(word);
                resultIndexes.addAll(indexes);
            }
        }

        if (resultIndexes.isEmpty())  {
            System.out.println("No matching people found.");
        } else {
            for (Integer lineIndex: resultIndexes) {
                System.out.println(people.get(lineIndex));
            }
        }
    }
}
