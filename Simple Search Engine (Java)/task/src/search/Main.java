package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter all people:");
        ArrayList<String> people = new ArrayList<>();

        String fileName = args[1];
        File file = new File(fileName);

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNext()) {
                people.add(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found");
        }

        HashMap<String, ArrayList<Integer>> peopleIndex = createIndex(people);

        boolean exit = false;

        while(!exit) {
            showMenu();

            int command = scanner.nextInt();
            scanner.nextLine();
            Action actionView = null;

            try {
                actionView = Action.values()[command];
            } catch (Exception e) {
                actionView = Action.Exception;
            }

            switch (actionView) {
                case Exit:
                    System.out.println("Bye!");
                    exit = true;
                    break;
                case Search:
                    findPerson(scanner, people, peopleIndex);
                    break;
                case Print:
                    printAll(people);
                    break;
                case Exception:
                    System.out.println("Incorrect option! Try again.");
            }
        }
    }

    public static void showMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    public static FindingStrategy getStrategy(String strategyName) {
        switch (strategyName) {
            case "ALL":
                return new AllStrategy();
            case "ANY":
                return new AnyStrategy();
            default:
                return new NoneStrategy();
        }
    }

    public static void findPerson(Scanner scanner, ArrayList<String> people, HashMap<String, ArrayList<Integer>> peopleIndex) {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategyName = scanner.nextLine();
        FindingStrategy strategy = getStrategy(strategyName);

        System.out.println("Enter a name or email to search all suitable people.");

        String search = scanner.nextLine();
        search = search.toLowerCase();

        String[] searchWords = search.split(" ");

        strategy.find(searchWords, people, peopleIndex);

    }

    public static void printAll(ArrayList<String> people) {
        System.out.println("=== List of people ===");
        for (String person : people) {
            System.out.println(person);
        }
    }

    public static HashMap<String, ArrayList<Integer>> createIndex(ArrayList<String> people) {
        HashMap<String, ArrayList<Integer>> peopleIndex = new HashMap<>();

        int i = 0;
        for (String person : people) {
            String[] splitted = person.split(" ");

            for (int j = 0; j < splitted.length; j++) {
                String personDataString = splitted[j].toLowerCase();

                if (peopleIndex.containsKey(personDataString)) {
                    ArrayList<Integer> currentValue = peopleIndex.get(personDataString);
                    currentValue.add(i);
                    peopleIndex.put(personDataString, currentValue);
                } else {
                    ArrayList<Integer> indexes = new ArrayList<>();
                    indexes.add(i);
                    peopleIndex.put(personDataString, indexes);
                }
            }

            i++;
        }

        return peopleIndex;
    }
}
