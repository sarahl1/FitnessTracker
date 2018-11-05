package model;


public class ExerciseDone extends ItemDone {

    public void addDone(Item i){
            this.getDone().add(i);
            i.setCompleted(this);
    }

    //MODIFIES: this
    //EFFECTS: if ItemList does not contain item, put the itemList and nutritional facts into the ItemList
    public void removeDone(Item i){
            this.getDone().remove(i);
            i.setCompleted(new ExerciseDone());
    }
}
