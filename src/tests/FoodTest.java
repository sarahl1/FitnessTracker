package tests;

import model.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FoodTest {
    public Food corn;
    public ArrayList<Food> testFood;
    public ArrayList<Food> testToRemove;

    private String corn_id = "565";
    private String corn_name= "Corn";
    private int corn_cals = 113;

    private int a_cals = 70;
    private int b_cals = 80;



    @BeforeEach
    public void runBefore() {
        corn = new Food(corn_id, corn_name, corn_cals);
        testFood = new ArrayList<>();
    }

    @Test
    public void testConstructor(){
        assertEquals(corn_id, corn.getId());
        assertEquals(corn_name, corn.getName());
        assertEquals(corn_cals, corn.getCalories());
    }

    @Test
    public void testAddOneFoodItem(){
        corn.addFood(testFood, corn);
        assertEquals(testFood.size(), 1);
        assertTrue(testFood.contains(corn));
        assertEquals(corn.total, corn_cals);
    }

    @Test
    public void testAddSomeFoodItems(){
        int b_cals = 80;
        Food banana = new Food("300", "Banana", b_cals);
        corn.addFood(testFood, corn);
        corn.addFood(testFood, banana);
        assertEquals(testFood.size(), 2);
        assertTrue(testFood.contains(corn));
        assertTrue(testFood.contains(banana));
        assertEquals(corn.total, corn_cals + b_cals);
    }

    @Test
    public void testAddNoFood(){
        assertEquals(testFood.size(), 0);
    }

    @Test
    public void testAddOneFoodItemToRemove(){
        testToRemove = new ArrayList<>();
        Food banana = new Food("300", "Banana", b_cals);
        corn.addFood(testFood, banana);
        corn.addFood(testFood, corn);
        corn.removeFood(testFood, "300", testToRemove);
        assertTrue(testToRemove.contains(banana));
        assertEquals(testToRemove.size(), 1);
    }

    @Test
    public void testRemoveOneFoodItem(){
        testToRemove = new ArrayList<>();
        Food banana = new Food("300", "Banana", b_cals);
        corn.addFood(testFood, banana);
        corn.addFood(testFood, corn);
        corn.removeFood(testFood, "300", testToRemove);
        corn.removeFromEaten(testToRemove, testFood);
        assertFalse(testFood.contains(banana));
        assertEquals(testFood.size(), 1);
        assertEquals(corn.total, corn_cals);
        assertEquals(testToRemove.size(), 1);
    }

    @Test
    public void testAddSomeFoodItemsToRemove(){
        testToRemove = new ArrayList<>();
        Food banana = new Food("300", "Banana", b_cals);
        Food apple = new Food("301", "Apple", a_cals);
        corn.addFood(testFood, banana);
        corn.addFood(testFood, corn);
        corn.addFood(testFood, apple);
        corn.removeFood(testFood, "301", testToRemove);
        corn.removeFood(testFood, "565", testToRemove);
        assertTrue(testToRemove.contains(apple));
        assertTrue(testToRemove.contains(corn));
        assertEquals(testToRemove.size(), 2);
    }

    @Test
    public void testRemoveSomeFoodItems(){
        testToRemove = new ArrayList<>();
        Food banana = new Food("300", "Banana", b_cals);
        Food apple = new Food("301", "Apple", a_cals);
        corn.addFood(testFood, banana);
        corn.addFood(testFood, corn);
        corn.addFood(testFood, apple);
        corn.removeFood(testFood, "301", testToRemove);
        corn.removeFood(testFood, "565", testToRemove);
        corn.removeFromEaten(testToRemove, testFood);
        assertEquals(testFood.size(), 1);
        assertEquals(testToRemove.size(), 2);
        assertTrue(testFood.contains(banana));
        assertFalse(testFood.contains(apple));
        assertEquals(corn.total, b_cals);
    }

    @Test
    public void testRemoveNoneWithSomePresent(){
        testToRemove = new ArrayList<>();
        Food banana = new Food("300", "Banana", b_cals);
        Food apple = new Food("301", "Apple", a_cals);
        corn.addFood(testFood, banana);
        corn.addFood(testFood, corn);
        corn.addFood(testFood, apple);
        corn.removeFromEaten(testToRemove, testFood);
        assertEquals(testToRemove.size(), 0);
        assertEquals(testFood.size(), 3);
        assertEquals(corn.total, corn_cals + b_cals + a_cals);
    }

    @Test
    public void testRemoveNoneWithNonePresent(){
        testToRemove = new ArrayList<>();
        corn.removeFromEaten(testToRemove, testFood);
        assertEquals(testToRemove.size(), 0);
        assertEquals(testFood.size(), 0);
        assertEquals(corn.total, 0);
    }

    @Test
    public void testRemoveOneWithNonePresent(){
        testToRemove = new ArrayList<>();
        corn.removeFood(testFood, "101", testToRemove);
        corn.removeFromEaten(testToRemove, testFood);
        assertEquals(testToRemove.size(), 0);
        assertEquals(testFood.size(), 0);
        assertEquals(corn.total, 0);
    }

    @Test
    public void testRemoveOneWithDifferentPresent(){
        testToRemove = new ArrayList<>();
        Food banana = new Food("300", "Banana", b_cals);
        Food apple = new Food("301", "Apple", a_cals);
        corn.addFood(testFood, banana);
        corn.addFood(testFood, corn);
        corn.addFood(testFood, apple);
        corn.removeFood(testFood, "000", testToRemove);
        corn.removeFromEaten(testToRemove, testFood);
        assertEquals(testToRemove.size(), 0);
        assertEquals(testFood.size(), 3);
        assertEquals(corn_cals + b_cals + a_cals, corn.total);
    }

    @Test
    public void testAddCal(){
        corn.addCal(corn);
        Food banana = new Food("300", "Banana", b_cals);
        corn.addCal(banana);
        assertEquals(corn.total, b_cals + corn_cals);
    }

    @Test
    public void testRemoveCal(){
        corn.removeCal(corn);
        Food apple = new Food("301", "Apple", a_cals);
        corn.removeCal(apple);
        assertEquals(corn.total, - a_cals - corn_cals);
    }




}
