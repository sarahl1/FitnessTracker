package model;


public class ExerciseDone extends ItemDone {

    public void addDone(Item i){
        if(!this.getDone().contains(i)) {
            this.getDone().add(i);
            i.setCompleted(this);
        } else this.getDone().add(i);
    }

    //MODIFIES: this
    //EFFECTS: if ItemList does not contain item, put the itemList and nutritional facts into the ItemList
    public void removeDone(Item i){
        if (this.getDone().contains(i)){
            this.getDone().remove(i);
            i.setCompleted(new ExerciseDone());
        }
    }
}
