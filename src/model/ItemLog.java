package model;

public class ItemLog {
    protected ItemList allFood;
    protected ItemList allExercise;

    public ItemLog(){
        allFood = new FoodList();
        allExercise = new ExerciseList();
    }

    public ItemList getAllFood(){
        return allFood;
    }

    public ItemList getAllExercise(){
        return allExercise;
    }
}
