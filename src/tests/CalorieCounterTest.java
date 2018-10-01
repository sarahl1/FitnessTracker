package tests;

import model.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalorieCounterTest {
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
