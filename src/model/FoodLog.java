package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class FoodLog implements Iterable<Food>{
    private static FoodLog foodEaten;
    private Collection<Food> food;

    private FoodLog(){
        food = new ArrayList<>();
    }

    public FoodLog getInstance(){
        if (foodEaten == null)
            foodEaten = new FoodLog();

        return foodEaten;
    }

    @Override
    public Iterator<Food> iterator() {
        return null;
    }

}
