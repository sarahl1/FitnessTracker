package tests;

import model.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveTest {
    private Food food;
    private Food apple;
    private Food banana;

    @BeforeEach
    public void runBefore() {
        food = new Food("1000", "Food", 0);
        banana = new Food("300", "Banana", 200);
        apple = new Food("301", "Apple", 100);
        food.makeFood();
        food.createRemoveList();
    }


    @Test
    public void testSaveToTotal() throws IOException {
        food.addFood(apple);
        food.addFood(banana);
        food.saveToTotal();
        String line = Files.readAllLines(Paths.get("inputTOTAL.txt")).get(0);
        assertEquals(food.total, parseInt(line));
    }


    @Test
    public void testSaveToTotalNone() throws IOException {
        food.saveToTotal();
        String line = Files.readAllLines(Paths.get("inputTOTAL.txt")).get(0);
        assertEquals(food.total, parseInt(line));
    }

    @Test
    public void testSaveToInputList() throws IOException {
        food.addFood(apple);
        food.addFood(banana);
        food.saveToInputList();
        BufferedReader reader = new BufferedReader(new FileReader("inputfileLIST.txt"));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        assertEquals(2, lines);
        String line = Files.readAllLines(Paths.get("inputfileLIST.txt")).get(0);
        assertEquals(apple.getId(), line);
    }

    @Test
    public void testSaveToInputListNone() throws IOException {
        food.saveToInputList();
        BufferedReader reader = new BufferedReader(new FileReader("inputfileLIST.txt"));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        assertEquals(0, lines);
    }
}
