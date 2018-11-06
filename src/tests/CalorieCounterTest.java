package tests;

import exceptions.HighTotalException;
import exceptions.LowTotalException;
import model.Exercise;
import model.Food;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CalorieCounterTest {
    public Item food;
    public Food corn;

    private String corn_id = "565";
    private String corn_name= "Corn";
    private int corn_cals = 113;

    private int a_cals = 70;
    private int b_cals = 80;


    @BeforeEach
    public void runBefore() {
        corn = new Food(corn_id, corn_name, corn_cals, false);
        food = new Food("1000", "Food", 0, true);
        food.makeItems();
        food.il.createRemoveList();
    }

    @Test
    public void testAddCalNoException() {
        Food banana = new Food("300", "Banana", b_cals, true);
        try {
            food.il.addCal(corn);
            food.il.addCal(banana);
        } catch (HighTotalException e) {
            fail();
        } catch (LowTotalException e) {
            fail();
        }
        assertEquals(food.getTotal(), b_cals + corn_cals);
    }

    @Test
    public void testAddCalHighTotalException() {
        Food banana = new Food("300", "Banana", 3000, true);
        try {
            food.il.addCal(corn);
            food.il.addCal(banana);
        } catch (HighTotalException e) {
        } catch (LowTotalException e) {
            fail();
        }
        assertEquals(food.getTotal(), 3000 + corn_cals);
    }

    @Test
    public void testAddCalLowTotalException() {
        Item jog = new Exercise("300", "Jogging", -500);
        try {
            food.il.addCal(corn);
            food.il.addCal(jog);
        } catch (HighTotalException e) {
            fail();
        } catch (LowTotalException e) {
        }

        assertEquals(food.getTotal(), -500 + corn_cals);
    }

    @Test
    public void testRemoveCalNoException(){
        Food apple = new Food("301", "Apple", a_cals, true);
        try {
            food.il.removeCal(apple);
        } catch (LowTotalException e) {
            fail();
        } catch (HighTotalException e) {
            fail();
        }
        assertEquals(food.getTotal(), - a_cals);
    }

    @Test
    public void testRemoveCalHighTotalException(){
        Exercise jog = new Exercise("301", "Jogging", -4000);
        try {
            food.il.removeCal(jog);
        } catch (LowTotalException e) {
            fail();
        } catch (HighTotalException e) {
        }
        assertEquals(food.getTotal(), 4000);
    }

    @Test
    public void testRemoveCalLowTotalException(){
        Food apple = new Food("301", "Apple", 300, true);
        try {
            food.il.removeCal(apple);
        } catch (LowTotalException e) {
        } catch (HighTotalException e) {
            fail();
        }
        assertEquals(food.getTotal(), -300);
    }


}
