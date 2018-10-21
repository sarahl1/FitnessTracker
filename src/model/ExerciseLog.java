package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ExerciseLog implements Iterable<Exercise>{
    private static ExerciseLog exerciseDone;
    private Collection<Exercise> exercises;

    private ExerciseLog(){
        exercises = new ArrayList<>();
    }

    public ExerciseLog getInstance(){
        if (exerciseDone == null)
            exerciseDone = new ExerciseLog();

        return exerciseDone;
    }

    @Override
    public Iterator<Exercise> iterator() {
        return null;
    }
}
