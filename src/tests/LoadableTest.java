package tests;

import exceptions.HighTotalException;
import exceptions.NoPreviousException;
import model.Food;
import model.FoodList;
import model.Item;
import model.ItemList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class LoadableTest {
    private Item food;
    private Item apple;
    private Item banana;
    private ArrayList<Item> testFood;
    private ItemList testAll;

    @BeforeEach
    public void runBefore() {
        testFood = new ArrayList<>();
        testAll = new FoodList();
        food = new Food("1000", "Food", 0, true);
        banana = new Food("300", "Banana", 200, true);
        apple = new Food("301", "Apple", 100, true);
        testAll.addItem(food);
        testAll.addItem(banana);
        testAll.addItem(apple);
        food.createRemoveList();
    }

    @Test
    public void testSetTotalNoneNoPreviousException() throws IOException {
        try {
            food.setTotal();
            fail();
        } catch (NoPreviousException e) {
        }
        assertEquals(0, food.getTotal());
        PrintWriter pw = new PrintWriter("previousTOTAL.txt");
        pw.close();
    }

    @Test
    public void testSetTotal() throws IOException {
        food.addItem(apple, testFood);
        food.addItem(banana, testFood);
        food.saveToPreviousTotal();
        try {
            food.setTotal();
        } catch (NoPreviousException e) {
            fail();
        }
        assertEquals(2*(apple.getCalories() + banana.getCalories()), food.getTotal());
        PrintWriter pw = new PrintWriter("previousTOTAL.txt");
        pw.close();
    }

    @Test
    public void testSetFoodEatenNoneNoPreviousException() throws IOException{
        try {
            food.setDone(testAll, testFood);
            fail();
        } catch (NoPreviousException e) {
        }
        assertEquals(0, testFood.size());
        PrintWriter pw = new PrintWriter("previous.txt");
        pw.close();
    }

    @Test
    public void testSetFoodEaten() throws IOException{
        food.addItem(apple, testFood);
        food.addItem(banana, testFood);
        food.saveToPrevious(testFood);
        PrintWriter pw = new PrintWriter("previous.txt");
        pw.close();
        try {
            food.setDone(testAll, testFood);
        } catch (NoPreviousException e) {
        }
        assertTrue(testFood.contains(apple));
        assertTrue(testFood.contains(banana));
        assertEquals(2, testFood.size());
        PrintWriter nw = new PrintWriter("previous.txt");
        nw.close();
    }

}
