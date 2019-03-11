package model;

public class ExerciseList extends ItemList {

    //MODIFIES: this
    //EFFECTS: adds item to list on UI
    @Override
    public void addItem(Item i) {
        if (!getLog().containsKey(i)) {
            getLog().put(i, i.nutriFacts.getNutriFacts());
            i.setList(this);
        }
    }

}
