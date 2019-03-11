package model;


public class ExerciseDone extends ItemDone{

    //MODIFIES: this
    //EFFECTS: adds item to list of exercise completed
    public void addDone(Item i){
            this.getDone().add(i);
            i.setCompleted(this);
    }

    //MODIFIES: this
    //EFFECTS: remove the item from the list
    public void removeDone(Item i){
            this.getDone().remove(i);
            i.setCompleted(new ExerciseDone());
    }

}
