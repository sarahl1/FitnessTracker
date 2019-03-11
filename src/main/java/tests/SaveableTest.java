package tests;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveableTest {
    private ItemLog food;
    private Item apple;
    private Item banana;
    private ItemDone testFood;
    private HashSet<Item> testAll;

    @BeforeEach
    public void runBefore() {
        testFood = new FoodEaten();
        testAll = new HashSet<>();
        food = new ItemLog();
        banana = new Food("300", "Banana", 200, true);
        apple = new Food("301", "Apple", 100, true);
        food.createRemoveList();
        testAll.add(banana);
        testAll.add(apple);
    }


    @Test
    public void testSaveToTotal() throws IOException{
        food.addItem(apple, testFood);
        food.addItem(banana, testFood);
        banana.saveToPreviousTotal();
        String line = Files.readAllLines(Paths.get("previousTOTAL.txt")).get(0);
        assertEquals(food.getTotal(), parseInt(line));
        PrintWriter pw = new PrintWriter("previousTOTAL.txt");
        pw.close();
    }



    @Test
    public void testSaveToTotalNone() throws IOException {
        banana.saveToPreviousTotal();
        String line = Files.readAllLines(Paths.get("previousTOTAL.txt")).get(0);
        assertEquals(food.getTotal(), parseInt(line));
        PrintWriter pw = new PrintWriter("previousTOTAL.txt");
        pw.close();
    }

    @Test
    public void testSaveToInputList() throws IOException {
        food.addItem(apple, testFood);
        food.addItem(banana, testFood);
        banana.saveToPrevious(testFood);
        BufferedReader reader = new BufferedReader(new FileReader("previous.txt"));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        assertEquals(2, lines);
        String line = Files.readAllLines(Paths.get("previous.txt")).get(0);
        assertEquals(apple.getId(), line);
        PrintWriter pw = new PrintWriter("previous.txt");
        pw.close();
    }

    @Test
    public void testSaveToInputListNone() throws IOException {
        banana.saveToPrevious(testFood);
        BufferedReader reader = new BufferedReader(new FileReader("previous.txt"));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        assertEquals(0, lines);
        PrintWriter pw = new PrintWriter("previous.txt");
        pw.close();
    }
}
