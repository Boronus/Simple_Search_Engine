import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import search.Main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MainTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;
    private static final InputStream originalIn = System.in;

    @BeforeAll
    public static void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        System.setIn(originalIn);
    }

    @Test
    public void shouldShowMenu() {
        Main.showMenu();
        Assertions.assertTrue(outContent.toString().contains("=== Menu ==="));
        Assertions.assertTrue(outContent.toString().contains("1. Find a person"));
        Assertions.assertTrue(outContent.toString().contains("2. Print all people"));
        Assertions.assertTrue(outContent.toString().contains("0. Exit"));
    }

    @Test
    public void shouldGetStrategy() {
        Assertions.assertEquals("AllStrategy", Main.getStrategy("ALL").getClass().getSimpleName());
        Assertions.assertEquals("AnyStrategy", Main.getStrategy("ANY").getClass().getSimpleName());
        Assertions.assertEquals("NoneStrategy", Main.getStrategy("NONE").getClass().getSimpleName());
    }

    @Test
    public void shouldFindRecord() {
        System.setIn(new ByteArrayInputStream("ANY\nfernando".getBytes()));
        Scanner scanner = new Scanner(System.in);

        NamesMock names = new NamesMock();
        HashMap<String, ArrayList<Integer>> peopleIndex = Main.createIndex(names.records);
        Main.findPerson(scanner, names.records, peopleIndex);

        Assertions.assertTrue(outContent.toString().contains("Fernando Marbury fernando_marbury@gmail.com"));
        Assertions.assertTrue(outContent.toString().contains("Fernando Mattei fernando_mattei@gmail.com"));
    }

    @Test
    public void shouldPrintAll() {
        NamesMock names = new NamesMock();
        Main.printAll(names.records);

        Assertions.assertTrue(outContent.toString().contains("Kristyn Nix nix-kris@gmail.com"));
        Assertions.assertTrue(outContent.toString().contains("Regenia Enderle"));
        Assertions.assertTrue(outContent.toString().contains("Fernando Marbury fernando_marbury@gmail.com"));
        Assertions.assertTrue(outContent.toString().contains("Fernando Mattei fernando_mattei@gmail.com"));
    }
}
