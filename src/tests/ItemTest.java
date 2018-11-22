//package tests;
//import model.Exercise;
//import model.Food;
//import model.ItemLog;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ItemTest {
//    public ItemLog il;
//    public Food corn;
//
//    private String corn_id = "565";
//    private String corn_name = "Corn";
//    private int corn_cals = 113;
//
//    private int a_cals = 70;
//    private int b_cals = 80;
//
//
//    @BeforeEach
//    public void runBefore() throws IOException {
//        corn = new Food(corn_id, corn_name, corn_cals, false);
//        il = new ItemLog();
//        il.makeItems();
//        il.createRemoveList();
//    }
//
//    @Test
//    public void testConstructor() {
//        assertEquals(corn_id, corn.getId());
//        assertEquals(corn_name, corn.getName());
//        assertEquals(corn_cals, corn.getCalories());
//    }
//
//
//    @Test
//    public void testAddItemsNoException() {
//        int b_cals = 80;
//        Food banana = new Food("300", "Banana", b_cals, true);
//        il.addItem(corn, il.getFoodEaten());
//        il.addItem(banana, il.getFoodEaten());
//        assertEquals(il.getFoodEaten().getDone().size(), 2);
//        assertTrue(il.getFoodEaten().getDone().contains(corn));
//        assertTrue(il.getFoodEaten().getDone().contains(banana));
//        assertEquals(il.getTotal(), corn_cals + b_cals);
//    }
//
//    @Test
//    public void testAddItemsHighTotalException() {
//        int b_cals = 8000;
//        Food banana = new Food("300", "Banana", b_cals, true);
//        il.addItem(corn, il.getFoodEaten());
//        il.addItem(banana, il.getFoodEaten());
//        assertEquals(il.getFoodEaten().getDone().size(), 2);
//        assertTrue(il.getFoodEaten().getDone().contains(corn));
//        assertTrue(il.getFoodEaten().getDone().contains(banana));
//        assertEquals(il.getTotal(), corn_cals + b_cals);
//    }
//
//    @Test
//    public void testAddItemsLowTotalException() {
//        int b_cals = -800;
//        Food banana = new Food("300", "Banana", b_cals, true);
//        il.addItem(corn, il.getFoodEaten());
//        il.addItem(banana, il.getFoodEaten());
//        assertEquals(il.getFoodEaten().getDone().size(), 2);
//        assertTrue(il.getFoodEaten().getDone().contains(corn));
//        assertTrue(il.getFoodEaten().getDone().contains(banana));
//        assertEquals(il.getTotal(), corn_cals + b_cals);
//    }
//
//
//    @Test
//    public void testAddItemToRemoveNoException() {
//        Food banana = new Food("300", "Banana", b_cals, true);
//        Food apple = new Food("301", "Apple", a_cals, true);
//        il.addItem(banana, il.getFoodEaten());
//        il.addItem(corn, il.getFoodEaten());
//        il.addItem(apple, il.getFoodEaten());
//        il.removeItem("301", il.getFoodEaten(), il.getExerciseDone(), il.getListToRemove());
//        il.removeItem("565", il.getFoodEaten(), il.getExerciseDone(), il.getListToRemove());
//        assertTrue(il.getListToRemove().contains(apple));
//        assertTrue(il.getListToRemove().contains(corn));
//        assertEquals(il.getListToRemove().size(), 2);
//    }
//
//    @Test
//    public void testRemoveItemsNoException() {
//        Food banana = new Food("300", "Banana", b_cals, true);
//        Food apple = new Food("301", "Apple", a_cals, true);
//        il.addItem(banana, il.getFoodEaten());
//        il.addItem(corn, il.getFoodEaten());
//        il.addItem(apple, il.getFoodEaten());
//        il.removeItem("301", il.getFoodEaten(), il.getExerciseDone(), il.getListToRemove());
//        il.removeItem("565", il.getFoodEaten(), il.getExerciseDone(), il.getListToRemove());
//        il.removeFromRemove(il.getFoodEaten(), il.getExerciseDone());
//        assertEquals(il.getFoodEaten().getDone().size(), 1);
//        assertEquals(il.getListToRemove().size(), 2);
//        assertTrue(il.getFoodEaten().getDone().contains(banana));
//        assertFalse(il.getFoodEaten().getDone().contains(apple));
//    }
//
//    @Test
//    public void testRemoveItemsHighTotalException() {
//        Food banana = new Food("300", "Banana", 3000, true);
//        Food apple = new Food("301", "Apple", a_cals, true);
//        Exercise jog = new Exercise("400", "jogging", -3000);
//        il.addItem(corn, il.getFoodEaten());
//        il.addItem(apple, il.getFoodEaten());
//        il.addItem(banana, il.getFoodEaten());
//
//        il.addItem(jog, il.getExerciseDone());
//        il.removeItem("400", il.getFoodEaten(), il.getExerciseDone(), il.getListToRemove());
//        il.removeItem("301", il.getFoodEaten(), il.getExerciseDone(), il.getListToRemove());
//        il.removeItem("565", il.getFoodEaten(), il.getExerciseDone(), il.getListToRemove());
//        il.removeFromRemove(il.getFoodEaten(), il.getExerciseDone());
//        assertEquals(il.getFoodEaten().getDone().size(), 1);
//        assertEquals(il.getListToRemove().size(), 3);
//        assertTrue(il.getFoodEaten().getDone().contains(banana));
//        assertFalse(il.getFoodEaten().getDone().contains(apple));
//    }
//
//    @Test
//    public void testRemoveItemsLowTotalException() {
//        Food banana = new Food("300", "Banana", b_cals, true);
//        Food apple = new Food("301", "Apple", a_cals, true);
//        il.addItem(banana, il.getFoodEaten());
//        il.addItem(corn, il.getFoodEaten());
//        il.addItem(apple, il.getFoodEaten());
//        il.removeItem("301", il.getFoodEaten(), il.getExerciseDone(), il.getListToRemove());
//        il.removeItem("565", il.getFoodEaten(), il.getExerciseDone(), il.getListToRemove());
//        il.removeFromRemove(il.getFoodEaten(), il.getExerciseDone());
//        assertEquals(il.getFoodEaten().getDone().size(), 1);
//        assertEquals(il.getListToRemove().size(), 2);
//        assertTrue(il.getFoodEaten().getDone().contains(banana));
//        assertFalse(il.getFoodEaten().getDone().contains(apple));
//    }
//
//
//    @Test
//    public void testRemoveOneWithDifferentPresent() {
//        Food banana = new Food("300", "Banana", b_cals, true);
//        Food apple = new Food("301", "Apple", a_cals, true);
//        il.addItem(banana, il.getFoodEaten());
//        il.addItem(corn, il.getFoodEaten());
//        il.addItem(apple, il.getFoodEaten());
//        il.removeItem("000", il.getFoodEaten(), il.getExerciseDone(), il.getListToRemove());
//        il.removeFromRemove(il.getFoodEaten(), il.getExerciseDone());
//        assertEquals(il.getListToRemove().size(), 0);
//        assertEquals(il.getFoodEaten().getDone().size(), 3);
//        assertEquals(corn_cals + b_cals + a_cals, il.getTotal());
//    }
//
//
//}
