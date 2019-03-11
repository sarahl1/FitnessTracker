package model;


import java.util.ArrayList;

public class FoodList extends ItemList{

    //MODIFIES: this
    //EFFECTS: if ItemList does not contain item, put the itemList and nutritional facts into the ItemList
    @Override
    public void addItem(Item i){
        if (!getLog().containsKey(i)){
            getLog().put(i, i.nutriFacts.getNutriFacts());
            i.setList(this);
        }
    }


}
