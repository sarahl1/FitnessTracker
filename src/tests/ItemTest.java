package tests;
import model.Exercise;
import model.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    public Food food;
    public Food corn;

    private String corn_id = "565";
    private String corn_name = "Corn";
    private int corn_cals = 113;

    private int a_cals = 70;
    private int b_cals = 80;


    @BeforeEach
    public void runBefore() {
        corn = new Food(corn_id, corn_name, corn_cals, false);
        food = new Food("1000", "Food", 0, true);
        food.makeItems();
        food.createRemoveList();
    }

    @Test
    public void testConstructor() {
        assertEquals(corn_id, corn.getId());
        assertEquals(corn_name, corn.getName());
        assertEquals(corn_cals, corn.getCalories());
    }


    @Test
    public void testAddItemsNoException() {
        int b_cals = 80;
        Food banana = new Food("300", "Banana", b_cals, true);
        food.addItem(corn, food.getFoodEaten());
        food.addItem(banana, food.getFoodEaten());
        assertEquals(food.getFoodEaten().getDone().size(), 2);
        assertTrue(food.getFoodEaten().getDone().contains(corn));
        assertTrue(food.getFoodEaten().getDone().contains(banana));
        assertEquals(food.getTotal(), corn_cals + b_cals);
    }

    @Test
    public void testAddItemsHighTotalException() {
        int b_cals = 8000;
        Food banana = new Food("300", "Banana", b_cals, true);
        food.addItem(corn, food.getFoodEaten());
        food.addItem(banana, food.getFoodEaten());
        assertEquals(food.getFoodEaten().getDone().size(), 2);
        assertTrue(food.getFoodEaten().getDone().contains(corn));
        assertTrue(food.getFoodEaten().getDone().contains(banana));
        assertEquals(food.getTotal(), corn_cals + b_cals);
    }

    @Test
    public void testAddItemsLowTotalException() {
        int b_cals = -800;
        Food banana = new Food("300", "Banana", b_cals, true);
        food.addItem(corn, food.getFoodEaten());
        food.addItem(banana, food.getFoodEaten());
        assertEquals(food.getFoodEaten().getDone().size(), 2);
        assertTrue(food.getFoodEaten().getDone().contains(corn));
        assertTrue(food.getFoodEaten().getDone().contains(banana));
        assertEquals(food.getTotal(), corn_cals + b_cals);
    }


    @Test
    public void testAddItemToRemoveNoException() {
        Food banana = new Food("300", "Banana", b_cals, true);
        Food apple = new Food("301", "Apple", a_cals, true);
        food.addItem(banana, food.getFoodEaten());
        food.addItem(corn, food.getFoodEaten());
        food.addItem(apple, food.getFoodEaten());
        food.removeItem("301", food.getFoodEaten(), food.getExerciseDone(), food.getListToRemove());
        food.removeItem("565", food.getFoodEaten(), food.getExerciseDone(), food.getListToRemove());
        assertTrue(food.getListToRemove().contains(apple));
        assertTrue(food.getListToRemove().contains(corn));
        assertEquals(food.getListToRemove().size(), 2);
    }

    @Test
    public void testRemoveItemsNoException() {
        Food banana = new Food("300", "Banana", b_cals, true);
        Food apple = new Food("301", "Apple", a_cals, true);
        food.addItem(banana, food.getFoodEaten());
        food.addItem(corn, food.getFoodEaten());
        food.addItem(apple, food.getFoodEaten());
        food.removeItem("301", food.getFoodEaten(), food.getExerciseDone(), food.getListToRemove());
        food.removeItem("565", food.getFoodEaten(), food.getExerciseDone(), food.getListToRemove());
        food.removeFromRemove(food.getFoodEaten(), food.getExerciseDone());
        assertEquals(food.getFoodEaten().getDone().size(), 1);
        assertEquals(food.getListToRemove().size(), 2);
        assertTrue(food.getFoodEaten().getDone().contains(banana));
        assertFalse(food.getFoodEaten().getDone().contains(apple));
    }

    @Test
    public void testRemoveItemsHighTotalException() {
        Food banana = new Food("300", "Banana", 3000, true);
        Food apple = new Food("301", "Apple", a_cals, true);
        Exercise jog = new Exercise("400", "jogging", -3000);
        food.addItem(corn, food.getFoodEaten());
        food.addItem(apple, food.getFoodEaten());
        food.addItem(banana, food.getFoodEaten());

        food.addItem(jog, food.getExerciseDone());
        food.removeItem("400", food.getFoodEaten(), food.getExerciseDone(), food.getListToRemove());
        food.removeItem("301", food.getFoodEaten(), food.getExerciseDone(), food.getListToRemove());
        food.removeItem("565", food.getFoodEaten(), food.getExerciseDone(), food.getListToRemove());
        food.removeFromRemove(food.getFoodEaten(), food.getExerciseDone());
        assertEquals(food.getFoodEaten().getDone().size(), 1);
        assertEquals(food.getListToRemove().size(), 3);
        assertTrue(food.getFoodEaten().getDone().contains(banana));
        assertFalse(food.getFoodEaten().getDone().contains(apple));
    }

    @Test
    public void testRemoveItemsLowTotalException() {
        Food banana = new Food("300", "Banana", b_cals, true);
        Food apple = new Food("301", "Apple", a_cals, true);
        food.addItem(banana, food.getFoodEaten());
        food.addItem(corn, food.getFoodEaten());
        food.addItem(apple, food.getFoodEaten());
        food.removeItem("301", food.getFoodEaten(), food.getExerciseDone(), food.getListToRemove());
        food.removeItem("565", food.getFoodEaten(), food.getExerciseDone(), food.getListToRemove());
        food.removeFromRemove(food.getFoodEaten(), food.getExerciseDone());
        assertEquals(food.getFoodEaten().getDone().size(), 1);
        assertEquals(food.getListToRemove().size(), 2);
        assertTrue(food.getFoodEaten().getDone().contains(banana));
        assertFalse(food.getFoodEaten().getDone().contains(apple));
    }


    @Test
    public void testRemoveOneWithDifferentPresent() {
        Food banana = new Food("300", "Banana", b_cals, true);
        Food apple = new Food("301", "Apple", a_cals, true);
        food.addItem(banana, food.getFoodEaten());
        food.addItem(corn, food.getFoodEaten());
        food.addItem(apple, food.getFoodEaten());
        food.removeItem("000", food.getFoodEaten(), food.getExerciseDone(), food.getListToRemove());
        food.removeFromRemove(food.getFoodEaten(), food.getExerciseDone());
        assertEquals(food.getListToRemove().size(), 0);
        assertEquals(food.getFoodEaten().getDone().size(), 3);
        assertEquals(corn_cals + b_cals + a_cals, food.getTotal());
    }


}
