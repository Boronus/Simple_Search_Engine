package search;

import java.util.ArrayList;
import java.util.HashMap;

public interface FindingStrategy {
    void find(String[] search, ArrayList<String> people, HashMap<String, ArrayList<Integer>> peopleIndex);
}
