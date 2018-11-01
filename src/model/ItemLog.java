package model;

public class ItemLog {
    protected ItemList allFood;
    protected ItemList allExercise;

    public ItemLog(){
        allFood = new FoodList();
        allExercise = new ExerciseList();
    }

    //EFFECTS: returns allFood
    public ItemList getAllFood(){
        return allFood;
    }

    //EFFECTS: returns allExercise
    public ItemList getAllExercise(){
        return allExercise;
    }
}
