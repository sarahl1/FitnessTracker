package tests;

import model.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FoodTest {
    public Food food;
    public Food corn;

    private String corn_id = "565";
    private String corn_name= "Corn";
    private int corn_cals = 113;

    private int a_cals = 70;
    private int b_cals = 80;


    @BeforeEach
    public void runBefore() {
        corn = new Food(corn_id, corn_name, corn_cals);
        food = new Food("1000", "Food", 0);
        food.makeFood();
        food.createRemoveList();
    }

    @Test
    public void testConstructor(){
        assertEquals(corn_id, corn.getId());
        assertEquals(corn_name, corn.getName());
        assertEquals(corn_cals, corn.getCalories());
    }

    @Test
    public void testAddOneFoodItem(){
        food.addFood(corn);
        assertEquals(food.getFood_eaten().size(), 1);
        assertTrue(food.getFood_eaten().contains(corn));
        assertEquals(food.total, corn_cals);
    }

    @Test
    public void testAddSomeFoodItems(){
        int b_cals = 80;
        Food banana = new Food("300", "Banana", b_cals);
        food.addFood(corn);
        food.addFood(banana);
        assertEquals(food.getFood_eaten().size(), 2);
        assertTrue(food.getFood_eaten().contains(corn));
        assertTrue(food.getFood_eaten().contains(banana));
        assertEquals(food.total, corn_cals + b_cals);
    }

    @Test
    public void testAddNoFood(){
        assertEquals(food.getFood_eaten().size(), 0);
    }

    @Test
    public void testAddOneFoodItemToRemove(){
        Food banana = new Food("300", "Banana", b_cals);
        food.addFood(banana);
        food.addFood(corn);
        food.removeFood("300");
        assertTrue(food.getListToRemove().contains(banana));
        assertEquals(food.getListToRemove().size(), 1);
    }

    @Test
    public void testRemoveOneFoodItem(){
        Food banana = new Food("300", "Banana", b_cals);
        food.addFood(banana);
        food.addFood(corn);
        food.removeFood("300");
        food.removeFromEaten();
        assertFalse(food.getFood_eaten().contains(banana));
        assertEquals(food.getFood_eaten().size(), 1);
        assertEquals(food.total, corn_cals);
        assertEquals(food.getListToRemove().size(), 1);
    }

    @Test
    public void testAddSomeFoodItemsToRemove(){
        Food banana = new Food("300", "Banana", b_cals);
        Food apple = new Food("301", "Apple", a_cals);
        food.addFood(banana);
        food.addFood(corn);
        food.addFood(apple);
        food.removeFood("301");
        food.removeFood("565");
        assertTrue(food.getListToRemove().contains(apple));
        assertTrue(food.getListToRemove().contains(corn));
        assertEquals(food.getListToRemove().size(), 2);
    }

    @Test
    public void testRemoveSomeFoodItems(){
        Food banana = new Food("300", "Banana", b_cals);
        Food apple = new Food("301", "Apple", a_cals);
        food.addFood(banana);
        food.addFood(corn);
        food.addFood(apple);
        food.removeFood("301");
        food.removeFood( "565");
        food.removeFromEaten();
        assertEquals(food.getFood_eaten().size(), 1);
        assertEquals(food.getListToRemove().size(), 2);
        assertTrue(food.getFood_eaten().contains(banana));
        assertFalse(food.getFood_eaten().contains(apple));
        assertEquals(food.total, b_cals);
    }

    @Test
    public void testRemoveNoneWithSomePresent(){
        Food banana = new Food("300", "Banana", b_cals);
        Food apple = new Food("301", "Apple", a_cals);
        food.addFood(banana);
        food.addFood(corn);
        food.addFood(apple);
        food.removeFromEaten();
        assertEquals(food.getListToRemove().size(), 0);
        assertEquals(food.getFood_eaten().size(), 3);
        assertEquals(food.total, corn_cals + b_cals + a_cals);
    }

    @Test
    public void testRemoveNoneWithNonePresent(){
        food.removeFromEaten();
        assertEquals(food.getListToRemove().size(), 0);
        assertEquals(food.getFood_eaten().size(), 0);
        assertEquals(food.total, 0);
    }

    @Test
    public void testRemoveOneWithNonePresent(){
        food.removeFood("101");
        food.removeFromEaten();
        assertEquals(food.getListToRemove().size(), 0);
        assertEquals(food.getFood_eaten().size(), 0);
        assertEquals(food.total, 0);
    }

    @Test
    public void testRemoveOneWithDifferentPresent(){
        Food banana = new Food("300", "Banana", b_cals);
        Food apple = new Food("301", "Apple", a_cals);
        food.addFood(banana);
        food.addFood(corn);
        food.addFood(apple);
        food.removeFood("000");
        food.removeFromEaten();
        assertEquals(food.getListToRemove().size(), 0);
        assertEquals(food.getFood_eaten().size(), 3);
        assertEquals(corn_cals + b_cals + a_cals, food.total);
    }

    @Test
    public void testAddCal(){
        food.addCal(corn);
        Food banana = new Food("300", "Banana", b_cals);
        food.addCal(banana);
        assertEquals(food.total, b_cals + corn_cals);
    }

    @Test
    public void testRemoveCal(){
        food.removeCal(corn);
        Food apple = new Food("301", "Apple", a_cals);
        food.removeCal(apple);
        assertEquals(food.total, - a_cals - corn_cals);
    }




}
