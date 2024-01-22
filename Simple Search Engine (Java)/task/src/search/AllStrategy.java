package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AllStrategy implements FindingStrategy {
    public void find (String[] searchWords, ArrayList<String> people, HashMap<String, ArrayList<Integer>> peopleIndex) {
        ArrayList<Integer> resultIndexes = new ArrayList<>();

        for (String word: searchWords) {
            if (peopleIndex.containsKey(word)) {
                ArrayList<Integer> indexes = peopleIndex.get(word);
                if (resultIndexes.isEmpty()) {
                    resultIndexes.addAll(indexes);
                } else {
                    resultIndexes.retainAll(indexes);
                }
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
