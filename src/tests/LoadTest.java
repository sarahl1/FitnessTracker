package tests;

import model.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadTest {
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
    public void testSetTotalNone() throws IOException {
        food.saveToTotal();
        food.setTotal();
        assertEquals(0, food.total);
    }

    @Test
    public void testSetTotal() throws IOException {
        food.addFood(apple);
        food.addFood(banana);
        food.saveToTotal();
        food.total = 0;
        food.setTotal();
        assertEquals(apple.getCalories() + banana.getCalories(), food.total);
    }

    @Test
    public void testSetFood_eatenNone() throws IOException {
        food.saveToInputList();
        food.setFood_eaten();
        assertEquals(0, food.getFood_eaten().size());
    }

    @Test
    public void testSetFood_eaten() throws IOException {
        food.addFood(apple);
        food.addFood(banana);
        food.saveToInputList();
        food.setFood_eaten();
        assertTrue(food.getFood_eaten().contains(apple));
        assertTrue(food.getFood_eaten().contains(banana));
        assertEquals(2, food.getFood_eaten().size());
    }

}
