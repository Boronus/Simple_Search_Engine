import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import search.AllStrategy;
import search.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class AllStrategyTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeAll
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void shouldPrintAllMatchingPeople() {
        NamesMock names = new NamesMock();
        HashMap<String, ArrayList<Integer>> peopleIndex = Main.createIndex(names.records);
        AllStrategy allStrategy = new AllStrategy();
        String[] searchWords = {"fernando"};
        allStrategy.find(searchWords, names.records, peopleIndex);
        Assertions.assertTrue(outContent.toString().contains("Fernando Marbury fernando_marbury@gmail.com"));
        Assertions.assertTrue(outContent.toString().contains("Fernando Mattei fernando_mattei@gmail.com"));
    }
}
